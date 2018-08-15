package adaptivex.pedidoscloud.Servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.util.HashMap;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.HorarioController;

import adaptivex.pedidoscloud.Core.parserJSONtoModel.HorarioParser;
import adaptivex.pedidoscloud.Model.Horario;


/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperHorarios extends AsyncTask<Void, Void, Void> {
    private Context                 ctx;
    private ProgressDialog          pDialog;
    private HashMap<String,String>  registro;
    private HorarioController       controller ;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Pedido, 2 ENVIAR TODOS LOS PEDIDOS PENDIENTES
    private String TEXT_RESPONSE;


    public static final int OPTION_FIND_ALL            = 1;

    //Constructor donde le pasas el numero de pedido temporal y la opcion
    //ID pedido temporal, es el ID que tiene en la base de datos SQLITE
    //ID pedido real, es el ID que se asigna en MYSQL
    //OPCION: 1 enviar solo un pedido
    //OPCION: 2 enviar todos los pedidos pendientes


    public HelperHorarios(Context pCtx){
        this.setCtx(pCtx);
        this.controller = new HorarioController(this.getCtx());
    }

    public HelperHorarios(Context pCtx, int opcion){
        this.setCtx(pCtx);
        this.controller = new HorarioController(this.getCtx());
        this.opcion = opcion;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            switch (getOpcion()){
                case OPTION_FIND_ALL:
                    try{
                        WebRequest webreq = new WebRequest();
                        TEXT_RESPONSE = webreq.makeWebServiceCall(Configurador.urlHorarios, WebRequest.GET);
                    }catch (Exception e){
                        Log.println(Log.ERROR,"ErrorHelperHorarios:",e.getMessage());
                    }
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
                HorarioParser hp = new HorarioParser();
                hp.parseJsonToObject(TEXT_RESPONSE);
                controller.abrir().limpiar();
                //Recorrer Lista
                for (int i = 0; i < hp.getListadoHorarios().size(); i++) {
                    controller.abrir().agregar(hp.getListadoHorarios().get(i));
                }
                setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);

                break;

        }

        if (pDialog.isShowing()) pDialog.dismiss();


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

    }













}
