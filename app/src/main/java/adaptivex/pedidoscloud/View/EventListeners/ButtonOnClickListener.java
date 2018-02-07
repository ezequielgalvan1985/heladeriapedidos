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

        if (v.getId() == R.id.btnEliminarPedido) {
            try {
                TextView tvDpfIdTmp =  (TextView)v.findViewById(R.id.tvDpfIdTmp);
                PedidoController pc = new PedidoController(getCtx());
                pc.abrir().deleteByIdTmp(Long.valueOf(tvDpfIdTmp.getText().toString()));
                pc.cerrar();
                GlobalValues.getINSTANCIA().setPEDIDO_ACTION_VALUE(GlobalValues.getINSTANCIA().PEDIDO_ACTION_DELETE);
                Toast.makeText(getCtx(), "Eliminado Correctamente ", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getCtx(), "Error RVAdapterPedido: " + e.getMessage(), Toast.LENGTH_LONG);
            }

        }else if (v.getId() == R.id.btnEnviarPedido2) {

            try{
                TextView tvDpfIdTmp =  (TextView)v.findViewById(R.id.tvDpfIdTmp);
                HelperPedidos hp = new HelperPedidos(v.getContext(), Long.valueOf(tvDpfIdTmp.getText().toString()),GlobalValues.getINSTANCIA().ENVIAR_PEDIDO);
                hp.execute();
            }catch (Exception e){
                Toast.makeText(v.getContext(),"Error RVAdapterPedido: "+ e.getMessage(), Toast.LENGTH_LONG);
            }
        }


    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
}
