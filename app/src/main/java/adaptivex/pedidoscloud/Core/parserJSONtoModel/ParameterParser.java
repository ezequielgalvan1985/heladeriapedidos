package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Model.Parameter;
import adaptivex.pedidoscloud.Model.ParameterDataBaseHelper;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class ParameterParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONArray data;
    private String status;
    private String message;
    private JSONObject parameterjson;
    private Parameter parameter;
    private ArrayList<Parameter> listadoParameters;
    /* Solamente parsea los datos de un String Json, al Objeto ParameterParser */
    public ParameterParser(){
    }

    public ParameterParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public Parameter parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoParameters  = new ArrayList<Parameter>();
            setJsonobj(new JSONObject(getJsonstr()));

            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));

            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray parameters = getData();
                Parameter parameter = new Parameter();
                for (int i = 0; i < parameters.length(); i++) {
                    JSONObject c = parameters.getJSONObject(i);

                    if (c.has(ParameterDataBaseHelper.ID)){
                        parameter.setId(c.getInt(ParameterDataBaseHelper.ID));
                    }
                    if (c.has(ParameterDataBaseHelper.NOMBRE)){
                        parameter.setNombre(c.getString(ParameterDataBaseHelper.NOMBRE));
                    }
                    if (c.has(ParameterDataBaseHelper.DESCRIPCION)){
                        parameter.setDescripcion(c.getString(ParameterDataBaseHelper.DESCRIPCION));
                    }
                    if (c.has(ParameterDataBaseHelper.VALOR_TEXTO)){
                        parameter.setValor_texto(c.getString(ParameterDataBaseHelper.VALOR_TEXTO));
                    }
                    if (c.has(ParameterDataBaseHelper.VALOR_FECHA)){
                        parameter.setValor_fecha(WorkDate.parseStringToDate(c.getString(ParameterDataBaseHelper.VALOR_FECHA)));
                    }
                    if (c.has(ParameterDataBaseHelper.VALOR_DECIMAL)){
                        parameter.setValor_decimal(c.getDouble(ParameterDataBaseHelper.VALOR_DECIMAL));
                    }
                    if (c.has(ParameterDataBaseHelper.VALOR_INTEGER)){
                        parameter.setValor_integer(c.getInt(ParameterDataBaseHelper.VALOR_INTEGER));
                    }
                    listadoParameters.add(parameter);
                    parameter =  new Parameter();
                }//endfor

            }else {
                Log.d("ParameterParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("ParameterParser: ", e.getMessage().toString());
        }

        return getParameter();
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

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
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

    public JSONObject getParameterjson() {
        return parameterjson;
    }

    public void setParameterjson(JSONObject parameterjson) {
        this.parameterjson = parameterjson;
    }

    public ArrayList<Parameter> getListadoParameters() {
        return listadoParameters;
    }

    public void setListadoParameters(ArrayList<Parameter> listadoParameters) {
        this.listadoParameters = listadoParameters;
    }
}
