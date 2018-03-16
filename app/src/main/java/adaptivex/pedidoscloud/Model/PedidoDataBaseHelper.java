package adaptivex.pedidoscloud.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PedidoDataBaseHelper extends SQLiteOpenHelper
{
    //CAMPOS EN TABLA SQLITE
    public static final String DB_NAME = Configurador.DBName;
    public static final String TABLE_NAME = "pedidos";
    public static final int DB_VERSION = Configurador.DBVersion ;
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_CREATED = "created";
    public static final String CAMPO_SUBTOTAL = "subtotal";
    public static final String CAMPO_IVA = "iva";
    public static final String CAMPO_MONTO = "monto";
    public static final String CAMPO_CLIENTE_ID = "cliente_id";
    public static final String CAMPO_BONIFICACION = "bonificacion";
    public static final String CAMPO_ESTADO_ID = "estado_id";
    public static final String CAMPO_ID_TMP = "idtmp";

    //DIRECCION
    public static final String CAMPO_LOCALIDAD = "localidad";
    public static final String CAMPO_CALLE     = "calle";
    public static final String CAMPO_NRO       = "nro";
    public static final String CAMPO_PISO      = "piso";
    public static final String CAMPO_TELEFONO  = "telefono";
    public static final String CAMPO_CONTACTO  = "contacto";

    //CANTIDADES
    public static final String CAMPO_CANTIDAD_POTES  = "cantidadpotes";
    public static final String CAMPO_CANTIDAD_KILOS  = "cantidadkilos";
    public static final String CAMPO_CUCHARITAS      = "cucharitas";
    public static final String CAMPO_CUCURUCHOS      = "cucuruchos";
    public static final String CAMPO_ENVIO_DOMICILIO = "envio";

    //CAMPOS QUE VIENEN EN EL API REST JSON. (NO ESTAN EN LA TABLA SQLITE)
    public static final String ANDROID_ID_JSON = "android_id";
    public static final String CLIENTE_JSON    = "cliente";
    public static final String CLIENTE_ID_JSON = "id";
    public static final String FECHA_JSON      = "fecha";


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            CAMPO_ID            + " integer null," +
            CAMPO_CREATED       + " date null ," +
            CAMPO_SUBTOTAL      + " real null ," +
            CAMPO_IVA           + " real null ," +
            CAMPO_MONTO         + " real null ," +
            CAMPO_CLIENTE_ID    + " integer null, " +
            CAMPO_BONIFICACION  + " real null, " +
            CAMPO_ESTADO_ID     + " integer null, " +
            CAMPO_LOCALIDAD     + " text null , " +
            CAMPO_CALLE         + " text null , " +
            CAMPO_NRO           + " text null , " +
            CAMPO_PISO          + " text null , " +
            CAMPO_TELEFONO      + " text null , " +
            CAMPO_CONTACTO      + " text null , " +
            CAMPO_CANTIDAD_KILOS  + " integer null, " +
            CAMPO_CANTIDAD_POTES  + " integer null, " +
            CAMPO_CUCHARITAS    + " integer null, " +
            CAMPO_CUCURUCHOS    + " integer null, " +
            CAMPO_ENVIO_DOMICILIO + " integer null, " +
            CAMPO_ID_TMP        + " integer primary key autoincrement not null" +
            ")";

    public PedidoDataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static String get_ACT_PED_TOTALES_TMP_ID(int tmpid){
        String sql =
                "update "+PedidoDataBaseHelper.TABLE_NAME +" " +
                " set " +
                CAMPO_MONTO + " = (select sum("+PedidodetalleDataBaseHelper.CAMPO_MONTO +") " +
                                     " from "+PedidodetalleDataBaseHelper.TABLE_NAME+
                                     " where "+PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP + " = "+  String.valueOf(tmpid) +
                                     " ) "+
                " where " + PedidoDataBaseHelper.CAMPO_ID_TMP + " = " + String.valueOf(tmpid);

        return sql;
    }

    public static String get_PED_TOTALES_TMP_ID(int tmpid){
        String sql ="select sum("+PedidodetalleDataBaseHelper.CAMPO_MONTO +") " +
                        " from "+PedidodetalleDataBaseHelper.TABLE_NAME+
                        " where "+PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID_TMP + " = "+  String.valueOf(tmpid);


        return sql;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

}
