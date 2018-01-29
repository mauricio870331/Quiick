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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author admin
 */
public class PerfilRoles extends Persistencia implements Serializable {

    private ArrayList<String> perfiles;
    private int idRol;
    private int idUserlog;
    private String Estado;
    SimpleDateFormat fFortmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public PerfilRoles() {
        super();
    }

    public ArrayList<String> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(ArrayList<String> perfiles) {
        this.perfiles = perfiles;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String deleteAfter = "delete from perfiles_x_rol where id_rol = ?";
        String prepareInsert = "insert into perfiles_x_rol (id_perfil,id_rol,estado,create_at,update_at,create_by,update_by) values (?,?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement prepareddelete = this.getConecion().con.prepareStatement(deleteAfter);
            prepareddelete.setInt(1, idRol);
            transaccion = PerfilRoles.this.getConecion().transaccion(prepareddelete);
            for (String perfil : perfiles) {
                PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
                preparedStatement.setInt(1, Integer.parseInt(perfil));
                preparedStatement.setInt(2, idRol);
                preparedStatement.setString(3, Estado);
                preparedStatement.setString(4, fFortmat.format(new Date()));
                preparedStatement.setString(5, fFortmat.format(new Date()));
                preparedStatement.setInt(6, idUserlog);
                preparedStatement.setInt(7, idUserlog);
                transaccion = PerfilRoles.this.getConecion().transaccion(preparedStatement);
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
        return -1;
    }

    @Override
    public int remove() {
        int transaccion = -1;
        String PrepareDelete = "delete from Rol where idRol=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);

            transaccion = PerfilRoles.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List List() {
        ArrayList<String> List = new ArrayList();
        String prepareQuery = "SELECT p.id_perfil FROM perfiles_x_rol pr "
                + "join perfiles p on p.id_perfil = pr.id_perfil "
                + "where id_rol = " + idRol;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = PerfilRoles.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                List.add(rs.getString(1));
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
        return List;
    }

    public Perfil getPerfilbyId(int id_perfil) {
        Perfil r = null;
        try {
            String sql = "select * from perfiles where id_perfil = " + id_perfil;
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = PerfilRoles.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                r = new Perfil(rs.getInt(1), rs.getString(2), rs.getString(3));
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
        return r;
    }

    public int ultimoid() {
        int id = -1;
        try {
            String sql = "Select LAST_INSERT_ID()";
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = PerfilRoles.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                id = rs.getInt(1);
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
        return id;
    }

    public int getIdUserlog() {
        return idUserlog;
    }

    public void setIdUserlog(int idUserlog) {
        this.idUserlog = idUserlog;
    }

}
