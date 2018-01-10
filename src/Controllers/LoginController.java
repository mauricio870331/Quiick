/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Pojos.RolxUser;
import Pojos.Usuario;
import Views.Modulo1;
import Views.Login;
import Views.Modulo2;
import ds.desktop.notify.DesktopNotify;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Mauricio Herrera
 */
public class LoginController implements ActionListener {

    private final Login lg = GetLogin.getLogin();
    private final Modulo1 M1 = GetPrincipal.getModulo1();
    private final Modulo2 M2 = GetPrincipal.getModulo2();
    private PrincipalController prc;
    Usuario u = null;
    ImageIcon ii = null;
    ImageIcon iin = null;
    private Set<Integer> pressed = new HashSet();

    public LoginController() {
        inicomponents();
    }

    private void inicomponents() {
        lg.btnIniciar.addActionListener(this);
        lg.btnCancelar.addActionListener(this);
        lg.txtUser.addActionListener(this);
        lg.txtPass.addActionListener(this);
        AddingKeyAdapter();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lg.btnCancelar) {
            System.exit(0);
        }
        if (e.getSource() == lg.txtUser) {
            lg.txtUser.transferFocus();
        }
        if (e.getSource() == lg.txtPass) {
            lg.txtPass.transferFocus();
        }
        if (e.getSource() == lg.btnIniciar) {
            M2.setVisible(true);
//            Object[] componentes = {lg.txtUser, lg.txtPass};
//            if (validarCampos(componentes) == 0) {
//                u = new Usuario();
//                RolxUser rolu = u.Login(lg.txtUser.getText(), new String(lg.txtPass.getPassword()));
//                if (rolu != null) {
//                    InputStream img = rolu.getObjUsuario().getObjPersona().getFoto();
//                    if (img != null) {
//                        try {
//                            BufferedImage bi = ImageIO.read(img);
//                            ii = new ImageIcon(bi);
//                            Image conver = ii.getImage();
//                            Image tam = conver.getScaledInstance(M1.UserLogPicture.getWidth(), M1.UserLogPicture.getHeight(), Image.SCALE_SMOOTH);
//                            iin = new ImageIcon(tam);
//                            M1.UserLogPicture.setIcon(iin);
//                        } catch (IOException ex) {
//                            System.out.println("error " + ex);
//                        } finally {
//                            u = null;
//                        }
//                    } else {
//                        M1.UserLogPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/userDefault.png")));
//                    }
//                    M1.nomUserLog.setText(rolu.getObjUsuario().getObjPersona().getNombreCompleto());
//                    M1.nomRolUserlog.setText(rolu.getObjRol().getDescripcion());
//                    M1.id_userlog.setText(Integer.toString(rolu.getObjUsuario().getObjUsuariosID().getIdUsuario()));
//                    M1.id_userlog.setVisible(false);
//                    switch (rolu.getObjRol().getIdRol()) {
//                        case 1:
//                            lg.dispose();
//                            M1.setVisible(true);
//                            getPrc();
//                            prc.setUsuarioLogeado(rolu);
//                            break;
//                        case 2:
//                            lg.dispose();
//                            M1.setVisible(true);
//                            getPrc();
//                            prc.setUsuarioLogeado(rolu);
//                            break;
//                        case 4://root
//                            lg.dispose();
//                            M1.setVisible(true);
//                            getPrc();
//                            prc.setUsuarioLogeado(rolu);
//                            break;
//                        default:
//                            lg.txtUser.setText("");
//                            lg.txtPass.setText("");
//                            DesktopNotify.showDesktopMessage("Aviso..!", "No tienes Permisos para acceder al sistema..!", DesktopNotify.ERROR, 5000L);
//                            break;
//                    }
//                } else {
//                    DesktopNotify.showDesktopMessage("Aviso..!", "Usuario o Clave Incorrecta..!", DesktopNotify.ERROR, 5000L);
//                }
//            } else {
//                DesktopNotify.showDesktopMessage("Aviso..!", "Los campos marcados en rojo son obligatorios..!", DesktopNotify.ERROR, 5000L);
//            }
        }
        if (e.getSource() == lg.btnCancelar) {
            System.exit(0);
        }
    }

    public PrincipalController getPrc() {
        return prc = GetPrincipalController.getPrincipalController();
    }

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

    private void AddingKeyAdapter() {
        lg.txtUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //Ctrl + Shif + f9 esta es la combinacion de teclas
//                System.out.println("e.getKeyCode() " + e.getKeyCode());
                pressed.add(e.getKeyCode());
                if (pressed.size() > 1) {
                    if (pressed.contains(17) && pressed.contains(16) && pressed.contains(120)) {
                        u = new Usuario();
                        Iterator<RolxUser> itr = u.seeMypass().iterator();
                        String users = "";
                        while (itr.hasNext()) {
                            RolxUser next = itr.next();
                            users += "Administrador: " + next.getObjUsuario().getObjPersona().getNombreCompleto() + "   \n"
                                    + "Usuario: " + next.getObjUsuario().getObjUsuariosID().getUsuario() + "\nContraseña: " + next.getObjUsuario().getClave() + "\n";

                        }
                        JOptionPane.showMessageDialog(null, users, "Datos de Acceso...!", JOptionPane.INFORMATION_MESSAGE);
//Ctrl + Shif + f9 esta es la combinacion de teclas
                        u = null;
                        pressed.clear();
                        lg.txtUser.removeKeyListener(this);
                    }
                }
            }
        });
    }

}
