package adaptivex.pedidoscloud.View.Pedidos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Core.WorkInteger;
import adaptivex.pedidoscloud.R;

public class CargarCantidadFragment extends Fragment implements View.OnClickListener{
    private Button btnAgregar,btnEditar, btnListo;
    private EditText txtCucuruchos, txtCucharas;
    private CheckBox chkEnvio;
    private Spinner spn_cantidad;
    private TextView lbl_cantidad_kilos, lbl_kilos_monto, lbl_cucuruchos_monto, lbl_monto_total;


    public CargarCantidadFragment() {
        // Required empty public constructor
    }



    private boolean validateForm(){
        boolean validate = false;

        //Cantidad Cargada tiene que ser mayor a 0
        if (GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidadKilos() > 0  ){
            return true;
        }

        if (validate==false){
            Toast.makeText(getView().getContext(),"Debe Agregar al menos un Pote",Toast.LENGTH_LONG).show();
        }
        return validate;
    }


    // TODO: Rename and change types and btnber of parameters
    public static CargarCantidadFragment newInstance(String param1, String param2) {
        CargarCantidadFragment fragment = new CargarCantidadFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_cargar_cantidad, container, false);

        GlobalValues.getINSTANCIA().CURRENT_FRAGMENT_NUEVO_PEDIDO = GlobalValues.getINSTANCIA().NP_CARGAR_CANTIDAD;

        btnAgregar      = (Button)   v.findViewById(R.id.cantidad_btn_agregar);
        btnEditar      = (Button)    v.findViewById(R.id.cantidad_btn_editar);
        btnListo        = (Button)   v.findViewById(R.id.cargar_cantidad_btn_listo);
        spn_cantidad    = (Spinner)  v.findViewById(R.id.cantidad_spn_cantidad);
        txtCucharas     = (EditText) v.findViewById(R.id.cargar_cantidad_cucharitas);
        txtCucuruchos   = (EditText) v.findViewById(R.id.cargar_cantidad_cucuruchos);
        chkEnvio        = (CheckBox) v.findViewById(R.id.cargar_cantidad_chk_envio);


        lbl_cantidad_kilos    = (TextView) v.findViewById(R.id.cargar_cantidad_lbl_cantidad_kilos);
        lbl_kilos_monto       = (TextView) v.findViewById(R.id.cargar_cantidad_lbl_kilos_monto);
        lbl_cucuruchos_monto  = (TextView) v.findViewById(R.id.cargar_cantidad_lbl_cucuruchos_monto);
        lbl_monto_total       = (TextView) v.findViewById(R.id.cargar_cantidad_lbl_monto_total);

        List<String> categories = new ArrayList<String>();
        categories.add("1 Kg");
        categories.add("3/4 Kg");
        categories.add("1/2 Kg");
        categories.add("1/4 Kg");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spn_cantidad.setAdapter(dataAdapter);

        btnEditar.setOnClickListener(this);
        btnAgregar.setOnClickListener(this);
        btnListo.setOnClickListener(this);

        refreshTextViews();

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


    public Integer getSpinnerSelection(){
        Integer medida_selected = 0;
        try{
            if (spn_cantidad.getSelectedItemPosition()== 0){
                medida_selected =  Constants.MEDIDA_KILO;
            }
            if (spn_cantidad.getSelectedItemPosition()== 1){
                medida_selected =  Constants.MEDIDA_TRESCUARTOS;
            }
            if (spn_cantidad.getSelectedItemPosition()== 2){
                medida_selected =  Constants.MEDIDA_MEDIO;
            }
            if (spn_cantidad.getSelectedItemPosition()== 3){
                medida_selected =  Constants.MEDIDA_CUARTO;
            }
            return medida_selected;
        }catch (Exception e){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public boolean savePedido(){
        //Obtiene valores del formulario, y luego lo guarda en la base de datos
        try{
            GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setEnvioDomicilio(chkEnvio.isChecked());
            GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCucharitas(WorkInteger.parseInteger(txtCucharas.getText().toString()));
            GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCucuruchos(WorkInteger.parseInteger(txtCucuruchos.getText().toString()));

            PedidoController pc = new PedidoController(getContext());
            pc.abrir().modificar(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL,true);
            return true;
        }catch (Exception e){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }
    }


    public void refreshTextViews(){
        try{
            //Actualizar valor de los textview, formatear valores pesos a #.##
            lbl_cantidad_kilos.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getKilosHeladosString());
            lbl_kilos_monto.setText("$ " + String.valueOf(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoHelados()));
            lbl_cucuruchos_monto.setText("$ " + String.valueOf(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoCucuruchos()));
            lbl_monto_total.setText("$ " + String.valueOf(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMonto()));
        }catch(Exception e ){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    public void clickAgregar(){
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.agregarPote(getSpinnerSelection());
        GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_NRO_POTE    = GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidadPotes();
        GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_MEDIDA_POTE = getSpinnerSelection();
        refreshTextViews();
        savePedido();
        openCargarHelados();
    }

    public void clickEditar(){
        try{
            //Obtener pedido Id TMP O ANDROID
            long idAndroid = GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getIdTmp();
            ListadoPotesFragment fragment  = new ListadoPotesFragment();
            Bundle args = new Bundle();
            args.putLong(Constants.PARAM_ANDROID_ID, idAndroid);
            //Pasar como parametro al frament LIsta de Potes
            fragment.setArguments(args);
            //LLamar al frament LIsta potes
            callFragment(fragment);
        }catch(Exception e){
            Toast.makeText(getContext(),"Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void openResumenFragment(){
        ResumenPedidoFragment fragment      = new ResumenPedidoFragment();
        callFragment(fragment);
    }

    public void callFragment(Fragment fragment){
        getFragmentManager().beginTransaction().remove(this).commit();
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_nuevo_pedido, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cantidad_btn_agregar:
                //Agregar pote, se suma cantidad de potes y se agrega cantidad de kilos
                clickAgregar();
                break;
            case R.id.cantidad_btn_editar:
                //Agregar pote, se suma cantidad de potes y se agrega cantidad de kilos
                clickEditar();
                break;



            case R.id.cargar_cantidad_btn_listo:
                //Realizar validaciones
                if(validateForm()){
                    //Guardar datos en Pedido
                    if (savePedido()){
                        openResumenFragment();
                    }
                }
                break;

        }
    }





}
