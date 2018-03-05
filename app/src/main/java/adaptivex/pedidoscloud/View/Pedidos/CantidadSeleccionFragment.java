package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adaptivex.pedidoscloud.R;
import ivb.com.materialstepper.stepperFragment;

public class CantidadSeleccionFragment extends stepperFragment {



    public CantidadSeleccionFragment() {
        // Required empty public constructor
    }

    @Override
    public boolean onNextButtonHandler() {
        return true;
    }


    // TODO: Rename and change types and number of parameters
    public static CantidadSeleccionFragment newInstance(String param1, String param2) {
        CantidadSeleccionFragment fragment = new CantidadSeleccionFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cantidad_seleccion, container, false);
    }



}
