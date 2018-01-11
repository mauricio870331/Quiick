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
public class categoria extends Persistencia implements Serializable {

    private BigDecimal idCategoria;
    private String Descripcion;
    private String Estado;
    private BigDecimal ganancia;

    public categoria() {
        super();
    }

    public BigDecimal getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(BigDecimal idCategoria) {
        this.idCategoria = idCategoria;
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

    public BigDecimal getGanancia() {
        return ganancia;
    }

    public void setGanancia(BigDecimal ganancia) {
        this.ganancia = ganancia;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into categoria (descripcion,Estado,Ganancia) "
                + "values (?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, Descripcion);
            preparedStatement.setString(2, Estado);
            preparedStatement.setBigDecimal(3, ganancia);
            transaccion = categoria.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update categoria set descripcion=?,Estado=?,Ganancia=?  where idCategoria=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, Descripcion);
            preparedStatement.setString(2, Estado);
            preparedStatement.setBigDecimal(3, ganancia);
            preparedStatement.setBigDecimal(4, idCategoria);

            transaccion = categoria.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from categoria where idCategoria=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setBigDecimal(1, idCategoria);
            transaccion = categoria.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<categoria> List = new ArrayList();
        String prepareQuery = "select idCategoria,descripcion,Estado,Ganancia from categoria";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = categoria.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                categoria tabla = new categoria();

                tabla.setIdCategoria(rs.getBigDecimal(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setEstado(rs.getString(3));
                tabla.setGanancia(rs.getBigDecimal(4));
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

    public categoria buscarCategoria(String idcategoria) {
        categoria Micategoria = new categoria();
        String prepareQuery = "select idCategoria,descripcion,Estado,Ganancia from categoria where idCategoria=" + idcategoria;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = categoria.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Micategoria.setIdCategoria(rs.getBigDecimal(1));
                Micategoria.setDescripcion(rs.getString(2));
                Micategoria.setEstado(rs.getString(3));
                Micategoria.setGanancia(rs.getBigDecimal(4));
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
        return Micategoria;
    }

    @Override
    public String toString() {
        return Descripcion + "% " + ganancia;
    }

}
