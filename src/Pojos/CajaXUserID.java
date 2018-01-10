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
public class CajaXUserID implements Serializable {

    private BigDecimal idcaja;
    private BigDecimal idUsuario;
    private String usuario;
    private BigDecimal idSede;
    private BigDecimal idempresa;
    private BigDecimal idPersona;

    public CajaXUserID() {
    }

    public BigDecimal getIdcaja() {
        return idcaja;
    }

    public void setIdcaja(BigDecimal idcaja) {
        this.idcaja = idcaja;
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

}
