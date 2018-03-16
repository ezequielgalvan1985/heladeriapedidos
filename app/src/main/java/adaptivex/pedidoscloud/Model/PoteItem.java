package adaptivex.pedidoscloud.Model;

import adaptivex.pedidoscloud.Config.Constants;

/**
 * Created by egalvan on 9/3/2018.
 */

public class PoteItem {
    private Producto producto;
    private Integer  cantidad;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public String getCantidadDescripction(){
        String texto = "";
        if (this.cantidad < Constants.MEDIDA_HELADO_EQUILIBRADO_DESDE){
            texto =  Constants.MEDIDA_HELADO_POCO;
        }
        if (this.cantidad >= Constants.MEDIDA_HELADO_EQUILIBRADO_DESDE && this.cantidad <= Constants.MEDIDA_HELADO_EQUILIBRADO_HASTA  ){
            texto =  Constants.MEDIDA_HELADO_EQUILIBRADO;
        }
        if (this.cantidad >= Constants.MEDIDA_HELADO_MUCHO_LIMIT_DESDE && this.cantidad <= Constants.MEDIDA_HELADO_MUCHO_LIMIT_HASTA  ){
            texto =  Constants.MEDIDA_HELADO_MUCHO;
        }
        return texto;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
