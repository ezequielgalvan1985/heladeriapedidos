package adaptivex.pedidoscloud.Servicios;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.HojarutaController;
import adaptivex.pedidoscloud.Controller.HojarutadetalleController;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.HojarutaParser;
import adaptivex.pedidoscloud.Model.Hojaruta;

import java.util.HashMap;

/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperHojarutas extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private HashMap<String,String> registro;
    private Hojaruta hojaruta;
    private HojarutaController hojarutaCtr;
    private HojarutadetalleController hojarutadetalleCtr;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Hojaruta
    private HojarutaParser cp;
    public HelperHojarutas(Context pCtx){
        this.setCtx(pCtx);
        this.hojarutaCtr = new HojarutaController(this.getCtx());
        this.hojarutadetalleCtr = new HojarutadetalleController(this.getCtx());
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            WebRequest webreq = new WebRequest();
            registro = new HashMap<String, String>();
            registro.put("empresa_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id()));
            String jsonStr = webreq.makeWebServiceCall(Configurador.urlHojarutas, WebRequest.POST,registro);
            cp = new HojarutaParser(jsonStr);

            //Parsea las hoja de rutas y sus detalles
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
        Toast.makeText(getCtx(), "Iniciando Descarga de Categorias...", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        //Limpiar Tablas Hoja de ruta y
        hojarutaCtr.abrir().limpiar();
        hojarutadetalleCtr.abrir().limpiar();

        //Recorrer Lista Hoja de ruta
        for (int i = 0; i < cp.getListadoHojarutas().size(); i++) {

            //Crea hoja de ruta
            hojarutaCtr.abrir().agregar(cp.getListadoHojarutas().get(i));

            // Recorrer detalles de la hoja de ruta
            Hojaruta hr = cp.getListadoHojarutas().get(i);

            for (int d = 0; d < hr.getItems().size(); d++) {
                //Agregar hoja ruta detalle
                hojarutadetalleCtr.abrir().agregar(hr.getItems().get(d));
            }

        }
        setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);

        if (getRespuesta()== GlobalValues.getINSTANCIA().RETURN_OK){

            Toast.makeText(getCtx(), "Guardado Correctamente ", Toast.LENGTH_SHORT).show();
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
