package adaptivex.pedidoscloud.Model;

/**
 * Created by ezequiel on 25/06/2016.
 */
public class Pedidodetalle {
    private Integer id;
    private Integer idTmp;
    private Integer pedidoId;
    private long pedidoTmpId;
    private Integer productoId;
    private Double cantidad;
    private Double preciounitario;
    private Double monto;
    private Integer estadoId;
    private Producto producto;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }



    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(Double preciounitario) {
        this.preciounitario = preciounitario;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }


    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public Integer getIdTmp() {
        return idTmp;
    }

    public void setIdTmp(Integer idTmp) {
        this.idTmp = idTmp;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public long getPedidoTmpId() {
        return pedidoTmpId;
    }

    public void setPedidoTmpId(long pedidoTmpId) {
        this.pedidoTmpId = pedidoTmpId;
    }


}
