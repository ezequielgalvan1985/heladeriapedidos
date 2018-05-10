package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
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

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Model.Pote;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterPote;

public class ListadoPotesFragment extends Fragment {

    private RecyclerView rvPotes;
    private RVAdapterPote rvAdapterPote;
    private long androidId;
    public ListadoPotesFragment() {


    }

    public static ListadoPotesFragment newInstance(String param1, String param2) {
        ListadoPotesFragment fragment = new ListadoPotesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            androidId = getArguments().getLong(Constants.PARAM_ANDROID_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_listado_potes, container, false);

        PedidoController pc = new PedidoController(v.getContext());

        ArrayList<Pote> listaPotes = pc.abrir().getPotesArrayList2(androidId);

        rvPotes = (RecyclerView)v.findViewById(R.id.rvPotes);
        rvPotes.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        rvPotes.setLayoutManager(llm);

        rvAdapterPote = new RVAdapterPote();
        rvAdapterPote.setCtx(getContext());
        rvAdapterPote.setFragmentManager(getFragmentManager());
        rvAdapterPote.setPotes(listaPotes);
        rvPotes.setAdapter(rvAdapterPote);

        return v;
    }



}
