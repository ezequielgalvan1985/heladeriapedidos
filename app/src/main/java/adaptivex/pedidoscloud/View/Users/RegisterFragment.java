package adaptivex.pedidoscloud.View.Users;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import adaptivex.pedidoscloud.R;


public class RegisterFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText txtTelefono;
    private EditText txtLocalidad;
    private EditText txtCalle;
    private EditText txtPiso;
    private EditText txtNro;
    private EditText txtContacto;


    private EditText mConfirmPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private CheckBox chkRecordarme;
    private Button register_link_login;
    private Button login_link_register;

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
        return inflater.inflate(R.layout.fragment_register, container, false);
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

    private boolean validateRegister() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email      = mEmailView.getText().toString();
        String password   = mPasswordView.getText().toString();
        String telefono   = txtTelefono.getText().toString();
        String localidad  = txtLocalidad.getText().toString();
        String calle      = txtCalle.getText().toString();
        String nro        = txtNro.getText().toString();
        String piso       = txtPiso.getText().toString();
        String contacto   = txtContacto.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) ) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        // Telefono Obligatoria.
        if (TextUtils.isEmpty(telefono)) {
            txtTelefono.setError(getString(R.string.error_field_required));
            focusView = txtTelefono;
            cancel = true;
        }

        // Localidad Obligatoria.
        if (TextUtils.isEmpty(localidad)) {
            txtLocalidad.setError(getString(R.string.error_field_required));
            focusView = txtLocalidad;
            cancel = true;
        }

        // Calle Obligatoria.
        if (TextUtils.isEmpty(calle)) {
            txtCalle.setError(getString(R.string.error_field_required));
            focusView = txtCalle;
            cancel = true;
        }

        // Nro Obligatoria.
        if (TextUtils.isEmpty(nro)) {
            txtNro.setError(getString(R.string.error_field_required));
            focusView = txtNro;
            cancel = true;
        }

        // Contacto Obligatorio.
        if (TextUtils.isEmpty(contacto)) {
            txtContacto.setError(getString(R.string.error_field_required));
            focusView = txtContacto;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        }
        return cancel;
    }
}
