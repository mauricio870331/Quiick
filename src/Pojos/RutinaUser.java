/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class RutinaUser extends Persistencia implements Serializable {
    
    private int idRutinaUser;
    private Rutina objRutina;
    private Usuario objUsuario;
    
    public RutinaUser() {
        super();
    }
    
    public int getIdRutinaUser() {
        return idRutinaUser;
    }
    
    public void setIdRutinaUser(int idRutinaUser) {
        this.idRutinaUser = idRutinaUser;
    }
    
    public Rutina getObjRutina() {
        if (objRutina == null) {
            objRutina = new Rutina();
        }
        return objRutina;
    }
    
    public void setObjRutina(Rutina objRutina) {
        this.objRutina = objRutina;
    }
    
    public Usuario getObjUsuario() {
        if (objUsuario == null) {
            objUsuario = new Usuario();
        }
        return objUsuario;
    }
    
    public void setObjUsuario(Usuario objUsuario) {
        this.objUsuario = objUsuario;
    }
    
    
       
    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into RutinaUser (idUsuario,usuario,idsede,idempresa,idpersona,idRutina) values (?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, objUsuario.getObjUsuariosID().getIdUsuario());
            preparedStatement.setString(2, objUsuario.getObjUsuariosID().getUsuario());
            preparedStatement.setInt(3, objUsuario.getObjUsuariosID().getIdSede());
            preparedStatement.setInt(4, objUsuario.getObjUsuariosID().getIdempresa());
            preparedStatement.setInt(5, objUsuario.getObjUsuariosID().getIdPersona());
            preparedStatement.setInt(6, objRutina.getIdRutina());
            
            transaccion = RutinaUser.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update RutinaUser set idUsuario=?,usuario=?,idsede=?,idempresa=?,idpersona=?,idRutina=? "
                + " where idRutinaUser=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setInt(1, objUsuario.getObjUsuariosID().getIdUsuario());
            preparedStatement.setString(2, objUsuario.getObjUsuariosID().getUsuario());
            preparedStatement.setInt(3, objUsuario.getObjUsuariosID().getIdSede());
            preparedStatement.setInt(4, objUsuario.getObjUsuariosID().getIdempresa());
            preparedStatement.setInt(5, objUsuario.getObjUsuariosID().getIdPersona());
            preparedStatement.setInt(6, objRutina.getIdRutina());
            preparedStatement.setInt(7, idRutinaUser);
            
            transaccion = RutinaUser.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from RutinaUser where idRutinaUser=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, idRutinaUser);
            
            transaccion = RutinaUser.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<RutinaUser> List = new ArrayList();
        String prepareQuery = "select * from RutinaUser";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = RutinaUser.super.getConecion().query(prepareQuery);
            
            while (rs.next()) {
                RutinaUser tabla = new RutinaUser();
                Usuario tablaUsu = new Usuario();
                Rutina tablaRut = new Rutina();
                
                tabla.setIdRutinaUser(rs.getInt(1));
                tablaUsu.getObjUsuariosID().setIdUsuario(rs.getInt(2));
                tablaUsu.getObjUsuariosID().setUsuario(rs.getString(3));
                tablaUsu.getObjUsuariosID().setIdSede(rs.getInt(4));
                tablaUsu.getObjUsuariosID().setIdempresa(rs.getInt(5));
                tablaUsu.getObjUsuariosID().setIdPersona(rs.getInt(6));
                
                tablaRut.setIdRutina(rs.getInt(7));
                
                tabla.setObjUsuario(tablaUsu);
                tabla.setObjRutina(tablaRut);
                
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
    
}
