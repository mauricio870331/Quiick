/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.LoginController;
import Pojos.Rol;
import Pojos.TipoDocumento;
import Pojos.Empresa;
import Pojos.Sedes;
import Pojos.Musculos;
import Pojos.dias;
import java.awt.Color;
import java.util.Date;
import javax.swing.ImageIcon;

/**
 *
 * @author Mauricio Herrera
 */
public class Modulo2 extends javax.swing.JFrame {

    /**
     * Creates new form Modulo1
     */
    int x,

    /**
     * Creates new form Modulo1
     */
    y;
    boolean maximize = false;
    String VistaActual = "";
    
    public LoginController login;
    
    public Modulo2() {
        initComponents();
        this.getContentPane().setBackground(new Color(34, 41, 50));
        setIconImage(new ImageIcon(getClass().getResource("/icons/favicon_2.png")).getImage());
        setTitle("AppGym V 1.0 - RC 2017-09-17");
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
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        onOff = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        UserLogPicture = new com.bolivia.label.CLabel();
        nomUserLog = new javax.swing.JLabel();
        nomRolUserlog = new javax.swing.JLabel();
        btnListUsers = new javax.swing.JButton();
        btnAsistencias = new javax.swing.JButton();
        btnAsistenciaManual = new javax.swing.JButton();
        id_userlog = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        btnListMusculos = new javax.swing.JButton();
        btnListEjercicios = new javax.swing.JButton();
        btnGestionRutinas = new javax.swing.JButton();
        btnTransaccionCaja = new javax.swing.JButton();
        btnMnuHorafeliz = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(54, 63, 73));
        setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        setLocationByPlatform(true);

        jPanel10.setBackground(new java.awt.Color(34, 41, 50));
        jPanel10.setPreferredSize(new java.awt.Dimension(200, 631));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Gestión De Usuarios");

        onOff.setBackground(new java.awt.Color(255, 0, 0));
        onOff.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        onOff.setForeground(new java.awt.Color(255, 255, 255));
        onOff.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        onOff.setText("OFF");
        onOff.setOpaque(true);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Ingresos");

        UserLogPicture.setBackground(new java.awt.Color(255, 255, 255));
        UserLogPicture.setForeground(new java.awt.Color(255, 255, 255));
        UserLogPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/userDefault.png"))); // NOI18N
        UserLogPicture.setText("");

        nomUserLog.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        nomUserLog.setForeground(new java.awt.Color(255, 255, 255));
        nomUserLog.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomUserLog.setText("Datos Admin");

        nomRolUserlog.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        nomRolUserlog.setForeground(new java.awt.Color(255, 255, 255));
        nomRolUserlog.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomRolUserlog.setText("Datos Admin");
        nomRolUserlog.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnListUsers.setBackground(new java.awt.Color(54, 63, 73));
        btnListUsers.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnListUsers.setForeground(new java.awt.Color(255, 255, 255));
        btnListUsers.setText("Lista de Usuarios");
        btnListUsers.setBorder(null);
        btnListUsers.setBorderPainted(false);
        btnListUsers.setContentAreaFilled(false);
        btnListUsers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnListUsers.setFocusPainted(false);
        btnListUsers.setHideActionText(true);
        btnListUsers.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListUsers.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnListUsers.setIconTextGap(1);

        btnAsistencias.setBackground(new java.awt.Color(54, 63, 73));
        btnAsistencias.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnAsistencias.setForeground(new java.awt.Color(255, 255, 255));
        btnAsistencias.setText("Verificar Asistencias");
        btnAsistencias.setBorder(null);
        btnAsistencias.setBorderPainted(false);
        btnAsistencias.setContentAreaFilled(false);
        btnAsistencias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAsistencias.setFocusPainted(false);
        btnAsistencias.setHideActionText(true);
        btnAsistencias.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAsistencias.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnAsistencias.setIconTextGap(1);

        btnAsistenciaManual.setBackground(new java.awt.Color(54, 63, 73));
        btnAsistenciaManual.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnAsistenciaManual.setForeground(new java.awt.Color(255, 255, 255));
        btnAsistenciaManual.setText("Asistencia Manual");
        btnAsistenciaManual.setBorder(null);
        btnAsistenciaManual.setBorderPainted(false);
        btnAsistenciaManual.setContentAreaFilled(false);
        btnAsistenciaManual.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAsistenciaManual.setFocusPainted(false);
        btnAsistenciaManual.setHideActionText(true);
        btnAsistenciaManual.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAsistenciaManual.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnAsistenciaManual.setIconTextGap(1);

        id_userlog.setEditable(false);
        id_userlog.setBackground(new java.awt.Color(34, 41, 50));
        id_userlog.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        id_userlog.setForeground(new java.awt.Color(255, 255, 255));
        id_userlog.setBorder(null);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Configuración");

        btnListMusculos.setBackground(new java.awt.Color(54, 63, 73));
        btnListMusculos.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnListMusculos.setForeground(new java.awt.Color(255, 255, 255));
        btnListMusculos.setText("Lista de Musculos");
        btnListMusculos.setBorder(null);
        btnListMusculos.setBorderPainted(false);
        btnListMusculos.setContentAreaFilled(false);
        btnListMusculos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnListMusculos.setFocusPainted(false);
        btnListMusculos.setHideActionText(true);
        btnListMusculos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListMusculos.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnListMusculos.setIconTextGap(1);

        btnListEjercicios.setBackground(new java.awt.Color(54, 63, 73));
        btnListEjercicios.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnListEjercicios.setForeground(new java.awt.Color(255, 255, 255));
        btnListEjercicios.setText("Lista de Ejercicios");
        btnListEjercicios.setBorder(null);
        btnListEjercicios.setBorderPainted(false);
        btnListEjercicios.setContentAreaFilled(false);
        btnListEjercicios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnListEjercicios.setFocusPainted(false);
        btnListEjercicios.setHideActionText(true);
        btnListEjercicios.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListEjercicios.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnListEjercicios.setIconTextGap(1);

        btnGestionRutinas.setBackground(new java.awt.Color(54, 63, 73));
        btnGestionRutinas.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnGestionRutinas.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionRutinas.setText("Gestion de Rutinas");
        btnGestionRutinas.setBorder(null);
        btnGestionRutinas.setBorderPainted(false);
        btnGestionRutinas.setContentAreaFilled(false);
        btnGestionRutinas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGestionRutinas.setFocusPainted(false);
        btnGestionRutinas.setHideActionText(true);
        btnGestionRutinas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGestionRutinas.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnGestionRutinas.setIconTextGap(1);

        btnTransaccionCaja.setBackground(new java.awt.Color(54, 63, 73));
        btnTransaccionCaja.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnTransaccionCaja.setForeground(new java.awt.Color(255, 255, 255));
        btnTransaccionCaja.setText("Mi Caja");
        btnTransaccionCaja.setBorder(null);
        btnTransaccionCaja.setBorderPainted(false);
        btnTransaccionCaja.setContentAreaFilled(false);
        btnTransaccionCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTransaccionCaja.setFocusPainted(false);
        btnTransaccionCaja.setHideActionText(true);
        btnTransaccionCaja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTransaccionCaja.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnTransaccionCaja.setIconTextGap(1);
        btnTransaccionCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransaccionCajaActionPerformed(evt);
            }
        });

        btnMnuHorafeliz.setBackground(new java.awt.Color(54, 63, 73));
        btnMnuHorafeliz.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnMnuHorafeliz.setForeground(new java.awt.Color(255, 255, 255));
        btnMnuHorafeliz.setText("Hora Feliz");
        btnMnuHorafeliz.setBorder(null);
        btnMnuHorafeliz.setBorderPainted(false);
        btnMnuHorafeliz.setContentAreaFilled(false);
        btnMnuHorafeliz.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMnuHorafeliz.setFocusPainted(false);
        btnMnuHorafeliz.setHideActionText(true);
        btnMnuHorafeliz.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMnuHorafeliz.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnMnuHorafeliz.setIconTextGap(1);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(nomUserLog, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(id_userlog))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nomRolUserlog, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnAsistenciaManual)
                                            .addGroup(jPanel10Layout.createSequentialGroup()
                                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(btnListUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnAsistencias))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(onOff, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jLabel32)
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnTransaccionCaja)
                                    .addComponent(btnListEjercicios)
                                    .addComponent(btnListMusculos)
                                    .addComponent(btnGestionRutinas)
                                    .addComponent(btnMnuHorafeliz))))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(UserLogPicture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UserLogPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomUserLog, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomRolUserlog, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnListUsers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onOff)
                    .addComponent(btnAsistencias))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAsistenciaManual)
                .addGap(40, 40, 40)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTransaccionCaja)
                .addGap(83, 83, 83)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListMusculos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnListEjercicios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGestionRutinas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMnuHorafeliz)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(id_userlog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(143, 143, 143)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(556, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel10, java.awt.BorderLayout.LINE_START);

        jLayeredPane1.setBackground(new java.awt.Color(34, 41, 50));
        jLayeredPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 697, Short.MAX_VALUE)
        );

        getContentPane().add(jLayeredPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTransaccionCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransaccionCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTransaccionCajaActionPerformed

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
            java.util.logging.Logger.getLogger(Modulo2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Modulo2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Modulo2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Modulo2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Modulo2().setVisible(true);
            }
        });
    }
    
    public LoginController getLogin() {
        return login;
    }
    
    public void setLogin(LoginController login) {
        this.login = login;
    }
    
    public String getVistaActual() {
        return VistaActual;
    }
    
    public void setVistaActual(String VistaActual) {
        this.VistaActual = VistaActual;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public com.bolivia.label.CLabel UserLogPicture;
    public javax.swing.JButton btnAsistenciaManual;
    public javax.swing.JButton btnAsistencias;
    public javax.swing.JButton btnGestionRutinas;
    public javax.swing.JButton btnListEjercicios;
    public javax.swing.JButton btnListMusculos;
    public javax.swing.JButton btnListUsers;
    public javax.swing.JButton btnMnuHorafeliz;
    public javax.swing.JButton btnTransaccionCaja;
    public javax.swing.JTextField id_userlog;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
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
    public javax.swing.JLabel nomRolUserlog;
    public javax.swing.JLabel nomUserLog;
    public javax.swing.JLabel onOff;
    public javax.swing.JPopupMenu poopupHistoryPays;
    private javax.swing.JPopupMenu popupEjercicios;
    private javax.swing.JPopupMenu popupPagosService;
    private javax.swing.JPopupMenu popupTblMusculos;
    private javax.swing.JPopupMenu popupTblUsers;
    // End of variables declaration//GEN-END:variables

}
