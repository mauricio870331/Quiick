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
public class  RestaurarBd implements Runnable {

    SimpleDateFormat sa = new SimpleDateFormat("yyyyMMddhhmmss");
    SimpleDateFormat sa2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Calendar c = new GregorianCalendar();
    

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
                String nombreArchivo = "";
                System.out.println("iniciando copia");
                File[] roots = File.listRoots();
                if (listarDirectorio(roots[0], "")) {
                    String comando = "C:\\xampp\\mysql\\bin\\\"mysqldump -uroot -pPpY8lfp838Et3716  quiick < " + folder.toString() + "/" + nombreArchivo;
                    backup.exec(comando);
                } else {
                    String comando = "cmd /K " + new File("").getAbsolutePath() + "/src/batch/backupmysql.bat";
                    backup.exec(comando);
                }
                System.out.println("copia finalizada");
                ProgressBackup.setVisible(false);
            } catch (IOException ex) {                            
                ProgressBackup.setVisible(false);
                System.out.println(ex.getMessage());
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


}
