package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Model.Categoria;
import adaptivex.pedidoscloud.Model.CategoriaDataBaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class CategoriaParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONArray data;
    private String status;
    private String message;
    private JSONObject categoriajson;
    private Categoria categoria;
    private ArrayList<Categoria> listadoCategorias;
    /* Solamente parsea los datos de un String Json, al Objeto CategoriaParser */
    public CategoriaParser(){
    }

    public CategoriaParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public Categoria parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoCategorias  = new ArrayList<Categoria>();
            setJsonobj(new JSONObject(getJsonstr()));

            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));

            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray categorias = getData();
                Categoria categoria = new Categoria();
                for (int i = 0; i < categorias.length(); i++) {
                    JSONObject c = categorias.getJSONObject(i);

                    categoria.setId(c.getInt("id"));
                    categoria.setNombre(c.getString(CategoriaDataBaseHelper.CAMPO_NOMBRE));
                    categoria.setDescripcion(c.getString(CategoriaDataBaseHelper.CAMPO_DESCRIPCION));
                    listadoCategorias.add(categoria);
                    categoria =  new Categoria();

                }//endfor

            }else {
                Log.d("CategoriaParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("CategoriaParser: ", e.getMessage().toString());
        }

        return getCategoria();
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    public JSONObject getCategoriajson() {
        return categoriajson;
    }

    public void setCategoriajson(JSONObject categoriajson) {
        this.categoriajson = categoriajson;
    }

    public ArrayList<Categoria> getListadoCategorias() {
        return listadoCategorias;
    }

    public void setListadoCategorias(ArrayList<Categoria> listadoCategorias) {
        this.listadoCategorias = listadoCategorias;
    }
}
