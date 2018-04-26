package adaptivex.pedidoscloud.Model;

/**
 * Created by egalvan on 13/3/2018.
 */

public class ItemHelado {
    private boolean checked;
    private Producto helado;
    private Integer proporcion;
    private Pedidodetalle pedidodetalle;

    public ItemHelado(){}

    public ItemHelado(Producto h, boolean c, Integer p){
        setHelado(h);
        setChecked(c);
        setProporcion(p);
    }
    public ItemHelado(Producto h, boolean c, Integer p, Pedidodetalle pd){
        setHelado(h);
        setChecked(c);
        setProporcion(p);
        setPedidodetalle(pd);
    }

    public boolean isChecked(){
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Producto getHelado() {
        return helado;
    }

    public void setHelado(Producto helado) {
        this.helado = helado;
    }

    public Integer getProporcion() {
        return proporcion;
    }

    public void setProporcion(Integer proporcion) {
        this.proporcion = proporcion;
    }

    public Pedidodetalle getPedidodetalle() {
        return pedidodetalle;
    }

    public void setPedidodetalle(Pedidodetalle pedidodetalle) {
        this.pedidodetalle = pedidodetalle;
    }
}
