package adaptivex.pedidoscloud.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class ParameterDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = Configurador.DBName;
    public static final String TABLE_NAME = "parameters";
    public static final int DB_VERSION = Configurador.DBVersion;
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String VALOR_TEXTO = "valor_texto";
    public static final String VALOR_DECIMAL = "valor_decimal";
    public static final String VALOR_INTEGER = "valor_integer";
    public static final String VALOR_FECHA = "valor_fecha";

    public static final String DESCRIPCION = "descripcion";


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + " (" +
            ID + " integer primary key autoincrement not null," +
            NOMBRE + " text null, " +
            DESCRIPCION + " text null, " +
            VALOR_TEXTO + " text null, " +
            VALOR_INTEGER + " integer null, " +
            VALOR_DECIMAL + " decimal null, " +
            VALOR_FECHA + " date null " +
            " )";

    public ParameterDataBaseHelper(Context context)
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
