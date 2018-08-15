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
    private Horario horario;
    private ArrayList<Horario> listadoHorarios;
    /* Solamente parsea los datos de un String Json, al Objeto HorarioParser */


    public HorarioParser(){
    }

    public Horario parseJsonToObject(String textJson){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoHorarios            = new ArrayList<Horario>();
            JSONObject  jsonobject     =  new JSONObject(textJson);


            //parser Usuario
            JSONArray horarios = jsonobject.getJSONArray("data");
            Horario horario = new Horario();
            for (int i = 0; i < horarios.length(); i++) {
                JSONObject c = horarios.getJSONObject(i);

                JSONObject item = c.getJSONObject(HorarioDataBaseHelper.CAMPO_ROOT);

                horario.setId(item.getInt(HorarioDataBaseHelper.CAMPO_ID));
                horario.setDia(item.getInt(HorarioDataBaseHelper.CAMPO_DIA));

                JSONObject apertura = item.getJSONObject(HorarioDataBaseHelper.CAMPO_APERTURA);
                horario.setApertura(WorkDate.parseStringToTime(apertura.getString(HorarioDataBaseHelper.CAMPO_DATE)));

                JSONObject cierre = item.getJSONObject(HorarioDataBaseHelper.CAMPO_CIERRE);
                horario.setCierre(WorkDate.parseStringToTime(cierre.getString(HorarioDataBaseHelper.CAMPO_DATE)));

                horario.setObservaciones(item.getString(HorarioDataBaseHelper.CAMPO_OBSERVACIONES));

                listadoHorarios.add(horario);
                horario =  new Horario();

            }//endfor
        }catch(Exception e ){
            Log.d("HorarioParser: ", e.getMessage().toString());
        }

        return getHorario();
    }




    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }





    public ArrayList<Horario> getListadoHorarios() {
        return listadoHorarios;
    }

    public void setListadoHorarios(ArrayList<Horario> listadoHorarios) {
        this.listadoHorarios = listadoHorarios;
    }
}
