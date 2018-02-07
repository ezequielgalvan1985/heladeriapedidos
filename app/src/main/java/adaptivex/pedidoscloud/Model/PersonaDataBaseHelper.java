package adaptivex.pedidoscloud.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PersonaDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = "db_contacto";
    public static final String TABLE_NAME = "persona";
    public static final int DB_VERSION = 51;
    public static final String CAMPO_ID = "_id";
    public static final String CAMPO_APELLIDO = "apellido";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_SEXO = "sexo";
    public static final String CAMPO_FECHA_NACIMIENTO = "fechanacimiento";
    public static final String CAMPO_EMAIL = "email";
    public static final String CAMPO_TELEFONO = "telefono";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            CAMPO_ID + " integer not null primary key autoincrement," +
            CAMPO_APELLIDO + " text not null," +
            CAMPO_NOMBRE + " text not null," +
            CAMPO_SEXO + " integer not null," +
            CAMPO_FECHA_NACIMIENTO + " text," +
            CAMPO_EMAIL + " text," +
            CAMPO_TELEFONO + " text)";
    public PersonaDataBaseHelper(Context context)
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
