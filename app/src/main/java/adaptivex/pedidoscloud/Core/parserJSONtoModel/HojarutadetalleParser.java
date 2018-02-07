package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Model.Hojarutadetalle;
import adaptivex.pedidoscloud.Model.HojarutadetalleDataBaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class HojarutadetalleParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONObject respuesta;
    private String status;
    private String message;
    private JSONArray data;
    private JSONObject hojarutadetallejson;
    private Hojarutadetalle hojarutadetalle;
    private ArrayList<Hojarutadetalle> listadoHojarutadetalles;
    /* Solamente parsea los datos de un String Json, al Objeto HojarutadetalleParser */
    public HojarutadetalleParser(){
    }

    public HojarutadetalleParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public Hojarutadetalle parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoHojarutadetalles  = new ArrayList<Hojarutadetalle>();
            setJsonobj(new JSONObject(getJsonstr()));
            respuesta = getJsonobj().getJSONObject("respuesta");
            setStatus(respuesta.getString("status"));
            setMessage( respuesta.getString("message"));
            setData(respuesta.getJSONArray("data"));
            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray hojarutadetalles = getData();
                Hojarutadetalle hojarutadetalle = new Hojarutadetalle();
                for (int i = 0; i < hojarutadetalles.length(); i++) {
                    JSONObject c = hojarutadetalles.getJSONObject(i);
                    JSONObject registro = c.getJSONObject("Hojarutadetalle");
                    hojarutadetalle.setId(registro.getInt("id"));
                    hojarutadetalle.setCliente_id(registro.getInt(HojarutadetalleDataBaseHelper.CLIENTE_ID));
                    hojarutadetalle.setNotas(registro.getString(HojarutadetalleDataBaseHelper.NOTAS));
                    hojarutadetalle.setHojaruta_id(registro.getInt(HojarutadetalleDataBaseHelper.HOJARUTA_ID));
                    hojarutadetalle.setHora(registro.getString(HojarutadetalleDataBaseHelper.HORA));

                    listadoHojarutadetalles.add(hojarutadetalle);
                    hojarutadetalle = new Hojarutadetalle();
                }//endfor

            }else {
                Log.d("HojarutadetalleParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("HojarutadetalleParser: ", e.getMessage().toString());
        }

        return getHojarutadetalle();
    }
    public JSONObject getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(JSONObject respuesta) {
        this.respuesta = respuesta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public Hojarutadetalle getHojarutadetalle() {
        return hojarutadetalle;
    }

    public void setHojarutadetalle(Hojarutadetalle hojarutadetalle) {
        this.hojarutadetalle = hojarutadetalle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getJsonobj() {
        return jsonobj;
    }

    public void setJsonobj(JSONObject jsonobj) {
        this.jsonobj = jsonobj;
    }

    public String getJsonstr() {
        return jsonstr;
    }

    public void setJsonstr(String jsonstr) {
        this.jsonstr = jsonstr;
    }

    public JSONObject getHojarutadetallejson() {
        return hojarutadetallejson;
    }

    public void setHojarutadetallejson(JSONObject hojarutadetallejson) {
        this.hojarutadetallejson = hojarutadetallejson;
    }

    public ArrayList<Hojarutadetalle> getListadoHojarutadetalles() {
        return listadoHojarutadetalles;
    }

    public void setListadoHojarutadetalles(ArrayList<Hojarutadetalle> listadoHojarutadetalles) {
        this.listadoHojarutadetalles = listadoHojarutadetalles;
    }
}
