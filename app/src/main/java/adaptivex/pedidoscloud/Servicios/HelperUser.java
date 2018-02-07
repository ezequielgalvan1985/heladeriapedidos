package adaptivex.pedidoscloud.Servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.UserController;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.UserParser;
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

    public HelperUser(){

    }
    public HelperUser(Context pCtx){
        this.setCtx(pCtx);
    }

    public void callService(){
        try{
            WebRequest webreq = new WebRequest();

            // Setear datos de usuario loguin para hacer el post
            registro = new HashMap<String, String>();
            registro.put("email", getUser().getEmail().toString());
            registro.put("password", getUser().getPassword().toString());

            //Enviar Post
            String jsonStr = webreq.makeWebServiceCall(Configurador.urlPostLogin, WebRequest.POST, this.registro);
            JSONObject jsonObj = new JSONObject(jsonStr);
            setParser(new UserParser());
            getParser().setJsonobj(jsonObj);
            getParser().parsearRespuesta();


            Log.println(Log.ERROR, "Helper:", " Guardado Correctamente");

        }catch (Exception e){

            setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            //Toast.makeText(this.getCtx(),"Error: "+e.getMessage(),Toast.LENGTH_LONG);

            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());

        }

    }
    @Override
    protected Void doInBackground(Void... voids) {
        callService();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(this.getCtx());
        pDialog.setMessage("Enviando Pedido...");
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Showing progress dialog
        if (pDialog.isShowing()){
            pDialog.dismiss();
            if (getRespuesta()== GlobalValues.getINSTANCIA().RETURN_OK){
                Toast.makeText(this.getCtx(), "Enviado Correctamente ", Toast.LENGTH_SHORT).show();
            }
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

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
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
}
