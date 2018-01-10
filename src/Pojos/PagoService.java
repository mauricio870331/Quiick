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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author admin
 */
public class PagoService extends Persistencia implements Serializable {

    private BigDecimal idTipoService;
    private BigDecimal idTipoPago;
    private Date FechaPago;
    private BigDecimal ValorPagado;
    private BigDecimal Devolucion;
    private BigDecimal valorNeto;
    private BigDecimal PorcentajeDescuento;
    private BigDecimal ValorDescuento;
    private BigDecimal ValorTotal;
    private Date fechaInicio;
    private Date fechaFinal;

    private PagoServiceID objPagoServiceID;
    private TipoService objTipoService;

    SimpleDateFormat sa = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat saSegu = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public PagoService() {
        super();
        objPagoServiceID = new PagoServiceID();
        objTipoService = new TipoService();
    }

    public BigDecimal getIdTipoService() {
        return idTipoService;
    }

    public void setIdTipoService(BigDecimal idTipoService) {
        this.idTipoService = idTipoService;
    }

    public BigDecimal getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(BigDecimal idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public Date getFechaPago() {
        return FechaPago;
    }

    public void setFechaPago(Date FechaPago) {
        this.FechaPago = FechaPago;
    }

    public BigDecimal getValorPagado() {
        return ValorPagado;
    }

    public void setValorPagado(BigDecimal ValorPagado) {
        this.ValorPagado = ValorPagado;
    }

    public BigDecimal getDevolucion() {
        return Devolucion;
    }

    public void setDevolucion(BigDecimal Devolucion) {
        this.Devolucion = Devolucion;
    }

    public BigDecimal getValorNeto() {
        return valorNeto;
    }

    public void setValorNeto(BigDecimal valorNeto) {
        this.valorNeto = valorNeto;
    }

    public BigDecimal getPorcentajeDescuento() {
        return PorcentajeDescuento;
    }

    public void setPorcentajeDescuento(BigDecimal PorcentajeDescuento) {
        this.PorcentajeDescuento = PorcentajeDescuento;
    }

    public BigDecimal getValorDescuento() {
        return ValorDescuento;
    }

    public void setValorDescuento(BigDecimal ValorDescuento) {
        this.ValorDescuento = ValorDescuento;
    }

    public BigDecimal getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(BigDecimal ValorTotal) {
        this.ValorTotal = ValorTotal;
    }

    public PagoServiceID getObjPagoServiceID() {
        return objPagoServiceID;
    }

    public void setObjPagoServiceID(PagoServiceID objPagoServiceID) {
        this.objPagoServiceID = objPagoServiceID;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into PagoService (idUsuarioCliente,usuarioCliente,idsedeCliente,"
                + "idempresaCliente,idpersonaCliente,idCaja,idUsuario,usuario,idsede,idempresa,idpersona,"
                + "idTipoService,idTipoPago,FechaPago,ValorPagado,devolucion,ValorNeto,PorcentajeDescuento,"
                + "ValorDescuento,ValorTotal,fechaInicio,fechaFinal) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setBigDecimal(1, objPagoServiceID.getIdUsuarioCliente());
            preparedStatement.setString(2, objPagoServiceID.getUsuarioCliente());
            preparedStatement.setBigDecimal(3, objPagoServiceID.getIdSedeCliente());
            preparedStatement.setBigDecimal(4, objPagoServiceID.getIdempresaCliente());
            preparedStatement.setBigDecimal(5, objPagoServiceID.getIdPersonaCliente());
            preparedStatement.setBigDecimal(6, objPagoServiceID.getIdcaja());
            preparedStatement.setBigDecimal(7, objPagoServiceID.getIdUsuario());
            preparedStatement.setString(8, objPagoServiceID.getUsuario());
            preparedStatement.setBigDecimal(9, objPagoServiceID.getIdSede());
            preparedStatement.setBigDecimal(10, objPagoServiceID.getIdempresa());
            preparedStatement.setBigDecimal(11, objPagoServiceID.getIdPersona());

            preparedStatement.setBigDecimal(12, idTipoService);
            preparedStatement.setBigDecimal(13, idTipoPago);
            preparedStatement.setDate(14, new java.sql.Date(FechaPago.getTime()));
            preparedStatement.setBigDecimal(15, ValorPagado);
            preparedStatement.setBigDecimal(16, Devolucion);
            preparedStatement.setBigDecimal(17, valorNeto);
            preparedStatement.setBigDecimal(18, PorcentajeDescuento);
            preparedStatement.setBigDecimal(19, ValorDescuento);
            preparedStatement.setBigDecimal(20, ValorTotal);
            preparedStatement.setDate(21, new java.sql.Date(fechaInicio.getTime()));
            preparedStatement.setDate(22, new java.sql.Date(fechaFinal.getTime()));

            transaccion = PagoService.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update PagoService set idTipoService=?,idTipoPago=?,FechaPago=?,ValorPagado=?,devolucion=?,ValorNeto=?,PorcentajeDescuento=?,"
                + "ValorDescuento=?,ValorTotal=? where idpago=? and  idUsuarioCliente=? and usuarioCliente=? and idsedeCliente=? and "
                + "idempresaCliente=? and idpersonaCliente=? and idCaja=? and idUsuario=? and usuario=? and "
                + "idsede=? and idempresa=? and idpersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);

            preparedStatement.setBigDecimal(1, idTipoService);
            preparedStatement.setBigDecimal(2, idTipoPago);
            preparedStatement.setDate(3, (java.sql.Date) FechaPago);
            preparedStatement.setBigDecimal(4, ValorPagado);
            preparedStatement.setBigDecimal(5, Devolucion);
            preparedStatement.setBigDecimal(6, valorNeto);
            preparedStatement.setBigDecimal(7, PorcentajeDescuento);
            preparedStatement.setBigDecimal(8, ValorDescuento);
            preparedStatement.setBigDecimal(9, ValorTotal);
            preparedStatement.setBigDecimal(10, objPagoServiceID.getIdPago());
            preparedStatement.setBigDecimal(11, objPagoServiceID.getIdUsuarioCliente());
            preparedStatement.setString(12, objPagoServiceID.getUsuarioCliente());
            preparedStatement.setBigDecimal(13, objPagoServiceID.getIdSedeCliente());
            preparedStatement.setBigDecimal(14, objPagoServiceID.getIdempresaCliente());
            preparedStatement.setBigDecimal(15, objPagoServiceID.getIdPersonaCliente());
            preparedStatement.setBigDecimal(16, objPagoServiceID.getIdcaja());
            preparedStatement.setBigDecimal(17, objPagoServiceID.getIdUsuario());
            preparedStatement.setString(18, objPagoServiceID.getUsuario());
            preparedStatement.setBigDecimal(19, objPagoServiceID.getIdSede());
            preparedStatement.setBigDecimal(20, objPagoServiceID.getIdempresa());
            preparedStatement.setBigDecimal(21, objPagoServiceID.getIdPersona());

            transaccion = PagoService.this.getConecion().transaccion(preparedStatement);
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

    public int EditFechas() {
        int transaccion = -1;
        String PrepareUpdate2 = "update PagoService set FechaPago='" + saSegu.format(getFechaPago()) + "', "
                + "fechaInicio='" + sa.format(getFechaInicio()) + "',"
                + "fechaFinal='" + sa.format(getFechaFinal()) + "', idTipoService = " + objTipoService.getIdtipoService() + " "
                + "where idPago = " + objPagoServiceID.getIdPago();

        System.out.println("query = " + PrepareUpdate2);
        String PrepareUpdate = "update PagoService set FechaPago=?, "
                + "fechaInicio=?, fechaFinal=?, idTipoService = ?, idTipoPago = ?,"
                + " ValorNeto = ?, ValorTotal = ?  where idPago = ?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, saSegu.format(getFechaPago()));
            preparedStatement.setString(2, sa.format(getFechaInicio()));
            preparedStatement.setString(3, sa.format(getFechaFinal()));
            preparedStatement.setBigDecimal(4, objTipoService.getIdtipoService());
            preparedStatement.setBigDecimal(5, getIdTipoPago());
            preparedStatement.setBigDecimal(6, getValorNeto());
            preparedStatement.setBigDecimal(7, getValorTotal());
            preparedStatement.setBigDecimal(8, objPagoServiceID.getIdPago());
            transaccion = PagoService.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from PagoService where idpago=? and  idUsuarioCliente=? and usuarioCliente=? and idsedeCliente=? and "
                + "idempresaCliente=? and idpersonaCliente=? and idCaja=? and idUsuario=? and usuario=? and "
                + "idsede=? and idempresa=? and idpersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setBigDecimal(1, objPagoServiceID.getIdPago());
            preparedStatement.setBigDecimal(2, objPagoServiceID.getIdUsuarioCliente());
            preparedStatement.setString(3, objPagoServiceID.getUsuarioCliente());
            preparedStatement.setBigDecimal(4, objPagoServiceID.getIdSedeCliente());
            preparedStatement.setBigDecimal(5, objPagoServiceID.getIdempresaCliente());
            preparedStatement.setBigDecimal(6, objPagoServiceID.getIdPersonaCliente());
            preparedStatement.setBigDecimal(7, objPagoServiceID.getIdcaja());
            preparedStatement.setBigDecimal(8, objPagoServiceID.getIdUsuario());
            preparedStatement.setString(9, objPagoServiceID.getUsuario());
            preparedStatement.setBigDecimal(10, objPagoServiceID.getIdSede());
            preparedStatement.setBigDecimal(11, objPagoServiceID.getIdempresa());
            preparedStatement.setBigDecimal(12, objPagoServiceID.getIdPersona());

            transaccion = PagoService.this.getConecion().transaccion(preparedStatement);
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

    public int removeById() {
        int transaccion = -1;
        String PrepareDelete = "delete from PagoService where idpago=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setBigDecimal(1, objPagoServiceID.getIdPago());
            transaccion = PagoService.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<PagoService> List = new ArrayList();
        String prepareQuery = "select * from PagoService";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = PagoService.super.getConecion().query(prepareQuery);

            while (rs.next()) {
                PagoService tabla = new PagoService();
                PagoServiceID tablaID = new PagoServiceID();

                tablaID.setIdPago(rs.getBigDecimal(1));
                tablaID.setIdUsuarioCliente(rs.getBigDecimal(2));
                tablaID.setUsuarioCliente(rs.getString(3));
                tablaID.setIdSedeCliente(rs.getBigDecimal(4));
                tablaID.setIdempresaCliente(rs.getBigDecimal(5));
                tablaID.setIdPersonaCliente(rs.getBigDecimal(6));
                tablaID.setIdcaja(rs.getBigDecimal(7));
                tablaID.setIdUsuario(rs.getBigDecimal(8));
                tablaID.setUsuario(rs.getString(9));
                tablaID.setIdSede(rs.getBigDecimal(10));
                tablaID.setIdempresa(rs.getBigDecimal(11));
                tablaID.setIdPersona(rs.getBigDecimal(12));

                tabla.setObjPagoServiceID(tablaID);
                tabla.setIdTipoService(rs.getBigDecimal(13));
                tabla.setIdTipoPago(rs.getBigDecimal(14));
                tabla.setFechaPago(rs.getDate(15));
                tabla.setValorPagado(rs.getBigDecimal(16));
                tabla.setDevolucion(rs.getBigDecimal(17));
                tabla.setValorNeto(rs.getBigDecimal(18));
                tabla.setPorcentajeDescuento(rs.getBigDecimal(19));
                tabla.setValorDescuento(rs.getBigDecimal(20));
                tabla.setValorTotal(rs.getBigDecimal(21));

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

    public java.util.List ListPagosXUsers(int condicion) {
        ArrayList<PagoService> List = new ArrayList();
        String prepareQuery = "";
        if (condicion == 1) {
            prepareQuery = "select A.*,B.Descripcion from PagoService A , tiposervice B "
                    + "where A.idTipoService=B.idTipoService and A.idcaja=" + objPagoServiceID.getIdcaja();
        } else if (condicion == 2) {
            prepareQuery = "select A.*,B.Descripcion from PagoService A , tiposervice B where A.idTipoService=B.idTipoService and A.idcaja in (select max(idcaja) from PagoService where idusuario="
                    + objPagoServiceID.getIdUsuario() + ")  ";
        }

        try {
            System.out.println("- " + prepareQuery);
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = PagoService.super.getConecion().query(prepareQuery);

            while (rs.next()) {
                PagoService tabla = new PagoService();
                PagoServiceID tablaID = new PagoServiceID();
                TipoService tipoService = new TipoService();

                tablaID.setIdPago(rs.getBigDecimal(1));
                tablaID.setIdUsuarioCliente(rs.getBigDecimal(2));
                tablaID.setUsuarioCliente(rs.getString(3));
                tablaID.setIdSedeCliente(rs.getBigDecimal(4));
                tablaID.setIdempresaCliente(rs.getBigDecimal(5));
                tablaID.setIdPersonaCliente(rs.getBigDecimal(6));
                tablaID.setIdcaja(rs.getBigDecimal(7));
                tablaID.setIdUsuario(rs.getBigDecimal(8));
                tablaID.setUsuario(rs.getString(9));
                tablaID.setIdSede(rs.getBigDecimal(10));
                tablaID.setIdempresa(rs.getBigDecimal(11));
                tablaID.setIdPersona(rs.getBigDecimal(12));

                tabla.setObjPagoServiceID(tablaID);
                tabla.setIdTipoService(rs.getBigDecimal(13));
                tabla.setIdTipoPago(rs.getBigDecimal(14));
                tabla.setFechaPago(rs.getDate(15));
                tabla.setValorPagado(rs.getBigDecimal(16));
                tabla.setDevolucion(rs.getBigDecimal(17));
                tabla.setValorNeto(rs.getBigDecimal(18));
                tabla.setPorcentajeDescuento(rs.getBigDecimal(19));
                tabla.setValorDescuento(rs.getBigDecimal(20));
                tabla.setValorTotal(rs.getBigDecimal(21));
                tabla.setFechaInicio(rs.getDate(22));
                tabla.setFechaFinal(rs.getDate(23));
                tipoService.setDescripcion(rs.getString(24));
                tabla.setObjTipoService(tipoService);

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

    public java.util.List ListPagosXClientes(int idUsuario, Date desde, Date hasta) {
        ArrayList<PagoService> List = new ArrayList();
        String and = "";
        if (desde != null && hasta != null) {
            and = " and A.FechaPago between '" + sa.format(desde) + " 00:00:00' and '" + sa.format(hasta) + " 23:29:59'";
        }
        String prepareQuery = "select A.*,B.Descripcion from PagoService A , tiposervice B where A.idTipoService=B.idTipoService and \n"
                + "idUsuarioCliente=" + idUsuario + and;
//        System.out.println(" prepareQuery " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = PagoService.super.getConecion().query(prepareQuery);

            while (rs.next()) {
                PagoService tabla = new PagoService();
                PagoServiceID tablaID = new PagoServiceID();
                TipoService tipoService = new TipoService();

                tablaID.setIdPago(rs.getBigDecimal(1));
                tablaID.setIdUsuarioCliente(rs.getBigDecimal(2));
                tablaID.setUsuarioCliente(rs.getString(3));
                tablaID.setIdSedeCliente(rs.getBigDecimal(4));
                tablaID.setIdempresaCliente(rs.getBigDecimal(5));
                tablaID.setIdPersonaCliente(rs.getBigDecimal(6));
                tablaID.setIdcaja(rs.getBigDecimal(7));
                tablaID.setIdUsuario(rs.getBigDecimal(8));
                tablaID.setUsuario(rs.getString(9));
                tablaID.setIdSede(rs.getBigDecimal(10));
                tablaID.setIdempresa(rs.getBigDecimal(11));
                tablaID.setIdPersona(rs.getBigDecimal(12));

                tabla.setObjPagoServiceID(tablaID);
                tabla.setIdTipoService(rs.getBigDecimal(13));
                tabla.setIdTipoPago(rs.getBigDecimal(14));
                tabla.setFechaPago(rs.getDate(15));
                tabla.setValorPagado(rs.getBigDecimal(16));
                tabla.setDevolucion(rs.getBigDecimal(17));
                tabla.setValorNeto(rs.getBigDecimal(18));
                tabla.setPorcentajeDescuento(rs.getBigDecimal(19));
                tabla.setValorDescuento(rs.getBigDecimal(20));
                tabla.setValorTotal(rs.getBigDecimal(21));
                tabla.setFechaInicio(rs.getDate(22));
                tabla.setFechaFinal(rs.getDate(23));
                tipoService.setDescripcion(rs.getString(24));
                tabla.setObjTipoService(tipoService);

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

    public boolean ValidacionPagos(int idUsuario, String fecIni, String fecFin) {
        boolean r = false;
        int valor = 0;
        ArrayList<PagoService> List = new ArrayList();
        String prepareQuery = "SELECT count(*) FROM pagoservice WHERE idUsuarioCliente=" + idUsuario
                + " and (fechaInicio between '" + fecIni + "' and '" + fecFin + "' OR fechaFinal between '" + fecIni
                + "' and '" + fecFin + "') and idPersonaCliente not in (select idPersona from persona where documento='1')";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = PagoService.super.getConecion().query(prepareQuery);
            System.out.println(": " + prepareQuery);
            if (rs.next()) {
                valor = rs.getInt(1);
            }
            if (valor > 0) {
                r = true;
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
        return r;
    }

    public int UltimoId() {
        int id = -1;
        try {
            String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES\n"
                    + "WHERE TABLE_SCHEMA = 'appgym' \n"
                    + "AND TABLE_NAME = 'pagoservice'";
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = PagoService.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                id = rs.getInt(1);
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
        return id;
    }

    public int ValidacionPagosVencidos(int idUsuario) {
        int valor = 0;
        String prepareQuery = "SELECT DATEDIFF(fechaFinal, curdate()) as dias FROM pagoservice WHERE idUsuarioCliente=" + idUsuario
                + " and idPersonaCliente not in (select idPersona from persona where documento='1') order by fechaFinal desc limit 1";
        //            System.out.println(": " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = PagoService.super.getConecion().query(prepareQuery);
            if (rs.next()) {
                valor = rs.getInt(1);
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
//        System.out.println("valor = " + valor);
        return valor;
    }

    public TipoService getObjTipoService() {
        return objTipoService;
    }

    public void setObjTipoService(TipoService objTipoService) {
        this.objTipoService = objTipoService;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

}
