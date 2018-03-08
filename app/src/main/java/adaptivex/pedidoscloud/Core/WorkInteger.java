package adaptivex.pedidoscloud.Core;

/**
 * Created by egalvan on 7/3/2018.
 */

public  class WorkInteger {
    public WorkInteger(){}

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
}
