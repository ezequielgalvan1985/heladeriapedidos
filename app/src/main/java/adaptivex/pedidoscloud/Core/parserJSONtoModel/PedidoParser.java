package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Core.WorkJsonField;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.PedidodetalleDataBaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
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
                if (pedidoJson.has(PedidoDataBaseHelper.CAMPO_TIEMPO_DEMORA))       pedido.setTiempoDemora(pedidoJson.getInt(PedidoDataBaseHelper.CAMPO_TIEMPO_DEMORA)); else pedido.setTiempoDemora(0);
                if (pedidoJson.has(PedidoDataBaseHelper.CAMPO_HORA_ENTREGA_JSON))   pedido.setHoraentrega(WorkDate.parseStringToDate(pedidoJson.getString(PedidoDataBaseHelper.CAMPO_HORA_ENTREGA_JSON))); else pedido.setHoraentrega(null);
                if (pedidoJson.has(PedidoDataBaseHelper.CAMPO_HORA_RECEPCION_JSON)) pedido.setHoraRecepcion(WorkDate.parseStringToDate(pedidoJson.getString(PedidoDataBaseHelper.CAMPO_HORA_RECEPCION_JSON))); else pedido.setHoraRecepcion(null);

            }

            return pedido;
        } catch (Exception e) {
            Log.d("PedidoParser: ", e.getMessage().toString());
            return null;
        }
    }

    // Devuelve una lista de pedidos, procesa los datos de getJsonstr
    public ArrayList<Pedido> parseResponseToArrayList() {
        /* Completa datos del objeto  */
        try {
            //leer raiz
            listadoPedidos = new ArrayList<Pedido>();

            setJsonobj(new JSONObject(getJsonstr()));
            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONObject("data"));

            if ((Integer.parseInt(getStatus()) == 200)|| (Integer.parseInt(getStatus()) == 300)) {

                JSONArray listaPedidos = getJsonobj().getJSONArray("data");

                for (int i = 0; i < listaPedidos.length(); i++) {
                    pedido = new Pedido();
                    JSONObject pedidoJson = (JSONObject) listaPedidos.get(i);
                    JSONArray pedidodetallesJson = pedidoJson.getJSONArray("pedidodetalles");
                    ArrayList <Pedidodetalle> detalles  = new ArrayList<Pedidodetalle> ();

                    //Pedido header
                    pedido.setId(WorkJsonField.getInt(pedidoJson,    PedidoDataBaseHelper.CAMPO_ID));
                    pedido.setIdTmp(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.ANDROID_ID_JSON));
                    pedido.setCreated(WorkJsonField.getString(pedidoJson, PedidoDataBaseHelper.FECHA_JSON));
                    pedido.setEstadoId(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.CAMPO_ESTADO_ID));
                    pedido.setCliente_id(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.CAMPO_CLIENTE_ID));

                    pedido.setTiempoDemora(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.CAMPO_TIEMPO_DEMORA));
                    pedido.setHoraentrega(WorkJsonField.getDate(pedidoJson, PedidoDataBaseHelper.CAMPO_HORA_ENTREGA_JSON));
                    pedido.setHoraRecepcion(WorkJsonField.getDate(pedidoJson, PedidoDataBaseHelper.CAMPO_HORA_RECEPCION_JSON));

                    pedido.setBonificacion(WorkJsonField.getDouble(pedidoJson, PedidoDataBaseHelper.CAMPO_BONIFICACION));
                    pedido.setSubtotal(WorkJsonField.getDouble(pedidoJson, PedidoDataBaseHelper.CAMPO_SUBTOTAL));
                    pedido.setIva(WorkJsonField.getDouble(pedidoJson, PedidoDataBaseHelper.CAMPO_IVA));
                    pedido.setMonto(WorkJsonField.getDouble(pedidoJson, PedidoDataBaseHelper.CAMPO_MONTO));
                    pedido.setMontoabona(WorkJsonField.getDouble(pedidoJson, PedidoDataBaseHelper.CAMPO_MONTO_ABONA));

                    pedido.setCantidadDescuento(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO));
                    pedido.setCantidadKilos(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS));
                    pedido.setCucuruchos(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.CAMPO_CUCURUCHOS));
                    pedido.setCucharitas(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.CAMPO_CUCHARITAS));

                    pedido.setMontoDescuento(WorkJsonField.getDouble(pedidoJson, PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO));
                    pedido.setMontoCucuruchos(WorkJsonField.getDouble(pedidoJson, PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS));
                    pedido.setMontoHelados(WorkJsonField.getDouble(pedidoJson, PedidoDataBaseHelper.CAMPO_MONTO_HELADOS));

                    pedido.setLocalidad(WorkJsonField.getString(pedidoJson, PedidoDataBaseHelper.CAMPO_LOCALIDAD));
                    pedido.setCalle(WorkJsonField.getString(pedidoJson, PedidoDataBaseHelper.CAMPO_CALLE));
                    pedido.setNro(WorkJsonField.getString(pedidoJson, PedidoDataBaseHelper.CAMPO_NRO));
                    pedido.setPiso(WorkJsonField.getString(pedidoJson, PedidoDataBaseHelper.CAMPO_PISO));
                    pedido.setTelefono(WorkJsonField.getString(pedidoJson, PedidoDataBaseHelper.CAMPO_TELEFONO));
                    pedido.setContacto(WorkJsonField.getString(pedidoJson, PedidoDataBaseHelper.CAMPO_CONTACTO));

                    pedido.setEnvioDomicilio(WorkJsonField.getBoolean(pedidoJson, PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO));

                    pedido.setCantPoteCuarto(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO));
                    pedido.setCantPoteMedio(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO));
                    pedido.setCantPoteTresCuarto(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO));
                    pedido.setCantPoteKilo(WorkJsonField.getInt(pedidoJson, PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO));

                    /*
                    * Leer detalle del pedido
                    * */
                    for (int d = 0; i < pedidodetallesJson.length(); d++) {
                        JSONObject pedidodetalleJson = (JSONObject) pedidodetallesJson.get(d);
                        Pedidodetalle pd = new Pedidodetalle();

                        pd.setIdTmp(WorkJsonField.getInt(pedidodetalleJson, PedidodetalleDataBaseHelper.CAMPO_ID_TMP));
                        pd.setId(WorkJsonField.getInt(pedidodetalleJson, PedidodetalleDataBaseHelper.CAMPO_ID));
                        pd.setMonto(WorkJsonField.getDouble(pedidodetalleJson, PedidodetalleDataBaseHelper.CAMPO_MONTO));
                        pd.setCantidad(WorkJsonField.getDouble(pedidodetalleJson, PedidodetalleDataBaseHelper.CAMPO_CANTIDAD));
                        pd.setMedidaPote(WorkJsonField.getInt(pedidodetalleJson, PedidodetalleDataBaseHelper.CAMPO_MONTO));
                        pd.setProporcionHelado(WorkJsonField.getInt(pedidodetalleJson, PedidodetalleDataBaseHelper.CAMPO_PROPORCION_HELADO));
                        pd.setNroPote(WorkJsonField.getInt(pedidodetalleJson, PedidodetalleDataBaseHelper.CAMPO_NRO_POTE));
                        pd.setPedidoId(WorkJsonField.getInt(pedidodetalleJson, PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID));
                        pd.setPedidoTmpId(WorkJsonField.getInt(pedidodetalleJson, PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP));

                        detalles.add(pd);
                    }
                    pedido.setDetalles(detalles);

                }



            }

            return listadoPedidos;
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
