package adaptivex.pedidoscloud.View.Pedidos;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import adaptivex.pedidoscloud.Model.Pote;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterPote;

public class CargarCantidadFragment extends Fragment implements View.OnClickListener{
    private Button btnAgregar,btnEditar, btnListo;
    private EditText txtCucuruchos, txtCucharas;
    private CheckBox chkEnvio;
    private Spinner spn_cantidad;

    private RecyclerView rvPotes;
    private RVAdapterPote rvAdapterPote;

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
        //btnEditar      = (Button)    v.findViewById(R.id.cantidad_btn_editar);
        btnListo        = (Button)   v.findViewById(R.id.cargar_cantidad_btn_listo);
        spn_cantidad    = (Spinner)  v.findViewById(R.id.cantidad_spn_cantidad);



        List<String> categories = new ArrayList<String>();
        categories.add("1 Kilo");
        categories.add("3/4 Kilo");
        categories.add("1/2 Kilo");
        categories.add("1/4 Kilo");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spn_cantidad.setAdapter(dataAdapter);

        //btnEditar.setOnClickListener(this);
        btnAgregar.setOnClickListener(this);
        btnListo.setOnClickListener(this);



        //Se agrega Recycle view de Potes Cargados
        PedidoController pc = new PedidoController(v.getContext());
        ArrayList<Pote> listaPotes = new ArrayList<Pote>();
        listaPotes = pc.abrir().getPotesArrayList(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getIdTmp());
        rvPotes = (RecyclerView)v.findViewById(R.id.cargar_cantidad_rvPotes);
        rvPotes.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        rvPotes.setLayoutManager(llm);

        rvAdapterPote = new RVAdapterPote();
        rvAdapterPote.setCtx(getContext());
        rvAdapterPote.setFragmentManager(getFragmentManager());
        rvAdapterPote.setPotes(listaPotes);
        rvPotes.setAdapter(rvAdapterPote);






        return v;
    }


    public void openCargarHelados(){
        CargarHeladosFragment fragment          = new CargarHeladosFragment();
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
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
    /*

    */





    public void clickAgregar(){
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.agregarPote(getSpinnerSelection());
        GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_NRO_POTE    = GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidadPotes();
        GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_MEDIDA_POTE = getSpinnerSelection();
        savePedido();
        openCargarHelados();
    }

    public boolean savePedido(){
        //Obtiene valores del formulario, y luego lo guarda en la base de datos
        try{

            PedidoController pc = new PedidoController(getContext());
            pc.abrir().modificar(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL,true);
            return true;
        }catch (Exception e){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }
    }



    public void openCargarOtrosDatos(){
        CargarOtrosDatosFragment fragment = new CargarOtrosDatosFragment();
        callFragment(fragment);
    }

    public void callFragment(Fragment fragment){
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(Constants.FRAGMENT_CARGAR_CANTIDAD);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cantidad_btn_agregar:
                //Agregar pote, se suma cantidad de potes y se agrega cantidad de kilos
                clickAgregar();
                break;



            case R.id.cargar_cantidad_btn_listo:
                //Realizar validaciones
                if(validateForm()){
                    //Guardar datos en Pedido
                    openCargarOtrosDatos();
                }
                break;

        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
