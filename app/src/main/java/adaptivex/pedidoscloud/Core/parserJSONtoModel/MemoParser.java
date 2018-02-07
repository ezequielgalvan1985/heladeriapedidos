package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Model.Memo;
import adaptivex.pedidoscloud.Model.MemoDataBaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class MemoParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONObject respuesta;
    private String status;
    private String message;
    private JSONArray data;
    private JSONObject memojson;
    private Memo memo;
    private ArrayList<Memo> listadoMemos;
    /* Solamente parsea los datos de un String Json, al Objeto MemoParser */
    public MemoParser(){
    }

    public MemoParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public Memo parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoMemos  = new ArrayList<Memo>();
            setJsonobj(new JSONObject(getJsonstr()));
            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));
            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray memos = getData();
                Memo memo = new Memo();
                for (int i = 0; i < memos.length(); i++) {
                    JSONObject registro = memos.getJSONObject(i);
                    memo.setCliente_id(registro.getLong(MemoDataBaseHelper.JSON_CLIENTE));
                    memo.setProducto_id(registro.getLong(MemoDataBaseHelper.JSON_PRODUCTO));
                    memo.setCantidad(registro.getInt(MemoDataBaseHelper.CAMPO_CANTIDAD));
                    listadoMemos.add(memo);
                    memo = new Memo();
                }//endfor

            }else {
                Log.d("MemoParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("MemoParser: ", e.getMessage().toString());
        }

        return getMemo();
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

    public Memo getMemo() {
        return memo;
    }

    public void setMemo(Memo memo) {
        this.memo = memo;
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

    public JSONObject getMemojson() {
        return memojson;
    }

    public void setMemojson(JSONObject memojson) {
        this.memojson = memojson;
    }

    public ArrayList<Memo> getListadoMemos() {
        return listadoMemos;
    }

    public void setListadoMemos(ArrayList<Memo> listadoMemos) {
        this.listadoMemos = listadoMemos;
    }
}
