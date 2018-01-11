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
 * @author JuanDavid
 */
public class productosID implements Serializable {

    private BigDecimal cod_producto;
    private BigDecimal idCategoria;

    public productosID() {
    }

    public BigDecimal getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(BigDecimal cod_producto) {
        this.cod_producto = cod_producto;
    }

    public BigDecimal getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(BigDecimal idCategoria) {
        this.idCategoria = idCategoria;
    }

}
