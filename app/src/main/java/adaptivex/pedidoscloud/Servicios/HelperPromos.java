package adaptivex.pedidoscloud.Servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PromoController;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.PromoParser;
import adaptivex.pedidoscloud.Model.Promo;

/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperPromos extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private HashMap<String,String> registro;
    private Promo promo;
    private PromoController promoCtr;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Promo
    private PromoParser cp;
    private ProgressDialog pDialog;
    private String jsonStr;
    
    public HelperPromos(Context pCtx){
        this.setCtx(pCtx);
        this.promoCtr = new PromoController(this.getCtx());
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            WebRequest webreq = new WebRequest();

            jsonStr = webreq.makeWebServiceCall(Configurador.urlPromos, WebRequest.POST,null);

        }catch (Exception e){
                setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
                Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());

        }
        return null;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        // Showing progress dialog


    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        try{

            cp = new PromoParser(jsonStr);
            cp.parseJsonToObject();

            promoCtr.abrir().limpiar();
            // Recorrer Lista
            for (int i = 0; i < cp.getListadoPromos().size(); i++) {
                Promo p = new Promo();
                p = cp.getListadoPromos().get(i);
                promoCtr.abrir().add(p);
            }
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);

            if (getRespuesta()== GlobalValues.getINSTANCIA().RETURN_ERROR){

            }

        }catch(Exception e){
            Toast.makeText(this.getCtx(), "Error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
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
}
