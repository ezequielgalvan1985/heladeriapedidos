package adaptivex.pedidoscloud.View.Pedidodetalles;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adaptivex.pedidoscloud.Controller.PedidodetalleController;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.PedidodetalleDataBaseHelper;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterPedidodetalle;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListadoPedidodetallesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListadoPedidodetallesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListadoPedidodetallesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Variables
    private RecyclerView rvPedidodetalles;
    private RecyclerView.Adapter mAdapter;


    public ListadoPedidodetallesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListadoPedidodetallesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListadoPedidodetallesFragment newInstance(String param1, String param2) {
        ListadoPedidodetallesFragment fragment = new ListadoPedidodetallesFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Generar Vista
        View vista = inflater.inflate(R.layout.fragment_listado_pedidodetalles, container, false);

        //DATOS
        PedidodetalleController dbHelper = new PedidodetalleController(vista.getContext());
        ArrayList<Pedidodetalle> arrayOfPedidodetalles = new ArrayList<Pedidodetalle>();
        Cursor c = dbHelper.abrir().obtenerTodos();
        String datos = "";
        Pedidodetalle registro;

        Producto producto;
        ProductoController dbProducto = new ProductoController(vista.getContext());

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            registro = new Pedidodetalle();
            registro.setPedidoTmpId(c.getLong(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP)));
            registro.setPedidoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID)));

            registro.setProductoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID)));
            producto = dbProducto.abrir().buscar(registro.getProductoId());
            registro.setProducto(producto);
            dbProducto.cerrar();
            int i = registro.getIdTmp();
            registro.setCantidad(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_CANTIDAD)));
            registro.setPreciounitario(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO)));
            registro.setMonto(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_MONTO)));
            registro.setEstadoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID)));
            arrayOfPedidodetalles.add(registro);
            registro = null;
        }


        //Con RECYCLEVIEW
        rvPedidodetalles = (RecyclerView)vista.findViewById(R.id.rvPedidodetalles);

        LinearLayoutManager llm = new LinearLayoutManager(vista.getContext());
        rvPedidodetalles.setLayoutManager(llm);

        RVAdapterPedidodetalle rvAdapterPedidodetalle = new RVAdapterPedidodetalle();
        rvAdapterPedidodetalle.setPedidodetalles(arrayOfPedidodetalles);
        rvPedidodetalles.setAdapter(rvAdapterPedidodetalle);


        return vista;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
