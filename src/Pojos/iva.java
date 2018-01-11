/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author JuanDavid
 */
public class iva extends Persistencia implements Serializable {

    private BigDecimal idIva;
    private String descripcion;
    private BigDecimal porcentaje;
    private String estado;

    public iva() {
        super();
    }

    public BigDecimal getIdIva() {
        return idIva;
    }

    public void setIdIva(BigDecimal idIva) {
        this.idIva = idIva;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int create() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        ArrayList<iva> List = new ArrayList();
        String prepareQuery = "SELECT * FROM iva WHERE estado='A'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = iva.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                iva tabla = new iva();
                tabla.setIdIva(rs.getBigDecimal(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setPorcentaje(rs.getBigDecimal(3));
                tabla.setEstado(rs.getString(4));
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

    @Override
    public String toString() {
        return descripcion;
    }

}
