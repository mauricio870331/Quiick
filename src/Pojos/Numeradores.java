/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author JuanDavid
 */
public class Numeradores extends Persistencia implements Serializable {

    private String numerador;
    private int rangoIni;
    private int rangoFin;
    private int secuencia;
    private String estado;

    public Numeradores() {
        super();
    }

    public String getNumerador() {
        return numerador;
    }

    public void setNumerador(String numerador) {
        this.numerador = numerador;
    }

    public int getRangoIni() {
        return rangoIni;
    }

    public void setRangoIni(int rangoIni) {
        this.rangoIni = rangoIni;
    }

    public int getRangoFin() {
        return rangoFin;
    }

    public void setRangoFin(int rangoFin) {
        this.rangoFin = rangoFin;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int create() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int edit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public java.util.List List() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int SecuenciaXNumerador(String numerador) {
        int secuencia = 0;
        try {            
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().cstmt = this.getConecion().con.prepareCall("{call GetNumerador(?)}");
            this.getConecion().cstmt.setString(1, numerador);

            ResultSet rs = Numeradores.super.getConecion().cstmt.executeQuery();
            if (rs.next()) {                
                secuencia = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return secuencia;
    }
    
//    public static void main(String[] args) {
//        Numeradores n=new Numeradores();
//        System.out.println("-- " + n.SecuenciaXNumerador("Factura"));
//    }
}
