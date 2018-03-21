package adaptivex.pedidoscloud.View.Promos;

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

import adaptivex.pedidoscloud.Controller.PromoController;
import adaptivex.pedidoscloud.Model.Promo;
import adaptivex.pedidoscloud.R;

public class ListadoPromosFragment extends Fragment {

    private RecyclerView rvPromos;
    private ArrayList<Promo> listaPromos;

    private OnFragmentInteractionListener mListener;

    public ListadoPromosFragment() {
        // Required empty public constructor
    }


    public static ListadoPromosFragment newInstance(String param1, String param2) {
        ListadoPromosFragment fragment = new ListadoPromosFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaPromos = new ArrayList<Promo>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_listado_promos, container, false);

        //1 - RECYCLE VIEW
        rvPromos = (RecyclerView)v.findViewById(R.id.rvPromos);
        rvPromos.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        rvPromos.setLayoutManager(llm);

        //2 - ArrayList Promos
        PromoController pc = new PromoController(getContext());
        Cursor c = pc.abrir().findAll();
        listaPromos = pc.abrir().parseCursorToArrayList(c);

        //3 - SET ADAPTER
        RVAdapterPromo adapterPromo = new RVAdapterPromo();
        RVAdapterPromo.setCtx(getContext());
        RVAdapterPromo.setProductos(listaPromos);
        rvPromos.setAdapter(rvAdapterProducto);

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
