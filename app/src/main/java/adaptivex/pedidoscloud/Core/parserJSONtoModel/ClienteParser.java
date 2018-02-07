package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Model.Cliente;
import adaptivex.pedidoscloud.Model.ClienteDataBaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class ClienteParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONObject respuesta;
    private String status;
    private String message;
    private JSONArray data;
    private JSONObject clientejson;
    private Cliente cliente;
    private ArrayList<Cliente> listadoClientes;
    /* Solamente parsea los datos de un String Json, al Objeto ClienteParser */
    public ClienteParser(){
    }

    public ClienteParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public Cliente parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoClientes  = new ArrayList<Cliente>();
            setJsonobj(new JSONObject(getJsonstr()));

            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));

            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray clientes = getData();
                Cliente cliente = new Cliente();
                for (int i = 0; i < clientes.length(); i++) {
                    JSONObject registro = clientes.getJSONObject(i);
                    cliente.setId(registro.getInt("id"));
                    if (registro.has(ClienteDataBaseHelper.CAMPO_RAZONSOCIAL)) cliente.setRazonsocial(registro.getString(ClienteDataBaseHelper.CAMPO_RAZONSOCIAL)); else cliente.setRazonsocial("");
                    if (registro.has(ClienteDataBaseHelper.CAMPO_CONTACTO)) cliente.setContacto(registro.getString(ClienteDataBaseHelper.CAMPO_CONTACTO)); else cliente.setContacto("");
                    if (registro.has(ClienteDataBaseHelper.CAMPO_NDOC)) cliente.setNdoc(registro.getString(ClienteDataBaseHelper.CAMPO_NDOC)); else cliente.setNdoc("");
                    if (registro.has(ClienteDataBaseHelper.CAMPO_DIRECCION)) cliente.setDireccion(registro.getString(ClienteDataBaseHelper.CAMPO_DIRECCION)); else cliente.setDireccion("");
                    if (registro.has(ClienteDataBaseHelper.CAMPO_TELEFONO)) cliente.setTelefono(registro.getString(ClienteDataBaseHelper.CAMPO_TELEFONO)); else cliente.setTelefono("");
                    if (registro.has(ClienteDataBaseHelper.CAMPO_EMAIL)) cliente.setEmail(registro.getString(ClienteDataBaseHelper.CAMPO_EMAIL)); else cliente.setEmail("");
                    listadoClientes.add(cliente);
                    cliente = new Cliente();
                }//endfor

            }else {
                Log.d("ClienteParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("ClienteParser: ", e.getMessage().toString());
        }

        return getCliente();
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public JSONObject getClientejson() {
        return clientejson;
    }

    public void setClientejson(JSONObject clientejson) {
        this.clientejson = clientejson;
    }

    public ArrayList<Cliente> getListadoClientes() {
        return listadoClientes;
    }

    public void setListadoClientes(ArrayList<Cliente> listadoClientes) {
        this.listadoClientes = listadoClientes;
    }
}
