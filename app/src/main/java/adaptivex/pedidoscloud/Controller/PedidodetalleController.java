package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.PedidodetalleDataBaseHelper;
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

        db.update(PedidodetalleDataBaseHelper.TABLE_NAME, valores,
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos);
    }
    public void eliminar(Pedidodetalle registro)
    {
        String[] argumentos = new String[]
                {String.valueOf(registro.getId())};
        db.delete(PedidodetalleDataBaseHelper.TABLE_NAME,
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos);
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

    public Cursor findByPedidoIdTmp(long pedidoIdTmp)
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
                PedidodetalleDataBaseHelper.CAMPO_ID_TMP
        };


        //String[] argumentos =   {String.valueOf(pedido.getIdTmp())};
        Cursor resultado = db.query(PedidodetalleDataBaseHelper.TABLE_NAME, campos,
                PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP + " = "+ String.valueOf(pedidoIdTmp), null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        //Completar objecto que sirva de


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