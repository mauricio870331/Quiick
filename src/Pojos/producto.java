/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanDavid
 */
public class producto extends Persistencia implements Serializable {

    private BigDecimal cod_producto;
    private String serieproducto;
    private String NombreProducto;
    private BigDecimal costo;
    private BigDecimal precio_venta;
    private int cantidad;
    private String porcentajedescuento;
    private BigDecimal valortotal;
    private BigDecimal stock;
    private String estado;
    private boolean Existe;
    private int cantidadBD;
    private int codCurrent;
    private int CantidadBase;

    private Unidad unidad;
    private iva IvaP;
    private categoria categoria;
    private productosID productosID;

    public producto() {
        super();
        unidad = new Unidad();
        IvaP = new iva();
        categoria = new categoria();
        productosID = new productosID();
    }

    public String getSerieproducto() {
        return serieproducto;
    }

    public void setSerieproducto(String serieproducto) {
        this.serieproducto = serieproducto;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public void setNombreProducto(String NombreProducto) {
        this.NombreProducto = NombreProducto;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(BigDecimal precio_venta) {
        this.precio_venta = precio_venta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getPorcentajedescuento() {
        return porcentajedescuento;
    }

    public void setPorcentajedescuento(String porcentajedescuento) {
        this.porcentajedescuento = porcentajedescuento;
    }

    public BigDecimal getValortotal() {
        return valortotal;
    }

    public void setValortotal(BigDecimal valortotal) {
        this.valortotal = valortotal;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isExiste() {
        return Existe;
    }

    public void setExiste(boolean Existe) {
        this.Existe = Existe;
    }

    public int getCantidadBD() {
        return cantidadBD;
    }

    public void setCantidadBD(int cantidadBD) {
        this.cantidadBD = cantidadBD;
    }

    public int getCodCurrent() {
        return codCurrent;
    }

    public void setCodCurrent(int codCurrent) {
        this.codCurrent = codCurrent;
    }

    public int getCantidadBase() {
        return CantidadBase;
    }

    public void setCantidadBase(int CantidadBase) {
        this.CantidadBase = CantidadBase;
    }

    public Unidad getUnidad() {
        if (unidad == null) {
            unidad = new Unidad();
        }
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public iva getIvaP() {
        if (IvaP == null) {
            IvaP = new iva();
        }
        return IvaP;
    }

    public void setIvaP(iva IvaP) {
        this.IvaP = IvaP;
    }

    public categoria getCategoria() {
        if (categoria == null) {
            categoria = new categoria();
        }
        return categoria;
    }

    public void setCategoria(categoria categoria) {
        this.categoria = categoria;
    }

    public productosID getProductosID() {
        if (productosID == null) {
            productosID = new productosID();
        }
        return productosID;
    }

    public void setProductosID(productosID productosID) {
        this.productosID = productosID;
    }

    public BigDecimal getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(BigDecimal cod_producto) {
        this.cod_producto = cod_producto;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into producto (idCategoria,SerieProducto,NombreProducto,idIva,costo,precio_venta,cantidad,porcentajeDescuento,"
                + "valorTotal,stock,estado,cod_unidad) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setBigDecimal(1, categoria.getIdCategoria());
            preparedStatement.setString(2, serieproducto);
            preparedStatement.setString(3, NombreProducto);
            preparedStatement.setBigDecimal(4, IvaP.getIdIva());
            preparedStatement.setBigDecimal(5, costo);
            preparedStatement.setBigDecimal(6, precio_venta);
            preparedStatement.setInt(7, cantidad);
            preparedStatement.setString(8, porcentajedescuento);
            preparedStatement.setBigDecimal(9, valortotal);
            preparedStatement.setBigDecimal(10, stock);
            preparedStatement.setString(11, estado);
            preparedStatement.setBigDecimal(12, unidad.getCod_unidad());

            transaccion = producto.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update producto set idCategoria=?,serieProducto=?,Nombreproducto=?,idIva=?,costo=?,"
                + "precio_venta=? ,valorTotal=?,stock=?,cod_unidad=? "
                + "where cod_producto=?";
        try {
            System.out.println("- " + PrepareUpdate);
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            System.out.println("-- " + categoria.getIdCategoria());
            preparedStatement.setBigDecimal(1, categoria.getIdCategoria());
            preparedStatement.setString(2, serieproducto);
            preparedStatement.setString(3, NombreProducto);
            preparedStatement.setBigDecimal(4, IvaP.getIdIva());
            preparedStatement.setBigDecimal(5, costo);
            preparedStatement.setBigDecimal(6, precio_venta);
            preparedStatement.setBigDecimal(7, valortotal);
            preparedStatement.setBigDecimal(8, stock);
            preparedStatement.setBigDecimal(9, unidad.getCod_unidad());
            preparedStatement.setBigDecimal(10, productosID.getCod_producto());
            transaccion = producto.this.getConecion().transaccion(preparedStatement);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int removeProductoXestado() {
        int transaccion = -1;
        String PrepareUpdate = "update producto set estado=? "
                + "where cod_producto=? and idCategoria=?";
        try {
            System.out.println("- " + PrepareUpdate);
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            System.out.println("-- " + categoria.getIdCategoria());
            preparedStatement.setString(1, estado);
            preparedStatement.setBigDecimal(2, productosID.getCod_producto());
            preparedStatement.setBigDecimal(3, categoria.getIdCategoria());
            transaccion = producto.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<producto> List = new ArrayList();
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().cstmt = this.getConecion().con.prepareCall("{call ListProductos}");
            ResultSet rs = producto.super.getConecion().cstmt.executeQuery();
            while (rs.next()) {
                producto tabla = new producto();
                productosID tablaID = new productosID();
                categoria cate = new categoria();
                Unidad unidad = new Unidad();
                iva iva = new iva();

                tablaID.setCod_producto(rs.getBigDecimal(1));
                tablaID.setIdCategoria(rs.getBigDecimal(2));

                tabla.setSerieproducto(rs.getString(3));
                tabla.setNombreProducto(rs.getString(4));
                tabla.setCosto(rs.getBigDecimal(6));
                tabla.setPrecio_venta(rs.getBigDecimal(7));
                tabla.setCantidad(rs.getInt(8));
                tabla.setPorcentajedescuento(rs.getString(9));
                tabla.setValortotal(rs.getBigDecimal(10));
                tabla.setStock(rs.getBigDecimal(11));
                tabla.setEstado(rs.getString(12));

                unidad.setCod_unidad(rs.getBigDecimal(13));
                unidad.setSiglas(rs.getString(15));

                cate.setIdCategoria(rs.getBigDecimal(2));
                cate.setDescripcion(rs.getString(14));

                iva.setIdIva(rs.getBigDecimal(5));
                iva.setDescripcion(rs.getString(16));

                cate.setGanancia(rs.getBigDecimal(17));

                tabla.setIvaP(iva);
                tabla.setCategoria(cate);
                tabla.setUnidad(unidad);
                tabla.setProductosID(tablaID);

                System.out.println("GANANCIA : " + cate.getGanancia());

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

    public java.util.List BuscarProducto(String filtro) {  //Busqueda Por codigo o por nombre
        ArrayList<producto> List = new ArrayList();

        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().cstmt = this.getConecion().con.prepareCall("{call ProductoBuscar(?)}");
            this.getConecion().cstmt.setString(1, filtro);

            ResultSet rs = producto.super.getConecion().cstmt.executeQuery();
            while (rs.next()) {
                System.out.println("encontro");
                producto tabla = new producto();
                productosID tablaID = new productosID();
                categoria cate = new categoria();
                Unidad unidad = new Unidad();
                iva iva = new iva();

                tablaID.setCod_producto(rs.getBigDecimal(1));
                tablaID.setIdCategoria(rs.getBigDecimal(2));

                tabla.setSerieproducto(rs.getString(3));
                tabla.setNombreProducto(rs.getString(4));
                tabla.setCosto(rs.getBigDecimal(6));
                tabla.setPrecio_venta(rs.getBigDecimal(7));
                tabla.setCantidad(rs.getInt(8));
                tabla.setPorcentajedescuento(rs.getString(9));
                tabla.setValortotal(rs.getBigDecimal(10));
                tabla.setStock(rs.getBigDecimal(11));
                tabla.setEstado(rs.getString(12));

                unidad.setCod_unidad(rs.getBigDecimal(13));
                unidad.setSiglas(rs.getString(15));

                cate.setIdCategoria(tablaID.getIdCategoria());
                cate.setDescripcion(rs.getString(14));

                iva.setIdIva(rs.getBigDecimal(5));
                iva.setDescripcion(rs.getString(16));

                cate.setGanancia(rs.getBigDecimal(17));

                unidad.setDescripcion(rs.getString(18));

                tabla.setIvaP(iva);
                tabla.setCategoria(cate);
                tabla.setUnidad(unidad);
                tabla.setProductosID(tablaID);

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

    public producto BuscarProductoXcodigo(int codigo, String serie) {  //Busqueda Por codigo o por nombre
        producto tabla = new producto();
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().cstmt = this.getConecion().con.prepareCall("{call BuscarProductoXCodigo(?,?)}");
            this.getConecion().cstmt.setString(1, serie);
            this.getConecion().cstmt.setInt(2, codigo);

            ResultSet rs = producto.super.getConecion().cstmt.executeQuery();
            if (rs.next()) {
                productosID tablaID = new productosID();
                categoria cate = new categoria();
                Unidad unidad = new Unidad();
                iva iva = new iva();

                tablaID.setCod_producto(rs.getBigDecimal(1));
                tablaID.setIdCategoria(rs.getBigDecimal(2));

                tabla.setSerieproducto(rs.getString(3));
                tabla.setNombreProducto(rs.getString(4));
                tabla.setCosto(rs.getBigDecimal(6));
                tabla.setPrecio_venta(rs.getBigDecimal(7));
                tabla.setCantidad(rs.getInt(8));
                tabla.setPorcentajedescuento(rs.getString(9));
                tabla.setValortotal(rs.getBigDecimal(10));
                tabla.setStock(rs.getBigDecimal(11));
                tabla.setEstado(rs.getString(12));

                unidad.setCod_unidad(rs.getBigDecimal(13));
                unidad.setSiglas(rs.getString(15));

                cate.setIdCategoria(tablaID.getIdCategoria());
                cate.setDescripcion(rs.getString(14));

                iva.setIdIva(rs.getBigDecimal(5));
                iva.setDescripcion(rs.getString(16));
                cate.setGanancia(rs.getBigDecimal(17));
                unidad.setDescripcion(rs.getString(18));

                tabla.setIvaP(iva);
                tabla.setCategoria(cate);
                tabla.setUnidad(unidad);
                tabla.setProductosID(tablaID);

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

    public BigDecimal PrecioSugerido(BigDecimal Costo, BigDecimal Ganancia) {
        return Costo.add(Costo.multiply(Ganancia.divide(new BigDecimal(100))));
    }

}
