package adaptivex.pedidoscloud.Core;

import android.content.Context;
import android.widget.Toast;

import java.sql.Time;

public class BusinessRules {
    private Context ctx;

    public BusinessRules(Context ctx){
        this.ctx = ctx;
    }
    public boolean checkLocalOpen(Time hour){
        boolean validate = false;
        try{
            //obtener dia de la semana
            //obtener horario de apertura y cierre del local
            //comparar si hour esta entre las dos horas


            validate = true;
            return validate;

        }catch(Exception e){
            Toast.makeText(ctx,"Error BusinessRules:"+ e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }

    }
}
