package adaptivex.pedidoscloud.View.Pedidos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidodetalleController;
import adaptivex.pedidoscloud.Core.WorkInteger;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.Pote;
import adaptivex.pedidoscloud.R;
import ivb.com.materialstepper.stepperFragment;

public class CantidadSeleccionFragment extends Fragment implements View.OnClickListener{
    private Button btnKilo;
    private Button btnTresCuartos;
    private Button btnMedio;
    private Button btnCuarto;

    private Button btnSiguiente;
    private Button btnAnterior;


    public CantidadSeleccionFragment() {
        // Required empty public constructor
    }



    private boolean validateForm(){
        boolean validate = false;



        if (validate==false){
            Toast.makeText(getView().getContext(),"Debe Seleccionar al menos una Cantidad",Toast.LENGTH_LONG).show();
        }
        return validate;
    }


    // TODO: Rename and change types and btnber of parameters
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

        btnKilo         = (Button) v.findViewById(R.id.cantidad_btn_kilo);
        btnMedio        = (Button) v.findViewById(R.id.cantidad_btn_medio);
        btnCuarto       = (Button) v.findViewById(R.id.cantidad_btn_cuarto);
        btnTresCuartos  = (Button) v.findViewById(R.id.cantidad_btn_trescuartos);
        btnSiguiente    = (Button) v.findViewById(R.id.cantidad_btn_siguiente);
        btnAnterior     = (Button) v.findViewById(R.id.cantidad_btn_anterior);

        btnKilo.setOnClickListener(this);
        btnMedio.setOnClickListener(this);
        btnCuarto.setOnClickListener(this);
        btnTresCuartos.setOnClickListener(this);
        btnAnterior.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
        return v;
    }


    public void openCargarHelados(){
        CargarHeladosFragment fragment          = new CargarHeladosFragment();
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_nuevo_pedido, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cantidad_btn_kilo:
                GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_NRO_POTE =GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.agregarPote();
                GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_MEDIDA_POTE = Constants.MEDIDA_KILO;
                openCargarHelados();

                break;
            case R.id.cantidad_btn_trescuartos:

                break;
            case R.id.cantidad_btn_medio:

                break;
            case R.id.cantidad_btn_cuarto:

                break;

            case R.id.cantidad_btn_anterior:

                break;

            case R.id.cantidad_btn_siguiente:
                //Realizar validaciones
                if(validateForm()){

                }
                break;

        }
    }





}
