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
import android.widget.CheckBox;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Model.Cantidad;
import adaptivex.pedidoscloud.R;
import ivb.com.materialstepper.stepperFragment;

public class CantidadSeleccionFragment extends stepperFragment {
    private CheckBox chkKilo;
    private CheckBox chkTresCuartos;
    private CheckBox chkMedio;
    private CheckBox chkCuarto;



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
        }else{
            Toast.makeText(getView().getContext(),"Debe Seleccionar al menos una Cantidad",Toast.LENGTH_LONG).show();
        }
        return validate;
    }

    private boolean validateForm(Cantidad c){
        boolean respuesta = false;
        if (c.isKilo())   respuesta = true;
        if (c.isMedio())  respuesta = true;
        if (c.isCuarto()) respuesta = true;
        if (c.isTrescuartos()) respuesta = true;
        return respuesta;
    }

    private Cantidad getCantidad(){
        Cantidad c = new Cantidad();
        chkKilo         = (CheckBox) getView().findViewById(R.id.cantidad_chk_kilo);
        chkMedio        = (CheckBox) getView().findViewById(R.id.cantidad_chk_medio);
        chkCuarto       = (CheckBox) getView().findViewById(R.id.cantidad_chk_cuarto);
        chkTresCuartos  = (CheckBox) getView().findViewById(R.id.cantidad_chk_trescuartos);

        c.setKilo(chkKilo.isChecked());
        c.setMedio(chkMedio.isChecked());
        c.setCuarto(chkCuarto.isChecked());
        c.setTrescuartos(chkTresCuartos.isChecked());

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
        return inflater.inflate(R.layout.fragment_cantidad_seleccion, container, false);
    }



}
