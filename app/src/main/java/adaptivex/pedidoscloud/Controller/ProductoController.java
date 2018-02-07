package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Core.ParameterHelper;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.Model.ProductoDataBaseHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class ProductoController
{
    private Context context;
    private ProductoDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public ProductoController(Context context)
    {
        this.context = context;
    }

    public ProductoController abrir() throws SQLiteException
    {
        dbHelper = new ProductoDataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        if ( db != null )
        {
            db.close();
        }
    }

    public Integer count(){
        try{
            return abrir().obtenerTodos().getCount();
        }catch(Exception e ){
            Log.d("Productos:", e.getMessage());
            return null;
        }

    }

    public long agregar(Producto item)
    {
        //checkServiceWorking();
        ContentValues valores = new ContentValues();
        valores.put(ProductoDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(ProductoDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
        valores.put(ProductoDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());
        valores.put(ProductoDataBaseHelper.CAMPO_IMAGEN, item.getImagen());
        valores.put(ProductoDataBaseHelper.CAMPO_IMAGENURL, item.getImagenurl());
        valores.put(ProductoDataBaseHelper.CAMPO_PRECIO, item.getPrecio());
        valores.put(ProductoDataBaseHelper.CAMPO_STOCK, item.getStock());
        valores.put(ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO, item.getCodigoexterno());
        return db.insert(ProductoDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(Producto item)
    {
        //checkServiceWorking();
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        ContentValues valores = new ContentValues();

        valores.put(ProductoDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
        valores.put(ProductoDataBaseHelper.CAMPO_PRECIO, item.getPrecio());
        valores.put(ProductoDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());
        valores.put(ProductoDataBaseHelper.CAMPO_IMAGEN, item.getImagen());
        valores.put(ProductoDataBaseHelper.CAMPO_IMAGENURL, item.getImagenurl());
        valores.put(ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO, item.getCodigoexterno());
        valores.put(ProductoDataBaseHelper.CAMPO_STOCK, item.getStock());

        db.update(ProductoDataBaseHelper.TABLE_NAME, valores,
                ProductoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public void eliminar(Producto persona)
    {
        //checkServiceWorking();
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(ProductoDataBaseHelper.TABLE_NAME,
                ProductoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }

    public void checkServiceWorking(){
        try {
            ParameterHelper ph = new ParameterHelper(context);
            while (ph.isServiceStockPrecioWorking()) {
                Toast.makeText(context, "La Aplicación esta actualizando Stocks y Precios, Aguarde un momento por favor...", Toast.LENGTH_SHORT).show();
                Thread.sleep(2000);
            }
        }catch (Exception e ){
            Toast.makeText(context, "Error " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor obtenerTodos()
    {
        try{
            //checkServiceWorking();
            String[] campos = {ProductoDataBaseHelper.CAMPO_ID,
                    ProductoDataBaseHelper.CAMPO_NOMBRE,
                    ProductoDataBaseHelper.CAMPO_DESCRIPCION,
                    ProductoDataBaseHelper.CAMPO_IMAGEN,
                    ProductoDataBaseHelper.CAMPO_IMAGENURL,
                    ProductoDataBaseHelper.CAMPO_PRECIO,
                    ProductoDataBaseHelper.CAMPO_STOCK,
                    ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO

            };

            Cursor resultado = db.query(ProductoDataBaseHelper.TABLE_NAME, campos,
                    null, null, null, null, null);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e ){
            Toast.makeText(context, "Error " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    public Cursor findByNombre(String pNombre){
        //checkServiceWorking();

        String[] campos = {ProductoDataBaseHelper.CAMPO_ID,
                ProductoDataBaseHelper.CAMPO_NOMBRE,
                ProductoDataBaseHelper.CAMPO_DESCRIPCION,
                ProductoDataBaseHelper.CAMPO_IMAGEN,
                ProductoDataBaseHelper.CAMPO_IMAGENURL,
                ProductoDataBaseHelper.CAMPO_PRECIO,
                ProductoDataBaseHelper.CAMPO_STOCK,
                ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO
        };

        String[] argumentos = {"%"+ String.valueOf(pNombre)+"%"};
        Cursor resultado = db.query(ProductoDataBaseHelper.TABLE_NAME, campos, ProductoDataBaseHelper.CAMPO_NOMBRE +" LIKE ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public Producto buscar(int id)
    {

        try {
            // validar si producto esta siendo modificado
            //checkServiceWorking();
            Producto registro = null;
            String[] campos = {ProductoDataBaseHelper.CAMPO_ID,
                    ProductoDataBaseHelper.CAMPO_NOMBRE,
                    ProductoDataBaseHelper.CAMPO_DESCRIPCION,
                    ProductoDataBaseHelper.CAMPO_IMAGEN,
                    ProductoDataBaseHelper.CAMPO_IMAGENURL,
                    ProductoDataBaseHelper.CAMPO_PRECIO,
                    ProductoDataBaseHelper.CAMPO_STOCK,
                    ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO
            };
            String[] argumentos = {String.valueOf(id)};
            Cursor resultado = db.query(ProductoDataBaseHelper.TABLE_NAME, campos,
                    ProductoDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, null);
            if (resultado != null) {
                resultado.moveToFirst();
                registro = new Producto();
                Log.d("Debug: PProductoID ", String.valueOf(id));
                Log.d("Debug: ProductoVal ", String.valueOf(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ID)));
                registro.setId(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ID)));
                registro.setNombre(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_NOMBRE)));
                registro.setDescripcion(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_DESCRIPCION)));
                registro.setImagen(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_IMAGEN)));
                registro.setImagenurl(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_IMAGENURL)));
                registro.setPrecio(resultado.getFloat(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_PRECIO)));
                registro.setStock(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_STOCK)));
                registro.setCodigoexterno(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO)));
            }
            return registro;
        }catch(Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public Producto findByCodigoExterno(String codigoExterno)
    {
        try{
            //checkServiceWorking();
            Producto registro = null;
            String[] campos = {ProductoDataBaseHelper.CAMPO_ID,
                    ProductoDataBaseHelper.CAMPO_NOMBRE,
                    ProductoDataBaseHelper.CAMPO_DESCRIPCION,
                    ProductoDataBaseHelper.CAMPO_IMAGEN,
                    ProductoDataBaseHelper.CAMPO_IMAGENURL,
                    ProductoDataBaseHelper.CAMPO_PRECIO,
                    ProductoDataBaseHelper.CAMPO_STOCK,
                    ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO
            };
            String[] argumentos = {codigoExterno};
            String where =ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO + "=?";

            Cursor resultado = db.query(ProductoDataBaseHelper.TABLE_NAME, campos, where, argumentos, null, null, null);
            if (resultado != null)
            {
                if (resultado.getCount()> 0 ){
                    resultado.moveToFirst();
                    registro = new Producto();
                    registro.setId(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ID)));
                    registro.setNombre(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_NOMBRE)));
                    registro.setDescripcion(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_DESCRIPCION)));
                    registro.setImagen(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_IMAGEN)));
                    registro.setImagenurl(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_IMAGENURL)));
                    registro.setPrecio(resultado.getFloat(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_PRECIO)));
                    registro.setStock(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_STOCK)));
                    registro.setCodigoexterno(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO)));
                }
            }
            return registro;
        }catch(Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void limpiar()
    {
        db.delete(ProductoDataBaseHelper.TABLE_NAME, null, null);
    }
    public void beginTransaction()
    {
        if ( db != null )
        {
            db.beginTransaction();
        }
    }
    public void flush()
    {
        if ( db != null )
        {
            db.setTransactionSuccessful();
        }
    }
    public void commit()
    {
        if ( db != null )
        {
            db.endTransaction();
        }
    }
}