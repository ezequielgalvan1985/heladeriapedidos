package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import adaptivex.pedidoscloud.Model.Persona;
import adaptivex.pedidoscloud.Model.PersonaDataBaseHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PersonaController
{
    private Context context;
    private PersonaDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public PersonaController(Context context)
    {
        this.context = context;
    }

    public PersonaController abrir() throws SQLiteException
    {
        dbHelper = new PersonaDataBaseHelper(context);
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

    public long agregar(Persona persona)
    {
        ContentValues valores = new ContentValues();
        valores.put(PersonaDataBaseHelper.CAMPO_APELLIDO, persona.getApellido());
        valores.put(PersonaDataBaseHelper.CAMPO_NOMBRE, persona.getNombre());
        valores.put(PersonaDataBaseHelper.CAMPO_SEXO, persona.getSexo());
        valores.put(PersonaDataBaseHelper.CAMPO_FECHA_NACIMIENTO, persona.getFechanacimiento());
        valores.put(PersonaDataBaseHelper.CAMPO_EMAIL, persona.getEmail());
        valores.put(PersonaDataBaseHelper.CAMPO_TELEFONO, persona.getTelefono());
        return db.insert(PersonaDataBaseHelper.TABLE_NAME, null, valores);
    }
    public void modificar(Persona persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        ContentValues valores = new ContentValues();
        valores.put(PersonaDataBaseHelper.CAMPO_APELLIDO, persona.getApellido());
        valores.put(PersonaDataBaseHelper.CAMPO_NOMBRE, persona.getNombre());
        valores.put(PersonaDataBaseHelper.CAMPO_SEXO, persona.getSexo());
        valores.put(PersonaDataBaseHelper.CAMPO_FECHA_NACIMIENTO,
                persona.getFechanacimiento());
        valores.put(PersonaDataBaseHelper.CAMPO_EMAIL, persona.getEmail());
        valores.put(PersonaDataBaseHelper.CAMPO_TELEFONO, persona.getTelefono());
        db.update(PersonaDataBaseHelper.TABLE_NAME, valores,
                PersonaDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public void eliminar(Persona persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(PersonaDataBaseHelper.TABLE_NAME,
                PersonaDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public Cursor obtenerTodos()
    {
        String[] campos = {PersonaDataBaseHelper.CAMPO_ID,
                PersonaDataBaseHelper.CAMPO_APELLIDO,
                PersonaDataBaseHelper.CAMPO_NOMBRE,
                PersonaDataBaseHelper.CAMPO_SEXO,
                PersonaDataBaseHelper.CAMPO_FECHA_NACIMIENTO,
                PersonaDataBaseHelper.CAMPO_EMAIL,
                PersonaDataBaseHelper.CAMPO_TELEFONO
        };
        Cursor resultado = db.query(PersonaDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public Persona buscar(int id)
    {
        Persona persona = null;
        String[] campos = {PersonaDataBaseHelper.CAMPO_ID,
                PersonaDataBaseHelper.CAMPO_APELLIDO,
                PersonaDataBaseHelper.CAMPO_NOMBRE,
                PersonaDataBaseHelper.CAMPO_SEXO,
                PersonaDataBaseHelper.CAMPO_FECHA_NACIMIENTO,
                PersonaDataBaseHelper.CAMPO_EMAIL,
                PersonaDataBaseHelper.CAMPO_TELEFONO
        };
        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(PersonaDataBaseHelper.TABLE_NAME, campos,
                PersonaDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            persona = new Persona();
            persona.setId(resultado.getInt(resultado.getColumnIndex(PersonaDataBaseHelper.CAMPO_ID)));
            persona.setApellido(resultado.getString(resultado.getColumnIndex(PersonaDataBaseHelper.CAMPO_APELLIDO)));
            persona.setNombre(resultado.getString(resultado.getColumnIndex(PersonaDataBaseHelper.CAMPO_NOMBRE)));
            persona.setSexo(resultado.getInt(resultado.getColumnIndex(PersonaDataBaseHelper.CAMPO_SEXO)));
            persona.setFechanacimiento(resultado.getString(resultado.getColumnIndex(PersonaDataBaseHelper.CAMPO_FECHA_NACIMIENTO)));
            persona.setEmail(resultado.getString(resultado.getColumnIndex(PersonaDataBaseHelper.CAMPO_EMAIL)));
            persona.setTelefono(resultado.getString(resultado.getColumnIndex(PersonaDataBaseHelper.CAMPO_TELEFONO)));
        }
        return persona;

    }
    public void limpiar()
    {
        db.delete(PersonaDataBaseHelper.TABLE_NAME, null, null);
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