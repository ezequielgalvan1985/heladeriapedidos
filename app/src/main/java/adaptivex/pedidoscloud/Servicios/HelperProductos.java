package adaptivex.pedidoscloud.Servicios;

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
    private int opcion; //1 enviar Post Producto
    private ProductoParser cp;
    private String jsonStr;
    public HelperProductos(Context pCtx){
        this.setCtx(pCtx);
        this.productoCtr = new ProductoController(this.getCtx());
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            WebRequest webreq = new WebRequest();
            jsonStr = webreq.makeWebServiceCall(Configurador.urlProductos, WebRequest.POST,null);

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
        productoCtr.abrir().limpiar();
        cp = new ProductoParser(jsonStr);
        cp.parseJsonToObject();

        //Recorrer Lista
        for (int i = 0; i < cp.getListadoProductos().size(); i++) {
            productoCtr.abrir().add(cp.getListadoProductos().get(i));
            productoCtr.cerrar();
        }
        setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);

        if (getRespuesta()== GlobalValues.getINSTANCIA().RETURN_OK){

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
