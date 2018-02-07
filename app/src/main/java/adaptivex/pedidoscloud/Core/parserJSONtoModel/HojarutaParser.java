package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Model.Hojaruta;
import adaptivex.pedidoscloud.Model.HojarutaDataBaseHelper;
import adaptivex.pedidoscloud.Model.Hojarutadetalle;
import adaptivex.pedidoscloud.Model.HojarutadetalleDataBaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class HojarutaParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONObject respuesta;
    private String status;
    private String message;
    private JSONArray data;
    private JSONObject hojarutajson;
    private Hojaruta hojaruta;
    private ArrayList<Hojaruta> listadoHojarutas;
    /* Solamente parsea los datos de un String Json, al Objeto HojarutaParser */
    public HojarutaParser(){
    }

    public HojarutaParser(String jsonstr){
        setJsonstr(jsonstr);
    }



    public Hojaruta parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoHojarutas  = new ArrayList<Hojaruta>();
            setJsonobj(new JSONObject(getJsonstr()));
            setStatus(getJsonobj().getString("code"));
            setMessage( getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));

            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray hojarutas = getData();
                Hojaruta hojaruta = new Hojaruta();

                for (int i = 0; i < hojarutas.length(); i++) {
                    //Inicializar listado de detalle de hoja de ruta
                    ArrayList<Hojarutadetalle> listadohojarutadetalle = new ArrayList<Hojarutadetalle>();
                    JSONObject registro = hojarutas.getJSONObject(i);

                    //Hojaruta Header
                    hojaruta.setId(registro.getInt(HojarutaDataBaseHelper.ID));
                    hojaruta.setDia_id(registro.getInt(HojarutaDataBaseHelper.DIA_ID));
                    hojaruta.setTitulo(registro.getString(HojarutaDataBaseHelper.TITULO));
                    hojaruta.setNotas(registro.getString(HojarutaDataBaseHelper.NOTAS));

                    //empleado
                    JSONObject empleado = registro.getJSONObject(HojarutaDataBaseHelper.EMPLEADO_JSON);
                    hojaruta.setUser_id(empleado.getInt(HojarutaDataBaseHelper.EMPLEADO_ID_JSON));

                    //usuario asosciado
                    JSONObject user = empleado.getJSONObject(HojarutaDataBaseHelper.USER_JSON);
                    hojaruta.setUser_id(user.getInt(HojarutaDataBaseHelper.USER_ID_JSON));

                    //detalle de hoja de ruta
                    JSONArray itemshojaruta = new JSONArray();
                    itemshojaruta = registro.getJSONArray(HojarutaDataBaseHelper.DETALLES_JSON);
                    for (int d = 0; d < itemshojaruta.length(); d++) {
                        //Leer registro detalle
                        JSONObject json_hrdetalle = itemshojaruta.getJSONObject(d);
                        Hojarutadetalle hrdetalle = new Hojarutadetalle();


                        hrdetalle.setId(json_hrdetalle.getInt(HojarutadetalleDataBaseHelper.ID));
                        hrdetalle.setHojaruta_id(hojaruta.getId());
                        hrdetalle.setHora(json_hrdetalle.getString(HojarutadetalleDataBaseHelper.HORA));
                        hrdetalle.setNotas(json_hrdetalle.getString(HojarutadetalleDataBaseHelper.NOTAS));

                        //Leer Cliente Object
                        JSONObject cliente = json_hrdetalle.getJSONObject(HojarutadetalleDataBaseHelper.CLIENTE_JSON);
                        hrdetalle.setCliente_id(cliente.getInt(HojarutadetalleDataBaseHelper.CLIENTE_ID_JSON));

                        //Cargar item
                        listadohojarutadetalle.add(hrdetalle);

                    }
                    //Asignar items a hoja de ruta
                    hojaruta.setItems(listadohojarutadetalle);
                    listadoHojarutas.add(hojaruta);
                    hojaruta = new Hojaruta();
                }//endfor

            }else {
                Log.d("HojarutaParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("HojarutaParser: ", e.getMessage().toString());
        }

        return getHojaruta();
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

    public Hojaruta getHojaruta() {
        return hojaruta;
    }

    public void setHojaruta(Hojaruta hojaruta) {
        this.hojaruta = hojaruta;
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

    public JSONObject getHojarutajson() {
        return hojarutajson;
    }

    public void setHojarutajson(JSONObject hojarutajson) {
        this.hojarutajson = hojarutajson;
    }

    public ArrayList<Hojaruta> getListadoHojarutas() {
        return listadoHojarutas;
    }

    public void setListadoHojarutas(ArrayList<Hojaruta> listadoHojarutas) {
        this.listadoHojarutas = listadoHojarutas;
    }
}
