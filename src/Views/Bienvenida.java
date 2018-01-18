/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.GetPrincipal;
import Pojos.RolxUser;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Mauricio Herrera
 */
public final class Bienvenida extends javax.swing.JFrame {

    /**
     * Creates new form Modulo1
     */   
    boolean maximize = false;
    String VistaActual = "";
    ImageIcon ii = null;
    ImageIcon iin = null;

    private RolxUser rolu;    
    private InputStream img;

    public Bienvenida(RolxUser rolu) {
        initComponents();
        this.getContentPane().setBackground(new Color(34, 41, 50));
        setIconImage(new ImageIcon(getClass().getResource("/icons/favicon_2.png")).getImage());
        setTitle("Quiicks V1.0 - RC 2017-09-17");
        this.rolu = rolu;
        img = rolu.getObjUsuario().getObjPersona().getFoto();
//        idpersonaOld.setVisible(false);
//        idUsuarioOld.setVisible(false);
//        idRolxuserOld.setVisible(false);
//        idEmpresaOld.setVisible(false);
//        idSedeOld.setVisible(false);
//        usuarioOld.setVisible(false);
//        idRolOld.setVisible(false);
//        idMusculoUpdate.setVisible(false);
//        idEjercicioUpdate.setVisible(false);
//        lblPayIdUser.setVisible(false);
//        lblIdPagoAction.setVisible(false);
        // setSize(1024, 720);
    }

    private Bienvenida() {

    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupTblUsers = new javax.swing.JPopupMenu();
        mnuAsocFinger = new javax.swing.JMenuItem();
        mnuUpdate = new javax.swing.JMenuItem();
        mnuDelete = new javax.swing.JMenuItem();
        mnuGenerarPago = new javax.swing.JMenuItem();
        mnuHistoryPays = new javax.swing.JMenuItem();
        mnuAddRutina = new javax.swing.JMenuItem();
        mnuMedidasUser = new javax.swing.JMenuItem();
        popupTblMusculos = new javax.swing.JPopupMenu();
        mnuUpdateMusculo = new javax.swing.JMenuItem();
        mnuDeleteMusculo = new javax.swing.JMenuItem();
        popupEjercicios = new javax.swing.JPopupMenu();
        mnuUpdateEjercicio = new javax.swing.JMenuItem();
        mnuDeleteEjercicio = new javax.swing.JMenuItem();
        popupPagosService = new javax.swing.JPopupMenu();
        mnuBusqueda = new javax.swing.JMenuItem();
        poopupHistoryPays = new javax.swing.JPopupMenu();
        mnuEditFechasPagos = new javax.swing.JMenuItem();
        mnuDeletePagos = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        pnM1 = new javax.swing.JPanel();
        lblM1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pnM2 = new javax.swing.JPanel();
        lblM2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pnM3 = new javax.swing.JPanel();
        lblM3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        pnM4 = new javax.swing.JPanel();
        lblM4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        mnuAsocFinger.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Finger18px.png"))); // NOI18N
        mnuAsocFinger.setText("Asociar Huellas");
        popupTblUsers.add(mnuAsocFinger);

        mnuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Edit18px.png"))); // NOI18N
        mnuUpdate.setText("Editar");
        popupTblUsers.add(mnuUpdate);

        mnuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Eraser.png"))); // NOI18N
        mnuDelete.setText("Eliminar");
        popupTblUsers.add(mnuDelete);

        mnuGenerarPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Pay.png"))); // NOI18N
        mnuGenerarPago.setText("Generar Pago");
        popupTblUsers.add(mnuGenerarPago);

        mnuHistoryPays.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/HistoryPays.png"))); // NOI18N
        mnuHistoryPays.setText("Ver Pagos");
        popupTblUsers.add(mnuHistoryPays);

        mnuAddRutina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Rutina.png"))); // NOI18N
        mnuAddRutina.setText("Asignar Rutina");
        mnuAddRutina.setEnabled(false);
        popupTblUsers.add(mnuAddRutina);

        mnuMedidasUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Sewing Tape Measure_18px.png"))); // NOI18N
        mnuMedidasUser.setText("Tomar Medidas");
        mnuMedidasUser.setEnabled(false);
        popupTblUsers.add(mnuMedidasUser);

        mnuUpdateMusculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Edit18px.png"))); // NOI18N
        mnuUpdateMusculo.setText("Editar");
        popupTblMusculos.add(mnuUpdateMusculo);

        mnuDeleteMusculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Eraser.png"))); // NOI18N
        mnuDeleteMusculo.setText("Eliminar");
        popupTblMusculos.add(mnuDeleteMusculo);

        mnuUpdateEjercicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Edit18px.png"))); // NOI18N
        mnuUpdateEjercicio.setText("Editar");
        popupEjercicios.add(mnuUpdateEjercicio);

        mnuDeleteEjercicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Eraser.png"))); // NOI18N
        mnuDeleteEjercicio.setText("Eliminar");
        popupEjercicios.add(mnuDeleteEjercicio);

        mnuBusqueda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Edit18px.png"))); // NOI18N
        mnuBusqueda.setText("Buscar");
        popupPagosService.add(mnuBusqueda);

        mnuEditFechasPagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Edit18px.png"))); // NOI18N
        mnuEditFechasPagos.setText("Editar");
        poopupHistoryPays.add(mnuEditFechasPagos);

        mnuDeletePagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Eraser.png"))); // NOI18N
        mnuDeletePagos.setText("Eliminar");
        poopupHistoryPays.add(mnuDeletePagos);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(54, 63, 73));
        setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(708, 552));
        setMinimumSize(new java.awt.Dimension(698, 552));
        setPreferredSize(new java.awt.Dimension(708, 552));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(new java.awt.Color(54, 63, 73));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 552));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(247, 247, 247));
        jPanel2.setPreferredSize(new java.awt.Dimension(708, 183));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(34, 41, 50));
        jLabel1.setText("Tu Negocio al Dia..");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 198, -1));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/3.png"))); // NOI18N
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 200, 180));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/linkLogo.png"))); // NOI18N
        jLabel14.setToolTipText("");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 40, 40));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(34, 41, 50));
        jLabel13.setText("Quiicks V1.0");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, -1, 50));

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(34, 41, 50));
        jPanel3.setPreferredSize(new java.awt.Dimension(708, 342));

        pnM1.setBackground(new java.awt.Color(109, 117, 125));
        pnM1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnM1.setPreferredSize(new java.awt.Dimension(143, 52));
        pnM1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnM1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnM1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnM1MouseExited(evt);
            }
        });

        lblM1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblM1.setForeground(new java.awt.Color(34, 41, 50));
        lblM1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblM1.setText("Sistema");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Modulo1.png"))); // NOI18N

        javax.swing.GroupLayout pnM1Layout = new javax.swing.GroupLayout(pnM1);
        pnM1.setLayout(pnM1Layout);
        pnM1Layout.setHorizontalGroup(
            pnM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnM1Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(31, 31, 31))
            .addGroup(pnM1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(lblM1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnM1Layout.setVerticalGroup(
            pnM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnM1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblM1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pnM2.setBackground(new java.awt.Color(109, 117, 125));
        pnM2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnM2.setPreferredSize(new java.awt.Dimension(143, 0));
        pnM2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnM2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnM2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnM2MouseExited(evt);
            }
        });

        lblM2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblM2.setForeground(new java.awt.Color(34, 41, 50));
        lblM2.setText("Usuarios");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Modulo2.png"))); // NOI18N

        javax.swing.GroupLayout pnM2Layout = new javax.swing.GroupLayout(pnM2);
        pnM2.setLayout(pnM2Layout);
        pnM2Layout.setHorizontalGroup(
            pnM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnM2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(lblM2)
                .addContainerGap(57, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnM2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(30, 30, 30))
        );
        pnM2Layout.setVerticalGroup(
            pnM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnM2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblM2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pnM3.setBackground(new java.awt.Color(109, 117, 125));
        pnM3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnM3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnM3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnM3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnM3MouseExited(evt);
            }
        });

        lblM3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblM3.setForeground(new java.awt.Color(34, 41, 50));
        lblM3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblM3.setText("Caja");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Modulo3.png"))); // NOI18N

        javax.swing.GroupLayout pnM3Layout = new javax.swing.GroupLayout(pnM3);
        pnM3.setLayout(pnM3Layout);
        pnM3Layout.setHorizontalGroup(
            pnM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnM3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(pnM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(lblM3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        pnM3Layout.setVerticalGroup(
            pnM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnM3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblM3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pnM4.setBackground(new java.awt.Color(109, 117, 125));
        pnM4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnM4.setPreferredSize(new java.awt.Dimension(143, 0));
        pnM4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnM4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnM4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnM4MouseExited(evt);
            }
        });

        lblM4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblM4.setForeground(new java.awt.Color(34, 41, 50));
        lblM4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblM4.setText("Gym");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Modulo4.png"))); // NOI18N

        javax.swing.GroupLayout pnM4Layout = new javax.swing.GroupLayout(pnM4);
        pnM4.setLayout(pnM4Layout);
        pnM4Layout.setHorizontalGroup(
            pnM4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnM4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(lblM4, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnM4Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(29, 29, 29))
        );
        pnM4Layout.setVerticalGroup(
            pnM4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnM4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblM4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Bienvenid@, selecciona un modulo.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(pnM1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnM2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(pnM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(pnM4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnM2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnM1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnM4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(120, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

  
    private void pnM1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM1MouseEntered
        pnM1.setBackground(new Color(126, 137, 149));
        lblM1.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_pnM1MouseEntered

    private void pnM1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM1MouseExited
        pnM1.setBackground(new Color(109, 117, 125));
        lblM1.setForeground(new Color(34, 41, 50));
    }//GEN-LAST:event_pnM1MouseExited

    private void pnM2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM2MouseEntered
        pnM2.setBackground(new Color(126, 137, 149));
        lblM2.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_pnM2MouseEntered

    private void pnM2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM2MouseExited
        pnM2.setBackground(new Color(109, 117, 125));
        lblM2.setForeground(new Color(34, 41, 50));
    }//GEN-LAST:event_pnM2MouseExited

    private void pnM3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM3MouseEntered
        pnM3.setBackground(new Color(126, 137, 149));
        lblM3.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_pnM3MouseEntered

    private void pnM3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM3MouseExited
        pnM3.setBackground(new Color(109, 117, 125));
        lblM3.setForeground(new Color(34, 41, 50));
    }//GEN-LAST:event_pnM3MouseExited

    private void pnM4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM4MouseEntered
        pnM4.setBackground(new Color(126, 137, 149));
        lblM4.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_pnM4MouseEntered

    private void pnM4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM4MouseExited
        pnM4.setBackground(new Color(109, 117, 125));
        lblM4.setForeground(new Color(34, 41, 50));
    }//GEN-LAST:event_pnM4MouseExited

    private void pnM1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM1MouseClicked
        this.dispose();
        Modulo1 M1 = GetPrincipal.getModulo1();
        if (img != null) {
            try {                
                BufferedImage bi = ImageIO.read(img);
                ii = new ImageIcon(bi);
                Image conver = ii.getImage();
                Image tam = conver.getScaledInstance(M1.UserLogPicture.getWidth(), M1.UserLogPicture.getHeight(), Image.SCALE_SMOOTH);
                iin = new ImageIcon(tam);
                M1.UserLogPicture.setIcon(iin);
            } catch (IOException ex) {
                System.out.println("error " + ex);
            }
        } else {
            M1.UserLogPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/userDefault.png")));            
        }
        //Modulo 1        
        M1.nomUserLog.setText(rolu.getObjUsuario().getObjPersona().getNombreCompleto());
        M1.nomRolUserlog.setText(rolu.getObjRol().getDescripcion());
        M1.id_userlog.setText(Integer.toString(rolu.getObjUsuario().getObjUsuariosID().getIdUsuario()));    
        M1.setLocationRelativeTo(null);
        M1.setVisible(true);
    }//GEN-LAST:event_pnM1MouseClicked

    private void pnM2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM2MouseClicked
        this.dispose();
        Modulo2 M2 = GetPrincipal.getModulo2();
        if (img != null) {
            try {
                BufferedImage bi = ImageIO.read(img);
                ii = new ImageIcon(bi);
                Image conver = ii.getImage();
                Image tam = conver.getScaledInstance(M2.UserLogPicture.getWidth(), M2.UserLogPicture.getHeight(), Image.SCALE_SMOOTH);
                iin = new ImageIcon(tam);
                M2.UserLogPicture.setIcon(iin);
            } catch (IOException ex) {
                System.out.println("error " + ex);
            }
        } else {
            M2.UserLogPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/userDefault.png")));            
        }
        M2.setLocationRelativeTo(null);
        M2.setVisible(true);
    }//GEN-LAST:event_pnM2MouseClicked

    private void pnM3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM3MouseClicked
        this.dispose();
        Modulo3 M3 = GetPrincipal.getModulo3();
        if (img != null) {
            try {
                BufferedImage bi = ImageIO.read(img);
                ii = new ImageIcon(bi);
                Image conver = ii.getImage();
                Image tam = conver.getScaledInstance(M3.UserLogPicture.getWidth(), M3.UserLogPicture.getHeight(), Image.SCALE_SMOOTH);
                iin = new ImageIcon(tam);
                M3.UserLogPicture.setIcon(iin);
            } catch (IOException ex) {
                System.out.println("error " + ex);
            }
        } else {
            M3.UserLogPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/userDefault.png")));            
        }
        M3.setLocationRelativeTo(null);
        M3.setVisible(true);
    }//GEN-LAST:event_pnM3MouseClicked

    private void pnM4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnM4MouseClicked
        this.dispose();
        Modulo4 M4 = GetPrincipal.getModulo4();
        if (img != null) {
            try {
                BufferedImage bi = ImageIO.read(img);
                ii = new ImageIcon(bi);
                Image conver = ii.getImage();
                Image tam = conver.getScaledInstance(M4.UserLogPicture.getWidth(), M4.UserLogPicture.getHeight(), Image.SCALE_SMOOTH);
                iin = new ImageIcon(tam);
                M4.UserLogPicture.setIcon(iin);
            } catch (IOException ex) {
                System.out.println("error " + ex);
            }
        } else {
            M4.UserLogPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/userDefault.png")));            
        }
        M4.setLocationRelativeTo(null);
        M4.setVisible(true);
    }//GEN-LAST:event_pnM4MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Bienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bienvenida().setVisible(true);
            }
        });
    }
  

    public String getVistaActual() {
        return VistaActual;
    }

    public void setVistaActual(String VistaActual) {
        this.VistaActual = VistaActual;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblM1;
    private javax.swing.JLabel lblM2;
    private javax.swing.JLabel lblM3;
    private javax.swing.JLabel lblM4;
    private javax.swing.JMenuItem mnuAddRutina;
    public javax.swing.JMenuItem mnuAsocFinger;
    public javax.swing.JMenuItem mnuBusqueda;
    public javax.swing.JMenuItem mnuDelete;
    public javax.swing.JMenuItem mnuDeleteEjercicio;
    public javax.swing.JMenuItem mnuDeleteMusculo;
    public javax.swing.JMenuItem mnuDeletePagos;
    public javax.swing.JMenuItem mnuEditFechasPagos;
    public javax.swing.JMenuItem mnuGenerarPago;
    public javax.swing.JMenuItem mnuHistoryPays;
    public javax.swing.JMenuItem mnuMedidasUser;
    public javax.swing.JMenuItem mnuUpdate;
    public javax.swing.JMenuItem mnuUpdateEjercicio;
    public javax.swing.JMenuItem mnuUpdateMusculo;
    private javax.swing.JPanel pnM1;
    private javax.swing.JPanel pnM2;
    private javax.swing.JPanel pnM3;
    private javax.swing.JPanel pnM4;
    public javax.swing.JPopupMenu poopupHistoryPays;
    private javax.swing.JPopupMenu popupEjercicios;
    private javax.swing.JPopupMenu popupPagosService;
    private javax.swing.JPopupMenu popupTblMusculos;
    private javax.swing.JPopupMenu popupTblUsers;
    // End of variables declaration//GEN-END:variables

}
