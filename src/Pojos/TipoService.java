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
public class TipoService extends Persistencia implements Serializable {

    private BigDecimal idtipoService;
    private String Descripcion;
    private BigDecimal valor;
    private BigDecimal porcentaje;
    private String Estado;
    private int dias;

    public TipoService() {
        super();
    }

    public BigDecimal getIdtipoService() {
        return idtipoService;
    }

    public void setIdtipoService(BigDecimal idtipoService) {
        this.idtipoService = idtipoService;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
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
        String prepareInsert = "insert into TipoService (Descripcion,valor,porcentaje,estado,Cant_Dias) values (?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, Descripcion);
            preparedStatement.setBigDecimal(2, valor);
            preparedStatement.setBigDecimal(3, porcentaje);
            preparedStatement.setString(4, Estado);
            preparedStatement.setInt(5, dias);

            transaccion = TipoService.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update TipoService set Descripcion=?,valor=?,porcentaje=?,estado=? where idTipoService=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, Descripcion);
            preparedStatement.setBigDecimal(2, valor);
            preparedStatement.setBigDecimal(3, porcentaje);
            preparedStatement.setString(4, Estado);
            preparedStatement.setBigDecimal(5, idtipoService);

            transaccion = TipoService.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from TipoService where idTipoService=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setBigDecimal(1, idtipoService);

            transaccion = TipoService.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<TipoService> List = new ArrayList();
        String prepareQuery = "select * from TipoService where Estado='A'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = TipoService.super.getConecion().query(prepareQuery);

            while (rs.next()) {
                TipoService tabla = new TipoService();
                tabla.setIdtipoService(rs.getBigDecimal(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setValor(rs.getBigDecimal(3));
                tabla.setPorcentaje(rs.getBigDecimal(4));
                tabla.setEstado(rs.getString(5));
                tabla.setDias(rs.getInt(6));

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

    public TipoService getTipoByDescripcion(String descripcion) {
        TipoService service = null;
        String prepareQuery = "select * from TipoService where Estado='A' and Descripcion = '" + descripcion + "'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = TipoService.super.getConecion().query(prepareQuery);

            if (rs.next()) {
                service = new TipoService();
                service.setIdtipoService(rs.getBigDecimal(1));
                service.setDescripcion(rs.getString(2));
                service.setValor(rs.getBigDecimal(3));
                service.setPorcentaje(rs.getBigDecimal(4));
                service.setEstado(rs.getString(5));
                service.setDias(rs.getInt(6));

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
        return service;
    }

    @Override
    public String toString() {
        return Descripcion;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

}
