package adaptivex.pedidoscloud.Core;

import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
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
        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date result =  df.parse(paramFecha);
            return result;
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
