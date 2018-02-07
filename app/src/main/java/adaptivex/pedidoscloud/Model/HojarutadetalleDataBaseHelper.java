package adaptivex.pedidoscloud.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class HojarutadetalleDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = Configurador.DBName;
    public static final String TABLE_NAME = "hojarutadetalles";
    public static final int DB_VERSION = Configurador.DBVersion;
    public static final String ID = "id";
    public static final String HOJARUTA_ID = "hojaruta_id";
    public static final String CLIENTE_ID = "cliente_id";
    public static final String HORA = "hora";
    public static final String NOTAS = "notas";
    public static final String CLIENTE_JSON = "cliente";
    public static final String CLIENTE_ID_JSON = "id";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            ID + " integer not null," +
            HOJARUTA_ID + " integer not null, " +
            CLIENTE_ID + " integer not null, " +
            HORA + " text null, " +
            NOTAS + " text null " +
            " )";

    public HojarutadetalleDataBaseHelper(Context context)
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
