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
import adaptivex.pedidoscloud.Model.Horario;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.Pedidodetalle;


/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperHorarios extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private ProgressDialog pDialog;
    private HashMap<String,String> registro;
    private Pedido pedido;
    private PedidoController pedidoCtr;

    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Pedido, 2 ENVIAR TODOS LOS PEDIDOS PENDIENTES


    private String TEXT_RESPONSE;
    private int CURRENT_OPTION = 0; //1 enviar Post Parameter


    public static final int OPTION_FIND_ALL            = 1;

    //Constructor donde le pasas el numero de pedido temporal y la opcion
    //ID pedido temporal, es el ID que tiene en la base de datos SQLITE
    //ID pedido real, es el ID que se asigna en MYSQL
    //OPCION: 1 enviar solo un pedido
    //OPCION: 2 enviar todos los pedidos pendientes
    public HelperHorarios(Context pCtx){
        this.setCtx(pCtx);
    }


    public HelperHorarios(Context pCtx, long pNroPedidoTmp, int opcion){
        this.setCtx(pCtx);
        this.pedidoCtr = new PedidoController(this.getCtx());
        //this.setPedido(pedidoCtr.abrir().buscar(pNroPedidoTmp, true));
        this.opcion = opcion;
    }

    public HelperHorarios(Context pCtx, int opcion){
        this.setCtx(pCtx);
        this.pedidoCtr = new PedidoController(this.getCtx());
        this.opcion = opcion;
    }

    public HelperHorarios(Context pCtx, int opcion, Pedido pedido){
        this.setCtx(pCtx);
        this.pedidoCtr = new PedidoController(this.getCtx());
        this.opcion = opcion;
        this.pedido = pedido;
    }



    @Override
    protected Void doInBackground(Void... voids) {
        try{
            switch (getOpcion()){
                case OPTION_FIND_ALL:
                    findAll();
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
        pDialog.setMessage("Obteniendo Horarios...");
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        switch(getOpcion()){
            case OPTION_FIND_ALL:
                HorarioParser hp = new HorarioParser(TEXT_RESPONSE);
                hp.parseJsonToObject();
                break;

        }

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





    /* ======================  Tratamiento de Datos =============================================== */

    private void findAll(){
        try{
            WebRequest webreq = new WebRequest();
//            JSONObject json = new JSONObject();
            TEXT_RESPONSE = webreq.makeWebServiceCallJson(Configurador.urlHorarios, WebRequest.POST, null);
        }catch (Exception e){
            Log.println(Log.ERROR,"ErrorHelperHorarios:",e.getMessage());
        }
    }













}
