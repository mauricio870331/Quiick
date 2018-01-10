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
public class RutinaDias extends Persistencia implements Serializable {
    
    private int idRutinaDias;
    private Rutina objRutina;
    private dias objdias;
    
    public RutinaDias() {
        super();
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
    
    public dias getObjdias() {
        if (objdias == null) {
            objdias = new dias();
        }
        return objdias;
    }
    
    public void setObjdias(dias objdias) {
        this.objdias = objdias;
    }
    
    public int getIdRutinaDias() {
        return idRutinaDias;
    }
    
    public void setIdRutinaDias(int idRutinaDias) {
        this.idRutinaDias = idRutinaDias;
    }
    
    
    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into RutinaDias (idRutina,idDias) values (?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, objRutina.getIdRutina());
            preparedStatement.setInt(2, objdias.getIdDias());
            
            transaccion = RutinaDias.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update RutinaDias set idDias=? where  idRutina=? and idRutinaDias=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setInt(1, objdias.getIdDias());
            preparedStatement.setInt(2, objRutina.getIdRutina());
            preparedStatement.setInt(3, idRutinaDias);
            
            transaccion = RutinaDias.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from RutinaDias where idrutina=? and idRutinaDias=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, objRutina.getIdRutina());
            preparedStatement.setInt(2, idRutinaDias);
            transaccion = RutinaDias.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<RutinaDias> List = new ArrayList();
        String prepareQuery = "select * from RutinaDias";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = RutinaDias.super.getConecion().query(prepareQuery);
            
            while (rs.next()) {
                
                RutinaDias tabla = new RutinaDias();
                Rutina tablaRut = new Rutina();
                dias tabladia = new dias();
                
                tablaRut.setIdRutina(rs.getInt(1));
                tabla.setIdRutinaDias(rs.getInt(2));
                tabladia.setIdDias(rs.getInt(3));
                
                tabla.setObjRutina(tablaRut);
                tabla.setObjdias(tabladia);
                
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
