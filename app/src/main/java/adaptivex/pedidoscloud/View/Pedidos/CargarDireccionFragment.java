package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Controller.UserController;
import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.R;
import ivb.com.materialstepper.stepperFragment;

public class CargarDireccionFragment extends stepperFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private AutoCompleteTextView txtLocalidad;
    private AutoCompleteTextView txtCalle;
    private AutoCompleteTextView txtTelefono;

    @Override
    public boolean onNextButtonHandler() {
        return true;
    }

    public CargarDireccionFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cargar_direccion, container, false);

        //Cargar datos del usuario logueado

        UserController uc = new UserController(v.getContext());
        User u = uc.getUserDB();
        if (u==null){
            Toast.makeText(v.getContext(), "Error: No se pudo obtener los datos de usuario", Toast.LENGTH_SHORT).show();
        }

        //Asignar los valores a los campos
        txtTelefono = (AutoCompleteTextView) v.findViewById(R.id.direccion_telefono);
        txtLocalidad = (AutoCompleteTextView) v.findViewById(R.id.direccion_localidad);
        txtCalle = (AutoCompleteTextView) v.findViewById(R.id.direccion_calle);


        txtTelefono.setText(u.getTelefono());
        txtLocalidad.setText(u.getLocalidad());
        txtCalle.setText(u.getCalle());

        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
