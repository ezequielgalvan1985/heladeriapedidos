package adaptivex.pedidoscloud.View.Users;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import adaptivex.pedidoscloud.Core.WorkString;
import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.Servicios.HelperUser;


public class RegisterFragment extends Fragment implements View.OnClickListener {


    private OnFragmentInteractionListener mListener;
    // UI references.

    private AutoCompleteTextView txtusername;
    private AutoCompleteTextView txtpassword;
    private AutoCompleteTextView txtEmail;
    private AutoCompleteTextView txtTelefono;
    private AutoCompleteTextView txtLocalidad;
    private AutoCompleteTextView txtCalle;
    private AutoCompleteTextView txtPiso;
    private AutoCompleteTextView txtNro;
    private AutoCompleteTextView txtContacto;
    private User user;
    private Button register_btn_register, register_btn_login;

    public RegisterFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        txtusername = (AutoCompleteTextView) v.findViewById(R.id.register_username);
        txtpassword = (AutoCompleteTextView) v.findViewById(R.id.register_password);

        txtEmail    = (AutoCompleteTextView) v.findViewById(R.id.register_email);


        register_btn_register = (Button) v.findViewById(R.id.register_btn_register);
        register_btn_login    = (Button) v.findViewById(R.id.register_btn_login);

        register_btn_login.setOnClickListener(this);
        register_btn_register.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn_login:
                clickLogin();
                break;
            case R.id.register_btn_register:
                clickRegister();
                break;

        }
    }

    private void clickLogin(){
        try{
           openLoginFragment();
        }catch (Exception e){
            Toast.makeText(getContext(), "Error: ", Toast.LENGTH_LONG).show();
        }
    }

    private void clickRegister(){
        try{
            if (validateForm()){
                HelperUser hu = new HelperUser(getContext());
                hu.setOpcion(HelperUser.OPTION_REGISTER);
                hu.setBEHAVIOR_POST_REGISTER(HelperUser.BEHAVIOR_POST_REGISTER_INICIAR_APP);
                hu.setUser(user);
                hu.execute();
            }
        }catch (Exception e){
            Toast.makeText(getContext(), "Error: ", Toast.LENGTH_LONG).show();
        }
    }


    private boolean validateForm(){
        try{
            String message = "";
            boolean validate = true;
            AutoCompleteTextView object;
            if (txtusername.getText().length() < 4){
                validate = false;
                message = "El usuario debe tener mas de 4 Caracters";
                //object = txtusername;
            }
            if (txtEmail.getText().length() < 1){
                validate = false;
                message = "El Email es Obligatorio";
                //object = txtEmail;
            }
            if (txtpassword.getText().length() < 4){
                validate = false;
                message = "La Contraseña debe tener Minimo 4 Caracteres";
                //object = txtpassword;
            }
            user = new User();
            user.setUsername(txtusername.getText().toString());
            user.setPassword(txtpassword.getText().toString());
            user.setEmail(txtEmail.getText().toString());
            /*
                if (txtLocalidad!=null) user.setLocalidad(txtLocalidad.getText().toString());
                if (txtCalle!=null)     user.setCalle(txtCalle.getText().toString());
                if (txtNro!=null)       user.setNro(txtNro.getText().toString());
                if (txtPiso!=null)      user.setPiso(txtPiso.getText().toString());
                if (txtTelefono!=null)  user.setTelefono(txtTelefono.getText().toString());
                if (txtContacto!=null)  user.setContacto(txtContacto.getText().toString());
            */
            if (!validate){
                Toast.makeText(getContext(), message , Toast.LENGTH_LONG).show();
            }
            return validate;

        }catch (Exception e){
            Toast.makeText(getContext(), "Error: " +e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void openLoginFragment(){
        LoginFragment fragment                  = new LoginFragment();
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main_register, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
