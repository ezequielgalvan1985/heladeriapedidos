package adaptivex.pedidoscloud.View.Users;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.RegisterActivity;
import adaptivex.pedidoscloud.Servicios.HelperUser;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private EditText login_password, login_email;


    private Button btn_login, btn_register;
    private User usertmp;


    public LoginFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();

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
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //Iniciar los campos
        login_email    = (EditText) v.findViewById(R.id.login_email);
        login_password = (EditText) v.findViewById(R.id.login_password);
        btn_login      = (Button)   v.findViewById(R.id.login_btn_login);
        btn_register   = (Button)   v.findViewById(R.id.login_btn_register);

        //Asignar Funcionalidad
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

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



    private void clickLogin(){
        if (validateLogin()){
            HelperUser hu = new HelperUser(getContext());
            hu.setOpcion(HelperUser.OPTION_LOGIN);
            hu.setUser(usertmp);
            hu.setBEHAVIOR_POST_REGISTER(HelperUser.BEHAVIOR_POST_REGISTER_INICIAR_APP);
            hu.execute();
        }

    }
    private boolean validateLogin(){
        try {
            boolean validate = true;
            if(login_email.getText().length() < 0) {
                Toast.makeText(getContext(), "Usuario o Email es Obligatorio: ", Toast.LENGTH_LONG).show();
            }
            if(login_password.getText().length()<4){
                Toast.makeText(getContext(), "Contraseña debe tener 4 caracteres minimo: ", Toast.LENGTH_LONG).show();
            }
            usertmp = new User();
            usertmp.setUsername(login_email.getText().toString());
            usertmp.setPassword(login_password.getText().toString());

            return validate;
        }catch (Exception e){
            Toast.makeText(getContext(),"Error: " + e.getMessage() ,Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void clickRegister(){
        RegisterFragment fragment      = new RegisterFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_main_register, fragment).addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:
                clickLogin();
                break;
            case R.id.login_btn_register:
                clickRegister();
                break;

            default:
                break;
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





}
