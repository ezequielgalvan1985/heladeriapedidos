package adaptivex.pedidoscloud.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PromoDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME              = Configurador.DBName;
    public static final String TABLE_NAME           = "promos";
    public static final int DB_VERSION              = Configurador.DBVersion;
    public static final String CAMPO_ID             = "id";
    public static final String CAMPO_ID_ANDROID     = "id_android";
    public static final String CAMPO_NOMBRE         = "nombre";
    public static final String CAMPO_DESCRIPCION    = "descripcion";
    public static final String CAMPO_FECHA_DESDE    = "fecha_desde";
    public static final String CAMPO_FECHA_HASTA    = "fecha_hasta";
    public static final String CAMPO_CANTIDAD_KILOS     = "cantidad_kilos";
    public static final String CAMPO_IMPORTE_DESCUENTO  = "importe_descuento";
    public static final String CAMPO_PRECIO_PROMO       = "precio_promo";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            CAMPO_ID                + " integer not null," +
            CAMPO_NOMBRE            + " text null, " +
            CAMPO_DESCRIPCION       + " text null, " +
            CAMPO_FECHA_DESDE       + " date null, " +
            CAMPO_FECHA_HASTA       + " date null, " +
            CAMPO_CANTIDAD_KILOS    + " integer null, " +
            CAMPO_IMPORTE_DESCUENTO + " real null, " +
            CAMPO_PRECIO_PROMO      + " real null, " +
            CAMPO_ID_ANDROID        + " integer primary key autoincrement not null" +
            " )";

    public PromoDataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
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
