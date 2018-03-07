/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import Views.Login;

/**
 *
 * @author admin
 */
public class Contenedor {

    public static objectobusqueda ob = null;

    public static objectobusqueda getLogin() {
        try {
            if (ob == null) {
                ob = new objectobusqueda();
            } else {
                System.out.println("Login ya estaba instanceado");
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }
        return ob;
    }
}
