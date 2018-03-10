package adaptivex.pedidoscloud.Model;

/**
 * Created by egalvan on 9/3/2018.
 */

public class PoteItem {
    private long idweb;
    private long idandroid;

    private Producto producto;
    private Integer  cantidad;

    public long getIdweb() {
        return idweb;
    }

    public void setIdweb(long idweb) {
        this.idweb = idweb;
    }

    public long getIdandroid() {
        return idandroid;
    }

    public void setIdandroid(long idandroid) {
        this.idandroid = idandroid;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
