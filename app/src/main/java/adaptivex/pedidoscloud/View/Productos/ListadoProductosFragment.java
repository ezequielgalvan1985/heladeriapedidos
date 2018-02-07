package adaptivex.pedidoscloud.View.Productos;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ClienteController;
import adaptivex.pedidoscloud.Controller.MemoController;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Core.ParameterHelper;
import adaptivex.pedidoscloud.Model.Cliente;
import adaptivex.pedidoscloud.Model.MarcaDataBaseHelper;
import adaptivex.pedidoscloud.Model.Producto;

import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterProducto;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListadoProductosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListadoProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListadoProductosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_CURSORPRODUCTOS = "cursorproductos";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Cursor cursorProductos;
    private OnFragmentInteractionListener mListener;

    //Variables
    private RecyclerView rvProductos;
    private RecyclerView.Adapter mAdapter;
    private  ArrayList<Producto> arrayOfProductos = new ArrayList<Producto>();




    public ListadoProductosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListadoProductosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListadoProductosFragment newInstance(String param1, String param2) {
        ListadoProductosFragment fragment = new ListadoProductosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            //mCursor = getArguments().getString(ARG_CURSORPRODUCTOS);

        }
        GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPRODUCTOS);

    }

    public void checkServiceWorking(){
        try {
            ParameterHelper ph = new ParameterHelper(this.getContext());
            while (ph.isServiceStockPrecioWorking()) {
                Toast.makeText(getContext(), "La Aplicación esta actualizando Stocks y Precios, Aguarde un momento por favor...", Toast.LENGTH_SHORT).show();
                Thread.sleep(2000);
            }
        }catch (Exception e ){
            Toast.makeText(getContext(), "Error " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Generar Vista

        View vista = inflater.inflate(R.layout.fragment_listado_productos, container, false);
        try{
            //DATOS
            TextView txtTitleProductos = (TextView) vista.findViewById(R.id.txtTitleProductos);
            txtTitleProductos.setText("Listado de Productos");
            ProductoController dbHelper = new ProductoController(vista.getContext());
            arrayOfProductos = new ArrayList<Producto>();
            String datos = "";
            Producto p2;

            //Chequea Filtro de texto o Voz, SI NO SE REALIZO EL FILTRO, se muestran todos
            if (getCursorProductos()== null){
                arrayOfProductos = cargarListadoTodosProductos();
            }

            //Chequea Filtro de Memo, si entro por Recordatorio
            if(GlobalValues.getINSTANCIA().IS_MEMO){
                ClienteController cc = new ClienteController(this.getContext());
                Cliente c = cc.abrir().buscar(GlobalValues.getINSTANCIA().CLIENTE_ID_PEDIDO_ACTUAL);
                txtTitleProductos.setText("Productos Comprados por: "+ c.getRazonsocial().toString());
                //Filtrar memo
                MemoController mc = new MemoController(this.getContext());
                arrayOfProductos = mc.abrir().findByClienteId(GlobalValues.getINSTANCIA().CLIENTE_ID_PEDIDO_ACTUAL);
                GlobalValues.getINSTANCIA().IS_MEMO = false;
            }

            //Si todavia listado de productos esta vacio, se carga con todos
            if (arrayOfProductos == null){
                arrayOfProductos = cargarListadoTodosProductos();
            }
            if (arrayOfProductos.size() < 1 ){
                arrayOfProductos = cargarListadoTodosProductos();
            }

            //Con RECYCLEVIEW
            rvProductos = (RecyclerView)vista.findViewById(R.id.rvProductos);
            rvProductos.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

            LinearLayoutManager llm = new LinearLayoutManager(vista.getContext());
            rvProductos.setLayoutManager(llm);
            RVAdapterProducto rvAdapterProducto = new RVAdapterProducto();

            rvAdapterProducto.setCtx(getContext());
            rvAdapterProducto.setProductos(arrayOfProductos);

            rvProductos.setAdapter(rvAdapterProducto);

        }catch(Exception e ){
            Toast.makeText(getContext(), "Error: " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }
            return vista;
    }

    public ArrayList<Producto> cargarListadoTodosProductos(){
        try{
            ProductoController dbHelper = new ProductoController(getContext());
            cursorProductos = dbHelper.abrir().obtenerTodos();
            dbHelper.cerrar();
            arrayOfProductos = new ArrayList<Producto>();

            for(cursorProductos.moveToFirst(); !cursorProductos.isAfterLast(); cursorProductos.moveToNext()) {
                Integer id = cursorProductos.getInt(cursorProductos.getColumnIndex(MarcaDataBaseHelper.CAMPO_ID));
                Producto p2 = dbHelper.abrir().buscar(id);
                dbHelper.cerrar();
                arrayOfProductos.add(p2);
            }
            return arrayOfProductos;
        }catch (Exception e ){
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
            Bundle bundle = new Bundle();
            bundle.putString("datos", "datos que necesito");

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

    public Cursor getCursorProductos() {
        return cursorProductos;
    }

    public void setCursorProductos(Cursor cursorProductos) {
        this.cursorProductos = cursorProductos;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
