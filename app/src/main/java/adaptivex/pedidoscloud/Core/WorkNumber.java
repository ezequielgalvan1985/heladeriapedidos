package adaptivex.pedidoscloud.Core;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by egalvan on 7/3/2018.
 */

public  class WorkNumber {
    public WorkNumber(){}

    public static Integer parseInteger(String str_numero){
        Integer number =0;
        try
        {
            if(str_numero != null)
                number = Integer.parseInt(str_numero);
        }
        catch (NumberFormatException e)
        {
            number = 0;
        }
       return number;
    }
    public static Double parseDouble(String str_numero){
        Double number =0.0;
        try
        {
            if(str_numero != null)
                number = Double.parseDouble(str_numero);
        }
        catch (NumberFormatException e)
        {
            number = 0.0;
        }
        return number;
    }

    public static Integer getValue(Integer numero){
        try
        {
            if(numero == null){
                return 0;
            }else{
                return numero;
            }
        }
        catch (Exception e)
        {
            numero = 0;
            return numero;
        }
    }


    public static Double getValue(Double numero){
        try
        {
            if(numero == null){
                return 0.0;
            }else{
                return numero;
            }
        }
        catch (Exception e)
        {
            numero = 0.0;
            return numero;
        }
    }

    public static String parseDoubleToString(Double numero){
        //Formatea  a Decimal
        String numeroFormateado= "0.0";
        try {
            numero = getValue(numero);
            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            numeroFormateado = formatter.format(numero);
            return numeroFormateado;
        } catch (Exception e) {
            return numeroFormateado;
        }
    }

    public static String moneyFormat(Double numero){
        try{
            Double money = getValue(numero);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String moneyString = formatter.format(money);
            return moneyString;
        } catch (Exception e) {
            return "0.00";
        }
    }


    public static String kilosFormat(Integer kilos){
        try {
            Integer number = getValue(kilos);
            float medida = 1000;
            float kilosSize = number.floatValue()/1000;
            //Integer kilosSize = number/1000;
            String kilosString = String.valueOf(kilosSize) + " Kg.";
            return kilosString;
        } catch (Exception e) {
            return "0 Kg";
        }
    }

}
