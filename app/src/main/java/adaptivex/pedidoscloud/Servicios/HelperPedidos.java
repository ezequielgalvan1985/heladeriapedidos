package adaptivex.pedidoscloud.Servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
import adaptivex.pedidoscloud.Core.Interfaces.OnTaskCompleted;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.PedidoParser;
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

    private OnTaskCompleted listener;
    private ArrayList<Pedido> pedidos;
    private String TEXT_RESPONSE;
    private int CURRENT_OPTION = 0; //1 enviar Post Parameter


    public static final int OPTION_ENVIAR_PEDIDO            = 1;
    public static final int OPTION_ENVIAR_PEDIDOSPENDIENTES = 2;
    public static final int OPTION_CHECK_STATUS             = 3;
    public static final int OPTION_FIND_ESTADO_ENCAMINO     = 4;

    //Constructor donde le pasas el numero de pedido temporal y la opcion
    //ID pedido temporal, es el ID que tiene en la base de datos SQLITE
    //ID pedido real, es el ID que se asigna en MYSQL
    //OPCION: 1 enviar solo un pedido
    //OPCION: 2 enviar todos los pedidos pendientes
    public HelperPedidos(Context pCtx){
        this.setCtx(pCtx);
    }

    public HelperPedidos(Context pCtx, OnTaskCompleted quienescucha){
        this.setListener(quienescucha);
        this.setCtx(pCtx);
    }

    public HelperPedidos(Context pCtx, long pNroPedidoTmp, int opcion){
        this.setCtx(pCtx);
        this.pedidoCtr = new PedidoController(this.getCtx());
        //this.setPedido(pedidoCtr.abrir().buscar(pNroPedidoTmp, true));
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



    @Override
    protected Void doInBackground(Void... voids) {
        try{
            switch (getOpcion()){
                case OPTION_ENVIAR_PEDIDO:
                    enviarPedido2(getPedido());
                    break;

                case OPTION_ENVIAR_PEDIDOSPENDIENTES:
                    enviarPedidosPendientes();
                    break;

                case OPTION_CHECK_STATUS:
                    checkStatusPedido(getPedido());
                    break;

                case OPTION_FIND_ESTADO_ENCAMINO:
                    findByEstadoEnCamino();
                    break;
            }


            return null;
        }catch (Exception e){
            if (pDialog.isShowing())
                pDialog.dismiss();
                setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);


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

        switch(getOpcion()){
            case OPTION_CHECK_STATUS:
                GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setEstadoId(getPedido().getEstadoId());
                GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setHoraentrega(getPedido().getHoraentrega());
                GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setHoraRecepcion(getPedido().getHoraRecepcion());
                GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.setTiempoDemora(getPedido().getTiempoDemora());
                break;

            case OPTION_FIND_ESTADO_ENCAMINO:
                onPostFindEstadoEnCamino();
                break;
        }
        getListener().onTaskCompleted();

        if (pDialog.isShowing()){
            pDialog.dismiss();
            if (getRespuesta()== GlobalValues.getINSTANCIA().RETURN_OK){

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



    /* ======================  Tratamiento de Datos =============================================== */

    private void findByEstadoEnCamino(){
        try{
            WebRequest webreq = new WebRequest();

            JSONObject json = new JSONObject();
            json.put("estado_id", String.valueOf(Constants.ESTADO_ENCAMINO));
            TEXT_RESPONSE = webreq.makeWebServiceCallJson(Configurador.urlPedidos, WebRequest.POST, json);
        }catch (Exception e){
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
        }
    }

    private void onPostFindEstadoEnCamino(){
        try{
            PedidoParser cp = new PedidoParser(TEXT_RESPONSE);
            setPedidos(cp.parseResponseToArrayList());

        }catch (Exception e){
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
        }
    }


    public JSONObject parseObjectToJson(Pedido paramPedido){
        try{
            JSONObject pedido = new JSONObject();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechahoy = new Date();
            String fechahoystr = dateFormat.format(fechahoy);
            JSONArray pedidodetalles = new JSONArray();

            pedido.put("id", String.valueOf(paramPedido.getId()));
            //pedido.put("fecha", String.valueOf(fechahoystr));
            pedido.put("empresa_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id()));
            pedido.put("user_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getId()));
            pedido.put("android_id", String.valueOf(paramPedido.getIdTmp()));
            pedido.put("subtotal", String.valueOf(paramPedido.getSubtotal()));
            pedido.put("monto", String.valueOf(paramPedido.getMonto())); //Precio total del helado
            pedido.put("iva", String.valueOf(paramPedido.getIva()));
            pedido.put("estado_id", String.valueOf(Constants.ESTADO_ENPREPARACION ));

            pedido.put("localidad", String.valueOf(paramPedido.getLocalidad()));
            pedido.put("calle", String.valueOf(paramPedido.getCalle()));
            pedido.put("nro", String.valueOf(paramPedido.getNro()));
            pedido.put("piso", String.valueOf(paramPedido.getPiso()));
            pedido.put("telefono", String.valueOf(paramPedido.getTelefono()));
            pedido.put("contacto", String.valueOf(paramPedido.getContacto()));

            pedido.put("cucharitas", String.valueOf(paramPedido.getCucharitas()));
            pedido.put("cucuruchos", String.valueOf(paramPedido.getCucuruchos()));
            pedido.put("cucurucho_monto", String.valueOf(paramPedido.getMontoCucuruchos()));
            pedido.put("envio_domicilio", paramPedido.getEnvioDomicilioBoolean());
            pedido.put("monto_descuento", String.valueOf(paramPedido.getMontoDescuento()));
            pedido.put("montoabona", String.valueOf(paramPedido.getMontoabona()));

            pedido.put("cantidad_descuento", String.valueOf(paramPedido.getCantidadDescuento()));

            pedido.put("precioxkilo", String.valueOf(paramPedido.getPrecioxkilo()));
            //pedido.put("tiempodemora", String.valueOf(paramPedido.getTiempoDemora()));
            pedido.put("cantidadkilos", String.valueOf(paramPedido.getCantidadKilos()));
            pedido.put("monto_helados", String.valueOf(paramPedido.getMontoHelados()));
            pedido.put("cantidadpotes", String.valueOf(paramPedido.getCantidadPotes()));

            for(int x = 0; x< paramPedido.getDetalles().size(); x++) {
                JSONObject item = new JSONObject();
                Pedidodetalle pd = (Pedidodetalle) paramPedido.getDetalles().get(x);
                item.put("producto_id", pd.getProductoId().toString());
                item.put("cantidad",  String.valueOf(pd.getCantidad())); //Proporcion
                item.put("medidapote",  String.valueOf(pd.getMedidaPote())); //Proporcion
                item.put("android_id",  String.valueOf(pd.getIdTmp()));
                item.put("nropote",  String.valueOf(pd.getNroPote()));
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


    public Pedido requestPost(Pedido paramPedido, String pUrl){
        try {
            URL url;
            URLConnection urlConn;
            DataOutputStream printout;
            DataInputStream input;

            //Seteo de conexion al servicio API
            URL object = new URL(pUrl);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            //Create JSONObject here
            JSONObject objectjson = new JSONObject();
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
                br.close();
                System.out.println("" + sb.toString());
            } else {
                System.out.println(con.getResponseMessage());
            }

            //Procesa respuesta y almacena en una variable pedido
            PedidoParser pp = new PedidoParser(sb.toString());
            Pedido pedidoresponse = pp.parseJsonToObject();
            return pedidoresponse;
        }catch(Exception e){
            if (pDialog.isShowing())
                pDialog.dismiss();
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
            return null;
        }
    }

    public void checkStatusPedido(Pedido pedido){
        Pedido pedidoresponse = requestPost(pedido, Configurador.urlPedidoFindById);

        //actualizar el estado y horas
        pedido.setEstadoId(pedidoresponse.getEstadoId());
        pedido.setHoraentrega(pedidoresponse.getHoraentrega());
        pedido.setHoraRecepcion(pedidoresponse.getHoraRecepcion());
        pedido.setTiempoDemora(pedidoresponse.getTiempoDemora());
        PedidoController pc = new PedidoController(getCtx());
        pc.abrir().edit(pedido);
        setPedido(pedido);

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
                //Toast.makeText(this.getCtx(),"ERROR: "+ con.getResponseMessage(),Toast.LENGTH_LONG ).show();
            }

            //Procesa post
            //Procesa respuesta y almacena en una variable pedido
            PedidoParser pp = new PedidoParser(sb.toString());
            Pedido pedidopostsave = pp.parseJsonToObject();


            paramPedido.setId(pedidopostsave.getId());
            paramPedido.setEstadoId(Constants.ESTADO_ENPREPARACION);
            pedidoCtr.abrir().edit(paramPedido);
            pedidoCtr.cerrar();


            return true;
        }catch (Exception e){
            /*
            if (pDialog.isShowing())
                pDialog.dismiss();
            */
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

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public OnTaskCompleted getListener() {
        return listener;
    }

    public void setListener(OnTaskCompleted listener) {
        this.listener = listener;
    }
}
