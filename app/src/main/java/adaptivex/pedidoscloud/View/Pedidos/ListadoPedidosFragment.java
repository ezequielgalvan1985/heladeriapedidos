package adaptivex.pedidoscloud.View.Pedidos;

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

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ClienteController;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Model.Cliente;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterPedido;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListadoPedidosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListadoPedidosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListadoPedidosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Variables
    private RecyclerView rvPedidos;
    private RecyclerView.Adapter mAdapter;


    public ListadoPedidosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListadoPedidosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListadoPedidosFragment newInstance(String param1, String param2) {
        ListadoPedidosFragment fragment = new ListadoPedidosFragment();
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
            View vista = inflater.inflate(R.layout.fragment_listado_pedidos, container, false);

            //DATOS
            PedidoController dbHelper = new PedidoController(vista.getContext());
            ArrayList<Pedido> arrayOfPedidos = new ArrayList<Pedido>();
            Cursor c;
            //DETERINAR QUE LISTADO SE VA A MOSTRAR
            if(GlobalValues.getINSTANCIA().getESTADO_ID_SELECCIONADO() == GlobalValues.getINSTANCIA().consPedidoEstadoEnviado) {
                c = dbHelper.abrir().findByEstadoId(GlobalValues.getINSTANCIA().consPedidoEstadoEnviado);
            }else if(GlobalValues.getINSTANCIA().getESTADO_ID_SELECCIONADO() == GlobalValues.getINSTANCIA().consPedidoEstadoNuevo){
                c = dbHelper.abrir().findByEstadoId(GlobalValues.getINSTANCIA().consPedidoEstadoNuevo);
            }else{
                c= dbHelper.abrir().obtenerTodos();
            }
            GlobalValues.getINSTANCIA().setESTADO_ID_SELECCIONADO(GlobalValues.getINSTANCIA().consPedidoEstadoTodos);

            String datos = "";
            Pedido registro;

            //Cliente
            Cliente cliente;
            ClienteController dbCliente = new ClienteController(vista.getContext());
            if (c!= null){
                if(c.getCount() > 0 ){
                    for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                        registro = new Pedido();
                        registro.setId(c.getInt(c.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID)));
                        registro.setCreated(c.getString(c.getColumnIndex(PedidoDataBaseHelper.CAMPO_CREATED)));
                        registro.setSubtotal(c.getDouble(c.getColumnIndex(PedidoDataBaseHelper.CAMPO_SUBTOTAL)));
                        registro.setIva(c.getDouble(c.getColumnIndex(PedidoDataBaseHelper.CAMPO_IVA)));
                        registro.setMonto(c.getDouble(c.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO)));
                        registro.setCliente_id(c.getInt(c.getColumnIndex(PedidoDataBaseHelper.CAMPO_CLIENTE_ID)));
                        registro.setEstadoId(c.getInt(c.getColumnIndex(PedidoDataBaseHelper.CAMPO_ESTADO_ID)));
                        cliente = dbCliente.abrir().buscar(registro.getCliente_id());
                        registro.setCliente(cliente);
                        registro.setBonificacion(c.getDouble(c.getColumnIndex(PedidoDataBaseHelper.CAMPO_BONIFICACION)));
                        registro.setIdTmp(c.getInt(c.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID_TMP)));
                        arrayOfPedidos.add(registro);
                        registro = null;
                    }
                }
            }
            //Con RECYCLEVIEW
            rvPedidos = (RecyclerView)vista.findViewById(R.id.rvPedidos);
            LinearLayoutManager llm = new LinearLayoutManager(vista.getContext());
            rvPedidos.setLayoutManager(llm);
            RVAdapterPedido rvAdapterPedido = new RVAdapterPedido();
            rvAdapterPedido.setCtx(getContext());
            rvAdapterPedido.setPedidos(arrayOfPedidos);
            rvPedidos.setAdapter(rvAdapterPedido);


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
