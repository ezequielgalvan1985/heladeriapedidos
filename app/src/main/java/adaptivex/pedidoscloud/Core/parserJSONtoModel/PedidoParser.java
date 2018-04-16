package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.PedidoDataBaseHelper;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class PedidoParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONObject respuesta;
    private String status;
    private String message;
    private JSONObject data;
    private JSONObject pedidojson;
    private Pedido pedido;
    private ArrayList<Pedido> listadoPedidos;

    /* Solamente parsea los datos de un String Json, al Objeto PedidoParser */
    public PedidoParser() {
    }

    public PedidoParser(String jsonstr) {
        setJsonstr(jsonstr);
    }

    public Pedido parseJsonToObject() {
        /* Completa datos del objeto  */
        try {
            //leer raiz
            listadoPedidos = new ArrayList<Pedido>();

            setJsonobj(new JSONObject(getJsonstr()));
            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONObject("data"));

            if ((Integer.parseInt(getStatus()) == 200)|| (Integer.parseInt(getStatus()) == 300)) {
                //parser Usuario

                pedido = new Pedido();
                JSONObject pedidoJson = getData();
                //Pedido header

                pedido.setId(pedidoJson.getInt(PedidoDataBaseHelper.CAMPO_ID));
                pedido.setIdTmp(pedidoJson.getInt(PedidoDataBaseHelper.ANDROID_ID_JSON));
                pedido.setCreated(pedidoJson.getString(PedidoDataBaseHelper.FECHA_JSON));
                pedido.setEstadoId(pedidoJson.getInt(PedidoDataBaseHelper.CAMPO_ESTADO_ID));

                //Parsear

                if (pedidoJson.has(PedidoDataBaseHelper.CAMPO_TIEMPO_DEMORA)) pedido.setTiempoDemora(pedidoJson.getInt(PedidoDataBaseHelper.CAMPO_TIEMPO_DEMORA)); else pedido.setTiempoDemora(0);
                if (pedidoJson.has(PedidoDataBaseHelper.CAMPO_HORA_ENTREGA)) pedido.setHoraentrega(WorkDate.parseStringToDate(pedidoJson.getString(PedidoDataBaseHelper.CAMPO_HORA_ENTREGA))); else pedido.setHoraentrega(null);
                if (pedidoJson.has(PedidoDataBaseHelper.CAMPO_HORA_RECEPCION)) pedido.setHoraRecepcion(WorkDate.parseStringToDate(pedidoJson.getString(PedidoDataBaseHelper.CAMPO_HORA_RECEPCION))); else pedido.setHoraRecepcion(null);

                //pedido.setHoraentrega(WorkDate.parseStringToDate(pedidoJson.getString(PedidoDataBaseHelper.CAMPO_HORA_ENTREGA)));

                //Cliente - User
                //JSONObject user = pedidoJson.getJSONObject(PedidoDataBaseHelper.USER_JSON);
                //pedido.setCliente_id(user.getInt(PedidoDataBaseHelper.USER_ID_JSON));
            }

            return pedido;
        } catch (Exception e) {
            Log.d("PedidoParser: ", e.getMessage().toString());
            return null;
        }
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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

    public JSONObject getPedidojson() {
        return pedidojson;
    }

    public void setPedidojson(JSONObject pedidojson) {
        this.pedidojson = pedidojson;
    }

    public ArrayList<Pedido> getListadoPedidos() {
        return listadoPedidos;
    }

    public void setListadoPedidos(ArrayList<Pedido> listadoPedidos) {
        this.listadoPedidos = listadoPedidos;
    }
}
