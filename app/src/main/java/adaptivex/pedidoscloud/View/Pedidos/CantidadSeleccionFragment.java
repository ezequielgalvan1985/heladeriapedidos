package adaptivex.pedidoscloud.View.Pedidos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Core.WorkInteger;
import adaptivex.pedidoscloud.R;
import ivb.com.materialstepper.stepperFragment;

public class CantidadSeleccionFragment extends Fragment {
    private EditText numKilo;
    private EditText numTresCuartos;
    private EditText numMedio;
    private EditText numCuarto;

    private Button btnSiguiente;
    private Button btnAnterior;


    public CantidadSeleccionFragment() {
        // Required empty public constructor
    }





    private boolean validateForm(){
        boolean validate = false;
        numKilo         = (EditText) getView().findViewById(R.id.cantidad_num_kilo);
        numMedio        = (EditText) getView().findViewById(R.id.cantidad_num_medio);
        numCuarto       = (EditText) getView().findViewById(R.id.cantidad_num_cuarto);
        numTresCuartos  = (EditText) getView().findViewById(R.id.cantidad_num_trescuartos);

        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setKilo(WorkInteger.parseInteger(numKilo.getText().toString()));
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setMedio(WorkInteger.parseInteger(numMedio.getText().toString()));
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCuarto(WorkInteger.parseInteger(numCuarto.getText().toString()));
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setTrescuartos(WorkInteger.parseInteger(numTresCuartos.getText().toString()));

        if (GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getKilo()>0)        validate = true;
        if (GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMedio()>0)       validate = true;
        if (GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getTrescuartos()>0) validate = true;
        if (GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCuarto()>0)      validate = true;

        if (validate==false){
            Toast.makeText(getView().getContext(),"Debe Seleccionar al menos una Cantidad",Toast.LENGTH_LONG).show();
        }
        return validate;
    }


    // TODO: Rename and change types and number of parameters
    public static CantidadSeleccionFragment newInstance(String param1, String param2) {
        CantidadSeleccionFragment fragment = new CantidadSeleccionFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_cantidad_seleccion, container, false);
        GlobalValues.getINSTANCIA().CURRENT_FRAGMENT_NUEVO_PEDIDO = GlobalValues.getINSTANCIA().NP_CARGAR_CANTIDAD;
        numKilo         = (EditText) v.findViewById(R.id.cantidad_num_kilo);
        numMedio        = (EditText) v.findViewById(R.id.cantidad_num_medio);
        numCuarto       = (EditText) v.findViewById(R.id.cantidad_num_cuarto);
        numTresCuartos  = (EditText) v.findViewById(R.id.cantidad_num_trescuartos);

        btnSiguiente = (Button) v.findViewById(R.id.cantidad_btn_siguiente);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Realizar validaciones
                if(validateForm()){

                    ListadoPotesFragment fragment           = new ListadoPotesFragment();
                    FragmentManager fragmentManager         = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_nuevo_pedido, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }
        });



        return v;
    }



}
