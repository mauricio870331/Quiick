/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

/**
 *
 * @author Mauricio Herrera
 */
public class GetPrincipalController {

    public static PrincipalController prc = null;

    public static PrincipalController getPrincipalController() {
        try {
            if (prc == null) {
                prc = new PrincipalController();                
            } else {
                System.out.println("PrincipalControler ya estaba instanceado");
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }
        return prc;
    }
}
