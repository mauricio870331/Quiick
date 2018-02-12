package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class compra_producto extends Persistencia implements Serializable {

    public Date fechacompra;
    public BigDecimal cantidadproductos;
    public BigDecimal costocompra;
    public String estadoCompra;
    public int Codcurrval;
    public String nombreEmpresa;
    public int Bodega;

    private compra_productoID compra_productoID;
    private Proveedor objm_proveedores;
    private persona objPersona;

    List<Proveedor> List_m_proveedores = new ArrayList();

    public compra_producto() {
        super();
        compra_productoID = new compra_productoID();
        objm_proveedores = new Proveedor();
    }

    public Date getFechacompra() {
        return fechacompra;
    }

    public void setFechacompra(Date fechacompra) {
        this.fechacompra = fechacompra;
    }

    public BigDecimal getCantidadproductos() {
        return cantidadproductos;
    }

    public void setCantidadproductos(BigDecimal cantidadproductos) {
        this.cantidadproductos = cantidadproductos;
    }

    public BigDecimal getCostocompra() {
        return costocompra;
    }

    public void setCostocompra(BigDecimal costocompra) {
        this.costocompra = costocompra;
    }

    public compra_productoID getCompra_productoID() {
        if (compra_productoID == null) {
            compra_productoID = new compra_productoID();
        }
        return compra_productoID;
    }

    public void setCompra_productoID(compra_productoID compra_productoID) {
        this.compra_productoID = compra_productoID;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into compra_productos (cod_factura,cod_proveedor,fecha_compra,estado_factura,costoCompra,CantidadProductos,idBodega) values (?,?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);

            preparedStatement.setString(1, compra_productoID.getCod_factura());
            preparedStatement.setBigDecimal(2, compra_productoID.getCod_proveedor());
            preparedStatement.setDate(3,new java.sql.Date(fechacompra.getTime()));

            preparedStatement.setString(4, estadoCompra);
            preparedStatement.setBigDecimal(5, costocompra);
            preparedStatement.setBigDecimal(6, cantidadproductos);
            preparedStatement.setInt(7, Bodega);
            //preparedStatement.setTimestamp(8, new Timestamp(fechacompra.getTime()));

            transaccion = compra_producto.this.getConecion().transaccion(preparedStatement);

            String sql = "Select LAST_INSERT_ID()";
            ResultSet rs = compra_producto.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                Codcurrval = rs.getInt(1);
            }

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
        return transaccion;
    }

    @Override
    public int edit() {
        int transaccion = -1;
//        String prepareEdit = "update compra_producto set fecha_compra=?,CantidadProductos=?,costoCompra=?,cod_factura=? where cod_compraproducto=? and cod_proveedor=? and id_persona=? and cod_usuario=? and usuario=? and id_empresahist=? and cod_regimen=? and cod_empresa=? ";
//        try {
////            this.getConecion().con = this.getConecion().dataSource.getConnection();
////            this.getConecion().con.setAutoCommit(false);
////            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
////            preparedStatement.setDate(1, new java.sql.Date(fechacompra.getTime()));
////            preparedStatement.setBigDecimal(2, cantidadproductos);
////            preparedStatement.setBigDecimal(3, costocompra);
////            preparedStatement.setString(7, compra_productoID.getCod_factura());
////
////            preparedStatement.setBigDecimal(8, compra_productoID.getCod_compraproducto());
////            preparedStatement.setBigDecimal(9, compra_productoID.getCod_proveedor());
////            preparedStatement.setBigDecimal(10, compra_productoID.getId_persona());
////            preparedStatement.setBigDecimal(11, compra_productoID.getCod_usuario());
////            preparedStatement.setString(12, compra_productoID.getUsuario());
////            preparedStatement.setBigDecimal(13, compra_productoID.getId_empresahist());
////            preparedStatement.setBigDecimal(14, compra_productoID.getCod_regimen());
////            preparedStatement.setBigDecimal(15, compra_productoID.getCod_empresa());
//
//            transaccion = compra_producto.this.getConecion().transaccion(preparedStatement);
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
//        String prepareDelete = "delete from  compra_producto where cod_compraproducto=? and cod_proveedor=? and id_persona=? and cod_usuario=? and usuario=? and id_empresahist=? and cod_regimen=? and cod_empresa=?";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            this.getConecion().con.setAutoCommit(false);
//            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
//            preparedStatement.setBigDecimal(1, compra_productoID.getCod_compraproducto());
//            preparedStatement.setBigDecimal(2, compra_productoID.getCod_proveedor());
//            preparedStatement.setBigDecimal(3, compra_productoID.getId_persona());
//            preparedStatement.setBigDecimal(4, compra_productoID.getCod_usuario());
//            preparedStatement.setString(5, compra_productoID.getUsuario());
//            preparedStatement.setBigDecimal(6, compra_productoID.getId_empresahist());
//            preparedStatement.setBigDecimal(7, compra_productoID.getCod_regimen());
//            preparedStatement.setBigDecimal(8, compra_productoID.getCod_empresa());
//            transaccion = compra_producto.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<compra_producto> List() {
        ArrayList<compra_producto> listcompra_producto = new ArrayList();
//        String prepareQuery = "select cod_compraproducto,cod_proveedor,A.id_persona,A.cod_usuario,A.usuario,A.id_empresahist,A.cod_regimen,\n"
//                + "cod_empresa,fechacompra,cantidadproductos,costocompra,A.usuariomod,\n"
//                + " A.fechamod,A.operacion,cod_factura,B.nombre_completo,(select X.nombreempresa from empresaproveedores X , m_proveedores B\n"
//                + "where X.idempresaprovedor=B.idempresaprovedor and B.cod_proveedor=A.cod_proveedor) empresa from compra_producto A , persona B\n"
//                + "where A.id_persona=B.id_persona \n"
//                + "order by 1 desc limit 500";
//        System.out.println("query : " + prepareQuery);
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            ResultSet rs = compra_producto.super.getConecion().query(prepareQuery);
//
//            compra_productoID tablaID = new compra_productoID();
//            persona p = new persona();
//            while (rs.next()) {
//                compra_producto tabla = new compra_producto();
//                tablaID.setCod_compraproducto(rs.getBigDecimal(1));
//                tablaID.setCod_proveedor(rs.getBigDecimal(2));
//                tablaID.setId_persona(rs.getBigDecimal(3));
//                tablaID.setCod_usuario(rs.getBigDecimal(4));
//                tablaID.setUsuario(rs.getString(5));
//                tablaID.setId_empresahist(rs.getBigDecimal(6));
//                tablaID.setCod_regimen(rs.getBigDecimal(7));
//                tablaID.setCod_empresa(rs.getBigDecimal(8));
//
//                tabla.setFechacompra(rs.getDate(9));
//                tabla.setCantidadproductos(rs.getBigDecimal(10));
//                tabla.setCostocompra(rs.getBigDecimal(11));
//                tabla.setUsuariomod(rs.getString(12));
//                tabla.setFechamod(rs.getDate(13));
//                tabla.setOperacion(rs.getString(14));
//                tabla.setCod_factura(rs.getBigDecimal(15));
//
//                p.setNombre_completo(rs.getString(16));
//                tabla.setObjPersona(p);
//                tabla.setNombreEmpresa(rs.getString(17));
//
//                tabla.setCompra_productoID(tablaID);
//                System.out.println("-------------- " + tabla.getCod_factura());
//                listcompra_producto.add(tabla);
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
//        System.out.println("------ ++ ---");
//        for (compra_producto object : listcompra_producto) {
//            System.out.println("--  " + object.getCod_factura());
//        }
        return listcompra_producto;
    }

    public int AjusteCompra(int codCompra) {
        int transaccion = 0;
        String prepareQuery = "select * from CompraUpdateProductos(" + codCompra + ")";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = compra_producto.super.getConecion().query(prepareQuery);

            while (rs.next()) {
                transaccion = rs.getInt(1);
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
        return transaccion;
    }

    public int EliminarCompra(int codCompra, String usuario) {
        int transaccion = 0;
        String prepareQuery = "select * from comprarevertir(" + codCompra + ",'" + usuario + "')";
        System.out.println(prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = compra_producto.super.getConecion().query(prepareQuery);

            while (rs.next()) {
                transaccion = rs.getInt(1);
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
        return transaccion;
    }

    public boolean BuscarFactura(int codFactura) {
        boolean r = false;
        String prepareQuery = "select * from compra_producto where cod_factura=" + codFactura;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = compra_producto.super.getConecion().query(prepareQuery);

            if (rs.next()) {
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

    public void ProductosEstadoP() {
        String prepareQuery = "    select * from ProductosEstadoP()";
        try {
            System.out.println("- " + prepareQuery);
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = compra_producto.super.getConecion().query(prepareQuery);

        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

    }

    public int getCodcurrval() {
        return Codcurrval;
    }

    public void setCodcurrval(int Codcurrval) {
        this.Codcurrval = Codcurrval;
    }

    public persona getObjPersona() {
        return objPersona;
    }

    public void setObjPersona(persona objPersona) {
        this.objPersona = objPersona;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getEstadoCompra() {
        return estadoCompra;
    }

    public void setEstadoCompra(String estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    public int getBodega() {
        return Bodega;
    }

    public void setBodega(int Bodega) {
        this.Bodega = Bodega;
    }

}
