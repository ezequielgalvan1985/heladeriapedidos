package adaptivex.pedidoscloud.View.Pedidos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Core.WorkNumber;
import adaptivex.pedidoscloud.R;

public class CargarOtrosDatosFragment extends Fragment implements View.OnClickListener {

    private EditText txtCucuruchos;
    private EditText txtCucharitas;
    private CheckBox chkEnvio;
    private Button   btnListo;



    private void getDataForm(){
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCucuruchos(WorkNumber.parseInteger(txtCucuruchos.getText().toString()));
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCucharitas(WorkNumber.parseInteger(txtCucharitas.getText().toString()));
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setEnvioDomicilio(chkEnvio.isChecked());

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
        View v = inflater.inflate(R.layout.fragment_cargar_otros_datos, container, false);
        txtCucuruchos  = (EditText) v.findViewById(R.id.otros_datos_cantidad_cucuruchos);
        txtCucharitas  = (EditText) v.findViewById(R.id.otros_datos_cantidad_cucharitas);
        chkEnvio       = (CheckBox) v.findViewById(R.id.otros_datos_chk_envio);
        btnListo       = (Button)   v.findViewById(R.id.otros_datos_btn_listo);

        btnListo.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.otros_datos_btn_listo:
                clickListo();
        }
    }


    public void clickListo(){
        //Se usa ClickListo por si en el futuro se tiene que hacer alguna otra cosa en el
        //evento click del boton listo
        if (savePedido()){
            openResumenFragment();
        };
    }

    public void openResumenFragment(){
        ResumenPedidoFragment fragment = new ResumenPedidoFragment();
        //getFragmentManager().beginTransaction().remove(this).commit();
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(Constants.FRAGMENT_CARGAR_OTROS_DATOS);
        fragmentTransaction.commit();
    }

    public boolean savePedido(){
        //Obtiene valores del formulario, y luego lo guarda en la base de datos
        try{
            getDataForm();
            PedidoController pc = new PedidoController(getContext());
            pc.abrir().edit(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL);
            return true;
        }catch (Exception e){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }
    }



}
