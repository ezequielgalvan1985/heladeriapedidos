package adaptivex.pedidoscloud.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ezequiel on 19/03/2017.
 */


public class InstalacionDB extends SQLiteOpenHelper {
    private Context ctx;
    public String DB_NAME = "db_contacto";
    public int DB_VERSION = 51;

    public InstalacionDB(Context context)
    {
        super(context, "db_contacto", null, 51);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.crearTablas(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void crearTablas(SQLiteDatabase db){
        ProductoDataBaseHelper pdb = new ProductoDataBaseHelper(this.ctx);
        db.execSQL(pdb.CREATE_TABLE);



    }

}
