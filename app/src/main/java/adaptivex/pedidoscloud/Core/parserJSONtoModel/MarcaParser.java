package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Model.Marca;
import adaptivex.pedidoscloud.Model.MarcaDataBaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class MarcaParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONObject respuesta;
    private String status;
    private String message;
    private JSONArray data;
    private JSONObject marcajson;
    private Marca marca;
    private ArrayList<Marca> listadoMarcas;
    /* Solamente parsea los datos de un String Json, al Objeto MarcaParser */
    public MarcaParser(){
    }

    public MarcaParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public Marca parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoMarcas  = new ArrayList<Marca>();
            setJsonobj(new JSONObject(getJsonstr()));
            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));
            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray marcas = getData();
                Marca marca = new Marca();
                for (int i = 0; i < marcas.length(); i++) {
                    JSONObject registro = marcas.getJSONObject(i);

                    marca.setId(registro.getInt("id"));
                    marca.setNombre(registro.getString(MarcaDataBaseHelper.CAMPO_NOMBRE));
                    marca.setDescripcion(registro.getString(MarcaDataBaseHelper.CAMPO_DESCRIPCION));
                    listadoMarcas.add(marca);
                    marca = new Marca();
                }//endfor

            }else {
                Log.d("MarcaParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("MarcaParser: ", e.getMessage().toString());
        }

        return getMarca();
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

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
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

    public JSONObject getMarcajson() {
        return marcajson;
    }

    public void setMarcajson(JSONObject marcajson) {
        this.marcajson = marcajson;
    }

    public ArrayList<Marca> getListadoMarcas() {
        return listadoMarcas;
    }

    public void setListadoMarcas(ArrayList<Marca> listadoMarcas) {
        this.listadoMarcas = listadoMarcas;
    }
}
