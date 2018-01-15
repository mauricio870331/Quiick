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
public class EmpresaProveedor extends Persistencia implements Serializable {

    private BigDecimal idEmpresaProveedor;
    private String nombreEmpresa;
    private String nit;
    private String direccion;
    private String Telefono;
    private String estado;

    public EmpresaProveedor() {
        super();
    }

    public BigDecimal getIdEmpresaProveedor() {
        return idEmpresaProveedor;
    }

    public void setIdEmpresaProveedor(BigDecimal idEmpresaProveedor) {
        this.idEmpresaProveedor = idEmpresaProveedor;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into EmpresaProveedor (nombreEmpresa,nit,direccion,telefono,estado) values (?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, nombreEmpresa);
            preparedStatement.setString(2, nit);
            preparedStatement.setString(3, direccion);
            preparedStatement.setString(4, Telefono);
            preparedStatement.setString(5, estado);

            transaccion = EmpresaProveedor.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update EmpresaProveedor set nombreEmpresa=?,nit=?,direccion=?,telefono=?,estado=? where idEmpresaProveedor=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, nombreEmpresa);
            preparedStatement.setString(2, nit);
            preparedStatement.setString(3, direccion);
            preparedStatement.setString(4, Telefono);
            preparedStatement.setString(5, estado);
            preparedStatement.setBigDecimal(6, idEmpresaProveedor);

            transaccion = EmpresaProveedor.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from EmpresaProveedor where idEmpresaProveedor=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
//            preparedStatement.setInt(1, idEmpresaProveedor);

            transaccion = EmpresaProveedor.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<EmpresaProveedor> List = new ArrayList();
        String prepareQuery = "SELECT idempresaproveedor, nombreEmpresa, nit, direccion, telefono, estado FROM appgym.empresaproveedor where estado='A' order by 2";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = EmpresaProveedor.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                EmpresaProveedor tabla = new EmpresaProveedor();
                tabla.setIdEmpresaProveedor(rs.getBigDecimal(1));
                tabla.setNombreEmpresa(rs.getString(2));
                tabla.setNit(rs.getString(3));
                tabla.setDireccion(rs.getString(4));
                tabla.setTelefono(rs.getString(5));
                tabla.setEstado(rs.getString(6));
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

    public EmpresaProveedor BuscarEmpresaXCodigo(int codigo) {  //Busqueda Por codigo o por nombre
        EmpresaProveedor tabla = new EmpresaProveedor();
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().cstmt = this.getConecion().con.prepareCall("{call BuscarEmpresaProveedorXCodigo(?)}");
            this.getConecion().cstmt.setInt(1, codigo);

            ResultSet rs = EmpresaProveedor.super.getConecion().cstmt.executeQuery();
            if (rs.next()) {

                tabla.setIdEmpresaProveedor(rs.getBigDecimal(1));
                tabla.setNombreEmpresa(rs.getString(2));
                tabla.setNit(rs.getString(3));
                tabla.setDireccion(rs.getString(4));
                tabla.setTelefono(rs.getString(5));
                tabla.setEstado(rs.getString(6));

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
        return tabla;
    }

    public int EliminarXEstado() {
        int transaccion = -1;
        String PrepareUpdate = "update EmpresaProveedor set estado=? where idEmpresaProveedor=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, estado);
            preparedStatement.setBigDecimal(2, idEmpresaProveedor);
            transaccion = EmpresaProveedor.this.getConecion().transaccion(preparedStatement);
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
    public String toString() {
        return nombreEmpresa;
    }

    public String ValidacionCampos() {
        String mns = "";
        System.out.println("------- " + "EmpresaProveedor{" + "idEmpresaProveedor=" + idEmpresaProveedor + ", nombreEmpresa=" + nombreEmpresa + ", nit=" + nit + ", direccion=" + direccion + ", Telefono=" + Telefono + ", estado=" + estado + '}');
        if (nombreEmpresa.length() <= 0) {
            mns = "Debe Digitar el Nombre de la empresa";
            return mns;
        } else if (nit.length() <= 0) {
            mns = "Debe Digitar el Nit de la empresa";
            return mns;
        } else if (direccion.length() <= 0) {
            mns = "Debe Digitar la direccion de la empresa";
            return mns;
        } else if (Telefono.length() <= 0) {
            mns = "Debe Digitar el telefono de la empresa";
            return mns;
        }
        return mns;
    }

}
