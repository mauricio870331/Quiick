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
import java.util.Date;

/**
 *
 * @author admin
 */
public class CajaXUser extends Persistencia implements Serializable {

    private Date fechainicio;
    private Date fechaFinal;
    private BigDecimal MontoInicial;
    private BigDecimal MontoVenta;
    private BigDecimal MontoFinal;
    private String estado;
    private int CodCurrent;

    private CajaXUserID objCajaxUserID;

    public CajaXUser() {
        super();
        objCajaxUserID = new CajaXUserID();
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public BigDecimal getMontoInicial() {
        return MontoInicial;
    }

    public void setMontoInicial(BigDecimal MontoInicial) {
        this.MontoInicial = MontoInicial;
    }

    public BigDecimal getMontoVenta() {
        return MontoVenta;
    }

    public void setMontoVenta(BigDecimal MontoVenta) {
        this.MontoVenta = MontoVenta;
    }

    public BigDecimal getMontoFinal() {
        return MontoFinal;
    }

    public void setMontoFinal(BigDecimal MontoFinal) {
        this.MontoFinal = MontoFinal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CajaXUserID getObjCajaxUserID() {
        return objCajaxUserID;
    }

    public void setObjCajaxUserID(CajaXUserID objCajaxUserID) {
        this.objCajaxUserID = objCajaxUserID;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into CajaXuser (idUsuario,usuario,idsede,idempresa,idpersona,fechainicio,fechafinal,montoinicial,montoventa,montofinal,estado) values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setBigDecimal(1, objCajaxUserID.getIdUsuario());
            preparedStatement.setString(2, objCajaxUserID.getUsuario());
            preparedStatement.setBigDecimal(3, objCajaxUserID.getIdSede());
            preparedStatement.setBigDecimal(4, objCajaxUserID.getIdempresa());
            preparedStatement.setBigDecimal(5, objCajaxUserID.getIdPersona());
            preparedStatement.setDate(6, new java.sql.Date(fechainicio.getTime()));
            preparedStatement.setDate(7, new java.sql.Date(fechaFinal.getTime()));
            preparedStatement.setBigDecimal(8, MontoInicial);
            preparedStatement.setBigDecimal(9, MontoVenta);
            preparedStatement.setBigDecimal(10, MontoFinal);
            preparedStatement.setString(11, estado);

            transaccion = CajaXUser.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update CajaXUser set "
                + " fechainicio=?,fechafinal=?,montoinicial=?,montoventa=?,montofinal=?,estado=? where"
                + " idCaja=? and idUsuario=? and usuario=? and idsede=? and idempresa=? and idpersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setDate(1, (java.sql.Date) fechainicio);
            preparedStatement.setDate(2, (java.sql.Date) fechaFinal);
            preparedStatement.setBigDecimal(3, MontoInicial);
            preparedStatement.setBigDecimal(4, MontoVenta);
            preparedStatement.setBigDecimal(5, MontoFinal);
            preparedStatement.setString(6, estado);
            preparedStatement.setBigDecimal(7, objCajaxUserID.getIdcaja());
            preparedStatement.setBigDecimal(8, objCajaxUserID.getIdUsuario());
            preparedStatement.setString(9, objCajaxUserID.getUsuario());
            preparedStatement.setBigDecimal(10, objCajaxUserID.getIdSede());
            preparedStatement.setBigDecimal(11, objCajaxUserID.getIdempresa());
            preparedStatement.setBigDecimal(12, objCajaxUserID.getIdPersona());

            transaccion = CajaXUser.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from CajaXUser where "
                + "idCaja=? and idUsuario=? and usuario=? and idsede=? and idempresa=? and idpersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setBigDecimal(1, objCajaxUserID.getIdcaja());
            preparedStatement.setBigDecimal(2, objCajaxUserID.getIdUsuario());
            preparedStatement.setString(3, objCajaxUserID.getUsuario());
            preparedStatement.setBigDecimal(4, objCajaxUserID.getIdSede());
            preparedStatement.setBigDecimal(5, objCajaxUserID.getIdempresa());
            preparedStatement.setBigDecimal(6, objCajaxUserID.getIdPersona());

            transaccion = CajaXUser.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<CajaXUser> List = new ArrayList();
        String prepareQuery = "select * from CajaXUser";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = CajaXUser.super.getConecion().query(prepareQuery);
            CajaXUser tabla = new CajaXUser();
            CajaXUserID tablaId = new CajaXUserID();
            while (rs.next()) {

                tablaId.setIdcaja(rs.getBigDecimal(1));
                tablaId.setIdUsuario(rs.getBigDecimal(2));
                tablaId.setUsuario(rs.getString(3));
                tablaId.setIdSede(rs.getBigDecimal(4));
                tablaId.setIdempresa(rs.getBigDecimal(5));
                tablaId.setIdPersona(rs.getBigDecimal(6));

                tabla.setFechainicio(rs.getDate(7));
                tabla.setFechaFinal(rs.getDate(8));
                tabla.setMontoInicial(rs.getBigDecimal(9));
                tabla.setMontoVenta(rs.getBigDecimal(10));
                tabla.setMontoFinal(rs.getBigDecimal(11));
                tabla.setEstado(rs.getString(12));

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

    public java.util.List ListMiCajaAbierta() {
        ArrayList<CajaXUser> List = new ArrayList();
        String prepareQuery = "select * from CajaXUser where estado='A' and idUsuario=" + MiCaja().getObjCajaxUserID().getIdUsuario();
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = CajaXUser.super.getConecion().query(prepareQuery);
            CajaXUser tabla = new CajaXUser();
            CajaXUserID tablaId = new CajaXUserID();
            while (rs.next()) {

                tablaId.setIdcaja(rs.getBigDecimal(1));
                tablaId.setIdUsuario(rs.getBigDecimal(2));
                tablaId.setUsuario(rs.getString(3));
                tablaId.setIdSede(rs.getBigDecimal(4));
                tablaId.setIdempresa(rs.getBigDecimal(5));
                tablaId.setIdPersona(rs.getBigDecimal(6));

                tabla.setFechainicio(rs.getDate(7));
                tabla.setFechaFinal(rs.getDate(8));
                tabla.setMontoInicial(rs.getBigDecimal(9));
                tabla.setMontoVenta(rs.getBigDecimal(10));
                tabla.setMontoFinal(rs.getBigDecimal(11));
                tabla.setEstado(rs.getString(12));

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

    public CajaXUser MiCaja() {
        CajaXUser tabla = new CajaXUser();
        System.out.println("-- " + toString());
        String prepareQuery = "select * from cajaxuser where estado='A' and idUsuario=" + objCajaxUserID.getIdUsuario();
        try {
            System.out.println("- " + prepareQuery);
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = CajaXUser.super.getConecion().query(prepareQuery);
            CajaXUserID tablaId = new CajaXUserID();
            if (rs.next()) {
                System.out.println("entro aqui-------------------");
                tablaId.setIdcaja(rs.getBigDecimal(1));
                tablaId.setIdUsuario(rs.getBigDecimal(2));
                tablaId.setUsuario(rs.getString(3));
                tablaId.setIdSede(rs.getBigDecimal(4));
                tablaId.setIdempresa(rs.getBigDecimal(5));
                tablaId.setIdPersona(rs.getBigDecimal(6));

                tabla.setFechainicio(rs.getDate(7));
                tabla.setFechaFinal(rs.getDate(8));
                tabla.setMontoInicial(rs.getBigDecimal(9));
                tabla.setMontoVenta(rs.getBigDecimal(10));
                tabla.setMontoFinal(rs.getBigDecimal(11));
                tabla.setEstado(rs.getString(12));
                tabla.setObjCajaxUserID(tablaId);

            } else {
                System.out.println("nuloooooo");
                tabla = null;
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

    public int CierreCaja() {
        int transaccion = -1; 
        String PrepareUpdate = "update CajaXUser set estado=?, "
                + "MontoVenta=(Select  case when sum(ValorTotal) is null then 0 else sum(ValorTotal) end as valorTotal"
                + " from pagoservice where idCaja = "+objCajaxUserID.getIdcaja()+"), "
                + "Montofinal=(MontoInicial+MontoVenta) where"
                + " idCaja=? and idUsuario=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);            
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, estado);
            preparedStatement.setBigDecimal(2, objCajaxUserID.getIdcaja());
            preparedStatement.setBigDecimal(3, objCajaxUserID.getIdUsuario());
            transaccion = CajaXUser.this.getConecion().transaccion(preparedStatement);
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

    public int CierreCajasAuto() {
        int transaccion = -1;
        String sql = "{call cerrarCajasAuto()}";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareCall(sql);
            transaccion = CajaXUser.this.getConecion().transaccion(preparedStatement);
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
        System.out.println("transaccion = " + transaccion);
        return transaccion;
    }

//    public void CerrarCajas() {
//        int transaccion = -1;
//        String PrepareUpdate = "update CajaXUser set estado=?, where"
//                + " FechaInicio<CURDATE()";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            this.getConecion().con.setAutoCommit(false);
//            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
//            preparedStatement.setString(1, estado);
//            preparedStatement.setBigDecimal(2, objCajaxUserID.getIdcaja());
//            preparedStatement.setBigDecimal(3, objCajaxUserID.getIdUsuario());
//
//            transaccion = CajaXUser.this.getConecion().transaccion(preparedStatement);
//        } catch (SQLException ex) {
//            System.out.println("Error SQL : " + ex.toString());
//        } finally {
//            try {
//                this.getConecion().getconecion().setAutoCommit(true);
//                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
//        return transaccion;
//    }
    public int getCodCurrent() {
        return CodCurrent;
    }

    public void setCodCurrent(int CodCurrent) {
        this.CodCurrent = CodCurrent;
    }

}
