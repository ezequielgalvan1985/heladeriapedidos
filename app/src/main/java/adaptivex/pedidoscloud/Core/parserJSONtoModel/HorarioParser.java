package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Model.Horario;
import adaptivex.pedidoscloud.Model.HorarioDataBaseHelper;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class HorarioParser {
    private String      jsonstr;
    private JSONObject  jsonobj;
    private JSONArray   data;
    private String      status;
    private String      message;
    private JSONObject  horariojson;
    private Horario horario;
    private ArrayList<Horario> listadoHorarios;
    /* Solamente parsea los datos de un String Json, al Objeto HorarioParser */
    public HorarioParser(){
    }

    public HorarioParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public Horario parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoHorarios  = new ArrayList<Horario>();
            setJsonobj(new JSONObject(getJsonstr()));

            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));

            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray horarios = getData();
                Horario horario = new Horario();
                for (int i = 0; i < horarios.length(); i++) {
                    JSONObject c = horarios.getJSONObject(i);

                    horario.setId(c.getInt("id"));
                    horario.setDia(c.getInt(HorarioDataBaseHelper.CAMPO_DIA));
                    horario.setApertura(WorkDate.parseStringToDate(c.getString(HorarioDataBaseHelper.CAMPO_APERTURA)));
                    horario.setCierre(WorkDate.parseStringToDate(HorarioDataBaseHelper.CAMPO_CIERRE));
                    horario.setObservaciones(c.getString(HorarioDataBaseHelper.CAMPO_OBSERVACIONES));
                    listadoHorarios.add(horario);
                    horario =  new Horario();

                }//endfor

            }else {
                Log.d("HorarioParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("HorarioParser: ", e.getMessage().toString());
        }

        return getHorario();
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

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
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

    public JSONObject getHorariojson() {
        return horariojson;
    }

    public void setHorariojson(JSONObject horariojson) {
        this.horariojson = horariojson;
    }

    public ArrayList<Horario> getListadoHorarios() {
        return listadoHorarios;
    }

    public void setListadoHorarios(ArrayList<Horario> listadoHorarios) {
        this.listadoHorarios = listadoHorarios;
    }
}
