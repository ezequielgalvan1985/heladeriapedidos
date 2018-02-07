package adaptivex.pedidoscloud.Model;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class Hojarutadetalle {
    private Integer id;
    private Integer hojaruta_id;
    private Integer cliente_id;
    private String hora;
    private String notas;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHojaruta_id() {
        return hojaruta_id;
    }

    public void setHojaruta_id(Integer hojaruta_id) {
        this.hojaruta_id = hojaruta_id;
    }

    public Integer getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Integer cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
