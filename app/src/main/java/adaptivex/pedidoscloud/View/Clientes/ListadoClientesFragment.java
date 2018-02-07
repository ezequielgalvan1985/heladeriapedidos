package adaptivex.pedidoscloud.View.Clientes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ClienteController;
import adaptivex.pedidoscloud.Controller.HojarutaController;
import adaptivex.pedidoscloud.Model.Cliente;
import adaptivex.pedidoscloud.Model.Hojaruta;
import adaptivex.pedidoscloud.Model.Hojarutadetalle;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterCliente;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListadoClientesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListadoClientesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListadoClientesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String paramMenuNuevoPedido = "nuevopedido";
    private static final String ARG_CURSORCLIENTES= "cursorclientes";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean vlMenuNuevoPedido;
    private ArrayList<Cliente> cursorClientes;

    private OnFragmentInteractionListener mListener;

    //Variables
    private RecyclerView rvClientes;
    private RecyclerView.Adapter mAdapter;


    public ListadoClientesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListadoClientesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListadoClientesFragment newInstance(String param1, String param2) {
        ListadoClientesFragment fragment = new ListadoClientesFragment();
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
            vlMenuNuevoPedido = getArguments().getBoolean(paramMenuNuevoPedido);
        }
        GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOCLIENTES);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Generar Vista
        View vista = inflater.inflate(R.layout.fragment_listado_clientes, container, false);
        try{
            //DATOS
            ClienteController dbHelper = new ClienteController(vista.getContext());
            String datos = "";
            Cliente registro;


            //VERIFICA SI VIENE POR HOJA DE RUTA
            if(GlobalValues.getINSTANCIA().getDiaSelecionado() > 0 ){
                HojarutaController hc = new HojarutaController(this.getContext());
                Hojaruta hojaruta = hc.abrir().findByDiaAndUser(GlobalValues.getINSTANCIA().getDiaSelecionado(), GlobalValues.getINSTANCIA().getUserlogued().getId());
                ClienteController cc= new ClienteController(this.getContext());
                ArrayList<Cliente> clientes = new ArrayList<Cliente>() ;
                ArrayList<Hojarutadetalle> items = hojaruta.getItems();
                for (Hojarutadetalle pd: items) {
                    clientes.add(cc.abrir().buscar(pd.getCliente_id()));
                }

                GlobalValues.getINSTANCIA().setDiaSelecionado(0);
                setCursorClientes(clientes);
            }

            //SI NO SE REALIZO EL FILTRO, se muestran todos
            if (getCursorClientes()== null){
                cursorClientes = dbHelper.parseCursorToArray(dbHelper.abrir().obtenerTodos());
            }

            //Con RECYCLEVIEW
            rvClientes = (RecyclerView)vista.findViewById(R.id.rvClientes);
            LinearLayoutManager llm = new LinearLayoutManager(vista.getContext());
            rvClientes.setLayoutManager(llm);
            RVAdapterCliente rvAdapterCliente = new RVAdapterCliente();
            rvAdapterCliente.setCtx(getContext());
            rvAdapterCliente.setClientes(cursorClientes);
            rvClientes.setAdapter(rvAdapterCliente);

        }catch(Exception e){
            Log.d("Error: ListadoClientes",e.getMessage());
        }
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

    public ArrayList<Cliente> getCursorClientes() {
        return cursorClientes;
    }

    public void setCursorClientes(ArrayList<Cliente> cursorClientes) {
        this.cursorClientes = cursorClientes;
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
