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




    public boolean checkLocalOpen(Date hour){
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

            long from   = h.getApertura().getHours() * 60 + h.getApertura().getMinutes();
            long to     = h.getCierre().getHours() * 60 + h.getCierre().getMinutes();
            long t      = hour.getHours() *60 + hour.getMinutes() ;//c.get(Calendar.HOUR_OF_DAY) * 100 + c.get(Calendar.MINUTE);

            //comparar si hour esta entre las dos horas
            boolean isBetween = to > from && t >= from && t <= to || to < from && (t >= from || t <= to);


            //ejemplo: hora pedido 11:00  -  apertura 10:00  - cierre 00:00
            //Preguntar si el horario del pedido esta dentro del horario de apretura y cierre


            validate = isBetween;
            if (!validate){
                String horapertura = WorkDate.getHourMinutesStringFromDate(h.getApertura());
                String horacierre  = WorkDate.getHourMinutesStringFromDate(h.getCierre());
                String texto       = "En este momento el ROMA HELADOS se encuentra cerrado, nuestro horario de antención es de "+ horapertura+ " a " + horacierre + ". ";
                Toast.makeText(ctx, texto,Toast.LENGTH_LONG).show();
            }

            return validate;

        }catch(Exception e){
            Toast.makeText(ctx,"Error BusinessRules:"+ e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }

    }
}
