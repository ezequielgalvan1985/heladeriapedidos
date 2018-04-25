package adaptivex.pedidoscloud.Core;

import android.text.TextUtils;

/**
 * Created by egalvan on 25/4/2018.
 */

public class WorkString {

    public static String getTexto(String texto){
        String response;
        try{
            if (TextUtils.isEmpty(texto)){
                response = "";
            }else{
                response = texto;
            }
            return response;
        }catch(Exception e){
            response = "";
            return response;
        }
    }
}
