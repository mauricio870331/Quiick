/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Pojos.Rol;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 *
 * @author Mauricio Herrera
 */
public class NewClass {
    
    public static SimpleDateFormat hh = new SimpleDateFormat("");
    
    public static void main(String[] args) {
//        System.out.println(System.getProperty("java.io.tmpdir"));
//
//        try {
//
//            File archivo = new File(System.getProperty("java.io.tmpdir")+"\\default.png");
//
//            boolean estatus = archivo.delete();
//
//            if (!estatus) {
//
//                System.out.println("Error no se ha podido eliminar el  archivo");
//
//            } else {
//
//                System.out.println("Se ha eliminado el archivo exitosamente");
//
//            }
//
//        } catch (Exception e) {
//
//            System.out.println(e);
//
//        }
        Rol r = new Rol();
        r.setDescripcion("prueba");
        r.setEstado("A");
        r.create();
        System.out.println("Id "+r.ultimoid());
       
   }
    
}
