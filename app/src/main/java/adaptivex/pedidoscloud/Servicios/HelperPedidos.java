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
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Controller.PedidodetalleController;
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

    public boolean enviarPedido(Pedido paramPedido){
        try{
            setPedido(paramPedido);
            WebRequest webreq = new WebRequest();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechahoy = new Date();
            String fechahoystr = dateFormat.format(fechahoy);
            System.out.println(dateFormat.format(fechahoy));
            //if (paramPedido.getEstadoId() == GlobalValues.getINSTANCIA().consPedidoEstadoNuevo) {
                this.registro = new HashMap<String, String>();

                this.registro.put("fecha", String.valueOf(fechahoystr));
                this.registro.put("user_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getId()));
                this.registro.put("empresa_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id()));
                this.registro.put("cliente_id", paramPedido.getCliente_id().toString());
                this.registro.put("android_id", paramPedido.getIdTmp().toString());
                this.registro.put("estado_id", String.valueOf(GlobalValues.getINSTANCIA().consPedidoEstadoEnviado));

                this.registro.put("monto", String.valueOf(paramPedido.getMonto()));
                this.registro.put("iva", String.valueOf(paramPedido.getIva()));
                this.registro.put("subtotal", String.valueOf(paramPedido.getSubtotal()));


                //Procesa post
                String jsonStr = webreq.makeWebServiceCall(Configurador.urlPostPedido, WebRequest.POST,this.registro);

                PedidoParser pp = new PedidoParser(jsonStr);
                Pedido pedidopostsave = pp.parseJsonToObject();
                Pedido pedidoprevsave = pedidoCtr.abrir().buscar(paramPedido.getIdTmp(),true);
                pedidoprevsave.setId(pedidopostsave.getId());
                pedidoprevsave.setEstadoId(GlobalValues.getINSTANCIA().consPedidoEstadoEnviado);
                pedidoCtr.abrir().modificar(pedidoprevsave, true);
                pedidoCtr.cerrar();
                //setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);
                Log.println(Log.ERROR, "Helper:", " Guardado Correctamente ");

                //Enviar Pedido Items
                Pedidodetalle pd = new Pedidodetalle();
                PedidodetalleController pdc = new PedidodetalleController(getCtx());

                for(int x = 0; x< paramPedido.getDetalles().size(); x++) {

                    pd = (Pedidodetalle) paramPedido.getDetalles().get(x);
                    pd.setPedidoId(paramPedido.getId());
                    pdc.abrir().modificar(pd);
                    pdc.cerrar();

                    HashMap<String,String> pedidodetalleitem = new HashMap<String, String>();
                    pedidodetalleitem.put("idtmp", "1");
                    pedidodetalleitem.put("producto_id", pd.getProductoId() .toString());
                    pedidodetalleitem.put("cantidad", pd.getCantidad().toString());
                    pedidodetalleitem.put("precio", pd.getPreciounitario().toString());
                    pedidodetalleitem.put("monto", pd.getMonto().toString());
                    pedidodetalleitem.put("pedidocabecera_idtmp", String.valueOf(pd.getPedidoTmpId()));
                    pedidodetalleitem.put("pedidocabecera_id", paramPedido.getId().toString());
                    pedidodetalleitem.put("created", fechahoystr );
                    pedidodetalleitem.put("subtotal", paramPedido.getSubtotal().toString() );
                    pedidodetalleitem.put("empresa_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id()));

                    String jsonStrpedidodetalle = webreq.makeWebServiceCall(Configurador.urlPostPedidodetalle, WebRequest.POST, pedidodetalleitem);
                    System.out.println(jsonStrpedidodetalle);
                }

            //}
            return true;
        }catch (Exception e){
            if (pDialog.isShowing())
                pDialog.dismiss();
            setRespuesta(GlobalValues.getINSTANCIA().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
            return false;
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
            JSONObject pedido = new JSONObject();
            JSONArray pedidodetalles = new JSONArray();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechahoy = new Date();
            String fechahoystr = dateFormat.format(fechahoy);
            System.out.println(dateFormat.format(fechahoy));

            pedido.put("fecha", String.valueOf(fechahoystr));
            pedido.put("user_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getId()));
            pedido.put("empresa_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id()));
            pedido.put("cliente_id", paramPedido.getCliente_id().toString());
            pedido.put("android_id", paramPedido.getIdTmp().toString());
            pedido.put("estado_id", String.valueOf(GlobalValues.getINSTANCIA().consPedidoEstadoEnviado));
            pedido.put("monto", String.valueOf(paramPedido.getMonto()));
            pedido.put("iva", String.valueOf(paramPedido.getIva()));
            pedido.put("subtotal",  String.valueOf(paramPedido.getSubtotal()));


            JSONObject pedidodetallejson = new JSONObject();

            //Armar Json detalle de pedidos
            //Enviar Pedido Items
            Pedidodetalle pd = new Pedidodetalle();
            PedidodetalleController pdc = new PedidodetalleController(getCtx());

            for(int x = 0; x< paramPedido.getDetalles().size(); x++) {
                JSONObject item = new JSONObject();
                pd = (Pedidodetalle) paramPedido.getDetalles().get(x);
                item.put("producto_id", pd.getProductoId().toString());
                item.put("cantidad",  String.valueOf(pd.getCantidad()));
                item.put("android_id",  String.valueOf(pd.getIdTmp()));
                item.put("pedido_android_id",  String.valueOf(paramPedido.getIdTmp()));
                pedidodetalles.put(item);
            }

            pedido.put("pedidodetalles", pedidodetalles);
            objectjson.put("pedido", pedido);

            //Enviar Json
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(objectjson.toString());
            wr.flush();

            //Procesar Respuesta, capurar nuero de pedido en el sistema web, y asignar a pedido.setId()
            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                //actulizar estado de pedido a enviado
                //paramPedido.setEstadoId(GlobalValues.getINSTANCIA().consPedidoEstadoEnviado);
                //pedidoCtr.abrir().modificar(paramPedido,true);
                //pedidoCtr.cerrar();

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
            pedidoprevsave.setEstadoId(GlobalValues.getINSTANCIA().consPedidoEstadoEnviado);
            pedidoCtr.abrir().modificar(pedidoprevsave, true);
            pedidoCtr.cerrar();
            //setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);
            Log.println(Log.ERROR, "Helper:", " Guardado Correctamente ");



            //}
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
