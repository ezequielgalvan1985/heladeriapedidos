package adaptivex.pedidoscloud.Core;

import android.util.Log;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import adaptivex.pedidoscloud.Config.Constants;

/**
 * Created by egalvan on 21/3/2018.
 */

public class WorkDate {

    public WorkDate(){}

    public static String getNowYMDString(){
        try{
            //devuelve la fecha del dia en formato String
            Date fecha = new Date();
            Calendar cal = Calendar.getInstance();
            fecha = cal.getTime();
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            String fechaYMD = df1.format(fecha);
            return fechaYMD;
        }catch(Exception e){
            Log.d("WorkDate: " , e.getMessage());
            return null;
        }
    }

    public static String getNowString(String format){
        try{
            //devuelve la fecha del dia en formato String
            //valores para format "yyyy-MM-dd"
            Date fecha = new Date();
            Calendar cal = Calendar.getInstance();
            fecha = cal.getTime();
            DateFormat df1 = new SimpleDateFormat(format);
            String fechaStr = df1.format(fecha);
            return fechaStr;
        }catch(Exception e){
            Log.d("WorkDate: " , e.getMessage());
            return null;
        }
    }

    public static Date parseStringToDate(String paramFecha){
        try {
            SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = format.parse(paramFecha);
            System.out.println(date);
            return date;
        } catch (ParseException e) {
            Log.d("WorkDate: " , e.getMessage());
            return null;
        }
    }
    public static Date parseStringToTime(String paramFecha){
        try {
            String fecha = paramFecha.substring(0,19);
            //String fecha = paramFecha.replace(" ","'T'");
            SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(fecha);
            System.out.println(date);
            return date;
        } catch (ParseException e) {
            Log.d("WorkDate: " , e.getMessage());
            return null;
        }
    }

    public static String getHourMinutesStringFromDate(Date date){
        try{
            DateFormat df1 = new SimpleDateFormat("HH:mm");
            String fechaStr = df1.format(date);
            return fechaStr;
        }catch(Exception e){
            Log.d("Pedido: ", e.getMessage().toString());
            return null;
        }
    }


    public static String parseDateToString(Date paramFecha){
        try{
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaStr = df1.format(paramFecha);
            return fechaStr;
        }catch(Exception e){
            Log.d("Pedido: ", e.getMessage().toString());
            return null;
        }
    }
    public static String parseDateToStringFormatHHmmss(Date paramFecha){
        try{
            DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
            String fechaStr = df1.format(paramFecha);
            return fechaStr;
        }catch(Exception e){
            Log.d("Pedido: ", e.getMessage().toString());
            return null;
        }
    }

    public static String calculateDiffereceDatesFormatMM(Date fecha1, Date fecha2){
        try{
            long milisegundos = fecha1.getTime() - fecha2.getTime();
            long minutos = (milisegundos/(1000*60)) % 60;

            if (minutos < 0 ){
                minutos = 0;
            }
            String diff = String.valueOf(minutos) + " Min.";
            return diff;
        }catch(Exception e){
            Log.d("Pedido: ", e.getMessage().toString());
            return null;
        }
    }

    public static Date getNowDate(){
        try{
            Date hoy     = new Date();
            Calendar cal = Calendar.getInstance();
            hoy          = cal.getTime();
            return hoy;
        }catch(Exception e){
            Log.d("Pedido: ", e.getMessage().toString());
            return null;
        }
    }




    public static String convertDateToString(String format, Integer dia, Integer mes, Integer anio){
        try{
            //devuelve la fecha del dia en formato String
            //valores para format "yyyy-MM-dd"

            Calendar cal = Calendar.getInstance();
            cal.set(anio,mes,dia);
            Date fecha = cal.getTime();
            DateFormat df1 = new SimpleDateFormat(format);
            String fechaStr = df1.format(fecha);
            return fechaStr;
        }catch(Exception e){
            Log.d("WorkDate: " , e.getMessage());
            return null;
        }
    }
    public static String convertDateToStringYMD( Integer dia, Integer mes, Integer anio){
        try{
            //devuelve la fecha del dia en formato String
            //valores para format "yyyy-MM-dd"
            String format = "yyyy-MM-dd";
            Calendar cal = Calendar.getInstance();
            cal.set(anio,mes,dia);
            Date fecha = cal.getTime();
            DateFormat df1 = new SimpleDateFormat(format);
            String fechaStr = df1.format(fecha);
            return fechaStr;
        }catch(Exception e){
            Log.d("WorkDate: " , e.getMessage());
            return null;
        }
    }

    public static Date parseStringToDate(String fecha, String formatFecha){
        try{
            //Esta funcion sirve para convertir la fecha guardada en SQLITE,
            // pasarlo a DATE y luego hacer calculos con las fechas

            DateFormat df = new SimpleDateFormat(formatFecha);
            Date result =  df.parse(fecha);
            return result;
        }catch(Exception e){
            Log.d("WorkDate: " , e.getMessage());
            return null;
        }
    }



    public static String changeFormatDateString(String fecha, String formatOrigin, String formatFinal){
        try{
            // Convertir a Date
            Calendar cal = Calendar.getInstance();
            Date date = parseStringToDate(fecha, formatOrigin);
            cal.setTime(date);
            String fechafinal = convertDateToString(formatFinal ,cal.DAY_OF_MONTH, cal.MONTH,cal.YEAR );
            return fechafinal;
        }catch(Exception e){
            Log.d("WorkDate: " , e.getMessage());
            return null;
        }
    }




}
