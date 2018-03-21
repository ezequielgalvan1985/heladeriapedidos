package adaptivex.pedidoscloud.View.Pedidos;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.Productos.ListadoHeladosFragment;
import ivb.com.materialstepper.stepperFragment;

public class ResumenPedidoFragment extends Fragment {


    private TextView lbl_cantidad_kilos, lbl_kilos_monto, lbl_cucuruchos_monto, lbl_monto_total;
    private TextView txt_cucuruchos, txt_direccion, txt_cucharitas, txt_monto_total, txtEnvio,
                     txt_pedido_id, txt_hora_entrega, txt_estado;

    public ResumenPedidoFragment() {

    }


    public static ResumenPedidoFragment newInstance(String param1, String param2) {
        ResumenPedidoFragment fragment = new ResumenPedidoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resumen_pedido, container, false);
        txt_direccion        = (TextView) v.findViewById(R.id.resumen_pedido_direccion);
        lbl_cantidad_kilos   = (TextView) v.findViewById(R.id.resumen_pedido_lbl_cantidad_kilos);
        lbl_kilos_monto      = (TextView) v.findViewById(R.id.resumen_pedido_lbl_kilos_monto);
        txt_cucuruchos       = (TextView) v.findViewById(R.id.resumen_pedido_txt_cucuruchos);
        lbl_cucuruchos_monto = (TextView) v.findViewById(R.id.resumen_pedido_txt_cucuruchos_monto);
        txt_cucharitas       = (TextView) v.findViewById(R.id.resumen_pedido_txt_cucharitas);
        lbl_monto_total      = (TextView) v.findViewById(R.id.resumen_pedido_txt_monto_total);
        txtEnvio             = (TextView) v.findViewById(R.id.resumen_pedido_txt_envio);
        txt_monto_total      = (TextView) v.findViewById(R.id.resumen_pedido_txt_monto_total);

        txt_estado           = (TextView) v.findViewById(R.id.resumen_pedido_txt_estado);
        txt_hora_entrega     = (TextView) v.findViewById(R.id.resumen_pedido_txt_hora_entrega);
        txt_pedido_id        = (TextView) v.findViewById(R.id.resumen_pedido_txt_pedido_id);

        refreshTextViews();
        return v;
    }

    public void refreshTextViews(){
        try{
            //Actualizar valor de los textview, formatear valores pesos a #.##
            txt_direccion.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getStringDireccion());
            lbl_cantidad_kilos.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getKilosHeladosString());
            lbl_kilos_monto.setText("$ " + String.valueOf(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoHelados()));
            txt_cucuruchos.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCucuruchos().toString());
            lbl_cucuruchos_monto.setText("$ " + String.valueOf(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoCucuruchos()));
            txt_cucharitas.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCucharitas().toString());
            txt_monto_total.setText("$ " + String.valueOf(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMonto()));
            txtEnvio.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getEnvioDomicilio());

            txt_pedido_id.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getId());
            txt_estado.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getEstadoId());
            txt_hora_entrega.setText("20:30");



        }catch(Exception e ){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }









}
