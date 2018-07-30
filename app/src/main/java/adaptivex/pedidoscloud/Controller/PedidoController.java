package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Core.Interfaces.ControllerInterface;
import adaptivex.pedidoscloud.Model.Cliente;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.PedidodetalleDataBaseHelper;
import adaptivex.pedidoscloud.Model.Pote;
import adaptivex.pedidoscloud.Model.PoteItem;
import adaptivex.pedidoscloud.Model.Promo;

import java.util.ArrayList;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PedidoController implements ControllerInterface
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






    public ArrayList<Pote> getPotesArrayList2(long idAndroid ){
        try{
            PedidodetalleController pdc = new PedidodetalleController(context);
            //Crear arraylist de potes
            ArrayList<Pote> listaPotes = new ArrayList<Pote>();
            Pedido p = new Pedido();

            p = this.abrir().findByIdTmp(idAndroid);
            //obtener todos los pedido detalles
            ArrayList  <Pedidodetalle> lista = pdc.findByPedido_android_idToArrayList(idAndroid);
            Integer currentnropote = 0;
            Pote pote            = new Pote();
            Pote poteanterior    = null;
            for (Pedidodetalle pd :lista){

                if  (currentnropote != pd.getNroPote()) {

                    //Crear Pote
                    pote = new Pote();
                    pote.setPedido(p);
                    pote.setNroPote(pd.getNroPote());
                    pote.setKilos(pd.getMedidaPote());
                    pote.setHeladomonto(pd.getMonto());
                    // FIn creacion de pote
                }
                // Crea el item del pote
                PoteItem item = new PoteItem();
                item.setCantidad(pd.getProporcionHelado());
                item.setProducto(pd.getProducto());
                pote.addItemPote(item);

                //
                poteanterior = pote;
                if  (currentnropote != pd.getNroPote()) {
                    if (poteanterior != null ) {
                        listaPotes.add(poteanterior);
                    }
                }
                currentnropote = pd.getNroPote();

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


    public long agregar(Pedido item)
    {

        ContentValues valores = new ContentValues();
        try{

            //valores.put(PedidoDataBaseHelper.CAMPO_ID_TMP, item.getIdTmp()); // es ID Autonumerico
            valores.put(PedidoDataBaseHelper.CAMPO_ID, item.getId());
            //valores.put(PedidoDataBaseHelper.CAMPO_CREATED, item.getCreated());
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

            //POTES
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO, item.getCantPoteCuarto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO, item.getCantPoteMedio());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO, item.getCantPoteTresCuarto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO, item.getCantPoteKilo());

            valores.put(PedidoDataBaseHelper.CAMPO_CUCHARITAS,       item.getCucharitas());
            valores.put(PedidoDataBaseHelper.CAMPO_CUCURUCHOS,       item.getCucuruchos());
            valores.put(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,  item.isEnvioDomicilio());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS, item.getMontoCucuruchos());

            //MONTO
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,  item.getMontoDescuento());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_ABONA,  item.getMontoabona());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO,  item.getMonto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO, item.getCantidadDescuento());


        }catch(Exception e ){
            Toast.makeText(context,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return db.insert(PedidoDataBaseHelper.TABLE_NAME, null, valores);
    }





    //Busca Maximo pedido por IDTMP, Es decir codigo autonumerico de SQLITE
    public long findByLastAndroidId(){

        long nroPedido = 0;
        try{
            String sSelect = "select max("+PedidoDataBaseHelper.CAMPO_ID_TMP +") ";
            String sFrom = " from "+ PedidoDataBaseHelper.TABLE_NAME  ;

            String selectQuery = sSelect + sFrom ;
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

    //Busca Maximo pedido por IDTMP, Es decir codigo autonumerico de SQLITE
    public long findByLastAndroidIdAndEstadoId(Integer estado){

        long nroPedido = 0;
        try{
            String sSelect = "select max("+PedidoDataBaseHelper.CAMPO_ID_TMP +") ";
            String sFrom = "  from "+ PedidoDataBaseHelper.TABLE_NAME  ;
            String sWhere = " where " + PedidoDataBaseHelper.CAMPO_ESTADO_ID + " = " + String.valueOf(estado);
            String selectQuery = sSelect + sFrom + sWhere;
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

            //POTES
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO, item.getCantPoteCuarto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO, item.getCantPoteMedio());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO, item.getCantPoteTresCuarto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO, item.getCantPoteKilo());

            valores.put(PedidoDataBaseHelper.CAMPO_CUCHARITAS,       item.getCucharitas());
            valores.put(PedidoDataBaseHelper.CAMPO_CUCURUCHOS,       item.getCucuruchos());
            valores.put(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,  item.isEnvioDomicilio());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS, item.getMontoCucuruchos());

            //MONTO
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,    item.getMontoDescuento());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO, item.getCantidadDescuento());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_ABONA,  item.getMontoabona());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO,  item.getMonto());


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
                    PedidoDataBaseHelper.CAMPO_ID_TMP,

                    PedidoDataBaseHelper.CAMPO_CUCURUCHOS,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO,

                    //POTES
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,

                    PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS,
                    PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,
                    PedidoDataBaseHelper.CAMPO_MONTO_ABONA,
                    PedidoDataBaseHelper.CAMPO_MONTO


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







    public boolean calculatePromoBeforeEdit2(Pedido pedido){
        try{
            PromoController promoCtr = new PromoController(context);
            Promo promo = promoCtr.abrir().matchPromoForPedido(pedido);
            if (promo!= null){
                if (promo.getImporteDescuento()!=null ){
                    pedido.setMontoDescuento(promo.getImporteDescuento());

                }
            }
            abrir().edit(pedido);
            return true;
        }catch (Exception e){
            Toast.makeText(context,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }
    }



    //Metodos de Interface
    @Override
    public long add(Object object)
    {
        Pedido item = (Pedido) object;
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

            //POTES
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO, item.getCantPoteCuarto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO, item.getCantPoteMedio());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO, item.getCantPoteTresCuarto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO, item.getCantPoteKilo());


            valores.put(PedidoDataBaseHelper.CAMPO_CUCHARITAS,       item.getCucharitas());
            valores.put(PedidoDataBaseHelper.CAMPO_CUCURUCHOS,       item.getCucuruchos());
            valores.put(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,  item.isEnvioDomicilio());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO, item.getCantidadDescuento());


            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS, item.getMontoCucuruchos());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,  item.getMontoDescuento());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_HELADOS, item.getMontoHelados());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_ABONA, item.getMontoabona());


            valores.put(PedidoDataBaseHelper.CAMPO_HORA_ENTREGA, String.valueOf(item.getHoraentrega()));
            valores.put(PedidoDataBaseHelper.CAMPO_HORA_RECEPCION, String.valueOf(item.getHoraRecepcion()));
            valores.put(PedidoDataBaseHelper.CAMPO_TIEMPO_DEMORA, item.getTiempoDemora());

            return db.insert(PedidoDataBaseHelper.TABLE_NAME, null, valores);
        }catch(Exception e ){
            Toast.makeText(context,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
            return -1;
        }
    }

    @Override
    public boolean edit(Object object) {

        try {
            Pedido item = (Pedido) object;
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
            valores.put(PedidoDataBaseHelper.CAMPO_CALLE, item.getCalle());
            valores.put(PedidoDataBaseHelper.CAMPO_NRO, item.getNro());
            valores.put(PedidoDataBaseHelper.CAMPO_PISO, item.getPiso());
            valores.put(PedidoDataBaseHelper.CAMPO_TELEFONO, item.getTelefono());
            valores.put(PedidoDataBaseHelper.CAMPO_CONTACTO, item.getContacto());

            //CANTIDAD
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS, item.getCantidadKilos());

            //POTES
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO, item.getCantPoteCuarto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO, item.getCantPoteMedio());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO, item.getCantPoteTresCuarto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO, item.getCantPoteKilo());


            valores.put(PedidoDataBaseHelper.CAMPO_CUCHARITAS, item.getCucharitas());
            valores.put(PedidoDataBaseHelper.CAMPO_CUCURUCHOS, item.getCucuruchos());
            valores.put(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO, item.isEnvioDomicilio());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS, item.getMontoCucuruchos());

            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO, item.getCantidadDescuento());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO, item.getMontoDescuento());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_HELADOS, item.getMontoHelados());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_ABONA, item.getMontoabona());


            valores.put(PedidoDataBaseHelper.CAMPO_HORA_ENTREGA, String.valueOf(item.getHoraentrega()));
            valores.put(PedidoDataBaseHelper.CAMPO_HORA_RECEPCION, String.valueOf(item.getHoraRecepcion()));
            valores.put(PedidoDataBaseHelper.CAMPO_TIEMPO_DEMORA, item.getTiempoDemora());

            valores.put(PedidoDataBaseHelper.CAMPO_ID_TMP, item.getIdTmp());

            String[] argumentos = new String[]{String.valueOf(item.getIdTmp())};
            db.update(PedidoDataBaseHelper.TABLE_NAME, valores, PedidoDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos);
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public boolean delete(Object object) {
        return false;
    }


    @Override
    public Cursor findAll(){
        try{
            String[] campos = {PedidoDataBaseHelper.CAMPO_ID,
                    PedidoDataBaseHelper.CAMPO_CREATED,
                    PedidoDataBaseHelper.CAMPO_SUBTOTAL,
                    PedidoDataBaseHelper.CAMPO_IVA,
                    PedidoDataBaseHelper.CAMPO_MONTO,
                    PedidoDataBaseHelper.CAMPO_CLIENTE_ID,
                    PedidoDataBaseHelper.CAMPO_BONIFICACION,
                    PedidoDataBaseHelper.CAMPO_ESTADO_ID,
                    PedidoDataBaseHelper.CAMPO_ID_TMP,
                    //DATOS HELADERIA
                    PedidoDataBaseHelper.CAMPO_LOCALIDAD,
                    PedidoDataBaseHelper.CAMPO_CALLE,
                    PedidoDataBaseHelper.CAMPO_NRO,
                    PedidoDataBaseHelper.CAMPO_PISO,
                    PedidoDataBaseHelper.CAMPO_TELEFONO,
                    PedidoDataBaseHelper.CAMPO_CONTACTO,

                    PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS,

                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,

                    PedidoDataBaseHelper.CAMPO_CUCHARITAS,
                    PedidoDataBaseHelper.CAMPO_CUCURUCHOS,
                    PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,
                    PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO,
                    PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,
                    PedidoDataBaseHelper.CAMPO_HORA_ENTREGA,
                    PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,
                    PedidoDataBaseHelper.CAMPO_MONTO_ABONA


            };

            Cursor resultado = db.query(PedidoDataBaseHelper.TABLE_NAME, campos,
                    null, null, null, null, PedidoDataBaseHelper.CAMPO_CREATED + " desc");
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e){
            Toast.makeText(context,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }




    @Override
    public Cursor findByIdAndroid(long idAndroid) {
        try{
            String[] campos = {
                    PedidoDataBaseHelper.CAMPO_ID,
                    PedidoDataBaseHelper.CAMPO_CREATED,
                    PedidoDataBaseHelper.CAMPO_SUBTOTAL,
                    PedidoDataBaseHelper.CAMPO_IVA,
                    PedidoDataBaseHelper.CAMPO_MONTO,
                    PedidoDataBaseHelper.CAMPO_CLIENTE_ID,
                    PedidoDataBaseHelper.CAMPO_BONIFICACION,
                    PedidoDataBaseHelper.CAMPO_ESTADO_ID,
                    PedidoDataBaseHelper.CAMPO_ID_TMP,
                    //DATOS HELADERIA
                    PedidoDataBaseHelper.CAMPO_LOCALIDAD,
                    PedidoDataBaseHelper.CAMPO_CALLE,
                    PedidoDataBaseHelper.CAMPO_NRO,
                    PedidoDataBaseHelper.CAMPO_PISO,
                    PedidoDataBaseHelper.CAMPO_TELEFONO,
                    PedidoDataBaseHelper.CAMPO_CONTACTO,

                    PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS,

                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,

                    PedidoDataBaseHelper.CAMPO_CUCHARITAS,
                    PedidoDataBaseHelper.CAMPO_CUCURUCHOS,
                    PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,
                    PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO,
                    PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,
                    PedidoDataBaseHelper.CAMPO_MONTO_HELADOS,
                    PedidoDataBaseHelper.CAMPO_HORA_ENTREGA,
                    PedidoDataBaseHelper.CAMPO_MONTO_ABONA



            };
            String[] argumentos = {String.valueOf(idAndroid)};
            String orderBy      = PedidoDataBaseHelper.CAMPO_CREATED + " DESC ";
            Cursor resultado = db.query(PedidoDataBaseHelper.TABLE_NAME, campos, PedidoDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos, null, null, orderBy);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e){
            Toast.makeText(context,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }

    @Override
    public ArrayList<Object> findAllToArrayList() {
        return null;
    }

    public Pedido parseCursorToPedido(Cursor resultado){
        try{
            Pedido registro = new Pedido();
            if (resultado != null)
            {
                resultado.moveToFirst();
                registro = new Pedido();
                registro.setId(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID)));
                registro.setCreated(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CREATED)));
                registro.setSubtotal(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_SUBTOTAL)));
                registro.setIva(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_IVA)));
                registro.setCliente_id(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CLIENTE_ID)));
                registro.setBonificacion(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_BONIFICACION)));
                registro.setEstadoId(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ESTADO_ID)));
                registro.setIdTmp(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID_TMP)));

                //Nuevos campos de PEdido para Heladeria
                registro.setEnvioDomicilio(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO)) >0);
                registro.setLocalidad(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_LOCALIDAD)));
                registro.setCalle(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CALLE)));
                registro.setNro(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_NRO)));
                registro.setPiso(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_PISO)));
                registro.setContacto(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CONTACTO)));
                registro.setTelefono(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_TELEFONO)));

                registro.setCucuruchos(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CUCURUCHOS)));
                registro.setCucharitas(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CUCHARITAS)));
                registro.setCantidadKilos(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS)));

                //POTES
                registro.setCantPoteCuarto(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO)));
                registro.setCantPoteMedio(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO)));
                registro.setCantPoteTresCuarto(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO)));
                registro.setCantPoteKilo(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO)));

                registro.setMontoHelados(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_HELADOS)));
                registro.setMontoCucuruchos(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS)));
                registro.setCantidadDescuento(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO)));
                registro.setMontoDescuento(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO)));
                registro.setMonto(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO)));
                registro.setMontoabona(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_ABONA)));
            }
            return registro;
        }catch(Exception e){
            Toast.makeText(context,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }


    @Override
    public Cursor findById(long id) {
        try{
            String[] campos = {
                    PedidoDataBaseHelper.CAMPO_ID,
                    PedidoDataBaseHelper.CAMPO_CREATED,
                    PedidoDataBaseHelper.CAMPO_SUBTOTAL,
                    PedidoDataBaseHelper.CAMPO_IVA,
                    PedidoDataBaseHelper.CAMPO_MONTO,
                    PedidoDataBaseHelper.CAMPO_CLIENTE_ID,
                    PedidoDataBaseHelper.CAMPO_BONIFICACION,
                    PedidoDataBaseHelper.CAMPO_ESTADO_ID,
                    PedidoDataBaseHelper.CAMPO_ID_TMP,
                    //DATOS HELADERIA
                    PedidoDataBaseHelper.CAMPO_LOCALIDAD,
                    PedidoDataBaseHelper.CAMPO_CALLE,
                    PedidoDataBaseHelper.CAMPO_NRO,
                    PedidoDataBaseHelper.CAMPO_PISO,
                    PedidoDataBaseHelper.CAMPO_TELEFONO,
                    PedidoDataBaseHelper.CAMPO_CONTACTO,

                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,


                    PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS,
                    PedidoDataBaseHelper.CAMPO_CUCHARITAS,
                    PedidoDataBaseHelper.CAMPO_CUCURUCHOS,
                    PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,
                    PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS,
                    PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO,
                    PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,
                    PedidoDataBaseHelper.CAMPO_HORA_ENTREGA,
                    PedidoDataBaseHelper.CAMPO_MONTO_ABONA,



            };
            String[] argumentos = {String.valueOf(id)};
            String orderBy      = PedidoDataBaseHelper.CAMPO_CREATED + " DESC ";
            Cursor resultado = db.query(PedidoDataBaseHelper.TABLE_NAME, campos, PedidoDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, orderBy);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e){
            Toast.makeText(context,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }


    @Override
    public ArrayList<Object> parseCursorToArrayList(Cursor c) {
        ArrayList<Object> lista = new ArrayList<Object>();
        Pedido p = new Pedido ();


        for (int i = 0;i <= lista.size(); i++){
            Pedido ped = (Pedido) lista.get(i);
        }

        return lista;
        //return null;
    }

    @Override
    public Object parseCursorToObject(Cursor c) {
        return null;
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
                PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO,

                PedidoDataBaseHelper.CAMPO_MONTO_HELADOS,
                PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS,
                PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,
                PedidoDataBaseHelper.CAMPO_MONTO_ABONA,

                PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
                PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO,
                PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
                PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,

                PedidoDataBaseHelper.CAMPO_LOCALIDAD,
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

            registro.setMontoHelados(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_HELADOS)));
            registro.setMontoCucuruchos(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS)));
            registro.setMontoDescuento(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO)));
            registro.setMontoabona(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_ABONA)));

            registro.setBonificacion(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_BONIFICACION)));
            registro.setEstadoId(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ESTADO_ID)));
            registro.setIdTmp(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID_TMP)));

            //Nuevos campos de Pedido para Heladeria
            registro.setEnvioDomicilio(Boolean.parseBoolean(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO))));
            registro.setCucuruchos(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CUCURUCHOS)));
            registro.setCucharitas(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CUCHARITAS)));
            registro.setCantidadKilos(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS)));

            //POTES
            registro.setCantPoteCuarto(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO)));
            registro.setCantPoteMedio(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO)));
            registro.setCantPoteTresCuarto(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO)));
            registro.setCantPoteKilo(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO)));

            registro.setLocalidad(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_LOCALIDAD)));
            registro.setCalle(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CALLE)));
            registro.setNro(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_NRO)));
            registro.setPiso(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_PISO)));
            registro.setContacto(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CONTACTO)));
            registro.setTelefono(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_TELEFONO)));

            //Carga Detalles

            ArrayList<Pedidodetalle> lista =  dbDetalles.abrir().findByPedido_android_idToArrayList(registro.getIdTmp());
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