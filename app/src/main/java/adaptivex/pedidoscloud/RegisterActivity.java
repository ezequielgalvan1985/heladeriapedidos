package adaptivex.pedidoscloud;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Core.IniciarApp;

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
            Fragment fragment = new HomeLoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main_register, fragment).addToBackStack(Constants.FRAGMENT_HOME_LOGIN)
                    .commit();
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






    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

