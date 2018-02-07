package adaptivex.pedidoscloud.Model;

import java.util.Date;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class Parameter {
    private Integer id;
    private String nombre;
    private String valor_texto;
    private Date valor_fecha;
    private Double valor_decimal;
    private Integer valor_integer;
    private String descripcion;


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


    public String getValor_texto() {
        return valor_texto;
    }

    public void setValor_texto(String valor_texto) {
        this.valor_texto = valor_texto;
    }

    public Date getValor_fecha() {
        return valor_fecha;
    }

    public void setValor_fecha(Date valor_fecha) {
        this.valor_fecha = valor_fecha;
    }

    public Double getValor_decimal() {
        return valor_decimal;
    }

    public void setValor_decimal(Double valor_decimal) {
        this.valor_decimal = valor_decimal;
    }

    public Integer getValor_integer() {
        return valor_integer;
    }

    public void setValor_integer(Integer valor_integer) {
        this.valor_integer = valor_integer;
    }
}
