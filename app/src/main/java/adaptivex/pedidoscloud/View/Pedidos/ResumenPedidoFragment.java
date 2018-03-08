package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.R;
import ivb.com.materialstepper.stepperFragment;

public class ResumenPedidoFragment extends stepperFragment {



    @Override
    public boolean onNextButtonHandler() {

        return true;
    }

    public ResumenPedidoFragment() {
        // Required empty public constructor
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
        TextView direccion = (TextView) v.findViewById(R.id.resumen_pedido_direccion);
        TextView cantidad = (TextView) v.findViewById(R.id.resumen_pedido_cantidad);
        TextView cucuruchos = (TextView) v.findViewById(R.id.resumen_pedido_cucuruchos);
        TextView cucharitas = (TextView) v.findViewById(R.id.resumen_pedido_cucharitas);


        direccion.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getDireccion().getStringDireccion());
        cucuruchos.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidad().getCucuruchos().toString());
        cucharitas.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidad().getCucharitas().toString());
        cantidad.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidad().calculateCantidadKilos().toString() +"Kg");


        return v;
    }


}
