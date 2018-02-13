package Views.Modales;

import Controllers.ControllerM2;
import Pojos.Proveedor;
import Pojos.Usuario;
import Pojos.objectobusqueda;
import Pojos.producto;
import java.sql.SQLException;
import Views.Modulo1;
import Views.Modulo2;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author usuario
 */
public class Busqueda extends javax.swing.JDialog {

    /**
     * Creates new form CategoriasRegistrar
     */
    Modulo1 M1;
    Modulo2 M2;
    ArrayList<Object> listObjectos = new ArrayList();
    private ControllerM2 prc;
    objectobusqueda objecto;

    public Busqueda(java.awt.Frame parent, boolean modal, objectobusqueda ob) throws SQLException {
        super(parent, modal);
        initComponents();
        System.out.println("inicio edit");
        this.setLocationRelativeTo(null);
        this.objecto = ob;
        this.filtro.setText(ob.getFiltro());
        this.titulo.setText(ob.getFiltro());
        if (this.objecto.getModulo() == 1) {
            M1 = (Modulo1) parent;
            if (M1.getVistaActual().equalsIgnoreCase("pnPagosService")
                    || M1.getVistaActual().equalsIgnoreCase("pnReportes")) {
                CargarPersonal();
            }
        } else if (this.objecto.getModulo() == 2) {
            prc = ob.getM2();
            M2 = (Modulo2) parent;
            System.out.println("--- " + M2.getVistaActual());
            if (M2.getVistaActual().equalsIgnoreCase("PnTransCompra") && ob.getCondicion() == 1) {
                System.out.println("Buscar Producto");
                CargarProductos();
            } else if (M2.getVistaActual().equalsIgnoreCase("PnTransCompra") && ob.getCondicion() == 2) {
                System.out.println("Buscar Proveedor");
                CargarProveedores();

            }

        }
    }

    public void CargarPersonal() {
        Usuario p = new Usuario();
        listObjectos = (ArrayList<Object>) p.ListaUsuarios();
        DefaultTableModel model = new DefaultTableModel();
        String Titulos[] = {"id", "Cedula", "Nombre"};
        model = new DefaultTableModel(null, Titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {//para evitar que las celdas sean editables
                return false;
            }
        };
        Object[] columna = new Object[3];

        Iterator<Object> nombreIterator = listObjectos.iterator();
        while (nombreIterator.hasNext()) {
            p = (Usuario) nombreIterator.next();
            columna[0] = p.getObjPersona().getIdPersona();
            columna[1] = p.getObjPersona().getDocumento();
            columna[2] = p.getObjPersona().getNombreCompleto();

            model.addRow(columna);
        }
        Datos.setModel(model);
        Datos.getColumnModel().getColumn(0).setMaxWidth(0);
        Datos.getColumnModel().getColumn(0).setMaxWidth(0);
        Datos.getColumnModel().getColumn(0).setPreferredWidth(0);
        Datos.getColumnModel().getColumn(1).setPreferredWidth(250);
        Datos.getColumnModel().getColumn(2).setPreferredWidth(100);
        Datos.setRowHeight(20);
        Datos.setModel(model);
    }

    public void CargarProductos() {
        producto p = new producto();
        listObjectos = (ArrayList<Object>) p.List();
        DefaultTableModel model = new DefaultTableModel();
        String Titulos[] = {"id", "Codigo", "Nombre", "Stock"};
        model = new DefaultTableModel(null, Titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {//para evitar que las celdas sean editables
                return false;
            }
        };
        Object[] columna = new Object[4];

        Iterator<Object> nombreIterator = listObjectos.iterator();
        while (nombreIterator.hasNext()) {
            p = (producto) nombreIterator.next();
            columna[0] = p.getProductosID().getCod_producto();
            columna[1] = p.getSerieproducto();
            columna[2] = p.getNombreProducto();
            columna[3] = p.getStock();

            model.addRow(columna);
        }
        Datos.setModel(model);
        Datos.getColumnModel().getColumn(0).setMaxWidth(0);
        Datos.getColumnModel().getColumn(0).setMaxWidth(0);
        Datos.getColumnModel().getColumn(0).setPreferredWidth(0);
        Datos.getColumnModel().getColumn(1).setPreferredWidth(100);
        Datos.getColumnModel().getColumn(2).setPreferredWidth(250);
        Datos.getColumnModel().getColumn(3).setPreferredWidth(100);
        Datos.setRowHeight(20);
        Datos.setModel(model);
    }

    public void CargarProveedores() {
        Proveedor p = new Proveedor();
        listObjectos = (ArrayList<Object>) p.List();
        DefaultTableModel model = new DefaultTableModel();
        String Titulos[] = {"id", "Empresa", "Cedula", "Nombre"};
        model = new DefaultTableModel(null, Titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {//para evitar que las celdas sean editables
                return false;
            }
        };
        Object[] columna = new Object[4];

        Iterator<Object> nombreIterator = listObjectos.iterator();
        while (nombreIterator.hasNext()) {
            p = (Proveedor) nombreIterator.next();
            columna[0] = p.getIdProveedor();
            columna[1] = p.getEmpresa().getNombreEmpresa();
            columna[2] = p.getPersona().getDocumento();
            columna[3] = p.getPersona().getNombre() + " " + p.getPersona().getApellido();

            model.addRow(columna);
        }
        Datos.setModel(model);
        Datos.getColumnModel().getColumn(0).setMaxWidth(0);
        Datos.getColumnModel().getColumn(0).setMaxWidth(0);
        Datos.getColumnModel().getColumn(0).setPreferredWidth(0);
        Datos.getColumnModel().getColumn(1).setPreferredWidth(250);
        Datos.getColumnModel().getColumn(2).setPreferredWidth(100);
        Datos.getColumnModel().getColumn(3).setPreferredWidth(250);
        Datos.setRowHeight(20);
        Datos.setModel(model);
    }

    public void RecuperarElemento() {
        int i = Datos.getSelectedRow();
        String cod = "";

        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Favor... seleccione una fila");
        } else {
            cod = (String) Datos.getValueAt(i, 0).toString().trim();
            System.out.println("codigo : " + cod);
//            JTable table = M1.tblListaPagosXuser;
            for (Iterator<Object> it = listObjectos.iterator(); it.hasNext();) {
                if (this.objecto.getModulo() == 1) {
                    if (M1.getVistaActual().equalsIgnoreCase("pnPagosService")) {
                        Usuario listObjecto = (Usuario) it.next();
                        if (listObjecto.getObjPersona().getIdPersona() == Integer.parseInt(cod)) {
                            System.out.println("encontre persona : " + listObjecto.getObjPersona().getIdPersona() + " = " + cod);
                            prc.setUs(listObjecto);
                            break;
                        }
                    }
                    if (M1.getVistaActual().equalsIgnoreCase("pnReportes")) {
                        Usuario listObjecto = (Usuario) it.next();
                        if (listObjecto.getObjPersona().getIdPersona() == Integer.parseInt(cod)) {
                            System.out.println("encontre persona : " + listObjecto.getObjPersona().getIdPersona() + " = " + cod);
                            prc.setUs(listObjecto);
                            //this.objecto.get = 3;
                            //table = M1.tblReportes;
                            break;
                        }
                    }
                } else if (this.objecto.getModulo() == 2) {
                    if (M2.getVistaActual().equalsIgnoreCase("PnTransCompra") && this.objecto.getCondicion() == 1) {
                        producto listObjecto = (producto) it.next();
                        if (Integer.parseInt(cod) == listObjecto.getProductosID().getCod_producto().intValue()) {
                            prc.getPr().getListProductos().add(listObjecto);
                            prc.ListProductosAñadidos();
                            break;
                        }

                    } else if (M2.getVistaActual().equalsIgnoreCase("PnTransCompra") && this.objecto.getCondicion() == 2) {
                        System.out.println("Buscar Proveedor");
                        Proveedor listObjecto = (Proveedor) it.next();
                        if (Integer.parseInt(cod) == listObjecto.getIdProveedor().intValue()) {
                            prc.CargarDatosProveedor(listObjecto);
                            break;
                        }

                    }
                }

            }
            //prc.PagosBuscarPersona(condicion, table);
            this.dispose();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nombrePersona = new javax.swing.JTextField();
        filtro = new javax.swing.JLabel();
        linea1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        ContenedorBuscar = new javax.swing.JScrollPane();
        Datos = new javax.swing.JTable();
        btnBuscarCliente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscar Clientes");
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setMinimumSize(new java.awt.Dimension(540, 400));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nombrePersona.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(nombrePersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 340, 29));

        filtro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filtro.setForeground(new java.awt.Color(54, 63, 73));
        filtro.setText("Nombre");
        jPanel1.add(filtro, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));
        jPanel1.add(linea1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 530, 10));

        jPanel2.setBackground(new java.awt.Color(54, 63, 73));

        titulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setText("Buscar Clientes");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(230, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 40));

        Datos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Datos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Datos.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                DatosMouseDragged(evt);
            }
        });
        Datos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                DatosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                DatosFocusLost(evt);
            }
        });
        Datos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DatosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DatosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DatosMouseExited(evt);
            }
        });
        Datos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DatosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                DatosKeyReleased(evt);
            }
        });
        ContenedorBuscar.setViewportView(Datos);

        jPanel1.add(ContenedorBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 490, 270));

        btnBuscarCliente.setBackground(new java.awt.Color(54, 63, 73));
        btnBuscarCliente.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnBuscarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/findWhite.png"))); // NOI18N
        btnBuscarCliente.setText("Buscar");
        btnBuscarCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnBuscarCliente.setContentAreaFilled(false);
        btnBuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarCliente.setFocusPainted(false);
        btnBuscarCliente.setHideActionText(true);
        btnBuscarCliente.setIconTextGap(3);
        btnBuscarCliente.setOpaque(true);
        btnBuscarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarClienteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBuscarClienteMouseExited(evt);
            }
        });
        jPanel1.add(btnBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, 76, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DatosMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DatosMouseDragged

    }//GEN-LAST:event_DatosMouseDragged

    private void DatosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DatosFocusGained

    }//GEN-LAST:event_DatosFocusGained

    private void DatosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DatosFocusLost
        //        Buscador.setVisible(false);
        //        jTextField2.setText("");
    }//GEN-LAST:event_DatosFocusLost

    private void DatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DatosMouseClicked
        //        // TODO add your handling code here:
        //        System.out.println("click : " + evt.getClickCount());
        if (evt.getClickCount() == 2) {
            RecuperarElemento();
        }

    }//GEN-LAST:event_DatosMouseClicked

    private void DatosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DatosMouseEntered

    }//GEN-LAST:event_DatosMouseEntered

    private void DatosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DatosMouseExited

    }//GEN-LAST:event_DatosMouseExited

    private void DatosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DatosKeyPressed

    }//GEN-LAST:event_DatosKeyPressed

    private void DatosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DatosKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_DatosKeyReleased

    private void btnBuscarClienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarClienteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarClienteMouseEntered

    private void btnBuscarClienteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarClienteMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarClienteMouseExited

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(CategoriasRegistrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(CategoriasRegistrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(CategoriasRegistrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(CategoriasRegistrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                CategoriasRegistrar dialog = new CategoriasRegistrar(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ContenedorBuscar;
    private javax.swing.JTable Datos;
    public javax.swing.JButton btnBuscarCliente;
    private javax.swing.JLabel filtro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator linea1;
    private javax.swing.JTextField nombrePersona;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
