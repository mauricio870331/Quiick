package Views.Modales;

import Controllers.PrincipalController;
import Pojos.Musculos;
import Pojos.Rol;
import Pojos.Sedes;
import Pojos.TipoDocumento;
import Pojos.dias;
import java.sql.SQLException;
import Views.Modulo1;
import com.toedter.calendar.JDateChooser;
import ds.desktop.notify.DesktopNotify;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author usuario
 */
public final class NuevaSede extends javax.swing.JDialog {

    /**
     * Creates new form CategoriasRegistrar
     */
    Modulo1 principal;
    ArrayList<Sedes> listObjectos = new ArrayList();
    private PrincipalController prc;
    private final int idEmpresa;
    private Sedes s;

    public NuevaSede(java.awt.Frame parent, boolean modal, int idEmpresa) throws SQLException {
        super(parent, modal);
        initComponents();
        this.idEmpresa = idEmpresa;
        cargarSedes();
    }

    public void cargarSedes() {
        getS();
        s.getObjEmpresa().setIdEmpresa(idEmpresa);
        listObjectos = (ArrayList<Sedes>) s.List();
        DefaultTableModel model = new DefaultTableModel();
        String Titulos[] = {"#", "Sede", "Dirección", "Telefonos", "Estado"};
        model = new DefaultTableModel(null, Titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {//para evitar que las celdas sean editables
                return false;
            }
        };
        Object[] columna = new Object[5];
        Iterator<Sedes> nombreIterator = listObjectos.iterator();
        while (nombreIterator.hasNext()) {
            Sedes sede = nombreIterator.next();
            columna[0] = sede.getObjSedesID().getIdSede();
            columna[1] = sede.getNombre();
            columna[2] = sede.getDireccion();
            columna[3] = sede.getTelefono();
            columna[4] = sede.getEstado();
            model.addRow(columna);
        }
        Datos.setModel(model);
        Datos.getColumnModel().getColumn(0).setPreferredWidth(0);
        Datos.setModel(model);
    }

    public void RecuperarElemento() {

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
        linea1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        btnGuardarSede = new javax.swing.JButton();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        txtNomSede = new javax.swing.JTextField();
        txtDirSede = new javax.swing.JTextField();
        txtTelSede = new javax.swing.JTextField();
        btnCancelarSede = new javax.swing.JButton();
        ContenedorBuscar = new javax.swing.JScrollPane();
        Datos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevas Sedes");
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setMinimumSize(new java.awt.Dimension(540, 400));

        jPanel2.setBackground(new java.awt.Color(54, 63, 73));

        btnGuardarSede.setBackground(new java.awt.Color(54, 63, 73));
        btnGuardarSede.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnGuardarSede.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarSede.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        btnGuardarSede.setText("Guardar");
        btnGuardarSede.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnGuardarSede.setContentAreaFilled(false);
        btnGuardarSede.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarSede.setFocusPainted(false);
        btnGuardarSede.setHideActionText(true);
        btnGuardarSede.setIconTextGap(1);
        btnGuardarSede.setOpaque(true);
        btnGuardarSede.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarSedeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarSedeMouseExited(evt);
            }
        });
        btnGuardarSede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarSedeActionPerformed(evt);
            }
        });

        jLabel83.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText("Nombre:");

        jLabel84.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Direccion:");

        jLabel85.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText("Telefono:");

        btnCancelarSede.setBackground(new java.awt.Color(54, 63, 73));
        btnCancelarSede.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnCancelarSede.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarSede.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel.png"))); // NOI18N
        btnCancelarSede.setText("Cancelar");
        btnCancelarSede.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnCancelarSede.setContentAreaFilled(false);
        btnCancelarSede.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelarSede.setFocusPainted(false);
        btnCancelarSede.setHideActionText(true);
        btnCancelarSede.setIconTextGap(1);
        btnCancelarSede.setOpaque(true);
        btnCancelarSede.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarSedeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarSedeMouseExited(evt);
            }
        });
        btnCancelarSede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarSedeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnGuardarSede, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelarSede, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel83)
                            .addComponent(jLabel84)
                            .addComponent(jLabel85))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDirSede, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomSede, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelSede, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(102, 102, 102))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomSede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel83))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDirSede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel84))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelSede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel85))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarSede)
                    .addComponent(btnCancelarSede))
                .addContainerGap())
        );

        Datos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Datos.setSelectionBackground(new java.awt.Color(54, 63, 73));
        Datos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Datos.setShowHorizontalLines(false);
        Datos.setShowVerticalLines(false);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(linea1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(ContenedorBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(ContenedorBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(linea1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        RecuperarElemento();

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

    private void btnGuardarSedeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarSedeMouseEntered
        btnGuardarSede.setBackground(new Color(124, 124, 124));
    }//GEN-LAST:event_btnGuardarSedeMouseEntered

    private void btnGuardarSedeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarSedeMouseExited
        btnGuardarSede.setBackground(new Color(54, 63, 73));
    }//GEN-LAST:event_btnGuardarSedeMouseExited

    private void btnGuardarSedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarSedeActionPerformed
        Object[] componentes = {txtNomSede, txtDirSede};
        if (validarCampos(componentes) == 0) {
            getS();
            s.setEstado("A");
            s.getObjEmpresa().setIdEmpresa(idEmpresa);
            s.setNombre(txtNomSede.getText());
            s.setDireccion(txtDirSede.getText());
            s.setTelefono(txtTelSede.getText());
            if (s.create() > 0) {
                DesktopNotify.showDesktopMessage("Aviso..!", "Sede creada con exìto..!", DesktopNotify.SUCCESS, 5000L);
                cargarSedes();
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", "Ocurrio un error al crear la sede..!", DesktopNotify.FAIL, 5000L);
            }
        } else {
            DesktopNotify.showDesktopMessage("Aviso..!", "Los campos marcados en rojo son obligatorios", DesktopNotify.FAIL, 5000L);
        }
    }//GEN-LAST:event_btnGuardarSedeActionPerformed

    private void btnCancelarSedeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarSedeMouseEntered
        btnCancelarSede.setBackground(new Color(124, 124, 124));
    }//GEN-LAST:event_btnCancelarSedeMouseEntered

    private void btnCancelarSedeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarSedeMouseExited
        btnCancelarSede.setBackground(new Color(54, 63, 73));
    }//GEN-LAST:event_btnCancelarSedeMouseExited

    private void btnCancelarSedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarSedeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarSedeActionPerformed

    public int validarCampos(Object[] componentes) {
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
        }
        return countErrors;
    }

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
    public javax.swing.JTable Datos;
    public javax.swing.JButton btnCancelarSede;
    public javax.swing.JButton btnGuardarSede;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator linea1;
    public javax.swing.JTextField txtDirSede;
    public javax.swing.JTextField txtNomSede;
    public javax.swing.JTextField txtTelSede;
    // End of variables declaration//GEN-END:variables

    public Sedes getS() {
        if (s == null) {
            s = new Sedes();
        }
        return s;
    }

    public void setS(Sedes s) {
        this.s = s;
    }
}
