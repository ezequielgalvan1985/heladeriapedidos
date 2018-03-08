package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Core.WorkInteger;
import adaptivex.pedidoscloud.Model.Cantidad;
import adaptivex.pedidoscloud.R;
import ivb.com.materialstepper.stepperFragment;

public class CantidadSeleccionFragment extends stepperFragment {
    private EditText numKilo;
    private EditText numTresCuartos;
    private EditText numMedio;
    private EditText numCuarto;



    public CantidadSeleccionFragment() {
        // Required empty public constructor
    }

    @Override
    public boolean onNextButtonHandler() {
        // Obtener Cantidad seleccionada
        //Validar, debe haber un dato seleccionado
        boolean validate = false;
        Cantidad c = getCantidad();
        if (validateForm(c)){
            GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCantidad(c);
            validate = true;
            Toast.makeText(getView().getContext(),GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidad().getStringCantidadHelado(),Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getView().getContext(),"Debe Seleccionar al menos una Cantidad",Toast.LENGTH_LONG).show();
        }
        return validate;
    }

    private boolean validateForm(Cantidad c){
        boolean respuesta = false;
        if (c.getKilo()>0)   respuesta = true;
        if (c.getMedio()>0)  respuesta = true;
        if (c.getTrescuartos()>0) respuesta = true;
        if (c.getCuarto()>0) respuesta = true;
        return respuesta;
    }

    private Cantidad getCantidad(){
        Cantidad c = new Cantidad();

        numKilo         = (EditText) getView().findViewById(R.id.cantidad_num_kilo);
        numMedio        = (EditText) getView().findViewById(R.id.cantidad_num_medio);
        numCuarto       = (EditText) getView().findViewById(R.id.cantidad_num_cuarto);
        numTresCuartos  = (EditText) getView().findViewById(R.id.cantidad_num_trescuartos);

        c.setKilo(WorkInteger.parseInteger(numKilo.getText().toString()));
        c.setMedio(WorkInteger.parseInteger(numMedio.getText().toString()));
        c.setCuarto(WorkInteger.parseInteger(numCuarto.getText().toString()));
        c.setTrescuartos(WorkInteger.parseInteger(numTresCuartos.getText().toString()));

        return c;
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

        numKilo         = (EditText) v.findViewById(R.id.cantidad_num_kilo);
        numMedio        = (EditText) v.findViewById(R.id.cantidad_num_medio);
        numCuarto       = (EditText) v.findViewById(R.id.cantidad_num_cuarto);
        numTresCuartos  = (EditText) v.findViewById(R.id.cantidad_num_trescuartos);

       

        return v;
    }



}
