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

public class HelperStockPrecios extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private HashMap<String,String> registro;
    private Producto producto;
    private ProductoController productoCtr;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Producto

    public HelperStockPrecios(Context pCtx){
        this.setCtx(pCtx);
        this.productoCtr = new ProductoController(this.getCtx());
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            WebRequest webreq = new WebRequest();
            registro = new HashMap<String, String>();
            registro.put("empresa_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id()));
            String jsonStr = webreq.makeWebServiceCall(Configurador.urlProductos, WebRequest.POST,registro);
            ProductoParser cp = new ProductoParser(jsonStr);
            cp.parseJsonToObject();
            productoCtr.abrir().limpiar();
            //Recorrer Lista
            for (int i = 0; i < cp.getListadoProductos().size(); i++) {
                //Validar, si existe producto, por codigo externo

                productoCtr.abrir().agregar(cp.getListadoProductos().get(i));
            }
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);

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
        Toast.makeText(getCtx(), "Comienzo Actualizacion de Productos ", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (getRespuesta()== GlobalValues.getINSTANCIA().RETURN_OK){
            Toast.makeText(getCtx(), "Actualizacion de Productos Finalizada ", Toast.LENGTH_SHORT).show();
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
