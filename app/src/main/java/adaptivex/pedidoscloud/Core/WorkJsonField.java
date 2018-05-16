package adaptivex.pedidoscloud.Core;

import android.util.Log;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by egalvan on 16/5/2018.
 */

public class WorkJsonField {

    public static Integer getInt(JSONObject json, String field){
        Integer value = 0 ;
        try{
            if (json.has(field)){
                value = json.getInt(field);
            }
            return value;
        } catch (Exception e) {
            Log.d("PedidoParser: ", e.getMessage().toString());
            return value;
        }
    }
    public static String getString(JSONObject json, String field){
        String value = "" ;
        try{
            if (json.has(field)){
                value = json.getString(field);
            }
            return value;
        } catch (Exception e) {
            Log.d("PedidoParser: ", e.getMessage().toString());
            return value;
        }
    }

    public static Date getDate(JSONObject json, String field){
        Date value = null ;
        try{
            if (json.has(field)){
                value = WorkDate.parseStringToDate(json.getString(field));
            }
            return value;
        } catch (Exception e) {
            Log.d("PedidoParser: ", e.getMessage().toString());
            return value;
        }
    }

    public static Double getDouble(JSONObject json, String field){
        Double value = 0.0 ;
        try{
            if (json.has(field)){
                value = json.getDouble(field);
            }
            return value;
        } catch (Exception e) {
            Log.d("PedidoParser: ", e.getMessage().toString());
            return value;
        }
    }

    public static Boolean getBoolean(JSONObject json, String field){
        Boolean value =false ;
        try{
            if (json.has(field)){
                value = json.getBoolean(field);
            }
            return value;
        } catch (Exception e) {
            Log.d("PedidoParser: ", e.getMessage().toString());
            return value;
        }
    }

}
