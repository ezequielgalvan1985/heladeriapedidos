package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import adaptivex.pedidoscloud.Model.Marca;
import adaptivex.pedidoscloud.Model.MarcaDataBaseHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class MarcaController
{
    private Context context;
    private MarcaDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public MarcaController(Context context)
    {
        this.context = context;
    }

    public MarcaController abrir() throws SQLiteException
    {
        dbHelper = new MarcaDataBaseHelper(context);
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

    public long agregar(Marca item)
    {
        ContentValues valores = new ContentValues();
        valores.put(MarcaDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(MarcaDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());

        return db.insert(MarcaDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(Marca item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        ContentValues valores = new ContentValues();
        valores.put(MarcaDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());

        db.update(MarcaDataBaseHelper.TABLE_NAME, valores,
                MarcaDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public void eliminar(Marca persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(MarcaDataBaseHelper.TABLE_NAME,
                MarcaDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public Cursor obtenerTodos()
    {
        String[] campos = {MarcaDataBaseHelper.CAMPO_ID,
                MarcaDataBaseHelper.CAMPO_DESCRIPCION

        };
        Cursor resultado = db.query(MarcaDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public Marca buscar(int id)
    {
        Marca registro = null;
        String[] campos = {MarcaDataBaseHelper.CAMPO_ID,

                MarcaDataBaseHelper.CAMPO_DESCRIPCION

        };
        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(MarcaDataBaseHelper.TABLE_NAME, campos,
                MarcaDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new Marca();
            registro.setId(resultado.getInt(resultado.getColumnIndex(MarcaDataBaseHelper.CAMPO_ID)));

            registro.setDescripcion(resultado.getString(resultado.getColumnIndex(MarcaDataBaseHelper.CAMPO_DESCRIPCION)));

        }
        return registro;

    }
    public void limpiar()
    {

        db.delete(MarcaDataBaseHelper.TABLE_NAME, null, null);

    }
    public void reset(){

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