package adaptivex.pedidoscloud;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Core.IniciarApp;

import adaptivex.pedidoscloud.Servicios.HelperParameters;
import adaptivex.pedidoscloud.View.Users.HomeLoginFragment;
import adaptivex.pedidoscloud.View.Users.LoginFragment;
import adaptivex.pedidoscloud.View.Users.RegisterFragment;

import static android.Manifest.permission.READ_CONTACTS;

public class RegisterActivity
        extends AppCompatActivity
        implements
        HomeLoginFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        try{
            preLoadActivity();

        }catch(Exception e){
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    private void preLoadActivity(){
        //Si no esta instalada, se instala, y luego se pide registrarse
        IniciarApp ia = new IniciarApp(this);
        //HelperParameters hp = new HelperParameters(getBaseContext());
        //hp.setCURRENT_OPTION(HelperParameters.OPTION_ALL);
        //hp.execute();
        if (!ia.isInstalled()){
            ia.instalarApp();
            openRegisterFragment();
        }else {
            //Esta Instalada, pregunta Esta recordado el usuario, entonces se inicia la app directamente,
            if (ia.isLoginRemember()) {
                Intent i = new Intent(this.getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            } else {
                //si no se abre formulario de seleccionar opcion HOMELogin login o register
                openLoginFragment();
            }
        }
    }


    private void openLoginFragment(){
        try{
            Fragment fragment = new HomeLoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main_register, fragment).addToBackStack(Constants.FRAGMENT_HOME_LOGIN)
                    .commit();

        }catch(Exception e){
            Toast.makeText(getBaseContext(),"Error: ",Toast.LENGTH_LONG).show();
        }
    }

    private void openRegisterFragment(){
        try{
            Fragment fragment = new RegisterFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main_register, fragment).addToBackStack(Constants.FRAGMENT_HOME_LOGIN)
                    .commit();

        }catch(Exception e){
            Toast.makeText(getBaseContext(),"Error: ",Toast.LENGTH_LONG).show();
        }

    }






    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

