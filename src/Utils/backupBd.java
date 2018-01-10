/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import static Views.Login.ProgressBackup;
import static Views.Login.usageDisk;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author clopez
 */
public class backupBd implements Runnable {

    SimpleDateFormat sa = new SimpleDateFormat("yyyyMMddhhmmss");
    SimpleDateFormat sa2 = new SimpleDateFormat("yyyy-MM-dd");
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
                System.out.println("iniciando copia");
                File[] roots = File.listRoots();
                if (listarDirectorio(roots[0], "")) {
                    backup.exec("C:/xampp/mysql/bin/mysqldump -v -v -v --host=localhost --user=root  --port=3306 --protocol=tcp --force --allow-keywords --compress --add-drop-table --routines --result-file=D:/Backup/appgym" + sa.format(new Date()) + ".sql --databases appgym");
                } else {
                    backup.exec("C:/Program Files (x86)/MySQL/MySQL Server 5.0/bin/mysqldump -v -v -v --host=localhost --user=root --password=ningina --port=3306 --protocol=tcp --force --allow-keywords --compress --add-drop-table --routines --result-file=D:/Backup/appgym" + sa.format(new Date()) + ".sql --databases appgym");
                }
                System.out.println("copia finalizada");
                ProgressBackup.setVisible(false);
            } catch (IOException ex) {
                System.out.println("error " + ex.getMessage());
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
