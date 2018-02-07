package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import adaptivex.pedidoscloud.Model.Hojarutadetalle;
import adaptivex.pedidoscloud.Model.HojarutadetalleDataBaseHelper;

import java.util.ArrayList;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class HojarutadetalleController
{
    private Context context;
    private HojarutadetalleDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public HojarutadetalleController(Context context)
    {
        this.context = context;
    }

    public HojarutadetalleController abrir() throws SQLiteException
    {
        dbHelper = new HojarutadetalleDataBaseHelper(context);
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

    public long agregar(Hojarutadetalle item)
    {
        ContentValues valores = new ContentValues();

        valores.put(HojarutadetalleDataBaseHelper.ID, item.getId());
        valores.put(HojarutadetalleDataBaseHelper.HOJARUTA_ID, item.getHojaruta_id());
        valores.put(HojarutadetalleDataBaseHelper.CLIENTE_ID, item.getCliente_id());
        valores.put(HojarutadetalleDataBaseHelper.HORA, item.getHora());
        valores.put(HojarutadetalleDataBaseHelper.NOTAS, item.getNotas());

        return db.insert(HojarutadetalleDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(Hojarutadetalle item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        ContentValues valores = new ContentValues();
        valores.put(HojarutadetalleDataBaseHelper.ID, item.getId());
        valores.put(HojarutadetalleDataBaseHelper.HOJARUTA_ID, item.getHojaruta_id());
        valores.put(HojarutadetalleDataBaseHelper.CLIENTE_ID, item.getCliente_id());
        valores.put(HojarutadetalleDataBaseHelper.HORA, item.getHora());
        valores.put(HojarutadetalleDataBaseHelper.NOTAS, item.getNotas());

        db.update(HojarutadetalleDataBaseHelper.TABLE_NAME, valores,
                HojarutadetalleDataBaseHelper.ID + " = ?", argumentos);
    }
    public void eliminar(Hojarutadetalle item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        db.delete(HojarutadetalleDataBaseHelper.TABLE_NAME,
                HojarutadetalleDataBaseHelper.ID + " = ?", argumentos);
    }

    //DEVUELVE UN LISTADO DE ITEMS DE DETALLES PARA UNA HOJA DE RUTA
    public ArrayList<Hojarutadetalle> findByHojarutaId(int pHojarutaId){
        ArrayList<Hojarutadetalle> items = new ArrayList<Hojarutadetalle>();
        Hojarutadetalle registro = null;
        String[] campos = {
                HojarutadetalleDataBaseHelper.ID,
                HojarutadetalleDataBaseHelper.HOJARUTA_ID,
                HojarutadetalleDataBaseHelper.CLIENTE_ID,
                HojarutadetalleDataBaseHelper.HORA,
                HojarutadetalleDataBaseHelper.NOTAS
        };
        String[] argumentos = {String.valueOf(pHojarutaId)};
        Cursor resultado = db.query(HojarutadetalleDataBaseHelper.TABLE_NAME, campos,
                HojarutadetalleDataBaseHelper.HOJARUTA_ID + " = ?", argumentos, null, null, null);
        while (resultado.moveToNext())
        {
            registro = new Hojarutadetalle();
            registro.setId(resultado.getInt(resultado.getColumnIndex(HojarutadetalleDataBaseHelper.ID)));
            registro.setHojaruta_id(resultado.getInt(resultado.getColumnIndex(HojarutadetalleDataBaseHelper.HOJARUTA_ID)));
            registro.setCliente_id(resultado.getInt(resultado.getColumnIndex(HojarutadetalleDataBaseHelper.CLIENTE_ID)));
            registro.setHora(resultado.getString(resultado.getColumnIndex(HojarutadetalleDataBaseHelper.HORA)));
            registro.setNotas(resultado.getString(resultado.getColumnIndex(HojarutadetalleDataBaseHelper.NOTAS)));
            items.add(registro);
        }
        return items;
    }

    public Cursor obtenerTodos()
    {
        String[] campos = {
                HojarutadetalleDataBaseHelper.ID,
                HojarutadetalleDataBaseHelper.HOJARUTA_ID,
                HojarutadetalleDataBaseHelper.CLIENTE_ID,
                HojarutadetalleDataBaseHelper.HORA,
                HojarutadetalleDataBaseHelper.NOTAS

        };
        Cursor resultado = db.query(HojarutadetalleDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }
    public Integer count(){
        try{
            return abrir().obtenerTodos().getCount();
        }catch(Exception e ){
            Log.d("Hojarutacontroller:", e.getMessage());
            return null;
        }
    }

    public Hojarutadetalle buscar(int id)
    {
        Hojarutadetalle registro = null;
        String[] campos = {
                HojarutadetalleDataBaseHelper.ID,
                HojarutadetalleDataBaseHelper.HOJARUTA_ID,
                HojarutadetalleDataBaseHelper.CLIENTE_ID,
                HojarutadetalleDataBaseHelper.HORA,
                HojarutadetalleDataBaseHelper.NOTAS
        };
        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(HojarutadetalleDataBaseHelper.TABLE_NAME, campos,
                HojarutadetalleDataBaseHelper.ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new Hojarutadetalle();
            registro.setId(resultado.getInt(resultado.getColumnIndex(HojarutadetalleDataBaseHelper.ID)));
            registro.setHojaruta_id(resultado.getInt(resultado.getColumnIndex(HojarutadetalleDataBaseHelper.HOJARUTA_ID)));
            registro.setCliente_id(resultado.getInt(resultado.getColumnIndex(HojarutadetalleDataBaseHelper.CLIENTE_ID)));
            registro.setHora(resultado.getString(resultado.getColumnIndex(HojarutadetalleDataBaseHelper.HORA)));
            registro.setNotas(resultado.getString(resultado.getColumnIndex(HojarutadetalleDataBaseHelper.NOTAS)));
        }
        return registro;

    }
    public void limpiar()
    {
        db.delete(HojarutadetalleDataBaseHelper.TABLE_NAME, null, null);
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