package adaptivex.pedidoscloud.Core.Sql;

import java.util.ArrayList;

/**
 * Created by egalvan on 25/3/2018.
 */

public class SqlWhere {
    private String field;
    private String arismeticOperator; // =, <=, >=
    private String value;
    private String conditionOperator; //AND OR


    public SqlWhere(){

    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }



    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getWhere(){
        return getField() + getArismeticOperator() + "? " + getConditionOperator();
    }





    public String getArismeticOperator() {
        return arismeticOperator;
    }

    public void setArismeticOperator(String arismeticOperator) {
        this.arismeticOperator = arismeticOperator;
    }

    public String getConditionOperator() {
        if (conditionOperator==null){
            conditionOperator = "";
        }
        return conditionOperator;
    }

    public void setConditionOperator(String conditionOperator) {
        this.conditionOperator = conditionOperator;
    }



}
