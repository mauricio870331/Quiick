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

/**
 *
 * @author admin
 */
public class SubMenus extends Persistencia implements Serializable {

    private int idSubMenu;
    private int idMenu;
    private String sub_menu;

    public SubMenus() {
        super();
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    @Override
    public int create() {
        int transaccion = -1;
//        String prepareInsert = "insert into Musculos (descripcion,estado) values (?,?)";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            this.getConecion().con.setAutoCommit(false);
//            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
//            preparedStatement.setString(1, descripcion);
//            preparedStatement.setString(2, Estado);
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
        ArrayList<SubMenus> List = new ArrayList();
        String prepareQuery = "select sm.* from sub_menus sm "
                + "inner join menus_usuarios mu on sm.id_submenu = mu.id_submenu "
                + "where sm.id_menu = " + getIdMenu();
//        System.out.println("prepareQuery " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = SubMenus.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                SubMenus tabla = new SubMenus();
                tabla.setIdSubMenu(rs.getInt(1));
                tabla.setIdMenu(rs.getInt(2));
                tabla.setSub_menu(rs.getString(3));
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

    public ArrayList<SubMenus> List(String filtro) {
//        ArrayList<Menus> List = new ArrayList();
//        String prepareQuery = "select * from Musculos where Estado = 'A' and Descripcion like '%" + filtro + "%'";
//        System.out.println("prepareQuery " + prepareQuery);
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            ResultSet rs = Menus.super.getConecion().query(prepareQuery);
//            while (rs.next()) {
//                Menus tabla = new Menus();
//                tabla.setIdMusculo(rs.getInt(1));
//                tabla.setdescripcion(rs.getString(2));
//                tabla.setEstado(rs.getString(3));
//                List.add(tabla);
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
        return null;
    }

    public SubMenus getMusculoById(int idMusculo) {
        SubMenus m = new SubMenus();
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
            ResultSet rs = SubMenus.super.getConecion().query(prepareQuery);
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

    public int getIdSubMenu() {
        return idSubMenu;
    }

    public void setIdSubMenu(int idSubMenu) {
        this.idSubMenu = idSubMenu;
    }

    public String getSub_menu() {
        return sub_menu;
    }

    public void setSub_menu(String sub_menu) {
        this.sub_menu = sub_menu;
    }

    @Override
    public String toString() {
        return "SubMenus{" + "idSubMenu=" + idSubMenu + ", idMenu=" + idMenu + ", sub_menu=" + sub_menu + '}';
    }

}
