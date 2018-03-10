package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adaptivex.pedidoscloud.R;

public class ListadoPotesFragment extends Fragment {



    public ListadoPotesFragment() {
        // Required empty public constructor
    }


    public static ListadoPotesFragment newInstance(String param1, String param2) {
        ListadoPotesFragment fragment = new ListadoPotesFragment();

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
        return inflater.inflate(R.layout.fragment_listado_potes, container, false);
    }

}
