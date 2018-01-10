/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import Coneccion.poolConecciones;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author admin
 */
public class TipoPago extends Persistencia implements Serializable {

    private BigDecimal idtipoPago;
    private String Descripcion;

    public TipoPago() {
        super();
    }

    public BasicDataSource getDataSource() {
        return DataSource;
    }

    public void setDataSource(BasicDataSource DataSource) {
        this.DataSource = DataSource;
    }

    public poolConecciones getPool() {
        return pool;
    }

    public void setPool(poolConecciones pool) {
        this.pool = pool;
    }

    public BigDecimal getIdtipoPago() {
        return idtipoPago;
    }

    public void setIdtipoPago(BigDecimal idtipoPago) {
        this.idtipoPago = idtipoPago;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into TipoPago (Descripcion) values (?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, Descripcion);

            transaccion = TipoPago.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update TipoPago set Descripcion=? where idTipoPago=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, Descripcion);
            preparedStatement.setBigDecimal(2, idtipoPago);

            transaccion = TipoPago.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from TipoPago where idTipoPago=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setBigDecimal(1, idtipoPago);

            transaccion = TipoPago.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<TipoPago> List = new ArrayList();
        String prepareQuery = "select * from TipoPago order by 1";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = TipoPago.super.getConecion().query(prepareQuery);

            while (rs.next()) {
                TipoPago tabla = new TipoPago();
                tabla.setIdtipoPago(rs.getBigDecimal(1));
                tabla.setDescripcion(rs.getString(2));
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
        return Descripcion;
    }

}
