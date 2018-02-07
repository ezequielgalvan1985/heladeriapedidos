package adaptivex.pedidoscloud.Model;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class Marca {
    private Integer id;
    private String nombre;
    private String descripcion;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
