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
import Utils.VistaActual;
import Views.Modulo1;
import Views.FrmCapturePict;
import Views.Modales.Busqueda;
import Views.Modales.NuevaSede;
import Views.Modales.NuevoProducto;
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
    private Proveedor pv;
    private EmpresaProveedor ep;
    public categoria c;
    public iva i;
    public Unidad u;
    private producto pr;
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

    public ControllerM2() throws IOException {
        inicomponents();
    }

    private void inicomponents() throws IOException {
        M2.btnProveedores.addActionListener(this);
        M2.btnGuardarProve.addActionListener(this);
        M2.btnViewEmpresaProvedor.addActionListener(this);
        M2.btnEmpresaProveGuardar.addActionListener(this);
        M2.mnuEditEmpresa.addActionListener(this);
        M2.mnuDeleteempresaProve.addActionListener(this);
        M2.mnuEditProveedor.addActionListener(this);
        M2.mnuDeleteProveedor.addActionListener(this);
        M2.btnCancelarProve.addActionListener(this);
        M2.btnCompras.addActionListener(this);
        M2.btnCompraTrans.addActionListener(this);
        M2.BntTranCompraBuscar.addActionListener(this);
        M2.BntTranCompraNuevo.addActionListener(this);
        Adaptador();
        cargarMenu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == M2.BntTranCompraNuevo) {
            try {
                new NuevoProducto(M2, true, this).setVisible(true);
            } catch (SQLException ex) {
                System.out.println("Error al abrir modal");
            }
        }

        if (e.getSource() == M2.BntTranCompraBuscar) {
            try {
                new Busqueda(M2, true, 2).setVisible(true);
            } catch (SQLException ex) {
                System.out.println("Error al abrir modal");
            }
        }

        if (e.getSource() == M2.btnCompraTrans) {
            ListProductosAñadidos();
            showPanel(2, "PnTransCompra");
        }

        if (e.getSource() == M2.btnCompras) {
            showPanel(2, "PnCompras");
        }

        if (e.getSource() == M2.btnProveedores) {
            System.out.println("Ingreso a proveedores");
            CargarDatosInicialesProveedores(1, null);
            cargarTiposDocumentosProveedor();
            ListProveedores();
            showPanel(2, "PnProveedores");
        }

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
                break;
            case 2:
                switch (string) {
                    case "PnProveedores":
                        M2.PnEmpresaProveedor.setVisible(false);
                        M2.PnProveedores.setVisible(true);
                        M2.PnCompras.setVisible(false);
                        M2.PnTransCompra.setVisible(false);
                        break;
                    case "PnEmpresaProveedor":
                        M2.PnEmpresaProveedor.setVisible(true);
                        M2.PnProveedores.setVisible(false);
                        M2.PnCompras.setVisible(false);
                        M2.PnTransCompra.setVisible(false);
                        break;
                    case "PnCompras":
                        M2.PnEmpresaProveedor.setVisible(false);
                        M2.PnProveedores.setVisible(false);
                        M2.PnTransCompra.setVisible(false);
                        M2.PnCompras.setVisible(true);
                        break;
                    case "PnTransCompra":
                        M2.PnEmpresaProveedor.setVisible(false);
                        M2.PnProveedores.setVisible(false);
                        M2.PnCompras.setVisible(false);
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
        float totalRegistros = getRuxuser().CountRs(filtro);
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

}
