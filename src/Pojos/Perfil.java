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
public class Perfil extends Persistencia implements Serializable {

    private int idPerfil;
    private String Descripcion;
    private String Estado;   
    SimpleDateFormat fFortmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Perfil() {
        super();
    }

    public Perfil(int idPerfil, String Descripcion, String Estado) {
        this.idPerfil = idPerfil;
        this.Descripcion = Descripcion;
        this.Estado = Estado;
    }
    
    

    

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    @Override
    public String toString() {
        return Descripcion;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into perfiles (nombre,estado,create_at,update_at) values (?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, Descripcion);
            preparedStatement.setString(2, (Estado.equalsIgnoreCase("Activo") ? "A" : "I"));
            preparedStatement.setString(3, fFortmat.format(new Date()));
            preparedStatement.setString(4, fFortmat.format(new Date()));
            transaccion = Perfil.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update perfiles set nombre=?,estado=?,update_at=? where id_perfil=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, Descripcion);
            preparedStatement.setString(2, (Estado.equalsIgnoreCase("Activo") ? "A" : "I"));
            preparedStatement.setString(3, fFortmat.format(new Date()));
            preparedStatement.setInt(4, idPerfil);
            transaccion = Perfil.this.getConecion().transaccion(preparedStatement);
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
    public int remove() {
        int transaccion = -1;
        String PrepareDelete = "delete from Rol where idRol=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, idPerfil);
            transaccion = Perfil.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<Perfil> List = new ArrayList();
        String prepareQuery = "select * from perfiles where estado = 'A'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Perfil.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Perfil tabla = new Perfil();
                tabla.setIdPerfil(rs.getInt(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setEstado(rs.getString(3));
                List.add(tabla);
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
            ResultSet rs = Perfil.super.getConecion().query(sql);
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
            ResultSet rs = Perfil.super.getConecion().query(sql);
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
}