package Pojos;

import java.io.Serializable;
import java.math.BigDecimal;

public class compradetalleID implements Serializable {

    public BigDecimal idCompra;
    public String serieProducto;
    public BigDecimal idDetalle;

    public compradetalleID() {
    }

    public BigDecimal getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(BigDecimal idCompra) {
        this.idCompra = idCompra;
    }

    public String getSerieProducto() {
        return serieProducto;
    }

    public void setSerieProducto(String serieProducto) {
        this.serieProducto = serieProducto;
    }

    public BigDecimal getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(BigDecimal idDetalle) {
        this.idDetalle = idDetalle;
    }

}
