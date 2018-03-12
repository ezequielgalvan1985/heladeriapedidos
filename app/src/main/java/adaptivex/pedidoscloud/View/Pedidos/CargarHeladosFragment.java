package adaptivex.pedidoscloud.View.Pedidos;

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

import java.util.ArrayList;

import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterHelado;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CargarHeladosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CargarHeladosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CargarHeladosFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Cursor cursorHelados;
    //Variables
    private RecyclerView rvHelados;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Producto> arrayOfProductos = new ArrayList<Producto>();

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cargar_helados, container, false);
        ProductoController dbHelper = new ProductoController(v.getContext());

        rvHelados = (RecyclerView)v.findViewById(R.id.rvHelados);
        rvHelados.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        rvHelados.setLayoutManager(llm);

        RVAdapterHelado rvAdapterHelado = new RVAdapterHelado();
        rvAdapterHelado.setCtx(getContext());

        rvAdapterHelado.setProductos(dbHelper.findAll());

        return v;


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
