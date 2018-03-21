package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Model.Promo;
import adaptivex.pedidoscloud.Model.PromoDataBaseHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PromoController
{
    private Context context;
    private PromoDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public PromoController(Context context)
    {
        this.context = context;
    }

    public PromoController abrir() throws SQLiteException
    {
        dbHelper = new PromoDataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public Integer count(){
        try{
            return abrir().findAll().getCount();
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

    public long add(Promo item)
    {
        ContentValues valores = new ContentValues();

        valores.put(PromoDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(PromoDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());
        valores.put(PromoDataBaseHelper.CAMPO_FECHA_DESDE, item.getFechaDesde());
        valores.put(PromoDataBaseHelper.CAMPO_FECHA_HASTA, item.getFechaHasta());
        valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS, item.getCantKilos());
        valores.put(PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO, item.getImporteDescuento());
        valores.put(PromoDataBaseHelper.CAMPO_PRECIO_PROMO, item.getPrecioPromo());

        return db.insert(PromoDataBaseHelper.TABLE_NAME, null, valores);
    }



    public void edit(Promo item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getIdAndroid())};

        ContentValues valores = new ContentValues();

        valores.put(PromoDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(PromoDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());
        valores.put(PromoDataBaseHelper.CAMPO_FECHA_DESDE, item.getFechaDesde());
        valores.put(PromoDataBaseHelper.CAMPO_FECHA_HASTA, item.getFechaHasta());
        valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS, item.getCantKilos());
        valores.put(PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO, item.getImporteDescuento());
        valores.put(PromoDataBaseHelper.CAMPO_PRECIO_PROMO, item.getPrecioPromo());

        db.update(PromoDataBaseHelper.TABLE_NAME, valores,
                PromoDataBaseHelper.CAMPO_ID_ANDROID + " = ?", argumentos);
    }



    public void delete(Promo promo)
    {
        String[] argumentos = new String[]
                {String.valueOf(promo.getIdAndroid())};
        db.delete(PromoDataBaseHelper.TABLE_NAME,
                PromoDataBaseHelper.CAMPO_ID_ANDROID + " = ?", argumentos);
    }



    public Cursor findAll()
    {
        String[] campos = {
                PromoDataBaseHelper.CAMPO_ID,
                PromoDataBaseHelper.CAMPO_DESCRIPCION,
                PromoDataBaseHelper.CAMPO_FECHA_DESDE,
                PromoDataBaseHelper.CAMPO_FECHA_HASTA,
                PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS,
                PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO,
                PromoDataBaseHelper.CAMPO_PRECIO_PROMO,
                PromoDataBaseHelper.CAMPO_ID_ANDROID

        };
        Cursor resultado = db.query(PromoDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }


    public Promo findByIdAndroid(Integer idAndroid)
    {
        Promo registro = null;
        String[] campos = {
                PromoDataBaseHelper.CAMPO_ID,
                PromoDataBaseHelper.CAMPO_DESCRIPCION,
                PromoDataBaseHelper.CAMPO_FECHA_DESDE,
                PromoDataBaseHelper.CAMPO_FECHA_HASTA,
                PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS,
                PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO,
                PromoDataBaseHelper.CAMPO_PRECIO_PROMO,
                PromoDataBaseHelper.CAMPO_ID_ANDROID
        };
        String[] argumentos = {String.valueOf(idAndroid)};

        Cursor resultado = db.query(PromoDataBaseHelper.TABLE_NAME, campos,
                PromoDataBaseHelper.CAMPO_ID_ANDROID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = parseObjectFromRecord(resultado);
        }
        return registro;

    }

    public ArrayList<Promo> parseCursorToArrayList(Cursor c){
        try{
            ArrayList<Promo> lista = new ArrayList<Promo>();
            //Recibe cursor y completa el arralist de pedidodetalles
            Promo registro;
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                registro = new Promo();

                registro = parseObjectFromRecord(c);
                /*
                registro.setIdAndroid(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_ID_ANDROID)));
                registro.setId(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_ID)));
                registro.setNombre(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_NOMBRE)));
                registro.setDescripcion(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_DESCRIPCION)));
                registro.setFechaDesde(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_FECHA_DESDE)));
                registro.setFechaHasta(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_FECHA_HASTA)));
                registro.setImporteDescuento(c.getDouble(c.getColumnIndex(PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO)));
                registro.setPrecioPromo(c.getDouble(c.getColumnIndex(PromoDataBaseHelper.CAMPO_PRECIO_PROMO)));
                */
                lista.add(registro);
                registro = null;
            }
            return lista;
        }catch(Exception e){
            Toast.makeText(context,"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public Promo parseObjectFromRecord(Cursor c ){
        try{
            Promo object = new Promo();
            object.setIdAndroid(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_ID_ANDROID)));
            object.setId(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_ID)));
            object.setNombre(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_NOMBRE)));
            object.setDescripcion(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_DESCRIPCION)));
            object.setFechaDesde(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_FECHA_DESDE)));
            object.setFechaHasta(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_FECHA_HASTA)));
            object.setImporteDescuento(c.getDouble(c.getColumnIndex(PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO)));
            object.setPrecioPromo(c.getDouble(c.getColumnIndex(PromoDataBaseHelper.CAMPO_PRECIO_PROMO)));
            return object;
        }catch(Exception e){
            Toast.makeText(context,"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public void limpiar()
    {
        db.delete(PromoDataBaseHelper.TABLE_NAME, null, null);
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
