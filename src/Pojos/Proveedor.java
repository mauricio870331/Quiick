/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import ds.desktop.notify.DesktopNotify;
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
public class Proveedor extends Persistencia implements Serializable {

    private BigDecimal idProveedor;
    private persona persona;
    private EmpresaProveedor empresa;
    private String estado;

    public Proveedor() {
        super();
        persona = new persona();
        empresa = new EmpresaProveedor();
    }

    public BigDecimal getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(BigDecimal idProveedor) {
        this.idProveedor = idProveedor;
    }

    public persona getPersona() {
        if (persona == null) {
            persona = new persona();
        }
        return persona;
    }

    public void setPersona(persona persona) {
        this.persona = persona;
    }

    public EmpresaProveedor getEmpresa() {
        if (empresa == null) {
            empresa = new EmpresaProveedor();
        }
        return empresa;
    }

    public void setEmpresa(EmpresaProveedor empresa) {
        this.empresa = empresa;
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
        if (persona.BuscarXDocumentoTrue(persona.getDocumento()) == false) {
            if (persona.create() > 0) {
                System.out.println("ya creo la persona " + persona.getCurrent());
                String prepareInsert = "insert into Proveedor (idempresaproveedor, estado, idPersona) values (?,?,?)";
                try {
                    this.getConecion().con = this.getConecion().dataSource.getConnection();
                    this.getConecion().con.setAutoCommit(false);
                    PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
                    preparedStatement.setBigDecimal(1, empresa.getIdEmpresaProveedor());
                    preparedStatement.setString(2, estado);
                    preparedStatement.setInt(3, persona.getCurrent());

                    transaccion = Proveedor.this.getConecion().transaccion(preparedStatement);
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
            } else {
                System.out.println("Error creando persona");
            }
        } else {
            DesktopNotify.showDesktopMessage("Aviso..!", "Ya existe la cedula registrada en el sistema", DesktopNotify.INFORMATION, 8000L);
        }
        return transaccion;
    }

    @Override
    public int edit() {
        int transaccion = -1;
        String PrepareUpdate = "update Proveedor set idempresaproveedor=?, estado=?, idPersona=? where idProveedor=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setBigDecimal(1, empresa.getIdEmpresaProveedor());
            preparedStatement.setString(2, estado);
            preparedStatement.setInt(3, persona.getIdPersona());
            preparedStatement.setBigDecimal(4, idProveedor);

            transaccion = Proveedor.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from Proveedor where idProveedor=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
//            preparedStatement.setInt(1, idProveedor);

            transaccion = Proveedor.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<Proveedor> List = new ArrayList();
        String prepareQuery = "SELECT idProveedor,C.idempresaproveedor,C.nombreEmpresa,C.nit,B.idPersona,B.documento,B.nombre,B.apellidos,B.telefono, B.correo , B.direccion , B.sexo FROM appgym.proveedor A , appgym.persona B , appgym.empresaproveedor C\n"
                + "where A.idPersona=B.idPersona and A.idempresaproveedor=C.idempresaproveedor and A.estado='A'  limit 100";
        try {
            System.out.println("" + prepareQuery);
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Proveedor.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Proveedor tabla = new Proveedor();
                EmpresaProveedor empresa = new EmpresaProveedor();
                persona persona = new persona();

                tabla.setIdProveedor(rs.getBigDecimal(1));

                empresa.setIdEmpresaProveedor(rs.getBigDecimal(2));
                empresa.setNombreEmpresa(rs.getString(3));
                empresa.setNit(rs.getString(4));

                persona.setIdPersona(rs.getInt(5));
                persona.setDocumento(rs.getString(6));
                persona.setNombre(rs.getString(7));
                persona.setApellido(rs.getString(8));
                persona.setNombreCompleto(persona.getNombre() + " " + persona.getApellido());
                persona.setTelefono(rs.getString(9));
                persona.setCorreo(rs.getString(10));
                persona.setDireccion(rs.getString(11));
                persona.setSexo(rs.getString(12));

                tabla.setEmpresa(empresa);
                tabla.setPersona(persona);

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

    public Proveedor BuscarProveedor(int idProveedor) {
        Proveedor tabla = new Proveedor();
        String prepareQuery = "SELECT idProveedor,C.idempresaproveedor,C.nombreEmpresa,C.nit,B.idPersona,B.documento,B.nombre,B.apellidos,B.telefono, B.correo , B.direccion , B.sexo FROM appgym.proveedor A , appgym.persona B , appgym.empresaproveedor C\n"
                + "where A.idPersona=B.idPersona and A.idempresaproveedor=C.idempresaproveedor "
                + "and A.estado='A' and idProveedor=" + idProveedor;
        try {
            System.out.println("" + prepareQuery);
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Proveedor.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                EmpresaProveedor empresa = new EmpresaProveedor();
                persona persona = new persona();

                tabla.setIdProveedor(rs.getBigDecimal(1));

                empresa.setIdEmpresaProveedor(rs.getBigDecimal(2));
                empresa.setNombreEmpresa(rs.getString(3));
                empresa.setNit(rs.getString(4));

                persona.setIdPersona(rs.getInt(5));
                persona.setDocumento(rs.getString(6));
                persona.setNombre(rs.getString(7));
                persona.setApellido(rs.getString(8));
                persona.setNombreCompleto(persona.getNombre() + " " + persona.getApellido());
                persona.setTelefono(rs.getString(9));
                persona.setCorreo(rs.getString(10));
                persona.setDireccion(rs.getString(11));
                persona.setSexo(rs.getString(12));

                tabla.setEmpresa(empresa);
                tabla.setPersona(persona);

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

}
