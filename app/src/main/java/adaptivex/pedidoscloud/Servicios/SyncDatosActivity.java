package adaptivex.pedidoscloud.Servicios;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.CategoriaController;
import adaptivex.pedidoscloud.Controller.HojarutaController;
import adaptivex.pedidoscloud.Controller.HojarutadetalleController;
import adaptivex.pedidoscloud.Controller.MarcaController;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Controller.PedidodetalleController;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.Categoria;
import adaptivex.pedidoscloud.Model.CategoriaDataBaseHelper;
import adaptivex.pedidoscloud.Model.ClienteDataBaseHelper;
import adaptivex.pedidoscloud.Model.Hojaruta;
import adaptivex.pedidoscloud.Model.HojarutaDataBaseHelper;
import adaptivex.pedidoscloud.Model.Hojarutadetalle;
import adaptivex.pedidoscloud.Model.HojarutadetalleDataBaseHelper;
import adaptivex.pedidoscloud.Model.Marca;
import adaptivex.pedidoscloud.Model.MarcaDataBaseHelper;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.Model.ProductoDataBaseHelper;
import adaptivex.pedidoscloud.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SyncDatosActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button btnSyncClientes = (Button) findViewById(R.id.btnSyncClientes);
        btnSyncClientes.setOnClickListener(this);

        Button btnSyncProductos = (Button) findViewById(R.id.btnSyncProductos);
        btnSyncProductos.setOnClickListener(this);

        Button btnSyncMarcas = (Button) findViewById(R.id.btnSyncMarcas);
        btnSyncMarcas.setOnClickListener(this);

        Button btnSyncCategorias = (Button) findViewById(R.id.btnSyncCategorias);
        btnSyncCategorias.setOnClickListener(this);

        Button btnSyncHojaruta = (Button) findViewById(R.id.btnSyncHojarutas);
        btnSyncHojaruta.setOnClickListener(this);

        Button btnSyncPedidos = (Button) findViewById(R.id.btnSyncPedidos);
        btnSyncPedidos.setOnClickListener(this);


        Button btnSyncHojarutadetalle = (Button) findViewById(R.id.btnSyncHojarutadetalles);
        btnSyncHojarutadetalle.setOnClickListener(this);


        Button btnLimpiarPedidos = (Button) findViewById(R.id.btnLimpiarPedidos);
        btnLimpiarPedidos.setOnClickListener(this);

        Button btnLimpiarPedidoDetalles = (Button) findViewById(R.id.btnLimpiarPedidoDetalles);
        btnLimpiarPedidoDetalles.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        Syncronizacion sync = new Syncronizacion();

        switch (v.getId()){

            case R.id.btnSyncClientes:
                Toast.makeText(getBaseContext(), "Descargando Clientes", Toast.LENGTH_LONG).show();
                HelperClientes hc = new HelperClientes(getBaseContext());
                hc.execute();
                break;

            case R.id.btnSyncProductos:
                Toast.makeText(getBaseContext(), "Descargando Productos", Toast.LENGTH_LONG).show();
                HelperProductos hp = new HelperProductos(getBaseContext());
                hp.execute();
                break;

            case R.id.btnSyncCategorias:
                Toast.makeText(getBaseContext(), "Descargando Categorias", Toast.LENGTH_LONG).show();
                HelperCategorias hcat = new HelperCategorias(getBaseContext());
                hcat.execute();
                break;

            case R.id.btnSyncMarcas:
                Toast.makeText(getBaseContext(), "Descargando Marcas", Toast.LENGTH_LONG).show();
                HelperMarcas hmar = new HelperMarcas(getBaseContext());
                hmar.execute();
                break;

            case R.id.btnSyncPedidos:
                Toast.makeText(getBaseContext(), "Descargando Pedidos", Toast.LENGTH_LONG).show();
                sync.setEntidad(PedidoDataBaseHelper.TABLE_NAME);
                sync.execute();
                break;

            case R.id.btnSyncHojarutas:
                Toast.makeText(getBaseContext(), "Descargando Hoja de Ruta ", Toast.LENGTH_LONG).show();
                HelperHojarutas hhr = new HelperHojarutas(getBaseContext());
                hhr.execute();
                break;

            case R.id.btnSyncHojarutadetalles:
                Toast.makeText(getBaseContext(), "Descargando Hoja de Ruta items", Toast.LENGTH_LONG).show();
                HelperHojarutadetalles hhrd = new HelperHojarutadetalles(getBaseContext());
                hhrd.execute();
                break;


            case R.id.btnLimpiarPedidos:
                PedidoController pd = new PedidoController(this);
                pd.abrir();
                pd.limpiar();
                pd.cerrar();
                Toast.makeText(getBaseContext(), "Tabla Pedidos Limpia", Toast.LENGTH_LONG).show();

                break;

            case R.id.btnLimpiarPedidoDetalles:
                PedidodetalleController pdet = new PedidodetalleController(this);
                pdet.abrir();
                pdet.limpiar();
                pdet.cerrar();
                Toast.makeText(getBaseContext(), "Tabla Pedidos Detalles Limpia", Toast.LENGTH_LONG).show();


                break;

            default:
                break;
        }


    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class Syncronizacion extends AsyncTask<Void, Void, Void> {

        // Hashmap for ListView
        public ArrayList<HashMap<String, String>> Listaregistros;
        private HashMap<String,String> registro;

        ProgressDialog pDialog;
        public String jsontexto;
        private String entidad;
        private String vMensaje;


        public void Syncronizacion(String entidad){
            Log.d("Debug: ", "> " + this.getEntidad());
            this.setEntidad(entidad);
            registro = new HashMap<String, String>();
            registro.put("empresa_id", String.valueOf(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id()));
            this.setvMensaje("Registros Agregados correctamente");
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SyncDatosActivity.this);
            pDialog.setMessage("Sincronizando...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            WebRequest webreq = new WebRequest();

            String jsonStr="";
            Log.d("Debug: ", "> " + this.getEntidad());
            //CONSUMIR WEB SERVICES
            switch (this.getEntidad()){

                case PedidoDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlPedidos, WebRequest.GET,this.registro);
                    SincronizarPedidos(jsonStr);
                    break;

                case ClienteDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlClientes, WebRequest.GET,this.registro);
                    //SincronizarClientes(jsonStr);
                    break;

                case ProductoDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlProductos, WebRequest.GET,this.registro);
                    SincronizarProductos(jsonStr);
                    break;

                case CategoriaDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlCategorias, WebRequest.GET,this.registro);
                    SincronizarCategorias(jsonStr);
                    break;

                case MarcaDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlMarcas, WebRequest.GET,this.registro);
                    SincronizarMarcas(jsonStr);
                    break;


                case HojarutaDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlHojarutas, WebRequest.GET,this.registro);
                    SincronizarHojarutas(jsonStr);
                    break;


                case HojarutadetalleDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlHojarutadetalles, WebRequest.GET,this.registro);
                    SincronizarHojarutadetalles(jsonStr);
                    break;



            }

            //setListaregistros(ParseJSON(jsonStr));

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            Log.d("Debug onPostExecute: ", "> " + jsontexto);
            switch (this.getEntidad()){
                case "cliente":
                    //SincronizarClientes(jsontexto);


                    break;

                case "producto":
                    SincronizarProductos(jsontexto);

                    break;

                case "categoria":
                    SincronizarCategorias(jsontexto);

                    break;

                case "marca":
                    SincronizarMarcas(jsontexto);

                    break;

                case "hojaruta":
                    SincronizarHojarutas(jsontexto);

                    break;
                case "hojarutadetalle":
                    SincronizarHojarutadetalles(jsontexto);
                    break;

                default:
                    Toast.makeText(getBaseContext(),getvMensaje(), Toast.LENGTH_LONG).show();
                    break;
            }



        }

        public ArrayList<HashMap<String, String>> getListaregistros() {
            return Listaregistros;
        }

        public void setListaregistros(ArrayList<HashMap<String, String>> Listaregistros) {
            this.Listaregistros = Listaregistros;
        }

        public String getEntidad() {
            return entidad;
        }

        public void setEntidad(String entidad) {
            this.entidad = entidad;
        }

        public String getvMensaje() {
            return vMensaje;
        }

        public void setvMensaje(String vMensaje) {
            this.vMensaje = vMensaje;
        }




    private boolean SincronizarPedidos(String json){
        PedidoController dbHelper = new PedidoController(getBaseContext());

        Log.d("Debug: json ", json);
        //dbHelper.beginTransaction();
        if (json != null) {
            try {
                Boolean resultado;
                resultado = false;
                //
                JSONObject jsonObj = new JSONObject(json);

                Log.d("Debug: ", jsonObj.toString());

                JSONArray pedidos = jsonObj.getJSONArray("pedidos");

                Pedido pedido = new Pedido();
                dbHelper.abrir().limpiar();
                //Recorrer Lista
                for (int i = 0; i < pedidos.length(); i++) {

                    JSONObject c = pedidos.getJSONObject(i);
                    JSONObject registro = c.getJSONObject("Pedidocabecera");
                    Log.d("Debug: ", "> " + registro.toString());

                   // pedido.setId(registro.getInt("id"));
                    pedido.setId(registro.getInt(PedidoDataBaseHelper.CAMPO_ID));
                    pedido.setCreated(registro.getString(PedidoDataBaseHelper.CAMPO_CREATED));
                    pedido.setCliente_id(registro.getInt(PedidoDataBaseHelper.CAMPO_CLIENTE_ID));

                    pedido.setMonto(registro.getDouble(PedidoDataBaseHelper.CAMPO_MONTO));
                    pedido.setIva(registro.getDouble(PedidoDataBaseHelper.CAMPO_IVA));
                    pedido.setBonificacion(registro.getDouble(PedidoDataBaseHelper.CAMPO_BONIFICACION));

                    dbHelper.abrir().agregar(pedido);
                }
                resultado = true;
                setvMensaje("Sincro OK");

                return resultado;

            } catch (JSONException e) {
                Log.d("SyncDatosactivity: ", e.toString());
                setvMensaje(e.getMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return false;
        }


    }

    private boolean SincronizarProductos(String json){
        ProductoController dbHelper = new ProductoController(getBaseContext());
        Log.d("Debug: ", "SincronizarProductos ");
        //dbHelper.beginTransaction();
        if (json != null) {
            try {
                Boolean resultado;
                resultado = false;
                JSONObject jsonObj = new JSONObject(json);

                Log.d("Debug: ", jsonObj.toString());

                JSONArray productos = jsonObj.getJSONArray("data");

                Producto producto = new Producto();

                dbHelper.abrir().limpiar();



                //Recorrer Lista
                for (int i = 0; i < productos.length(); i++) {

                    JSONObject c = productos.getJSONObject(i);
                    JSONObject registro = c.getJSONObject("Producto");
                    Log.d("Debug: ", "> " + registro.toString());

                    producto.setId(registro.getInt("id"));
                    producto.setNombre(registro.getString(ProductoDataBaseHelper.CAMPO_NOMBRE));
                    producto.setDescripcion(registro.getString(ProductoDataBaseHelper.CAMPO_DESCRIPCION));
                    producto.setPrecio(registro.getInt(ProductoDataBaseHelper.CAMPO_PRECIO));
                    producto.setImagen(registro.getString(ProductoDataBaseHelper.CAMPO_IMAGEN));

                    String imagenurl = Configurador.urlImgClientes + registro.getInt("id") + "/"+ registro.getString(ProductoDataBaseHelper.CAMPO_IMAGEN);
                    producto.setImagenurl(imagenurl);

                    dbHelper.abrir().agregar(producto);
                }
                resultado = true;
                setvMensaje("Sincro OK");

                return resultado;

            } catch (JSONException e) {
                Log.d("SyncDatosActivity: ", e.toString());
                e.printStackTrace();

                setvMensaje(e.getMessage());

                return false;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return false;
        }

    } // Fin SincronizarProductos


    private boolean SincronizarCategorias(String json){
        CategoriaController dbHelper = new CategoriaController(getBaseContext());
        //dbHelper.beginTransaction();
        if (json != null) {
            try {
                Boolean resultado;
                resultado = false;

                JSONObject jsonObj = new JSONObject(json);
                Log.d("Debug: ", jsonObj.toString());
                JSONArray categorias = jsonObj.getJSONArray("data");
                Categoria categoria = new Categoria();
                dbHelper.abrir().limpiar();
                //Recorrer Lista
                for (int i = 0; i < categorias.length(); i++) {

                    JSONObject c = categorias.getJSONObject(i);
                    JSONObject registro = c.getJSONObject("Categoria");
                    Log.d("Debug: ", "> " + registro.toString());
                    categoria.setId(registro.getInt("id"));
                    categoria.setDescripcion(registro.getString(CategoriaDataBaseHelper.CAMPO_DESCRIPCION));
                    dbHelper.abrir().agregar(categoria);
                }
                resultado = true;
                setvMensaje("SincroOK");

                return resultado;
            } catch (JSONException e) {
                Log.d("SyncDatosActivity: ", e.toString());
                setvMensaje(e.getMessage());

                return false;
            }
        } else {
            Log.e("Sincronizacion:", "No se pudo obtener informacion del web services Categorias");

            return false;
        }

    } // Fin SincronizarCategorias


    private boolean SincronizarMarcas(String json){
        MarcaController dbHelper = new MarcaController(getBaseContext());
        dbHelper.abrir();

        //dbHelper.beginTransaction();
        if (json != null) {
            try {
                Boolean resultado;
                resultado = false;

                JSONObject jsonObj = new JSONObject(json);
                JSONArray marcas = jsonObj.getJSONArray("data");
                Marca marca = new Marca();
                dbHelper.limpiar();

                Log.e("Debug:",marca.toString() );
                //Recorrer Lista
                for (int i = 0; i < marcas.length(); i++) {

                    JSONObject c = marcas.getJSONObject(i);
                    JSONObject registro = c.getJSONObject("Marca");
                    Log.d("Debuging1: ", "> " + registro.toString());

                    marca.setId(registro.getInt("id"));

                    marca.setDescripcion(registro.getString(CategoriaDataBaseHelper.CAMPO_DESCRIPCION));
                    dbHelper.abrir().agregar(marca);
                }
                resultado = true;
                setvMensaje("Sincro OK");

                return resultado;
            } catch (JSONException e) {
                setvMensaje(e.getMessage());

                Log.d("SyncDatosActivity: ", e.toString());

                return false;
            }
        } else {

            return false;
        }

    } // Fin SincronizarMarcas



    private boolean SincronizarHojarutas(String json){
        HojarutaController dbHelper = new HojarutaController(getBaseContext());
        dbHelper.abrir();

        if (json != null) {
            try {
                Boolean resultado;
                resultado = false;
                JSONObject jsonObj = new JSONObject(json);
                JSONArray hojarutas = jsonObj.getJSONArray("data");
                Hojaruta item = new Hojaruta();
                dbHelper.limpiar();

                Log.e("Debug:",item.toString() );
                //Recorrer Lista
                for (int i = 0; i < hojarutas.length(); i++) {
                    JSONObject c = hojarutas.getJSONObject(i);
                    JSONObject registro = c.getJSONObject("Hojaruta");
                    Log.d("Debuging1: ", "> " + registro.toString());
                    item.setId(registro.getInt("id"));
                    item.setUser_id(registro.getInt(HojarutaDataBaseHelper.USER_ID));
                    item.setDia_id(registro.getInt(HojarutaDataBaseHelper.DIA_ID));
                    item.setTitulo(registro.getString(HojarutaDataBaseHelper.TITULO));
                    item.setNotas(registro.getString(HojarutaDataBaseHelper.NOTAS));
                    dbHelper.abrir().agregar(item);
                }
                resultado = true;
                Toast.makeText(getBaseContext(), "Sincronizacion OK", Toast.LENGTH_LONG).show();

                return resultado;
            } catch (JSONException e) {
                Log.d("SyncDatosActivity: ", e.toString());
                setvMensaje(e.getMessage());
                return false;
            }
        } else {
            Log.e("Sincronizacion:", "No se pudo obtener informacion del web servisces Hojarutas");

            return false;
        }

    } // Fin SincronizarHojarutas

    private boolean SincronizarHojarutadetalles(String json){
        HojarutadetalleController dbHelper = new HojarutadetalleController(getBaseContext());
        dbHelper.abrir();

        if (json != null) {
            try {
                Boolean resultado;
                resultado = false;

                JSONObject jsonObj = new JSONObject(json);
                JSONArray Hojarutadetalles = jsonObj.getJSONArray("data");
                Hojarutadetalle item = new Hojarutadetalle();
                dbHelper.limpiar();

                Log.e("Debug:",item.toString() );
                //Recorrer Lista
                for (int i = 0; i < Hojarutadetalles.length(); i++) {

                    JSONObject c = Hojarutadetalles.getJSONObject(i);
                    JSONObject registro = c.getJSONObject("Hojarutadetalle");
                    Log.d("Debuging1: ", "> " + registro.toString());

                    item.setId(registro.getInt("id"));
                    item.setHojaruta_id(registro.getInt(HojarutadetalleDataBaseHelper.HOJARUTA_ID));
                    item.setCliente_id(registro.getInt(HojarutadetalleDataBaseHelper.CLIENTE_ID));
                    item.setHora(registro.getString(HojarutadetalleDataBaseHelper.HORA));
                    item.setNotas(registro.getString(HojarutadetalleDataBaseHelper.NOTAS));

                    dbHelper.abrir().agregar(item);
                }
                resultado = true;
                setvMensaje("Sincro OK");

                return resultado;

            } catch (JSONException e) {
                Log.d("SyncDatosActivity: ", e.toString());
                setvMensaje(e.getMessage());
                return false;
            }
        } else {
            Log.e("Sincronizacion:", "No se pudo obtener informacion del web servisces Hojarutadetalles");

            return false;
        }

    } // Fin SincronizarHojarutadetalle
    }
}
