package adaptivex.pedidoscloud.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class UserDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = Configurador.DBName;
    public static final String TABLE_NAME = "users";
    public static final int DB_VERSION = Configurador.DBVersion ;

    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String ENTIDAD_ID = "entidad_id";
    public static final String GROUP_ID = "group_id";
    public static final String EMAIL = "email";
    public static final String EMPLEADO_ID = "empleado_id";

    public static final String LOCALIDAD = "localidad";
    public static final String CALLE     = "calle";
    public static final String NRO       = "nro";
    public static final String PISO      = "piso";
    public static final String TELEFONO  = "telefono";
    public static final String CONTACTO  = "contacto";

    public static final String LOGUED    = "logued";
    public static final String ID_ANDROID = "id_android";


    //Estructura JSON
    public static final String EMPRESA_JSON = "empresa";
    public static final String EMPRESA_ID_JSON = "id";
    public static final String EMPLEADO_JSON = "empleado";
    public static final String EMPLEADO_ID_JSON = "id";


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            ID + "         integer  null," +
            USERNAME + "   text null, " +
            ENTIDAD_ID + " text null, " +
            GROUP_ID + "   text null, " +
            EMAIL + "      text null, " +
            LOCALIDAD + "  text null, " +
            CALLE     + "  text null, " +
            NRO       + "  text null, " +
            PISO      + "  text null, " +
            TELEFONO  + "  text null, " +
            CONTACTO  + "  text null, " +
            LOGUED    + "  text null, " +
            ID_ANDROID  + "  integer null " +
            " )";

    public UserDataBaseHelper(Context context)
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

        db.execSQL(CREATE_TABLE);

    }

}
