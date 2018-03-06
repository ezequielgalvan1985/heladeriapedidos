package adaptivex.pedidoscloud;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Core.IniciarApp;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.UserParser;
import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.Servicios.WebRequest;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>
{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserRegisterTask userRegisterTask = null;
    private UserLoginTask userLoginTask = null;

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

    private CardView cvLogin;
    private CardView cvRegister;

    private LinearLayout form_register;
    private LinearLayout form_login;
    private Button login_btn_login;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        try{


            IniciarApp ia = new IniciarApp(this.getBaseContext());
            if (!ia.isInstalled()){
                ia.iniciarBD();
            }


            if(ia.isLoginRememberr()){
                Intent i = new Intent(this.getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            }

            //Iniciar los campos
            mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
            mPasswordView = (EditText) findViewById(R.id.password);
            txtTelefono  = (EditText) findViewById(R.id.register_telefono);
            txtLocalidad  = (EditText) findViewById(R.id.register_localidad);
            txtCalle      = (EditText) findViewById(R.id.register_calle);
            txtPiso       = (EditText) findViewById(R.id.register_piso);
            txtNro        = (EditText) findViewById(R.id.register_nro);
            txtContacto   = (EditText) findViewById(R.id.register_contacto);


            register_link_login = (Button) findViewById(R.id.register_link_login);
            login_link_register = (Button) findViewById(R.id.login_link_register);

            form_register = (LinearLayout) findViewById(R.id.form_register);
            form_login = (LinearLayout) findViewById(R.id.form_login);

            register_link_login.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    form_register.setVisibility(View.INVISIBLE);
                    form_login.setVisibility(View.VISIBLE);

                }
            });


            login_link_register.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    form_register.setVisibility(View.VISIBLE);
                    form_login.setVisibility(View.INVISIBLE);
                }
            });


            populateAutoComplete();

            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        validateRegister();
                        return true;
                    }
                    return false;
                }
            });


            //Boton Crear cuenta
            Button register_btn_register = (Button) findViewById(R.id.register_btn_register);
            register_btn_register.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    validateRegister();
                }
            });


            //Boton Iniciar sesion
            login_btn_login = (Button) findViewById(R.id.login_btn_login);
            login_btn_login.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateLogin();
                }
            });





        }catch(Exception e){
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }



    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    //Valida campos en el Login
    private void validateLogin(){
        if (userRegisterTask != null) {
            return;
        }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email      = mEmailView.getText().toString();
        String password   = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
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




        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            userLoginTask = new UserLoginTask(email, password);
            userLoginTask.setCtx(this.getBaseContext());
            userLoginTask.execute((Void) null);
        }
    }



    private void validateRegister() {
        if (userRegisterTask != null) {
            return;
        }

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
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
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
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            userRegisterTask = new UserRegisterTask(email, password, telefono, localidad, calle, nro, piso, contacto);
            userRegisterTask.setCtx(this.getBaseContext());
            userRegisterTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }




    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mTelefono;
        private final String mLocalidad;
        private final String mCalle;
        private final String mNro;
        private final String mPiso;
        private final String mContacto;

        private Context ctx;
        private User user;
        private HashMap<String,String> registro;
        private UserParser parser;

        UserRegisterTask(String email, String password, String telefono, String localidad, String calle, String nro, String piso, String contacto) {
            mEmail = email;
            mPassword = password;
            mTelefono = telefono;
            mLocalidad = localidad;
            mCalle = calle;
            mNro = nro;
            mPiso = piso;
            mContacto = contacto;

            user = new User();

            user.setEmail(this.mEmail);
            user.setPassword(this.mPassword);
            user.setTelefono(this.mTelefono);
            user.setLocalidad(this.mLocalidad);
            user.setCalle(this.mCalle);
            user.setNro(this.mNro);
            user.setPiso(this.mPiso);
            user.setContacto(this.mContacto);

            parser = new UserParser();
        }





        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                //Pasar los datos al JSON,
                registro = new HashMap<String, String>();
                registro.put("username", mEmail);
                registro.put("password", mPassword);
                registro.put("telefono", mTelefono);
                registro.put("localidad", mLocalidad);
                registro.put("calle", mCalle);
                registro.put("nro", mNro);
                registro.put("piso", mPiso);
                registro.put("contacto", mContacto);

                WebRequest webreq = new WebRequest();
                String jsonStr = webreq.makeWebServiceCall(Configurador.urlPostRegister, WebRequest.POST, registro);
                parser.setStrJson(jsonStr);
                parser.parsearRespuesta();
                if (jsonStr.equals("")){

                    return false;
                }
            } catch (Exception e) {
                Log.d("LoginActivity1:", e.getMessage());
                return false;
            }
            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            try{
                userRegisterTask = null;
                showProgress(false);

                //LOGIN EXITOSO
                if ( Integer.parseInt(parser.getStatus())==200 ){

                    GlobalValues.getINSTANCIA().setUserlogued(parser.getUser());
                    //SE INSTALA LA APP, EN CASO DE NO ESTARLO
                    IniciarApp ia = new IniciarApp(this.getCtx());
                    if (!ia.isInstalled()){
                        ia.iniciarBD();
                    }
                    //SE DESCARGAN LOS DATOS
                    if (!ia.isDatabaseDownload()){
                        ia.downloadDatabase();
                    }

                    ia.loginRememberr(parser.getUser());

                    Intent i = new Intent(this.getCtx(), MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                    Log.d("LoginActivity3:", parser.getStatus());
                }
            }catch(Exception e ){
                Log.d("LoginActivity4:", e.getMessage());
            }
        }

        @Override
        protected void onCancelled() {
            userRegisterTask = null;
            showProgress(false);
        }

        public Context getCtx() {
            return ctx;
        }

        public void setCtx(Context ctx) {
            this.ctx = ctx;
        }
    }




    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        private Context ctx;
        private User user;
        private HashMap<String,String> registro;
        private UserParser parser;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;

            user = new User();

            user.setEmail(this.mEmail);
            user.setPassword(this.mPassword);

            parser = new UserParser();
        }



        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                //Pasar los datos al JSON,
                registro = new HashMap<String, String>();
                registro.put("username", mEmail);
                registro.put("password", mPassword);

                WebRequest webreq = new WebRequest();
                String jsonStr = webreq.makeWebServiceCall(Configurador.urlPostLogin, WebRequest.POST, registro);
                parser.setStrJson(jsonStr);
                parser.parsearRespuesta();
                if (jsonStr.equals("")){
                    return false;
                }
            } catch (Exception e) {
                Log.d("LoginActivity1:", e.getMessage());
                return false;
            }
            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            try{
                userRegisterTask = null;
                showProgress(false);

                //LOGIN EXITOSO
                if ( Integer.parseInt(parser.getStatus())==200 ){

                    GlobalValues.getINSTANCIA().setUserlogued(parser.getUser());
                    //SE INSTALA LA APP, EN CASO DE NO ESTARLO
                    IniciarApp ia = new IniciarApp(this.getCtx());
                    if (!ia.isInstalled()){
                        ia.iniciarBD();
                    }
                    //SE DESCARGAN LOS DATOS
                    if (!ia.isDatabaseDownload()){
                        ia.downloadDatabase();
                    }

                    ia.loginRememberr(parser.getUser());

                    Intent i = new Intent(this.getCtx(), MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                    Log.d("LoginActivity3:", parser.getStatus());
                }
            }catch(Exception e ){
                Log.d("LoginActivity4:", e.getMessage());
            }
        }

        @Override
        protected void onCancelled() {
            userRegisterTask = null;
            showProgress(false);
        }

        public Context getCtx() {
            return ctx;
        }

        public void setCtx(Context ctx) {
            this.ctx = ctx;
        }
    }
}

