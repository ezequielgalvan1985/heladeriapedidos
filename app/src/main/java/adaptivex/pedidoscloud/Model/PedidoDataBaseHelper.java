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

    public static final String ANDROID_ID_JSON = "android_id";
    public static final String CLIENTE_JSON = "cliente";
    public static final String CLIENTE_ID_JSON = "id";
    public static final String FECHA_JSON = "fecha";


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            CAMPO_ID + " integer null," +
            CAMPO_CREATED + " date null ," +
            CAMPO_SUBTOTAL + " real null ," +
            CAMPO_IVA + " real null ," +
            CAMPO_MONTO + " real null ," +
            CAMPO_CLIENTE_ID + " integer null ," +
            CAMPO_BONIFICACION + " real null ," +
            CAMPO_ESTADO_ID + " integer null ," +
            CAMPO_ID_TMP + " integer " +
            "primary key autoincrement " +
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
