package Views.Modales;

import Pojos.Menus;
import Pojos.MenusForUsuarios;
import Pojos.SubMenus;
import ds.desktop.notify.DesktopNotify;
import java.awt.Color;
import java.sql.SQLException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author usuario
 */
public final class MenusXUsuarios extends javax.swing.JDialog implements ItemListener {

    /**
     * Creates new form CategoriasRegistrar
     */
    private final int idUser;
    private Menus menus;
    private MenusForUsuarios mnusxusers;
    public JCheckBox cb[];
    public JLabel lblMenus[];
    public JPanel pnMNU[];
    public ArrayList<MenusForUsuarios> listMenususuario = new ArrayList<>();
    public ArrayList<JCheckBox> listCheckBox = new ArrayList();

    public MenusXUsuarios(java.awt.Frame parent, boolean modal, int idUser) throws SQLException {
        super(parent, modal);
        initComponents();
        this.idUser = idUser;   
        crearCheckbox(this);
    }

    public void crearCheckbox(MenusXUsuarios modal) {
        getMenus();        
        List<Menus> list = menus.ListMenus("");
        int cantMenus = list.size();
        pnMenus.removeAll();
        pnMenus.setLayout(new java.awt.GridLayout(1, cantMenus));
        pnMNU = new JPanel[cantMenus];
        int i = 0;
        Iterator<Menus> nombreIterator = list.iterator();
        while (nombreIterator.hasNext()) {
            Menus m = nombreIterator.next();
            pnMNU[i] = new JPanel();
            pnMenus.add(crearPnMenu(pnMNU[i], m.getNombre(), m.getListSubmenus(), modal));
            i++;
        }
        setMenus(null);
        pnMenus.updateUI();

    }

    public JPanel crearPnMenu(JPanel pnMNU, String Menu, ArrayList<SubMenus> submenus, MenusXUsuarios modal) {
        getMnusxusers().setIdUsuario(idUser);
        List<MenusForUsuarios> actuales = mnusxusers.List();
        JLabel textMenu = new JLabel();
        textMenu.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        textMenu.setForeground(new java.awt.Color(54, 63, 73));
        textMenu.setText(Menu);
        JCheckBox textSubmenu[] = new JCheckBox[submenus.size()];
        JPanel panelSubmenus = new JPanel();
        pnMNU.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelSubmenus.setLayout(new java.awt.GridLayout(submenus.size(), 1, 5, 0));
        Iterator<SubMenus> itrs = submenus.iterator();
        int i = 0;
        while (itrs.hasNext()) {
            SubMenus next = itrs.next();
            textSubmenu[i] = new JCheckBox();
            textSubmenu[i].setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
            textSubmenu[i].setForeground(new java.awt.Color(54, 63, 73));
            textSubmenu[i].setText(next.getSub_menu());
            textSubmenu[i].setActionCommand(next.getIdSubMenu() + "_" + next.getIdMenu());
            actuales.stream().filter((act) -> (act.getIdSubMenu() == next.getIdSubMenu())).forEachOrdered((_item) -> {
                textSubmenu[i].setSelected(true);
                MenusForUsuarios mfu = new MenusForUsuarios();
                mfu.setIdSubMenu(next.getIdSubMenu());
                mfu.setIdMenu(next.getIdMenu());
                mfu.setIdUsuario(idUser);
                listMenususuario.add(mfu);
            });
            textSubmenu[i].addItemListener(modal);
            panelSubmenus.add(textSubmenu[i]);
            listCheckBox.add(textSubmenu[i]);
        }
        javax.swing.GroupLayout mnu1Layout = new javax.swing.GroupLayout(pnMNU);
        pnMNU.setLayout(mnu1Layout);
        mnu1Layout.setHorizontalGroup(
                mnu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mnu1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mnu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mnu1Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(panelSubmenus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(mnu1Layout.createSequentialGroup()
                                                .addComponent(textMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(544, Short.MAX_VALUE))))
        );
        mnu1Layout.setVerticalGroup(
                mnu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mnu1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(textMenu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelSubmenus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        setMnusxusers(null);
        return pnMNU;
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

        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        pnMenus = new javax.swing.JPanel();
        mnu1 = new javax.swing.JPanel();
        lblHeader1 = new javax.swing.JLabel();
        pncontent1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnGuardarMenusUser = new javax.swing.JButton();
        btnCancelarMenusUser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Asignar Menus a Usuario");
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(403, 281));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(54, 63, 73));
        jPanel6.setPreferredSize(new java.awt.Dimension(574, 50));

        lblUser.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("Usuario:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUser)
                .addGap(94, 94, 94))
        );

        jPanel4.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        pnMenus.setBackground(new java.awt.Color(255, 255, 255));
        pnMenus.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Menus->Submenus", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12), new java.awt.Color(54, 63, 73))); // NOI18N
        pnMenus.setPreferredSize(new java.awt.Dimension(574, 370));
        pnMenus.setLayout(new java.awt.GridLayout(1, 4));

        mnu1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblHeader1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        lblHeader1.setForeground(new java.awt.Color(54, 63, 73));
        lblHeader1.setText("Menu");

        pncontent1.setLayout(new java.awt.GridLayout(10, 1, 5, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(54, 63, 73));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Submenu1");
        pncontent1.add(jLabel2);

        javax.swing.GroupLayout mnu1Layout = new javax.swing.GroupLayout(mnu1);
        mnu1.setLayout(mnu1Layout);
        mnu1Layout.setHorizontalGroup(
            mnu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mnu1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pncontent1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(mnu1Layout.createSequentialGroup()
                .addComponent(lblHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 555, Short.MAX_VALUE))
        );
        mnu1Layout.setVerticalGroup(
            mnu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mnu1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHeader1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pncontent1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnMenus.add(mnu1);

        jPanel4.add(pnMenus, java.awt.BorderLayout.CENTER);

        jPanel7.setPreferredSize(new java.awt.Dimension(574, 40));

        btnGuardarMenusUser.setBackground(new java.awt.Color(54, 63, 73));
        btnGuardarMenusUser.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnGuardarMenusUser.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarMenusUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        btnGuardarMenusUser.setText("Guardar");
        btnGuardarMenusUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnGuardarMenusUser.setContentAreaFilled(false);
        btnGuardarMenusUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarMenusUser.setFocusPainted(false);
        btnGuardarMenusUser.setHideActionText(true);
        btnGuardarMenusUser.setIconTextGap(1);
        btnGuardarMenusUser.setOpaque(true);
        btnGuardarMenusUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMenusUserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMenusUserMouseExited(evt);
            }
        });
        btnGuardarMenusUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMenusUserActionPerformed(evt);
            }
        });

        btnCancelarMenusUser.setBackground(new java.awt.Color(54, 63, 73));
        btnCancelarMenusUser.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnCancelarMenusUser.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarMenusUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel.png"))); // NOI18N
        btnCancelarMenusUser.setText("Cancelar");
        btnCancelarMenusUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnCancelarMenusUser.setContentAreaFilled(false);
        btnCancelarMenusUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelarMenusUser.setFocusPainted(false);
        btnCancelarMenusUser.setHideActionText(true);
        btnCancelarMenusUser.setIconTextGap(1);
        btnCancelarMenusUser.setOpaque(true);
        btnCancelarMenusUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMenusUserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMenusUserMouseExited(evt);
            }
        });
        btnCancelarMenusUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarMenusUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(535, Short.MAX_VALUE)
                .addComponent(btnGuardarMenusUser, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnCancelarMenusUser, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarMenusUser)
                    .addComponent(btnCancelarMenusUser))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarMenusUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMenusUserMouseEntered
        btnGuardarMenusUser.setBackground(new Color(124, 124, 124));
    }//GEN-LAST:event_btnGuardarMenusUserMouseEntered

    private void btnGuardarMenusUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMenusUserMouseExited
        btnGuardarMenusUser.setBackground(new Color(54, 63, 73));
    }//GEN-LAST:event_btnGuardarMenusUserMouseExited

    private void btnGuardarMenusUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMenusUserActionPerformed
        if (listMenususuario.isEmpty()) {
            DesktopNotify.showDesktopMessage("Aviso..!", "Debes Seleccionar almenos un menu", DesktopNotify.ERROR, 5000L);
        } else {
            getMnusxusers();
            mnusxusers.setListaMenus(listMenususuario);
            int result = 0;
            String msn = "Menus Asignados con exito..!";
            String msnerror = "Ocurrio un error al asignar los menus..!";
            result = mnusxusers.create();
            if (result > 0) {
                DesktopNotify.showDesktopMessage("Aviso..!", msn, DesktopNotify.SUCCESS, 5000L);
                //                cargarSedes();
                setMnusxusers(null);
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", msnerror, DesktopNotify.FAIL, 5000L);
            }

        }
    }//GEN-LAST:event_btnGuardarMenusUserActionPerformed

    private void btnCancelarMenusUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMenusUserMouseEntered
        btnCancelarMenusUser.setBackground(new Color(124, 124, 124));
    }//GEN-LAST:event_btnCancelarMenusUserMouseEntered

    private void btnCancelarMenusUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMenusUserMouseExited
        btnCancelarMenusUser.setBackground(new Color(54, 63, 73));
    }//GEN-LAST:event_btnCancelarMenusUserMouseExited

    private void btnCancelarMenusUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarMenusUserActionPerformed
//        listPerfilesToRol.clear();
//        setPerfil(null);
//        setPerfilxrol(null);
//        pnPerfiles.removeAll();
//        this.dispose();
    }//GEN-LAST:event_btnCancelarMenusUserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancelarMenusUser;
    public javax.swing.JButton btnGuardarMenusUser;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblHeader1;
    public javax.swing.JLabel lblUser;
    private javax.swing.JPanel mnu1;
    private javax.swing.JPanel pnMenus;
    private javax.swing.JPanel pncontent1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void itemStateChanged(ItemEvent e) {
        listCheckBox.forEach((JCheckBox t) -> {
            if (e.getSource() == t) {
                if (t.isSelected()) {
                    MenusForUsuarios mfu = new MenusForUsuarios();
                    String datos[] = t.getActionCommand().split("_");
                    mfu.setIdSubMenu(Integer.parseInt(datos[0]));
                    mfu.setIdMenu(Integer.parseInt(datos[1]));
                    mfu.setIdUsuario(idUser);
                    listMenususuario.add(mfu);
                } else {
                    Iterator<MenusForUsuarios> itr = listMenususuario.iterator();
                    while (itr.hasNext()) {
                        MenusForUsuarios borrar = itr.next();
                        if (borrar.toString().equals(t.getActionCommand().trim())) {
                            itr.remove();
                        }
                    }
                }
            }
        });

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

    public MenusForUsuarios getMnusxusers() {
        if (mnusxusers == null) {
            mnusxusers = new MenusForUsuarios();
        }
        return mnusxusers;
    }

    public void setMnusxusers(MenusForUsuarios mnusxusers) {
        this.mnusxusers = mnusxusers;
    }

}
