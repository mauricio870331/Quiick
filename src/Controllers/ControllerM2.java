/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Pojos.*;
import Utils.Reportes;
import Utils.TablaModel;
import Utils.VistaActual;
import Views.Modulo1;
import Views.Modales.Busqueda;
import Views.Modales.NuevoProducto;
import Views.Modulo2;
import Views.Modulo3;
import Views.Modulo4;
import Views.ModuloRoot;
import ds.desktop.notify.DesktopNotify;
import fingerUtils.CaptureFinger;
import fingerUtils.ReadFinger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mauricio Herrera
 */
public class ControllerM2 implements ActionListener, MouseListener, KeyListener {

    private final Modulo1 M1 = GetPrincipal.getModulo1();
    private final Modulo2 M2 = GetPrincipal.getModulo2();
    private final Modulo3 M3 = GetPrincipal.getModulo3();
    private final Modulo4 M4 = GetPrincipal.getModulo4();
    private final ModuloRoot MR = GetPrincipal.getModuloRoot();

    public RolxUser UsuarioLogeado;
    private persona p;
    private RolxUser ruxuser;
    private UsuarioID uid;
    private Reportes reportes;
    private Usuario us;
    private TipoDocumento td;
    private Rol rol;
    private Sedes sede;
    private Asistencia ad;
    private Musculos musculos;
    private dias dias;
    private Ejercicios ejercicio;
    private CaptureFinger cf;
    private ReadFinger rf;
    private Rutina rutina;
    private CajaXUser MiCaja;
    private PagoService pagoService;
    private TipoService Ts;
    private TipoPago Tp;
    public Proveedor pv;
    private EmpresaProveedor ep;
    public categoria c;
    public iva i;
    public Unidad u;
    private producto pr;
    private compra_producto cp;
    private compradetalle cd;
    private Bodega b;
    private objectobusqueda ob;
    private Cliente cl;
    private venta v;
    private ventaproducto vp;
    SimpleDateFormat sa = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat userFormat = new SimpleDateFormat("yyyyMMddhhmmss");
    SimpleDateFormat hh = new SimpleDateFormat("HH:mm:ss");
    private int countAction = 0;
    JFileChooser FileChooser = new JFileChooser();
    private String foto = "";
    ImageIcon ii = null;
    ImageIcon iin = null;
    int desde = 0;
    int hasta = 10;
    int cantidadregistros = 10;
    int currentpage = 1;
    String filtro = "";
    String opcPaginacion = "";
    private Object currentObject;

    private int cantRegustrosUsuarios = 0;
    private final ArrayList<Ejercicios> newRutina = new ArrayList();
    private ArrayList<Ejercicios> allEjercicios = new ArrayList();
    private Empresas empresas;

    private Menus menus;
    private SubMenus submenus;
    public JLabel lblMnues[];
    public JPanel pnMenuContent[];
    private PerfilRoles perfilxrol;
    ArrayList<JButton> listBtnMenus = new ArrayList();

    public ControllerM2(RolxUser UsuarioLogeado) throws IOException {
        this.UsuarioLogeado = UsuarioLogeado;
        inicomponents();
    }

    private void inicomponents() throws IOException {
        M2.btnGuardarProve.addActionListener(this);
        M2.btnViewEmpresaProvedor.addActionListener(this);
        M2.btnEmpresaProveGuardar.addActionListener(this);
        M2.mnuEditEmpresa.addActionListener(this);
        M2.mnuDeleteempresaProve.addActionListener(this);
        M2.mnuEditProveedor.addActionListener(this);
        M2.mnuDeleteProveedor.addActionListener(this);
        M2.btnCancelarProve.addActionListener(this);
        M2.btnCompraTrans.addActionListener(this);
        M2.BntTranCompraBuscar.addActionListener(this);
        M2.BntTranCompraNuevo.addActionListener(this);
        M2.btnCompraNueva.addActionListener(this);
        M2.txtComboSedeCompra.addActionListener(this);
        M2.mnuBuscarProveedor.addActionListener(this);
        M2.BntTranVentaBuscar.addActionListener(this);
        M2.mnuBuscarCliente.addActionListener(this);
        M2.txtVentaCodCliente.addKeyListener(this);
        M2.btnventa.addActionListener(this);
        M2.txtVentEfectivo.addKeyListener(this);
        M2.TxtbuscarProductoVenta.addKeyListener(this);
        M2.btnVentaNueva.addActionListener(this);
        M2.btnCaja.addActionListener(this);
        M2.VentaProductosAdd.addKeyListener(this);

        Adaptador();
//        cargarMenu();

//        getMiCaja().CierreCajasAuto();
//        setMiCaja(null);
//        cargarTiposDocumentos();
//        cargarRoles();
//        cargarEmpresas();
//        cargarTblUsers(filtro);
//        showPanel(2, "PnProveedores");
//        System.out.println("pr.pnMicajaEstado.getText() " + pr.pnMicajaEstado.getText());
//        MR.btnGuardarEmpresa.addActionListener(this);
//        MR.btnCancelarEmpresa.addActionListener(this);
////        MR.btnEmpresas.addMouseListener(this);
//        MR.mnuEditEmpresa.addActionListener(this);
//        MR.mnuDeleteEmpresa.addActionListener(this);
//        MR.mnuNewSede.addActionListener(this);
//        MR.btnGuardarRol.addActionListener(this);
////        MR.btnRoles.addActionListener(this);
//        MR.btnCancelarRol.addActionListener(this);
//        MR.mnuEditRol.addActionListener(this);
//        MR.mnuDeleteRol.addActionListener(this);

        cargarMenus();
        cargarPerfilesXRol();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == M2.btnVentaNueva) {
            getV();
            getCl();
            getTp();
            getVp();
            ventaID VD = new ventaID();
            v.setFechaVenta(new Date());
            v.getVentaid().setIdTipoVenta(new BigDecimal(1));
            v.setIdPersonaCliente(cl.getCodCliente());
            Tp = (TipoPago) M2.txtVenaTipoPago.getSelectedItem();
            v.setIdTipoPago(Tp.getIdtipoPago());

            VD.setIdCaja(new BigDecimal(1));
            VD.setIdUsuario(new BigDecimal(Contenedor.getUsuario().getObjUsuariosID().getIdUsuario()));
            VD.setUsuario(Contenedor.getUsuario().getObjUsuariosID().getUsuario());
            VD.setIdSede(new BigDecimal(Contenedor.getUsuario().getObjUsuariosID().getIdSede()));
            VD.setIdEmpresa(new BigDecimal(Contenedor.getUsuario().getObjUsuariosID().getIdempresa()));
            VD.setIdPersona(new BigDecimal(Contenedor.getUsuario().getObjUsuariosID().getIdPersona()));

            for (producto ObjProducto : Contenedor.getListProductos()) {
                vp.setCantidadVenta(new BigDecimal(ObjProducto.getCantidad()));
                vp.setValoriva(new BigDecimal(0));
                vp.setValorTotal(ObjProducto.getValortotal().multiply(new BigDecimal(ObjProducto.getCantidad())));
                vp.setValorproducto(ObjProducto.getValortotal());
                vp.setCod_producto(ObjProducto.getProductosID().getCod_producto());
                vp.setIdCategoria(ObjProducto.getProductosID().getIdCategoria());
            }

        }

        if (e.getSource() == M2.mnuBuscarCliente) {
            try {
                getOb();
                ob.setTitulo("Buscar Cliente");
                ob.setFiltro("Nombre ó Cedula");
                ob.setModulo(2);
                ob.setCondicion(2);
                ob.setM2(this);
                new Busqueda(M2, true, ob).setVisible(true);
            } catch (SQLException ex) {
                System.out.println("Error al abrir modal");
            }
        }

        if (e.getSource() == M2.btnventa) {
            CargarPorcentajesDescuentos();
            CalculosVenta();
            getCl();
            CargaTiposPagos();
            cl.setCodCliente(new BigDecimal(1));
            M2.txtVentaCodCliente.setText(cl.getP().getDocumento());
            M2.txtVentaNomcliente.setText(cl.getP().getNombreCompleto());
            Contenedor.getListProductos().clear();            
            showPanel(2, "PnTransVenta");
        }

        if (e.getSource() == M2.mnuBuscarProveedor) {
            try {
                getOb();
                ob.setTitulo("Buscar Proveedor");
                ob.setFiltro("Nombre ó Cedula");
                ob.setModulo(2);
                ob.setCondicion(2);
                ob.setM2(this);
                new Busqueda(M2, true, ob).setVisible(true);
            } catch (SQLException ex) {
                System.out.println("Error al abrir modal");
            }
        }

        if (e.getSource() == M2.BntTranCompraBuscar || e.getSource() == M2.BntTranVentaBuscar) {
            try {
                System.out.println("Click en buscar producto");
                getOb();
                ob.setTitulo("Buscar Producto");
                ob.setFiltro("Nombre ó Cedula");
                ob.setModulo(2);
                ob.setCondicion(1);
                ob.setM2(this);
                new Busqueda(M2, true, ob).setVisible(true);
            } catch (SQLException ex) {
                System.out.println("Error al abrir modal");
            }
        }

        if (e.getSource() == M2.txtComboSedeCompra) {
            System.out.println("acciono");
            ListBodegas();
        }

        if (e.getSource() == M2.BntTranCompraNuevo) {
            try {
                new NuevoProducto(null, true, this).setVisible(true);
            } catch (SQLException ex) {
                System.out.println("Error al abrir modal");
            }
        }

        if (e.getSource() == M2.btnCompraTrans) {
            getCp();
            cp.setCostocompra(new BigDecimal(0));
            ListProductosAñadidos();
            ListSedes();
            CargarDatosProveedor(null);
            showPanel(2, "PnTransCompra");
        }

        listBtnMenus.forEach((JButton listBtnMenu) -> {
            if (e.getSource() == listBtnMenu) {
//                System.out.println("btn " + listBtnMenu.getActionCommand());
                switch (listBtnMenu.getActionCommand()) {
                    case "Caja":
                        try {
                            System.out.println("Transaccion de caja");
                            EstadoMiCaja();
                            showPanel(2, "btnTransaccionCaja");
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ControllerM2.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Lista Proveedores":
                        System.out.println("Ingreso a proveedores");
                        CargarDatosInicialesProveedores(1, null);
                        cargarTiposDocumentosProveedor();
                        ListProveedores();
                        showPanel(2, "PnProveedores");
                        break;
                    case "Compras":
                        showPanel(2, "PnCompras");
                        break;
                }
            }
        });

//        if (e.getSource() == M2.btnProveedores || e.getSource() == MR.btnProveedores) {
//            System.out.println("Ingreso a proveedores");
//            CargarDatosInicialesProveedores(1, null);
//            cargarTiposDocumentosProveedor();
//            ListProveedores();
//            showPanel(2, "PnProveedores");
//        }
        if (e.getSource() == M2.btnGuardarProve && M2.btnGuardarProve.getText().trim().equals("Guardar")) {
            getPv();
            getP();

            TipoDocumento t = (TipoDocumento) M2.txtTipoDocProveedor.getSelectedItem();
            EmpresaProveedor empresa = (EmpresaProveedor) M2.cboEmpresasProveedor.getSelectedItem();

            p.setDocumento(M2.txtDocProve.getText());
            p.setIdtipoDocumento(t.getIdTipoDocumento());
            p.setNombre(M2.txtNombresProve.getText());
            p.setApellido(M2.txtApellidosProve.getText());
            p.setNombreCompleto(M2.txtNombresProve.getText() + " " + M2.txtApellidosProve.getText());
            p.setDireccion("");
            p.setTelefono(M2.txtTelefonosProve.getText());
            p.setSexo("M");
            p.setFechaNacimiento(null);
            p.setCorreo("");
            p.setEstado("A");
            p.setPathFoto(""); //vacio

            pv.setEmpresa(empresa);
            pv.setPersona(p);
            pv.setEstado("A");
            String mns = p.ValidacionCampos(3);

            if (mns.length() == 0) {
                if (pv.create() > 0) {
                    DesktopNotify.showDesktopMessage("Aviso..!", "Exito al crear el proveedor", DesktopNotify.INFORMATION, 5000L);
                    LimpiarCampos("proveedores");
                    ListProveedores();
                } else {
                    DesktopNotify.showDesktopMessage("Aviso..!", "Error al crear proveedor", DesktopNotify.ERROR, 5000L);
                }
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", mns, DesktopNotify.INFORMATION, 5000L);
            }
        }
//        
        if (e.getSource() == M2.mnuDeleteempresaProve) {
            getEp();
            ep.setEstado("I");
            int fila = M2.tblListaEmpresasProve.getSelectedRow();
            if (fila >= 0) {
                String codigo = M2.tblListaEmpresasProve.getValueAt(fila, 0).toString();
                ep.setIdEmpresaProveedor(new BigDecimal(codigo));
                if (ep.EliminarXEstado() > 0) {
                    DesktopNotify.showDesktopMessage("Aviso..!", "Exito al Eliminar la Empresa", DesktopNotify.INFORMATION, 5000L);
                    ListEmpresasProveedor("");
                } else {
                    DesktopNotify.showDesktopMessage("Aviso..!", "Error al Eliminar Empresa", DesktopNotify.ERROR, 5000L);
                }
            }

        }

        if (e.getSource() == M2.btnCancelarProve) {

            M2.txtDocProve.setText("");
            M2.txtNombresProve.setText("");
            M2.txtApellidosProve.setText("");
            M2.txtTelefonosProve.setText("");
            M2.btnGuardarProve.setText("Guardar");
        }

        if (e.getSource() == M2.mnuEditProveedor) {
            int fila = M2.tblProveedores.getSelectedRow();
            if (fila >= 0) {
                String codigo = M2.tblProveedores.getValueAt(fila, 0).toString();
                CargarDatosProvedor(Integer.parseInt(codigo));
                M2.btnGuardarProve.setText("Editar");
            }

        }

        if (e.getSource() == M2.btnGuardarProve && M2.btnGuardarProve.getText().trim().equals("Editar")) {
            getPv();
            getP();

            TipoDocumento t = (TipoDocumento) M2.txtTipoDocProveedor.getSelectedItem();
            EmpresaProveedor empresa = (EmpresaProveedor) M2.cboEmpresasProveedor.getSelectedItem();

            p.setDocumento(M2.txtDocProve.getText());
            p.setIdtipoDocumento(t.getIdTipoDocumento());
            p.setNombre(M2.txtNombresProve.getText());
            p.setApellido(M2.txtApellidosProve.getText());
            p.setNombreCompleto(M2.txtNombresProve.getText() + " " + M2.txtApellidosProve.getText());
            p.setDireccion("");
            p.setTelefono(M2.txtTelefonosProve.getText());
            p.setSexo("M");
            p.setFechaNacimiento(null);
            p.setCorreo("");
            p.setEstado("A");
            p.setPathFoto(""); //vacio

            pv.setEmpresa(empresa);
            pv.setPersona(p);
            pv.setEstado("A");
            String mns = p.ValidacionCampos(3);

            if (mns.length() == 0) {
                if (pv.edit() > 0) {
                    DesktopNotify.showDesktopMessage("Aviso..!", "Exito al Modificar proveedor", DesktopNotify.INFORMATION, 5000L);
                    LimpiarCampos("proveedores");
                    ListProveedores();
                    M2.btnGuardarProve.setText("Guardar");
                } else {
                    DesktopNotify.showDesktopMessage("Aviso..!", "Error al Modificar proveedor", DesktopNotify.ERROR, 5000L);
                }
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", mns, DesktopNotify.INFORMATION, 5000L);
            }
        }

        if (e.getSource() == M2.btnEmpresaProvedorCancelar) {
            M2.txtProveEmpNombre.setText("");
            M2.txtProveEmpNit.setText("");
            M2.txtProveEmpDireccion.setText("");
            M2.txtProveEmpTelefono.setText("");
            M2.btnEmpresaProveGuardar.setText("Guardar");
        }

        if (e.getSource() == M2.btnEmpresaProveGuardar && M2.btnEmpresaProveGuardar.getText().trim().equals("Editar")) {
            getEp();
            ep.setNombreEmpresa(M2.txtProveEmpNombre.getText().trim());
            ep.setNit(M2.txtProveEmpNit.getText().trim());
            ep.setDireccion(M2.txtProveEmpDireccion.getText().trim());
            ep.setTelefono(M2.txtProveEmpTelefono.getText().trim());
            ep.setEstado("A");
            String mns = ep.ValidacionCampos();

            if (mns.length() == 0) {
                if (ep.edit() > 0) {
                    DesktopNotify.showDesktopMessage("Aviso..!", "Exito al Modificar Empresa", DesktopNotify.INFORMATION, 5000L);
                    ListEmpresasProveedor("");
                    M2.txtProveEmpNombre.setText("");
                    M2.txtProveEmpNit.setText("");
                    M2.txtProveEmpDireccion.setText("");
                    M2.txtProveEmpTelefono.setText("");
                    M2.btnEmpresaProveGuardar.setText("Guardar");
                } else {
                    DesktopNotify.showDesktopMessage("Aviso..!", "Error al eliminar Producto", DesktopNotify.ERROR, 5000L);
                }
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", mns, DesktopNotify.INFORMATION, 5000L);
            }
        }

        if (e.getSource() == M2.mnuEditEmpresa) {
            int fila = M2.tblListaEmpresasProve.getSelectedRow();
            if (fila >= 0) {
                String codigo = M2.tblListaEmpresasProve.getValueAt(fila, 0).toString();
                CargarDatosEmpresaProvedor(Integer.parseInt(codigo));
            }

        }

        if (e.getSource() == M2.btnEmpresaProveGuardar && M2.btnEmpresaProveGuardar.getText().trim().equals("Guardar")) {
            getEp();

            ep.setNombreEmpresa(M2.txtProveEmpNombre.getText().trim());
            ep.setNit(M2.txtProveEmpNit.getText().trim());
            ep.setDireccion(M2.txtProveEmpDireccion.getText().trim());
            ep.setTelefono(M2.txtProveEmpTelefono.getText().trim());
            ep.setEstado("A");
            String mns = ep.ValidacionCampos();

            if (mns.length() == 0) {
                if (ep.getNombreEmpresa().length() > 0) {
                    if (ep.create() > 0) {
                        DesktopNotify.showDesktopMessage("Aviso..!", "Exito al crear empresa", DesktopNotify.INFORMATION, 5000L);
                        ListEmpresasProveedor("");
                        M2.txtProveEmpNombre.setText("");
                        M2.txtProveEmpNit.setText("");
                        M2.txtProveEmpDireccion.setText("");
                        M2.txtProveEmpTelefono.setText("");
                        showPanel(2, "PnEmpresaProveedor");
                    } else {
                        DesktopNotify.showDesktopMessage("Aviso..!", "Error al eliminar Producto", DesktopNotify.ERROR, 5000L);
                    }
                }
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", mns, DesktopNotify.INFORMATION, 5000L);
            }
        }

        if (e.getSource() == M2.btnViewEmpresaProvedor) {
            ListEmpresasProveedor("");
            showPanel(2, "PnEmpresaProveedor");
        }

//
//        if (e.getSource() == pr.mnuEditFechasPagos) {
//            CargarServicios();
//            CargarTiposPagos();
//            int fila = pr.tblHistoryPays.getSelectedRow();
//            if (fila >= 0) {
//                try {
//                    pr.lblIdPagoAction.setText(pr.tblHistoryPays.getValueAt(fila, 0).toString());
//                    for (int i = 0; i < pr.combotiposService1.getItemCount(); i++) {
//                        if (pr.combotiposService1.getItemAt(i).toString().equals(getTs().getTipoByDescripcion(pr.tblHistoryPays.getValueAt(fila, 1).toString()).toString())) {
//                            pr.combotiposService1.setSelectedIndex(i);
//                        }
//                    }
//                    pr.cldFechaPagoHistory.setDate(sa.parse(pr.tblHistoryPays.getValueAt(fila, 2).toString()));
//                    pr.cldFechaPagoDesde.setDate(sa.parse(pr.tblHistoryPays.getValueAt(fila, 3).toString()));
//                    pr.cldFechaPagoHasta.setDate(sa.parse(pr.tblHistoryPays.getValueAt(fila, 4).toString()));
//                    setTs(null);
//                } catch (ParseException ex) {
//                    System.out.println("error " + ex);
//                }
//            } else {
//                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
//            }
//        }
//
//        if (e.getSource() == pr.combotiposService1) {
//            if (pr.combotiposService1.getSelectedItem() != null) {
//                cargarDescuentosPagos(1, 2);
//            }
//        }
//
//        if (e.getSource() == pr.cboRol) {
//            Rol r = (Rol) pr.cboRol.getSelectedItem();
//            if (r.getDescripcion().equals("Administrador")) {
//                pr.txtUser.setEnabled(true);
//                pr.txtClave.setEnabled(true);
//                pr.txtUser.setText("");
//                pr.txtClave.setText("");
//            } else {
//                pr.txtUser.setEnabled(false);
//                pr.txtUser.setText(userFormat.format(new Date()));
//                pr.txtClave.setEnabled(false);
//                pr.txtClave.setText("123456");
//            }
//
//        }
//
//        if (e.getSource() == pr.mnuDeletePagos) {
//            int fila = pr.tblHistoryPays.getSelectedRow();
//            if (fila >= 0) {
//                String clave = JOptionPane.showInputDialog(null, "Para eliminar un pago debe\nproporcionar la clave de seguridad..!", "Información", 1);
//                if (clave != null && !clave.equals("")) {
//                    if (getUsuarioLogeado().getObjUsuario().getClave().equals(clave)) {
//                        PagoService p = getPagoService();
//                        p.getObjPagoServiceID().setIdPago(new BigDecimal(pr.tblHistoryPays.getValueAt(fila, 0).toString()));
//                        if (p.removeById() > 0) {
//                            DesktopNotify.showDesktopMessage("Aviso..!", "Pago eliminado con exito..!", DesktopNotify.SUCCESS, 5000L);
//                            cargarHistorialPagos(Integer.parseInt(pr.lblPayIdUser.getText()), null, null, pr.tblHistoryPays);
//                        } else {
//                            DesktopNotify.showDesktopMessage("Aviso..!", "No se pudo eliminar el pago..!", DesktopNotify.ERROR, 5000L);
//                        }
//                    } else {
//                        DesktopNotify.showDesktopMessage("Aviso..!", "Clave de seguridad incorrecta..!", DesktopNotify.ERROR, 5000L);
//                    }
//                }
//            } else {
//                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
//            }
//        }
//
//        if (e.getSource() == pr.btnEditPago) {
//            //validar servicio
//            Object[] componentes = {pr.cldFechaPagoHistory, pr.cldFechaPagoDesde, pr.cldFechaPagoHasta};
//            if (validarCampos(componentes, "") == 0) {
//                PagoService p = getPagoService();
//                Ts = (TipoService) pr.combotiposService1.getSelectedItem();
//                Tp = (TipoPago) pr.combotiposPago1.getSelectedItem();
//                p.getObjPagoServiceID().setIdPago(new BigDecimal(pr.lblIdPagoAction.getText()));
//                p.setFechaPago(pr.cldFechaPagoHistory.getDate());
//                p.setFechaInicio(pr.cldFechaPagoDesde.getDate());
//                p.setFechaFinal(pr.cldFechaPagoHasta.getDate());
//                p.getObjTipoService().setIdtipoService(Ts.getIdtipoService());
//                p.setIdTipoPago(Tp.getIdtipoPago());
//                p.setValorNeto(new BigDecimal(pr.txtpagosValor1.getText()));
//                p.setValorTotal(new BigDecimal(pr.txtpagosValorTot1.getText()));
//                if (p.EditFechas() > 0) {
//                    DesktopNotify.showDesktopMessage("Aviso..!", "Pago actualizado con exito..!", DesktopNotify.SUCCESS, 5000L);
//                    pr.cldFechaPagoHistory.setDate(new Date());
//                    pr.cldFechaPagoDesde.setDate(null);
//                    pr.cldFechaPagoHasta.setDate(null);
//                    pr.lblIdPagoAction.setText("");
//                    pr.combotiposService1.setSelectedIndex(0);
//                    pr.combotiposPago1.setSelectedIndex(0);
//                    cargarHistorialPagos(Integer.parseInt(pr.lblPayIdUser.getText()), null, null, pr.tblHistoryPays);
//                    setPagoService(null);
//                    Ts = null;
//                    Tp = null;
//                    p = null;
//                } else {
//                    DesktopNotify.showDesktopMessage("Aviso..!", "Ocurrio un error al actualizar el pago..!", DesktopNotify.FAIL, 5000L);
//                }
//            } else {
//                DesktopNotify.showDesktopMessage("Aviso..!", "Los campos Marcados en rojo son obligatorios...!", DesktopNotify.ERROR, 5000L);
//            }
//        }
//
//        if (e.getSource() == pr.btnEditPagacancel) {
//            Object[] componentes = {pr.lblIdPagoAction, pr.lblPayIdUser, pr.cldDesdePagos, pr.cldHastaPagos, pr.cldFechaPagoHistory, pr.cldFechaPagoDesde, pr.cldFechaPagoHasta};
//            resetCampos(componentes);
//            pr.combotiposService1.setSelectedIndex(0);
//            try {
//                cargarTblUsers("");
//                showPanel("mnuUsers");
//            } catch (IOException ex) {
//                System.out.println("error linea 241 " + ex);
//            }
//
//        }
//
//        if (e.getSource() == pr.mnuHistoryPays) {
//            int fila = pr.tblUsers.getSelectedRow();
//            if (fila >= 0) {
//                pr.cldFechaPagoHistory.setDate(null);
//                pr.cldFechaPagoDesde.setDate(null);
//                pr.cldFechaPagoHasta.setDate(null);
//                int id_usuario = Integer.parseInt(pr.tblUsers.getValueAt(fila, 9).toString());
//                pr.lblPayIdUser.setText(Integer.toString(id_usuario));
//                cargarHistorialPagos(id_usuario, null, null, pr.tblHistoryPays);
//                showPanel("mnuHistoryPays");
//            } else {
//                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
//            }
//        }
//
//        if (e.getSource() == pr.btnBuscarPago) {
//            Object[] componentes = {pr.cldDesdePagos, pr.cldHastaPagos};
//            if (validarCampos(componentes, "") == 0) {
//                cargarHistorialPagos(Integer.parseInt(pr.lblPayIdUser.getText()), pr.cldDesdePagos.getDate(), pr.cldHastaPagos.getDate(), pr.tblHistoryPays);
//            } else {
//                DesktopNotify.showDesktopMessage("Informacion..!", "El rango de fechas es obligatorio..!", DesktopNotify.WARNING, 6000L);
//                pr.cldDesdePagos.requestFocus();
//            }
//
//        }
//
//        if (e.getSource() == pr.btnCancelarPagos) {
//            showPanel("btnTransaccionCaja");
//        }
//
//        if (e.getSource() == pr.mnuGenerarPago) {
//            try {
//                EstadoMiCaja();
//            } catch (ClassNotFoundException ex) {
//                System.out.println("error linea 312 " + ex);
//            }
//            if (pr.pnMicajaEstado.getText().equalsIgnoreCase("Cerrada") || pr.pnMicajaEstado.getText().equalsIgnoreCase("Estado")) {
//                DesktopNotify.showDesktopMessage("Aviso..!", "Para realizar pagos debe haber almenos una caja abierta..!", DesktopNotify.ERROR, 6000L);
//            } else {
//                int fila = pr.tblUsers.getSelectedRow();
//                if (fila >= 0) {
//                    int id_persona = Integer.parseInt(pr.tblUsers.getValueAt(fila, 0).toString());
//                    System.out.println("id_persona " + id_persona);
//                    RolxUser p = getRuxuser().getDatosPersonaById(id_persona);
//                    Usuario u = new Usuario();
//                    u.getObjUsuariosID().setIdPersona(p.getObjUsuario().getObjPersona().getIdPersona());
//                    u.getObjUsuariosID().setIdSede(p.getObjUsuario().getObjUsuariosID().getIdSede());
//                    u.getObjUsuariosID().setIdUsuario(p.getObjUsuario().getObjUsuariosID().getIdUsuario());
//                    u.getObjUsuariosID().setIdempresa(p.getObjUsuario().getObjUsuariosID().getIdempresa());
//                    u.getObjUsuariosID().setUsuario(p.getObjUsuario().getObjUsuariosID().getUsuario());
//                    setUs(u);
//                    getMiCaja();
//                    MiCaja.getObjCajaxUserID().setIdUsuario(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdUsuario()));
//                    MiCaja = MiCaja.MiCaja();
//                    IniciarPagos(p.getObjUsuario().getObjPersona().getDocumento(), p.getObjUsuario().getObjPersona().getNombre() + " " + p.getObjUsuario().getObjPersona().getApellido(), false);
//                    ListPagosXuser(pr.tblListaPagosXuser);
//                    setRuxser(null);
//                    u = null;
//                } else {
//                    DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
//                }
//            }
//        }
//
//        if (e.getSource() == pr.btnPagosCaja) {
//            if (us != null) {
//                getPagoService();
//                if (validacionPagos() == false) {
//                    Ts = (TipoService) pr.combotiposService.getSelectedItem();
//                    Tp = (TipoPago) pr.combotiposPago.getSelectedItem();
//                    String Descuento[] = pr.comboDescuentos.getSelectedItem().toString().split("%");
//                    pagoService.setFechaPago(new Date());
//                    pagoService.setFechaInicio(pr.txtpagosFechaIni.getDate());
//                    pagoService.setFechaFinal(pr.txtpagosFechaFinal.getDate());
//                    pagoService.setDevolucion(new BigDecimal(0));
//                    pagoService.setIdTipoPago(Tp.getIdtipoPago());
//                    pagoService.setIdTipoService(Ts.getIdtipoService());
//                    pagoService.setPorcentajeDescuento(new BigDecimal(0));
//                    pagoService.setValorNeto(new BigDecimal(pr.txtpagosValor.getText()));
//                    pagoService.setValorPagado(new BigDecimal(pr.txtpagosValor.getText()));
//                    pagoService.setValorTotal(new BigDecimal(pr.txtpagosValorTot.getText()));
//                    pagoService.setValorDescuento(new BigDecimal(pr.txtpagosValorDescuento.getText()));
//
//                    pagoService.getObjPagoServiceID().setIdPersona(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdPersona()));
//                    pagoService.getObjPagoServiceID().setIdSede(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdSede()));
//                    pagoService.getObjPagoServiceID().setIdUsuario(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdUsuario()));
//                    pagoService.getObjPagoServiceID().setIdempresa(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdempresa()));
//                    pagoService.getObjPagoServiceID().setUsuario(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getUsuario());
//                    pagoService.getObjPagoServiceID().setIdcaja(MiCaja.getObjCajaxUserID().getIdcaja());
//
//                    pagoService.getObjPagoServiceID().setIdPersonaCliente(new BigDecimal(us.getObjUsuariosID().getIdPersona()));
//                    pagoService.getObjPagoServiceID().setIdSedeCliente(new BigDecimal(us.getObjUsuariosID().getIdSede()));
//                    pagoService.getObjPagoServiceID().setIdUsuarioCliente(new BigDecimal(us.getObjUsuariosID().getIdUsuario()));
//                    pagoService.getObjPagoServiceID().setIdempresaCliente(new BigDecimal(us.getObjUsuariosID().getIdempresa()));
//                    pagoService.getObjPagoServiceID().setUsuarioCliente(us.getObjUsuariosID().getUsuario());
//
//                    if (pagoService.create() > 0) {
//                        DesktopNotify.showDesktopMessage("Informacion!!", "Exito al Realizar Transaccion", DesktopNotify.INFORMATION, 5000L);
//                        IniciarPagos("", "", true);
//                        us = null;
//                    }
//
//                } else {
//                    DesktopNotify.showDesktopMessage("Aviso!!", "Ya existe un pago Realizado , en ese periodo de Tiempo Revisar por favor , el historial de pagos", DesktopNotify.WARNING, 9000L);
//                }
//            } else {
//                DesktopNotify.showDesktopMessage("Aviso!!", "Debe Seleccionar un cliente", DesktopNotify.ERROR, 5000L);
//                pr.txtpagosCedula.requestFocus();
//            }
//            //pagoService.set
//            //pagoService.setIdTipoPago(-1);
//        }
//
//        if (e.getSource() == pr.comboDescuentos) {
//            System.out.println("descuento");
//            if (pr.comboDescuentos.getSelectedItem() != null) {
//                cargarDescuentosPagos(2, 1);
//            }
//        }
//        if (e.getSource() == pr.combotiposService) {
//            if (pr.combotiposService.getSelectedItem() != null) {
//                System.out.println("por aquiiii");
//                cargarDescuentosPagos(1, 1);
//            }
//        }
//
//        if (e.getSource() == pr.mnuBusqueda) {
//            try {
//                new Busqueda(pr, true).setVisible(true);
//            } catch (SQLException ex) {
//                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        if (e.getSource() == pr.BtnGenerarPagos) {
//            IniciarPagos("", "", true);
//        }
//
//        if (e.getSource() == pr.cboGym) {
//            Empresa empre = (Empresa) pr.cboGym.getSelectedItem();
//            if (!empre.getNombre().equals("Seleccione")) {
//                cargarSedesByEmprsa(empre.getIdempresa());
//            }
//        }
//
        if (e.getSource() == M2.btnCaja) {
            if (M2.pnMicajaEstado.getText().equalsIgnoreCase("Cerrada")) {
                String base = JOptionPane.showInputDialog(null, "Base :", "Abrir Turno", 1);
                System.out.println("base = " + base);
                if (base == null || base.equals("")) {
                    base = "0";
                }
                getMiCaja();
                MiCaja.setEstado("A");
                MiCaja.setFechainicio(new Date());
                MiCaja.setFechaFinal(new Date());
                MiCaja.setMontoFinal(new BigDecimal(0));
                MiCaja.setMontoInicial(new BigDecimal(base));
                MiCaja.setMontoVenta(new BigDecimal(0));;
                MiCaja.getObjCajaxUserID().setIdPersona(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdPersona()));
                MiCaja.getObjCajaxUserID().setIdSede(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdSede()));
                MiCaja.getObjCajaxUserID().setIdUsuario(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdUsuario()));
                MiCaja.getObjCajaxUserID().setIdempresa(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdempresa()));
                MiCaja.getObjCajaxUserID().setUsuario(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getUsuario());

                if (MiCaja.create() > 0) {
                    try {
                        DesktopNotify.showDesktopMessage("Información", "Caja Abierta Con Exito", DesktopNotify.INFORMATION, 5000L);
                        EstadoMiCaja();
                    } catch (ClassNotFoundException ex) {

                    }
                }

            } else {
                Object[] opciones = {"Si", "No"};
                int eleccion = JOptionPane.showOptionDialog(null, "¿Desea Cerrar la Caja?", "Mensaje de Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Si");
                if (eleccion == JOptionPane.YES_OPTION) {
                    System.out.println("cerrarndo caja");
                    MiCaja.setEstado("C");
                    MiCaja.getObjCajaxUserID().setIdPersona(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdPersona()));
                    MiCaja.getObjCajaxUserID().setIdSede(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdSede()));
                    MiCaja.getObjCajaxUserID().setIdUsuario(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdUsuario()));
                    MiCaja.getObjCajaxUserID().setIdempresa(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdempresa()));
                    MiCaja.getObjCajaxUserID().setUsuario(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getUsuario());

                    if (MiCaja.CierreCaja() > 0) {
                        try {
                            DesktopNotify.showDesktopMessage("Información", "Caja Cerrada Con Exíto", DesktopNotify.INFORMATION, 5000L);
                            EstadoMiCaja();
                        } catch (ClassNotFoundException ex) {

                        }
                    }
                }
            }

        }
//
//        if (e.getSource() == pr.cboMusculos2) {
//            Musculos ej = (Musculos) pr.cboMusculos2.getSelectedItem();
//            int idmusculo = 0;
//            if (ej != null && !ej.getdescripcion().equals("Seleccione")) {
//                idmusculo = ej.getIdMusculo();
//            }
//            try {
//                cargarTblEjercicios2(idmusculo);
//            } catch (IOException ex) {
//                System.out.println("error " + ex);
//            }
//        }
//
//        if (e.getSource() == pr.btnGestionRutinas) {
//            try {
//                clearFormUsers();
//                cargarDiasMusculos();
//                cargarTblRutinas("");
//                cargarAllEjericios();
//                showPanel("mnuRutinas");
//            } catch (IOException ex) {
//                System.out.println("error " + ex);
//            }
//        }
//
//        if (e.getSource() == pr.btnListUsers) {
//            try {
//                pr.cldDesdePagos.setDate(null);
//                pr.cldHastaPagos.setDate(null);
//                clearFormUsers();
//                cargarTblUsers(filtro);
//                showPanel("mnuUsers");
//            } catch (IOException ex) {
//                System.out.println("error " + ex);
//            }
//        }
//
//        if (e.getSource() == pr.btnListMusculos) {
//            cargarTblMusculos("");
//            showPanel("mnuMusculos");
//        }
//
//        if (e.getSource() == pr.btnReporteCaja) {
//            Object[] componentes = {pr.txtUserForReports};
//            resetCampos(componentes);
//            pr.txtUserForReports.setText("");
//            showPanel("pnReportes");
//        }
//
//        if (e.getSource() == pr.btnListEjercicios) {
//            try {
//                cargarMusculos();
//                cargarTblEjercicios("");
//                showPanel("mnuEjercicios");
//            } catch (IOException ex) {
//                System.out.println("error " + ex);
//            }
//        }
//
//        if (e.getSource() == pr.btnAdjuntarfoto || e.getSource() == pr.btnSelectFotoEjercicio) {
//            setCountAction(1);
//            File archivo;
//            if (getCountAction() == 1) {
//                addFilter();
//            }
//            FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//            if (FileChooser.showDialog(null, "Seleccionar Archivo") == JFileChooser.APPROVE_OPTION) {
//                archivo = FileChooser.getSelectedFile();
//                if (archivo.getName().endsWith("png") || archivo.getName().endsWith("jpg")) {
////                    System.out.println("r = "+String.valueOf(archivo));
//                    setFoto(String.valueOf(archivo));
//                    //-----------------------------//                  
//                    ii = new ImageIcon(archivo.getPath());
//                    Image conver = ii.getImage();
//                    Image tam = conver.getScaledInstance(pr.cLabel1.getWidth(), pr.cLabel1.getHeight(), Image.SCALE_SMOOTH);
//                    iin = new ImageIcon(tam);
//                    if (e.getSource() == pr.btnAdjuntarfoto) {
//                        pr.cLabel1.setIcon(iin);
//                        String NombreArchivo = FileChooser.getName(archivo);
//                        pr.fileChosed.setText(String.valueOf(NombreArchivo));
//                    } else {
//                        pr.lblImgEjercicioSelected.setIcon(iin);
//                    }
//                    //-----------------------//                   
//                } else {
//                    JOptionPane.showMessageDialog(null, "Elija un formato valido");
//                }
//            }
//        }
//
//        if (e.getSource() == pr.btnCapturePhoto) {
//            FrmCapturePict wc = new FrmCapturePict();
//            wc.run();
//        }
//
//        if (e.getSource() == pr.btnAsistencias) {
//            if (cf != null) {
//                cf.stop();
//                setCf(null);
//            }
//            if (pr.onOff.getText().equals("ON")) {
//                pr.onOff.setBackground(new Color(255, 0, 0));
//                pr.onOff.setText("OFF");
//                if (rf != null) {
//                    rf.stop();
//                    setRf(null);
//                } else {
//                    getRf();
//                    rf.start();
//                }
//            } else {
//                pr.onOff.setBackground(new Color(22, 204, 119));
//                pr.onOff.setText("ON");
//                getRf();
//                rf.start();
//            }
//        }
//
//        if (e.getSource() == pr.btnAsistenciaManual) {
//            cargarTblAsistencias(null, null);
//            showPanel("mnuAsistencias");
//        }
//
//        //asociar huellas a usuario
//        if (e.getSource() == pr.mnuAsocFinger) {
//            if (pr.onOff.getText().equals("ON")) {
//                pr.onOff.setBackground(new Color(255, 0, 0));
//                pr.onOff.setText("OFF");
//            }
//            if (rf != null) {
//                rf.stop();
//                setRf(null);
//            }
//            int fila = pr.tblUsers.getSelectedRow();
//            if (fila >= 0) {
//                try {
//                    int id_persona = Integer.parseInt(pr.tblUsers.getValueAt(fila, 0).toString());
//                    RolxUser per = getRuxuser().getDatosPersonaById(id_persona);
//                    getCf();
//                    cf.setIdPersona(id_persona);
//                    cf.setIdUsuario(per.getObjUsuario().getObjUsuariosID().getIdUsuario());
//                    cf.setUsuario(per.getObjUsuario().getObjUsuariosID().getUsuario());
//                    cf.setIdSede(per.getObjUsuario().getObjUsuariosID().getIdSede());
//                    cf.setIdempresa(per.getObjUsuario().getObjUsuariosID().getIdempresa());
//                    cf.start();
//                    pr.lblEstadohuellas.setText("Muestra de Huellas Necesarias para Guardar 4");
//                    ii = new javax.swing.ImageIcon(getClass().getResource("/icons/finger2.png"));
//                    Image conver = ii.getImage();
//                    Image tam = conver.getScaledInstance(230, 280, Image.SCALE_SMOOTH);
//                    iin = new ImageIcon(tam);
//                    pr.lblImagenHuella.setIcon(iin);
//                    cargarTblUsers(filtro);
//                    clearFormUsers();
//                    showPanel("mnuAsocFinger");
//                } catch (IOException ex) {
//                    System.out.println("error " + ex);
//                }
//            } else {
//                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
//            }
//        }
//
//        if (e.getSource() == pr.mnuUpdate) {
//            clearFormUsers();
//            int fila = pr.tblUsers.getSelectedRow();
//            if (fila >= 0) {
//                try {
//                    int id_persona = Integer.parseInt(pr.tblUsers.getValueAt(fila, 0).toString());
//                    RolxUser p = getRuxuser().getDatosPersonaById(id_persona);
//                    pr.btnGuardar.setText("Actualizar");
//                    for (int i = 0; i < pr.cboGym.getItemCount(); i++) {
//                        if (pr.cboGym.getItemAt(i).toString().equals(getEmp().getEmpresaById(p.getObjUsuario().getObjUsuariosID().getIdempresa()).toString())) {
//                            pr.cboGym.setSelectedIndex(i);
//                        }
//                    }
//                    for (int i = 0; i < pr.cboSedes.getItemCount(); i++) {
//                        if (pr.cboSedes.getItemAt(i).toString().equals(getSede().getSedeById(p.getObjUsuario().getObjUsuariosID().getIdSede()).toString())) {
//                            pr.cboSedes.setSelectedIndex(i);
//                        }
//                    }
//                    for (int i = 0; i < pr.cboTiposDoc.getItemCount(); i++) {
//                        if (pr.cboTiposDoc.getItemAt(i).toString().equals(getTd().getTipoDocById(p.getObjUsuario().getObjPersona().getIdtipoDocumento()).toString())) {
//                            pr.cboTiposDoc.setSelectedIndex(i);
//                        }
//                    }
//                    for (int i = 0; i < pr.cboRol.getItemCount(); i++) {
//                        if (pr.cboRol.getItemAt(i).toString().equals(getRd().getRolbyId(getRuxuser().getidRolbyIpersona(id_persona)).toString())) {
//                            pr.cboRol.setSelectedIndex(i);
//                        }
//                    }
//                    pr.cboSexo.setSelectedItem(p.getObjUsuario().getObjPersona().getSexo());
//                    pr.txtDoc.setText(p.getObjUsuario().getObjPersona().getDocumento());
//                    pr.txtDireccion.setText(p.getObjUsuario().getObjPersona().getDireccion());
//                    pr.txtNombres.setText(p.getObjUsuario().getObjPersona().getNombre());
//                    pr.txtApellidos.setText(p.getObjUsuario().getObjPersona().getApellido());
//                    pr.txtTelefonos.setText(p.getObjUsuario().getObjPersona().getTelefono());
//                    pr.cldNacimiento.setDate(p.getObjUsuario().getObjPersona().getFechaNacimiento());
//                    pr.txtCorreo.setText(p.getObjUsuario().getObjPersona().getCorreo());
//                    pr.txtUser.setText(p.getObjUsuario().getObjUsuariosID().getUsuario());
//                    pr.txtClave.setText(p.getObjUsuario().getClave());
//                    pr.idpersonaOld.setText(Integer.toString(id_persona));
//                    pr.idUsuarioOld.setText(Integer.toString(p.getObjUsuario().getObjUsuariosID().getIdUsuario()));
//                    pr.idRolxuserOld.setText(Integer.toString(p.getIdRolxUser()));
//                    pr.idEmpresaOld.setText(Integer.toString(p.getObjUsuario().getObjUsuariosID().getIdempresa()));
//                    pr.idSedeOld.setText(Integer.toString(p.getObjUsuario().getObjUsuariosID().getIdSede()));
//                    pr.usuarioOld.setText(p.getObjUsuario().getObjUsuariosID().getUsuario());
//                    pr.idRolOld.setText(Integer.toString(p.getObjRol().getIdRol()));
//                    InputStream img = p.getObjUsuario().getObjPersona().getFoto();
//                    if (img != null) {
//                        BufferedImage bi = ImageIO.read(img);
//                        ii = new ImageIcon(bi);
//                        Image conver = ii.getImage();
//                        Image tam = conver.getScaledInstance(pr.cLabel1.getWidth(), pr.cLabel1.getHeight(), Image.SCALE_SMOOTH);
//                        iin = new ImageIcon(tam);
//                        pr.cLabel1.setIcon(iin);
//                    } else {
//                        pr.cLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user40.png")));
//                    }
//                } catch (IOException ex) {
//                    System.out.println("error " + ex);
//                }
//            } else {
//                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
//            }
//        }
//
//        if (e.getSource() == pr.mnuDelete) {
//            int fila = pr.tblUsers.getSelectedRow();
//            if (fila >= 0) {
//                Object[] opciones = {"Si", "No"};
//                int eleccion = JOptionPane.showOptionDialog(null, "¿En realidad, desea eliminar el usuario?", "Mensaje de Confirmación",
//                        JOptionPane.YES_NO_OPTION,
//                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Si");
//                if (eleccion == JOptionPane.YES_OPTION) {
//                    try {
//                        int id_persona = Integer.parseInt(pr.tblUsers.getValueAt(fila, 0).toString());
//                        RolxUser p = getRuxuser().getDatosPersonaById(id_persona);
//                        if (p.remove() > 0) {
//                            DesktopNotify.showDesktopMessage("Informacion..!", "Usuario Eliminado con exito", DesktopNotify.SUCCESS, 6000L);
//                            cargarTblUsers(filtro);
//                        } else {
//                            DesktopNotify.showDesktopMessage("Aviso..!", "Ocurrio un error al eliminar el usuario", DesktopNotify.ERROR, 5000L);
//                        }
//                    } catch (IOException ex) {
//                        System.out.println("error " + ex);
//                    }
//                }
//            } else {
//                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
//            }
//        }
//
//        if (e.getSource() == pr.mnuDeleteEjercicio) {
//            int fila = pr.tblEjercicios.getSelectedRow();
//            if (fila >= 0) {
//                Object[] opciones = {"Si", "No"};
//                int eleccion = JOptionPane.showOptionDialog(null, "¿En realidad, desea eliminar el ejercicio?", "Mensaje de Confirmación",
//                        JOptionPane.YES_NO_OPTION,
//                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Si");
//                if (eleccion == JOptionPane.YES_OPTION) {
//                    try {
//                        Ejercicios objEjercicio = getEjercicio();
//                        objEjercicio.getObjEjerciciosID().setIdEjercicio(Integer.parseInt(pr.tblEjercicios.getValueAt(fila, 0).toString()));
//                        objEjercicio.getObjEjerciciosID().setIdMusculo(Integer.parseInt(pr.tblEjercicios.getValueAt(fila, 4).toString()));
//                        if (objEjercicio.remove() > 0) {
//                            DesktopNotify.showDesktopMessage("Informacion..!", "Ejercicio Eliminado con exito", DesktopNotify.SUCCESS, 6000L);
//                            cargarTblEjercicios("");
//                            setEjercicio(null);
//                        } else {
//                            DesktopNotify.showDesktopMessage("Aviso..!", "Ocurrio un error al eliminar el Ejercicio", DesktopNotify.ERROR, 5000L);
//                        }
//                    } catch (IOException ex) {
//                        DesktopNotify.showDesktopMessage("Error..!", ex.toString(), DesktopNotify.ERROR, 5000L);
//                    }
//                }
//            } else {
//                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
//            }
//        }
//
//        if (e.getSource() == pr.btnGuardar) {
//            String msnok = null;
//            String msnerror = null;
//            try {
//                Object[] componentes = {pr.cboTiposDoc, pr.cboRol, pr.txtDoc, pr.txtDireccion,
//                    pr.txtNombres, pr.txtApellidos, pr.txtTelefonos, pr.cboGym, pr.cboSedes,
//                    pr.cldNacimiento, pr.cboSexo,
//                    pr.txtUser, pr.txtClave};
//                if (validarCampos(componentes, "") == 0) {
//                    TipoDocumento t = (TipoDocumento) pr.cboTiposDoc.getSelectedItem();
//                    Rol r = (Rol) pr.cboRol.getSelectedItem();
//                    Empresa emp = (Empresa) pr.cboGym.getSelectedItem();
//                    Sedes sede = (Sedes) pr.cboSedes.getSelectedItem();
//                    persona p = getP();
//                    UsuarioID uid = getUid();
//                    Usuario us = getUs();
//                    RolxUser roluser = getRuxuser();
//                    uid.setUsuario(pr.txtUser.getText());
//                    uid.setIdSede(sede.getObjSedesID().getIdSede());
//                    uid.setIdempresa(emp.getIdempresa());
//                    p.setDocumento(pr.txtDoc.getText());
//                    p.setIdtipoDocumento(t.getIdTipoDocumento());
//                    p.setNombre(pr.txtNombres.getText());
//                    p.setApellido(pr.txtApellidos.getText());
//                    p.setNombreCompleto(pr.txtNombres.getText() + " " + pr.txtApellidos.getText());
//                    p.setDireccion(pr.txtDireccion.getText());
//                    p.setTelefono(pr.txtTelefonos.getText());
//                    p.setSexo((String) pr.cboSexo.getSelectedItem());
//                    p.setFechaNacimiento(pr.cldNacimiento.getDate());
//                    p.setCorreo(pr.txtCorreo.getText());
//                    p.setEstado("A");
//                    p.setPathFoto(getFoto());
//                    us.setObjUsuariosID(uid);
//                    us.setEstado("A");
//                    us.setClave(new String(pr.txtClave.getPassword()));
//                    us.setNickName(pr.txtNombres.getText());
//                    us.setObjPersona(p);
//                    roluser.setObjUsuario(us);
//                    roluser.setObjRol(r);
//                    int response = 0;
//                    switch (pr.btnGuardar.getText()) {
//                        case "Guardar":
//                            msnok = "Usuario Creado Con Exito.";
//                            msnerror = "Error al crear el usuario";
//                            response = roluser.create();
//                            break;
//                        case "Actualizar":
//                            msnok = "Usuario Actualizado Con Exito.";
//                            msnerror = "Error al actualizar el usuario";
//                            roluser.setIdpersonaOld(Integer.parseInt(pr.idpersonaOld.getText()));
//                            roluser.setIdUsuarioOld(Integer.parseInt(pr.idUsuarioOld.getText()));
//                            roluser.setIdRolxUserOld(Integer.parseInt(pr.idRolxuserOld.getText()));
//                            roluser.setUsuarioOld(pr.usuarioOld.getText());
//                            roluser.setIdEmpresaOld(Integer.parseInt(pr.idEmpresaOld.getText()));
//                            roluser.setIdsedeOld(Integer.parseInt(pr.idSedeOld.getText()));
//                            roluser.setIdRolOld(Integer.parseInt(pr.idRolOld.getText()));
//                            response = roluser.edit();
//                            break;
//                    }
//                    if (response > 0) {
//                        //crear usuario
//                        DesktopNotify.showDesktopMessage("Informacion..!", msnok, DesktopNotify.SUCCESS, 6000L);
//                        clearFormUsers();
//                    } else {
//                        DesktopNotify.showDesktopMessage("Informacion..!", msnerror, DesktopNotify.ERROR, 6000L);
//                    }
//                    setP(null);
//                    setUid(null);
//                    setUs(null);
//                    setRuxser(null);
//                    cargarTblUsers(filtro);
//                    borrarImagenTemp();
//                } else {
//                    DesktopNotify.showDesktopMessage("Informacion..!", "Los campos marcados en rojo son obligatorios..!", DesktopNotify.ERROR, 6000L);
//
//                }
//            } catch (IOException ex) {
//                System.out.println("error " + ex);
//            }
//
//        }
//
//        if (e.getSource() == pr.btnCancelarEjercicio) {
//            try {
//                Object[] componentes = {pr.txtNomEjercicio, pr.cboMusculos};
//                pr.txtNomEjercicio.setText("");
//                pr.cboMusculos.setSelectedIndex(0);
//                pr.lblImgEjercicioSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Treadmill_28px.png")));
//                resetCampos(componentes);
//                cargarTblEjercicios("");
//            } catch (IOException ex) {
//                System.out.println("error linea 834 " + ex);
//            }
//        }
//
//        if (e.getSource() == pr.btnGuardarEjercicio) {
//            String msnok = null;
//            String msnerror = null;
//            try {
//                Object[] componentes = {pr.txtNomEjercicio, pr.cboMusculos};
//                if (validarCampos(componentes, "cboMusculos") == 0) {
//                    Musculos musculo = (Musculos) pr.cboMusculos.getSelectedItem();
//                    Ejercicios ej = getEjercicio();
//                    ej.getObjEjerciciosID().setIdMusculo(musculo.getIdMusculo());
//                    ej.setDescripcion(pr.txtNomEjercicio.getText());
//                    ej.setPathImagen(getFoto());
//                    int response = 0;
//                    switch (pr.btnGuardarEjercicio.getText()) {
//                        case "Guardar":
//                            msnok = "Ejercicio Creado Con Exito.";
//                            msnerror = "Error al crear el ejercicio";
//                            response = ej.create();
//                            break;
//                        case "Actualizar":
//                            msnok = "Ejercicio Actualizado Con Exito.";
//                            msnerror = "Error al actualizar el ejercicio";
//                            ej.getObjEjerciciosID().setIdEjercicio(Integer.parseInt(pr.idEjercicioUpdate.getText()));
//                            response = ej.edit();
//                            break;
//                    }
//                    if (response > 0) {
//                        DesktopNotify.showDesktopMessage("Informacion..!", msnok, DesktopNotify.SUCCESS, 6000L);
//                        pr.txtNomEjercicio.setText("");
//                        pr.cboMusculos.setSelectedIndex(0);
//                        pr.lblImgEjercicioSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Treadmill_28px.png")));
//                        setFoto("");
//                    } else {
//                        DesktopNotify.showDesktopMessage("Informacion..!", msnerror, DesktopNotify.ERROR, 6000L);
//                    }
//                    setEjercicio(null);
//                    ej = null;
//                    cargarTblEjercicios("");
//                } else {
//                    DesktopNotify.showDesktopMessage("Informacion..!", "Los campos marcados en rojo son obligatorios..!", DesktopNotify.ERROR, 6000L);
//
//                }
//            } catch (IOException ex) {
//                System.out.println("error " + ex);
//            }
//        }
//
//        if (e.getSource() == pr.btnGuardarMusculo) {
//            String msnok = null;
//            String msnerror = null;
//            int response = 0;
//            try {
//                Object[] componentes = {pr.txtNomMusculo};
//                if (validarCampos(componentes, "") == 0) {
//                    Musculos m = getMusculos();
//                    m.setdescripcion(pr.txtNomMusculo.getText());
//                    m.setEstado("A");
//                    switch (pr.btnGuardarMusculo.getText()) {
//                        case "Guardar":
//                            msnok = "Musculo Creado Con Exito.";
//                            msnerror = "Error al crear el Musculo";
//                            response = m.create();
//                            break;
//                        case "Actualizar":
//                            msnok = "Musculo Actualizado Con Exito.";
//                            msnerror = "Error al actualizar el Musculo";
//                            m.setIdMusculo(Integer.parseInt(pr.idMusculoUpdate.getText()));
//                            response = m.edit();
//                            break;
//                    }
//                    if (response > 0) {
//                        DesktopNotify.showDesktopMessage("Informacion..!", msnok, DesktopNotify.SUCCESS, 6000L);
//                        pr.txtNomMusculo.setText("");
//                        pr.idMusculoUpdate.setText("");
//                        pr.btnGuardarMusculo.setText("Guardar");
//                        resetCampos(componentes);
//                        setMusculos(null);
//                        cargarTblMusculos("");
//                    } else {
//                        DesktopNotify.showDesktopMessage("Informacion..!", msnerror, DesktopNotify.ERROR, 6000L);
//                    }
//                } else {
//                    DesktopNotify.showDesktopMessage("Informacion..!", "El campo Nombre de Musculo es obligatorio..!", DesktopNotify.ERROR, 6000L);
//                }
//            } catch (NumberFormatException ex) {
//                DesktopNotify.showDesktopMessage("Error..!", ex.toString(), DesktopNotify.FAIL, 6000L);
//            }
//        }
//
//        if (e.getSource() == pr.mnuUpdateMusculo) {
//            int fila = pr.tblMusculos.getSelectedRow();
//            if (fila >= 0) {
//                pr.btnGuardarMusculo.setText("Actualizar");
//                pr.idMusculoUpdate.setText(pr.tblMusculos.getValueAt(fila, 0).toString());
//                pr.txtNomMusculo.setText(pr.tblMusculos.getValueAt(fila, 1).toString());
//            } else {
//                DesktopNotify.showDesktopMessage("Informacion..!", "No has seleccionado un registro..!", DesktopNotify.ERROR, 6000L);
//            }
//        }
//
//        if (e.getSource() == pr.mnuUpdateEjercicio) {
//            int fila = pr.tblEjercicios.getSelectedRow();
//            if (fila >= 0) {
//                try {
//                    Ejercicios ej = getEjercicio().getEjercicioById(Integer.parseInt(pr.tblEjercicios.getValueAt(fila, 0).toString()));
//                    pr.btnGuardarEjercicio.setText("Actualizar");
//                    pr.txtNomEjercicio.setText(ej.getDescripcion());
//                    pr.idEjercicioUpdate.setText(pr.tblEjercicios.getValueAt(fila, 0).toString());
//                    for (int i = 0; i < pr.cboMusculos.getItemCount(); i++) {
//                        if (pr.cboMusculos.getItemAt(i).toString().equals(pr.tblEjercicios.getValueAt(fila, 2).toString())) {
//                            pr.cboMusculos.setSelectedIndex(i);
//                        }
//                    }
//                    InputStream img = ej.getImagen();
//                    if (img != null) {
//                        BufferedImage bi = ImageIO.read(img);
//                        ii = new ImageIcon(bi);
//                        Image conver = ii.getImage();
//                        Image tam = conver.getScaledInstance(pr.cLabel1.getWidth(), pr.cLabel1.getHeight(), Image.SCALE_SMOOTH);
//                        iin = new ImageIcon(tam);
//                        pr.lblImgEjercicioSelected.setIcon(iin);
//                    } else {
//                        pr.lblImgEjercicioSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Treadmill_28px.png")));
//                    }
//                    setEjercicio(null);
//                    ej = null;
//                } catch (IOException ex) {
//                    System.out.println("error al cargar la imagen " + ex);
//                }
//            } else {
//                DesktopNotify.showDesktopMessage("Informacion..!", "No has seleccionado un registro..!", DesktopNotify.ERROR, 6000L);
//            }
//        }
//
//        if (e.getSource() == pr.mnuDeleteMusculo) {
//            int fila = pr.tblMusculos.getSelectedRow();
//            if (fila >= 0) {
//                Object[] opciones = {"Si", "No"};
//                int eleccion = JOptionPane.showOptionDialog(null, "¿En realidad, desea eliminar el musculo?", "Mensaje de Confirmación",
//                        JOptionPane.YES_NO_OPTION,
//                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Si");
//                if (eleccion == JOptionPane.YES_OPTION) {
//                    int idMusculo = Integer.parseInt(pr.tblMusculos.getValueAt(fila, 0).toString());
//                    Musculos m = getMusculos();
//                    m.setIdMusculo(idMusculo);
//                    int response = m.remove();
//                    if (response == 0) {
//                        DesktopNotify.showDesktopMessage("Informacion..!", "El musculo esta asociado a una rutina o a una medida, no se puede eliminar", DesktopNotify.SUCCESS, 6000L);
//                    } else if (response > 0) {
//                        DesktopNotify.showDesktopMessage("Informacion..!", "Musculo Eliminado con exito", DesktopNotify.SUCCESS, 6000L);
//                        cargarTblMusculos("");
//                    } else {
//                        DesktopNotify.showDesktopMessage("Aviso..!", "Ocurrio un error al eliminar el Musculo", DesktopNotify.ERROR, 5000L);
//                    }
//                }
//            } else {
//                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
//            }
//        }
//
//        if (e.getSource() == pr.btnCancelarMusculo) {
//            pr.txtNomMusculo.setText("");
//            pr.idMusculoUpdate.setText("");
//            Object[] componentes = {pr.txtNomMusculo};
//            resetCampos(componentes);
//        }
//
//        if (e.getSource() == pr.btnGuardaAsistencia) {
//            Object[] componentes = {pr.txtDocAsist};
//            if (validarCampos(componentes, "") == 0) {
//                RolxUser persona = getRuxuser().getDatosPersonaByDoc(pr.txtDocAsist.getText());
//                if (persona != null) {
//                    Asistencia a = getAd();
//                    a.getObjAsistenciaID().setIdPersona(persona.getObjUsuario().getObjPersona().getIdPersona());
//                    a.getObjAsistenciaID().setIdSede(persona.getObjUsuario().getObjUsuariosID().getIdSede());
//                    a.getObjAsistenciaID().setIdempresa(persona.getObjUsuario().getObjUsuariosID().getIdempresa());
//                    a.getObjAsistenciaID().setUsuario(persona.getObjUsuario().getObjUsuariosID().getUsuario());
//                    a.getObjAsistenciaID().setIdUsuario(persona.getObjUsuario().getObjUsuariosID().getIdUsuario());
//                    a.setFechaMarcacion(new Date());
//                    a.setHoraMarcacion(new Date());
//                    if (a.create() > 0) {
//                        DesktopNotify.showDesktopMessage("Información..!", "Asistencia generada..!", DesktopNotify.SUCCESS, 6000L);
//                        pr.txtDocAsist.setText("");
//                    } else {
//                        DesktopNotify.showDesktopMessage("Información..!", "Error Al Guardar La Asistencia\n"
//                                + "Es posible que el número de documento ingresado no exista o sea incorrecto\n"
//                                + "Si el error continúa contactese con soporte técnico..!", DesktopNotify.FAIL, 6000L);
//                        pr.txtDocAsist.requestFocus();
//                    }
//                } else {
//                    DesktopNotify.showDesktopMessage("Aviso..!", "El número de documento ingresado no existe..!", DesktopNotify.FAIL, 6000L);
//                    pr.txtDocAsist.requestFocus();
//                }
//                setAd(null);
//                setRuxser(null);
//                cargarTblAsistencias(null, null);
//            } else {
//                DesktopNotify.showDesktopMessage("Informacion..!", "El campo Documento es obligatorio..!", DesktopNotify.WARNING, 6000L);
//                pr.txtDocAsist.requestFocus();
//            }
//        }
//
//        if (e.getSource() == pr.btnBuscarAsistencias) {
//            Object[] componentes = {pr.cldInicio, pr.cldFin};
//            if (validarCampos(componentes, "") == 0) {
//                cargarTblAsistencias(pr.cldInicio.getDate(), pr.cldFin.getDate());
//            } else {
//                DesktopNotify.showDesktopMessage("Informacion..!", "El rango de fechas es obligatorio..!", DesktopNotify.WARNING, 6000L);
//                pr.cldInicio.requestFocus();
//            }
//
//        }
//
//        if (e.getSource() == pr.btnCancelar) {
//            clearFormUsers();
//        }
//
//        if (e.getSource() == pr.btnPrimerReg) {
//            try {
//                setPrimero();
//                cargarTblUsers(filtro);
//            } catch (IOException ex) {
//                System.out.println("error " + ex);
//            }
//        }
//
//        if (e.getSource() == pr.btnMenosReg) {
//            try {
//                setMenos();
//                cargarTblUsers(filtro);
//            } catch (IOException ex) {
//                System.out.println("error " + ex);
//            }
//        }
//
//        if (e.getSource() == pr.BtnGuardarRutina) {
//            Object[] componentes = {pr.txtDescRutina, pr.cboDia, pr.cboMusculos2
//            };
//            if (validarCampos(componentes, "cboMusculos2") == 0) {
//                Rutina rut = getRutina();
//                rut.setDescripcion(pr.txtDescRutina.getText());
//                rut.setFechaCreacion(new Date());
//                rut.setEstado("A");
//                rut.setNewRutina(newRutina);
//                rut.create();
//            } else {
//                DesktopNotify.showDesktopMessage("Informacion..!", "Los campos marcados en rojo son obligatorios..!", DesktopNotify.ERROR, 6000L);
//            }
//        }
//
//        if (e.getSource() == pr.btnSiguienteReg) {
//            try {
//                setMas();
//                cargarTblUsers(filtro);
//            } catch (IOException ex) {
//                System.out.println("error " + ex);
//            }
//        }
//
//        if (e.getSource() == pr.btnLastReg) {
//            try {
//                setUltimo();
//                cargarTblUsers(filtro);
//            } catch (IOException ex) {
//                System.out.println("error " + ex);
//            }
//        }
//
//        if (e.getSource() == pr.btnBack) {
//            if (pr.onOff.getText().equals("OFF")) {
//                pr.onOff.setBackground(new Color(255, 0, 0));
//                pr.onOff.setText("OFF");
//            } else {
//                pr.onOff.setBackground(new Color(22, 204, 119));
//                pr.onOff.setText("ON");
//            }
//            getCf().stop();
//            setCf(null);
//            showPanel("mnuUsers");
//            pr.txtArea.setText("");
//        }
//
//        if (e.getSource() == pr.btnFindUser) {
//            try {
//                desde = 0;
//                hasta = 10;
//                cantidadregistros = 10;
//                currentpage = 1;
//                filtro = pr.txtFindUser.getText();
//                if (filtro.equals("")) {
//                    pr.btnSiguienteReg.removeActionListener(this);
//                    pr.btnLastReg.removeActionListener(this);
//                    pr.btnMenosReg.removeActionListener(this);
//                    pr.btnPrimerReg.removeActionListener(this);
//                    pr.btnLastReg.addActionListener(this);
//                    pr.btnSiguienteReg.addActionListener(this);
//                    pr.btnPrimerReg.setEnabled(false);
//                    pr.btnMenosReg.setEnabled(false);
//                    pr.btnLastReg.setEnabled(true);
//                    pr.btnSiguienteReg.setEnabled(true);
//                } else {
//                    opcPaginacion = "";
//                    inhabilitarPaginacion();
//                }
//                cargarTblUsers(filtro);
//            } catch (IOException ex) {
//                System.out.println("error " + ex);
//            }
//        }
//
//        if (e.getSource() == pr.btnFindMusculo) {
//            cargarTblMusculos(pr.txtFindMusculo.getText());
//        }
//
//        if (e.getSource() == pr.btnGenerarReporteByTipo) {
//            generarReportes();
//        }
        //        if (e.getSource() == pr.mnuUpdate) {
        //            clearFormUsers();
        //            int fila = pr.tblUsers.getSelectedRow();
        //            if (fila >= 0) {
        //                try {
        //                    int id_persona = Integer.parseInt(pr.tblUsers.getValueAt(fila, 0).toString());
        //                    RolxUser p = getRuxuser().getDatosPersonaById(id_persona);
        //                    pr.btnGuardar.setText("Actualizar");
        //                    for (int i = 0; i < pr.cboGym.getItemCount(); i++) {
        //                        if (pr.cboGym.getItemAt(i).toString().equals(getEmp().getEmpresaById(p.getObjUsuario().getObjUsuariosID().getIdempresa()).toString())) {
        //                            pr.cboGym.setSelectedIndex(i);
        //                        }
        //                    }
        //                    for (int i = 0; i < pr.cboSedes.getItemCount(); i++) {
        //                        if (pr.cboSedes.getItemAt(i).toString().equals(getSede().getSedeById(p.getObjUsuario().getObjUsuariosID().getIdSede()).toString())) {
        //                            pr.cboSedes.setSelectedIndex(i);
        //                        }
        //                    }
        //                    for (int i = 0; i < pr.cboTiposDoc.getItemCount(); i++) {
        //                        if (pr.cboTiposDoc.getItemAt(i).toString().equals(getTd().getTipoDocById(p.getObjUsuario().getObjPersona().getIdtipoDocumento()).toString())) {
        //                            pr.cboTiposDoc.setSelectedIndex(i);
        //                        }
        //                    }
        //                    for (int i = 0; i < pr.cboRol.getItemCount(); i++) {
        //                        if (pr.cboRol.getItemAt(i).toString().equals(getRd().getRolbyId(getRuxuser().getidRolbyIpersona(id_persona)).toString())) {
        //                            pr.cboRol.setSelectedIndex(i);
        //                        }
        //                    }
        //                    pr.cboSexo.setSelectedItem(p.getObjUsuario().getObjPersona().getSexo());
        //                    pr.txtDoc.setText(p.getObjUsuario().getObjPersona().getDocumento());
        //                    pr.txtDireccion.setText(p.getObjUsuario().getObjPersona().getDireccion());
        //                    pr.txtNombres.setText(p.getObjUsuario().getObjPersona().getNombre());
        //                    pr.txtApellidos.setText(p.getObjUsuario().getObjPersona().getApellido());
        //                    pr.txtTelefonos.setText(p.getObjUsuario().getObjPersona().getTelefono());
        //                    pr.cldNacimiento.setDate(p.getObjUsuario().getObjPersona().getFechaNacimiento());
        //                    pr.txtCorreo.setText(p.getObjUsuario().getObjPersona().getCorreo());
        //                    pr.txtUser.setText(p.getObjUsuario().getObjUsuariosID().getUsuario());
        //                    pr.txtClave.setText(p.getObjUsuario().getClave());
        //                    pr.idpersonaOld.setText(Integer.toString(id_persona));
        //                    pr.idUsuarioOld.setText(Integer.toString(p.getObjUsuario().getObjUsuariosID().getIdUsuario()));
        //                    pr.idRolxuserOld.setText(Integer.toString(p.getIdRolxUser()));
        //                    pr.idEmpresaOld.setText(Integer.toString(p.getObjUsuario().getObjUsuariosID().getIdempresa()));
        //                    pr.idSedeOld.setText(Integer.toString(p.getObjUsuario().getObjUsuariosID().getIdSede()));
        //                    pr.usuarioOld.setText(p.getObjUsuario().getObjUsuariosID().getUsuario());
        //                    pr.idRolOld.setText(Integer.toString(p.getObjRol().getIdRol()));
        //                    InputStream img = p.getObjUsuario().getObjPersona().getFoto();
        //                    if (img != null) {
        //                        BufferedImage bi = ImageIO.read(img);
        //                        ii = new ImageIcon(bi);
        //                        Image conver = ii.getImage();
        //                        Image tam = conver.getScaledInstance(pr.cLabel1.getWidth(), pr.cLabel1.getHeight(), Image.SCALE_SMOOTH);
        //                        iin = new ImageIcon(tam);
        //                        pr.cLabel1.setIcon(iin);
        //                    } else {
        //                        pr.cLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user40.png")));
        //                    }
        //                } catch (IOException ex) {
        //                    System.out.println("error " + ex);
        //                }
        //            } else {
        //                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.mnuDelete) {
        //            int fila = pr.tblUsers.getSelectedRow();
        //            if (fila >= 0) {
        //                Object[] opciones = {"Si", "No"};
        //                int eleccion = JOptionPane.showOptionDialog(null, "¿En realidad, desea eliminar el usuario?", "Mensaje de Confirmación",
        //                        JOptionPane.YES_NO_OPTION,
        //                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Si");
        //                if (eleccion == JOptionPane.YES_OPTION) {
        //                    try {
        //                        int id_persona = Integer.parseInt(pr.tblUsers.getValueAt(fila, 0).toString());
        //                        RolxUser p = getRuxuser().getDatosPersonaById(id_persona);
        //                        if (p.remove() > 0) {
        //                            DesktopNotify.showDesktopMessage("Informacion..!", "Usuario Eliminado con exito", DesktopNotify.SUCCESS, 6000L);
        //                            cargarTblUsers(filtro);
        //                        } else {
        //                            DesktopNotify.showDesktopMessage("Aviso..!", "Ocurrio un error al eliminar el usuario", DesktopNotify.ERROR, 5000L);
        //                        }
        //                    } catch (IOException ex) {
        //                        System.out.println("error " + ex);
        //                    }
        //                }
        //            } else {
        //                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.mnuDeleteEjercicio) {
        //            int fila = pr.tblEjercicios.getSelectedRow();
        //            if (fila >= 0) {
        //                Object[] opciones = {"Si", "No"};
        //                int eleccion = JOptionPane.showOptionDialog(null, "¿En realidad, desea eliminar el ejercicio?", "Mensaje de Confirmación",
        //                        JOptionPane.YES_NO_OPTION,
        //                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Si");
        //                if (eleccion == JOptionPane.YES_OPTION) {
        //                    try {
        //                        Ejercicios objEjercicio = getEjercicio();
        //                        objEjercicio.getObjEjerciciosID().setIdEjercicio(Integer.parseInt(pr.tblEjercicios.getValueAt(fila, 0).toString()));
        //                        objEjercicio.getObjEjerciciosID().setIdMusculo(Integer.parseInt(pr.tblEjercicios.getValueAt(fila, 4).toString()));
        //                        if (objEjercicio.remove() > 0) {
        //                            DesktopNotify.showDesktopMessage("Informacion..!", "Ejercicio Eliminado con exito", DesktopNotify.SUCCESS, 6000L);
        //                            cargarTblEjercicios("");
        //                            setEjercicio(null);
        //                        } else {
        //                            DesktopNotify.showDesktopMessage("Aviso..!", "Ocurrio un error al eliminar el Ejercicio", DesktopNotify.ERROR, 5000L);
        //                        }
        //                    } catch (IOException ex) {
        //                        DesktopNotify.showDesktopMessage("Error..!", ex.toString(), DesktopNotify.ERROR, 5000L);
        //                    }
        //                }
        //            } else {
        //                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.btnGuardar) {
        //            String msnok = null;
        //            String msnerror = null;
        //            try {
        //                Object[] componentes = {pr.cboTiposDoc, pr.cboRol, pr.txtDoc, pr.txtDireccion,
        //                    pr.txtNombres, pr.txtApellidos, pr.txtTelefonos, pr.cboGym, pr.cboSedes,
        //                    pr.cldNacimiento, pr.cboSexo,
        //                    pr.txtUser, pr.txtClave};
        //                if (validarCampos(componentes, "") == 0) {
        //                    TipoDocumento t = (TipoDocumento) pr.cboTiposDoc.getSelectedItem();
        //                    Rol r = (Rol) pr.cboRol.getSelectedItem();
        //                    Empresa emp = (Empresa) pr.cboGym.getSelectedItem();
        //                    Sedes sede = (Sedes) pr.cboSedes.getSelectedItem();
        //                    persona p = getP();
        //                    UsuarioID uid = getUid();
        //                    Usuario us = getUs();
        //                    RolxUser roluser = getRuxuser();
        //                    uid.setUsuario(pr.txtUser.getText());
        //                    uid.setIdSede(sede.getObjSedesID().getIdSede());
        //                    uid.setIdempresa(emp.getIdempresa());
        //                    p.setDocumento(pr.txtDoc.getText());
        //                    p.setIdtipoDocumento(t.getIdTipoDocumento());
        //                    p.setNombre(pr.txtNombres.getText());
        //                    p.setApellido(pr.txtApellidos.getText());
        //                    p.setNombreCompleto(pr.txtNombres.getText() + " " + pr.txtApellidos.getText());
        //                    p.setDireccion(pr.txtDireccion.getText());
        //                    p.setTelefono(pr.txtTelefonos.getText());
        //                    p.setSexo((String) pr.cboSexo.getSelectedItem());
        //                    p.setFechaNacimiento(pr.cldNacimiento.getDate());
        //                    p.setCorreo(pr.txtCorreo.getText());
        //                    p.setEstado("A");
        //                    p.setPathFoto(getFoto());
        //                    us.setObjUsuariosID(uid);
        //                    us.setEstado("A");
        //                    us.setClave(new String(pr.txtClave.getPassword()));
        //                    us.setNickName(pr.txtNombres.getText());
        //                    us.setObjPersona(p);
        //                    roluser.setObjUsuario(us);
        //                    roluser.setObjRol(r);
        //                    int response = 0;
        //                    switch (pr.btnGuardar.getText()) {
        //                        case "Guardar":
        //                            msnok = "Usuario Creado Con Exito.";
        //                            msnerror = "Error al crear el usuario";
        //                            response = roluser.create();
        //                            break;
        //                        case "Actualizar":
        //                            msnok = "Usuario Actualizado Con Exito.";
        //                            msnerror = "Error al actualizar el usuario";
        //                            roluser.setIdpersonaOld(Integer.parseInt(pr.idpersonaOld.getText()));
        //                            roluser.setIdUsuarioOld(Integer.parseInt(pr.idUsuarioOld.getText()));
        //                            roluser.setIdRolxUserOld(Integer.parseInt(pr.idRolxuserOld.getText()));
        //                            roluser.setUsuarioOld(pr.usuarioOld.getText());
        //                            roluser.setIdEmpresaOld(Integer.parseInt(pr.idEmpresaOld.getText()));
        //                            roluser.setIdsedeOld(Integer.parseInt(pr.idSedeOld.getText()));
        //                            roluser.setIdRolOld(Integer.parseInt(pr.idRolOld.getText()));
        //                            response = roluser.edit();
        //                            break;
        //                    }
        //                    if (response > 0) {
        //                        //crear usuario
        //                        DesktopNotify.showDesktopMessage("Informacion..!", msnok, DesktopNotify.SUCCESS, 6000L);
        //                        clearFormUsers();
        //                    } else {
        //                        DesktopNotify.showDesktopMessage("Informacion..!", msnerror, DesktopNotify.ERROR, 6000L);
        //                    }
        //                    setP(null);
        //                    setUid(null);
        //                    setUs(null);
        //                    setRuxser(null);
        //                    cargarTblUsers(filtro);
        //                    borrarImagenTemp();
        //                } else {
        //                    DesktopNotify.showDesktopMessage("Informacion..!", "Los campos marcados en rojo son obligatorios..!", DesktopNotify.ERROR, 6000L);
        //
        //                }
        //            } catch (IOException ex) {
        //                System.out.println("error " + ex);
        //            }
        //
        //        }
        //
        //        if (e.getSource() == pr.btnCancelarEjercicio) {
        //            try {
        //                Object[] componentes = {pr.txtNomEjercicio, pr.cboMusculos};
        //                pr.txtNomEjercicio.setText("");
        //                pr.cboMusculos.setSelectedIndex(0);
        //                pr.lblImgEjercicioSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Treadmill_28px.png")));
        //                resetCampos(componentes);
        //                cargarTblEjercicios("");
        //            } catch (IOException ex) {
        //                System.out.println("error linea 834 " + ex);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.btnGuardarEjercicio) {
        //            String msnok = null;
        //            String msnerror = null;
        //            try {
        //                Object[] componentes = {pr.txtNomEjercicio, pr.cboMusculos};
        //                if (validarCampos(componentes, "cboMusculos") == 0) {
        //                    Musculos musculo = (Musculos) pr.cboMusculos.getSelectedItem();
        //                    Ejercicios ej = getEjercicio();
        //                    ej.getObjEjerciciosID().setIdMusculo(musculo.getIdMusculo());
        //                    ej.setDescripcion(pr.txtNomEjercicio.getText());
        //                    ej.setPathImagen(getFoto());
        //                    int response = 0;
        //                    switch (pr.btnGuardarEjercicio.getText()) {
        //                        case "Guardar":
        //                            msnok = "Ejercicio Creado Con Exito.";
        //                            msnerror = "Error al crear el ejercicio";
        //                            response = ej.create();
        //                            break;
        //                        case "Actualizar":
        //                            msnok = "Ejercicio Actualizado Con Exito.";
        //                            msnerror = "Error al actualizar el ejercicio";
        //                            ej.getObjEjerciciosID().setIdEjercicio(Integer.parseInt(pr.idEjercicioUpdate.getText()));
        //                            response = ej.edit();
        //                            break;
        //                    }
        //                    if (response > 0) {
        //                        DesktopNotify.showDesktopMessage("Informacion..!", msnok, DesktopNotify.SUCCESS, 6000L);
        //                        pr.txtNomEjercicio.setText("");
        //                        pr.cboMusculos.setSelectedIndex(0);
        //                        pr.lblImgEjercicioSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Treadmill_28px.png")));
        //                        setFoto("");
        //                    } else {
        //                        DesktopNotify.showDesktopMessage("Informacion..!", msnerror, DesktopNotify.ERROR, 6000L);
        //                    }
        //                    setEjercicio(null);
        //                    ej = null;
        //                    cargarTblEjercicios("");
        //                } else {
        //                    DesktopNotify.showDesktopMessage("Informacion..!", "Los campos marcados en rojo son obligatorios..!", DesktopNotify.ERROR, 6000L);
        //
        //                }
        //            } catch (IOException ex) {
        //                System.out.println("error " + ex);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.btnGuardarMusculo) {
        //            String msnok = null;
        //            String msnerror = null;
        //            int response = 0;
        //            try {
        //                Object[] componentes = {pr.txtNomMusculo};
        //                if (validarCampos(componentes, "") == 0) {
        //                    Musculos m = getMusculos();
        //                    m.setdescripcion(pr.txtNomMusculo.getText());
        //                    m.setEstado("A");
        //                    switch (pr.btnGuardarMusculo.getText()) {
        //                        case "Guardar":
        //                            msnok = "Musculo Creado Con Exito.";
        //                            msnerror = "Error al crear el Musculo";
        //                            response = m.create();
        //                            break;
        //                        case "Actualizar":
        //                            msnok = "Musculo Actualizado Con Exito.";
        //                            msnerror = "Error al actualizar el Musculo";
        //                            m.setIdMusculo(Integer.parseInt(pr.idMusculoUpdate.getText()));
        //                            response = m.edit();
        //                            break;
        //                    }
        //                    if (response > 0) {
        //                        DesktopNotify.showDesktopMessage("Informacion..!", msnok, DesktopNotify.SUCCESS, 6000L);
        //                        pr.txtNomMusculo.setText("");
        //                        pr.idMusculoUpdate.setText("");
        //                        pr.btnGuardarMusculo.setText("Guardar");
        //                        resetCampos(componentes);
        //                        setMusculos(null);
        //                        cargarTblMusculos("");
        //                    } else {
        //                        DesktopNotify.showDesktopMessage("Informacion..!", msnerror, DesktopNotify.ERROR, 6000L);
        //                    }
        //                } else {
        //                    DesktopNotify.showDesktopMessage("Informacion..!", "El campo Nombre de Musculo es obligatorio..!", DesktopNotify.ERROR, 6000L);
        //                }
        //            } catch (NumberFormatException ex) {
        //                DesktopNotify.showDesktopMessage("Error..!", ex.toString(), DesktopNotify.FAIL, 6000L);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.mnuUpdateMusculo) {
        //            int fila = pr.tblMusculos.getSelectedRow();
        //            if (fila >= 0) {
        //                pr.btnGuardarMusculo.setText("Actualizar");
        //                pr.idMusculoUpdate.setText(pr.tblMusculos.getValueAt(fila, 0).toString());
        //                pr.txtNomMusculo.setText(pr.tblMusculos.getValueAt(fila, 1).toString());
        //            } else {
        //                DesktopNotify.showDesktopMessage("Informacion..!", "No has seleccionado un registro..!", DesktopNotify.ERROR, 6000L);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.mnuUpdateEjercicio) {
        //            int fila = pr.tblEjercicios.getSelectedRow();
        //            if (fila >= 0) {
        //                try {
        //                    Ejercicios ej = getEjercicio().getEjercicioById(Integer.parseInt(pr.tblEjercicios.getValueAt(fila, 0).toString()));
        //                    pr.btnGuardarEjercicio.setText("Actualizar");
        //                    pr.txtNomEjercicio.setText(ej.getDescripcion());
        //                    pr.idEjercicioUpdate.setText(pr.tblEjercicios.getValueAt(fila, 0).toString());
        //                    for (int i = 0; i < pr.cboMusculos.getItemCount(); i++) {
        //                        if (pr.cboMusculos.getItemAt(i).toString().equals(pr.tblEjercicios.getValueAt(fila, 2).toString())) {
        //                            pr.cboMusculos.setSelectedIndex(i);
        //                        }
        //                    }
        //                    InputStream img = ej.getImagen();
        //                    if (img != null) {
        //                        BufferedImage bi = ImageIO.read(img);
        //                        ii = new ImageIcon(bi);
        //                        Image conver = ii.getImage();
        //                        Image tam = conver.getScaledInstance(pr.cLabel1.getWidth(), pr.cLabel1.getHeight(), Image.SCALE_SMOOTH);
        //                        iin = new ImageIcon(tam);
        //                        pr.lblImgEjercicioSelected.setIcon(iin);
        //                    } else {
        //                        pr.lblImgEjercicioSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Treadmill_28px.png")));
        //                    }
        //                    setEjercicio(null);
        //                    ej = null;
        //                } catch (IOException ex) {
        //                    System.out.println("error al cargar la imagen " + ex);
        //                }
        //            } else {
        //                DesktopNotify.showDesktopMessage("Informacion..!", "No has seleccionado un registro..!", DesktopNotify.ERROR, 6000L);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.mnuDeleteMusculo) {
        //            int fila = pr.tblMusculos.getSelectedRow();
        //            if (fila >= 0) {
        //                Object[] opciones = {"Si", "No"};
        //                int eleccion = JOptionPane.showOptionDialog(null, "¿En realidad, desea eliminar el musculo?", "Mensaje de Confirmación",
        //                        JOptionPane.YES_NO_OPTION,
        //                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Si");
        //                if (eleccion == JOptionPane.YES_OPTION) {
        //                    int idMusculo = Integer.parseInt(pr.tblMusculos.getValueAt(fila, 0).toString());
        //                    Musculos m = getMusculos();
        //                    m.setIdMusculo(idMusculo);
        //                    int response = m.remove();
        //                    if (response == 0) {
        //                        DesktopNotify.showDesktopMessage("Informacion..!", "El musculo esta asociado a una rutina o a una medida, no se puede eliminar", DesktopNotify.SUCCESS, 6000L);
        //                    } else if (response > 0) {
        //                        DesktopNotify.showDesktopMessage("Informacion..!", "Musculo Eliminado con exito", DesktopNotify.SUCCESS, 6000L);
        //                        cargarTblMusculos("");
        //                    } else {
        //                        DesktopNotify.showDesktopMessage("Aviso..!", "Ocurrio un error al eliminar el Musculo", DesktopNotify.ERROR, 5000L);
        //                    }
        //                }
        //            } else {
        //                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.btnCancelarMusculo) {
        //            pr.txtNomMusculo.setText("");
        //            pr.idMusculoUpdate.setText("");
        //            Object[] componentes = {pr.txtNomMusculo};
        //            resetCampos(componentes);
        //        }
        //
        //        if (e.getSource() == pr.btnGuardaAsistencia) {
        //            Object[] componentes = {pr.txtDocAsist};
        //            if (validarCampos(componentes, "") == 0) {
        //                RolxUser persona = getRuxuser().getDatosPersonaByDoc(pr.txtDocAsist.getText());
        //                if (persona != null) {
        //                    Asistencia a = getAd();
        //                    a.getObjAsistenciaID().setIdPersona(persona.getObjUsuario().getObjPersona().getIdPersona());
        //                    a.getObjAsistenciaID().setIdSede(persona.getObjUsuario().getObjUsuariosID().getIdSede());
        //                    a.getObjAsistenciaID().setIdempresa(persona.getObjUsuario().getObjUsuariosID().getIdempresa());
        //                    a.getObjAsistenciaID().setUsuario(persona.getObjUsuario().getObjUsuariosID().getUsuario());
        //                    a.getObjAsistenciaID().setIdUsuario(persona.getObjUsuario().getObjUsuariosID().getIdUsuario());
        //                    a.setFechaMarcacion(new Date());
        //                    a.setHoraMarcacion(new Date());
        //                    if (a.create() > 0) {
        //                        DesktopNotify.showDesktopMessage("Información..!", "Asistencia generada..!", DesktopNotify.SUCCESS, 6000L);
        //                        pr.txtDocAsist.setText("");
        //                    } else {
        //                        DesktopNotify.showDesktopMessage("Información..!", "Error Al Guardar La Asistencia\n"
        //                                + "Es posible que el número de documento ingresado no exista o sea incorrecto\n"
        //                                + "Si el error continúa contactese con soporte técnico..!", DesktopNotify.FAIL, 6000L);
        //                        pr.txtDocAsist.requestFocus();
        //                    }
        //                } else {
        //                    DesktopNotify.showDesktopMessage("Aviso..!", "El número de documento ingresado no existe..!", DesktopNotify.FAIL, 6000L);
        //                    pr.txtDocAsist.requestFocus();
        //                }
        //                setAd(null);
        //                setRuxser(null);
        //                cargarTblAsistencias(null, null);
        //            } else {
        //                DesktopNotify.showDesktopMessage("Informacion..!", "El campo Documento es obligatorio..!", DesktopNotify.WARNING, 6000L);
        //                pr.txtDocAsist.requestFocus();
        //            }
        //        }
        //
        //        if (e.getSource() == pr.btnBuscarAsistencias) {
        //            Object[] componentes = {pr.cldInicio, pr.cldFin};
        //            if (validarCampos(componentes, "") == 0) {
        //                cargarTblAsistencias(pr.cldInicio.getDate(), pr.cldFin.getDate());
        //            } else {
        //                DesktopNotify.showDesktopMessage("Informacion..!", "El rango de fechas es obligatorio..!", DesktopNotify.WARNING, 6000L);
        //                pr.cldInicio.requestFocus();
        //            }
        //
        //        }
        //
        //        if (e.getSource() == pr.btnCancelar) {
        //            clearFormUsers();
        //        }
        //
        //        if (e.getSource() == pr.btnPrimerReg) {
        //            try {
        //                setPrimero();
        //                cargarTblUsers(filtro);
        //            } catch (IOException ex) {
        //                System.out.println("error " + ex);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.btnMenosReg) {
        //            try {
        //                setMenos();
        //                cargarTblUsers(filtro);
        //            } catch (IOException ex) {
        //                System.out.println("error " + ex);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.BtnGuardarRutina) {
        //            Object[] componentes = {pr.txtDescRutina, pr.cboDia, pr.cboMusculos2
        //            };
        //            if (validarCampos(componentes, "cboMusculos2") == 0) {
        //                Rutina rut = getRutina();
        //                rut.setDescripcion(pr.txtDescRutina.getText());
        //                rut.setFechaCreacion(new Date());
        //                rut.setEstado("A");
        //                rut.setNewRutina(newRutina);
        //                rut.create();
        //            } else {
        //                DesktopNotify.showDesktopMessage("Informacion..!", "Los campos marcados en rojo son obligatorios..!", DesktopNotify.ERROR, 6000L);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.btnSiguienteReg) {
        //            try {
        //                setMas();
        //                cargarTblUsers(filtro);
        //            } catch (IOException ex) {
        //                System.out.println("error " + ex);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.btnLastReg) {
        //            try {
        //                setUltimo();
        //                cargarTblUsers(filtro);
        //            } catch (IOException ex) {
        //                System.out.println("error " + ex);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.btnBack) {
        //            if (pr.onOff.getText().equals("OFF")) {
        //                pr.onOff.setBackground(new Color(255, 0, 0));
        //                pr.onOff.setText("OFF");
        //            } else {
        //                pr.onOff.setBackground(new Color(22, 204, 119));
        //                pr.onOff.setText("ON");
        //            }
        //            getCf().stop();
        //            setCf(null);
        //            showPanel("mnuUsers");
        //            pr.txtArea.setText("");
        //        }
        //
        //        if (e.getSource() == pr.btnFindUser) {
        //            try {
        //                desde = 0;
        //                hasta = 10;
        //                cantidadregistros = 10;
        //                currentpage = 1;
        //                filtro = pr.txtFindUser.getText();
        //                if (filtro.equals("")) {
        //                    pr.btnSiguienteReg.removeActionListener(this);
        //                    pr.btnLastReg.removeActionListener(this);
        //                    pr.btnMenosReg.removeActionListener(this);
        //                    pr.btnPrimerReg.removeActionListener(this);
        //                    pr.btnLastReg.addActionListener(this);
        //                    pr.btnSiguienteReg.addActionListener(this);
        //                    pr.btnPrimerReg.setEnabled(false);
        //                    pr.btnMenosReg.setEnabled(false);
        //                    pr.btnLastReg.setEnabled(true);
        //                    pr.btnSiguienteReg.setEnabled(true);
        //                } else {
        //                    opcPaginacion = "";
        //                    inhabilitarPaginacion();
        //                }
        //                cargarTblUsers(filtro);
        //            } catch (IOException ex) {
        //                System.out.println("error " + ex);
        //            }
        //        }
        //
        //        if (e.getSource() == pr.btnFindMusculo) {
        //            cargarTblMusculos(pr.txtFindMusculo.getText());
        //        }
        //
        //        if (e.getSource() == pr.btnGenerarReporteByTipo) {
        //            generarReportes();
        //        }
//        if (e.getSource() == MR.btnEmpresas) {
//            ListEmpresas("");
//            showPanel(1, "pnEmpresas");
//        }
//
//        if (e.getSource() == MR.btnRoles) {
//            ListRoles("");
//            showPanel(1, "pnRoles");
//        }

    }

    private void addFilter() {
        FileChooser.setFileFilter(new FileNameExtensionFilter("Imagen (*.PNG)", "png"));
        FileChooser.setFileFilter(new FileNameExtensionFilter("Imagen (*.JPG)", "jpg"));
    }

    public void showPanel(int Modulo, String string) {
        switch (Modulo) {
            case 1:
                M2.setVisible(true);
                MR.setVisible(false);
                MR.setVistaActual(string);
                M2.setVistaActual(string);
                switch (string) {
                    case "pnEmpresas":
                        MR.pnRoles.setVisible(false);
                        MR.pnEmpresas.setVisible(true);
                        break;
                    case "pnRoles":
                        MR.pnEmpresas.setVisible(false);
                        MR.pnRoles.setVisible(true);
                        break;
                }
                break;
            case 2:
                M2.setVistaActual(string);
                switch (string) {
                    case "PnProveedores":
                        M2.PnEmpresaProveedor.setVisible(false);
                        M2.PnProveedores.setVisible(true);
                        M2.PnCompras.setVisible(false);
                        M2.pnMiCaja.setVisible(false);
                        M2.PnTransVenta.setVisible(false);
                        M2.PnTransCompra.setVisible(false);
                        break;
                    case "PnEmpresaProveedor":
                        M2.PnEmpresaProveedor.setVisible(true);
                        M2.PnProveedores.setVisible(false);
                        M2.PnCompras.setVisible(false);

                        M2.pnMiCaja.setVisible(false);
                        M2.PnTransVenta.setVisible(false);

                        M2.PnTransCompra.setVisible(false);
                        break;
                    case "PnCompras":
                        M2.PnEmpresaProveedor.setVisible(false);
                        M2.PnProveedores.setVisible(false);
                        M2.PnTransCompra.setVisible(false);

                        M2.pnMiCaja.setVisible(false);
                        M2.PnTransVenta.setVisible(false);

                        M2.PnCompras.setVisible(true);
                        break;
                    case "PnTransCompra":
                        M2.PnEmpresaProveedor.setVisible(false);
                        M2.PnProveedores.setVisible(false);
                        M2.PnCompras.setVisible(false);

                        M2.pnMiCaja.setVisible(false);
                        M2.PnTransVenta.setVisible(false);
                        M2.PnTransCompra.setVisible(true);
                        break;
                    case "btnTransaccionCaja":
                        M2.PnEmpresaProveedor.setVisible(false);
                        M2.PnProveedores.setVisible(false);
                        M2.PnCompras.setVisible(false);
                        M2.PnTransCompra.setVisible(false);
                        M2.PnTransVenta.setVisible(false);
                        M2.pnMiCaja.setVisible(true);
                        break;
                    case "PnTransVenta":
                        M2.PnEmpresaProveedor.setVisible(false);
                        M2.PnProveedores.setVisible(false);
                        M2.PnCompras.setVisible(false);
                        M2.PnTransCompra.setVisible(false);
                        M2.PnTransVenta.setVisible(true);
                        M2.pnMiCaja.setVisible(false);
                        M2.PnTransCompra.setVisible(true);
                        break;
                }
                break;
            case 3:
                M3.setVistaActual(string);
                break;
            case 4:
                M4.setVistaActual(string);
                break;

        }

    }

    private void cargarTiposDocumentos() {
//        Iterator<TipoDocumento> it = getTd().List().iterator();
//        pr.cboTiposDoc.removeAllItems();
//        TipoDocumento t = new TipoDocumento();
//        t.setIdTipoDocumento(0);
//        t.setDescripcion("Seleccione");
//        t.setEstado("A");
//        pr.cboTiposDoc.addItem(t);
//        while (it.hasNext()) {
//            TipoDocumento next = it.next();
//            pr.cboTiposDoc.addItem(next);
//        }
//        setTd(null);
//        t = null;
    }

    private void CargarServicios() {
//        getTs();
//        pr.combotiposService.removeAllItems();
//        pr.combotiposService1.removeAllItems();
//        ArrayList<TipoService> listServicios = new ArrayList();
//        listServicios = (ArrayList<TipoService>) Ts.List();
//        for (TipoService listServicio : listServicios) {
//            pr.combotiposService.addItem(listServicio);
//            pr.combotiposService1.addItem(listServicio);
//        }
    }

    private void CargarTiposPagos() {
//        getTp();
//        pr.combotiposPago.removeAllItems();
//        ArrayList<TipoPago> listServicios = new ArrayList();
//        listServicios = (ArrayList<TipoPago>) Tp.List();
//        for (TipoPago listServicio : listServicios) {
//            pr.combotiposPago.addItem(listServicio);
//            pr.combotiposPago1.addItem(listServicio);
//        }
    }

    private void cargarDescuentosPagos(int condicion, int panel) {
//        getTs();
//        if (condicion == 1) {
//            if (panel == 1) {
//                Ts = (TipoService) pr.combotiposService.getSelectedItem();
//                pr.comboDescuentos.removeAllItems();
//                pr.comboDescuentos.addItem("0 %");
//                for (int i = 1; i <= Ts.getPorcentaje().intValue(); i++) {
//                    pr.comboDescuentos.addItem(i + " %");
//                }
//                pr.txtpagosValor.setText(Ts.getValor().toString());
//                pr.txtpagosValorDescuento.setText("" + 0);
//                pr.txtpagosValorTot.setText(Ts.getValor().toString());
//                pr.txtpagosFechaIni.setDate(new Date());
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(new Date()); // Configuramos la fecha que se recibe
//                calendar.add(Calendar.DAY_OF_YEAR, Ts.getDias() - 1);
//
//                pr.txtpagosFechaFinal.setDate(calendar.getTime());
//            } else {
//                Ts = (TipoService) pr.combotiposService1.getSelectedItem();
//                pr.txtpagosValor1.setText(Ts.getValor().toString());
//                pr.txtpagosValorTot1.setText(Ts.getValor().toString());
//                pr.cldFechaPagoDesde.setDate(new Date());
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(new Date()); // Configuramos la fecha que se recibe
//                calendar.add(Calendar.DAY_OF_YEAR, Ts.getDias() - 1);
//                pr.cldFechaPagoHasta.setDate(calendar.getTime());
//            }
//        } else if (condicion == 2) {
//            System.out.println("descuentoooo");
//            Ts = (TipoService) pr.combotiposService.getSelectedItem();
//
//            String porcentaje[] = pr.comboDescuentos.getSelectedItem().toString().split("%");
//            BigDecimal ValorDescuento = (Ts.getValor().multiply(new BigDecimal(porcentaje[0].trim()).divide(new BigDecimal(100))));
//            BigDecimal ValorPago = (Ts.getValor().subtract(ValorDescuento));
//            pr.txtpagosValorDescuento.setText("" + ValorDescuento);
//            pr.txtpagosValorTot.setText("" + ValorPago.toString());
//        }

    }

    public void PagosBuscarPersona(int condicion, JTable table) {
//        switch (condicion) {
//            case 1:
//                pr.txtpagosCedula.setText(us.getObjPersona().getDocumento());
//                pr.txtpagosNombre.setText(us.getObjPersona().getNombreCompleto());
//                break;
//            case 2:
//                p = p.BuscarXDocumento(pr.txtpagosCedula.getText());
//                break;
//            case 3:
//                pr.txtUserForReports.setText(us.getObjPersona().getDocumento() + " " + us.getObjPersona().getNombreCompleto());
//                break;
//            default:
//                System.out.println("ok");
//                break;
//        }
//        ListPagosXuser(table);

    }

//    private void cargarTiposPagos() {
//        Iterator<TiposPagos> it = getTp().getTiposPago().iterator();
//        pr.cboTipoPago.removeAllItems();
//        pr.cboTipoPago.addItem(new TiposPagos("", "Seleccione"));
//        while (it.hasNext()) {
//            TiposPagos next = it.next();
//            pr.cboTipoPago.addItem(next);
//        }
//        setTp(null);
//    }
    private void cargarRoles() {
//        Iterator<Rol> it = getRd().List().iterator();
//        pr.cboRol.removeAllItems();
//        Rol r = new Rol();
//        r.setIdRol(0);
//        r.setDescripcion("Seleccione");
//        pr.cboRol.addItem(r);
//        while (it.hasNext()) {
//            Rol next = it.next();
//            pr.cboRol.addItem(next);
//        }
//        setRd(null);
//        r = null;
    }

    private void cargarMusculos() {
//        Iterator<Musculos> it = getMusculos().List().iterator();
//        pr.cboMusculos.removeAllItems();
//        Musculos m = new Musculos();
//        m.setIdMusculo(0);
//        m.setdescripcion("Seleccione");
//        pr.cboMusculos.addItem(m);
//        while (it.hasNext()) {
//            Musculos next = it.next();
//            pr.cboMusculos.addItem(next);
//        }
//        setMusculos(null);
//        m = null;
    }

    private void cargarDiasMusculos() {
//        Iterator<dias> it = getDias().List().iterator();
//        Iterator<Musculos> itmus = getMusculos().List().iterator();
//        //dias
//        pr.cboDia.removeAllItems();
//        dias d = new dias();
//        d.setIdDias(0);
//        d.setDescripcion("Seleccione");
//        pr.cboDia.addItem(d);
//        while (it.hasNext()) {
//            dias next = it.next();
//            pr.cboDia.addItem(next);
//        }
//        //Musculos
//        pr.cboMusculos2.removeAllItems();
//        Musculos e = new Musculos();
//        e.setIdMusculo(0);
//        e.setdescripcion("Seleccione");
//        pr.cboMusculos2.addItem(e);
//        while (itmus.hasNext()) {
//            Musculos next = itmus.next();
//            pr.cboMusculos2.addItem(next);
//        }
//        setMusculos(null);
//        setDias(null);
//        d = null;
//        e = null;
    }

    private void cargarEmpresas() {//Gyms
//        Iterator<Empresa> it = getEmp().List().iterator();
//        pr.cboGym.removeAllItems();
////        Empresa e = new Empresa(0, "Seleccione");
////        pr.cboGym.addItem(e);
//        while (it.hasNext()) {
//            Empresa next = it.next();
//            pr.cboGym.addItem(next);
//        }
//        setEmp(null);
////        e = null;
    }

    private void cargarSedesByEmprsa(int id_empresa) {//sede * Gyms
//        Sedes s = getSede();
//        s.getObjEmpresa().setIdempresa(id_empresa);
//        Iterator<Sedes> it = s.List().iterator();
//        pr.cboSedes.removeAllItems();
////        Sedes s2 = new Sedes();
////        s2.getObjSedesID().setIdSede(0);
////        s2.getObjSedesID().setIdEmpresa(0);
////        s2.setNombre("Seleccione");
////        pr.cboSedes.addItem(s2);
//        while (it.hasNext()) {
//            Sedes next = it.next();
//            pr.cboSedes.addItem(next);
//        }
//        setSede(null);
//        s2 = null;
    }

    private void cargarTblUsers(String filtro) throws IOException {
//        pr.tblUsers.setDefaultRenderer(Object.class, new ImagensTabla());
//        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
//        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
//        DefaultTableModel modelo;
//        String Titulos[] = {"id", "Tipo Documento", "Documento", "Nombres", "Apellidos", "Dirección", "Teléfono", "Rol", "Foto", ""};
//        modelo = new DefaultTableModel(null, Titulos) {
//            @Override
//            public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
//                return false;
//            }
//        };
//        Object[] columna = new Object[10];
//        setCantRegustrosUsuarios(getRuxuser().CountRs(filtro));
//        inhabilitarPaginacion();
//        Iterator<RolxUser> itr = getRuxuser().List(desde, hasta, filtro).iterator();
//        paginarRs();
//        while (itr.hasNext()) {
//            RolxUser u = itr.next();
//            columna[0] = (int) u.getObjUsuario().getObjPersona().getIdPersona();
//            columna[1] = getTd().getTipoDocById(u.getObjUsuario().getObjPersona().getIdtipoDocumento()).getDescripcion();
//            columna[2] = u.getObjUsuario().getObjPersona().getDocumento();
//            columna[3] = u.getObjUsuario().getObjPersona().getNombre();
//            columna[4] = u.getObjUsuario().getObjPersona().getApellido();
//            columna[5] = u.getObjUsuario().getObjPersona().getDireccion();
//            columna[6] = u.getObjUsuario().getObjPersona().getTelefono();
//            columna[7] = getRuxuser().getRolbyIdRolXUser(u.getIdRolxUser());
//            columna[9] = u.getObjUsuario().getObjUsuariosID().getIdUsuario();
//            InputStream img = u.getObjUsuario().getObjPersona().getFoto();
//            if (img != null) {
//                BufferedImage bi = ImageIO.read(img);
//                ii = new ImageIcon(bi);
//                Image conver = ii.getImage();
//                Image tam = conver.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
//                iin = new ImageIcon(tam);
//                columna[8] = new JLabel(iin);
//            } else {
//                columna[8] = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/icons/user52.png")));
//
//            }
//            modelo.addRow(columna);
//        }
//        pr.tblUsers.setModel(modelo);
//        TableRowSorter<TableModel> ordenar = new TableRowSorter<>(modelo);
//        pr.tblUsers.setRowSorter(ordenar);
//        pr.tblUsers.getColumnModel().getColumn(0).setMaxWidth(0);
//        pr.tblUsers.getColumnModel().getColumn(0).setMinWidth(0);
//        pr.tblUsers.getColumnModel().getColumn(0).setPreferredWidth(0);
//        pr.tblUsers.getColumnModel().getColumn(9).setMaxWidth(0);
//        pr.tblUsers.getColumnModel().getColumn(9).setMinWidth(0);
//        pr.tblUsers.getColumnModel().getColumn(9).setPreferredWidth(0);
//        pr.tblUsers.getColumnModel().getColumn(1).setMaxWidth(105);
//        pr.tblUsers.getColumnModel().getColumn(1).setMinWidth(105);
//        pr.tblUsers.getColumnModel().getColumn(1).setPreferredWidth(105);
//        pr.tblUsers.getColumnModel().getColumn(7).setMaxWidth(80);
//        pr.tblUsers.getColumnModel().getColumn(7).setMinWidth(80);
//        pr.tblUsers.getColumnModel().getColumn(7).setPreferredWidth(80);
//        pr.tblUsers.getColumnModel().getColumn(1).setCellRenderer(Alinear);
//        pr.tblUsers.getColumnModel().getColumn(2).setCellRenderer(Alinear);
//        pr.tblUsers.getColumnModel().getColumn(3).setCellRenderer(Alinear);
//        pr.tblUsers.getColumnModel().getColumn(4).setCellRenderer(Alinear);
//        pr.tblUsers.getColumnModel().getColumn(5).setCellRenderer(Alinear);
//        pr.tblUsers.getColumnModel().getColumn(6).setCellRenderer(Alinear);
//        pr.tblUsers.setModel(modelo);
//        pr.tblUsers.setRowHeight(60);
//        setRuxser(null);
//        setTd(null);
    }

    public void paginarRs() {
//        total registros  
//        float totalRegistros = getRuxuser().CountRs(filtro);
//        float totalPaginas = totalRegistros / cantidadregistros;
//        int cantidadP = (int) Math.ceil(totalPaginas);
//        String texto = "Mostrando " + cantidadregistros + " registros de " + (int) totalRegistros + " en total  -  Pagina " + currentpage + " de " + cantidadP;
//        pr.totalRegistros.setText(texto);

    }

    public void cargarTblAsistencias(Date inicio, Date fin) {
//        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
//        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
//        DefaultTableModel modelo;
//        String Titulos[] = {"id", "Fecha", "Hora", "Cliente"};
//        modelo = new DefaultTableModel(null, Titulos) {
//            @Override
//            public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
//                return false;
//            }
//        };
//        Object[] columna = new Object[4];
//        Iterator<Asistencia> itr = getAd().List().iterator();
//        if (inicio != null && fin != null) {
//            itr = getAd().List(inicio, fin).iterator();
//        }
//
//        if (itr.hasNext()) {
//            while (itr.hasNext()) {
//                Asistencia a = itr.next();
//                columna[0] = (int) a.getObjAsistenciaID().getIdAsistencia();
//                columna[1] = sa.format(a.getFechaMarcacion());
//                columna[2] = hh.format(a.getHoraMarcacion());
//                columna[3] = a.getNombreCliente();
//                modelo.addRow(columna);
//            }
//        } else {
//            DesktopNotify.showDesktopMessage("Informacion..!", "No hay registros..!", DesktopNotify.WARNING, 6000L);
//        }
//
//        pr.tblAsistencias.setModel(modelo);
//        TableRowSorter<TableModel> ordenar = new TableRowSorter<>(modelo);
//        pr.tblAsistencias.setRowSorter(ordenar);
//        pr.tblAsistencias.getColumnModel().getColumn(0).setMaxWidth(0);
//        pr.tblAsistencias.getColumnModel().getColumn(0).setMinWidth(0);
//        pr.tblAsistencias.getColumnModel().getColumn(0).setPreferredWidth(0);
//        pr.tblAsistencias.getColumnModel().getColumn(1).setCellRenderer(Alinear);
//        pr.tblAsistencias.getColumnModel().getColumn(2).setCellRenderer(Alinear);
//        pr.tblAsistencias.getColumnModel().getColumn(3).setCellRenderer(Alinear);
//        pr.tblAsistencias.setModel(modelo);
//        setAd(null);
    }

    private void cargarTblMusculos(String filtro) {
//        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
//        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
//        DefaultTableModel modelo;
//        String Titulos[] = {"id", "Nombre Musculo", "Estado"};
//        modelo = new DefaultTableModel(null, Titulos) {
//            @Override
//            public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
//                return false;
//            }
//        };
//        Object[] columna = new Object[3];
//        Iterator<Musculos> itr = null;
//        if (filtro.equals("")) {
//            itr = getMusculos().List().iterator();
//        } else {
//            itr = getMusculos().List(filtro).iterator();
//        }
//
//        while (itr.hasNext()) {
//            Musculos m = itr.next();
//            columna[0] = (int) m.getIdMusculo();
//            columna[1] = m.getdescripcion();
//            columna[2] = (m.getEstado().equals("A")) ? "Activo" : "Inactivo";
//            modelo.addRow(columna);
//        }
//
//        pr.tblMusculos.setModel(modelo);
//        TableRowSorter<TableModel> ordenar = new TableRowSorter<>(modelo);
//        pr.tblMusculos.setRowSorter(ordenar);
//        pr.tblMusculos.getColumnModel().getColumn(0).setMaxWidth(0);
//        pr.tblMusculos.getColumnModel().getColumn(0).setMinWidth(0);
//        pr.tblMusculos.getColumnModel().getColumn(0).setPreferredWidth(0);
//        pr.tblMusculos.getColumnModel().getColumn(1).setCellRenderer(Alinear);
//        pr.tblMusculos.getColumnModel().getColumn(2).setCellRenderer(Alinear);
//        pr.tblMusculos.setModel(modelo);
//        pr.tblMusculos.setRowHeight(25);
//        setMusculos(null);
    }

    private void cargarTblEjercicios(String filtro) throws IOException {
//        pr.tblEjercicios.setDefaultRenderer(Object.class, new ImagensTabla());
//        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
//        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
//        DefaultTableModel modelo;
//        String Titulos[] = {"id", "Ejercicio", "Musculo", "Foto", ""};
//        modelo = new DefaultTableModel(null, Titulos) {
//            @Override
//            public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
//                return false;
//            }
//        };
//        Object[] columna = new Object[5];
//        Iterator<Ejercicios> itr = null;
//        if (filtro.equals("")) {
//            itr = getEjercicio().List().iterator();
//        } else {
//            itr = getEjercicio().List(filtro).iterator();
//        }
//        while (itr.hasNext()) {
//            Musculos m = new Musculos();
//            Ejercicios e = itr.next();
//            columna[0] = (int) e.getObjEjerciciosID().getIdEjercicio();
//            columna[1] = e.getDescripcion();
//            columna[2] = m.getNombreMusculoById(e.getObjEjerciciosID().getIdMusculo());
//            columna[4] = (int) e.getObjEjerciciosID().getIdMusculo();
//            InputStream img = e.getImagen();
//            if (img != null) {
//                BufferedImage bi = ImageIO.read(img);
//                ii = new ImageIcon(bi);
//                Image conver = ii.getImage();
//                Image tam = conver.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
//                iin = new ImageIcon(tam);
//                columna[3] = new JLabel(iin);
//            } else {
//                columna[3] = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/icons/Treadmill_50px.png")));
//            }
//            modelo.addRow(columna);
//            m = null;
//        }
//
//        pr.tblEjercicios.setModel(modelo);
//        TableRowSorter<TableModel> ordenar = new TableRowSorter<>(modelo);
//        pr.tblEjercicios.setRowSorter(ordenar);
//        pr.tblEjercicios.getColumnModel().getColumn(0).setMaxWidth(0);
//        pr.tblEjercicios.getColumnModel().getColumn(0).setMinWidth(0);
//        pr.tblEjercicios.getColumnModel().getColumn(0).setPreferredWidth(0);
//        pr.tblEjercicios.getColumnModel().getColumn(4).setMaxWidth(0);
//        pr.tblEjercicios.getColumnModel().getColumn(4).setMinWidth(0);
//        pr.tblEjercicios.getColumnModel().getColumn(4).setPreferredWidth(0);
//        pr.tblEjercicios.getColumnModel().getColumn(1).setCellRenderer(Alinear);
//        pr.tblEjercicios.getColumnModel().getColumn(2).setCellRenderer(Alinear);
//        pr.tblEjercicios.setModel(modelo);
//        pr.tblEjercicios.setRowHeight(60);
//        setMusculos(null);
    }

    private void cargarTblEjercicios2(int idMusculo) throws IOException {
//        pr.tblEjercicios2.setDefaultRenderer(Object.class, new ImagensTabla());
//        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
//
//        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
//        DefaultTableModel modelo;
//        String Titulos[] = {"id", "Accion", "Ejercicio", "Musculo", "Foto", ""};
//
//        modelo = new DefaultTableModel(null, Titulos) {
//            @Override
//            public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
//                return false;
//            }
//        };
//        Object[] columna = new Object[6];
//        for (int i = 0; i < allEjercicios.size(); i++) {
//            if (allEjercicios.get(i).getObjEjerciciosID().getIdMusculo() == idMusculo) {
//                Musculos m = new Musculos();
//                columna[0] = (int) allEjercicios.get(i).getObjEjerciciosID().getIdEjercicio();
//                columna[1] = allEjercicios.get(i).getOpcion();
//                columna[2] = allEjercicios.get(i).getDescripcion();
//                columna[3] = m.getNombreMusculoById(allEjercicios.get(i).getObjEjerciciosID().getIdMusculo());
//                columna[5] = (int) allEjercicios.get(i).getObjEjerciciosID().getIdMusculo();
//                Ejercicios e = new Ejercicios();
//                InputStream img = e.getImagenByIdEjercicio(allEjercicios.get(i).getObjEjerciciosID().getIdEjercicio());
//                if (img != null) {
//                    BufferedImage bi = ImageIO.read(img);
//                    ii = new ImageIcon(bi);
//                    Image conver = ii.getImage();
//                    Image tam = conver.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
//                    iin = new ImageIcon(tam);
//                    columna[4] = new JLabel(iin);
//                } else {
//                    columna[4] = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/icons/Treadmill_50px.png")));
//                }
//                e = null;
//                m = null;
//                modelo.addRow(columna);
//            }
//        }
//        pr.tblEjercicios2.setModel(modelo);
//        TableRowSorter<TableModel> ordenar = new TableRowSorter<>(modelo);
//        pr.tblEjercicios2.setRowSorter(ordenar);
//        pr.tblEjercicios2.getColumnModel().getColumn(0).setMaxWidth(0);
//        pr.tblEjercicios2.getColumnModel().getColumn(0).setMinWidth(0);
//        pr.tblEjercicios2.getColumnModel().getColumn(0).setPreferredWidth(0);
//        pr.tblEjercicios2.getColumnModel().getColumn(1).setMaxWidth(60);
//        pr.tblEjercicios2.getColumnModel().getColumn(1).setMinWidth(60);
//        pr.tblEjercicios2.getColumnModel().getColumn(1).setPreferredWidth(60);
//        pr.tblEjercicios2.getColumnModel().getColumn(5).setMaxWidth(0);
//        pr.tblEjercicios2.getColumnModel().getColumn(5).setMinWidth(0);
//        pr.tblEjercicios2.getColumnModel().getColumn(5).setPreferredWidth(0);
//        pr.tblEjercicios2.getColumnModel().getColumn(2).setCellRenderer(Alinear);
//        //Se crea el JCheckBox en la columna indicada en getColumn, en este caso, la primera columna
////        pr.tblEjercicios2.getColumnModel().getColumn(1).setCellEditor(new Celda_CheckBox());
////        //para pintar la columna con el CheckBox en la tabla, en este caso, la primera columna
////        pr.tblEjercicios2.getColumnModel().getColumn(1).setCellRenderer(new Render_CheckBox());
//        pr.tblEjercicios2.setModel(modelo);
//        pr.tblEjercicios2.setRowHeight(60);
//
//        setMusculos(null);

    }

    public CaptureFinger getCf() {
        if (cf == null) {
            cf = new CaptureFinger();
        }
        return cf;
    }

    public void setCf(CaptureFinger cf) {
        this.cf = cf;
    }

    public ReadFinger getRf() {
        if (rf == null) {
            rf = new ReadFinger();
        }
        return rf;
    }

    public void setRf(ReadFinger rf) {
        this.rf = rf;
    }

    public Rol getRol() {
        if (rol == null) {
            rol = new Rol();
        }
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public TipoDocumento getTd() {
        if (td == null) {
            td = new TipoDocumento();
        }
        return td;
    }

    public void setTd(TipoDocumento td) {
        this.td = td;

    }

    public Asistencia getAd() {
        if (ad == null) {
            ad = new Asistencia();
        }
        return ad;
    }

    public void setAd(Asistencia ad) {
        this.ad = ad;
    }

    public int getCountAction() {
        return countAction;
    }

    public void setCountAction(int countAction) {
        this.countAction += countAction;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    private void borrarImagenTemp() {
        try {
            File archivo = new File(System.getProperty("java.io.tmpdir") + "\\default.png");
            if (archivo.exists()) {
                boolean estatus = archivo.delete();
                if (!estatus) {
                    System.out.println("Error no se ha podido eliminar el  archivo");
                } else {
                    System.out.println("Se ha eliminado el archivo exitosamente");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setPrimero() {
        desde = 0;
        hasta = 10;
        currentpage = 1;
        opcPaginacion = "primero";
        inhabilitarPaginacion();
        System.out.println("desde = " + desde + " hasta = " + hasta);
    }

    public void setMas() {
        desde += hasta;
        currentpage += 1;
        opcPaginacion = "mas";
        inhabilitarPaginacion();
        System.out.println("desde = " + desde + " hasta = " + hasta);
    }

    public void setMenos() {
        if (desde > 0) {
            desde -= hasta;
            currentpage -= 1;
        } else {
            desde = 0;
            currentpage = 1;
        }
        opcPaginacion = "menos";
        inhabilitarPaginacion();
        System.out.println("desde = " + desde + " hasta = " + hasta);
    }

    public void setUltimo() {
        float totalRegistros = getRuxuser().CountRs(filtro, "");
        float totalPaginas = totalRegistros / cantidadregistros;
        int ultimo = (int) (totalRegistros) - (((int) totalPaginas % 2 == 0) ? cantidadregistros : 1);
        desde = ultimo;
        currentpage = (int) Math.ceil(totalPaginas);
        opcPaginacion = "ultimo";
        inhabilitarPaginacion();
        System.out.println("desde = " + desde + " hasta = " + hasta + " totalPaginas " + currentpage + " ulttimo = " + ultimo);
    }

    private void inhabilitarPaginacion() {
//        System.out.println("opc " + opcPaginacion);
//        float cant_reg = getRuxuser().CountRs(filtro);
//        float totalPaginas = cant_reg / cantidadregistros;
//        int cantidadP = (int) Math.ceil(totalPaginas);
//        if (cantidadP == 1) {
//            System.out.println("opc ***");
//            pr.btnPrimerReg.setEnabled(false);
//            pr.btnSiguienteReg.setEnabled(false);
//            pr.btnLastReg.setEnabled(false);
//            pr.btnMenosReg.setEnabled(false);
//        } else if (cant_reg > 0 && cant_reg < 10) {
//            System.out.println("opc *****--**--");
//            pr.btnPrimerReg.setEnabled(false);
//            pr.btnSiguienteReg.setEnabled(false);
//            pr.btnLastReg.setEnabled(false);
//            pr.btnMenosReg.setEnabled(false);
//        } else {
//            System.out.println("aqui");
//            switch (opcPaginacion) {
//                case "primero":
//                    pr.btnSiguienteReg.removeActionListener(this);
//                    pr.btnLastReg.removeActionListener(this);
//                    pr.btnPrimerReg.removeActionListener(this);
//                    pr.btnPrimerReg.setEnabled(false);
//                    pr.btnMenosReg.removeActionListener(this);
//                    pr.btnMenosReg.setEnabled(false);
//                    pr.btnSiguienteReg.addActionListener(this);
//                    pr.btnSiguienteReg.setEnabled(true);
//                    pr.btnLastReg.addActionListener(this);
//                    pr.btnLastReg.setEnabled(true);
//                    break;
//                case "menos":
//                    if (desde == 0) {
//                        pr.btnSiguienteReg.removeActionListener(this);
//                        pr.btnLastReg.removeActionListener(this);
//                        pr.btnPrimerReg.removeActionListener(this);
//                        pr.btnPrimerReg.setEnabled(false);
//                        pr.btnMenosReg.removeActionListener(this);
//                        pr.btnMenosReg.setEnabled(false);
//                        pr.btnSiguienteReg.addActionListener(this);
//                        pr.btnSiguienteReg.setEnabled(true);
//                        pr.btnLastReg.addActionListener(this);
//                        pr.btnLastReg.setEnabled(true);
//                    } else {
//                        pr.btnSiguienteReg.removeActionListener(this);
//                        pr.btnLastReg.removeActionListener(this);
//                        pr.btnPrimerReg.removeActionListener(this);
//                        pr.btnMenosReg.removeActionListener(this);
//                        pr.btnPrimerReg.setEnabled(true);
//                        pr.btnMenosReg.setEnabled(true);
//                        pr.btnSiguienteReg.setEnabled(true);
//                        pr.btnLastReg.setEnabled(true);
//                        pr.btnPrimerReg.addActionListener(this);
//                        pr.btnMenosReg.addActionListener(this);
//                        pr.btnSiguienteReg.addActionListener(this);
//                        pr.btnLastReg.addActionListener(this);
//                    }
//                    break;
//                case "mas":
//                    System.out.println("currentpage = " + currentpage + " totalPaginas =" + (int) Math.ceil(totalPaginas));
//                    if (currentpage == cantidadP) {
//                        pr.btnSiguienteReg.removeActionListener(this);
//                        pr.btnLastReg.removeActionListener(this);
//                        pr.btnPrimerReg.removeActionListener(this);
//                        pr.btnMenosReg.removeActionListener(this);
//                        pr.btnSiguienteReg.setEnabled(false);
//                        pr.btnLastReg.setEnabled(false);
//                        pr.btnPrimerReg.setEnabled(true);
//                        pr.btnMenosReg.setEnabled(true);
//                        pr.btnPrimerReg.addActionListener(this);
//                        pr.btnMenosReg.addActionListener(this);
//                    } else {
//                        pr.btnSiguienteReg.removeActionListener(this);
//                        pr.btnLastReg.removeActionListener(this);
//                        pr.btnPrimerReg.removeActionListener(this);
//                        pr.btnMenosReg.removeActionListener(this);
//                        pr.btnSiguienteReg.setEnabled(true);
//                        pr.btnLastReg.setEnabled(true);
//                        pr.btnPrimerReg.setEnabled(true);
//                        pr.btnMenosReg.setEnabled(true);
//                        pr.btnPrimerReg.addActionListener(this);
//                        pr.btnMenosReg.addActionListener(this);
//                        pr.btnSiguienteReg.addActionListener(this);
//                        pr.btnLastReg.addActionListener(this);
//                    }
//                    break;
//                case "ultimo":
//                    pr.btnSiguienteReg.removeActionListener(this);
//                    pr.btnLastReg.removeActionListener(this);
//                    pr.btnPrimerReg.removeActionListener(this);
//                    pr.btnMenosReg.removeActionListener(this);
//                    pr.btnSiguienteReg.setEnabled(false);
//                    pr.btnLastReg.setEnabled(false);
//                    pr.btnPrimerReg.setEnabled(true);
//                    pr.btnMenosReg.setEnabled(true);
//                    pr.btnPrimerReg.addActionListener(this);
//                    pr.btnMenosReg.addActionListener(this);
//                    break;
//            }
//
//        }
    }

    public persona getP() {
        if (p == null) {
            p = new persona();
        }
        return p;
    }

    public void setP(persona p) {
        this.p = p;
    }

    public RolxUser getRuxuser() {
        if (ruxuser == null) {
            ruxuser = new RolxUser();
        }
        return ruxuser;
    }

    public void setRuxser(RolxUser ruxuser) {
        this.ruxuser = ruxuser;
    }

    public UsuarioID getUid() {
        if (uid == null) {
            uid = new UsuarioID();
        }
        return uid;
    }

    public void setUid(UsuarioID uid) {
        this.uid = uid;
    }

    public Usuario getUs() {
        if (us == null) {
            us = new Usuario();
        }
        return us;
    }

    public void setUs(Usuario us) {
        this.us = us;
    }

    public Sedes getSede() {
        if (sede == null) {
            sede = new Sedes();
        }
        return sede;
    }

    public void setSede(Sedes sede) {
        this.sede = sede;
    }

    public int getCantRegustrosUsuarios() {
        return cantRegustrosUsuarios;
    }

    public void setCantRegustrosUsuarios(int cantRegustrosUsuarios) {
        this.cantRegustrosUsuarios = cantRegustrosUsuarios;
    }

    public Musculos getMusculos() {
        if (musculos == null) {
            musculos = new Musculos();
        }
        return musculos;
    }

    public void setMusculos(Musculos musculos) {
        this.musculos = musculos;
    }

    public Ejercicios getEjercicio() {
        if (ejercicio == null) {
            ejercicio = new Ejercicios();
        }
        return ejercicio;
    }

    public void setEjercicio(Ejercicios ejercicio) {
        this.ejercicio = ejercicio;
    }

    public dias getDias() {
        if (dias == null) {
            dias = new dias();
        }
        return dias;
    }

    public void setDias(dias dias) {
        this.dias = dias;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        if (e.getSource() == pr.tblEjercicios2) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Rutina getRutina() {
        if (rutina == null) {
            rutina = new Rutina();
        }
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == M2.txtVentaCodCliente && e.getKeyCode() == 10) {
            getCl();
            ArrayList<Cliente> ListCliente = new ArrayList();
            ListCliente = (ArrayList<Cliente>) cl.BuscarXCliente(M2.txtVentaCodCliente.getText().trim());
            if (ListCliente.size() > 1) {
                try {
                    getOb();
                    ob.setTitulo("Buscar Cliente");
                    ob.setFiltro("Nombre ó Cedula");
                    ob.setModulo(2);
                    ob.setCondicion(3);
                    ob.setM2(this);
                    ob.setListObjectos((ArrayList<Object>) (Object) ListCliente);
                    new Busqueda(M2, true, ob).setVisible(true);
                } catch (SQLException ex) {
                    System.out.println("Error al abrir modal");
                }
            } else {
                PasarClienteventa(ListCliente.get(0));
            }
        } else if (e.getSource() == M2.txtVentEfectivo && e.getKeyCode() == 10) {
            CalculosVenta();
        } else if (e.getSource() == M2.TxtbuscarProductoVenta && e.getKeyCode() == 10) {
            getPr();
            ArrayList<producto> ListProducto = new ArrayList();
            ListProducto = (ArrayList<producto>) pr.BuscarProducto(M2.TxtbuscarProductoVenta.getText().trim());
            System.out.println("Tam . " + ListProducto.size());
            if (ListProducto.size() > 1) {
                try {
                    getOb();
                    ob.setTitulo("Buscar Producto");
                    ob.setFiltro("Nombre ó Codigo");
                    ob.setModulo(2);
                    ob.setCondicion(4);
                    ob.setM2(this);
                    ob.setListObjectos((ArrayList<Object>) (Object) ListProducto);
                    new Busqueda(M2, true, ob).setVisible(true);
                } catch (SQLException ex) {
                    System.out.println("Error al abrir modal");
                }
            } else {
                Contenedor.getListProductos().add(ListProducto.get(0));
                ListProductosVenta();
                CalculosVenta();
            }
        } 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == M2.VentaProductosAdd && e.getKeyCode() == 10) {
            System.out.println("Ajusto Cantidad2");
            int i = M2.VentaProductosAdd.getSelectedRow();
            calculos(i);
        }
    }

    public void calculos(int posicion) {        
        String id = (String) M2.VentaProductosAdd.getValueAt(posicion, 0).toString();
        String cant = (String) M2.VentaProductosAdd.getValueAt(posicion, 3).toString();        

        for (producto p : Contenedor.getListProductos()) {
            if (p.getProductosID().getCod_producto().intValue()==Integer.parseInt(id)) {
                p.setCantidad(Integer.parseInt(cant));
            }
        }
    
        CalculosVenta();
    }

    public void RestaurarValoresViewPago(String Doc, String Nombre, boolean restaurarUser) {
//        if (restaurarUser) {
//            us = null;
//        }
//        pr.txtpagosValorTot.setText("");
//        pr.txtpagosValorDescuento.setText("");
//        pr.txtpagosValor.setText("");
//        pr.txtpagosCedula.setText(Doc);
//        pr.txtpagosNombre.setText(Nombre);
    }

    public void ListPagosXuser(JTable table) {
        if (us != null) {
            ArrayList<PagoService> listPagos = new ArrayList();
            listPagos = (ArrayList<PagoService>) pagoService.ListPagosXClientes(us.getObjUsuariosID().getIdUsuario(), null, null);
            DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
            Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
            DefaultTableModel modelo;
            String Titulos[] = {"Factura", "Servicio", "Fecha Pago", "Fecha Inicio", "Fecha Fin", "Valor", "Descuento %", "Total"};
            modelo = new DefaultTableModel(null, Titulos) {
                @Override
                public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
                    return false;
                }
            };
            Object[] columna = new Object[8];
            Iterator<PagoService> itr = listPagos.iterator();
            while (itr.hasNext()) {
                PagoService e = itr.next();
                columna[0] = e.getObjPagoServiceID().getIdPago();
                columna[1] = e.getObjTipoService().getDescripcion();
                columna[2] = e.getFechaPago();
                columna[3] = e.getFechaInicio();
                columna[4] = e.getFechaFinal();
                columna[5] = e.getValorNeto();
                columna[6] = e.getValorDescuento();
                columna[7] = e.getValorTotal();
                modelo.addRow(columna);
            }
            table.setModel(modelo);
            table.setRowHeight(30);
        }
    }

    public void CargarPorcentajesDescuentos(){
        M2.ComboVentPorcentaje.removeAllItems();
        for (int j = 0; j < 100; j++) {
            M2.ComboVentPorcentaje.addItem(j);
        }
    }
//    public boolean validacionPagos() {
//        return pagoService.ValidacionPagos(us.getObjUsuariosID().getIdUsuario(), sa.format(pr.txtpagosFechaIni.getDate()), sa.format(pr.txtpagosFechaFinal.getDate()));
//    }
    public void IniciarPagos(String Documento, String Nombre, boolean restaurarUser) {
//        getPagoService();
//        pr.txtfactura.setText("" + pagoService.UltimoId());
//        CargarServicios();
//        CargarTiposPagos();
//        RestaurarValoresViewPago(Documento, Nombre, restaurarUser);
//        cargarDescuentosPagos(1, 1);
//        ListPagosXuser(pr.tblListaPagosXuser);
//        showPanel("pnPagosService");
    }

    public void generarReportes() {
//        //llamar metodo agerar reporte de pagoservices pojo
//        Object[] componentes = {pr.txtUserForReports};
//        if (validarCampos(componentes, "") == 0) {
//            Reportes r = getReportes();
//            r.setUs(us);
//            r.setTipoReporte("");
//            r.setDesde(pr.fechaPagoUserdesde.getDate());
//            r.setHasta(pr.fechaPagoUserhasta.getDate());
//            pr.preloader.setVisible(true);
//            Thread thread = new Thread(r);
//            thread.start();
//            setPagoService(null);
//            thread = null;
//            r = null;
//        } else {
//            DesktopNotify.showDesktopMessage("Informacion..!", "El Campo Usuario Es Obligatorio..!", DesktopNotify.ERROR, 6000L);
//        }

    }

    public void EstadoMiCaja() throws ClassNotFoundException {
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        getMiCaja();
        System.out.println("iniciamos");
        if (M2.pnMicajaEstado.getText().equals("CERRADA") || M2.pnMicajaEstado.getText().equals("Estado")) {
            M2.BtnGenerarPagos.setEnabled(false);
        }
        MiCaja.getObjCajaxUserID().setIdUsuario(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdUsuario()));
        System.out.println("seguimos");
        MiCaja = MiCaja.MiCaja();
        System.out.println("MiCaja " + MiCaja);
        if (MiCaja != null) {
            M2.pnMicajaMns.setVisible(true);
            M2.pnMicajaEstado.setText("Abierta");
            M2.btnCaja.setText("Cerrar");
//            pr.btnCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Wallet.png")));
            CargarDatosCaja(1);
//            pr.btnReporteCaja.setEnabled(false);
            M2.btnDetallePago.setEnabled(true);
            M2.BtnGenerarPagos.setEnabled(true);
            System.out.println("Mi caja : " + MiCaja.getObjCajaxUserID().getIdcaja());
            M2.pnMicajaMns.setText("MI CAJA #" + MiCaja.getObjCajaxUserID().getIdcaja() + "         " + dt1.format(new Date()));
            M2.pnMicajaMns2.setText("Historial de Pagos");
        } else {
            M2.pnMicajaMns.setVisible(false);
            CargarDatosCaja(2);
            this.M2.pnMicajaMns2.setVisible(true);
            M2.btnDetallePago.setEnabled(false);
            M2.btnCaja.setText("Abrir");
            this.M2.pnMicajaMns2.setText("Datos de la ultima caja Abierta ");
            M2.pnMicajaEstado.setText("CERRADA");
            M2.BtnGenerarPagos.setEnabled(false);
            M2.btnCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/CashRegister.png")));
            M2.btnReporteCaja.setEnabled(true);
        }
    }

    public void CargarDatosCaja(int condicion) {

        ArrayList<PagoService> listUsuario = new ArrayList();
        getPagoService();
        if (MiCaja != null) {
            pagoService.getObjPagoServiceID().setIdcaja(MiCaja.getObjCajaxUserID().getIdcaja());
        } else {
            pagoService.getObjPagoServiceID().setIdUsuario(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdUsuario()));
        }

        listUsuario = (ArrayList<PagoService>) pagoService.ListPagosXUsers(condicion);

        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        DefaultTableModel modelo;
        String Titulos[] = {"Factura", "Servicio", "Valor"};
        modelo = new DefaultTableModel(null, Titulos) {
            @Override
            public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
                return false;
            }
        };
        Object[] columna = new Object[4];
        Iterator<PagoService> listPagos = listUsuario.iterator();
        while (listPagos.hasNext()) {
            PagoService u = listPagos.next();
            columna[0] = u.getObjPagoServiceID().getIdPago();
            columna[1] = u.getObjTipoService().getDescripcion();
            columna[2] = u.getValorTotal();

            modelo.addRow(columna);
        }
        M2.tblListaPagos.setModel(modelo);
        M2.tblListaPagos.getColumnModel().getColumn(0).setPreferredWidth(100);
        M2.tblListaPagos.getColumnModel().getColumn(1).setPreferredWidth(100);
        M2.tblListaPagos.getColumnModel().getColumn(2).setPreferredWidth(100);
        M2.tblListaPagos.setRowHeight(30);
//        pr.tblListaPagos.getColumnModel().getColumn(2).setCellRenderer(Alinear);
    }

    public CajaXUser getMiCaja() {
        if (MiCaja == null) {
            MiCaja = new CajaXUser();
        }
        return MiCaja;
    }

    public void setMiCaja(CajaXUser MiCaja) {
        this.MiCaja = MiCaja;
    }

    public RolxUser getUsuarioLogeado() {
        if (UsuarioLogeado == null) {
            UsuarioLogeado = new RolxUser();
        }
        return UsuarioLogeado;
    }

    public void setUsuarioLogeado(RolxUser UsuarioLogeado) {
        getUsuarioLogeado();
        this.UsuarioLogeado = UsuarioLogeado;
    }

    public PagoService getPagoService() {
        if (pagoService == null) {
            pagoService = new PagoService();
        }
        return pagoService;
    }

    public void setPagoService(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    public TipoService getTs() {
        if (Ts == null) {
            Ts = new TipoService();
        }
        return Ts;
    }

    public void setTs(TipoService Ts) {
        this.Ts = Ts;
    }

    public TipoPago getTp() {
        if (Tp == null) {
            Tp = new TipoPago();
        }
        return Tp;
    }

    public void setTp(TipoPago Tp) {
        this.Tp = Tp;
    }

    public void Adaptador() {
        M2.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                Object[] opciones = {"Aceptar", "Cancelar"};
                int eleccion = JOptionPane.showOptionDialog(null, "En realidad desea cerrar la aplicacion", "Mensaje de Confirmacion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
                if (eleccion == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private void cargarHistorialPagos(int id_usuario, Date desde, Date hasta, JTable table) {
//        ArrayList<PagoService> listPagos = new ArrayList();
//        listPagos = (ArrayList<PagoService>) getPagoService().ListPagosXClientes(id_usuario, desde, hasta);
//        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
//        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
//        DefaultTableModel modelo;
//        String Titulos[] = {"Factura", "Servicio", "Fecha Pago", "Fecha Inicio", "Fecha Fin", "Valor", "Descuento %", "Total"};
//        modelo = new DefaultTableModel(null, Titulos) {
//            @Override
//            public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
//                return false;
//            }
//        };
//        Object[] columna = new Object[8];
//        Iterator<PagoService> itr = listPagos.iterator();
//        while (itr.hasNext()) {
//            PagoService e = itr.next();
//            columna[0] = e.getObjPagoServiceID().getIdPago();
//            columna[1] = e.getObjTipoService().getDescripcion();
//            columna[2] = e.getFechaPago();
//            columna[3] = e.getFechaInicio();
//            columna[4] = e.getFechaFinal();
//            columna[5] = e.getValorNeto();
//            columna[6] = e.getValorDescuento();
//            columna[7] = e.getValorTotal();
//            modelo.addRow(columna);
//
//        }
//        setPagoService(pagoService);
//        table.setModel(modelo);
//        table.getColumnModel().getColumn(0).setPreferredWidth(100);
//        table.getColumnModel().getColumn(1).setPreferredWidth(150);
//        table.getColumnModel().getColumn(2).setPreferredWidth(150);
//        table.getColumnModel().getColumn(3).setPreferredWidth(150);
//        table.getColumnModel().getColumn(4).setPreferredWidth(150);
//        table.getColumnModel().getColumn(5).setPreferredWidth(150);
//        table.getColumnModel().getColumn(6).setPreferredWidth(150);
//        table.getColumnModel().getColumn(7).setPreferredWidth(150);
//        table.getColumnModel().getColumn(0).setCellRenderer(Alinear);
//        table.getColumnModel().getColumn(1).setCellRenderer(Alinear);
//        table.getColumnModel().getColumn(2).setCellRenderer(Alinear);
//        table.getColumnModel().getColumn(3).setCellRenderer(Alinear);
//        table.getColumnModel().getColumn(4).setCellRenderer(Alinear);
//        table.getColumnModel().getColumn(5).setCellRenderer(Alinear);
//        table.getColumnModel().getColumn(6).setCellRenderer(Alinear);
//        table.getColumnModel().getColumn(7).setCellRenderer(Alinear);
//        table.setRowHeight(30);
    }

    public Reportes getReportes() {
        if (reportes == null) {
            reportes = new Reportes();
        }
        return reportes;
    }

    public void setReportes(Reportes reportes) {
        this.reportes = reportes;
    }

    public void CargarDatosInicialesProveedores(int condicion, EmpresaProveedor ObjEmp) {
        getEp();

        if (condicion == 1) {
            ArrayList<EmpresaProveedor> ListEmp = new ArrayList();
            ListEmp = (ArrayList<EmpresaProveedor>) ep.List();
            M2.cboEmpresasProveedor.removeAllItems();
            for (EmpresaProveedor empresaProveedor : ListEmp) {
                M2.cboEmpresasProveedor.addItem(empresaProveedor);
            }
        } else {
            ArrayList<EmpresaProveedor> ListEmp = new ArrayList();
            ListEmp = (ArrayList<EmpresaProveedor>) ep.ListXEdicion(ObjEmp.getIdEmpresaProveedor().intValue());
            M2.cboEmpresasProveedor.removeAllItems();
            for (EmpresaProveedor empresaProveedor : ListEmp) {
                M2.cboEmpresasProveedor.addItem(empresaProveedor);
            }
        }

    }

    private void cargarTiposDocumentosProveedor() {
        Iterator<TipoDocumento> it = getTd().List().iterator();
        M2.txtTipoDocProveedor.removeAllItems();
        TipoDocumento t = new TipoDocumento();
        t.setIdTipoDocumento(new BigDecimal(0));
        t.setDescripcion("Seleccione");
        t.setEstado("A");
        M2.txtTipoDocProveedor.addItem(t);
        while (it.hasNext()) {
            TipoDocumento next = it.next();
            M2.txtTipoDocProveedor.addItem(next);
        }
        setTd(null);
        t = null;
    }

    public void ListEmpresasProveedor(String filtro) {
        getEp();
        ArrayList<EmpresaProveedor> listEmpresaProve = new ArrayList();

        if (filtro.length() <= 0) {
            listEmpresaProve = (ArrayList<EmpresaProveedor>) ep.List();
        } else if (filtro.length() > 0) {
            //listEmpresaProve = (ArrayList<EmpresaProveedor>) ep.BuscarProducto(filtro);
        }

        M2.tblListaEmpresasProve.removeAll();
        TablaModel tablaModel = new TablaModel(listEmpresaProve, 4);
        M2.tblListaEmpresasProve.setModel(tablaModel.ModelListEmpresasProveedor());
        M2.tblListaEmpresasProve.getColumnModel().getColumn(0).setPreferredWidth(10);
        M2.tblListaEmpresasProve.getColumnModel().getColumn(1).setPreferredWidth(300);
        M2.tblListaEmpresasProve.getColumnModel().getColumn(2).setPreferredWidth(10);
        M2.tblListaEmpresasProve.getColumnModel().getColumn(3).setPreferredWidth(10);
        M2.tblListaEmpresasProve.getColumnModel().getColumn(4).setPreferredWidth(10);

        M2.tblListaEmpresasProve.setRowHeight(30);
    }

    public void ListProveedores() {
        getPv();
        ArrayList<Proveedor> listaProveedores = new ArrayList();

        listaProveedores = (ArrayList<Proveedor>) pv.List();

        M2.tblProveedores.removeAll();
        TablaModel tablaModel = new TablaModel(listaProveedores, 3);
        M2.tblProveedores.setModel(tablaModel.ModelListProveedor());
        M2.tblProveedores.getColumnModel().getColumn(0).setPreferredWidth(10);
        M2.tblProveedores.getColumnModel().getColumn(1).setPreferredWidth(50);
        M2.tblProveedores.getColumnModel().getColumn(2).setPreferredWidth(20);
        M2.tblProveedores.getColumnModel().getColumn(3).setPreferredWidth(50);
        M2.tblProveedores.getColumnModel().getColumn(4).setPreferredWidth(20);
        M2.tblProveedores.getColumnModel().getColumn(5).setPreferredWidth(10);
        M2.tblProveedores.getColumnModel().getColumn(6).setPreferredWidth(20);
        M2.tblProveedores.getColumnModel().getColumn(7).setPreferredWidth(6);

        M2.tblProveedores.setRowHeight(30);
    }

    public void CargarDatosEmpresaProvedor(int codigo) {
        int fila = M2.tblListaEmpresasProve.getSelectedRow();
        if (fila >= 0) {
            getEp();
            ep = ep.BuscarEmpresaXCodigo(codigo);

            M2.txtProveEmpNombre.setText(ep.getNombreEmpresa());
            M2.txtProveEmpNit.setText(ep.getNit());
            M2.txtProveEmpDireccion.setText(ep.getDireccion());
            M2.txtProveEmpTelefono.setText(ep.getTelefono());

            M2.btnEmpresaProveGuardar.setText("Editar");
        }
    }

    public void CargarDatosProvedor(int codigo) {
        int fila = M2.tblProveedores.getSelectedRow();
        if (fila >= 0) {
            getPv();
            pv = pv.BuscarProveedor(codigo);
            TipoDocumento t = (TipoDocumento) M2.txtTipoDocProveedor.getSelectedItem();

            M2.txtDocProve.setText(pv.getPersona().getDocumento());
            M2.txtNombresProve.setText(pv.getPersona().getNombre());
            M2.txtApellidosProve.setText(pv.getPersona().getApellido());
            M2.txtTelefonosProve.setText(pv.getPersona().getTelefono());
            CargarDatosInicialesProveedores(2, pv.getEmpresa());

            M2.btnEmpresaProveGuardar.setText("Editar");
        }
    }

    public void CargarDatosProveedor(Proveedor p) {
        if (p == null) {
            M2.txtProveedorCompra.setText("");
            M2.txtnomProveedornombre.setText("");
            M2.txtempproemprnombre.setText("");

        } else if (p != null) {
            getPv();
            pv = p;
            M2.txtProveedorCompra.setText(p.getPersona().getDocumento());
            M2.txtnomProveedornombre.setText(p.getPersona().getNombreCompleto());
            M2.txtempproemprnombre.setText(p.getEmpresa().getNombreEmpresa());
        }
    }

    public void cargarMenu() {
        if (VistaActual.getMenu().length() > 1) {
            switch (VistaActual.getMenu()) {
                case "PnProveedores":
                    CargarDatosInicialesProveedores(1, null);
                    cargarTiposDocumentosProveedor();
                    ListProveedores();
                    showPanel(2, "PnProveedores");
                    break;
            }
        }
        VistaActual.setMenu("");
    }

    public void ListProductosAñadidos() {
        getPr();
        getCp();
        M2.tableProductosAdd.removeAll();
        TablaModel tablaModel = new TablaModel(pr.getListProductos(), 2);
        M2.tableProductosAdd.setModel(tablaModel.ModelListProductosAñadidos());
        M2.tableProductosAdd.getColumnModel().getColumn(0).setMaxWidth(0);
        M2.tableProductosAdd.getColumnModel().getColumn(0).setMinWidth(0);
        M2.tableProductosAdd.getColumnModel().getColumn(0).setPreferredWidth(0);
        M2.tableProductosAdd.getColumnModel().getColumn(1).setPreferredWidth(10);
        M2.tableProductosAdd.getColumnModel().getColumn(2).setPreferredWidth(50);
        M2.tableProductosAdd.getColumnModel().getColumn(3).setPreferredWidth(30);
        M2.tableProductosAdd.getColumnModel().getColumn(4).setPreferredWidth(10);
        M2.tableProductosAdd.getColumnModel().getColumn(5).setPreferredWidth(10);
        M2.tableProductosAdd.getColumnModel().getColumn(6).setPreferredWidth(10);
        M2.tableProductosAdd.getColumnModel().getColumn(7).setPreferredWidth(10);
        M2.tableProductosAdd.getColumnModel().getColumn(8).setPreferredWidth(10);
        M2.tableProductosAdd.getColumnModel().getColumn(9).setPreferredWidth(10);

        M2.tableProductosAdd.setRowHeight(30);
    }

    public void ListProductosVenta() {
        M2.VentaProductosAdd.removeAll();
        TablaModel tablaModel = new TablaModel(Contenedor.getListProductos(), 2);
        M2.VentaProductosAdd.setModel(tablaModel.ModelListProductosVenta());
        M2.VentaProductosAdd.getColumnModel().getColumn(0).setMaxWidth(0);
        M2.VentaProductosAdd.getColumnModel().getColumn(0).setMinWidth(0);
        M2.VentaProductosAdd.getColumnModel().getColumn(0).setPreferredWidth(0);
        M2.VentaProductosAdd.getColumnModel().getColumn(1).setPreferredWidth(10);
        M2.VentaProductosAdd.getColumnModel().getColumn(2).setPreferredWidth(50);
        M2.VentaProductosAdd.getColumnModel().getColumn(3).setPreferredWidth(30);
        M2.VentaProductosAdd.getColumnModel().getColumn(4).setPreferredWidth(10);
        M2.VentaProductosAdd.getColumnModel().getColumn(5).setPreferredWidth(10);
        M2.tableProductosAdd.setRowHeight(30);
    }

    public void CalculosVenta() {
        getPr();
        getV();
        double subtotal = 0;
        double ValorIva = 0;
        double ValorPorcentaje = 0;
        double Devuelta = 0;
        for (producto p : Contenedor.getListProductos()) {
            subtotal += (p.getPrecio_venta().doubleValue()*p.getCantidad());
            ValorIva += (p.getPrecio_venta().doubleValue() * p.getIvaP().getPorcentaje().doubleValue());
        }
        System.out.println("--- " + subtotal);
        M2.txtSubTotalVenta.setText("" + subtotal);        

        ValorPorcentaje = (subtotal * Double.parseDouble(M2.ComboVentPorcentaje.getSelectedItem().toString()));

        M2.txtVentValorDesc.setText("" + ValorPorcentaje);
        M2.txtVentaValorIva.setText("" + ValorIva);

        System.out.println("Efectivo : " + M2.txtVentEfectivo.getText());

        Devuelta = Double.parseDouble(M2.txtVentEfectivo.getText().length() > 0 ? M2.txtVentEfectivo.getText() : "0");

        M2.txtVentaValorTotal.setText("TOTAL : $ " + (subtotal - ValorPorcentaje));
        M2.txtVentaDevuelta.setText("DEVOLUCION : $ " + (M2.txtVentEfectivo.getText().length() > 0 ? (Devuelta - (subtotal - ValorPorcentaje)) : 0));

        v.setValorNeto(new BigDecimal(M2.txtSubTotalVenta.getText()));
        v.setValoriva(new BigDecimal(M2.txtVentaValorIva.getText()));
        v.setPorcentajeDescuento(new BigDecimal(M2.ComboVentPorcentaje.getSelectedItem().toString()));
        v.setValorDescuento(new BigDecimal(M2.txtVentValorDesc.getText()));
        v.setTotal_venta(new BigDecimal(subtotal - ValorPorcentaje));
        v.setDevuelta(new BigDecimal((M2.txtVentEfectivo.getText().length() > 0 ? (Devuelta - (subtotal - ValorPorcentaje)) : 0)));

    }

    public void CargaTiposPagos() {
        getTp();
        ArrayList<TipoPago> listPagos = new ArrayList();
        listPagos = (ArrayList<TipoPago>) Tp.List();
        M2.txtVenaTipoPago.removeAllItems();
        for (TipoPago listPago : listPagos) {
            M2.txtVenaTipoPago.addItem(listPago);
        }
    }

    public void ListSedes() {
        getSede();
        ArrayList<Sedes> listSedes = new ArrayList<>();
        listSedes = (ArrayList<Sedes>) sede.List();
        for (Sedes sede : listSedes) {
            M2.txtComboSedeCompra.addItem(sede);
        }
    }

    public void ListBodegas() {
        M2.txtComboBodegasCompra.removeAllItems();
        getB();
        getSede();
        sede = (Sedes) M2.txtComboSedeCompra.getSelectedItem();
        ArrayList<Bodega> listBodega = new ArrayList<>();
        listBodega = (ArrayList<Bodega>) b.ListXSedes(sede.getObjSedesID().getIdSede());
        for (Bodega bodega : listBodega) {
            M2.txtComboBodegasCompra.addItem(bodega);
        }
    }

    public void LimpiarCampos(String menu) {
        switch (menu) {
            case "proveedores":
                M2.txtDocProve.setText("");
                M2.txtNombresProve.setText("");
                M2.txtApellidosProve.setText("");
                M2.txtTelefonosProve.setText("");
                break;
        }
    }

    public void PasarClienteventa(Cliente c) {
        getCl();
        cl = c;
        M2.txtVentaCodCliente.setText(cl.getP().getDocumento());
        M2.txtVentaNomcliente.setText(cl.getP().getNombreCompleto());
    }

    public Proveedor getPv() {
        if (pv == null) {
            pv = new Proveedor();
        }
        return pv;
    }

    public void setPv(Proveedor pv) {
        this.pv = pv;
    }

    public EmpresaProveedor getEp() {
        if (ep == null) {
            ep = new EmpresaProveedor();
        }
        return ep;
    }

    public void setEp(EmpresaProveedor ep) {
        this.ep = ep;
    }

    public Empresas getEmpresas() {
        if (empresas == null) {
            empresas = new Empresas();
        }
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    public iva getI() {
        if (i == null) {
            i = new iva();
        }
        return i;
    }

    public void setI(iva i) {
        this.i = i;
    }

    public categoria getC() {
        if (c == null) {
            c = new categoria();
        }
        return c;
    }

    public void setC(categoria c) {
        this.c = c;
    }

    public Unidad getU() {
        if (u == null) {
            u = new Unidad();
        }
        return u;
    }

    public void setU(Unidad u) {
        this.u = u;
    }

    public producto getPr() {
        if (pr == null) {
            pr = new producto();
        }
        return pr;
    }

    public void setPr(producto pr) {
        this.pr = pr;
    }

    public compra_producto getCp() {
        if (cp == null) {
            cp = new compra_producto();
        }
        return cp;
    }

    public void setCp(compra_producto cp) {
        this.cp = cp;
    }

    public compradetalle getCd() {
        if (cd == null) {
            cd = new compradetalle();
        }
        return cd;
    }

    public void setCd(compradetalle cd) {
        this.cd = cd;
    }

    public Bodega getB() {
        if (b == null) {
            b = new Bodega();
        }
        return b;
    }

    public void setB(Bodega b) {
        this.b = b;
    }

    public objectobusqueda getOb() {
        if (ob == null) {
            ob = new objectobusqueda();
        }
        return ob;
    }

    public void setOb(objectobusqueda ob) {
        this.ob = ob;
    }

    public Cliente getCl() {
        if (cl == null) {
            cl = new Cliente();
        }
        return cl;
    }

    public void setCl(Cliente cl) {
        this.cl = cl;
    }

    public venta getV() {
        if (v == null) {
            v = new venta();
        }
        return v;
    }

    public void setV(venta v) {
        this.v = v;
    }

    public ventaproducto getVp() {
        if (vp == null) {
            vp = new ventaproducto();
        }
        return vp;
    }

    public void setVp(ventaproducto vp) {
        this.vp = vp;
    }


    public Menus getMenus() {
        if (menus == null) {
            menus = new Menus();
        }
        return menus;
    }

    public void setMenus(Menus menus) {
        this.menus = menus;
    }

    public SubMenus getSubmenus() {
        if (submenus == null) {
            submenus = new SubMenus();
        }
        return submenus;
    }

    public void setSubmenus(SubMenus submenus) {
        this.submenus = submenus;
    }

    public PerfilRoles getPerfilxrol() {
        if (perfilxrol == null) {
            perfilxrol = new PerfilRoles();
        }
        return perfilxrol;
    }

    public void setPerfilxrol(PerfilRoles perfilxrol) {
        this.perfilxrol = perfilxrol;
    }

    public void cargarMenus() {
        getMenus();
        menus.setIdUsuarioMenu(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdUsuario());
        List<Menus> list = menus.ListMenusForUser();
        int cantMenus = list.size();
        M2.pnMnus.removeAll();
        M2.pnMnus.setOpaque(false);
        M2.pnMnus.setLayout(new java.awt.GridLayout(cantMenus, 1, 150, 0));
        lblMnues = new JLabel[cantMenus];
        pnMenuContent = new JPanel[cantMenus];
        int i = 0;
        Iterator<Menus> nombreIterator = list.iterator();
        while (nombreIterator.hasNext()) {
            System.out.println("Menu ------");
            Menus m = nombreIterator.next();
            pnMenuContent[i] = new JPanel();
            M2.pnMnus.add(crearPnMenu(pnMenuContent[i], m.getNombre(), m.getIdMenu()));
            i++;
        }
        setMenus(null);
        M2.pnMnus.updateUI();
              
    }

    public JPanel crearPnMenu(JPanel panel, String texto, int idMenu) {
        getSubmenus();
        submenus.setIdMenu(idMenu);
        List<SubMenus> list = submenus.List();
        int cantSubMenus = list.size();
        JButton btnMnus[] = new JButton[cantSubMenus];
        int i = 0;

        panel.setOpaque(false);
        panel.setLayout(new java.awt.BorderLayout());

        JPanel pnTitleMnuUsers = new JPanel();
        JPanel pnContentMnuUsers = new JPanel();
        JLabel lblTextMenu = new JLabel();
        JSeparator sp = new JSeparator();

        //add tittle mnu
        pnTitleMnuUsers.setOpaque(false);
        pnTitleMnuUsers.setPreferredSize(new java.awt.Dimension(200, 25));
        pnTitleMnuUsers.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTextMenu.setFont(new java.awt.Font("Segoe UI", 3, 11)); // NOI18N
        lblTextMenu.setForeground(new java.awt.Color(255, 255, 255));
        lblTextMenu.setText(texto);

        pnTitleMnuUsers.add(lblTextMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, 190, -1));
        pnTitleMnuUsers.add(sp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 19, 180, -1));

        //add content mnu
        pnContentMnuUsers.setOpaque(false);
        pnContentMnuUsers.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        //add btnMnus
        Iterator<SubMenus> nombreIterator = list.iterator();
        int y = 0;
        while (nombreIterator.hasNext()) {
            SubMenus s = nombreIterator.next();
            btnMnus[i] = new JButton();
            if (s.getSub_menu().equals("Verificar Asistencias")) {
                btnMnus[i].setForeground(new java.awt.Color(255, 0, 0));
            } else {
                btnMnus[i].setForeground(new java.awt.Color(255, 255, 255));
            }
            btnMnus[i].setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
            btnMnus[i].setBackground(new java.awt.Color(54, 63, 73));
            btnMnus[i].setText(s.getSub_menu());
            btnMnus[i].setActionCommand(s.getSub_menu());
            btnMnus[i].setBorder(null);
            btnMnus[i].setBorderPainted(false);
            btnMnus[i].setContentAreaFilled(false);
            btnMnus[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btnMnus[i].setFocusPainted(false);
            btnMnus[i].setHideActionText(true);
            btnMnus[i].setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnMnus[i].setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
            btnMnus[i].setIconTextGap(1);
            btnMnus[i].addActionListener(this);
            pnContentMnuUsers.add(btnMnus[i], new org.netbeans.lib.awtextra.AbsoluteConstraints(10, y, -1, 20));
            listBtnMenus.add(btnMnus[i]);
            i++;
            y += 17;
        }
        setSubmenus(null);
        panel.add(pnTitleMnuUsers, java.awt.BorderLayout.PAGE_START);
        panel.add(pnContentMnuUsers, java.awt.BorderLayout.CENTER);
        panel.updateUI();
        return panel;
    }

    public void cargarPerfilesXRol() {
        getPerfilxrol().setIdRol(UsuarioLogeado.getObjRol().getIdRol());
        List<String> actuales = getPerfilxrol().List();
        //agregar los componentes que se van a validar para habilitar por rol
        Object[] componentes = {MR.btnGuardarUser, MR.mnuAddMenues};
        for (Object componente : componentes) {
            if (componente instanceof JButton) {
                if (!actuales.contains(((JButton) componente).getName())) {
                    ((JButton) componente).setEnabled(false);
                    ((JButton) componente).removeMouseListener(this);
                    ((JButton) componente).setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(34, 41, 50)));
                } else {
                    ((JButton) componente).setEnabled(true);
                    ((JButton) componente).addMouseListener(this);
                    ((JButton) componente).setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
                }
            }
            if (componente instanceof JMenuItem) {
                if (!actuales.contains(((JMenuItem) componente).getName())) {
                    ((JMenuItem) componente).setVisible(false);
                } else {
                    ((JMenuItem) componente).setVisible(true);
                }
            }
        }
    }


}
