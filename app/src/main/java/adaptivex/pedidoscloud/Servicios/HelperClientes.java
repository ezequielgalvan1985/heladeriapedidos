package adaptivex.pedidoscloud.Servicios;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ClienteController;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.ClienteParser;
import adaptivex.pedidoscloud.Model.Cliente;

import java.util.HashMap;

/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperClientes extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private HashMap<String,String> registro;
    private Cliente cliente;
    private ClienteController clienteCtr;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Cliente
    private ClienteParser cp;

    public HelperClientes(Context pCtx){
        this.setCtx(pCtx);
        this.clienteCtr = new ClienteController(this.getCtx());
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            WebRequest webreq = new WebRequest();
            registro = new HashMap<String, String>();
            registro.put("empresa_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id()));
            String jsonStr = webreq.makeWebServiceCall(Configurador.urlClientes, WebRequest.POST,registro);
            cp = new ClienteParser(jsonStr);
            cp.parseJsonToObject();

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
        Toast.makeText(getCtx(), "Iniciando Descarga de Clientes...", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        clienteCtr.abrir().limpiar();
        //Recorrer Lista
        for (int i = 0; i < cp.getListadoClientes().size(); i++) {
            clienteCtr.abrir().agregar(cp.getListadoClientes().get(i));
        }
        setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);

        if (getRespuesta()== GlobalValues.getINSTANCIA().RETURN_OK){
            Toast.makeText(getCtx(), "Descarga de Clientes Completada ", Toast.LENGTH_SHORT).show();
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
