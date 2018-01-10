/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author admin
 */
public class HuellaID implements Serializable {

    private BigDecimal idhuella;
    private BigDecimal idUsuario;
    private String usuario;
    private BigDecimal idSede;
    private BigDecimal idempresa;
    private BigDecimal idPersona;

    public HuellaID() {
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getIdSede() {
        return idSede;
    }

    public void setIdSede(BigDecimal idSede) {
        this.idSede = idSede;
    }

    public BigDecimal getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(BigDecimal idempresa) {
        this.idempresa = idempresa;
    }

    public BigDecimal getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(BigDecimal idPersona) {
        this.idPersona = idPersona;
    }

    public BigDecimal getIdhuella() {
        return idhuella;
    }

    public void setIdhuella(BigDecimal idhuella) {
        this.idhuella = idhuella;
    }

}
