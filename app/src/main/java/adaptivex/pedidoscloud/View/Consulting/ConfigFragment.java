package adaptivex.pedidoscloud.View.Consulting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ParameterController;
import adaptivex.pedidoscloud.Core.IniciarApp;
import adaptivex.pedidoscloud.Core.ParameterHelper;
import adaptivex.pedidoscloud.Model.Parameter;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.Servicios.HelperMemo;
import adaptivex.pedidoscloud.Servicios.IntentServiceEnvioPedidos;
import adaptivex.pedidoscloud.Servicios.IntentServiceStockPrecios;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfigFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    protected ToggleButton tbActStockPrecios;
    protected ToggleButton tbEnvioPedidos;
    protected Intent intentServiceStockPrecios;
    protected Intent intentServiceEnvioPedidos;


    public ConfigFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigFragment newInstance(String param1, String param2) {
        ConfigFragment fragment = new ConfigFragment();
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
        View vista = inflater.inflate(R.layout.fragment_config, container, false);

        Button btnDescargarBaseDatos = (Button) vista.findViewById(R.id.btnDescargarBaseDatos);
        Button btnRecordatorio = (Button) vista.findViewById(R.id.btnDescargarRecordatorio);
        tbActStockPrecios  = (ToggleButton) vista.findViewById(R.id.toggleButtonStockPrecios);

        intentServiceStockPrecios = new Intent(getContext(), IntentServiceStockPrecios.class);
        intentServiceEnvioPedidos = new Intent(getContext(), IntentServiceEnvioPedidos.class);
        //Boton simple
        btnRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    HelperMemo hm = new HelperMemo(getContext());
                    hm.execute();
                }catch (Exception e ){
                    Toast.makeText(getContext(), "Error " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });



        //Chequear Servicio Stock Precios
        ParameterHelper ph = new ParameterHelper(getContext());
        tbActStockPrecios.setChecked(ph.isStockPrecios());
        tbActStockPrecios.setOnClickListener( new ClickListenerToggleButton());

        btnDescargarBaseDatos.setOnClickListener(new ClickListenerDescargarBaseDatos());

        //Chequear Servicio Envio de Pedidos
        //tbEnvioPedidos.setChecked(ph.isEnvioPedidos());
        //tbEnvioPedidos.setOnClickListener(new ClickListenerToggleButton());

        return vista;
    }

    private class ClickListenerDescargarBaseDatos implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            IniciarApp ia = new IniciarApp(getContext());
            ia.downloadDatabase();
        }
    }

    private class ClickListenerToggleButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {

                ParameterController pc = new ParameterController(getContext());
                Parameter p = new Parameter();
                p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_SERVICE_STOCK_PRECIOS_ACTIVATE);

                Parameter pEnvioPedidos = new Parameter();
                pEnvioPedidos = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_SERVICE_ENVIO_PEDIDOS_ACTIVATE);

                switch (v.getId()) {
                    //StockPrecios
                    case R.id.toggleButtonStockPrecios:
                        if (tbActStockPrecios.isChecked()) {
                            if (p != null) {
                                p.setValor_texto("Y");
                                pc.abrir().modificar(p);
                                pc.cerrar();
                            }
                            getContext().startService(intentServiceStockPrecios);
                        } else {
                            getContext().stopService(intentServiceStockPrecios);
                            if (p != null) {
                                p.setValor_texto("N");
                                pc.abrir().modificar(p);
                                pc.cerrar();
                            }
                        }
                        break;



                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
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
