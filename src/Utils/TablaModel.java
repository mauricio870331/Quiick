/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Pojos.EmpresaProveedor;
import Pojos.Proveedor;
import Pojos.categoria;
import Pojos.producto;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class TablaModel {

    ArrayList<categoria> listcategoria = new ArrayList();
    ArrayList<producto> listproductos = new ArrayList();
    ArrayList<Proveedor> listaProveedores = new ArrayList();
    ArrayList<EmpresaProveedor> listEmpresaProve = new ArrayList();

    public TablaModel(ArrayList x, int condicion) {
        switch (condicion) {
            case 1:
                this.listcategoria = x;
                break;
            case 2:
                this.listproductos = x;
                break;
            case 3:
                this.listaProveedores = x;
                break;
            case 4:
                this.listEmpresaProve = x;
                break;
        }

    }

    public DefaultTableModel ModelListCategoria() {
        DefaultTableModel model = new DefaultTableModel();
        String Titulos[] = {"#", "Nombre", "Ganancia"};
        model = new DefaultTableModel(null, Titulos) {
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        Object[] columna = new Object[3];
        categoria temp = null;
        Iterator<categoria> nombreIterator = listcategoria.iterator();
        while (nombreIterator.hasNext()) {
            temp = nombreIterator.next();
            columna[0] = temp.getIdCategoria();
            columna[1] = temp.getDescripcion();
            columna[2] = temp.getGanancia();

            model.addRow(columna);
        }
        model.addRow(new Object[]{"", ""});

        return model;
    }

    public DefaultTableModel ModelListProveedor() {
        DefaultTableModel model = new DefaultTableModel();
        String Titulos[] = {"#", "Empresa", "Nit", "Nombre Completo", "Direccion", "Telefono", "Correo", "Sexo"};
        model = new DefaultTableModel(null, Titulos) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        Object[] columna = new Object[8];
        Proveedor temp = null;
        Iterator<Proveedor> nombreIterator = listaProveedores.iterator();
        while (nombreIterator.hasNext()) {
            temp = nombreIterator.next();
            columna[0] = temp.getIdProveedor();
            columna[1] = temp.getEmpresa().getNombreEmpresa();
            columna[2] = temp.getEmpresa().getNit();
            columna[3] = temp.getPersona().getNombreCompleto();
            columna[4] = temp.getPersona().getDireccion();
            columna[5] = temp.getPersona().getTelefono();
            columna[6] = temp.getPersona().getCorreo();
            columna[7] = temp.getPersona().getSexo();

            model.addRow(columna);
        }
        model.addRow(new Object[]{"", ""});

        return model;
    }

    public DefaultTableModel ModelListProductos() {
        DefaultTableModel model = new DefaultTableModel();
        String Titulos[] = {"#", "Codigo", "Nombre", "Categoria", "Costo", "Iva", "Precio", "Cantidad", "Unidad", "Stock"};
        model = new DefaultTableModel(null, Titulos) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        Object[] columna = new Object[10];
        producto temp = null;
        Iterator<producto> nombreIterator = listproductos.iterator();
        while (nombreIterator.hasNext()) {
            temp = nombreIterator.next();
            System.out.println("Codigo de Producto : " + temp.getProductosID().getCod_producto());
            columna[0] = temp.getProductosID().getCod_producto();
            columna[1] = temp.getSerieproducto();
            columna[2] = temp.getNombreProducto();
            columna[3] = temp.getCategoria().getDescripcion();
            columna[4] = temp.getCosto();
            columna[5] = temp.getIvaP().getDescripcion();
            columna[6] = temp.getPrecio_venta();
            columna[7] = temp.getCantidad();
            columna[8] = temp.getUnidad().getSiglas();
            columna[9] = temp.getStock();

            model.addRow(columna);
        }
        model.addRow(new Object[]{"", ""});

        return model;
    }

    public DefaultTableModel ModelListProductosAñadidos() {
        DefaultTableModel model = new DefaultTableModel();
        String Titulos[] = {"#", "Codigo", "Nombre", "Categoria", "Costo", "Iva", "Unidad", "Cantidad", "Stock", "Valor"};
        model = new DefaultTableModel(null, Titulos) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, true, false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        Object[] columna = new Object[10];
        producto temp = null;
        Iterator<producto> nombreIterator = listproductos.iterator();
        while (nombreIterator.hasNext()) {
            temp = nombreIterator.next();
            System.out.println("Codigo de Producto : " + temp.getProductosID().getCod_producto());
            columna[0] = temp.getProductosID().getCod_producto();
            columna[1] = temp.getSerieproducto();
            columna[2] = temp.getNombreProducto();
            columna[3] = temp.getCategoria().getDescripcion();
            columna[4] = temp.getCosto();
            columna[5] = temp.getIvaP().getDescripcion();
            columna[6] = temp.getUnidad().getSiglas();
            columna[7] = temp.getCantidad();
            columna[8] = temp.getStock();
            columna[9] = temp.getPrecio_venta();

            model.addRow(columna);
        }
        model.addRow(new Object[]{"", ""});

        return model;
    }
    
    public DefaultTableModel ModelListProductosVenta() {
        DefaultTableModel model = new DefaultTableModel();
        String Titulos[] = {"#", "Codigo", "Nombre", "Cantidad", "Unidad", "Valor"};
        model = new DefaultTableModel(null, Titulos) {
            boolean[] canEdit = new boolean[]{
                false, false, false, true,  false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        Object[] columna = new Object[6];
        producto temp = null;
        Iterator<producto> nombreIterator = listproductos.iterator();
        while (nombreIterator.hasNext()) {
            temp = nombreIterator.next();
            System.out.println("Codigo de Producto : " + temp.getProductosID().getCod_producto());
            columna[0] = temp.getProductosID().getCod_producto();
            columna[1] = temp.getSerieproducto();
            columna[2] = temp.getNombreProducto();
            columna[3] = temp.getCantidad();
            columna[4] = temp.getUnidad().getSiglas();                        
            columna[5] = temp.getPrecio_venta();

            model.addRow(columna);
        }
        model.addRow(new Object[]{"", ""});

        return model;
    }

    public DefaultTableModel ModelListEmpresasProveedor() {
        DefaultTableModel model = new DefaultTableModel();
        String Titulo[] = {"#", "Nombre Empresa", "Nit", "Direccion", "Telefono"};
        model = new DefaultTableModel(null, Titulo) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        Object[] columna = new Object[5];
        EmpresaProveedor temp = null;
        Iterator<EmpresaProveedor> nombreIterator = listEmpresaProve.iterator();
        while (nombreIterator.hasNext()) {
            temp = nombreIterator.next();
            columna[0] = temp.getIdEmpresaProveedor();
            columna[1] = temp.getNombreEmpresa();
            columna[2] = temp.getNit();
            columna[3] = temp.getDireccion();
            columna[4] = temp.getTelefono();

            model.addRow(columna);
        }
        model.addRow(new Object[]{"", ""});

        return model;
    }
}
