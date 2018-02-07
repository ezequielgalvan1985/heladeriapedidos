package adaptivex.pedidoscloud.Servicios;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.MemoController;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.MemoParser;
import adaptivex.pedidoscloud.Model.Memo;

/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperMemo extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private HashMap<String,String> registro;
    private Memo memos;
    private MemoController memosCtr;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Memo
    private MemoParser cp;


    public HelperMemo(Context pCtx){
        this.setCtx(pCtx);
        this.memosCtr = new MemoController(this.getCtx());
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            WebRequest webreq = new WebRequest();
            registro = new HashMap<String, String>();
            registro.put("empresa_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id()));
            String jsonStr = webreq.makeWebServiceCall(Configurador.urlMemos, WebRequest.POST,registro);
            cp = new MemoParser(jsonStr);
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
        Toast.makeText(getCtx(), "Iniciando Descarga de Memo...", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        try{
            memosCtr.abrir().limpiar();
            for (int i = 0; i < cp.getListadoMemos().size(); i++) {
                memosCtr.abrir().agregar(cp.getListadoMemos().get(i));
            }
            memosCtr.cerrar();
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);
            Toast.makeText(getCtx(), "Descarga de Memos Completada ", Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            Toast.makeText(getCtx(), "Error: "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
