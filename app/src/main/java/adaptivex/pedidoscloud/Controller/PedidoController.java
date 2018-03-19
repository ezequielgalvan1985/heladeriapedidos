package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Model.Cliente;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.PedidodetalleDataBaseHelper;
import adaptivex.pedidoscloud.Model.Pote;
import adaptivex.pedidoscloud.Model.PoteItem;

import java.util.ArrayList;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PedidoController
{
    private Context context;
    private PedidoDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public PedidoController(Context context)
    {
        this.context = context;
    }

    public PedidoController abrir() throws SQLiteException
    {
        dbHelper = new PedidoDataBaseHelper(context);
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



    public ArrayList<Pote> getPotesArrayList(long idAndroid ){
        try{
            PedidodetalleController pdc = new PedidodetalleController(context);
            //Crear arraylist de potes
            ArrayList<Pote> listaPotes = new ArrayList<Pote>();
            Pedido p = new Pedido();

            //Debe Buscar el
            //Buscar el Pedido
            p = this.abrir().findByIdTmp(idAndroid);
            //obtener total de potes
            int potesTotal = p.getCantidadPotes();
            int poteActual = 0;

            //hacer un for por cantidad total de potes
            for(int i= 1; i <= potesTotal; i++){
                poteActual = i;
                //crear un pote
                Pote pote = new Pote();
                pote.setPedido(p);

                //buscar pedido detalle por idpedido y nro pote
                Cursor c = pdc.abrir().findByPedidoAndroidIdAndNroPote(idAndroid,poteActual);
                ArrayList<Pedidodetalle> listaHeladosPote = pdc.abrir().parseCursorToArrayList(c);
                //recorrer arraylist
                for(Pedidodetalle pd: listaHeladosPote){
                    //Crear poteitem
                    pote.setNroPote(pd.getNroPote());
                    pote.setKilos(pd.getMedidaPote());
                    pote.setHeladomonto(pd.getMonto());

                    PoteItem item = new PoteItem();
                    item.setCantidad(pd.getProporcionHelado());
                    item.setProducto(pd.getProducto());
                    //agregar poteitem al pote
                    pote.addItemPote(item);
                }
                //Agregar pote al arraylist de potes
                listaPotes.add(pote);
            }
            return listaPotes;
        }catch(Exception e ){
            Toast.makeText(context, "Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }







    public ArrayList<Pedidodetalle> findByIdAndroidAndNroPote(long idAndroid, long nroPote) {

        try{
            ArrayList<Pedidodetalle> lista = new ArrayList<Pedidodetalle>();


            return lista;

        }catch(Exception e){
            Toast.makeText(context,"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public void actualizarTotales(int pedidoIdTmp){
        //PedidodetalleController dbDetalles = new PedidodetalleController(this.context);
        //Double subtotal, iva, total;
        this.abrir();

        Log.d("Deb actualizarTotales", PedidoDataBaseHelper.get_ACT_PED_TOTALES_TMP_ID(pedidoIdTmp));
        db.execSQL(PedidoDataBaseHelper.get_ACT_PED_TOTALES_TMP_ID(pedidoIdTmp));
       // db.execSQL(PedidoDataBaseHelper.get_ACT_PED_TOTALES_TMP_ID(pedidoIdTmp));
        this.cerrar();
/*
        String[] campos = {"sum("+ PedidodetalleDataBaseHelper.CAMPO_MONTO +") as monto "
        };

        Cursor resultado = db.query(PedidodetalleDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
        */
        /*
        //Obtener pedido por ID
        this.abrir();
        pedido =  this.buscar(pedidoId,true);
        this.cerrar();

        //Iniciar Variables
        total = 0.00; subtotal = 0.00 ; iva = 0.00;

        //si pedido todavia no esta definido
        Cursor c;
        //buscar pedidodetalles para idtmp
        c = dbDetalles.findByPedidoIdTmp(pedido);



        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
           subtotal =+ c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_MONTO));
        }

        iva = subtotal * GlobalValues.consIva;
        total = subtotal + iva;
        pedido.setIva(iva);
        pedido.setSubtotal(subtotal);
        pedido.setMonto(total);

        this.abrir();
        this.modificar(pedido, true);
        this.cerrar();
*/

    }




    public long agregar(Pedido item)
    {

        ContentValues valores = new ContentValues();
        try{

            //valores.put(PedidoDataBaseHelper.CAMPO_ID_TMP, item.getIdTmp()); // es ID Autonumerico
            valores.put(PedidoDataBaseHelper.CAMPO_ID, item.getId());
            valores.put(PedidoDataBaseHelper.CAMPO_CREATED, item.getCreated());
            valores.put(PedidoDataBaseHelper.CAMPO_SUBTOTAL, item.getSubtotal());
            valores.put(PedidoDataBaseHelper.CAMPO_IVA, item.getIva());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO, item.getMonto());
            valores.put(PedidoDataBaseHelper.CAMPO_CLIENTE_ID, item.getCliente_id());
            valores.put(PedidoDataBaseHelper.CAMPO_BONIFICACION, item.getBonificacion());
            valores.put(PedidoDataBaseHelper.CAMPO_ESTADO_ID, item.getEstadoId());


            //Nuevos campos para Heladerias
            //DIRECCION
            valores.put(PedidoDataBaseHelper.CAMPO_LOCALIDAD, item.getLocalidad());
            valores.put(PedidoDataBaseHelper.CAMPO_CALLE,     item.getCalle());
            valores.put(PedidoDataBaseHelper.CAMPO_NRO,       item.getNro());
            valores.put(PedidoDataBaseHelper.CAMPO_PISO,      item.getPiso());
            valores.put(PedidoDataBaseHelper.CAMPO_TELEFONO,  item.getTelefono());
            valores.put(PedidoDataBaseHelper.CAMPO_CONTACTO,  item.getContacto());

            //CANTIDAD
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS,   item.getCantidadKilos());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTES,   item.getCantidadPotes()  );
            valores.put(PedidoDataBaseHelper.CAMPO_CUCHARITAS,       item.getCucharitas());
            valores.put(PedidoDataBaseHelper.CAMPO_CUCURUCHOS,       item.getCucuruchos());
            valores.put(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,  item.isEnvioDomicilio());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS, item.getMontoCucuruchos());



        }catch(Exception e ){
            Toast.makeText(context,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();

        }
        return db.insert(PedidoDataBaseHelper.TABLE_NAME, null, valores);
    }

    //Busca Maximo pedido por IDTMP, Es decir codigo autonumerico de SQLITE
    public long getMaxIdTmpPedido(){

        long nroPedido = 0;
        try{
            String sSelect = "select max("+PedidoDataBaseHelper.CAMPO_ID_TMP +") ";
            String sFrom = " from "+ PedidoDataBaseHelper.TABLE_NAME  ;
            String sWhere = " where " + PedidoDataBaseHelper.CAMPO_ESTADO_ID + " = " + GlobalValues.getINSTANCIA().consPedidoEstadoNuevo;
            String selectQuery = sSelect + sFrom + sWhere;
            Log.println(Log.ERROR,"MainActivity:"," No Hay Pedidos Generados "+selectQuery);
            dbHelper = new PedidoDataBaseHelper(context);
            db = dbHelper.getReadableDatabase();

            Cursor c = db.rawQuery(selectQuery, null);
            if(c.getCount()>0){
                c.moveToFirst();
                nroPedido =  c.getLong(0);
            }
            c.close();
            db.close();

        }catch (Exception e){
            Toast.makeText(this.context, "PedidoController" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return nroPedido;
    }

    public void modificar(Pedido item, boolean isIdTmp )
    {
        try{
            ContentValues valores = new ContentValues();
            valores.put(PedidoDataBaseHelper.CAMPO_ID, item.getId());
            valores.put(PedidoDataBaseHelper.CAMPO_CREATED, item.getCreated());
            valores.put(PedidoDataBaseHelper.CAMPO_SUBTOTAL, item.getSubtotal());
            valores.put(PedidoDataBaseHelper.CAMPO_IVA, item.getIva());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO, item.getMonto());
            valores.put(PedidoDataBaseHelper.CAMPO_CLIENTE_ID, item.getCliente_id());
            valores.put(PedidoDataBaseHelper.CAMPO_BONIFICACION, item.getBonificacion());
            valores.put(PedidoDataBaseHelper.CAMPO_ESTADO_ID, item.getEstadoId());

            //DIRECCION
            valores.put(PedidoDataBaseHelper.CAMPO_LOCALIDAD, item.getLocalidad());
            valores.put(PedidoDataBaseHelper.CAMPO_CALLE,     item.getCalle());
            valores.put(PedidoDataBaseHelper.CAMPO_NRO,       item.getNro());
            valores.put(PedidoDataBaseHelper.CAMPO_PISO,      item.getPiso());
            valores.put(PedidoDataBaseHelper.CAMPO_TELEFONO,  item.getTelefono());
            valores.put(PedidoDataBaseHelper.CAMPO_CONTACTO,  item.getContacto());

            //CANTIDAD
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS,   item.getCantidadKilos());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTES,   item.getCantidadPotes()  );
            valores.put(PedidoDataBaseHelper.CAMPO_CUCHARITAS,       item.getCucharitas());
            valores.put(PedidoDataBaseHelper.CAMPO_CUCURUCHOS,       item.getCucuruchos());
            valores.put(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,  item.isEnvioDomicilio());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS, item.getMontoCucuruchos());

            valores.put(PedidoDataBaseHelper.CAMPO_ID_TMP, item.getIdTmp());

            if (isIdTmp){
                String[] argumentos = new String[] {String.valueOf(item.getIdTmp())};
                db.update(PedidoDataBaseHelper.TABLE_NAME, valores,
                        PedidoDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos);
            }else{
                String[] argumentos = new String[]
                        {String.valueOf(item.getId())};
                db.update(PedidoDataBaseHelper.TABLE_NAME, valores,
                        PedidoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
            }
        }catch(Exception e){
            Toast.makeText(context, "Error " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public void eliminar(Pedido pedido)
    {
        String[] argumentos = new String[]
                {String.valueOf(pedido.getId())};
        db.delete(PedidoDataBaseHelper.TABLE_NAME,
                PedidoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public void deleteByIdTmp(long idTmp)
    {
        String[] argumentos = new String[]
                {String.valueOf(idTmp)};
        db.delete(PedidoDataBaseHelper.TABLE_NAME,
                PedidoDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos);
    }

    public ArrayList<Pedido> findByEstadoId_ArrayList(int pEstadoId)
    {
        try {
            ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
            String[] campos = {
                PedidoDataBaseHelper.CAMPO_ESTADO_ID,
                PedidoDataBaseHelper.CAMPO_ID_TMP

        };
            String[] argumentos = {String.valueOf(pEstadoId)};
            Cursor resultado = db.query(PedidoDataBaseHelper.TABLE_NAME, campos,
                    PedidoDataBaseHelper.CAMPO_ESTADO_ID + " = ?", argumentos, null, null, PedidoDataBaseHelper.CAMPO_ID_TMP + " desc");

            if (resultado != null)
            {
                if (resultado.getCount() > 0 ){
                    while (resultado.moveToNext()) {
                        int idtemp = resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID_TMP));
                        Pedido pedido  = new Pedido();
                        pedido = findByIdTmp(idtemp);
                        listaPedidos.add(pedido);
                    }
                }
            }
            return listaPedidos;
        }catch(Exception e){
            return null;
        }
    }

    public Cursor findByEstadoId(int pEstadoId)
    {
        try {
            ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
            String[] campos = {
                    PedidoDataBaseHelper.CAMPO_ID,
                    PedidoDataBaseHelper.CAMPO_CREATED,
                    PedidoDataBaseHelper.CAMPO_SUBTOTAL,
                    PedidoDataBaseHelper.CAMPO_IVA,
                    PedidoDataBaseHelper.CAMPO_MONTO,
                    PedidoDataBaseHelper.CAMPO_CLIENTE_ID,
                    PedidoDataBaseHelper.CAMPO_BONIFICACION,
                    PedidoDataBaseHelper.CAMPO_ESTADO_ID,
                    PedidoDataBaseHelper.CAMPO_ID_TMP

            };
            String[] argumentos = {String.valueOf(pEstadoId)};
            Cursor resultado = db.query(PedidoDataBaseHelper.TABLE_NAME, campos,
                    PedidoDataBaseHelper.CAMPO_ESTADO_ID + " = ?", argumentos, null, null, PedidoDataBaseHelper.CAMPO_ID_TMP + " desc");

            resultado.moveToFirst();
            return resultado;
        }catch(Exception e){
            Log.d("PedidoController", e.getMessage());
            return null;
        }
    }


    public Cursor obtenerTodos()
    {
        String[] campos = {PedidoDataBaseHelper.CAMPO_ID,
                PedidoDataBaseHelper.CAMPO_CREATED,
                PedidoDataBaseHelper.CAMPO_SUBTOTAL,
                PedidoDataBaseHelper.CAMPO_IVA,
                PedidoDataBaseHelper.CAMPO_MONTO,
                PedidoDataBaseHelper.CAMPO_CLIENTE_ID,
                PedidoDataBaseHelper.CAMPO_BONIFICACION,
                PedidoDataBaseHelper.CAMPO_ESTADO_ID,
                PedidoDataBaseHelper.CAMPO_ID_TMP

        };

        Cursor resultado = db.query(PedidoDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, PedidoDataBaseHelper.CAMPO_CREATED + " asc");
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }


    public Cursor obtenerTodosWithSubquery() {
        String[] campos = {PedidoDataBaseHelper.CAMPO_ID,
                PedidoDataBaseHelper.CAMPO_CREATED,
                PedidoDataBaseHelper.CAMPO_SUBTOTAL,
                PedidoDataBaseHelper.CAMPO_IVA,
                PedidoDataBaseHelper.CAMPO_MONTO,
                PedidoDataBaseHelper.CAMPO_CLIENTE_ID,
                PedidoDataBaseHelper.CAMPO_BONIFICACION,
                PedidoDataBaseHelper.CAMPO_ESTADO_ID,
                PedidoDataBaseHelper.CAMPO_ID_TMP

        };

        //String sql = "select p.*, c.* from pedidos p inner join clientes c on p.cliente_id = c.id ";


        Cursor resultado = db.query(PedidoDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public Pedido buscar(long id, boolean isIdTmp)
    {
        Pedido registro = null;
        String[] campos = {
                PedidoDataBaseHelper.CAMPO_ID,
                PedidoDataBaseHelper.CAMPO_ID_TMP,
                PedidoDataBaseHelper.CAMPO_CREATED,
                PedidoDataBaseHelper.CAMPO_SUBTOTAL,
                PedidoDataBaseHelper.CAMPO_IVA,
                PedidoDataBaseHelper.CAMPO_MONTO,
                PedidoDataBaseHelper.CAMPO_CLIENTE_ID,
                PedidoDataBaseHelper.CAMPO_BONIFICACION,
                PedidoDataBaseHelper.CAMPO_ESTADO_ID


        };



        String CAMPOID = "";
        if (isIdTmp) {
            CAMPOID = PedidoDataBaseHelper.CAMPO_ID_TMP;
        }else{
            CAMPOID = PedidoDataBaseHelper.CAMPO_ID;
        }
        Log.d("Deb Buscar Pedido ", CAMPOID);
        Log.d("Deb Buscar Id ", String.valueOf(id));

        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(PedidoDataBaseHelper.TABLE_NAME, campos,
                CAMPOID + " = ?", argumentos, null, null, null);

        ClienteController dbCliente = new ClienteController(this.context);
        PedidodetalleController dbDetalles = new PedidodetalleController(this.context);

        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new Pedido();
            registro.setId(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID)));
            registro.setCreated(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CREATED)));
            registro.setSubtotal(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_SUBTOTAL)));
            registro.setIva(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_IVA)));
            registro.setMonto(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO)));
            registro.setCliente_id(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CLIENTE_ID)));
            Cliente cliente = dbCliente.abrir().buscar(registro.getCliente_id());
            registro.setCliente(cliente);
            dbCliente.cerrar();


            registro.setBonificacion(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_BONIFICACION)));
            registro.setEstadoId(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ESTADO_ID)));
            registro.setIdTmp(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID_TMP)));

            //Carga Detalles
            //Cursor detallecursor = dbDetalles.abrir().obtenerTodos();
            Cursor detallecursor = dbDetalles.abrir().findByPedidoIdTmp(registro.getIdTmp());
            registro.setDetalles(detallecursor);
            dbDetalles.cerrar();

        }
        return registro;

    }





    public Pedido findByIdTmp(long idTmp)
    {
        Pedido registro = null;
        String[] campos = {
                PedidoDataBaseHelper.CAMPO_ID,
                PedidoDataBaseHelper.CAMPO_ID_TMP,
                PedidoDataBaseHelper.CAMPO_CREATED,
                PedidoDataBaseHelper.CAMPO_SUBTOTAL,
                PedidoDataBaseHelper.CAMPO_IVA,
                PedidoDataBaseHelper.CAMPO_MONTO,
                PedidoDataBaseHelper.CAMPO_CLIENTE_ID,
                PedidoDataBaseHelper.CAMPO_BONIFICACION,
                PedidoDataBaseHelper.CAMPO_ESTADO_ID,
                //Campos nuevos de heladeria
                PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,
                PedidoDataBaseHelper.CAMPO_CUCURUCHOS,
                PedidoDataBaseHelper.CAMPO_CUCHARITAS,
                PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS,
                PedidoDataBaseHelper.CAMPO_CANTIDAD_POTES,
                PedidoDataBaseHelper.CAMPO_CALLE,
                PedidoDataBaseHelper.CAMPO_NRO,
                PedidoDataBaseHelper.CAMPO_PISO,
                PedidoDataBaseHelper.CAMPO_CONTACTO,
                PedidoDataBaseHelper.CAMPO_TELEFONO

        };


        String[] argumentos = {String.valueOf(idTmp)};
        Cursor resultado = db.query(PedidoDataBaseHelper.TABLE_NAME, campos,
                PedidoDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos, null, null, null);

        ClienteController dbCliente = new ClienteController(this.context);
        PedidodetalleController dbDetalles = new PedidodetalleController(this.context);

        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new Pedido();
            registro.setId(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID)));
            registro.setCreated(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CREATED)));
            registro.setSubtotal(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_SUBTOTAL)));
            registro.setIva(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_IVA)));
            registro.setMonto(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO)));
            registro.setCliente_id(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CLIENTE_ID)));

            //Cliente cliente = dbCliente.abrir().buscar(registro.getCliente_id());
            //registro.setCliente(cliente);
            //dbCliente.cerrar();

            registro.setBonificacion(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_BONIFICACION)));
            registro.setEstadoId(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ESTADO_ID)));
            registro.setIdTmp(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID_TMP)));

            //Nuevos campos de PEdido para Heladeria
            registro.setEnvioDomicilio(Boolean.parseBoolean(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO))));
            registro.setCucuruchos(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CUCURUCHOS)));
            registro.setCucharitas(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CUCHARITAS)));
            registro.setCantidadKilos(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS)));
            registro.setCantidadPotes(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTES)));
            registro.setCalle(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CALLE)));
            registro.setNro(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_NRO)));
            registro.setPiso(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_PISO)));
            registro.setContacto(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CONTACTO)));
            registro.setTelefono(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_TELEFONO)));


            //Carga Detalles
            //Cursor detallecursor = dbDetalles.abrir().obtenerTodos();
            Cursor detallecursor = dbDetalles.abrir().findByPedidoIdTmp(registro.getIdTmp());
            ArrayList<Pedidodetalle> lista = dbDetalles.abrir().parseCursorToArrayList(detallecursor);
            registro.setDetalles(lista);
            dbDetalles.cerrar();

        }
        return registro;

    }





    public void limpiar()
    {

        db.delete(PedidoDataBaseHelper.TABLE_NAME, null, null);

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