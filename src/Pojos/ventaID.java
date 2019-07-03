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
 * @author Juan
 */
public class ventaID implements Serializable{
    private BigDecimal cod_factura;
    private BigDecimal idTipoVenta;
    private BigDecimal idCaja;
    private BigDecimal idUsuario;
    private String usuario;
    private BigDecimal idSede;
    private BigDecimal idEmpresa;
    private BigDecimal idPersona;

    public ventaID() {        
    }

    public BigDecimal getCod_factura() {
        return cod_factura;
    }

    public void setCod_factura(BigDecimal cod_factura) {
        this.cod_factura = cod_factura;
    }

    public BigDecimal getIdTipoVenta() {
        return idTipoVenta;
    }

    public void setIdTipoVenta(BigDecimal idTipoVenta) {
        this.idTipoVenta = idTipoVenta;
    }

    public BigDecimal getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(BigDecimal idCaja) {
        this.idCaja = idCaja;
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

    public BigDecimal getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigDecimal idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public BigDecimal getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(BigDecimal idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public String toString() {
        return "ventaID{" + "cod_factura=" + cod_factura + ", idTipoVenta=" + idTipoVenta + ", idCaja=" + idCaja + ", idUsuario=" + idUsuario + ", usuario=" + usuario + ", idSede=" + idSede + ", idEmpresa=" + idEmpresa + ", idPersona=" + idPersona + '}';
    }
    
    
    
}
