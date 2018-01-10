/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;


/**
 *
 * @author admin
 */
public class AsistenciaID implements Serializable {

    private int idAsistencia;
    private int idUsuario;
    private String usuario;
    private int idSede;
    private int idempresa;
    private int idPersona;

    public AsistenciaID() {
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public String toString() {
        return "AsistenciaID{" + "idAsistencia=" + idAsistencia + ", idUsuario=" + idUsuario + ", usuario=" + usuario + ", idSede=" + idSede + ", idempresa=" + idempresa + ", idPersona=" + idPersona + '}';
    }
    
    

}
