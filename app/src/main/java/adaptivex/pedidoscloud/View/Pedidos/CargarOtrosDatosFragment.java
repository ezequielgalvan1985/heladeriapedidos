package adaptivex.pedidoscloud.View.Pedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Core.WorkInteger;
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
        cantidad_cucuruchos  = (EditText) getView().findViewById(R.id.cantidad_cucuruchos);
        cantidad_cucharitas  = (EditText) getView().findViewById(R.id.cantidad_cucharitas);

        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCucuruchos(WorkInteger.parseInteger(cantidad_cucuruchos.getText().toString()));
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCucharitas(WorkInteger.parseInteger(cantidad_cucharitas.getText().toString()));

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
