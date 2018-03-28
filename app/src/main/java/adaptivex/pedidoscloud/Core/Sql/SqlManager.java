package adaptivex.pedidoscloud.Core.Sql;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Config.Constants;

/**
 * Created by egalvan on 25/3/2018.
 */

public class SqlManager {
    private static ArrayList<SqlWhere> listaWhere =new ArrayList<SqlWhere>();

    public SqlManager (){

    }

    public static String getConditions (){
        //CADA CAMPO, O SEA CADA CONDICION
        String cadena = "";
        for(SqlWhere w : getListaWhere()){
            cadena += w.getWhere() + " ";
        }
        return cadena;
    }

    public static String[] getArguments(){
        //LOS VALORES DE CADA CONDICION
        ArrayList<String> arg = new ArrayList<String>();

        for(SqlWhere w : getListaWhere()){
            arg.add(w.getValue());
        }
        String[] argumentos = new String[arg.size()];
        arg.toArray(argumentos);
        return argumentos;
    }

    public static void addWhere(SqlWhere sqlWhere){
        getListaWhere().add(sqlWhere);
    }

    public static ArrayList<SqlWhere> getListaWhere() {
        return listaWhere;
    }

    public static void setListaWhere(ArrayList<SqlWhere> listaWhere) {
        SqlManager.listaWhere = listaWhere;
    }

    public static void addWhere(String field, String operator, String value){
        SqlWhere w = new SqlWhere();
        w.setField(field);
        w.setArismeticOperator(operator);
        w.setValue(value);

        listaWhere.add(w);
    }
    public void addWhereAnd(String field, String operator, String value){
        SqlWhere w = new SqlWhere();
        w.setField(field);
        w.setArismeticOperator(operator);
        w.setValue(value);
        w.setConditionOperator(Constants.AND);

        listaWhere.add(w);
    }

    public void addWhereOr(String field, String operator, String value){
        SqlWhere w = new SqlWhere();
        w.setField(field);
        w.setArismeticOperator(operator);
        w.setValue(value);
        w.setConditionOperator(Constants.OR);
        listaWhere.add(w);
    }
}
