package adaptivex.pedidoscloud.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class MemoDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = Configurador.DBName;
    public static final String TABLE_NAME = "memos";
    public static final int DB_VERSION = Configurador.DBVersion;
    public static final String CAMPO_CLIENTE_ID = "cliente_id";
    public static final String CAMPO_PRODUCTO_ID = "producto_id";
    public static final String CAMPO_CANTIDAD = "cantidad";
    public static final String JSON_CLIENTE = "cliente";
    public static final String JSON_PRODUCTO = "producto";


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            CAMPO_CLIENTE_ID + " integer not null, " +
            CAMPO_PRODUCTO_ID + " integer not null, " +
            CAMPO_CANTIDAD + " integer not null " +
            " )";

    public MemoDataBaseHelper(Context context)
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
