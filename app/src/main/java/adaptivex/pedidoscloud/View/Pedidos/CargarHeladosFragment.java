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
import android.view.KeyEvent;
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

public class CargarHeladosFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {

    private OnFragmentInteractionListener mListener;

    //Variables
    private RecyclerView rvHelados;

    private ArrayList<Object> listaHelados = new ArrayList<Object>();
    private RVAdapterHelado rvAdapterHelado;

    //Lista que se carga cuando se recibe por parametro el pedidoid y nropote

    private long    pedido_android_id = 0 ;
    private Integer pedido_nro_pote = 0 ;






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

            /* Edicion de pedido */
            pedido_android_id = getArguments().getLong(Constants.PARAM_PEDIDO_ANDROID_ID);
            pedido_nro_pote = getArguments().getInt(Constants.PARAM_PEDIDO_NRO_POTE);



        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_cargar_helados, container, false);
            ArrayList<Pedidodetalle> listaHeladosSelected = new ArrayList<Pedidodetalle>();

            rvHelados = (RecyclerView)v.findViewById(R.id.rvHelados);
            rvHelados.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            rvHelados.setLayoutManager(llm);

            rvAdapterHelado = new RVAdapterHelado();
            rvAdapterHelado.init(v.getContext(), pedido_android_id, pedido_nro_pote );

            rvHelados.setAdapter(rvAdapterHelado);


            Button btnListo = (Button) v.findViewById(R.id.cargar_helados_btn_listo);
            btnListo.setOnClickListener(this);

            return v;
        }catch(Exception e){
            Toast.makeText(getContext(), "Error: " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private ArrayList<Pedidodetalle> cargarListaHeladosSeleccionados(View v){
        //Leer Pedidod detalles por pedidoId y nroPote
        PedidodetalleController pdc = new PedidodetalleController(v.getContext());
        Cursor c = pdc.abrir().findByPedidoAndroidIdAndNroPote(pedido_android_id, pedido_nro_pote);
        pdc.cerrar();
        return  pdc.abrir().parseCursorToArrayList(c);

    }
    private void cargarListaHeladosTodos( View v){
        ProductoController dbHelper = new ProductoController(v.getContext());
        listaHelados = dbHelper.abrir().findAllToArrayList();
    }



    private ArrayList<ItemHelado> cargarListaItemsHelados ( View v){
        /*Genera lista de items */
        ArrayList<ItemHelado> arrayListItemHelado = new ArrayList<ItemHelado>();


        PedidodetalleController pdc = new PedidodetalleController(v.getContext());
        Cursor c = pdc.abrir().findByPedidoAndroidIdAndNroPote(pedido_android_id, pedido_nro_pote);
        pdc.cerrar();
        ArrayList<Pedidodetalle> listaHeladosSelected = new ArrayList<Pedidodetalle>();
        listaHeladosSelected = pdc.abrir().parseCursorToArrayList(c);

        ProductoController dbHelper = new ProductoController(v.getContext());
        listaHelados = dbHelper.abrir().findAllToArrayList();

        //crear la lista de Items helado
        //Recorrer lista de productos

        for(Object o: listaHelados){
            Producto p       = (Producto) o;
            Pedidodetalle pd = checkHelado(p,listaHeladosSelected);
            ItemHelado ih = new ItemHelado(p, false,75);

            if (pd!=null){
                ih.setPedidodetalle(pd);
                ih.setChecked(true);
                ih.setProporcion(pd.getProporcionHelado());
            }
            arrayListItemHelado.add(ih);
        }
        return arrayListItemHelado;
    }


    public Pedidodetalle checkHelado(Producto p, ArrayList<Pedidodetalle> listaHeladosSelected ){
        //devuelve el pedidodetalle que coincide con el Producto,
        // y devuelve el pedido detalle para el producto
        //Pregunta si el producto esta dentro de los seleccionados y devuelve el pedidodetalle asociado


        try{
            Pedidodetalle pdSelected = null;
            if (listaHeladosSelected != null){
                if (listaHeladosSelected.size()> 0 ){
                    for(Pedidodetalle pd: listaHeladosSelected){
                        if (pd.getProducto().getId()==p.getId()) {
                            // El Item fue seleccionado
                            pdSelected =  pd;
                        }
                    }
                }
            }
            return pdSelected;

        }catch (Exception e ){

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
        //getFragmentManager().beginTransaction().remove(this).commit();
        CargarCantidadFragment fragment      = new CargarCantidadFragment();
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(Constants.FRAGMENT_CARGAR_HELADOS);
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
            PedidoController pc = new PedidoController(getContext());

            /*
            * 1 recorrer lista de items seleccionados
            * 2 consultar si el item esta en selecionado, obtener item
            * 3     consultar si item tiene pedidodetalle asignado, entonces se actualiza en DB y en Listatemporal
            * 4
         * * */
            for (int i=0; i<GlobalValues.getINSTANCIA().listaHeladosSeleccionados.size(); i++){
                if (GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(i).isChecked()){

                    ItemHelado item = (ItemHelado) (GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(i));
                    Pedidodetalle pd = new Pedidodetalle();
                    if (item.getPedidodetalle() ==null){
                        //agregar item
                        pd.setId(0);
                        pd.setNroPote(GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_NRO_POTE);

                    }else{
                        //Editar Item
                        pd = item.getPedidodetalle();
                    }
                    pd.setPedidoTmpId(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getIdTmp());
                    pd.setEstadoId(Constants.ESTADO_NUEVO);
                    pd.setMedidaPote(GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_MEDIDA_POTE);
                    pd.setMonto(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getPrecioMedidaPote(GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_MEDIDA_POTE));
                    pd.setCantidad(Double.parseDouble(item.getProporcion().toString())); //POCO - EQUILIBRADO - MUCHO
                    pd.setProporcionHelado(item.getProporcion()); //POCO - EQUILIBRADO - MUCHO

                    pd.setProducto(item.getHelado());




                    if (item.getPedidodetalle() == null){
                        //agregar item
                        long idAndroid = pdc.abrir().agregar(pd);
                        Integer idandroidinteger = (int) (long) idAndroid;
                        pd.setIdTmp(idandroidinteger);
                        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.addPedidodetalle(pd);

                    }else{
                        //Editar Item

                        pdc.abrir().modificar(pd);
                        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.editPedidodetalle(pd);

                    }



                }
            }
            pc.abrir().edit(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL);
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

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK )
        {
            if (validateForm()){
                return true;
            }
        }
        return false;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
