package adaptivex.pedidoscloud.Controller;

import android.content.Context;

import adaptivex.pedidoscloud.Config.BDConexion;

/**
 * Created by ezequiel on 28/05/2016.
 */
public class AppController {
    private BDConexion conn;
    private Context context;


    public void AppController(Context context){
        setConn(new BDConexion(context));
    }

    protected BDConexion getConn() {
        return conn;
    }

    protected void setConn(BDConexion conn) {
        this.conn = conn;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
