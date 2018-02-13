package Pojos;

import java.io.Serializable;
import java.math.BigDecimal;

public class compra_productoID implements Serializable {

    public BigDecimal cod_compraproducto;
    public BigDecimal cod_proveedor;
    public String cod_factura;

    public compra_productoID() {
    }

    public BigDecimal getCod_compraproducto() {
        return cod_compraproducto;
    }

    public void setCod_compraproducto(BigDecimal cod_compraproducto) {
        this.cod_compraproducto = cod_compraproducto;
    }

    public BigDecimal getCod_proveedor() {
        return cod_proveedor;
    }

    public void setCod_proveedor(BigDecimal cod_proveedor) {
        this.cod_proveedor = cod_proveedor;
    }

    public String getCod_factura() {
        return cod_factura;
    }

    public void setCod_factura(String cod_factura) {
        this.cod_factura = cod_factura;
    }

}
