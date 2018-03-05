package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Model.Cantidad;
import adaptivex.pedidoscloud.R;
import ivb.com.materialstepper.stepperFragment;

public class CargarOtrosDatosFragment extends stepperFragment {

    private EditText cantidad_cucuruchos;
    private EditText cantidad_cucharitas;


    @Override
    public boolean onNextButtonHandler() {
        // Obtener Cantidad seleccionada
        getOtrosDatos();



        return true;
    }

    private void getOtrosDatos(){
        Cantidad c = GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidad();
        cantidad_cucuruchos  = (EditText) getView().findViewById(R.id.cantidad_cucuruchos);
        cantidad_cucharitas  = (EditText) getView().findViewById(R.id.cantidad_cucharitas);

        c.setCucuruchos(Integer.parseInt(cantidad_cucuruchos.getText().toString()));
        c.setCucharitas(Integer.parseInt(cantidad_cucharitas.getText().toString()));
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCantidad(c);
    }

    public CargarOtrosDatosFragment() {
        // Required empty public constructor
    }


    public static CargarOtrosDatosFragment newInstance(String param1, String param2) {
        CargarOtrosDatosFragment fragment = new CargarOtrosDatosFragment();

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
        return inflater.inflate(R.layout.fragment_cargar_otros_datos, container, false);
    }



}
