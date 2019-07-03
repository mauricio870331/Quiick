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
public class ventaproductoID implements Serializable{
    
    private BigDecimal ventaproducto;
    private BigDecimal cod_factura;

    public ventaproductoID() {
    }

    public BigDecimal getVentaproducto() {
        return ventaproducto;
    }

    public void setVentaproducto(BigDecimal ventaproducto) {
        this.ventaproducto = ventaproducto;
    }

    public BigDecimal getCod_factura() {
        return cod_factura;
    }

    public void setCod_factura(BigDecimal cod_factura) {
        this.cod_factura = cod_factura;
    }

    @Override
    public String toString() {
        return "ventaproductoID{" + "ventaproducto=" + ventaproducto + ", cod_factura=" + cod_factura + '}';
    }
    
    
    
    
}
