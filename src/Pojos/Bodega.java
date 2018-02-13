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
 * @author Juan
 */
public class Bodega extends Persistencia implements Serializable {

    private BigDecimal idBodega;
    private BigDecimal idempresa;
    private BigDecimal idSede;
    private String nombreBodega;
    private String estado;

    public Bodega() {
        super();
    }

    public BigDecimal getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(BigDecimal idempresa) {
        this.idempresa = idempresa;
    }

    public BigDecimal getIdSede() {
        return idSede;
    }

    public void setIdSede(BigDecimal idSede) {
        this.idSede = idSede;
    }

    public String getNombreBodeg() {
        return nombreBodega;
    }

    public void setNombreBodeg(String nombreBodeg) {
        this.nombreBodega = nombreBodeg;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(BigDecimal idBodega) {
        this.idBodega = idBodega;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into Bodega (idempresa,idsede,nombreBodega,estado) values (?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setBigDecimal(1, idempresa);
            preparedStatement.setBigDecimal(2, idSede);
            preparedStatement.setString(3, nombreBodega);
            preparedStatement.setString(4, estado);

            transaccion = Bodega.this.getConecion().transaccion(preparedStatement);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public java.util.List List() {
        ArrayList<Bodega> List = new ArrayList();
        String prepareQuery = "select idBodega,idempresa,idSede,nombreBodega,estado from Bodega";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Bodega.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Bodega tabla = new Bodega();
                tabla.setIdBodega(rs.getBigDecimal(1));
                tabla.setIdempresa(rs.getBigDecimal(2));
                tabla.setIdSede(rs.getBigDecimal(3));
                tabla.setNombreBodega(rs.getString(4));
                tabla.setEstado(rs.getString(5));
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

    public java.util.List ListXSedes(int sede) {
        ArrayList<Bodega> List = new ArrayList();
        String prepareQuery = "select idBodega,idempresa,idSede,nombreBodega,estado from Bodega where idSede=" + sede;
        System.out.println(prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Bodega.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Bodega tabla = new Bodega();
                tabla.setIdBodega(rs.getBigDecimal(1));
                tabla.setIdempresa(rs.getBigDecimal(2));
                tabla.setIdSede(rs.getBigDecimal(3));
                tabla.setNombreBodega(rs.getString(4));
                tabla.setEstado(rs.getString(5));
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

    @Override
    public String toString() {
        return nombreBodega;
    }

}
