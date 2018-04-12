package adaptivex.pedidoscloud.Servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Controller.PedidodetalleController;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.PedidoParser;
import adaptivex.pedidoscloud.Model.PoteItem;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.Pedidodetalle;


/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperPedidos extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private ProgressDialog pDialog;
    private HashMap<String,String> registro;
    private Pedido pedido;
    private PedidoController pedidoCtr;
    private long nroPeddo;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Pedido, 2 ENVIAR TODOS LOS PEDIDOS PENDIENTES



    //Constructor donde le pasas el numero de pedido temporal y la opcion
    //ID pedido temporal, es el ID que tiene en la base de datos SQLITE
    //ID pedido real, es el ID que se asigna en MYSQL
    //OPCION: 1 enviar solo un pedido
    //OPCION: 2 enviar todos los pedidos pendientes
    public HelperPedidos(Context pCtx, long pNroPedidoTmp, int opcion){
        this.setCtx(pCtx);
        this.pedidoCtr = new PedidoController(this.getCtx());
        this.setPedido(pedidoCtr.abrir().buscar(pNroPedidoTmp, true));
        this.opcion = opcion;
    }

    public HelperPedidos(Context pCtx, int opcion){
        this.setCtx(pCtx);
        this.pedidoCtr = new PedidoController(this.getCtx());
        this.opcion = opcion;
    }

    public HelperPedidos(Context pCtx, int opcion, Pedido pedido){
        this.setCtx(pCtx);
        this.pedidoCtr = new PedidoController(this.getCtx());
        this.opcion = opcion;
        this.pedido = pedido;
    }



    public JSONObject parseObjectToJson(Pedido paramPedido){
        try{
            JSONObject pedido = new JSONObject();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechahoy = new Date();
            String fechahoystr = dateFormat.format(fechahoy);
            JSONArray pedidodetalles = new JSONArray();


            pedido.put("fecha", String.valueOf(fechahoystr));
            pedido.put("empresa_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id()));
            pedido.put("user_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getId()));
            pedido.put("android_id", String.valueOf(paramPedido.getIdTmp()));
            pedido.put("subtotal", String.valueOf(paramPedido.getSubtotal()));
            pedido.put("monto", String.valueOf(paramPedido.getMonto())); //Precio total del helado
            pedido.put("iva", String.valueOf(paramPedido.getIva()));
            pedido.put("estado_id", String.valueOf(Constants.ESTADO_ENVIADO ));

            pedido.put("localidad", String.valueOf(paramPedido.getLocalidad()));
            pedido.put("calle", String.valueOf(paramPedido.getCalle()));
            pedido.put("nro", String.valueOf(paramPedido.getNro()));
            pedido.put("piso", String.valueOf(paramPedido.getPiso()));
            pedido.put("telefono", String.valueOf(paramPedido.getTelefono()));
            pedido.put("contacto", String.valueOf(paramPedido.getContacto()));

            pedido.put("cucharitas", String.valueOf(paramPedido.getCucharitas()));
            pedido.put("cucuruchos", String.valueOf(paramPedido.getCucuruchos()));
            pedido.put("cucurucho_monto", String.valueOf(paramPedido.getMontoCucuruchos()));
            pedido.put("envio_domicilio", String.valueOf(paramPedido.getEnvioDomicilio()));
            pedido.put("monto_descuento", String.valueOf(paramPedido.getMontoDescuento()));
            pedido.put("cantidad_descuento", String.valueOf(paramPedido.getCantidadDescuento()));

            pedido.put("precioxkilo", String.valueOf(paramPedido.getPrecioxkilo()));
            pedido.put("tiempodemora", String.valueOf(paramPedido.getTiempoDemora()));
            pedido.put("cantidadkilos", String.valueOf(paramPedido.getCantidadKilos()));
            pedido.put("monto_helados", String.valueOf(paramPedido.getMontoHelados()));
            pedido.put("cantidadpotes", String.valueOf(paramPedido.getCantidadPotes()));

            for(int x = 0; x< paramPedido.getDetalles().size(); x++) {
                JSONObject item = new JSONObject();
                Pedidodetalle pd = (Pedidodetalle) paramPedido.getDetalles().get(x);
                item.put("producto_id", pd.getProductoId().toString());
                item.put("cantidad",  String.valueOf(pd.getCantidad()));
                item.put("android_id",  String.valueOf(pd.getIdTmp()));
                item.put("nropote",  String.valueOf(paramPedido.getIdTmp()));
                pedidodetalles.put(item);
            }
            pedido.put("pedidodetalles", pedidodetalles);

            return pedido;

        }catch (Exception e){
            if (pDialog.isShowing())
                pDialog.dismiss();
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
            return null;
        }
    }


    public boolean enviarPedido2(Pedido paramPedido){
        try{
            setPedido(paramPedido);
            URL url;
            URLConnection urlConn;
            DataOutputStream printout;
            DataInputStream input;


            //Seteo de conexion al servicio API
            URL object=new URL(Configurador.urlPostPedido);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            //Create JSONObject here
            JSONObject objectjson = new JSONObject();
            PedidodetalleController pdc = new PedidodetalleController(getCtx());
            objectjson.put("pedido", parseObjectToJson(paramPedido));

            //Enviar Json
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(objectjson.toString());
            wr.flush();

            //Procesar Respuesta, capurar nuero de pedido en el sistema web, y asignar a pedido.setId()
            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                //actulizar estado de pedido a enviado


                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                //JSONObject obj = new JSONObject(sb.toString());
                br.close();
                System.out.println("" + sb.toString());
            } else {
                System.out.println(con.getResponseMessage());
            }

            //Procesa post

            PedidoParser pp = new PedidoParser(sb.toString());
            Pedido pedidopostsave = pp.parseJsonToObject();
            Pedido pedidoprevsave = pedidoCtr.abrir().buscar(paramPedido.getIdTmp(),true);
            pedidoprevsave.setId(pedidopostsave.getId());
            pedidoprevsave.setEstadoId(Constants.ESTADO_ENPREPARACION);
            pedidoCtr.abrir().modificar(pedidoprevsave, true);
            pedidoCtr.cerrar();


            return true;
        }catch (Exception e){
            if (pDialog.isShowing())
                pDialog.dismiss();
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
            return false;
        }
    }

    public boolean enviarPedidosPendientes(){
        try {
            ArrayList<Pedido> listaPedidos = pedidoCtr.abrir().findByEstadoId_ArrayList(GlobalValues.getINSTANCIA().ESTADO_NUEVO);
            for (Pedido pedido : listaPedidos)
            {
                enviarPedido2(pedido);
            }
            return true;
        }catch(Exception e ){
            if (pDialog.isShowing())
                pDialog.dismiss();
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
            return false;
        }
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{

            if (getOpcion() == GlobalValues.getINSTANCIA().ENVIAR_PEDIDO) {
                enviarPedido2(getPedido());
            }else if(getOpcion()==GlobalValues.getINSTANCIA().ENVIAR_PEDIDOSPENDIENTES){
                enviarPedidosPendientes();
            }

            return null;
        }catch (Exception e){
            if (pDialog.isShowing())
                pDialog.dismiss();
                setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            //Toast.makeText(this.getCtx(),"Error: "+e.getMessage(),Toast.LENGTH_LONG);

            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
            return null;
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(this.getCtx());
        pDialog.setMessage("Enviando Pedido...");
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Showing progress dialog
        if (pDialog.isShowing()){
            pDialog.dismiss();
            if (getRespuesta()== GlobalValues.getINSTANCIA().RETURN_OK){
                Toast.makeText(this.getCtx(), "Enviado Correctamente ", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
