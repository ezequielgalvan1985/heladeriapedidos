package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Controller.PedidodetalleController;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.PedidodetalleDataBaseHelper;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.Servicios.HelperPedidos;
import adaptivex.pedidoscloud.View.EventListeners.ButtonOnClickListener;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterPedidodetalle;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetallePedidoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetallePedidoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallePedidoFragment extends Fragment implements  View.OnClickListener{

    //Variables
    public static final String paramPedidoIdTmp = "pedidoIdTmp";
    private long vlPedidoIdTmp;

    //Elementos
    private TextView tvDpfId,tvDpfIdTmp, tvDpfCreated, tvDpfSubtotal, tvDpfIva, tvDpfMonto,
            tvDpfEstadoId, tvDpfEstadoDesc, tvIpfIdTmp, tvDpfClienteDesc;
    private RecyclerView rvPedidodetalles;
    private Button btnEliminarPedido, btnEnviarPedido ;

    private OnFragmentInteractionListener mListener;

    public DetallePedidoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetallePedidoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetallePedidoFragment newInstance(String param1, String param2) {
        DetallePedidoFragment fragment = new DetallePedidoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vlPedidoIdTmp = 0;
        if (getArguments() != null) {
            vlPedidoIdTmp = getArguments().getLong(paramPedidoIdTmp);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_detalle_pedido, container, false);

        //Datos de pedido
        PedidoController dbPedido = new PedidoController(vista.getContext());
        Pedido pedido ;
        long idtemp = GlobalValues.getINSTANCIA().getVgPedidoIdActual() ;
        if (this.vlPedidoIdTmp != 0 ){
            idtemp = this.vlPedidoIdTmp;
        }
        dbPedido.abrir();
        pedido = dbPedido.buscar(idtemp, true);
        dbPedido.cerrar();
        tvDpfId = (TextView)vista.findViewById(R.id.tvDpfId);
        tvDpfIdTmp =  (TextView)vista.findViewById(R.id.tvDpfIdTmp);
        tvDpfCreated = (TextView)vista.findViewById(R.id.tvDpfCreated);
        tvDpfMonto = (TextView)vista.findViewById(R.id.tvDpfMonto);
        tvDpfEstadoDesc =  (TextView)vista.findViewById(R.id.tvDpfEstadoDesc);
        tvDpfClienteDesc =(TextView)vista.findViewById(R.id.tvDpfClienteDesc);

        ButtonOnClickListener btnClick = new ButtonOnClickListener(this.getContext());
        btnEliminarPedido =(Button)vista.findViewById(R.id.btnEliminarPedido);
        btnEliminarPedido.setOnClickListener(this);
        //btnEliminarPedido.setOnClickListener(btnClick);
        btnEnviarPedido =(Button)vista.findViewById(R.id.btnEnviarPedido2);
        btnEnviarPedido.setOnClickListener(this);

        if (pedido.getEstadoId()==GlobalValues.getINSTANCIA().ESTADO_ENVIADO){
            btnEnviarPedido.setEnabled(false);
        }

        //Cargar Valores a referencias
        tvDpfId.setText(String.valueOf(pedido.getId()));
        tvDpfIdTmp.setText(String.valueOf(pedido.getIdTmp()));
        tvDpfCreated.setText(String.valueOf(pedido.getCreatedDMY()));
        tvDpfMonto.setText(String.valueOf(pedido.getMonto()));
        tvDpfEstadoDesc.setText(String.valueOf(GlobalValues.getINSTANCIA().ESTADOS[pedido.getEstadoId()]));
        tvDpfClienteDesc.setText(pedido.getCliente().getContacto());




        //Completar RECICLEVIEW DE PEDIDOS con detalle
        //DATOS

        PedidodetalleController dbHelper = new PedidodetalleController(vista.getContext());
        //OBTENER TODOS LOS PRODUCTOS DE UN PEDIDO
        Cursor c = dbHelper.abrir().findByPedidoIdTmp(idtemp);
        //Cursor c = dbHelper.abrir().obtenerTodos();

        dbHelper.cerrar();
        ArrayList<Pedidodetalle> arrayOfPedidodetalles = new ArrayList<Pedidodetalle>();

        String datos = "";
        Producto p2;
        ProductoController dbProducto = new ProductoController(vista.getContext());
        Log.d("DeatllepedidoFrag","Antes del For " + c.getCount());

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            try{
                Pedidodetalle dp = new Pedidodetalle();
                int nid = c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ID_TMP));
                dp = dbHelper.abrir().findByIdTmp(nid);
                dbHelper.cerrar();
                Log.d("DeatllepedidoFrag","Detro del for " + dp.getId());

                arrayOfPedidodetalles.add(dp);
                Log.d("DeatllepedidoFrag",dp.getPedidoTmpId() + " - "+ dp.getProducto().getDescripcion());
                dp = null;

            }catch(Exception e){
                Toast.makeText(vista.getContext(),"DetallePedidoFragment: "+e.getMessage(), Toast.LENGTH_LONG);
                Log.d("DeatllepedidoFrag",e.getMessage());
            }
        }
        //Con RECYCLEVIEW

        rvPedidodetalles = (RecyclerView)vista.findViewById(R.id.rvPedidodetalles);
        rvPedidodetalles.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        LinearLayoutManager llm = new LinearLayoutManager(vista.getContext());
        rvPedidodetalles.setLayoutManager(llm);
        RVAdapterPedidodetalle rvAdapterPedidodetalle = new RVAdapterPedidodetalle();
        rvAdapterPedidodetalle.setCtx(getContext());
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



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnEliminarPedido) {
            try {
                PedidoController pc = new PedidoController(this.getContext());
                pc.abrir().deleteByIdTmp(Long.valueOf(this.tvDpfIdTmp.getText().toString()));
                pc.cerrar();
                GlobalValues.getINSTANCIA().setPEDIDO_ACTION_VALUE(GlobalValues.getINSTANCIA().PEDIDO_ACTION_DELETE);
                Toast.makeText(view.getContext(), "Eliminado Correctamente ", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(this.getContext(), "Error RVAdapterPedido: " + e.getMessage(), Toast.LENGTH_LONG);
            }

        }else if (view.getId() == R.id.btnEnviarPedido2) {
            try{
                HelperPedidos hp = new HelperPedidos(view.getContext(), Long.valueOf(this.tvDpfIdTmp.getText().toString()), GlobalValues.getINSTANCIA().ENVIAR_PEDIDO);
                hp.execute();
                sleep(1500);
                PedidoController pc = new PedidoController(this.getContext());
                Pedido p = pc.abrir().buscar(Long.valueOf(this.tvDpfIdTmp.getText().toString()),true);
                tvDpfEstadoDesc.setText(String.valueOf(GlobalValues.getINSTANCIA().ESTADOS[p.getEstadoId()]));
                tvDpfId.setText(String.valueOf(p.getId()));
                Button btnEnviar = (Button) view.findViewById(R.id.btnEnviarPedido2);
                if (p.getEstadoId()==GlobalValues.getINSTANCIA().ESTADO_ENVIADO){
                    btnEnviar.setEnabled(false);
                }



            }catch (Exception e){
                Toast.makeText(view.getContext(),"Error RVAdapterPedido: "+ e.getMessage(), Toast.LENGTH_LONG);
            }
        }

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
