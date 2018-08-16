package adaptivex.pedidoscloud.Servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.ProductoParser;
import adaptivex.pedidoscloud.Model.Producto;

import java.util.HashMap;

/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperProductos extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private HashMap<String,String> registro;
    private Producto producto;
    private ProductoController productoCtr;
    private int respuesta; //1=ok, 200=error
    private ProductoParser cp;
    private String jsonStr;
    private int OPTION                      = 0 ;
    public static final int OPTION_ALL             = 1;
    public static final int OPTION_UPDATE_ENABLED  = 2;
    private ProgressDialog          pDialog;



    public HelperProductos(Context pCtx){
        this.setCtx(pCtx);
        this.productoCtr = new ProductoController(this.getCtx());
        this.OPTION = OPTION_ALL; // por default trae todos
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            WebRequest webreq = new WebRequest();
            switch (OPTION){
                case OPTION_ALL:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlProductos, WebRequest.POST,null);
                    break;

                case OPTION_UPDATE_ENABLED:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlProductos, WebRequest.POST,null);
                    break;
            }


        }catch (Exception e){
                setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
                Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(this.getCtx());
        String texto = "";
        switch (OPTION) {
            case OPTION_ALL:
                texto = "Actualizando Helados...";
                break;
            case OPTION_UPDATE_ENABLED:
                texto = "Comprobando Helados disponibles...";
                break;
        }
        pDialog.setMessage(texto);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        switch (OPTION){

            case OPTION_ALL:
                productoCtr.abrir().limpiar();
                cp = new ProductoParser(jsonStr);
                cp.parseJsonToObject();

                //Recorrer Lista
                for (int i = 0; i < cp.getListadoProductos().size(); i++) {
                    productoCtr.abrir().add(cp.getListadoProductos().get(i));
                    productoCtr.cerrar();
                }
                break;

            case OPTION_UPDATE_ENABLED:
                cp = new ProductoParser(jsonStr);
                cp.parseJsonToObject();
                for (int i = 0; i < cp.getListadoProductos().size(); i++) {
                    productoCtr.abrir().edit(cp.getListadoProductos().get(i));
                    productoCtr.cerrar();
                }

                break;



        }

        setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);
        if (pDialog.isShowing()) pDialog.dismiss();

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



    public int getOPTION() {
        return OPTION;
    }

    public void setOPTION(int OPTION) {
        this.OPTION = OPTION;
    }
}
