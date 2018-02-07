package adaptivex.pedidoscloud.Core;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ezequiel on 26/8/2017.
 */

public class SystemFileHelper {
    private Context ctx;
    private String file;


    public SystemFileHelper(Context ctx, String file){

        this.setCtx(ctx);
        this.setFile(file);
        if (!existsFile(getFile())){
            createFile(getFile());
        }
    };

    public SystemFileHelper(Context ctx){
        //Valor default de file es CONFIGFILE
        this.setCtx(ctx);
        //this.setFile(GlobalValues.getINSTANCIA().PARAM_CONFIGFILE);
        if (!existsFile(getFile())){
            createFile(getFile());
        }
    };

    public boolean createFile(String file){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getCtx().openFileOutput(getFile(), Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();
            return true;
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }
    }

    public boolean putData(String key, String value){
        try{
            String nuevovalor = key +"="+ value+",";
            String values = readFromFile();

            //Existe key, entonces reemplazo, de lo contrario agrego
            if(values.indexOf(key)> 0 ){

                //Leer ClaveValoractual
                String valoractual = "";
                List<String> valueList = Arrays.asList(values.split(","));
                for (String keyvalue : valueList) {
                    if (keyvalue.startsWith(key)){
                        valoractual = keyvalue;
                        break;
                    }
                }
                //reemplazar valor actual con nuevo valor
                values = values.replaceAll(valoractual+"=", nuevovalor);
            }else {
                values+=nuevovalor;
            }

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getCtx().openFileOutput(getFile(), Context.MODE_PRIVATE));
            outputStreamWriter.write(values);
            outputStreamWriter.close();
            return true;
        }catch(Exception e ){
            return false;
        }
    }
    public void blanquearConfig(){
        createFile(getFile());
    }
    public String readData(String pKey){
        try{
            String value = "";
            String values = readFromFile();
            List<String> valueList = Arrays.asList(values.split(","));

            for (String keyvalue : valueList) {

                if (keyvalue.startsWith(pKey)){
                    // se encontro la clave
                    //leer el valor
                    int posigual = keyvalue.indexOf("="); // posicion del igual
                    value = keyvalue.substring(posigual+1,keyvalue.length() );
                    Log.d("SystemFileHelper",value);
                    return value;
                }
            }

            return value;
        }catch (Exception e ){
            Log.d("SystemFileHelper",e.getMessage());
            return null;
        }
    }

    public boolean existsFile(String pFile){
        try {
            InputStream inputStream = getCtx().openFileInput(pFile);
            if ( inputStream != null ) {
                return true;
            }else{
                return false;
            }
        }catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return false;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            return false;
        }
    }

    public String readFromFile() {
        String ret = "";
        try {
            InputStream inputStream = getCtx().openFileInput(getFile());

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    private void writeToFile(String data, Context context, String pFile) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(pFile, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
