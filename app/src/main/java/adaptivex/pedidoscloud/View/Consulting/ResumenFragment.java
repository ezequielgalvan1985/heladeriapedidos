package adaptivex.pedidoscloud.View.Consulting;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ClienteController;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResumenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResumenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResumenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnEliminarPedidosEnviados_resumen;
    private OnFragmentInteractionListener mListener;

    public ResumenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResumenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResumenFragment newInstance(String param1, String param2) {
        ResumenFragment fragment = new ResumenFragment();
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
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_resumen, container, false);
        try{

                String pedidosPendientes="";
                String pedidosEnviados="";
                String usuarioLogueado="";
                PedidoController pedidoCtr = new PedidoController(this.getContext());
                ClienteController clienteCtr = new ClienteController(this.getContext());
                ProductoController productoCtr = new ProductoController(this.getContext());

                btnEliminarPedidosEnviados_resumen = (Button) vista.findViewById(R.id.btnEliminarPedidosEnviados_resumen);
                btnEliminarPedidosEnviados_resumen.setOnClickListener(new onClickButton());

                Cursor cursorPedidosPendientes = pedidoCtr.abrir().findByEstadoId(GlobalValues.getINSTANCIA().ESTADO_NUEVO);
                pedidosPendientes = String.valueOf(cursorPedidosPendientes.getCount());

                Cursor cursorPedidosEnviados = pedidoCtr.abrir().findByEstadoId(GlobalValues.getINSTANCIA().ESTADO_ENVIADO);
                pedidosEnviados = String.valueOf(cursorPedidosEnviados.getCount());

                //Setear variables
                TextView tvPedidosPendientes = (TextView) vista.findViewById(R.id.tvPedidosPendientes);
                tvPedidosPendientes.setText(pedidosPendientes);

                TextView tvPedidosEnviados = (TextView) vista.findViewById(R.id.tvPedidosEnviados);
                tvPedidosEnviados.setText(pedidosEnviados);

                TextView tvUserLogued = (TextView) vista.findViewById(R.id.tvUserLogued);
                usuarioLogueado = GlobalValues.getINSTANCIA().getUserlogued().getUserDescription();
                tvUserLogued.setText(usuarioLogueado);

                Integer cantidadclientes = clienteCtr.abrir().obtenerTodos().getCount();
                TextView tvCantidadClientes = (TextView) vista.findViewById(R.id.tvCantidadClientes);
                tvCantidadClientes.setText(String.valueOf(cantidadclientes));

                Integer cantidadproductos = productoCtr.abrir().obtenerTodos().getCount();
                TextView tvCantidadProductos = (TextView) vista.findViewById(R.id.tvCantidadProductos);
                tvCantidadProductos.setText(String.valueOf(cantidadproductos));

            return vista;
        }catch (Exception e){
            Toast.makeText(this.getContext(),"Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private class onClickButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.btnEliminarPedidosEnviados_resumen:
                        //Buscar Pedidos Enviados
                        PedidoController pc = new PedidoController(getContext());

                            ArrayList<Pedido> listadoPedidos = pc.abrir().findByEstadoId_ArrayList(GlobalValues.getINSTANCIA().consPedidoEstadoEnviado);
                            for (Pedido pedido : listadoPedidos)
                            {
                                pc.abrir().deleteByIdTmp(pedido.getIdTmp());
                                pc.cerrar();
                            }
                            Toast.makeText(getContext(), "Pedidos Enviados, Eliminados Correctamente!",Toast.LENGTH_LONG).show();
                        break;
                }
            }catch(Exception e ){
                Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
            }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
