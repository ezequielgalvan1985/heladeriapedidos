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

import java.util.ArrayList;

import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterHelado;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterProducto;

public class ListadoHeladosFragment extends Fragment {

    private RecyclerView rvHeladosPostres;
    private ArrayList<Object> listaHelados;

    private OnFragmentInteractionListener mListener;

    public ListadoHeladosFragment() {
        // Required empty public constructor
    }


    public static ListadoHeladosFragment newInstance(String param1, String param2) {
        ListadoHeladosFragment fragment = new ListadoHeladosFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaHelados = new ArrayList<Object>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_listado_promos, container, false);

        //1 - RECYCLE VIEW
        rvHeladosPostres = (RecyclerView)v.findViewById(R.id.rvHeladosPostres);
        rvHeladosPostres.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        rvHeladosPostres.setLayoutManager(llm);

        //2 - ArrayList Helados
        ProductoController pc = new ProductoController(getContext());
        listaHelados = pc.abrir().findAllToArrayList();

        //3 - SET ADAPTER
        RVAdapterProducto adapterProducto = new RVAdapterProducto();
        adapterProducto.setCtx(getContext());
        adapterProducto.setProductos(listaHelados);
        rvHeladosPostres.setAdapter(adapterProducto);

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
