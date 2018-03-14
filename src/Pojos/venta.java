/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Juan
 */
public class venta extends Persistencia implements Serializable {

    private ventaID ventaid;
    private Date fechaVenta;
    private BigDecimal idPersonaCliente;
    private BigDecimal valorNeto;
    private BigDecimal valoriva;
    private BigDecimal PorcentajeDescuento;
    private BigDecimal valorDescuento;
    private BigDecimal total_venta;
    private BigDecimal Devuelta;
    private BigDecimal idTipoPago;
    private BigDecimal Paga;

    public venta() {
        super();
        ventaid = new ventaID();
    }

    public ventaID getVentaid() {
        return ventaid;
    }

    public void setVentaid(ventaID ventaid) {
        this.ventaid = ventaid;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getIdPersonaCliente() {
        return idPersonaCliente;
    }

    public void setIdPersonaCliente(BigDecimal idPersonaCliente) {
        this.idPersonaCliente = idPersonaCliente;
    }

    public BigDecimal getValorNeto() {
        return valorNeto;
    }

    public void setValorNeto(BigDecimal valorNeto) {
        this.valorNeto = valorNeto;
    }

    public BigDecimal getValoriva() {
        return valoriva;
    }

    public void setValoriva(BigDecimal valoriva) {
        this.valoriva = valoriva;
    }

    public BigDecimal getPorcentajeDescuento() {
        return PorcentajeDescuento;
    }

    public void setPorcentajeDescuento(BigDecimal PorcentajeDescuento) {
        this.PorcentajeDescuento = PorcentajeDescuento;
    }

    public BigDecimal getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(BigDecimal valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public BigDecimal getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(BigDecimal total_venta) {
        this.total_venta = total_venta;
    }

    public BigDecimal getDevuelta() {
        return Devuelta;
    }

    public void setDevuelta(BigDecimal Devuelta) {
        this.Devuelta = Devuelta;
    }

    public BigDecimal getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(BigDecimal idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public BigDecimal getPaga() {
        return Paga;
    }

    public void setPaga(BigDecimal Paga) {
        this.Paga = Paga;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into venta (cod_factura, fecha_venta, idTipoVenta, idPersonaCliente,"
                + " valorNeto, valoriva, PorcentajeDescuento, valorDescuento, total_venta, Devuelta, "
                + "idTipoPago, Paga, idCaja, idUsuario, usuario, idSede, idempresa, idPersona)"
                + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setBigDecimal(1, ventaid.getCod_factura());
            preparedStatement.setDate(2, (java.sql.Date) fechaVenta);
            preparedStatement.setBigDecimal(3, ventaid.getIdTipoVenta());
            preparedStatement.setBigDecimal(4, idPersonaCliente);
            preparedStatement.setBigDecimal(5, valorNeto);
            preparedStatement.setBigDecimal(6, valoriva);
            preparedStatement.setBigDecimal(7, PorcentajeDescuento);
            preparedStatement.setBigDecimal(8, valorDescuento);
            preparedStatement.setBigDecimal(9, total_venta);
            preparedStatement.setBigDecimal(10, Devuelta);

            preparedStatement.setBigDecimal(11, idTipoPago);
            preparedStatement.setBigDecimal(12, Paga);
            preparedStatement.setBigDecimal(13, ventaid.getIdCaja());
            preparedStatement.setBigDecimal(14, ventaid.getIdUsuario());
            preparedStatement.setString(15, ventaid.getUsuario());
            preparedStatement.setBigDecimal(16, ventaid.getIdSede());
            preparedStatement.setBigDecimal(17, ventaid.getIdEmpresa());
            preparedStatement.setBigDecimal(18, ventaid.getIdPersona());

            transaccion = venta.this.getConecion().transaccion(preparedStatement);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public java.util.List List() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
