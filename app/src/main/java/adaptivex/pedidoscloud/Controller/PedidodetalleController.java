package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Model.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.PedidodetalleDataBaseHelper;
import adaptivex.pedidoscloud.Model.Pote;
import adaptivex.pedidoscloud.Model.Producto;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PedidodetalleController
{
    private Context context;
    private PedidodetalleDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public PedidodetalleController(Context context)
    {
        this.context = context;
    }

    public PedidodetalleController abrir() throws SQLiteException
    {
        dbHelper = new PedidodetalleDataBaseHelper(context);
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

    public long agregar(Pedidodetalle item)
    {
        ContentValues valores = new ContentValues();
        //valores.put(PedidodetalleDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID, item.getPedidoId());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP, item.getPedidoTmpId());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID, item.getProductoId());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_CANTIDAD, item.getCantidad());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO, item.getPreciounitario());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_MONTO, item.getMonto());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID, item.getEstadoId());

        //Heladeria
        valores.put(PedidodetalleDataBaseHelper.CAMPO_NRO_POTE, item.getNroPote());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_MEDIDA_POTE, item.getMedidaPote());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PROPORCION_HELADO, item.getProporcionHelado());

        return db.insert(PedidodetalleDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(Pedidodetalle item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getIdTmp())};
        ContentValues valores = new ContentValues();
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID, item.getPedidoId());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID, item.getProductoId());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_CANTIDAD, item.getCantidad());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO, item.getPreciounitario());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_MONTO, item.getMonto());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID, item.getEstadoId());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_ID_TMP, item.getIdTmp());

        //Heladeria
        valores.put(PedidodetalleDataBaseHelper.CAMPO_NRO_POTE, item.getNroPote());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_MEDIDA_POTE, item.getMedidaPote());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PROPORCION_HELADO, item.getProporcionHelado());

        db.update(PedidodetalleDataBaseHelper.TABLE_NAME, valores,
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos);
    }
    public void eliminar(Pedidodetalle registro)
    {
        String[] argumentos = new String[]
                {String.valueOf(registro.getIdTmp())};
        db.delete(PedidodetalleDataBaseHelper.TABLE_NAME,
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos);
    }


    public void deleteByPote(Pote pote)
    {
        String[] argumentos = new String[]{String.valueOf(pote.getPedido().getIdTmp()), String.valueOf(pote.getNroPote())};

        db.delete(PedidodetalleDataBaseHelper.TABLE_NAME,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP + " = ? AND " +
                PedidodetalleDataBaseHelper.CAMPO_NRO_POTE+ " = ?"
                , argumentos);
    }




    public Cursor obtenerTodos()
    {
        String[] campos = {PedidodetalleDataBaseHelper.CAMPO_ID,
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP,
                PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID,
                PedidodetalleDataBaseHelper.CAMPO_CANTIDAD,
                PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO,
                PedidodetalleDataBaseHelper.CAMPO_MONTO,
                PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID,

        };


        Cursor resultado = db.query(PedidodetalleDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public Cursor findAll()
    {
        String[] campos = {
                PedidodetalleDataBaseHelper.CAMPO_ID,
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP,
                PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID,
                PedidodetalleDataBaseHelper.CAMPO_CANTIDAD,
                PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO,
                PedidodetalleDataBaseHelper.CAMPO_MONTO,
                PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID,

        };


        Cursor resultado = db.query(PedidodetalleDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public  ArrayList<Pedidodetalle> findAllToArrayList(){
        try{
            ArrayList<Pedidodetalle> listaPedidodetalle = new  ArrayList<Pedidodetalle>();
            Cursor c = abrir().findAll();
            listaPedidodetalle = parseCursorToArrayList(c);
            return listaPedidodetalle;
        }catch (Exception e){
            Log.e("Error: ", e.getMessage());
            return null;
        }
    }


    public ArrayList<Pedidodetalle> findByPedido_android_idToArrayList(long android_id ){
        try{
            Cursor c = abrir().findByPedidoIdTmp(android_id);
            ArrayList<Pedidodetalle> lista = parseCursorToArrayList(c);
            return lista;
        }catch (Exception e){
            Log.e("Error: ", e.getMessage());
            return null;
        }
    }

    public ArrayList<Pedidodetalle> parseCursorToArrayList(Cursor c){
        try{
            ArrayList<Pedidodetalle> lista = new ArrayList<Pedidodetalle>();

            //Recibe cursor y completa el arralist de pedidodetalles
            Pedidodetalle registro;
            Producto p = new Producto();
            ProductoController prodCtrl = new ProductoController(context);
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                registro = new Pedidodetalle();
                registro.setIdTmp(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ID_TMP)));
                registro.setId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ID)));

                registro.setPedidoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID)));
                registro.setPedidoTmpId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP)));
                //Producto
                registro.setProductoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID)));
                p = prodCtrl.abrir().buscar(registro.getProductoId());
                registro.setProducto(p);


                registro.setCantidad(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_CANTIDAD)));
                registro.setPreciounitario(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO)));
                registro.setMonto(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_MONTO)));
                registro.setEstadoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID)));

                //Nuevos Campos para heladeria
                registro.setNroPote(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_NRO_POTE)));
                registro.setProporcionHelado(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PROPORCION_HELADO)));
                registro.setMedidaPote(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_MEDIDA_POTE)));
                lista.add(registro);
                registro = null;
            }
            return lista;
        }catch(Exception e){
            Toast.makeText(context,"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public Cursor findByPedidoAndroidIdAndNroPote(long pedidoAndroidId, long nroPote){
        try{
            String[] campos = {
                    PedidodetalleDataBaseHelper.CAMPO_ID,
                    PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID,
                    PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP,
                    PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID,
                    PedidodetalleDataBaseHelper.CAMPO_CANTIDAD,
                    PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO,
                    PedidodetalleDataBaseHelper.CAMPO_MONTO,
                    PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID,
                    PedidodetalleDataBaseHelper.CAMPO_ID_TMP,
                    PedidodetalleDataBaseHelper.CAMPO_PROPORCION_HELADO,
                    PedidodetalleDataBaseHelper.CAMPO_NRO_POTE,
                    PedidodetalleDataBaseHelper.CAMPO_MEDIDA_POTE,

            };


            String[] argumentos =   {String.valueOf(pedidoAndroidId), String.valueOf(nroPote)};
            Cursor resultado = db.query(PedidodetalleDataBaseHelper.TABLE_NAME, campos,
                    PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP +"=? AND " +
                            PedidodetalleDataBaseHelper.CAMPO_NRO_POTE +"=? ",
                    argumentos, null, null, null);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e ){
            Toast.makeText(context,"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }

    }


    private Cursor findByPedidoIdTmp(long pedidoIdTmp)
    {
        String[] campos = {
                PedidodetalleDataBaseHelper.CAMPO_ID,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP,
                PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID,
                PedidodetalleDataBaseHelper.CAMPO_CANTIDAD,
                PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO,
                PedidodetalleDataBaseHelper.CAMPO_MONTO,
                PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID,
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP,
                PedidodetalleDataBaseHelper.CAMPO_NRO_POTE,
                PedidodetalleDataBaseHelper.CAMPO_PROPORCION_HELADO,
                PedidodetalleDataBaseHelper.CAMPO_MEDIDA_POTE
        };


        Cursor resultado = db.query(PedidodetalleDataBaseHelper.TABLE_NAME, campos,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP + " = "+ String.valueOf(pedidoIdTmp), null, null, null, PedidodetalleDataBaseHelper.CAMPO_NRO_POTE );
        if (resultado != null)
        {
            resultado.moveToFirst();
        }


        return resultado;
    }




    public Pedidodetalle buscar(long id)
    {
        Pedidodetalle registro = null;
        String[] campos = {PedidodetalleDataBaseHelper.CAMPO_ID,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID,
                PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID,
                PedidodetalleDataBaseHelper.CAMPO_CANTIDAD,
                PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO,
                PedidodetalleDataBaseHelper.CAMPO_MONTO,
                PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID,
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP

        };
        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(PedidodetalleDataBaseHelper.TABLE_NAME, campos,
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos, null, null, null);

        Producto producto;
        ProductoController dbProducto = new ProductoController(this.context);

        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new Pedidodetalle();
            registro.setId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ID)));
            registro.setPedidoId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID)));
            registro.setProductoId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID)));
            registro.setCantidad(resultado.getDouble(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_CANTIDAD)));
            registro.setPreciounitario(resultado.getDouble(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO)));
            registro.setMonto(resultado.getDouble(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_MONTO)));
            registro.setEstadoId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID)));
            registro.setIdTmp(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ID_TMP)));
            registro.setProducto(dbProducto.abrir().buscar(registro.getProductoId()));
            dbProducto.cerrar();

        }
        return registro;

    }


    public Pedidodetalle findByIdTmp(long idTmp)
    {
        Pedidodetalle registro = null;
        String[] campos = {PedidodetalleDataBaseHelper.CAMPO_ID,
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP,
                PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID,
                PedidodetalleDataBaseHelper.CAMPO_CANTIDAD,
                PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO,
                PedidodetalleDataBaseHelper.CAMPO_MONTO,
                PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID

        };
        String[] argumentos = {String.valueOf(idTmp)};
        Cursor resultado = db.query(PedidodetalleDataBaseHelper.TABLE_NAME, campos,
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos, null, null, null);

        Producto producto;
        ProductoController dbProducto = new ProductoController(this.context);

        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new Pedidodetalle();
            registro.setId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ID)));
            registro.setPedidoId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID)));
            registro.setPedidoTmpId(resultado.getLong(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP)));
            registro.setProductoId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID)));
            registro.setCantidad(resultado.getDouble(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_CANTIDAD)));
            registro.setPreciounitario(resultado.getDouble(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO)));
            registro.setMonto(resultado.getDouble(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_MONTO)));
            registro.setEstadoId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID)));
            registro.setIdTmp(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ID_TMP)));
            registro.setProducto(dbProducto.abrir().buscar(registro.getProductoId()));
            dbProducto.cerrar();

        }
        return registro;

    }



    public void limpiar()
    {

        db.delete(PedidodetalleDataBaseHelper.TABLE_NAME, null, null);

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