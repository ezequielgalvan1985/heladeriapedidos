package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Model.Memo;
import adaptivex.pedidoscloud.Model.MemoDataBaseHelper;
import adaptivex.pedidoscloud.Model.Producto;

import java.util.ArrayList;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class MemoController
{
    private Context context;
    private MemoDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public MemoController(Context context)
    {
        this.context = context;
    }

    public MemoController abrir() throws SQLiteException
    {
        dbHelper = new MemoDataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public Integer count(){
        try{
            return abrir().obtenerTodos().getCount();
        }catch(Exception e ){
            Log.d("Clientes:", e.getMessage());
            return null;
        }

    }
    public void cerrar()
    {
        if ( db != null )
        {
            db.close();
        }
    }

    public long agregar(Memo item)
    {
        ContentValues valores = new ContentValues();
        valores.put(MemoDataBaseHelper.CAMPO_CLIENTE_ID, item.getCliente_id());
        valores.put(MemoDataBaseHelper.CAMPO_PRODUCTO_ID, item.getProducto_id());
        valores.put(MemoDataBaseHelper.CAMPO_CANTIDAD, item.getCantidad());
        return db.insert(MemoDataBaseHelper.TABLE_NAME, null, valores);
    }



    public Cursor obtenerTodos()
    {
        String[] campos = {
                MemoDataBaseHelper.CAMPO_CLIENTE_ID,
                MemoDataBaseHelper.CAMPO_PRODUCTO_ID,
                MemoDataBaseHelper.CAMPO_CANTIDAD,

        };
        Cursor resultado = db.query(MemoDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public ArrayList<Producto> findByClienteId(int cliente_id){
        try{
            ProductoController pc =new ProductoController(context);

            ArrayList<Producto> arrayOfProductos = new ArrayList<Producto>();
            Memo registro = null;
            String select = " SELECT "+ MemoDataBaseHelper.CAMPO_PRODUCTO_ID;
            String from = " FROM "+MemoDataBaseHelper.TABLE_NAME;
            String where = " WHERE "+ MemoDataBaseHelper.CAMPO_CLIENTE_ID + " = " + String.valueOf(cliente_id);
            String query = select + from + where;
            Cursor c = db.rawQuery(query , null);
            int producto_id= 0;
            if (c != null && c.getCount()>0)
            {
                for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    producto_id = c.getInt(c.getColumnIndex(MemoDataBaseHelper.CAMPO_PRODUCTO_ID));
                    Producto p = pc.abrir().buscar(producto_id);
                    arrayOfProductos.add(p);
                    pc.cerrar();
                }
            }
            return arrayOfProductos;
        }catch(Exception e){
            Toast.makeText(this.context, "Error: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void limpiar()
    {
        db.delete(MemoDataBaseHelper.TABLE_NAME, null, null);
    }
    public void beginTransaction()
    {
        if ( db != null )
            db.beginTransaction();
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