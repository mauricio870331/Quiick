package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class compradetalle extends Persistencia implements Serializable {

    public String nombreProducto;
    public BigDecimal costoproducto;
    public BigDecimal cod_iva;
    public BigDecimal cantidad;
    public BigDecimal stock;
    public BigDecimal valorVenta;
    public BigDecimal cod_unidad;
    public BigDecimal cod_producto;
    public BigDecimal idDetalle;
    private compradetalleID compradetalleID;
    private compra_producto objcompra_producto;
    private iva objm_iva;

    List<compra_producto> List_compra_producto = new ArrayList();
    List<compradetalle> ListDetalles = new ArrayList();

    public compradetalle() {
        super();
        compradetalleID = new compradetalleID();
        objcompra_producto = new compra_producto();
        objm_iva = new iva();

    }

    public compradetalle(BigDecimal cod_producto, BigDecimal costoproducto, BigDecimal cod_iva, BigDecimal cantidad) {
        this.cod_producto = cod_producto;
        this.costoproducto = costoproducto;
        this.cod_iva = cod_iva;
        this.cantidad = cantidad;
    }

    public BigDecimal getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(BigDecimal cod_producto) {
        this.cod_producto = cod_producto;
    }

    public BigDecimal getCostoproducto() {
        return costoproducto;
    }

    public void setCostoproducto(BigDecimal costoproducto) {
        this.costoproducto = costoproducto;
    }

    public BigDecimal getCod_iva() {
        return cod_iva;
    }

    public void setCod_iva(BigDecimal cod_iva) {
        this.cod_iva = cod_iva;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public compradetalleID getCompradetalleID() {
        if (compradetalleID == null) {
            compradetalleID = new compradetalleID();
        }
        return compradetalleID;
    }

    public void setCompradetalleID(compradetalleID compradetalleID) {
        this.compradetalleID = compradetalleID;
    }

    public compra_producto getObjcompra_producto() {
        if (objcompra_producto == null) {
            objcompra_producto = new compra_producto();
        }
        return objcompra_producto;
    }

    public void setObjcompra_producto(compra_producto objcompra_producto) {
        this.objcompra_producto = objcompra_producto;
    }

    public List<compra_producto> getListCompra_producto() {
        List_compra_producto = objcompra_producto.List();
        return List_compra_producto;
    }

    public void setListCompra_producto(List<compra_producto> Listcompra_producto) {
        this.List_compra_producto = Listcompra_producto;
    }

    public iva getObjm_iva() {
        if (objm_iva == null) {
            objm_iva = new iva();
        }
        return objm_iva;
    }

    public void setObjm_iva(iva objm_iva) {
        this.objm_iva = objm_iva;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(BigDecimal valorVenta) {
        this.valorVenta = valorVenta;
    }

    public BigDecimal getCod_unidad() {
        return cod_unidad;
    }

    public void setCod_unidad(BigDecimal cod_unidad) {
        this.cod_unidad = cod_unidad;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into compra_detalle (idCompra,"
                + "serie_producto,nombre_producto,costo,idiva,cantidad,stock,valor_venta,cod_unidad,"
                + "cod_producto) values (?,?,?,?,?,?,?,?,?,?)";
        if (objcompra_producto.create() > 0) {
            try {
                System.out.println(" Productos ... " + ListDetalles.size());
                this.getConecion().con = this.getConecion().dataSource.getConnection();
                this.getConecion().con.setAutoCommit(false);
                PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
                for (compradetalle object : ListDetalles) {
                    preparedStatement.setBigDecimal(1, new BigDecimal(objcompra_producto.getCodcurrval()));
                    preparedStatement.setString(2, object.getCompradetalleID().getSerieProducto());
                    preparedStatement.setString(3, object.getNombreProducto());
                    preparedStatement.setBigDecimal(4, object.getCostoproducto());
                    preparedStatement.setBigDecimal(5, object.getCod_iva());
                    preparedStatement.setBigDecimal(6, object.getCantidad());
                    preparedStatement.setBigDecimal(7, object.getStock());
                    preparedStatement.setBigDecimal(8, object.getValorVenta());
                    preparedStatement.setBigDecimal(9, object.getCod_unidad());
                    preparedStatement.setBigDecimal(10, object.getCod_producto());
                    transaccion = compradetalle.this.getConecion().transaccion(preparedStatement);
                }

                this.getConecion().cstmt = this.getConecion().con.prepareCall("{call Compra_ajusteProductos(?)}");

                this.getConecion().cstmt.setInt(1, objcompra_producto.getCodcurrval());

                int rep = compradetalle.super.getConecion().cstmt.executeUpdate();

                System.out.println("Respuesta Procedure  : " + rep);

            } catch (SQLException ex) {
                System.out.println("Error SQL : " + ex.toString());
            } finally {
                try {
                    //this.getConecion().getconecion().commit();
                    this.getConecion().getconecion().setAutoCommit(true);
                    this.getConecion().con.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return transaccion;
    }

    @Override
    public int edit() {
        int transaccion = -1;
//        String prepareEdit = "update compradetalle set cod_producto=?,costoproducto=?,cod_iva=?,cantidad=?,usuariomod=?,fechamod=?,operacion=? where cod_compradetalle=? and cod_compraproducto=? and cod_proveedor=? and id_persona=? and cod_usuario=? and usuario=? and id_empresahist=? and cod_regimen=? and cod_empresa=? ";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            this.getConecion().con.setAutoCommit(false);
//            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
//            preparedStatement.setBigDecimal(1, cod_producto);
//            preparedStatement.setBigDecimal(2, costoproducto);
//            preparedStatement.setBigDecimal(3, cod_iva);
//            preparedStatement.setBigDecimal(4, cantidad);
//            preparedStatement.setString(5, usuariomod);
//            preparedStatement.setDate(6, new java.sql.Date(fechamod.getTime()));
//            preparedStatement.setString(7, operacion);
//
//            preparedStatement.setBigDecimal(8, compradetalleID.getCod_compradetalle());
//            preparedStatement.setBigDecimal(9, compradetalleID.getCod_compraproducto());
//            preparedStatement.setBigDecimal(10, compradetalleID.getCod_proveedor());
//            preparedStatement.setBigDecimal(11, compradetalleID.getId_persona());
//            preparedStatement.setBigDecimal(12, compradetalleID.getCod_usuario());
//            preparedStatement.setString(13, compradetalleID.getUsuario());
//            preparedStatement.setBigDecimal(14, compradetalleID.getId_empresahist());
//            preparedStatement.setBigDecimal(15, compradetalleID.getCod_regimen());
//            preparedStatement.setBigDecimal(16, compradetalleID.getCod_empresa());
//
//            transaccion = compradetalle.this.getConecion().transaccion(preparedStatement);
//        } catch (SQLException ex) {
//            System.out.println("Error SQL : " + ex.toString());
//        } finally {
//            try {
//                //this.getConecion().getconecion().commit();
//                this.getConecion().getconecion().setAutoCommit(true);
//                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
        return transaccion;
    }

    @Override
    public int remove() {
        int transaccion = -1;
//        String prepareDelete = "delete from  compradetalle where cod_compradetalle=? and cod_compraproducto=? and cod_proveedor=? and id_persona=? and cod_usuario=? and usuario=? and id_empresahist=? and cod_regimen=? and cod_empresa=?";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            this.getConecion().con.setAutoCommit(false);
//            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
//            preparedStatement.setBigDecimal(1, compradetalleID.getCod_compradetalle());
//            preparedStatement.setBigDecimal(2, compradetalleID.getCod_compraproducto());
//            preparedStatement.setBigDecimal(3, compradetalleID.getCod_proveedor());
//            preparedStatement.setBigDecimal(4, compradetalleID.getId_persona());
//            preparedStatement.setBigDecimal(5, compradetalleID.getCod_usuario());
//            preparedStatement.setString(6, compradetalleID.getUsuario());
//            preparedStatement.setBigDecimal(7, compradetalleID.getId_empresahist());
//            preparedStatement.setBigDecimal(8, compradetalleID.getCod_regimen());
//            preparedStatement.setBigDecimal(9, compradetalleID.getCod_empresa());
//            transaccion = compradetalle.this.getConecion().transaccion(preparedStatement);
//        } catch (SQLException ex) {
//            System.out.println("Error SQL : " + ex.toString());
//        } finally {
//            try {
//                //this.getConecion().getconecion().commit();
//                this.getConecion().getconecion().setAutoCommit(true);
//                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
        return transaccion;
    }

    @Override
    public java.util.List<compradetalle> List() {
        ArrayList<compradetalle> listcompradetalle = new ArrayList();
//        String prepareQuery = "select cod_compradetalle,cod_compraproducto,cod_proveedor,id_persona,cod_usuario,usuario,id_empresahist,cod_regimen,cod_empresa,cod_producto,costoproducto,cod_iva,cantidad,usuariomod,fechamod,operacion from compradetalle";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            ResultSet rs = compradetalle.super.getConecion().query(prepareQuery);
//            compradetalle tabla = new compradetalle();
//            compradetalleID tablaID = new compradetalleID();
//            while (rs.next()) {
//                tablaID.setCod_compradetalle(rs.getBigDecimal(1));
//                tablaID.setCod_compraproducto(rs.getBigDecimal(2));
//                tablaID.setCod_proveedor(rs.getBigDecimal(3));
//                tablaID.setId_persona(rs.getBigDecimal(4));
//                tablaID.setCod_usuario(rs.getBigDecimal(5));
//                tablaID.setUsuario(rs.getString(6));
//                tablaID.setId_empresahist(rs.getBigDecimal(7));
//                tablaID.setCod_regimen(rs.getBigDecimal(8));
//                tablaID.setCod_empresa(rs.getBigDecimal(9));
//
//                tabla.setCod_producto(rs.getBigDecimal(10));
//                tabla.setCostoproducto(rs.getBigDecimal(11));
//                tabla.setCod_iva(rs.getBigDecimal(12));
//                tabla.setCantidad(rs.getBigDecimal(13));
//                tabla.setUsuariomod(rs.getString(14));
//                tabla.setFechamod(rs.getDate(15));
//                tabla.setOperacion(rs.getString(16));
//                tabla.setCompradetalleID(tablaID);
//
//                listcompradetalle.add(tabla);
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error Consulta : " + ex.toString());
//        } finally {
//            try {
//                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
        return listcompradetalle;
    }

    public List<compradetalle> getListDetalles() {
        return ListDetalles;
    }

    public void setListDetalles(List<compradetalle> ListDetalles) {
        this.ListDetalles = ListDetalles;
    }

}
