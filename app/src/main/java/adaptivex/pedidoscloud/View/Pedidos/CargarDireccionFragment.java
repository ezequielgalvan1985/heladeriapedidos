package adaptivex.pedidoscloud.View.Pedidos;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Controller.UserController;
import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.R;

public class CargarDireccionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private AutoCompleteTextView txtLocalidad;
    private AutoCompleteTextView txtCalle;
    private AutoCompleteTextView txtTelefono;
    private AutoCompleteTextView txtNro;
    private AutoCompleteTextView txtPiso;
    private AutoCompleteTextView txtContacto;
    private Button               btnSiguiente;
    private Button               btnAnterior;




    private boolean validateForm(){
        boolean validate = true;
        String message = "";

        txtTelefono  = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_telefono);
        txtLocalidad = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_localidad);
        txtCalle     = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_calle);
        txtNro       = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_nro);
        txtPiso      = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_piso);
        txtContacto  = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_contacto);

        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setTelefono(txtTelefono.getText().toString());
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setLocalidad(txtLocalidad.getText().toString());
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setCalle(txtCalle.getText().toString());
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setNro(txtNro.getText().toString());
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setPiso(txtPiso.getText().toString());
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setContacto(txtContacto.getText().toString());

        if (txtTelefono.getText().equals(""))
        {
            validate  = false;
            message ="* Telefono es Obligatorio \n";
        }
        if (txtLocalidad.getText().equals(""))
        {
            message ="* Localidad es Obligatorio \n";
            validate = false;
        }
        if (txtCalle.getText().equals("")){
            validate     = false;
            message ="* Calle es Obligatorio \n";
        }
        if (txtNro.getText().equals("")){
            validate       = false;
            message ="* Nro es Obligatorio \n";
        }
        //if (txtPiso.getText().equals("")) validate      = false;
        if (txtContacto.getText().equals("")){
            validate  = false;
            message ="* Contacto es Obligatorio \n";
        }

        if (validate == false){
            Toast.makeText(getView().getContext(),message,Toast.LENGTH_LONG).show();
        }
        return validate;
    }


    public CargarDireccionFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cargar_direccion, container, false);
        GlobalValues.getINSTANCIA().CURRENT_FRAGMENT_NUEVO_PEDIDO = GlobalValues.getINSTANCIA().NP_CARGAR_DIRECCION;
        //Cargar datos del usuario logueado

        UserController uc = new UserController(v.getContext());
        User u = uc.getUserDB();
        if (u==null){
            Toast.makeText(v.getContext(), "Error: No se pudo obtener los datos de usuario", Toast.LENGTH_SHORT).show();
        }

        //Asignar los valores a los campos
        txtTelefono   = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_telefono);
        txtLocalidad  = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_localidad);
        txtCalle      = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_calle);
        txtNro        = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_nro);
        txtPiso       = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_piso);
        txtContacto   = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_contacto);

        txtTelefono.setText(u.getTelefono());
        txtLocalidad.setText(u.getLocalidad());
        txtCalle.setText(u.getCalle());
        txtNro.setText(u.getNro());
        txtPiso.setText(u.getPiso());
        txtContacto.setText(u.getContacto());

        btnSiguiente = (Button) v.findViewById(R.id.cargar_direccion_btn_siguiente);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Realizar validaciones
                if(validateForm()){
                    //LLAMAR AL SIGUIENTE FRAMENT
                    if (saveDireccion()){
                        openFragmentCargarCantidad();
                    }

                }
            }
        });


        return v;
    }

    public void openFragmentCargarCantidad(){
        //getFragmentManager().beginTransaction().remove(this).commit();
        CargarCantidadFragment fragment      = new CargarCantidadFragment();
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(Constants.FRAGMENT_CARGAR_DIRECCION);
        fragmentTransaction.commit();
    }

    public boolean saveDireccion(){
        boolean validate = false;
        PedidoController pc = new PedidoController(getContext());
        // pc.abrir().modificar(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL,true);
        validate = true;
        return validate;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
