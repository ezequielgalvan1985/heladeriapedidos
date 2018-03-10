package adaptivex.pedidoscloud.Model;

import java.util.ArrayList;

/**
 * Created by egalvan on 9/3/2018.
 */

public class Pote {
    private long idweb;  //Clave web
    private long idandroid; //Clave android
    private long pedidodetalle_id; //FK

    private Integer contenido; //medida si es de 1 kilo 3/4, medio, o cuarto, expresado en gramos 1000, 750, 500, 250
    private ArrayList <PoteItem> itemsPote; // es el helado que va a estar en el pote

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

    public long getPedidodetalle_id() {
        return pedidodetalle_id;
    }

    public void setPedidodetalle_id(long pedidodetalle_id) {
        this.pedidodetalle_id = pedidodetalle_id;
    }

    public Integer getContenido() {
        return contenido;
    }

    public void setContenido(Integer contenido) {
        this.contenido = contenido;
    }

    public ArrayList<PoteItem> getItemsPote() {
        return itemsPote;
    }

    public void setItemsPote(ArrayList<PoteItem> itemsPote) {
        this.itemsPote = itemsPote;
    }
}
