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
public class PagoServiceID implements Serializable {

    private BigDecimal idPago;
    private BigDecimal idUsuarioCliente;
    private String usuarioCliente;
    private BigDecimal idSedeCliente;
    private BigDecimal idempresaCliente;
    private BigDecimal idPersonaCliente;
    private BigDecimal idcaja;
    private BigDecimal idUsuario;
    private String usuario;
    private BigDecimal idSede;
    private BigDecimal idempresa;
    private BigDecimal idPersona;

    public PagoServiceID() {
    }

    public BigDecimal getIdPago() {
        return idPago;
    }

    public void setIdPago(BigDecimal idPago) {
        this.idPago = idPago;
    }

    public BigDecimal getIdUsuarioCliente() {
        return idUsuarioCliente;
    }

    public void setIdUsuarioCliente(BigDecimal idUsuarioCliente) {
        this.idUsuarioCliente = idUsuarioCliente;
    }

    public String getUsuarioCliente() {
        return usuarioCliente;
    }

    public void setUsuarioCliente(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;
    }

    public BigDecimal getIdSedeCliente() {
        return idSedeCliente;
    }

    public void setIdSedeCliente(BigDecimal idSedeCliente) {
        this.idSedeCliente = idSedeCliente;
    }

    public BigDecimal getIdempresaCliente() {
        return idempresaCliente;
    }

    public void setIdempresaCliente(BigDecimal idempresaCliente) {
        this.idempresaCliente = idempresaCliente;
    }

    public BigDecimal getIdPersonaCliente() {
        return idPersonaCliente;
    }

    public void setIdPersonaCliente(BigDecimal idPersonaCliente) {
        this.idPersonaCliente = idPersonaCliente;
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
