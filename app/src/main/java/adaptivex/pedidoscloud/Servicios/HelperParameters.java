package adaptivex.pedidoscloud.Servicios;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ParameterController;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.ParameterParser;
import adaptivex.pedidoscloud.Model.Parameter;

/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperParameters extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private HashMap<String,String> registro;
    private Parameter parameter;
    private ParameterController parameterCtr;
    private int respuesta; //1=ok, 200=error


    private String TEXT_RESPONSE;
    private int CURRENT_OPTION = 0; //1 enviar Post Parameter


    public static final int OPTION_ALL = 1;
    public static final int OPTION_ONLY_PRICE = 2;

    public HelperParameters(Context pCtx){
        this.setCtx(pCtx);
        this.parameterCtr = new ParameterController(this.getCtx());
    }

    private void findAll(){
        try{
            WebRequest webreq = new WebRequest();
            TEXT_RESPONSE = webreq.makeWebServiceCall(Configurador.urlParameters, WebRequest.POST, null);
        }catch (Exception e){
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
        }
    }

    private void findOnlyPrices(){
        try{
            WebRequest webreq = new WebRequest();
            TEXT_RESPONSE = webreq.makeWebServiceCall(Configurador.urlParameters, WebRequest.POST, null);
        }catch (Exception e){
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
        }
    }



    private boolean onPostFindAll(){
        try{
            ParameterParser cp = new ParameterParser(TEXT_RESPONSE);
            cp.parseJsonToObject();
            parameterCtr.abrir().limpiar();
            for (int i = 0; i < cp.getListadoParameters().size(); i++) {
                parameterCtr.abrir().agregar(cp.getListadoParameters().get(i));
            }
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);
            return true;
        }catch (Exception e){
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
            return false;
        }
    }







    @Override
    protected Void doInBackground(Void... voids) {
        switch (this.getCURRENT_OPTION()){
            case OPTION_ALL:
                findAll();
                break;
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
        switch (getCURRENT_OPTION()){
            case OPTION_ALL:
                onPostFindAll();
                break;
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


    public int getCURRENT_OPTION() {
        return CURRENT_OPTION;
    }

    public void setCURRENT_OPTION(int CURRENT_OPTION) {
        this.CURRENT_OPTION = CURRENT_OPTION;
    }
}
