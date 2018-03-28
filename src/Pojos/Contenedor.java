/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import Views.Login;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Contenedor {

    public static ArrayList<producto> ListProductos=new ArrayList();
    public static Usuario usuario;

    public static ArrayList<producto> getListProductos() {
        return ListProductos;
    }

    public static void setListProductos(ArrayList<producto> ListProductos) {
        Contenedor.ListProductos = ListProductos;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Contenedor.usuario = usuario;
    }
    
    
}
