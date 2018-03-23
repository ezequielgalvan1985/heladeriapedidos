package adaptivex.pedidoscloud.View.Resumenes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.Pedidos.CargarDireccionFragment;
import adaptivex.pedidoscloud.View.Productos.ListadoHeladosFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button btn_nuevo, btn_micuenta, btn_postres, btn_helados;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_home, container, false);

        btn_helados  = (Button) vista.findViewById(R.id.home_btn_helados);
        btn_micuenta = (Button) vista.findViewById(R.id.home_btn_mi_cuenta);
        btn_nuevo    = (Button) vista.findViewById(R.id.home_btn_nuevo_pedido);
        btn_postres  = (Button) vista.findViewById(R.id.home_btn_postres);

        btn_nuevo.setOnClickListener(this);
        btn_postres.setOnClickListener(this);
        btn_micuenta.setOnClickListener(this);
        btn_helados.setOnClickListener(this);



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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_btn_helados:
                Fragment fragment = new ListadoHeladosFragment();
                openFragment(fragment);

                break;
            case R.id.home_btn_postres:
                Fragment fragment1 = new ListadoHeladosFragment();
                openFragment(fragment1);
                break;

            case R.id.home_btn_mi_cuenta:
                Fragment fragment2 = new CargarDireccionFragment();
                Bundle args = new Bundle();
                args.putBoolean(Constants.PARAM_MODE_EDIT_USER, Constants.PARAM_MODE_EDIT_USER_ON);
                fragment2.setArguments(args);
                openFragment(fragment2);
                break;

            case R.id.home_btn_nuevo_pedido:
                GlobalValues.getINSTANCIA().crearNuevoPedido(getContext());
                Fragment fragment3 =  new CargarDireccionFragment();
                openFragment(fragment3);
                break;

        }
    }
    public void openFragment(Fragment fragment){
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(Constants.FRAGMENT_CARGAR_HOME);
        fragmentTransaction.commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
