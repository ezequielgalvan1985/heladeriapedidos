package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Controller.PedidodetalleController;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.ItemHelado;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterHelado;

public class CargarHeladosFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Cursor cursorHelados;
    //Variables
    private RecyclerView rvHelados;

    private ArrayList<Producto> listaHelados = new ArrayList<Producto>();
    private RVAdapterHelado rvAdapterHelado;

    //Lista que se carga cuando se recibe por parametro el pedidoid y nropote
    private ArrayList<Pedidodetalle> listaHeladosSelected = new ArrayList<Pedidodetalle>();
    private long    pedido_android_id;
    private Integer pedido_nro_pote;






    public CargarHeladosFragment() {
        // Required empty public constructor
    }


    public static CargarHeladosFragment newInstance(String param1, String param2) {
        CargarHeladosFragment fragment = new CargarHeladosFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pedido_android_id = getArguments().getLong(Constants.PARAM_PEDIDO_ANDROID_ID);
            pedido_nro_pote = getArguments().getInt(Constants.PARAM_PEDIDO_NRO_POTE);

            //Leer Pedidod detalles por pedidoId y nroPote
            PedidodetalleController pdc = new PedidodetalleController(getContext());
            Cursor c = pdc.abrir().findByPedidoAndroidIdAndNroPote(pedido_android_id,pedido_nro_pote);
            pdc.cerrar();
            listaHeladosSelected = pdc.abrir().parseCursorToArrayList(c);
            String str = "";
            for (Pedidodetalle pd :listaHeladosSelected){
                str = str + " Pedidodetalle: " + pd.getIdTmp().toString() + "\n";

            }
            Toast.makeText(getContext(),str,Toast.LENGTH_LONG ).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_cargar_helados, container, false);
            ProductoController dbHelper = new ProductoController(v.getContext());

            rvHelados = (RecyclerView)v.findViewById(R.id.rvHelados);
            rvHelados.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            rvHelados.setLayoutManager(llm);

            rvAdapterHelado = new RVAdapterHelado();
            rvAdapterHelado.setCtx(getContext());
            listaHelados = dbHelper.findAll();
            rvAdapterHelado.setListaHeladosSelected(listaHeladosSelected);
            rvAdapterHelado.setProductos(listaHelados);
            rvHelados.setAdapter(rvAdapterHelado);

            Button btnListo = (Button) v.findViewById(R.id.cargar_helados_btn_listo);
            btnListo.setOnClickListener(this);


            return v;
        }catch(Exception e){
            Toast.makeText(getContext(), "Error: " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }






    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void clickListo(){
        if (validateForm()){
            // Gennerar los pedidos detalles
            if(savePedidodetalle()){
                //Cerrar Fragment actual
                openFragmentCargarCantidad();
            };
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cargar_helados_btn_listo:
                clickListo();
                break;



        }
    }

    public void openFragmentCargarCantidad(){
        getFragmentManager().beginTransaction().remove(this).commit();
        CargarCantidadFragment fragment      = new CargarCantidadFragment();
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_nuevo_pedido, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void deletePedidodetalleIfExists(ArrayList<Pedidodetalle> paramListaHeladosSelected){
        PedidodetalleController pdc = new PedidodetalleController(getContext());
        for(Pedidodetalle pd: paramListaHeladosSelected){
            pdc.abrir().eliminar(pd);
        }
    }
    public boolean savePedidodetalle(){

        try{
            PedidodetalleController pdc = new PedidodetalleController(getContext());

            //Primero se deben eliminar todos los pedidodetalles para este pedido y pote
            deletePedidodetalleIfExists(listaHeladosSelected);

            //Se limpia el array de Pedidodetalle dentro de Pedido, para luego agregar todos los que se seleccionaro ahora
            ArrayList<Pedidodetalle> lista = new ArrayList<Pedidodetalle>();
            GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setDetalles(lista);

            for (int i=0; i<GlobalValues.getINSTANCIA().listaHeladosSeleccionados.size(); i++){
                if (GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(i).isChecked()){
                    ItemHelado item = (ItemHelado) (GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(i));
                    Pedidodetalle pd = new Pedidodetalle();
                    pd.setId(0);
                    pd.setPedidoTmpId(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getIdTmp());
                    pd.setEstadoId(Constants.ESTADO_NUEVO);
                    pd.setMedidaPote(GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_MEDIDA_POTE);
                    pd.setMonto(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getPrecioMedidaPote(GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_MEDIDA_POTE));
                    pd.setCantidad(Double.parseDouble(item.getProporcion().toString())); //POCO - EQUILIBRADO - MUCHO
                    pd.setProporcionHelado(item.getProporcion()); //POCO - EQUILIBRADO - MUCHO
                    pd.setNroPote(GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_NRO_POTE);
                    pd.setProducto(item.getHelado());

                    GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.addPedidodetalle(pd);

                    long idAndroid = pdc.abrir().agregar(pd);

                    Toast.makeText(getContext(), "Pedidodetalle Generado: " + String.valueOf(idAndroid), Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }catch (Exception e ){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }




    public boolean validateForm(){
        boolean validate = true;
        Integer contador = 0;
        try{

            for (int i=0; i<GlobalValues.getINSTANCIA().listaHeladosSeleccionados.size(); i++){
                if (GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(i).isChecked()){
                    contador++;
                }
            }

            if (GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_MEDIDA_POTE==Constants.MEDIDA_KILO && contador > 4){
                Toast.makeText(getContext(), "Solo se Pueden Elegir Hasta 4 Helados ",Toast.LENGTH_LONG).show();
                validate = false;
            }

            if (GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_MEDIDA_POTE!=Constants.MEDIDA_KILO && contador > 3){
                Toast.makeText(getContext(), "Solo se Pueden Elegir Hasta 3 Helados ",Toast.LENGTH_LONG).show();
                validate = false;
            }
            if ( contador == 0){
                Toast.makeText(getContext(), "Debe Seleccionar al Menos 1 Helado ",Toast.LENGTH_LONG).show();
                validate = false;
            }
            return validate;

        }catch (Exception e){
            Toast.makeText(getContext(), "Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
            return validate;
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
