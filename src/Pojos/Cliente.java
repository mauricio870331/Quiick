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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class Cliente extends Persistencia implements Serializable {

    private BigDecimal codCliente;
    private String tipoCliente;

    private persona p;

    public Cliente() {
        super();
        p = new persona();
    }

    public BigDecimal getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(BigDecimal codCliente) {
        this.codCliente = codCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public persona getP() {
        if (p == null) {
            p = new persona();
        }
        return p;
    }

    public void setP(persona p) {
        this.p = p;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into Cliente (idPersona,TipoCliente) "
                + "values (?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, p.getIdPersona());
            preparedStatement.setString(2, tipoCliente);

            transaccion = Cliente.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update Cliente set idPersona=? where"
                + " TipoCliente=? where IdCliente=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setInt(1, p.getIdPersona());
            preparedStatement.setString(2, tipoCliente);
            preparedStatement.setBigDecimal(3, codCliente);

            transaccion = Cliente.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from Cliente where IdCliente=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setBigDecimal(1, codCliente);

            transaccion = Cliente.this.getConecion().transaccion(preparedStatement);
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
    public List List() {
        ArrayList<Cliente> List = new ArrayList();
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().cstmt = this.getConecion().con.prepareCall("{call ListClientes}");
            ResultSet rs = Cliente.super.getConecion().cstmt.executeQuery();
            while (rs.next()) {
                Cliente tabla = new Cliente();
                persona p = new persona();
                TipoDocumento tipoDoc = new TipoDocumento();

                tabla.setCodCliente(rs.getBigDecimal(1));
                tabla.setTipoCliente(rs.getString(2));

                p.setIdPersona(rs.getInt(3));

                tipoDoc.setIdTipoDocumento(rs.getBigDecimal(4));
                tipoDoc.setSiglas(rs.getString(5));

                p.setDocumento(rs.getString(6));
                p.setNombre(rs.getString(7));
                p.setApellido(rs.getString(8));
                p.setNombreCompleto(rs.getString(9));
                p.setDireccion(rs.getString(10));
                p.setTelefono(rs.getString(11));
                p.setSexo(rs.getString(12));

                p.setTipodocumento(tipoDoc);
                tabla.setP(p);

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

    public List BuscarXCliente(String Busqueda) {
        ArrayList<Cliente> List = new ArrayList();
        String prepareQuery = "SELECT a.idcliente,a.tipocliente,b.idPersona,c.idTipoDocumento,c.siglas,b.Documento,b.Nombre,"
                + "b.Apellidos,b.NombreCompleto,b.direccion,b.Telefono,b.Sexo FROM `cliente` a , persona b ,"
                + " tipodocumento c WHERE a.idpersona=b.idPersona and b.idTipoDocumento=c.idTipoDocumento "
                + "and b.Estado='A' and (b.Documento like '%" + Busqueda + "%' or b.NombreCompleto like '%" + Busqueda + "%') limit 100";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Cliente.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Cliente tabla = new Cliente();
                persona p = new persona();
                TipoDocumento tipoDoc = new TipoDocumento();

                tabla.setCodCliente(rs.getBigDecimal(1));
                tabla.setTipoCliente(rs.getString(2));

                p.setIdPersona(rs.getInt(3));

                tipoDoc.setIdTipoDocumento(rs.getBigDecimal(4));
                tipoDoc.setSiglas(rs.getString(5));

                p.setDocumento(rs.getString(6));
                p.setNombre(rs.getString(7));
                p.setApellido(rs.getString(8));
                p.setNombreCompleto(rs.getString(9));
                p.setDireccion(rs.getString(10));
                p.setTelefono(rs.getString(11));
                p.setSexo(rs.getString(12));

                p.setTipodocumento(tipoDoc);
                tabla.setP(p);

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
