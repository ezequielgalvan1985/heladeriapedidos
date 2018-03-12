package adaptivex.pedidoscloud.Core.Components;

import adaptivex.pedidoscloud.Model.Pedidodetalle;

/**
 * Created by egalvan on 10/3/2018.
 */

public class ListItemPote {

    private Pedidodetalle pedidodetalle;


    //constructor initializing values
    public ListItemPote(Pedidodetalle pd) {
        this.setPedidodetalle(pd);
    }


    public Pedidodetalle getPedidodetalle() {
        return pedidodetalle;
    }

    public void setPedidodetalle(Pedidodetalle pedidodetalle) {
        this.pedidodetalle = pedidodetalle;
    }
}
