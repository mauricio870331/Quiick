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

/**
 *
 * @author Mauricio Herrera
 */
public class GetPrincipal {

    public static Modulo1 M1 = null;
    public static Modulo2 M2 = null;
    public static Modulo3 M3 = null;
    public static Modulo4 M4 = null;
    public static ModuloRoot MR = null;
    

    public static Modulo1 getModulo1() {
        try {
            if (M1 == null) {
                M1 = new Modulo1();
                M1.setLocationRelativeTo(null);
            } else {
                System.out.println("Principal ya estaba instanceado");
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }
        return M1;
    }
    
    public static ModuloRoot getModuloRoot() {
        try {
            if (MR == null) {
                MR = new ModuloRoot();
                MR.setLocationRelativeTo(null);
            } else {
                System.out.println("Principal ya estaba instanceado");
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }
        return MR;
    }

    public static Modulo2 getModulo2() {
        try {
            if (M2 == null) {
                M2 = new Modulo2();
                M2.setLocationRelativeTo(null);
            } else {
                System.out.println("Principal ya estaba instanceado");
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }
        return M2;
    }

    public static Modulo3 getModulo3() {
        try {
            if (M3 == null) {
                M3 = new Modulo3();
                M3.setLocationRelativeTo(null);
            } else {
                System.out.println("Principal ya estaba instanceado");
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }
        return M3;
    }

    public static Modulo4 getModulo4() {
        try {
            if (M4 == null) {
                M4 = new Modulo4();
                M4.setLocationRelativeTo(null);
            } else {
                System.out.println("Principal ya estaba instanceado");
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }
        return M4;
    }

}
