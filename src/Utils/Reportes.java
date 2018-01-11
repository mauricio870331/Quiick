/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Controllers.GetPrincipal;
import Pojos.Persistencia;
import Pojos.Usuario;
import Views.Modulo1;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Mauricio Herrera
 */
public class Reportes extends Persistencia implements Runnable {

    private Usuario us;
    private final Modulo1 pr = GetPrincipal.getModulo1();
    SimpleDateFormat sa = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat saSegu = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private Date desde;
    private Date hasta;
    private String tipoReporte;

    public Reportes() {
        super();
    }

    @Override
    public void run() {
        try {
            String and = "";
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            JasperDesign jd = JRXmlLoader.load("src/Reportes/Pagosusuarios.jrxml");
            //parametros de entrada
            Map parametros = new HashMap();
            parametros.clear();
            parametros.put("logo", this.getClass().getResourceAsStream("/Reportes/logoUrbanGym.jpg"));
//            parametros.put("Bg", Copia);
//            parametros.put("retenciones", (retenciones.equals("") ? "Sin retenciones" : retenciones));           

            if (desde != null && hasta != null) {
                and = " and A.FechaPago between '" + sa.format(desde) + " 00:00:00' and '" + sa.format(hasta) + " 23:29:59'";
            }
            String query = "select A.*,B.Descripcion from PagoService A , tiposervice B"
                    + " where A.idTipoService=B.idTipoService and "
                    + "idUsuarioCliente=" + us.getObjUsuariosID().getIdUsuario() + and;
            System.out.println("query r " + query);
            JRDesignQuery nuevaC = new JRDesignQuery();
            nuevaC.setText(query);
            jd.setQuery(nuevaC);
            JasperReport jasperRep = JasperCompileManager.compileReport(jd);
            JasperPrint JasPrint = JasperFillManager.fillReport(jasperRep, parametros, this.getConecion().con);//null = parametros
            JasperViewer jv = new JasperViewer(JasPrint, false);
            jv.setVisible(true);
            jv.setLocationRelativeTo(null);
            jv.setTitle("Reportes");
//            pr.preloader.setVisible(false);
        } catch (JRException ex) {
            System.out.println("Error jasper: " + ex);
        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int create() {
        return 0;
    }

    @Override
    public int edit() {
        return 0;

    }

    @Override
    public int remove() {
        return 0;

    }

    @Override
    public java.util.List List() {
        return null;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public Usuario getUs() {
        return us;
    }

    public void setUs(Usuario us) {
        this.us = us;
    }

}
