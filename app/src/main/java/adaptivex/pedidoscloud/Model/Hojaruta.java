package adaptivex.pedidoscloud.Model;

import java.util.ArrayList;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class Hojaruta {
    private Integer id;
    private Integer user_id;
    private Integer dia_id;
    private String titulo;
    private String notas;
    private ArrayList<Hojarutadetalle> items;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getDia_id() {
        return dia_id;
    }

    public void setDia_id(Integer dia_id) {
        this.dia_id = dia_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public ArrayList<Hojarutadetalle> getItems() {
        return items;
    }

    public void setItems(ArrayList<Hojarutadetalle> items) {
        this.items = items;
    }
}
