/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.GetLogin;
import Controllers.LoginController;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import java.util.Properties;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Mauricio Herrera
 */
public class Start {

    private static Login lg = null;

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        try {
            Properties props = new Properties();
            props.put("logoString", "M-Systems");
            AcrylLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {

        }
        lg = GetLogin.getLogin();
        lg.setVisible(true);
        lg.setLocationRelativeTo(null);
        LoginController lgController = new LoginController();
    }
}
