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
import java.util.ArrayList;

/**
 *
 * @author Juan
 */
public class ventaproducto extends Persistencia implements Serializable {

    private ventaproductoID ventaproID;
    private venta venta;
    private BigDecimal cantidadVenta;
    private BigDecimal valoriva;
    private BigDecimal ValorTotal;
    private BigDecimal valorproducto;
    private BigDecimal cod_producto;
    private BigDecimal idCategoria;

    ArrayList<ventaproducto> listProductos = new ArrayList();

    public ventaproducto() {
        super();
        ventaproID = new ventaproductoID();
        venta = new venta();
    }

    public ventaproductoID getVentaproID() {
        return ventaproID;
    }

    public void setVentaproID(ventaproductoID ventaproID) {
        this.ventaproID = ventaproID;
    }

    public venta getVenta() {
        return venta;
    }

    public void setVenta(venta venta) {
        this.venta = venta;
    }

    public BigDecimal getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(BigDecimal cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public BigDecimal getValoriva() {
        return valoriva;
    }

    public void setValoriva(BigDecimal valoriva) {
        this.valoriva = valoriva;
    }

    public BigDecimal getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(BigDecimal ValorTotal) {
        this.ValorTotal = ValorTotal;
    }

    public BigDecimal getValorproducto() {
        return valorproducto;
    }

    public void setValorproducto(BigDecimal valorproducto) {
        this.valorproducto = valorproducto;
    }

    public BigDecimal getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(BigDecimal cod_producto) {
        this.cod_producto = cod_producto;
    }

    public BigDecimal getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(BigDecimal idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int create() {
        int transaccion = -1;
        
          String prepareInsertVenta = "insert into venta (cod_factura, fecha_venta, idTipoVenta, idPersonaCliente,"
                    + " valorNeto, valoriva, PorcentajeDescuento, valorDescuento, total_venta, Devuelta, "
                    + "idTipoPago, Paga, idCaja, idUsuario, usuario, idSede, idempresa, idPersona)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        String prepareInsert = "insert into ventaproducto (cod_factura, cantidadVenta, valoriva, ValorTotal, valorproducto, cod_producto, idCategoria)"
                + " values (?,?,?,?,?,?,?)";
        System.out.println(".. : " + venta.toString());

        try {
                this.getConecion().con = this.getConecion().dataSource.getConnection();
                this.getConecion().con.setAutoCommit(false);
                PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsertVenta);
                preparedStatement.setBigDecimal(1, venta.getVentaid().getCod_factura());
                preparedStatement.setDate(2, (java.sql.Date) venta.getFechaVenta());
                preparedStatement.setBigDecimal(3, venta.getVentaid().getIdTipoVenta());
                preparedStatement.setBigDecimal(4, venta.getIdPersonaCliente());
                preparedStatement.setBigDecimal(5, venta.getValorNeto());
                preparedStatement.setBigDecimal(6, venta.getValoriva());
                preparedStatement.setBigDecimal(7, venta.getPorcentajeDescuento());
                preparedStatement.setBigDecimal(8, venta.getValorDescuento());
                preparedStatement.setBigDecimal(9, venta.getTotal_venta());
                preparedStatement.setBigDecimal(10, venta.getDevuelta());

//                preparedStatement.setBigDecimal(11, idTipoPago);
//                preparedStatement.setBigDecimal(13, ventaid.getIdCaja());
//                preparedStatement.setBigDecimal(14, ventaid.getIdUsuario());
//                preparedStatement.setString(15, ventaid.getUsuario());
//                preparedStatement.setBigDecimal(16, ventaid.getIdSede());
//                preparedStatement.setBigDecimal(17, ventaid.getIdEmpresa());
//                preparedStatement.setBigDecimal(18, ventaid.getIdPersona());
//
//                transaccion = venta.this.getConecion().transaccion(preparedStatement);


//                for (ventaproducto listProducto : listProductos) {
//                    System.out.println(".. : " + listProducto.toString());
//                    PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
//                    preparedStatement.setBigDecimal(1, venta.getCodCurrent());
//                    preparedStatement.setBigDecimal(2, cantidadVenta);
//                    preparedStatement.setBigDecimal(3, valoriva);
//                    preparedStatement.setBigDecimal(4, ValorTotal);
//                    preparedStatement.setBigDecimal(5, valorproducto);
//                    preparedStatement.setBigDecimal(6, cod_producto);
//                    preparedStatement.setBigDecimal(7, idCategoria);
//                    transaccion = ventaproducto.this.getConecion().transaccion(preparedStatement);
//                }
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

    public ArrayList<ventaproducto> getListProductos() {
        System.out.println("getListProductos : " + listProductos.size());
        return listProductos;
    }

    public void setListProductos(ArrayList<ventaproducto> listProductos) {
        this.listProductos = listProductos;
    }

    @Override
    public String toString() {
        return "ventaproducto{" + "ventaproID=" + ventaproID.toString() + " cantidadVenta=" + cantidadVenta + ", valoriva=" + valoriva + ", ValorTotal=" + ValorTotal + ", valorproducto=" + valorproducto + ", cod_producto=" + cod_producto + ", idCategoria=" + idCategoria + ", listProductos=" + listProductos + '}';
    }

}
