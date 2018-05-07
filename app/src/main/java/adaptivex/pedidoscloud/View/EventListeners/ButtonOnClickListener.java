package adaptivex.pedidoscloud.View.EventListeners;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.Servicios.HelperPedidos;

/**
 * Created by Ezequiel on 06/04/2017.
 */

public class ButtonOnClickListener  implements  View.OnClickListener {
    private Context ctx;

    public ButtonOnClickListener(Context c){
        setCtx(c);
    }
    @Override
    public void onClick(View v) {




    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
}
