/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author admin
 */
public class MenusForUsuarios extends Persistencia implements Serializable {

    private int idSubMenu;
    private int idMenu;
    private int idUsuario;
    private ArrayList<MenusForUsuarios> listaMenus;

    public MenusForUsuarios() {
        super();
    }

    public int getIdSubMenu() {
        return idSubMenu;
    }

    public void setIdSubMenu(int idSubMenu) {
        this.idSubMenu = idSubMenu;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return idSubMenu + "_" + idMenu;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String deleteAfter = "delete from menus_usuarios where idUsuario = ?";
        String prepareInsert = "insert into menus_usuarios (id_submenu,id_menu,idUsuario) values (?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedelete = this.getConecion().con.prepareStatement(deleteAfter);
            preparedelete.setInt(1, listaMenus.get(0).getIdUsuario());
            transaccion = MenusForUsuarios.this.getConecion().transaccion(preparedelete);
            for (MenusForUsuarios next : listaMenus) {
                PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
                preparedStatement.setInt(1, next.getIdSubMenu());
                preparedStatement.setInt(2, next.getIdMenu());
                preparedStatement.setInt(3, next.getIdUsuario());
                transaccion = MenusForUsuarios.this.getConecion().transaccion(preparedStatement);
            }

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
        int transaccion = -1;
//        String PrepareUpdate = "update Musculos set descripcion=?,Estado=? where idMusculo=?";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            this.getConecion().con.setAutoCommit(false);
//            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
//            preparedStatement.setString(1, descripcion);
//            preparedStatement.setString(2, Estado);
//            preparedStatement.setInt(3, idMusculo);
//
//            transaccion = Menus.this.getConecion().transaccion(preparedStatement);
//        } catch (SQLException ex) {
//            System.out.println("Error SQL : " + ex.toString());
//        } finally {
//            try {
//                this.getConecion().getconecion().setAutoCommit(true);
//                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
        return transaccion;
    }

    @Override
    public int remove() {
        int transaccion = -1;
//        String PrepareDelete = "delete from Musculos where idMusculo=?";
//        String PrepareDelete = "update Musculos set Estado = 'I' where idMusculo=?";
//        ResultSet rs;
//        String sql = "Select m.idMusculo from Musculos m , medidaxmusculo mm , rutdiaejercicio r "
//                + "where (m.idMusculo = mm.idMusculo or m.idMusculo = r.idMusculo) and  m.idMusculo =" + idMusculo;
//
//        System.out.println(sql);
//
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            this.getConecion().con.setAutoCommit(false);
//            rs = Menus.this.getConecion().query(sql);
//            if (!rs.absolute(1)) {
//                PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
//                preparedStatement.setInt(1, idMusculo);
//                transaccion = Menus.this.getConecion().transaccion(preparedStatement);
//            } else {
//                transaccion = 0;
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error SQL : " + ex.toString());
//        } finally {
//            try {
//                this.getConecion().getconecion().setAutoCommit(true);
//                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
        return transaccion;
    }

    @Override
    public java.util.List List() {
        ArrayList<MenusForUsuarios> List = new ArrayList();
        String prepareQuery = "select * from menus_usuarios where idUsuario = " + idUsuario;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = MenusForUsuarios.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                MenusForUsuarios tabla = new MenusForUsuarios();
                tabla.setIdSubMenu(rs.getInt(1));
                tabla.setIdMenu(rs.getInt(2));
                tabla.setIdUsuario(rs.getInt(3));
                List.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().getconecion().setAutoCommit(true);
//                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return List;
    }

    public ArrayList<MenusForUsuarios> ListMenus(String filtro) {
        ArrayList<MenusForUsuarios> List = (ArrayList<MenusForUsuarios>) List();
//        Iterator<MenusXUsuarios> iterator = List.iterator();
//        ArrayList<SubMenus> ListSubmenus = new ArrayList();
//        String prepareQuery = "select * from sub_menus";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            ResultSet rs = MenusForUsuarios.super.getConecion().query(prepareQuery);
//            while (rs.next()) {
//                SubMenus tabla = new SubMenus();
//                tabla.setIdSubMenu(rs.getInt(1));
//                tabla.setIdMenu(rs.getInt(2));
//                tabla.setSub_menu(rs.getString(3));
//                ListSubmenus.add(tabla);
//            }
//            while (iterator.hasNext()) {
//                MenusForUsuarios next = iterator.next();
//                next.setListSubmenus(addSubmenusArray(next, ListSubmenus));  
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error Consulta : " + ex.toString());
//        } finally {
//            try {
//                this.getConecion().getconecion().setAutoCommit(true);
////                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
        return List;
    }

    public ArrayList<SubMenus> addSubmenusArray(MenusForUsuarios mnu, ArrayList<SubMenus> ListSubmenus) {
        ArrayList<SubMenus> listaForMenu = new ArrayList();
        Iterator<SubMenus> sitr = ListSubmenus.iterator();
        while (sitr.hasNext()) {
            SubMenus next = sitr.next();
            if (mnu.getIdMenu() == next.getIdMenu()) {
                SubMenus s = new SubMenus();
                s.setIdSubMenu(next.getIdSubMenu());
                s.setIdMenu(next.getIdMenu());
                s.setSub_menu(next.getSub_menu());
                listaForMenu.add(s);
            }
        }
        return listaForMenu;
    }

    public MenusForUsuarios getMusculoById(int idMusculo) {
        MenusForUsuarios m = new MenusForUsuarios();
//        String prepareQuery = "select * from Musculos where idMusculo = " + idMusculo + "  and Estado = 'A'";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            ResultSet rs = Menus.super.getConecion().query(prepareQuery);
//            while (rs.next()) {
//                m.setIdMusculo(rs.getInt(1));
//                m.setdescripcion(rs.getString(2));
//                m.setEstado(rs.getString(3));
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error Consulta : " + ex.toString());
//        } finally {
//            try {
//                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
        return m;
    }

    public String getNombreMusculoById(int idMusculo) {
        String nombre = "";
        String prepareQuery = "select Descripcion from Musculos where idMusculo = " + idMusculo + "  and Estado = 'A'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = MenusForUsuarios.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                nombre = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return nombre;
    }

    public ArrayList<MenusForUsuarios> getListaMenus() {
        return listaMenus;
    }

    public void setListaMenus(ArrayList<MenusForUsuarios> listaMenus) {
        this.listaMenus = listaMenus;
    }
}
