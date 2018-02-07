package adaptivex.pedidoscloud.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class ProductoDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = Configurador.DBName;
    public static final String TABLE_NAME = "productos";
    public static final int DB_VERSION =  Configurador.DBVersion;
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_DESCRIPCION = "descripcion";
    public static final String CAMPO_PRECIO = "precio";
    public static final String CAMPO_STOCK = "stock";
    public static final String CAMPO_IMAGEN = "imagen";
    public static final String CAMPO_IMAGENURL = "imagenurl";
    public static final String CAMPO_CODIGOEXTERNO = "codigoexterno";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            CAMPO_ID + " integer not null ," +
            CAMPO_NOMBRE + " text not null," +
            CAMPO_DESCRIPCION + " text null, " +
            CAMPO_PRECIO + " decimal (7,2) default 0, " +
            CAMPO_STOCK + " integer null default 0, " +
            CAMPO_IMAGEN + " text null default ''," +
            CAMPO_IMAGENURL + " text null default ''," +
            CAMPO_CODIGOEXTERNO + " text null default ''" +
            ")";

    public ProductoDataBaseHelper(Context context)
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
