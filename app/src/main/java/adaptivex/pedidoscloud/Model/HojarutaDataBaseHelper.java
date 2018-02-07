package adaptivex.pedidoscloud.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class HojarutaDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = Configurador.DBName;
    public static final String TABLE_NAME = "hojarutas";
    public static final int DB_VERSION = Configurador.DBVersion;
    public static final String ID = "id";
    public static final String DIA_ID = "dia_id";
    public static final String USER_ID = "user_id";
    public static final String TITULO = "titulo";
    public static final String NOTAS = "notas";

    //Estructura del Json
    public static final String EMPLEADO_ID_JSON = "id";
    public static final String DETALLES_JSON = "hojarutadetalles";
    public static final String EMPLEADO_JSON = "empleado";
    public static final String USER_JSON = "user";
    public static final String USER_ID_JSON = "id";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            ID + " integer not null," +
            DIA_ID + " integer not null, " +
            USER_ID + " integer not null, " +
            TITULO + " text null, " +
            NOTAS + " text null " +
            " )";

    public HojarutaDataBaseHelper(Context context)
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
