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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import adaptivex.pedidoscloud.View.Pedidos.CargarHeladosFragment;
import adaptivex.pedidoscloud.View.Users.LoginFragment;
import adaptivex.pedidoscloud.View.Users.RegisterFragment;

import static android.Manifest.permission.READ_CONTACTS;

public class RegisterActivity extends AppCompatActivity implements OnClickListener {

    private Button register_btn_register,register_btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        try{

            register_btn_login = (Button) findViewById(R.id.register_btn_login);
            register_btn_login.setOnClickListener(this);

            register_btn_register = (Button) findViewById(R.id.register_btn_register);
            register_btn_register.setOnClickListener(this);
            preLoadActivity();
        }catch(Exception e){
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    public void preLoadActivity(){
        IniciarApp ia = new IniciarApp(this.getBaseContext());
        if (!ia.isInstalled()){
            ia.iniciarBD();
        }

        if(ia.isLoginRemember()) {
            Intent i = new Intent(this.getBaseContext(), MainActivity.class);
            startActivity(i);
            finish();
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

    public void clickLogin(){
        LoginFragment fragment          = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, fragment).addToBackStack(null)
                .commit();
    }

    public void clickRegister(){
        RegisterFragment fragment      = new RegisterFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, fragment).addToBackStack(null)
                .commit();
    }

}

