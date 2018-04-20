package adaptivex.pedidoscloud.Servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.UserController;
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
    private Context ctx;
    private ProgressDialog pDialog;
    private HashMap<String,String> registro;
    private User user;
    private UserController userCtl;
    private UserParser parser;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Pedido

    public final static int OPTION_LOGIN    = 1;
    public final static int OPTION_REGISTER = 2;
    public final static int OPTION_UPDATE   = 3;
    public final static int RETURN_ERROR    = 500;
    public final static int RETURN_OK       = 200;


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
            String jsonStr = webreq.makeWebServiceCall(Configurador.urlPostLogin, WebRequest.POST, this.registro);
            UserParser up = new UserParser();
            up.setStrJson(jsonStr);
            up.parsearRespuesta();
            setParser(up);

            if (Integer.parseInt(getParser().getStatus()) != RETURN_OK ){
                mostrarMensaje(getParser().getMessage());
            }
        }catch (Exception e){
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());

        }

    }

    private void registerService(){
        try{
            WebRequest webreq = new WebRequest();

            // Setear datos de usuario loguin para hacer el post
            registro = new HashMap<String, String>();
            registro.put("email", getUser().getEmail().toString());
            registro.put("password", getUser().getPassword().toString());
            registro.put("localidad", getUser().getLocalidad().toString());
            registro.put("calle", getUser().getCalle().toString());
            registro.put("nro", getUser().getNro().toString());
            registro.put("piso", getUser().getPiso().toString());
            registro.put("contacto", getUser().getContacto().toString());
            registro.put("telefono", getUser().getTelefono().toString());

            //Enviar Post
            String jsonStr = webreq.makeWebServiceCall(Configurador.urlPostRegister, WebRequest.POST, this.registro);
            JSONObject jsonObj = new JSONObject(jsonStr);
            setParser(new UserParser());
            getParser().setJsonobj(jsonObj);
            getParser().parsearRespuesta();

            Log.println(Log.ERROR, "Helper:", " Guardado Correctamente");

        }catch (Exception e){
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            Toast.makeText(this.getCtx(),"Error: "+e.getMessage(),Toast.LENGTH_LONG);

        }


    }
    private void updateUserService()
    {
        try{
            WebRequest webreq = new WebRequest();

            // Setear datos de usuario loguin para hacer el post
            registro = new HashMap<String, String>();
            registro.put("id", getUser().getId().toString());
            registro.put("email", getUser().getEmail().toString());
            registro.put("password", getUser().getPassword().toString());
            registro.put("localidad", getUser().getLocalidad().toString());
            registro.put("calle", getUser().getCalle().toString());
            registro.put("nro", getUser().getNro().toString());
            registro.put("piso", getUser().getPiso().toString());
            registro.put("contacto", getUser().getContacto().toString());
            registro.put("telefono", getUser().getTelefono().toString());

            //Enviar Post
            String jsonStr = webreq.makeWebServiceCall(Configurador.urlPostUpdateUser, WebRequest.POST, this.registro);
            JSONObject jsonObj = new JSONObject(jsonStr);
            setParser(new UserParser());
            getParser().setJsonobj(jsonObj);
            getParser().parsearRespuesta();

            Log.println(Log.ERROR, "Helper:", " Guardado Correctamente");

        }catch (Exception e){
            setRespuesta(RETURN_ERROR);
            Toast.makeText(this.getCtx(),"Error: "+e.getMessage(),Toast.LENGTH_LONG);

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

    private void mostrarMensaje(String mensaje){
        if (pDialog.isShowing()) {
            pDialog.dismiss();
            Toast.makeText(this.getCtx(), mensaje, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        setRespuesta(Integer.parseInt(getParser().getStatus()));

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

        if (pDialog.isShowing()){
            pDialog.dismiss();
            if (getRespuesta()== GlobalValues.getINSTANCIA().RETURN_OK){
                Toast.makeText(this.getCtx(), "Enviado Correctamente ", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void onPostUserLogin(){
        try{
            if (getRespuesta()== RETURN_OK){
                GlobalValues.getINSTANCIA().setUserlogued(parser.getUser());

                IniciarApp ia = new IniciarApp(this.getCtx());
                if (ia.isInstalled()==false){
                    ia.iniciarBD();
                }
                //SE DESCARGAN LOS DATOS
                if (ia.isDatabaseDownload()==false){
                    ia.downloadDatabase();
                }
                ia.loginRememberr(parser.getUser());

                Intent i = new Intent(this.getCtx(), MainActivity.class);
                getCtx().startActivity(i);
            }
        }catch(Exception e){

        }


    }
    public void onPostUserRegister(){}
    public void onPostUserUpdate(){}


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
}
