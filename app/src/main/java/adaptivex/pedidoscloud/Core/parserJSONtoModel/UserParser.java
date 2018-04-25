package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.Model.UserDataBaseHelper;

import org.json.JSONObject;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class UserParser {
    private String strJson;
    private JSONObject jsonobj;
    private JSONObject respuesta;
    private String status;
    private String message;
    private JSONObject data;
    private JSONObject userjson;
    private User user;

    public UserParser(){
        this.user = new User();
    }

    public User parsearRespuesta(String json){

        try{
            JSONObject object = new JSONObject(json);
            setStatus(object.getString("code"));
            setMessage( object.getString("message"));

            if (Integer.parseInt(getStatus())== 200){
                User vuser = new User();
                JSONObject data = new JSONObject(object.getString("data"));
                setData(data);

                vuser.setId(data.getInt(UserDataBaseHelper.ID));
                vuser.setUsername(data.getString(UserDataBaseHelper.USERNAME));
                vuser.setEmail(data.getString(UserDataBaseHelper.EMAIL));


                if (data.has(UserDataBaseHelper.TELEFONO)) vuser.setTelefono(data.getString(UserDataBaseHelper.TELEFONO)); else vuser.setTelefono("");
                if (data.has(UserDataBaseHelper.LOCALIDAD)) vuser.setLocalidad(data.getString(UserDataBaseHelper.LOCALIDAD)); else vuser.setLocalidad("");
                if (data.has(UserDataBaseHelper.CALLE)) vuser.setCalle(data.getString(UserDataBaseHelper.CALLE)); else vuser.setCalle("");
                if (data.has(UserDataBaseHelper.NRO)) vuser.setNro(data.getString(UserDataBaseHelper.NRO)); else vuser.setNro("");
                if (data.has(UserDataBaseHelper.PISO)) vuser.setPiso(data.getString(UserDataBaseHelper.PISO)); else vuser.setPiso("");
                if (data.has(UserDataBaseHelper.CONTACTO)) vuser.setContacto(data.getString(UserDataBaseHelper.CONTACTO)); else vuser.setContacto("");
                setUser(vuser);

            }else {
                Log.e("UserParser: ", "Status: " + getStatus().toString());
                Log.e("UserParser: ", "Status: " + getMessage().toString());

            }
        }catch(Exception e ){
            setStatus("500");
            setMessage(e.getMessage());
            Log.d("UserParserError: ", e.getMessage().toString());
        }

        return getUser();
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

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getStrJson() {
        return strJson;
    }

    public void setStrJson(String strJson) {
        this.strJson = strJson;
    }
}
