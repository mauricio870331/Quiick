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
public class Unidad extends Persistencia implements Serializable {

    private BigDecimal cod_unidad;
    private String descripcion;
    private String siglas;

    public Unidad() {
        super();
    }

    public BigDecimal getCod_unidad() {
        return cod_unidad;
    }

    public void setCod_unidad(BigDecimal cod_unidad) {
        this.cod_unidad = cod_unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
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
        ArrayList<Unidad> List = new ArrayList();
        String prepareQuery = "SELECT * FROM unidad";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Unidad.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Unidad tabla = new Unidad();

                tabla.setCod_unidad(rs.getBigDecimal(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setSiglas(rs.getString(3));

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
