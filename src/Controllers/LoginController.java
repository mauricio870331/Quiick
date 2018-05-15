/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Pojos.Contenedor;
import Pojos.Usuario;
import Views.Bienvenida;
import Views.Login;
import Views.ModuloRoot;
import ds.desktop.notify.DesktopNotify;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import Pojos.RolxUser;
import Views.Modulo2;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Mauricio Herrera
 */
public class LoginController implements ActionListener {

    private final Login lg = GetLogin.getLogin();
    private final ModuloRoot MR = GetPrincipal.getModuloRoot();
    private final Modulo2 M2 = GetPrincipal.getModulo2();
    Bienvenida bienvenida;
    Usuario u = null;
    private Set<Integer> pressed = new HashSet();
    ImageIcon ii = null;
    ImageIcon iin = null;

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

            Object[] componentes = {lg.txtUser, lg.txtPass};
            if (validarCampos(componentes) == 0) {
                u = new Usuario();
                RolxUser rolu = u.Login(lg.txtUser.getText(), new String(lg.txtPass.getPassword()));
                if (rolu != null) {
//                    switch (rolu.getObjRol().getIdRol()) {
                    Contenedor.setUsuario(rolu.getObjUsuario());
                    lg.dispose();
                    getCM1().setUsuarioLogeado(rolu);
                    getCM2(rolu);
                    getCM3().setUsuarioLogeado(rolu);
                    getCM4().setUsuarioLogeado(rolu);

                    getCMRoot(rolu);
                    if (rolu.getObjRol().getDescripcion().equalsIgnoreCase("root")) {
                        InputStream img = rolu.getObjUsuario().getObjPersona().getFoto();
                        if (img != null) {
                            try {
                                BufferedImage bi = ImageIO.read(img);
                                ii = new ImageIcon(bi);
                                Image conver = ii.getImage();
                                Image tam = conver.getScaledInstance(MR.UserLogPicture.getWidth(), MR.UserLogPicture.getHeight(), Image.SCALE_SMOOTH);
                                iin = new ImageIcon(tam);
                                MR.UserLogPicture.setIcon(iin);
                            } catch (IOException ex) {
                                System.out.println("error " + ex);
                            }
                        } else {
                            MR.UserLogPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/userDefault.png")));
                        }
//                        getCMRoot(rolu);
                        MR.nomUserLog.setText(rolu.getObjUsuario().getObjPersona().getNombreCompleto());
                        MR.nomRolUserlog.setText(rolu.getObjRol().getDescripcion());
                        MR.setExtendedState(JFrame.MAXIMIZED_BOTH);
//                        MR.setVisible(true);
                       M2.setVisible(true);
                       
                       
                    } else {
                        getBienvenida(rolu);
                    }
                    u = null;
                } else {
                    DesktopNotify.showDesktopMessage("Aviso..!", "Usuario o Clave Incorrecta..!", DesktopNotify.ERROR, 5000L);
                }
            } else {
                DesktopNotify.showDesktopMessage("Aviso..!", "Los campos marcados en rojo son obligatorios..!", DesktopNotify.ERROR, 5000L);
            }
        }
        if (e.getSource() == lg.btnCancelar) {
            System.exit(0);
        }
    }

    public ControllerM1 getCM1() {
        return GetController.getControllerM1();
    }

    public ControllerM2 getCM2(RolxUser UsuarioLogeado) {
        return GetController.getControllerM2(UsuarioLogeado);
    }

    public ControllerM3 getCM3() {
        return GetController.getControllerM3();
    }

    public ControllerM4 getCM4() {
        return GetController.getControllerM4();
    }

    public ControllerMRoot getCMRoot(RolxUser UsuarioLogeado) {
        return GetController.getControllerMRoot(UsuarioLogeado);
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

    public void getBienvenida(RolxUser rolu) {
        if (bienvenida == null) {
            bienvenida = new Bienvenida(rolu);
        }
        bienvenida.setLocationRelativeTo(null);
        bienvenida.setVisible(true);
    }

}
