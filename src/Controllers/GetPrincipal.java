/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Views.Modulo1;

/**
 *
 * @author Mauricio Herrera
 */
public class GetPrincipal {

    public static Modulo1 pr = null;

    public static Modulo1 getPrincipal() {
        try {
            if (pr == null) {
                pr = new Modulo1();
                pr.setLocationRelativeTo(null);
            } else {
                System.out.println("Principal ya estaba instanceado");
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }
        return pr;
    }
}
