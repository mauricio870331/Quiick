/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Pojos.Persistencia;
import static Views.Login.ProgressBackup;
import static Views.Login.usageDisk;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author clopez
 */
public class backupBd extends Persistencia implements Runnable {

    SimpleDateFormat sa = new SimpleDateFormat("yyyyMMddhhmmss");
    SimpleDateFormat sa2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Calendar c = new GregorianCalendar();
    int id;
    Date fecha = new Date();
    String error;
    String causa;
    String linea_archivo;
    int notificado;

    @Override
    public void run() {

        File folder = new File("D:/Backup");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File[] ficheros = folder.listFiles();
        long difMs = 0;
        long dias = 0;
        long weighFile = 3;//KB
        for (File fichero : ficheros) {
            weighFile += fichero.length() / 1024; //Kb 1024 kb = 1 Mega -- 1024 megas = 1 Giga
            Date d = new Date(fichero.lastModified());
            Date current = new Date();
            difMs = (current.getTime() - d.getTime());
            dias = difMs / 86400000;
            if ((int) dias == 15) {
                if (fichero.delete()) {
                    System.out.println("eliminado");
                }
            }
        }
        if ((weighFile / 1024) < 1024) {
            Runtime backup = Runtime.getRuntime();
            try {
                System.out.println("iniciando copia");
                File[] roots = File.listRoots();
                if (listarDirectorio(roots[0], "")) {
                    String comando = "cmd /K " + new File("").getAbsolutePath() + "/src/batch/backupxampp.bat";
                    backup.exec(comando);
                } else {
                    String comando = "cmd /K " + new File("").getAbsolutePath() + "/src/batch/backupmysql.bat";
                    backup.exec(comando);
                }
                System.out.println("copia finalizada");
                ProgressBackup.setVisible(false);
            } catch (IOException ex) {
                error = ex.getMessage();
                causa = "Generar backup BD";
                linea_archivo = "Linea 74, Archivo Quiick\\src\\Utils\\backupBd.java";
                notificado = 3;
                create();
                ProgressBackup.setVisible(false);
//                System.out.println(ex.getMessage());
            }
        }
        usageDisk.setText("Almacenamiento de BD: " + (weighFile / 1024) + "Mb");
    }

    public boolean listarDirectorio(File f, String separador) {
        boolean r = false;
        File[] ficheros = f.listFiles();
        if (ficheros != null) {
            for (int x = 0; x < ficheros.length; x++) {
                if (ficheros[x].isDirectory()) {
                    if (ficheros[x].getName().equals("xampp")) {
                        r = true;
                    }
                }
            }
        }
        return r;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into log_errores (fecha,error,causa,linea_archivo,notificar) "
                + "values (?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, sa.format(fecha));
            preparedStatement.setString(2, error);
            preparedStatement.setString(3, causa);
            preparedStatement.setString(4, linea_archivo);
            preparedStatement.setInt(5, notificado);
            transaccion = backupBd.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } finally {
            try {
                this.getConecion().getconecion().setAutoCommit(true);
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return transaccion;
    }

    @Override
    public int edit() {
        return 0;
    }

    @Override
    public int remove() {
        return 0;
    }

    @Override
    public java.util.List List() {
        return null;
    }

}
