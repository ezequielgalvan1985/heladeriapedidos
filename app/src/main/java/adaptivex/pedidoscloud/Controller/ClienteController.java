package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import adaptivex.pedidoscloud.Model.Cliente;
import adaptivex.pedidoscloud.Model.ClienteDataBaseHelper;

import java.util.ArrayList;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class ClienteController
{
    private Context context;
    private ClienteDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public ClienteController(Context context)
    {
        this.context = context;
    }

    public ClienteController abrir() throws SQLiteException
    {
        dbHelper = new ClienteDataBaseHelper(context);
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
            Log.d("Clientes:", e.getMessage());
            return null;
        }

    }
    public ArrayList<Cliente> parseCursorToArray(Cursor c) {
        try{
            ArrayList<Cliente> al = new ArrayList<Cliente>();
            while (c.moveToNext()) {
                Cliente registro = new Cliente();
                registro.setId(c.getInt(c.getColumnIndex(ClienteDataBaseHelper.CAMPO_ID)));
                registro.setContacto(c.getString(c.getColumnIndex(ClienteDataBaseHelper.CAMPO_CONTACTO)));
                registro.setNdoc(c.getString(c.getColumnIndex(ClienteDataBaseHelper.CAMPO_NDOC)));
                registro.setRazonsocial(c.getString(c.getColumnIndex(ClienteDataBaseHelper.CAMPO_RAZONSOCIAL)));
                registro.setDireccion(c.getString(c.getColumnIndex(ClienteDataBaseHelper.CAMPO_DIRECCION)));
                registro.setTelefono(c.getString(c.getColumnIndex(ClienteDataBaseHelper.CAMPO_TELEFONO)));
                registro.setEmail(c.getString(c.getColumnIndex(ClienteDataBaseHelper.CAMPO_EMAIL)));
                al.add(registro);
                registro = null;
            }
            return al;
        }catch(Exception e ){
            Log.d("ClienteController", e.getMessage());
            return null;
        }
    }
    public long agregar(Cliente item)
    {
        ContentValues valores = new ContentValues();
        valores.put(ClienteDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(ClienteDataBaseHelper.CAMPO_RAZONSOCIAL, item.getRazonsocial());
        valores.put(ClienteDataBaseHelper.CAMPO_DIRECCION, item.getDireccion());
        valores.put(ClienteDataBaseHelper.CAMPO_TELEFONO, item.getTelefono());
        valores.put(ClienteDataBaseHelper.CAMPO_EMAIL, item.getEmail());
        valores.put(ClienteDataBaseHelper.CAMPO_CONTACTO, item.getContacto());
        valores.put(ClienteDataBaseHelper.CAMPO_NDOC, item.getNdoc());

        return db.insert(ClienteDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(Cliente item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        ContentValues valores = new ContentValues();

        valores.put(ClienteDataBaseHelper.CAMPO_RAZONSOCIAL, item.getRazonsocial());
        valores.put(ClienteDataBaseHelper.CAMPO_DIRECCION, item.getDireccion());
        valores.put(ClienteDataBaseHelper.CAMPO_TELEFONO, item.getTelefono());
        valores.put(ClienteDataBaseHelper.CAMPO_EMAIL, item.getEmail());
        valores.put(ClienteDataBaseHelper.CAMPO_CONTACTO, item.getContacto());
        valores.put(ClienteDataBaseHelper.CAMPO_NDOC, item.getNdoc());

        db.update(ClienteDataBaseHelper.TABLE_NAME, valores,
                ClienteDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public void eliminar(Cliente persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(ClienteDataBaseHelper.TABLE_NAME,
                ClienteDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public Cursor obtenerTodos()
    {
        String[] campos = {ClienteDataBaseHelper.CAMPO_ID,
                ClienteDataBaseHelper.CAMPO_RAZONSOCIAL,
                ClienteDataBaseHelper.CAMPO_DIRECCION,
                ClienteDataBaseHelper.CAMPO_TELEFONO,
                ClienteDataBaseHelper.CAMPO_EMAIL,
                ClienteDataBaseHelper.CAMPO_CONTACTO,
                ClienteDataBaseHelper.CAMPO_NDOC

        };
        Cursor resultado = db.query(ClienteDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
           // resultado.moveToFirst();
        }
        return resultado;
    }

    public Cliente buscar(int id)
    {
        Cliente registro = null;
        String[] campos = {ClienteDataBaseHelper.CAMPO_ID,
                ClienteDataBaseHelper.CAMPO_RAZONSOCIAL,
                ClienteDataBaseHelper.CAMPO_DIRECCION,
                ClienteDataBaseHelper.CAMPO_TELEFONO,
                ClienteDataBaseHelper.CAMPO_EMAIL,
                ClienteDataBaseHelper.CAMPO_CONTACTO,
                ClienteDataBaseHelper.CAMPO_NDOC

        };
        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(ClienteDataBaseHelper.TABLE_NAME, campos,
                ClienteDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new Cliente();
            registro.setId(resultado.getInt(resultado.getColumnIndex(ClienteDataBaseHelper.CAMPO_ID)));
            registro.setRazonsocial(resultado.getString(resultado.getColumnIndex(ClienteDataBaseHelper.CAMPO_RAZONSOCIAL)));
            registro.setDireccion(resultado.getString(resultado.getColumnIndex(ClienteDataBaseHelper.CAMPO_DIRECCION)));
            registro.setTelefono(resultado.getString(resultado.getColumnIndex(ClienteDataBaseHelper.CAMPO_TELEFONO)));
            registro.setEmail(resultado.getString(resultado.getColumnIndex(ClienteDataBaseHelper.CAMPO_EMAIL)));
            registro.setContacto(resultado.getString(resultado.getColumnIndex(ClienteDataBaseHelper.CAMPO_CONTACTO)));
            registro.setNdoc(resultado.getString(resultado.getColumnIndex(ClienteDataBaseHelper.CAMPO_NDOC)));

        }
        return registro;

    }

    public Cursor findByNombreApellido(String pTexto){
        String[] campos = {ClienteDataBaseHelper.CAMPO_ID,

                ClienteDataBaseHelper.CAMPO_RAZONSOCIAL,
                ClienteDataBaseHelper.CAMPO_DIRECCION,
                ClienteDataBaseHelper.CAMPO_TELEFONO,
                ClienteDataBaseHelper.CAMPO_EMAIL,
                ClienteDataBaseHelper.CAMPO_CONTACTO,
                ClienteDataBaseHelper.CAMPO_NDOC

        };
        String[] argumentos = {"%"+ String.valueOf(pTexto)+"%"};

        Cursor resultado = db.query(ClienteDataBaseHelper.TABLE_NAME, campos,
                ClienteDataBaseHelper.CAMPO_CONTACTO+" LIKE ? ", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;

    }

    public void limpiar()
    {

        db.delete(ClienteDataBaseHelper.TABLE_NAME, null, null);

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