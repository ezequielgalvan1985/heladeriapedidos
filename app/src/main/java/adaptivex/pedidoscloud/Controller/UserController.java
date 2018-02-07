package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.Servicios.HelperUser;

/**
 * Created by ezequiel on 28/05/2016.
 */
public class UserController extends AppController{
    private User user;
    private User[] users;
    private boolean wreturn;
    private String tabla = "User";
    private Context context;



    public UserController(Context context){
        this.setContext(context);
    }

    public boolean add(User userParam){

        //cargar valores
        ContentValues valores = new ContentValues();
        valores.put("username",userParam.getUsername());
        valores.put("email", userParam.getEmail());
        valores.put("password",userParam.getPassword());

        try {
            this.getConn().add(tabla, valores);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wreturn;

    }

    public boolean edit(User userParam){

        return wreturn;

    }
    public boolean delete(int id){

        return wreturn;

    }
    public boolean login(String username, String pass){
        boolean result = false;
        HelperUser hu = new HelperUser(this.getContext());
        //hu.setUser(this.getUser());
        hu.execute();


        return result;

    }
    public boolean isUserLogin(){
        if (GlobalValues.getINSTANCIA().getUSER_ID_LOGIN() < 1){
            return false;
        }else{
            return true;
        }
    }


    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
