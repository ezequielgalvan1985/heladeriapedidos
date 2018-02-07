package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import adaptivex.pedidoscloud.Model.Parameter;
import adaptivex.pedidoscloud.Model.ParameterDataBaseHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class ParameterController
{
    private Context context;
    private ParameterDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public ParameterController(Context context)
    {
        this.context = context;
    }

    public ParameterController abrir() throws SQLiteException
    {
        dbHelper = new ParameterDataBaseHelper(context);
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

    public long agregar(Parameter item)
    {
        ContentValues valores = new ContentValues();
        valores.put(ParameterDataBaseHelper.ID, item.getId());
        valores.put(ParameterDataBaseHelper.NOMBRE, item.getNombre());
        valores.put(ParameterDataBaseHelper.DESCRIPCION, item.getDescripcion());
        valores.put(ParameterDataBaseHelper.VALOR_TEXTO, item.getValor_texto());
        valores.put(ParameterDataBaseHelper.VALOR_INTEGER, item.getValor_integer());
        valores.put(ParameterDataBaseHelper.VALOR_DECIMAL, item.getValor_decimal());
        return db.insert(ParameterDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(Parameter item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        ContentValues valores = new ContentValues();
        valores.put(ParameterDataBaseHelper.ID, item.getId());
        valores.put(ParameterDataBaseHelper.NOMBRE, item.getNombre());
        valores.put(ParameterDataBaseHelper.DESCRIPCION, item.getDescripcion());
        valores.put(ParameterDataBaseHelper.VALOR_TEXTO, item.getValor_texto());
        valores.put(ParameterDataBaseHelper.VALOR_INTEGER, item.getValor_integer());
        valores.put(ParameterDataBaseHelper.VALOR_DECIMAL, item.getValor_decimal());
       // valores.put(ParameterDataBaseHelper.VALOR_FECHA, item.getValor_fecha().toString());
        db.update(ParameterDataBaseHelper.TABLE_NAME, valores,
                ParameterDataBaseHelper.ID + " = ?", argumentos);
    }
    public void eliminar(Parameter persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(ParameterDataBaseHelper.TABLE_NAME,
                ParameterDataBaseHelper.ID + " = ?", argumentos);
    }
    public Cursor obtenerTodos()
    {
        String[] campos = {
                ParameterDataBaseHelper.ID,
                ParameterDataBaseHelper.DESCRIPCION,
                ParameterDataBaseHelper.NOMBRE,
                ParameterDataBaseHelper.VALOR_INTEGER,
                ParameterDataBaseHelper.VALOR_DECIMAL,
                ParameterDataBaseHelper.VALOR_TEXTO
                //      ParameterDataBaseHelper.VALOR_FECHA
        };
        Cursor resultado = db.query(ParameterDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public Parameter findById(int id)
    {
        try{
            Parameter registro = null;
            String[] campos = {
                    ParameterDataBaseHelper.ID,
                    ParameterDataBaseHelper.DESCRIPCION,
                    ParameterDataBaseHelper.NOMBRE,
                    ParameterDataBaseHelper.VALOR_INTEGER,
                    ParameterDataBaseHelper.VALOR_DECIMAL,
                    ParameterDataBaseHelper.VALOR_TEXTO,

            };
            String[] argumentos = {String.valueOf(id)};
            Cursor resultado = db.query(ParameterDataBaseHelper.TABLE_NAME, campos,
                    ParameterDataBaseHelper.ID + " = ?", argumentos, null, null, null);


            if (resultado != null)
            {
                resultado.moveToFirst();
                registro = new Parameter();
                registro.setId(resultado.getInt(resultado.getColumnIndex(ParameterDataBaseHelper.ID)));
                registro.setNombre(resultado.getString(resultado.getColumnIndex(ParameterDataBaseHelper.NOMBRE)));
                registro.setValor_texto(resultado.getString(resultado.getColumnIndex(ParameterDataBaseHelper.VALOR_TEXTO)));
                registro.setValor_integer(resultado.getInt(resultado.getColumnIndex(ParameterDataBaseHelper.VALOR_INTEGER)));
                registro.setValor_decimal(resultado.getDouble(resultado.getColumnIndex(ParameterDataBaseHelper.VALOR_DECIMAL)));
                registro.setDescripcion(resultado.getString(resultado.getColumnIndex(ParameterDataBaseHelper.DESCRIPCION)));
            }
            return registro;
        }catch (Exception e){
            Log.d("findByID", e.getMessage());
            return null;
        }
    }

    public Parameter findByNombre(String nombre)
    {
        Parameter registro = null;
        try{

            String[] campos = {
                    ParameterDataBaseHelper.ID,
                    ParameterDataBaseHelper.DESCRIPCION,
                    ParameterDataBaseHelper.NOMBRE,
                    ParameterDataBaseHelper.VALOR_INTEGER,
                    ParameterDataBaseHelper.VALOR_DECIMAL,
                    ParameterDataBaseHelper.VALOR_TEXTO
                    };


           String select  = "SELECT " +
                   ParameterDataBaseHelper.ID + ", "+
                   ParameterDataBaseHelper.DESCRIPCION + ", "+
                   ParameterDataBaseHelper.NOMBRE + ", "+
                   ParameterDataBaseHelper.VALOR_INTEGER + ", "+
                   ParameterDataBaseHelper.VALOR_DECIMAL + ", "+
                   ParameterDataBaseHelper.VALOR_TEXTO;
           String from = " FROM " + ParameterDataBaseHelper.TABLE_NAME;
           String where = " WHERE " +  ParameterDataBaseHelper.NOMBRE + " = '"+ nombre + "'";
           String query = select + from + where;
           Cursor resultado =  db.rawQuery(query,null);

            if (resultado != null)
            {
                if (resultado.getCount() > 0 ){
                    resultado.moveToFirst();
                    registro = new Parameter();
                    registro.setId(resultado.getInt(resultado.getColumnIndex(ParameterDataBaseHelper.ID)));
                    registro.setNombre(resultado.getString(resultado.getColumnIndex(ParameterDataBaseHelper.NOMBRE)));
                    registro.setValor_texto(resultado.getString(resultado.getColumnIndex(ParameterDataBaseHelper.VALOR_TEXTO)));
                    registro.setValor_integer(resultado.getInt(resultado.getColumnIndex(ParameterDataBaseHelper.VALOR_INTEGER)));
                    registro.setValor_decimal(resultado.getDouble(resultado.getColumnIndex(ParameterDataBaseHelper.VALOR_DECIMAL)));

                    registro.setDescripcion(resultado.getString(resultado.getColumnIndex(ParameterDataBaseHelper.DESCRIPCION)));
                    return registro;
                }
            }
        }catch (Exception e ){
            Log.d("ParameterController",e.getMessage());
            return null;
        }
        return registro;

    }
    public void limpiar()
    {
        db.delete(ParameterDataBaseHelper.TABLE_NAME, null, null);
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