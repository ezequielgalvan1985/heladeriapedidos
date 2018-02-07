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

    public User parsearRespuesta(){
        try{
            JSONObject object = new JSONObject(getStrJson());
            setStatus(object.getString("code"));
            setMessage( object.getString("message"));
            JSONObject data = new JSONObject(object.getString("data"));
            setData(data);

            if (Integer.parseInt(getStatus())== 200){
                User vuser = new User();
                vuser.setId(data.getInt(UserDataBaseHelper.ID));
                vuser.setUsername(data.getString(UserDataBaseHelper.USERNAME));
                vuser.setEmail(data.getString(UserDataBaseHelper.EMAIL));

                JSONObject empresa = new JSONObject(data.getString(UserDataBaseHelper.EMPRESA_JSON));
                vuser.setEntidad_id(empresa.getInt(UserDataBaseHelper.EMPRESA_ID_JSON));

                setUser(vuser);
            }else {
                Log.d("UserParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
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
