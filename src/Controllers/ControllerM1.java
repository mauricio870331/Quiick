/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Pojos.*;
import Utils.ImagensTabla;
import Utils.Reportes;
import Utils.TablaModel;
import Views.Modulo1;
import Views.FrmCapturePict;
import Views.Modales.Busqueda;
import Views.Modales.NuevaSede;
import Views.Modulo2;
import Views.Modulo3;
import Views.Modulo4;
import Views.ModuloRoot;
import com.toedter.calendar.JDateChooser;
import ds.desktop.notify.DesktopNotify;
import fingerUtils.CaptureFinger;
import fingerUtils.ReadFinger;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Mauricio Herrera
 */
public class ControllerM1 implements ActionListener, MouseListener, KeyListener {

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
    private Proveedor pv;
    private EmpresaProveedor ep;
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

    public ControllerM1() throws IOException {
        inicomponents();
    }

    private void inicomponents() throws IOException {
        M1.btnProveedores.addActionListener(this);
        M2.btnProveedores.addActionListener(this);
        M2.btnGuardarProve.addActionListener(this);
        M2.btnViewEmpresaProvedor.addActionListener(this);
        M2.btnEmpresaProveGuardar.addActionListener(this);
        M2.mnuEditEmpresa.addActionListener(this);
        M2.mnuDeleteempresaProve.addActionListener(this);
        M2.mnuEditProveedor.addActionListener(this);
        M2.mnuDeleteProveedor.addActionListener(this);
        M2.btnCancelarProve.addActionListener(this);
////        pr.mnuUsers.addMouseListener(this);
////        pr.mnuGimnasios.addMouseListener(this);     
////        pr.btnGuardar.addActionListener(this);
////        pr.btnCancelar.addActionListener(this);
//        M1.mnuAsocFinger.addActionListener(this);
////        pr.btnBack.addActionListener(this);
//        pr.btnAsistencias.addActionListener(this);
//        pr.btnListUsers.addActionListener(this);
//        pr.mnuUpdate.addActionListener(this);
//        pr.mnuDelete.addActionListener(this);
////        pr.btnGuardaAsistencia.addActionListener(this);
//        pr.btnAsistenciaManual.addActionListener(this);
////        pr.btnAdjuntarfoto.addActionListener(this);
////        pr.btnCapturePhoto.addActionListener(this);
////        pr.btnPrimerReg.addActionListener(this);
////        pr.btnSiguienteReg.addActionListener(this);
////        pr.btnMenosReg.addActionListener(this);
////        pr.btnLastReg.addActionListener(this);
////        pr.btnFindUser.addActionListener(this);
//        pr.mnuGenerarPago.addActionListener(this);
////        pr.cboGym.addActionListener(this);
////        pr.btnBuscarAsistencias.addActionListener(this);
//        pr.btnListMusculos.addActionListener(this);
//        pr.btnListEjercicios.addActionListener(this);
////        pr.btnGuardarMusculo.addActionListener(this);
////        pr.btnCancelarMusculo.addActionListener(this);
//        pr.mnuUpdateMusculo.addActionListener(this);
//        pr.mnuDeleteMusculo.addActionListener(this);
////        pr.btnFindMusculo.addActionListener(this);
////        pr.btnSelectFotoEjercicio.addActionListener(this);
////        pr.btnGuardarEjercicio.addActionListener(this);
//        pr.mnuUpdateEjercicio.addActionListener(this);
//        pr.mnuDeleteEjercicio.addActionListener(this);
//        pr.btnGestionRutinas.addActionListener(this);
////        pr.cboMusculos2.addActionListener(this);
////        pr.tblEjercicios2.addMouseListener(this);
////        pr.tblNewRutina.addMouseListener(this);
////        pr.BtnGuardarRutina.addActionListener(this);
////        pr.btnCaja.addActionListener(this);
//        pr.btnTransaccionCaja.addActionListener(this);
////        pr.btnPagosCaja.addActionListener(this);
////        pr.BtnGenerarPagos.addActionListener(this);
//        pr.mnuBusqueda.addActionListener(this);
////        pr.combotiposService.addActionListener(this);
////        pr.comboDescuentos.addActionListener(this);
////        pr.btnCancelarPagos.addActionListener(this);
//        pr.mnuHistoryPays.addActionListener(this);
////        pr.btnBuscarPago.addActionListener(this);
//        pr.mnuEditFechasPagos.addActionListener(this);
//        pr.mnuDeletePagos.addActionListener(this);
////        pr.btnEditPago.addActionListener(this);
////        pr.btnEditPagacancel.addActionListener(this);
////        pr.cboRol.addActionListener(this);
////        pr.txtUser.setEnabled(false);
////        pr.txtUser.setText(userFormat.format(new Date()));
////        pr.txtClave.setEnabled(false);
////        pr.txtClave.setText("123456");
////        pr.btnCancelarEjercicio.addActionListener(this);
////        pr.cldNacimiento.setDate(new Date());
////        pr.btnReporteCaja.addActionListener(this);
////        pr.btnBackReports.addActionListener(this);
////        pr.btnGenerarReporteByTipo.addActionListener(this);
////        pr.preloader.setVisible(false);
////        pr.combotiposService1.addActionListener(this);
        Adaptador();
//        getMiCaja().CierreCajasAuto();
//        setMiCaja(null);
//        cargarTiposDocumentos();
//        cargarRoles();
//        cargarEmpresas();
//        cargarTblUsers(filtro);
//        showPanel(2, "PnProveedores");
//        System.out.println("pr.pnMicajaEstado.getText() " + pr.pnMicajaEstado.getText());
        MR.btnGuardarEmpresa.addActionListener(this);
        MR.btnCancelarEmpresa.addActionListener(this);
//        MR.btnEmpresas.addMouseListener(this);
        MR.mnuEditEmpresa.addActionListener(this);
        MR.mnuDeleteEmpresa.addActionListener(this);
        MR.mnuNewSede.addActionListener(this);
        MR.btnGuardarRol.addActionListener(this);
//        MR.btnRoles.addActionListener(this);
        MR.btnCancelarRol.addActionListener(this);
        MR.mnuEditRol.addActionListener(this);
        MR.mnuDeleteRol.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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
//        if (e.getSource() == pr.btnTransaccionCaja || e.getSource() == pr.btnBackReports) {
//            try {
//                EstadoMiCaja();
//                showPanel("btnTransaccionCaja");
//            } catch (ClassNotFoundException ex) {
//                System.out.println("Error al Validar Estado de la caja.");
//            }
//        }
//
//        if (e.getSource() == pr.btnCaja) {
//            if (pr.pnMicajaEstado.getText().equalsIgnoreCase("Cerrada")) {
//                String base = JOptionPane.showInputDialog(null, "Base :", "Abrir Turno", 1);
//                System.out.println("base = " + base);
//                if (base == null || base.equals("")) {
//                    base = "0";
//                }
//                getMiCaja();
//                MiCaja.setEstado("A");
//                MiCaja.setFechainicio(new Date());
//                MiCaja.setFechaFinal(new Date());
//                MiCaja.setMontoFinal(new BigDecimal(0));
//                MiCaja.setMontoInicial(new BigDecimal(base));
//                MiCaja.setMontoVenta(new BigDecimal(0));;
//                MiCaja.getObjCajaxUserID().setIdPersona(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdPersona()));
//                MiCaja.getObjCajaxUserID().setIdSede(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdSede()));
//                MiCaja.getObjCajaxUserID().setIdUsuario(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdUsuario()));
//                MiCaja.getObjCajaxUserID().setIdempresa(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdempresa()));
//                MiCaja.getObjCajaxUserID().setUsuario(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getUsuario());
//
//                if (MiCaja.create() > 0) {
//                    try {
//                        DesktopNotify.showDesktopMessage("Información", "Caja Abierta Con Exito", DesktopNotify.INFORMATION, 5000L);
//                        EstadoMiCaja();
//                    } catch (ClassNotFoundException ex) {
//                        Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//
//            } else {
//                Object[] opciones = {"Si", "No"};
//                int eleccion = JOptionPane.showOptionDialog(null, "¿Desea Cerrar la Caja?", "Mensaje de Confirmación",
//                        JOptionPane.YES_NO_OPTION,
//                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Si");
//                if (eleccion == JOptionPane.YES_OPTION) {
//                    System.out.println("cerrarndo caja");
//                    MiCaja.setEstado("C");
//                    MiCaja.getObjCajaxUserID().setIdPersona(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdPersona()));
//                    MiCaja.getObjCajaxUserID().setIdSede(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdSede()));
//                    MiCaja.getObjCajaxUserID().setIdUsuario(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdUsuario()));
//                    MiCaja.getObjCajaxUserID().setIdempresa(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdempresa()));
//                    MiCaja.getObjCajaxUserID().setUsuario(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getUsuario());
//
//                    if (MiCaja.CierreCaja() > 0) {
//                        try {
//                            DesktopNotify.showDesktopMessage("Información", "Caja Cerrada Con Exíto", DesktopNotify.INFORMATION, 5000L);
//                            EstadoMiCaja();
//                        } catch (ClassNotFoundException ex) {
//                            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }
//            }
//
//        }
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

        if (e.getSource() == MR.btnGuardarEmpresa) {
            Object[] componentes = {MR.txtDocNit, MR.txtNomEmpresa};
            if (validarCampos(componentes, "", MR) == 0) {
                getEmpresas();
                empresas.setNit(MR.txtDocNit.getText());
                empresas.setNombre(MR.txtNomEmpresa.getText());
                empresas.setDireccion(MR.txtDirEmpresa.getText());
                empresas.setTelefonos(MR.txtTelEmpresa.getText());
                empresas.setPathLogo(foto);
                empresas.setCreate_at(new Date());
                int result = 0;
                String msn = "Empresa creada con exito..!";
                String msnerror = "Ocurrio un error al crear la empresa..!";
                if (MR.btnGuardarEmpresa.getText().equalsIgnoreCase("Guardar")) {
                    result = empresas.create();
                } else {
                    msn = "Empresa editada con exito..!";
                    msnerror = "Ocurrio un error al editar la empresa..!";
                    empresas.setIdEmpresa(((Empresas) currentObject).getIdEmpresa());
                    result = empresas.edit();
                }
                if (result > 0) {
                    DesktopNotify.showDesktopMessage("Aviso..!", msn, DesktopNotify.SUCCESS, 5000L);
                    setFoto("");
                    setEmpresas(null);
                    ListEmpresas("");
                    currentObject = null;
                    LimpiarCampos("empresas");
                    MR.btnGuardarEmpresa.setText("Guardar");
                } else {
                    DesktopNotify.showDesktopMessage("Aviso..!", msnerror, DesktopNotify.FAIL, 5000L);
                }

            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", "Los campos Marcados en rojo son obligatorios...!", DesktopNotify.ERROR, 5000L);
            }
        }

        if (e.getSource() == MR.btnCancelarEmpresa) {
            currentObject = null;
            LimpiarCampos("empresas");
        }

        if (e.getSource() == MR.mnuEditEmpresa) {
            MR.btnGuardarEmpresa.setText("Editar");
            int fila = MR.tblEmpresas.getSelectedRow();
            if (fila >= 0) {
                getEmpresas();
                currentObject = empresas.findById(Integer.parseInt(MR.tblEmpresas.getValueAt(fila, 0).toString()));
                if (currentObject != null) {
                    MR.txtDocNit.setText(((Empresas) currentObject).getNit());
                    MR.txtNomEmpresa.setText(((Empresas) currentObject).getNombre());
                    MR.txtDirEmpresa.setText(((Empresas) currentObject).getDireccion());
                    MR.txtTelEmpresa.setText(((Empresas) currentObject).getTelefonos());
                    InputStream img = ((Empresas) currentObject).getLogo();
                    if (img != null) {
                        BufferedImage bi;
                        try {
                            bi = ImageIO.read(img);
                            ii = new ImageIcon(bi);
                            Image conver = ii.getImage();
                            Image tam = conver.getScaledInstance(MR.lblLogoEmpresa.getWidth(), MR.lblLogoEmpresa.getHeight(), Image.SCALE_SMOOTH);
                            iin = new ImageIcon(tam);
                            MR.lblLogoEmpresa.setIcon(iin);
                        } catch (IOException ex) {
                            System.out.println("error " + ex);
                        }

                    } else {
                        MR.lblLogoEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user40.png")));
                    }
                }
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
            }
        }

        if (e.getSource() == MR.mnuDeleteEmpresa) {
            int fila = MR.tblEmpresas.getSelectedRow();
            if (fila >= 0) {
                Object[] opciones = {"Si", "No"};
                int eleccion = JOptionPane.showOptionDialog(null, "¿En realidad, desea eliminar la empresa?", "Mensaje de Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Si");
                if (eleccion == JOptionPane.YES_OPTION) {
                    int id_empresa = Integer.parseInt(MR.tblEmpresas.getValueAt(fila, 0).toString());
                    getEmpresas();
                    empresas.setIdEmpresa(id_empresa);
                    if (empresas.remove() > 0) {
                        DesktopNotify.showDesktopMessage("Informacion..!", "Empresa Eliminada con exito", DesktopNotify.SUCCESS, 6000L);
                        setEmpresas(null);
                        ListEmpresas("");
                    } else {
                        DesktopNotify.showDesktopMessage("Aviso..!", "Ocurrio un error al eliminar la empresa", DesktopNotify.ERROR, 5000L);
                    }
                }
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
            }
        }

        if (e.getSource() == MR.mnuNewSede) {
            int fila = MR.tblEmpresas.getSelectedRow();
            if (fila >= 0) {
                try {
                    NuevaSede s = new NuevaSede(MR, true, Integer.parseInt(MR.tblEmpresas.getValueAt(fila, 0).toString()));
                    s.setLocationRelativeTo(null);
                    s.setVisible(true);
                } catch (SQLException ex) {
                    System.out.println("error linea 1541 " + ex);
                }
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
            }
        }

        //******Crud Roles ********\\
        if (e.getSource() == MR.btnGuardarRol) {
            Object[] componentes = {MR.txtDescRol, MR.cboEstadoRol};
            if (validarCampos(componentes, "", MR) == 0) {
                getRol();
                rol.setDescripcion(MR.txtDescRol.getText());
                rol.setEstado((String) MR.cboEstadoRol.getSelectedItem());
                int result = 0;
                String msn = "Rol creado con exito..!";
                String msnerror = "Ocurrio un error al crear el rol..!";
                if (MR.btnGuardarRol.getText().equalsIgnoreCase("Guardar")) {
                    result = rol.create();
                } else {
                    msn = "Rol editado con exito..!";
                    msnerror = "Ocurrio un error al editar el rol..!";
                    rol.setIdRol(((Rol) currentObject).getIdRol());
                    result = rol.edit();
                }
                if (result > 0) {
                    DesktopNotify.showDesktopMessage("Aviso..!", msn, DesktopNotify.SUCCESS, 5000L);
                    setRol(null);
                    ListRoles("");
                    currentObject = null;
                    LimpiarCampos("rol");
                    MR.btnGuardarRol.setText("Guardar");
                } else {
                    DesktopNotify.showDesktopMessage("Aviso..!", msnerror, DesktopNotify.FAIL, 5000L);
                }

            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", "Los campos Marcados en rojo son obligatorios...!", DesktopNotify.ERROR, 5000L);
            }
        }

        if (e.getSource() == MR.btnCancelarRol) {
            currentObject = null;
            LimpiarCampos("rol");
        }

        if (e.getSource() == MR.mnuEditRol) {
            MR.btnGuardarRol.setText("Editar");
            int fila = MR.tblRoles.getSelectedRow();
            if (fila >= 0) {
                getRol();
                currentObject = rol.getRolbyId(Integer.parseInt(MR.tblRoles.getValueAt(fila, 0).toString()));
                if (currentObject != null) {
                    MR.txtDescRol.setText(((Rol) currentObject).getDescripcion());
                    MR.cboEstadoRol.setSelectedItem((((Rol) currentObject).getEstado().equalsIgnoreCase("A")) ? "Activo" : "Inactivo");
                }
                setRol(null);
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
            }
        }        
        
        if (e.getSource() == MR.mnuDeleteRol) {
            int fila = MR.tblRoles.getSelectedRow();
            if (fila >= 0) {
                Object[] opciones = {"Si", "No"};
                int eleccion = JOptionPane.showOptionDialog(null, "¿En realidad, desea eliminar el rol?", "Mensaje de Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Si");
                if (eleccion == JOptionPane.YES_OPTION) {                    
                    getRol();
                    rol.setIdRol(Integer.parseInt(MR.tblRoles.getValueAt(fila, 0).toString()));
                    if (rol.remove() > 0) {
                        DesktopNotify.showDesktopMessage("Informacion..!", "Rol Eliminado con exito", DesktopNotify.SUCCESS, 6000L);
                        setRol(null);
                        ListRoles("");
                    } else {
                        DesktopNotify.showDesktopMessage("Aviso..!", "Ocurrio un error al eliminar el rol", DesktopNotify.ERROR, 5000L);
                    }
                }
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", "Debes seleccionar un registro", DesktopNotify.ERROR, 5000L);
            }
        }

        //******Fin Crud Roles ********\\
    }

    private void addFilter() {
        FileChooser.setFileFilter(new FileNameExtensionFilter("Imagen (*.PNG)", "png"));
        FileChooser.setFileFilter(new FileNameExtensionFilter("Imagen (*.JPG)", "jpg"));
    }

    public void showPanel(int Modulo, String string) {
        switch (Modulo) {
            case 1:
                M2.setVisible(false);
                MR.setVisible(true);
                MR.setVistaActual(string);
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
                M1.setVisible(false);
                M2.setVisible(true);
                M2.setVistaActual(string);
                switch (string) {
                    case "PnProveedores":
                        M2.PnEmpresaProveedor.setVisible(false);
                        M2.PnProveedores.setVisible(true);
                        break;
                    case "PnEmpresaProveedor":
                        M2.PnEmpresaProveedor.setVisible(true);
                        M2.PnProveedores.setVisible(false);
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

    private void cargarTblNewRutina() throws IOException {
//        pr.tblNewRutina.setDefaultRenderer(Object.class, new ImagensTabla());
//        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
//        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
//        DefaultTableModel modelo;
//        String Titulos[] = {"idEjercicio", "Accion", "Ejercicio", "Musculo", "", "Dia", "Series", "Repeticiones", "Observación"};
//
//        modelo = new DefaultTableModel(null, Titulos) {
//            @Override
//            public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
//                return column == 6 || column == 7 || column == 8;
//
//            }
//        };
//
//        Object[] columna = new Object[9];
//        Iterator<Ejercicios> itr = newRutina.iterator();
//        while (itr.hasNext()) {
//            Musculos m = new Musculos();
//            dias d = new dias();
//            Ejercicios e = itr.next();
//            columna[0] = (int) e.getObjEjerciciosID().getIdEjercicio();
//            columna[1] = "Quitar";
//            columna[2] = e.getDescripcion();
//            columna[3] = m.getNombreMusculoById(e.getObjEjerciciosID().getIdMusculo());
//            columna[4] = (int) e.getObjEjerciciosID().getIdMusculo();
//            columna[5] = d.getDiaById(e.getDia());
//            columna[6] = "";
//            columna[7] = "";
//            columna[8] = "";
//            modelo.addRow(columna);
//            m = null;
//        }
//        pr.tblNewRutina.setModel(modelo);
//        TableRowSorter<TableModel> ordenar = new TableRowSorter<>(modelo);
//        pr.tblNewRutina.setRowSorter(ordenar);
//        pr.tblNewRutina.getColumnModel().getColumn(0).setMaxWidth(0);
//        pr.tblNewRutina.getColumnModel().getColumn(0).setMinWidth(0);
//        pr.tblNewRutina.getColumnModel().getColumn(0).setPreferredWidth(0);
//        pr.tblNewRutina.getColumnModel().getColumn(1).setMaxWidth(60);
//        pr.tblNewRutina.getColumnModel().getColumn(1).setMinWidth(60);
//        pr.tblNewRutina.getColumnModel().getColumn(1).setPreferredWidth(60);
//        pr.tblNewRutina.getColumnModel().getColumn(4).setMaxWidth(0);
//        pr.tblNewRutina.getColumnModel().getColumn(4).setMinWidth(0);
//        pr.tblNewRutina.getColumnModel().getColumn(4).setPreferredWidth(0);
////        pr.tblNewRutina.getColumnModel().getColumn(6).setMaxWidth(0);
////        pr.tblNewRutina.getColumnModel().getColumn(6).setMinWidth(0);
////        pr.tblNewRutina.getColumnModel().getColumn(6).setPreferredWidth(0);
//        pr.tblNewRutina.getColumnModel().getColumn(2).setCellRenderer(Alinear);
//        pr.tblNewRutina.setModel(modelo);
//        setMusculos(null);
    }

    private void cargarTblRutinas(String filtro) throws IOException {
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

    private void clearFormUsers() {
//        pr.btnGuardar.setText("Guardar");
//        pr.cboTiposDoc.setSelectedIndex(0);
//        pr.cboRol.setSelectedIndex(0);
//        pr.txtDoc.setText("");
//        pr.txtDireccion.setText("");
//        pr.txtNombres.setText("");
//        pr.txtApellidos.setText("");
//        pr.txtTelefonos.setText("");
//        pr.cboTiposDoc.requestFocus();
//        pr.fileChosed.setText("");
//        pr.cboGym.setSelectedIndex(0);
//        if (pr.cboSedes.getItemCount() > 0) {
//            pr.cboSedes.setSelectedIndex(0);
//        }
//        pr.txtCorreo.setText("");
//        pr.cldNacimiento.setDate(new Date());
//        pr.cboSexo.setSelectedIndex(0);
//        pr.txtUser.setText("");
//        pr.txtClave.setText("");
//        pr.idpersonaOld.setText("");
//        pr.idUsuarioOld.setText("");
//        pr.idRolxuserOld.setText("");
//        pr.idEmpresaOld.setText("");
//        pr.idSedeOld.setText("");
//        pr.usuarioOld.setText("");
//        pr.idRolOld.setText("");
//        setFoto("");
//        setCountAction(0);
//        pr.cLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user40.png")));
//        Object[] componentes = {pr.cboTiposDoc, pr.cboRol, pr.txtDoc, pr.txtDireccion,
//            pr.txtNombres, pr.txtApellidos, pr.txtTelefonos, pr.cboGym, pr.cboSedes, pr.cldNacimiento, pr.cboSexo,
//            pr.txtUser, pr.txtClave, pr.cldDesdePagos, pr.cldHastaPagos, pr.cldFechaPagoHistory, pr.cldFechaPagoDesde, pr.cldFechaPagoHasta};
//        resetCampos(componentes);
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

    public int validarCampos(Object[] componentes, String nameComponent, Object Modulo) {
//        ((Modulo1) Modulo).txtDocNit.getText();
        int countErrors = 0;
        for (Object componente : componentes) {
            if (componente instanceof JTextField) {
                boolean equals = ((JTextField) componente).getText().equals("");
                if (equals) {
                    countErrors++;
                    ((JTextField) componente).setBorder(BorderFactory.createLineBorder(Color.decode("#EE1313")));  //2C6791                  
                } else {
                    ((JTextField) componente).setBorder(BorderFactory.createLineBorder(Color.decode("#848484")));
                }
            }
            if (componente instanceof JComboBox) {
                boolean equals = false;
                if (((JComboBox) componente).getSelectedItem() instanceof TipoDocumento) {
//                    TipoDocumento t = (TipoDocumento) Modulo.cboTiposDoc.getSelectedItem();
//                    equals = t.toString().equals("Seleccione");
                } else if (((JComboBox) componente).getSelectedItem() instanceof Rol) {
//                    Rol r = (Rol) pr.cboRol.getSelectedItem();
//                    equals = r.toString().equals("Seleccione");
                } else if (((JComboBox) componente).getSelectedItem() instanceof Empresas) {
//                    Empresas r = (Empresas) pr.cboGym.getSelectedItem();
//                    equals = r.toString().equals("Seleccione");
                } else if (((JComboBox) componente).getSelectedItem() instanceof Sedes) {
//                    Sedes r = (Sedes) pr.cboSedes.getSelectedItem();
//                    equals = r.toString().equals("Seleccione");
                } else if (((JComboBox) componente).getSelectedItem() instanceof Musculos) {
//                    Musculos r;
//                    if (nameComponent.equals("cboMusculos")) {
//                        r = (Musculos) pr.cboMusculos.getSelectedItem();
//                    } else {
//                        r = (Musculos) pr.cboMusculos2.getSelectedItem();
//                    }
//                    equals = r.toString().equals("Seleccione");
                } else if (((JComboBox) componente).getSelectedItem() instanceof dias) {
//                    dias d = (dias) pr.cboDia.getSelectedItem();
//                    equals = d.toString().equals("Seleccione");
                } else if (((JComboBox) componente).getSelectedItem() instanceof String) {
                    equals = ((JComboBox) componente).getSelectedItem().equals("Seleccione");
                }
                if (equals) {
                    countErrors++;
                    ((JComboBox) componente).setBorder(BorderFactory.createLineBorder(Color.decode("#EE1313")));  //2C6791                  
                } else {
                    ((JComboBox) componente).setBorder(BorderFactory.createLineBorder(Color.decode("#848484")));
                }
            }
            if (componente instanceof JDateChooser) {
                boolean equals = ((JDateChooser) componente).getDate() == null;
                if (equals) {
                    countErrors++;
                    ((JDateChooser) componente).setBorder(BorderFactory.createLineBorder(Color.decode("#EE1313")));  //2C6791                  
                } else {
                    ((JDateChooser) componente).setBorder(BorderFactory.createLineBorder(Color.decode("#848484")));
                }
            }
        }
        return countErrors;
    }

    public void resetCampos(Object[] componentes) {
        for (Object componente : componentes) {
            if (componente instanceof JTextField) {
                ((JTextField) componente).setBorder(BorderFactory.createLineBorder(Color.decode("#848484")));
            }
            if (componente instanceof JComboBox) {
                ((JComboBox) componente).setBorder(BorderFactory.createLineBorder(Color.decode("#848484")));
            }
            if (componente instanceof JDateChooser) {
                ((JDateChooser) componente).setBorder(BorderFactory.createLineBorder(Color.decode("#848484")));
            }
        }
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
//
//            int fila = pr.tblEjercicios2.getSelectedRow();
//            String idEjercicio = pr.tblEjercicios2.getValueAt(fila, 0).toString();
//            String OpcionEjercicioSelected = pr.tblEjercicios2.getValueAt(fila, 1).toString();
//            Ejercicios ej = getEjercicio().getEjercicioById(Integer.parseInt(idEjercicio));
//            if (fila >= 0) {
//                Object[] componentes = {pr.txtDescRutina, pr.cboDia, pr.cboMusculos2};
//                if (validarCampos(componentes, "cboMusculos2") == 0) {
//                    if (OpcionEjercicioSelected.equals("Agregar")) {
//                        boolean existe = false;
//                        for (Ejercicios ejercicios : newRutina) {
//                            if (ejercicios.getObjEjerciciosID().getIdEjercicio() == ej.getObjEjerciciosID().getIdEjercicio()) {
//                                existe = true;
//                            }
//                        }
//                        for (int i = 0; i < allEjercicios.size(); i++) {
//                            if (allEjercicios.get(i).getObjEjerciciosID().getIdEjercicio() == ej.getObjEjerciciosID().getIdEjercicio()) {
//                                allEjercicios.get(i).setOpcion("Quitar");
//                            }
//                        }
//                        if (!existe) {
//                            dias d = (dias) pr.cboDia.getSelectedItem();
//                            ej.setDia(d.getIdDias());
//                            newRutina.add(ej);
//                            d = null;
//                        } else {
//                            DesktopNotify.showDesktopMessage("Aviso..!", "El ejercicio seleccionado ya esta en la lista..!", DesktopNotify.WARNING, 6000L);
//                        }
//                        pr.tblEjercicios2.setValueAt("Quitar", fila, 1);
//                    } else {
//                        pr.tblEjercicios2.setValueAt("Agregar", fila, 1);
//
//                        for (int i = 0; i < allEjercicios.size(); i++) {
//                            if (allEjercicios.get(i).getObjEjerciciosID().getIdEjercicio() == ej.getObjEjerciciosID().getIdEjercicio()) {
//                                allEjercicios.get(i).setOpcion("Agregar");
//                            }
//                        }
//
//                        for (int i = 0; i < newRutina.size(); i++) {
//                            if (newRutina.get(i).getObjEjerciciosID().getIdEjercicio() == ej.getObjEjerciciosID().getIdEjercicio()) {
//                                newRutina.remove(i);
//                            }
//                        }
//                    }
//                    try {
//                        if (newRutina.isEmpty()) {
//                            newRutina.clear();
//                        }
//                        cargarTblNewRutina();
//                    } catch (IOException ex) {
//                        System.out.println("error " + ex);
//                    }
//                    setEjercicio(null);
//                    ej = null;
//                } else {
//                    DesktopNotify.showDesktopMessage("Informacion..!", "Los campos marcados en rojo son obligatorios..!", DesktopNotify.ERROR, 6000L);
//                }
//            }

//        }
//
//        if (e.getSource() == pr.tblNewRutina) {
//            int fila = pr.tblNewRutina.getSelectedRow();
//            int column = pr.tblNewRutina.getSelectedColumn();
//            if (fila >= 0 && column == 1) {
//                try {
//                    String idEjercicio = pr.tblNewRutina.getValueAt(fila, 0).toString();
//                    Ejercicios ej = getEjercicio().getEjercicioById(Integer.parseInt(idEjercicio));
//                    Musculos musculo = (Musculos) pr.cboMusculos2.getSelectedItem();
//                    //pr.tblEjercicios2.setValueAt("Agregar", Integer.parseInt(pr.tblNewRutina.getValueAt(fila, 6).toString()), 1);
//                    for (int i = 0; i < allEjercicios.size(); i++) {
//                        if (allEjercicios.get(i).getObjEjerciciosID().getIdEjercicio() == Integer.parseInt(idEjercicio)) {
//                            allEjercicios.get(i).setOpcion("Agregar");
//                        }
//                    }
//                    for (int i = 0; i < newRutina.size(); i++) {
//                        if (newRutina.get(i).getObjEjerciciosID().getIdEjercicio() == Integer.parseInt(idEjercicio)) {
//                            newRutina.remove(i);
//                        }
//                    }
//                    if (pr.tblEjercicios2.getRowCount() > 0 && musculo.getIdMusculo() == ej.getObjEjerciciosID().getIdMusculo()) {
//                        cargarTblEjercicios2(ej.getObjEjerciciosID().getIdMusculo());
//                    }
//                    if (newRutina.isEmpty()) {
//                        newRutina.clear();
//                    }
//                    cargarTblNewRutina();
//                    ej = null;
//                } catch (IOException ex) {
//                    System.out.println("error " + ex);
//                }
//            }
//
//        }
    }

    public void EstadoMiCaja() throws ClassNotFoundException {
//        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
//        getMiCaja();
//        System.out.println("iniciamos");
//        if (pr.pnMicajaEstado.getText().equals("CERRADA") || pr.pnMicajaEstado.getText().equals("Estado")) {
//            pr.BtnGenerarPagos.setEnabled(false);
//        }
//        MiCaja.getObjCajaxUserID().setIdUsuario(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdUsuario()));
//        System.out.println("seguimos");
//        MiCaja = MiCaja.MiCaja();
//        System.out.println("MiCaja " + MiCaja);
//        if (MiCaja != null) {
//            pr.pnMicajaMns.setVisible(true);
//            pr.pnMicajaEstado.setText("Abierta");
//            pr.btnCaja.setText("Cerrar");
////            pr.btnCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Wallet.png")));
//            CargarDatosCaja(1);
////            pr.btnReporteCaja.setEnabled(false);
//            pr.btnDetallePago.setEnabled(true);
//            pr.BtnGenerarPagos.setEnabled(true);
//            System.out.println("Mi caja : " + MiCaja.getObjCajaxUserID().getIdcaja());
//            pr.pnMicajaMns.setText("MI CAJA #" + MiCaja.getObjCajaxUserID().getIdcaja() + "         " + dt1.format(new Date()));
//            pr.pnMicajaMns2.setText("Historial de Pagos");
//        } else {
//            pr.pnMicajaMns.setVisible(false);
//            CargarDatosCaja(2);
//            this.pr.pnMicajaMns2.setVisible(true);
//            pr.btnDetallePago.setEnabled(false);
//            pr.btnCaja.setText("Abrir");
//            this.pr.pnMicajaMns2.setText("Datos de la ultima caja Abierta ");
//            pr.pnMicajaEstado.setText("CERRADA");
//            pr.BtnGenerarPagos.setEnabled(false);
////            pr.btnCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/CashRegister.png")));
////            pr.btnReporteCaja.setEnabled(true);
//        }
    }

    public void CargarDatosCaja(int condicion) {

//        ArrayList<PagoService> listUsuario = new ArrayList();
//        getPagoService();
//        if (MiCaja != null) {
//            pagoService.getObjPagoServiceID().setIdcaja(MiCaja.getObjCajaxUserID().getIdcaja());
//        } else {
//            pagoService.getObjPagoServiceID().setIdUsuario(new BigDecimal(UsuarioLogeado.getObjUsuario().getObjUsuariosID().getIdUsuario()));
//        }
//
//        listUsuario = (ArrayList<PagoService>) pagoService.ListPagosXUsers(condicion);
//
//        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
//        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
//        DefaultTableModel modelo;
//        String Titulos[] = {"Factura", "Servicio", "Valor"};
//        modelo = new DefaultTableModel(null, Titulos) {
//            @Override
//            public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
//                return false;
//            }
//        };
//        Object[] columna = new Object[4];
//        Iterator<PagoService> listPagos = listUsuario.iterator();
//        while (listPagos.hasNext()) {
//            PagoService u = listPagos.next();
//            columna[0] = u.getObjPagoServiceID().getIdPago();
//            columna[1] = u.getObjTipoService().getDescripcion();
//            columna[2] = u.getValorTotal();
//
//            modelo.addRow(columna);
//        }
//        pr.tblListaPagos.setModel(modelo);
//        pr.tblListaPagos.getColumnModel().getColumn(0).setPreferredWidth(100);
//        pr.tblListaPagos.getColumnModel().getColumn(1).setPreferredWidth(100);
//        pr.tblListaPagos.getColumnModel().getColumn(2).setPreferredWidth(100);
//        pr.tblListaPagos.setRowHeight(30);
////        pr.tblListaPagos.getColumnModel().getColumn(2).setCellRenderer(Alinear);
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

    private void cargarAllEjericios() {
        allEjercicios.clear();
        newRutina.clear();
        allEjercicios = (ArrayList<Ejercicios>) getEjercicio().List();
        setEjercicio(null);
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

    }

    @Override
    public void keyReleased(KeyEvent e) {
//        if (e.getSource() == pr.tblNewRutina) {
//            int columna = pr.tblNewRutina.getSelectedColumn();
//            int fila = pr.tblNewRutina.getSelectedRow();
//            int idEjercicio = (int) pr.tblNewRutina.getValueAt(fila, 0);
//            char tecla = e.getKeyChar();
//            if (tecla == KeyEvent.VK_ENTER) {
//                if (columna == 6) {
//                    for (int i = 0; i < newRutina.size(); i++) {
//                        if (newRutina.get(i).getObjEjerciciosID().getIdEjercicio() == idEjercicio) {
//                            newRutina.get(i).setSeries((int) pr.tblNewRutina.getValueAt(fila, 6));
//                        }
//                    }
//                }
//            }
//        }
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
        M1.addWindowListener(new WindowAdapter() {
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
        t.setIdTipoDocumento(0);
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

    public void LimpiarCampos(String menu) {
        switch (menu) {
            case "proveedores":
                M2.txtDocProve.setText("");
                M2.txtNombresProve.setText("");
                M2.txtApellidosProve.setText("");
                M2.txtTelefonosProve.setText("");
                break;
            case "empresas":
                MR.txtDocNit.setText("");
                MR.txtNomEmpresa.setText("");
                MR.txtDirEmpresa.setText("");
                MR.txtTelEmpresa.setText("");
                setFoto("");
                setEmpresas(null);
                break;
            case "rol":
                MR.txtDescRol.setText("");
                MR.cboEstadoRol.setSelectedIndex(0);
                break;
        }
    }

    public void ListEmpresas(String filtro) {
        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        DefaultTableModel modelo;
        String Titulos[] = {"", "NIT", "Nombre", "Direccion", "Telefono"};
        getEmpresas();
        ArrayList<Empresas> listEmpresas = new ArrayList();
        if (filtro.length() <= 0) {
            listEmpresas = (ArrayList<Empresas>) empresas.List();
        } else if (filtro.length() > 0) {
            //listEmpresaProve = (ArrayList<EmpresaProveedor>) ep.BuscarProducto(filtro);
        }
        modelo = new DefaultTableModel(null, Titulos) {
            @Override
            public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
                return false;
            }
        };
        Object[] columna = new Object[5];
        Iterator<Empresas> itr = listEmpresas.iterator();
        while (itr.hasNext()) {
            Empresas e = itr.next();
            columna[0] = e.getIdEmpresa();
            columna[1] = e.getNit();
            columna[2] = e.getNombre();
            columna[3] = e.getDireccion();
            columna[4] = e.getTelefonos();
            modelo.addRow(columna);
        }
        MR.tblEmpresas.setModel(modelo);
        MR.tblEmpresas.getColumnModel().getColumn(0).setPreferredWidth(0);
        MR.tblEmpresas.getColumnModel().getColumn(1).setPreferredWidth(150);
        MR.tblEmpresas.getColumnModel().getColumn(1).setCellRenderer(Alinear);
        MR.tblEmpresas.getColumnModel().getColumn(2).setCellRenderer(Alinear);
        MR.tblEmpresas.getColumnModel().getColumn(3).setCellRenderer(Alinear);
        MR.tblEmpresas.getColumnModel().getColumn(4).setCellRenderer(Alinear);
//        table.setRowHeight(30);
    }

    public void ListRoles(String filtro) {
        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        DefaultTableModel modelo;
        String Titulos[] = {"", "Descripción", "Estado"};
        getRol();
        ArrayList<Rol> listRoles = new ArrayList();
        if (filtro.length() <= 0) {
            listRoles = (ArrayList<Rol>) rol.List();
        } else if (filtro.length() > 0) {
            //listEmpresaProve = (ArrayList<EmpresaProveedor>) ep.BuscarProducto(filtro);
        }
        modelo = new DefaultTableModel(null, Titulos) {
            @Override
            public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
                return false;
            }
        };
        Object[] columna = new Object[4];
        Iterator<Rol> itr = listRoles.iterator();
        while (itr.hasNext()) {
            Rol r = itr.next();
            columna[0] = r.getIdRol();
            columna[1] = r.getDescripcion();
            columna[2] = (r.getEstado().equalsIgnoreCase("A") ? "Activo" : "Inactivo");
            modelo.addRow(columna);
        }
        MR.tblRoles.setModel(modelo);
        MR.tblRoles.getColumnModel().getColumn(0).setPreferredWidth(0);
        MR.tblRoles.getColumnModel().getColumn(1).setCellRenderer(Alinear);
        MR.tblRoles.getColumnModel().getColumn(2).setCellRenderer(Alinear);
//        table.setRowHeight(30);
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

}
