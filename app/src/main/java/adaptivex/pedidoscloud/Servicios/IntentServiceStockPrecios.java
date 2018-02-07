package adaptivex.pedidoscloud.Servicios;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.HashMap;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Core.ParameterHelper;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.ProductoParser;
import adaptivex.pedidoscloud.Model.Producto;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IntentServiceStockPrecios extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.ezequiel.myapplication1.Servicios.action.FOO";
    private static final String ACTION_BAZ = "com.example.ezequiel.myapplication1.Servicios.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.ezequiel.myapplication1.Servicios.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.ezequiel.myapplication1.Servicios.extra.PARAM2";


    private Context ctx;
    private HashMap<String,String> registro;
    private Producto producto;
    private ProductoController productoCtr;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Producto
    private ProgressDialog pDialog;

    public IntentServiceStockPrecios() {
        super("IntentServiceStockPrecios");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */


    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    // TODO: Customize helper method
    public static void startActionGetStockPrecios(Context context) {
        Intent intent = new Intent(context, IntentServiceStockPrecios.class);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
        while(true) {
            final String action = intent.getAction();
/*
            if (GlobalValues.getINSTANCIA().ACTION_GET_STOCK_PRECIOS.equals(action)) {

            }
*/
            this.productoCtr = new ProductoController(getBaseContext());
            Toast.makeText(this.getBaseContext(), "Comienzo Actualizacion de Productos ", Toast.LENGTH_SHORT).show();
            try {
                WebRequest webreq = new WebRequest();
                registro = new HashMap<String, String>();
                registro.put("empresa_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id()));
                String jsonStr = webreq.makeWebServiceCall(Configurador.urlProductos, WebRequest.POST, registro);
                ProductoParser cp = new ProductoParser(jsonStr);
                cp.parseJsonToObject();
                //Recorrer Lista
                // Setear bandera de Actualizacion de stock
                ParameterHelper ph = new ParameterHelper(getBaseContext());
                //ph.setServiceStockPrecioWorking(GlobalValues.getINSTANCIA().VALUE_SERVICE_STOCK_PRECIOS_WORKING);



                for (int i = 0; i < cp.getListadoProductos().size(); i++) {
                    //Validar, si existe producto, por codigo externo
                    Producto itemProducto = cp.getListadoProductos().get(i);
                    Producto p = new Producto();
                    p = productoCtr.abrir().findByCodigoExterno(itemProducto.getCodigoexterno());
                    if (p== null){
                        //si no existe agrego el nuevo producto
                        productoCtr.abrir().agregar(itemProducto);
                        productoCtr.cerrar();
                    }else{
                        // SI existe modifico precio y stock
                        p.setStock(itemProducto.getStock());
                        p.setPrecio(itemProducto.getPrecio());
                        productoCtr.abrir().modificar(p);
                        productoCtr.cerrar();
                    }

                }

                //ph.setServiceStockPrecioWorking(GlobalValues.getINSTANCIA().VALUE_SERVICE_STOCK_PRECIOS_WORKING_NOT);
                setRespuesta(GlobalValues.getINSTANCIA().RETURN_OK);
                Toast.makeText(getBaseContext(), "Finalizacion Actualizacion de Productos ", Toast.LENGTH_SHORT).show();
                Thread.sleep(10000);


            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }catch(Exception e ){
                Toast.makeText(getBaseContext(), "Error " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }


            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
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
}
