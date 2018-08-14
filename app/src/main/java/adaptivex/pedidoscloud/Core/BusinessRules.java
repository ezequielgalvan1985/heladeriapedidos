package adaptivex.pedidoscloud.Core;

import android.content.Context;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import adaptivex.pedidoscloud.Controller.HorarioController;
import adaptivex.pedidoscloud.Model.Horario;

public class BusinessRules {
    private Context ctx;

    public BusinessRules(Context ctx){
        this.ctx = ctx;
    }




    public boolean checkLocalOpen(long hourInMiliseconds){
        boolean validate = false;
        try{
            //obtener dia de la semana  de hoy
            Date date       = new Date();
            Calendar c      = Calendar.getInstance();
            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            // obtener hora actual

            //obtener horario de apertura y cierre del local para el dia actual
            HorarioController controller = new HorarioController(this.ctx);
            Horario h = controller.abrir().getByDayOfWeekObject(dayOfWeek);

            long from   = h.getApertura().getTime();
            long to     = h.getCierre().getTime();
            long t      = hourInMiliseconds ;//c.get(Calendar.HOUR_OF_DAY) * 100 + c.get(Calendar.MINUTE);

            //comparar si hour esta entre las dos horas
            boolean isBetween = to > from && t >= from && t <= to || to < from && (t >= from || t <= to);


            //ejemplo: hora pedido 11:00  -  apertura 10:00  - cierre 00:00
            //Preguntar si el horario del pedido esta dentro del horario de apretura y cierre





            validate = isBetween;
            return validate;

        }catch(Exception e){
            Toast.makeText(ctx,"Error BusinessRules:"+ e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }

    }
}
