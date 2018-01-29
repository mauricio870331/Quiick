/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Views.Modulo1;
import Views.Modulo2;
import Views.Modulo3;
import Views.Modulo4;
import Views.ModuloRoot;
import java.io.IOException;

/**
 *
 * @author Mauricio Herrera
 */
public class GetController {

    public static ControllerM1 M1 = null;
    public static ControllerM2 M2 = null;
    public static ControllerM3 M3 = null;
    public static ControllerM4 M4 = null;
    public static ControllerMRoot MR = null;
    

    public static ControllerM1 getControllerM1() {
        try {
            if (M1 == null) {
                M1 = new ControllerM1();                
            } else {
                System.out.println("Controlador Modulo 1 ya fue instanceado");
            }
        } catch (IOException e) {
            System.out.println("error = " + e);
        }
        return M1;
    }
    
    
    public static ControllerM2 getControllerM2() {
        try {
            if (M2 == null) {
                M2 = new ControllerM2();
            } else {
                System.out.println("Controlador Modulo 2 ya fue instanceado");
            }
        } catch (IOException e) {
            System.out.println("error = " + e);
        }
        return M2;
    }

    public static ControllerM3 getControllerM3() {
        try {
            if (M3 == null) {
                M3 = new ControllerM3();
            } else {
                System.out.println("Controlador Modulo 3 ya fue instanceado");
            }
        } catch (IOException e) {
            System.out.println("error = " + e);
        }
        return M3;
    }

     public static ControllerM4 getControllerM4() {
        try {
            if (M4 == null) {
                M4 = new ControllerM4();
            } else {
                System.out.println("Controlador Modulo 4 ya fue instanceado");
            }
        } catch (IOException e) {
            System.out.println("error = " + e);
        }
        return M4;
    }
     
      public static ControllerMRoot getControllerMRoot() {
        try {
            if (MR == null) {
                MR = new ControllerMRoot();
            } else {
                System.out.println("Controlador Modulo Root ya fue instanceado");
            }
        } catch (IOException e) {
            System.out.println("error = " + e);
        }
        return MR;
    }

}