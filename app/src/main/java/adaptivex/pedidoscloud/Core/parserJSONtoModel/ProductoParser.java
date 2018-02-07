package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.Model.ProductoDataBaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class ProductoParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONObject respuesta;
    private String status;
    private String message;
    private JSONArray data;
    private JSONObject productojson;
    private Producto producto;
    private ArrayList<Producto> listadoProductos;
    /* Solamente parsea los datos de un String Json, al Objeto ProductoParser */
    public ProductoParser(){
    }

    public ProductoParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public Producto parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoProductos  = new ArrayList<Producto>();
            setJsonobj(new JSONObject(getJsonstr()));
            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));

            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray productos = getData();
                Producto producto = new Producto();
                for (int i = 0; i < productos.length(); i++) {
                    JSONObject registro = productos.getJSONObject(i);

                    producto.setId(registro.getInt("id"));
                    if (registro.has(ProductoDataBaseHelper.CAMPO_NOMBRE)) producto.setNombre(registro.getString(ProductoDataBaseHelper.CAMPO_NOMBRE)); else producto.setNombre("");
                    if (registro.has(ProductoDataBaseHelper.CAMPO_DESCRIPCION)) producto.setDescripcion(registro.getString(ProductoDataBaseHelper.CAMPO_DESCRIPCION)); else producto.setDescripcion("");
                    if (registro.has(ProductoDataBaseHelper.CAMPO_IMAGEN)) producto.setImagen(registro.getString(ProductoDataBaseHelper.CAMPO_IMAGEN)); else producto.setImagen("");
                    if (registro.has(ProductoDataBaseHelper.CAMPO_PRECIO)) producto.setPrecio(registro.getInt(ProductoDataBaseHelper.CAMPO_PRECIO)); else producto.setPrecio(0);
                    if (registro.has(ProductoDataBaseHelper.CAMPO_STOCK)) producto.setStock(registro.getInt(ProductoDataBaseHelper.CAMPO_STOCK)); else producto.setStock(0);
                    if (registro.has(ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO)) producto.setCodigoexterno(registro.getString(ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO)); else producto.setCodigoexterno("");
                    listadoProductos.add(producto);
                    producto = new Producto();
                }//endfor

            }else {
                Log.d("ProductoParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("ProductoParser: ", e.getMessage().toString());
        }

        return getProducto();
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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

    public JSONObject getProductojson() {
        return productojson;
    }

    public void setProductojson(JSONObject productojson) {
        this.productojson = productojson;
    }

    public ArrayList<Producto> getListadoProductos() {
        return listadoProductos;
    }

    public void setListadoProductos(ArrayList<Producto> listadoProductos) {
        this.listadoProductos = listadoProductos;
    }
}
