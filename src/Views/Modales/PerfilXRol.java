package Views.Modales;

import Pojos.Perfil;
import Pojos.PerfilRoles;
import java.sql.SQLException;
import ds.desktop.notify.DesktopNotify;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JCheckBox;

/**
 *
 * @author usuario
 */
public final class PerfilXRol extends javax.swing.JDialog implements ItemListener {

    /**
     * Creates new form CategoriasRegistrar
     */
    private final int idRol;
    private final int userLog;
    private Perfil pefil;
    private PerfilRoles perfilxrol;
    public JCheckBox cb[];
    public ArrayList<String> listPerfilesToRol = new ArrayList<>();
    
    public PerfilXRol(java.awt.Frame parent, boolean modal, int idRol, int userLog) throws SQLException {
        super(parent, modal);
        initComponents();
        this.idRol = idRol;
        this.userLog = userLog;
        crearCheckbox(this);
    }
    
    public void crearCheckbox(PerfilXRol modal) {
        List<Perfil> perilesList = getPerfil().List();
        getPerfilxrol().setIdRol(idRol);
        List<String> actuales = getPerfilxrol().List();
        int cantChecks = perilesList.size();
        pnPerfiles.removeAll();
        pnPerfiles.setLayout(new java.awt.GridLayout(2, cantChecks));
        cb = new JCheckBox[cantChecks];
        int i = 0;
        Iterator<Perfil> nombreIterator = perilesList.iterator();
        while (nombreIterator.hasNext()) {
            Perfil p = nombreIterator.next();
            cb[i] = new JCheckBox();
            cb[i].setText(p.getDescripcion());
            cb[i].setActionCommand(p.getIdPerfil() + "");
            cb[i].addItemListener(modal);
            if (actuales.contains(Integer.toString(p.getIdPerfil()))) {
                cb[i].setSelected(true);
                listPerfilesToRol.add(p.getIdPerfil() + "");
            }
            pnPerfiles.add(cb[i]);
            i++;
        }
        setPerfil(null);
        setPerfilxrol(null);
        pnPerfiles.updateUI();
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
        lblRol = new javax.swing.JLabel();
        pnPerfiles = new javax.swing.JPanel();
        btnGuardarPerfilxrol = new javax.swing.JButton();
        btnCancelarPerfilxrol = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Asignar Perfiles a Rol");
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setMinimumSize(new java.awt.Dimension(540, 400));

        jPanel2.setBackground(new java.awt.Color(54, 63, 73));

        lblRol.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        lblRol.setForeground(new java.awt.Color(255, 255, 255));
        lblRol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRol.setText("Rol:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRol, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRol)
                .addGap(94, 94, 94))
        );

        pnPerfiles.setBackground(new java.awt.Color(255, 255, 255));
        pnPerfiles.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout pnPerfilesLayout = new javax.swing.GroupLayout(pnPerfiles);
        pnPerfiles.setLayout(pnPerfilesLayout);
        pnPerfilesLayout.setHorizontalGroup(
            pnPerfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 379, Short.MAX_VALUE)
        );
        pnPerfilesLayout.setVerticalGroup(
            pnPerfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );

        btnGuardarPerfilxrol.setBackground(new java.awt.Color(54, 63, 73));
        btnGuardarPerfilxrol.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnGuardarPerfilxrol.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarPerfilxrol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        btnGuardarPerfilxrol.setText("Guardar");
        btnGuardarPerfilxrol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnGuardarPerfilxrol.setContentAreaFilled(false);
        btnGuardarPerfilxrol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarPerfilxrol.setFocusPainted(false);
        btnGuardarPerfilxrol.setHideActionText(true);
        btnGuardarPerfilxrol.setIconTextGap(1);
        btnGuardarPerfilxrol.setOpaque(true);
        btnGuardarPerfilxrol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarPerfilxrolMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarPerfilxrolMouseExited(evt);
            }
        });
        btnGuardarPerfilxrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPerfilxrolActionPerformed(evt);
            }
        });

        btnCancelarPerfilxrol.setBackground(new java.awt.Color(54, 63, 73));
        btnCancelarPerfilxrol.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnCancelarPerfilxrol.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarPerfilxrol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel.png"))); // NOI18N
        btnCancelarPerfilxrol.setText("Cancelar");
        btnCancelarPerfilxrol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnCancelarPerfilxrol.setContentAreaFilled(false);
        btnCancelarPerfilxrol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelarPerfilxrol.setFocusPainted(false);
        btnCancelarPerfilxrol.setHideActionText(true);
        btnCancelarPerfilxrol.setIconTextGap(1);
        btnCancelarPerfilxrol.setOpaque(true);
        btnCancelarPerfilxrol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarPerfilxrolMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarPerfilxrolMouseExited(evt);
            }
        });
        btnCancelarPerfilxrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPerfilxrolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnPerfiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardarPerfilxrol, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarPerfilxrol, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(linea1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnPerfiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarPerfilxrol)
                    .addComponent(btnCancelarPerfilxrol))
                .addGap(10, 10, 10)
                .addComponent(linea1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarPerfilxrolMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarPerfilxrolMouseEntered
        btnGuardarPerfilxrol.setBackground(new Color(124, 124, 124));
    }//GEN-LAST:event_btnGuardarPerfilxrolMouseEntered

    private void btnGuardarPerfilxrolMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarPerfilxrolMouseExited
        btnGuardarPerfilxrol.setBackground(new Color(54, 63, 73));
    }//GEN-LAST:event_btnGuardarPerfilxrolMouseExited

    private void btnGuardarPerfilxrolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPerfilxrolActionPerformed
        if (listPerfilesToRol.isEmpty()) {
            DesktopNotify.showDesktopMessage("Aviso..!", "Debes Seleccionar almenos un peril", DesktopNotify.ERROR, 5000L);
        } else {
            getPerfilxrol();
            perfilxrol.setIdRol(idRol);
            perfilxrol.setPerfiles(listPerfilesToRol);
            perfilxrol.setEstado("A");
            perfilxrol.setIdUserlog(userLog);
            int result = 0;
            String msn = "Perfiles Asignados con exito..!";
            String msnerror = "Ocurrio un error al asignar los perfiles..!";
            if (btnGuardarPerfilxrol.getText().equalsIgnoreCase("Guardar")) {
                result = perfilxrol.create();
            } else {
                msn = "Perfiles editados con exito..!";
                msnerror = "Ocurrio un error al editar los perfiles..!";
                result = perfilxrol.edit();
            }
            if (result > 0) {
                DesktopNotify.showDesktopMessage("Aviso..!", msn, DesktopNotify.SUCCESS, 5000L);
//                cargarSedes();
                setPerfilxrol(null);
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", msnerror, DesktopNotify.FAIL, 5000L);
            }
            
        }
    }//GEN-LAST:event_btnGuardarPerfilxrolActionPerformed

    private void btnCancelarPerfilxrolMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarPerfilxrolMouseEntered
        btnCancelarPerfilxrol.setBackground(new Color(124, 124, 124));
    }//GEN-LAST:event_btnCancelarPerfilxrolMouseEntered

    private void btnCancelarPerfilxrolMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarPerfilxrolMouseExited
        btnCancelarPerfilxrol.setBackground(new Color(54, 63, 73));
    }//GEN-LAST:event_btnCancelarPerfilxrolMouseExited

    private void btnCancelarPerfilxrolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPerfilxrolActionPerformed
        listPerfilesToRol.clear();
        setPerfil(null);
        setPerfilxrol(null);
        pnPerfiles.removeAll();
        this.dispose();
    }//GEN-LAST:event_btnCancelarPerfilxrolActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancelarPerfilxrol;
    public javax.swing.JButton btnGuardarPerfilxrol;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JLabel lblRol;
    private javax.swing.JSeparator linea1;
    private javax.swing.JPanel pnPerfiles;
    // End of variables declaration//GEN-END:variables

    public Perfil getPerfil() {
        if (pefil == null) {
            pefil = new Perfil();
        }
        return pefil;
    }
    
    public void setPerfil(Perfil pefil) {
        this.pefil = pefil;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        for (int i = 0; i < pnPerfiles.getComponentCount(); i++) {
            if (e.getSource() == cb[i]) {
                if (cb[i].isSelected()) {
                    listPerfilesToRol.add(cb[i].getActionCommand());
                } else {
                    Iterator<String> st = listPerfilesToRol.iterator();
                    while (st.hasNext()) {
                        String borrar = st.next();
                        if (borrar.equals(cb[i].getActionCommand().trim())) {
                            st.remove();
                        }
                    }
                }
                if (listPerfilesToRol.isEmpty()) {
                    listPerfilesToRol.clear();
                }
//                listPerfilesToRol.forEach((string) -> {
//                    System.out.println("perfil " + string);
//                });
            }
        }
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
    
}
