package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import adaptivex.pedidoscloud.Model.Hojaruta;
import adaptivex.pedidoscloud.Model.HojarutaDataBaseHelper;
import adaptivex.pedidoscloud.Model.Hojarutadetalle;

import java.util.ArrayList;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class HojarutaController
{
    private Context context;
    private HojarutaDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public HojarutaController(Context context)
    {
        this.context = context;
    }

    public HojarutaController abrir() throws SQLiteException
    {
        dbHelper = new HojarutaDataBaseHelper(context);
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

    public long agregar(Hojaruta item)
    {
        ContentValues valores = new ContentValues();

        valores.put(HojarutaDataBaseHelper.ID, item.getId());
        valores.put(HojarutaDataBaseHelper.USER_ID, item.getUser_id());
        valores.put(HojarutaDataBaseHelper.DIA_ID, item.getDia_id());
        valores.put(HojarutaDataBaseHelper.TITULO, item.getTitulo());
        valores.put(HojarutaDataBaseHelper.NOTAS, item.getNotas());

        return db.insert(HojarutaDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(Hojaruta item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        ContentValues valores = new ContentValues();
        valores.put(HojarutaDataBaseHelper.ID, item.getId());
        valores.put(HojarutaDataBaseHelper.USER_ID, item.getUser_id());
        valores.put(HojarutaDataBaseHelper.DIA_ID, item.getDia_id());
        valores.put(HojarutaDataBaseHelper.TITULO, item.getTitulo());
        valores.put(HojarutaDataBaseHelper.NOTAS, item.getNotas());

        db.update(HojarutaDataBaseHelper.TABLE_NAME, valores,
                HojarutaDataBaseHelper.ID + " = ?", argumentos);
    }
    public void eliminar(Hojaruta item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        db.delete(HojarutaDataBaseHelper.TABLE_NAME,
                HojarutaDataBaseHelper.ID + " = ?", argumentos);
    }
    public Integer CountRows(){
        Cursor c = this.abrir().obtenerTodos();
        return c.getCount();

    }

    public Cursor obtenerTodos()
    {
        String[] campos = {
                HojarutaDataBaseHelper.ID,
                HojarutaDataBaseHelper.USER_ID,
                HojarutaDataBaseHelper.DIA_ID,
                HojarutaDataBaseHelper.TITULO,
                HojarutaDataBaseHelper.NOTAS
        };
        Cursor resultado = db.query(HojarutaDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public Hojaruta buscar(int id)
    {
        Hojaruta registro = null;
        String[] campos = {HojarutaDataBaseHelper.ID,
                HojarutaDataBaseHelper.USER_ID,
                HojarutaDataBaseHelper.DIA_ID,
                HojarutaDataBaseHelper.TITULO,
                HojarutaDataBaseHelper.NOTAS
        };
        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(HojarutaDataBaseHelper.TABLE_NAME, campos,
                HojarutaDataBaseHelper.ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new Hojaruta();
            registro.setId(resultado.getInt(resultado.getColumnIndex(HojarutaDataBaseHelper.ID)));
            registro.setUser_id(resultado.getInt(resultado.getColumnIndex(HojarutaDataBaseHelper.USER_ID)));
            registro.setDia_id(resultado.getInt(resultado.getColumnIndex(HojarutaDataBaseHelper.DIA_ID)));
            registro.setTitulo(resultado.getString(resultado.getColumnIndex(HojarutaDataBaseHelper.TITULO)));
            registro.setNotas(resultado.getString(resultado.getColumnIndex(HojarutaDataBaseHelper.NOTAS)));
        }
        return registro;

    }

    public Hojaruta findByDiaAndUser(int pDia, int pUser){
        //DEVOLVER LA HOJA DE RUTA POR DIA Y USUARIO
        try{
            Hojaruta registro = null;
            String[] campos = {HojarutaDataBaseHelper.ID,
                    HojarutaDataBaseHelper.USER_ID,
                    HojarutaDataBaseHelper.DIA_ID,
                    HojarutaDataBaseHelper.TITULO,
                    HojarutaDataBaseHelper.NOTAS
            };
            Cursor resultado = db.query(HojarutaDataBaseHelper.TABLE_NAME,
                    campos,
                    HojarutaDataBaseHelper.DIA_ID + " =? " + " AND "
                    + HojarutaDataBaseHelper.USER_ID + " =? ",
                    new String[]{String.valueOf(pDia), String.valueOf(pUser)},
                    null, null, null);
            if (resultado != null)
            {
                resultado.moveToFirst();
                registro = new Hojaruta();
                registro.setId(resultado.getInt(resultado.getColumnIndex(HojarutaDataBaseHelper.ID)));
                registro.setUser_id(resultado.getInt(resultado.getColumnIndex(HojarutaDataBaseHelper.USER_ID)));
                registro.setDia_id(resultado.getInt(resultado.getColumnIndex(HojarutaDataBaseHelper.DIA_ID)));
                registro.setTitulo(resultado.getString(resultado.getColumnIndex(HojarutaDataBaseHelper.TITULO)));
                registro.setNotas(resultado.getString(resultado.getColumnIndex(HojarutaDataBaseHelper.NOTAS)));

                //Leer detalle de la hoja de ruta
                HojarutadetalleController hrd = new HojarutadetalleController(this.getContext());
                registro.setItems(hrd.abrir().findByHojarutaId(registro.getId()));
            }

            return registro;
        }catch(Exception e ){
            Log.d("Hojarutacontroller", e.getMessage());
            return null;
        }
    }

    public ArrayList<Hojarutadetalle> findDetallesByHojarutaId(Hojaruta h){
        try{
            HojarutadetalleController hrc = new HojarutadetalleController(this.getContext());
            h.setItems(hrc.findByHojarutaId(h.getId()));
            return h.getItems();
        }catch(Exception e){
            Log.d("HOjarutaController:", e.getMessage());
            return null;
        }
    }

    public ArrayList<Hojaruta> findAllArray(){
        ArrayList<Hojaruta> hojarutas = new ArrayList<Hojaruta>();
        try{

            Cursor c = this.abrir().obtenerTodos();
            Hojaruta registro = new Hojaruta();
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                registro = new Hojaruta();
                registro.setId(c.getInt(c.getColumnIndex(HojarutaDataBaseHelper.ID)));
                registro.setDia_id(c.getInt(c.getColumnIndex(HojarutaDataBaseHelper.DIA_ID)));
                registro.setUser_id(c.getInt(c.getColumnIndex(HojarutaDataBaseHelper.USER_ID)));
                registro.setTitulo(c.getString(c.getColumnIndex(HojarutaDataBaseHelper.TITULO)));
                registro.setNotas(c.getString(c.getColumnIndex(HojarutaDataBaseHelper.NOTAS)));
                hojarutas.add(registro);
                registro = null;
            }
        }catch (Exception e){
            Log.d("Hojarutacontroller: ", e.getMessage());
        }
        return hojarutas;
    }
    public Integer count(){
        try{
            return abrir().obtenerTodos().getCount();
        }catch(Exception e ){
            Log.d("Hojarutacontroller:", e.getMessage());
            return null;
        }

    }
    public void limpiar()
    {
        db.delete(HojarutaDataBaseHelper.TABLE_NAME, null, null);
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}