/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import Controllers.ControllerM2;
import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author Juan
 */
public class objectobusqueda implements Serializable {

    private String titulo;
    private String filtro;
    private int modulo;
    private int condicion;
    private ControllerM2 M2;
    ArrayList<Object> ListObjectos = new ArrayList();
    ArrayList<Object> ListObjectosReturns = new ArrayList();

    public objectobusqueda() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }

    public ControllerM2 getM2() {
        return M2;
    }

    public void setM2(ControllerM2 M2) {
        this.M2 = M2;
    }

    public ArrayList<Object> getListObjectos() {
        return ListObjectos;
    }

    public void setListObjectos(ArrayList<Object> ListObjectos) {
        this.ListObjectos = ListObjectos;
    }
    

}
