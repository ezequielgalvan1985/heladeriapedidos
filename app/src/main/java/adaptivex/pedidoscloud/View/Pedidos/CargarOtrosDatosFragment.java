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
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ParameterController;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Core.WorkNumber;
import adaptivex.pedidoscloud.Core.WorkString;
import adaptivex.pedidoscloud.Model.Parameter;
import adaptivex.pedidoscloud.R;

public class CargarOtrosDatosFragment extends Fragment implements View.OnClickListener {

    private EditText txtCucuruchos, txtMontoAbona,txtCucharitas;
    private TextView txtCucuruchoPrecio, txt_monto_total_helados;
    private CheckBox chkEnvio;
    private Button   btnListo;



    private void getDataForm(){
        if (txtCucuruchos.getText()!= null) GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCucuruchos(WorkNumber.parseInteger(txtCucuruchos.getText().toString()));
        if (txtCucharitas.getText()!= null) GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCucharitas(WorkNumber.parseInteger(txtCucharitas.getText().toString()));
        if (txtMontoAbona.getText()!= null) GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setMontoabona(WorkNumber.parseDouble(txtMontoAbona.getText().toString()));
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
        try{
            View v = inflater.inflate(R.layout.fragment_cargar_otros_datos, container, false);
            //Inicializacion de Objetos
            txtCucuruchoPrecio      = (TextView) v.findViewById(R.id.cargar_otros_datos_txt_cucuruchos_precio);
            txtCucuruchos           = (EditText) v.findViewById(R.id.otros_datos_cantidad_cucuruchos);
            txtCucharitas           = (EditText) v.findViewById(R.id.otros_datos_cantidad_cucharitas);
            txtMontoAbona           = (EditText) v.findViewById(R.id.otros_datos_txt_monto_abona);
            chkEnvio                = (CheckBox) v.findViewById(R.id.otros_datos_chk_envio);
            btnListo                = (Button)   v.findViewById(R.id.otros_datos_btn_listo);
            txt_monto_total_helados = (TextView) v.findViewById(R.id.otros_datos_txt_monto_total_helados);

            //Cargar datos en los Objetos
            btnListo.setOnClickListener(this);

            txtCucuruchoPrecio.setText("Cucurucho ("+GlobalValues.getINSTANCIA().PRECIO_CUCURUCHO_MONEY+" c/u):");
            txt_monto_total_helados.setText("Monto a Pagar: "+ GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoHeladoFormatMoney());


            return v;
        }catch (Exception e){
            return null;
        }

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
        if (validateForm()){
            if (savePedido()){
                openResumenFragment();
            };
        }
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


    private boolean validateForm(){
        boolean validate = true;
        String message = "";
        Double monto = WorkNumber.parseDouble(txtMontoAbona.getText().toString());
        if (monto > 0.00 && monto < GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoHelados() )
        {
            validate  = false;
            message ="* Monto ABONADO debe ser mayor a Monto a PAGAR \n";
        }



        if (validate == false){
            Toast.makeText(getView().getContext(),message,Toast.LENGTH_LONG).show();
        }
        return validate;
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
