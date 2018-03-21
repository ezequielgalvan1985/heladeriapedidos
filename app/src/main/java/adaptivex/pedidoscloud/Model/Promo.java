package adaptivex.pedidoscloud.Model;

import java.util.Date;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class Promo {
    private Integer id;
    private Integer idAndroid;
    private String nombre;
    private String descripcion;
    private String fechaDesde;
    private String fechaHasta;
    private Integer cantKilos;
    private Double importeDescuento;
    private Double precioPromo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



    public Integer getCantKilos() {
        return cantKilos;
    }

    public void setCantKilos(Integer cantKilos) {
        this.cantKilos = cantKilos;
    }

    public Double getImporteDescuento() {
        return importeDescuento;
    }

    public void setImporteDescuento(Double importeDescuento) {
        this.importeDescuento = importeDescuento;
    }

    public Double getPrecioPromo() {
        return precioPromo;
    }

    public void setPrecioPromo(Double precioPromo) {
        this.precioPromo = precioPromo;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Integer getIdAndroid() {
        return idAndroid;
    }

    public void setIdAndroid(Integer idAndroid) {
        this.idAndroid = idAndroid;
    }
}
