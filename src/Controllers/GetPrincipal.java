/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Views.FrmPrincipal;

/**
 *
 * @author Mauricio Herrera
 */
public class GetPrincipal {

    public static FrmPrincipal pr = null;

    public static FrmPrincipal getPrincipal() {
        try {
            if (pr == null) {
                pr = new FrmPrincipal();
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
