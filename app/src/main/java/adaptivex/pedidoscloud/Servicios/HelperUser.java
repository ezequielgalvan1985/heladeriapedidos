package adaptivex.pedidoscloud.Servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Core.IniciarApp;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.UserParser;
import adaptivex.pedidoscloud.MainActivity;
import adaptivex.pedidoscloud.Model.User;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperUser extends AsyncTask<Void, Void, Void> {
    private Context                  ctx;
    private ProgressDialog           pDialog;
    private HashMap<String,String>   registro;
    private User                     user;
    private UserParser               parser;
    private int                      respuesta; //1=ok, 200=error
    private int                      opcion; //1 enviar Post Pedido

    private String jsonStr       ="";
    private Fragment nextFragment;

    public final static int OPTION_LOGIN    = 1;
    public final static int OPTION_REGISTER = 2;
    public final static int OPTION_UPDATE   = 3;
    public final static int RETURN_ERROR    = 500;
    public final static int RETURN_OK       = 200;

    private int             BEHAVIOR_POST_REGISTER             = 0;
    public final static int BEHAVIOR_POST_REGISTER_INICIAR_APP = 1;

    private int RESPONSE_CODE       = 0;
    private String RESPONSE_MESSAGE = "";




    public HelperUser(){

    }
    public HelperUser(Context pCtx){
        this.setCtx(pCtx);
    }

    public void loginService(){
        try{
            WebRequest webreq = new WebRequest();
            // Setear datos de usuario loguin para hacer el post
            registro = new HashMap<String, String>();
            registro.put("username", getUser().getUsername().toString());
            registro.put("password", getUser().getPassword().toString());
            //Enviar Post
            jsonStr = webreq.makeWebServiceCall(Configurador.urlPostLogin, WebRequest.POST, this.registro);

        }catch (Exception e){
            Log.e("Error:",e.getMessage());
            setRespuesta(RETURN_ERROR);

        }
    }

    private void registerService(){
        try{
            WebRequest webreq = new WebRequest();

            // Setear datos de usuario loguin para hacer el post
            registro = new HashMap<String, String>();

            registro.put("username", getUser().getUsername().toString());
            registro.put("password", getUser().getPassword().toString());
            registro.put("email", getUser().getEmail().toString());

            registro.put("localidad", getUser().getLocalidad().toString());
            registro.put("calle", getUser().getCalle().toString());
            registro.put("nro", getUser().getNro().toString());
            registro.put("piso", getUser().getPiso().toString());
            registro.put("contacto", getUser().getContacto().toString());
            registro.put("telefono", getUser().getTelefono().toString());

            //Enviar Post
            jsonStr = webreq.makeWebServiceCall(Configurador.urlPostRegister, WebRequest.POST, this.registro);

        }catch (Exception e){
            setRespuesta(RETURN_ERROR);
            Log.e("Error:",e.getMessage());
        }
    }


    private void updateUserService()
    {
        try{
            WebRequest webreq = new WebRequest();

            // Setear datos de usuario loguin para hacer el post
            registro = new HashMap<String, String>();

            registro.put("id", getUser().getId().toString());
            registro.put("username", getUser().getUsername().toString());
            registro.put("email", getUser().getEmail().toString());

            registro.put("localidad", getUser().getLocalidad().toString());
            registro.put("calle", getUser().getCalle().toString());
            registro.put("nro", getUser().getNro().toString());
            registro.put("piso", getUser().getPiso().toString());
            registro.put("contacto", getUser().getContacto().toString());
            registro.put("telefono", getUser().getTelefono().toString());

            //Enviar Post
            jsonStr = webreq.makeWebServiceCall(Configurador.urlPostUpdateUser, WebRequest.POST, this.registro);


        }catch (Exception e){
            setRespuesta(RETURN_ERROR);
            Log.e("Error:",e.getMessage());


        }
    }

    @Override
    protected Void doInBackground(Void... voids) {


        switch (this.getOpcion()){
            case OPTION_LOGIN:
                loginService();
                break;
            case OPTION_REGISTER:
                registerService();
                break;
            case OPTION_UPDATE:
                updateUserService();
                break;
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(this.getCtx());
        pDialog.setMessage("Enviando Datos al Servidor, aguarde un momento...");
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        try{

            parser = new UserParser();
            parser.parsearRespuesta(jsonStr);
            RESPONSE_CODE = Integer.parseInt(parser.getStatus());
            RESPONSE_MESSAGE = parser.getMessage();

            if (RESPONSE_CODE == RETURN_OK){
                GlobalValues.getINSTANCIA().setUserlogued(parser.getUser());

                switch (this.getOpcion()){
                    case OPTION_LOGIN:
                        onPostUserLogin();
                        break;
                    case OPTION_REGISTER:
                        onPostUserRegister();
                        break;
                    case OPTION_UPDATE:
                        onPostUserUpdate();
                        break;
                }
            }
            if (pDialog.isShowing()) pDialog.dismiss();

            if (RESPONSE_CODE !=RETURN_OK ){
                Toast.makeText(this.getCtx(), "Error: " + RESPONSE_MESSAGE, Toast.LENGTH_SHORT).show();
            }



        }catch(Exception e){
            Log.e("Error:",e.getMessage());
            if (pDialog.isShowing()){
                pDialog.dismiss();
                if (getRespuesta()== GlobalValues.getINSTANCIA().RETURN_OK){

                }
            }
        }



    }



    public void onPostUserLogin(){
        try{
            if (RESPONSE_CODE ==RETURN_OK){
                IniciarApp ia = new IniciarApp(this.getCtx());
                ia.loginRemember(parser.getUser());

                Intent i = new Intent(this.getCtx(), MainActivity.class);
                getCtx().startActivity(i);
            }
        }catch(Exception e){
            Log.e("Error:",e.getMessage());
            Toast.makeText(this.getCtx(), " ", Toast.LENGTH_SHORT).show();
        }
    }

    public void onPostUserRegister(){
        try{
            if (RESPONSE_CODE ==RETURN_OK) {
                //Luego de regisrar
                if (getBEHAVIOR_POST_REGISTER() == BEHAVIOR_POST_REGISTER_INICIAR_APP) {
                    Intent i = new Intent(getCtx(), MainActivity.class);
                    getCtx().startActivity(i);

                }
            }
        }catch(Exception e){
            Log.e("Error:",e.getMessage());
            Toast.makeText(this.getCtx(), " ", Toast.LENGTH_SHORT).show();
        }
    }

    public void onPostUserUpdate(){
        try{
            if (RESPONSE_CODE ==RETURN_OK) {
                //Luego de regisrar
                Toast.makeText(this.getCtx(), "Usuario Actualizado en Servidor", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Log.e("Error:",e.getMessage());
            Toast.makeText(this.getCtx(), "ERROR " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public UserParser getParser() {
        return parser;
    }

    public void setParser(UserParser parser) {
        this.parser = parser;
    }


    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public Fragment getNextFragment() {
        return nextFragment;
    }

    public void setNextFragment(Fragment nextFragment) {
        this.nextFragment = nextFragment;
    }

    public int getBEHAVIOR_POST_REGISTER() {
        return BEHAVIOR_POST_REGISTER;
    }

    public void setBEHAVIOR_POST_REGISTER(int BEHAVIOR_POST_REGISTER) {
        this.BEHAVIOR_POST_REGISTER = BEHAVIOR_POST_REGISTER;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
