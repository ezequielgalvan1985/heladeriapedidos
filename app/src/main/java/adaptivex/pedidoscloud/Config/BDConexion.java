package adaptivex.pedidoscloud.Config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ezequiel on 08/05/2016.
 */
public  class BDConexion extends SQLiteOpenHelper {

    Context ctx;
    BDConexion conexion;
    SQLiteDatabase bd;

    public BDConexion(Context context) {
        super(context, "Laboratorio4", null, 1);
        ctx = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //Entidades definidas creat tabla
        String createTableTelefonos ="CREATE TABLE telefonos (numero TEXT NOT NULL )";

        //db.execSQL(createTableTelefonos);
        //db.execSQL(User.getCreatetable());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE telefonos IF EXISTS telefonos");
        db.execSQL("DROP TABLE productos IF EXISTS productos");
        db.execSQL("DROP TABLE rutinas IF EXISTS rutinas");
        db.execSQL("DROP TABLE rutinadetalles IF EXISTS rutinadetalles");

        onCreate(db);
    }


    public void abrir(){
        conexion = new BDConexion(ctx);
        bd = conexion.getWritableDatabase();

    }

    public void cerrar(){
        bd.close();
    }

    public long add(String tabla, ContentValues valores)throws Exception {
        return  bd.insert(tabla, null, valores);
    }

    public long registrar(String pNumero)throws Exception {
        ContentValues valores = new ContentValues();
        valores.put("numero", pNumero);
        abrir();
        long nerror = bd.insert("telefonos", null, valores);
        cerrar();
        return nerror;
    }


    public String consultar()throws Exception {
        String datos ="";
        String[] columnas = new String[]{"numero"};
        abrir();
        Cursor c = bd.query("telefonos", columnas,null,null,null, null ,null );

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            datos += c.getString(c.getColumnIndex("numero"))+ "\n";
        }
        cerrar();
        return datos;

    }
    public String consultar2(String tabla, String[] columnas)throws Exception {
        String datos ="";

        Cursor c = bd.query(tabla, columnas,null, null,null, null ,null );

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            datos += c.getString(c.getColumnIndex("nombre"))+ "\n";
            datos += c.getString(c.getColumnIndex("descripcion"))+ "\n";
        }
        return datos;

    }

    public Cursor consultarCursor(String tabla, String[] columnas)throws Exception {
        Cursor c = bd.query(tabla, columnas,null, null,null, null ,null );

        return c;
    }





}
