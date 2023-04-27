package Main.studySpring.Mybaits.utils;

import java.io.Serializable;

/**
 * @Classname Query
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/26 12:50
 * @Created by Enzuo
 */

public class Query{
    private StringBuilder whereString ;
    public Query(){
        whereString=new StringBuilder();
    }
    public Query eq(Serializable key, Serializable value){
        if(value instanceof Number){
            whereString.append(key).append("=").append(value).append(",");
        }else {
            whereString.append(key).append("=").append("'").append(value).append("'").append(",");

        }
        return this;
    }
    public Query greater(Serializable key, Serializable value){
        if(value instanceof Number){
            whereString.append(key).append(">").append(value).append(",");
        }else {
            whereString.append(key).append(">").append("'").append(value).append("'").append(",");

        }
        return this;
    }
    public Query less(Serializable key, Serializable value){
        if(value instanceof Number){
            whereString.append(key).append("<").append(value).append(",");
        }else {
            whereString.append(key).append("<").append("'").append(value).append("'").append(",");

        }
        return this;
    }
    public Query lessAndEq(Serializable key, Serializable value){
        if(value instanceof Number){
            whereString.append(key).append("<=").append(value).append(",");
        }else {
            whereString.append(key).append("<=").append("'").append(value).append("'").append(",");

        }
        return this;
    }
    public Query greaterAndEq(Serializable key, Serializable value){
        if(value instanceof Number){
            whereString.append(key).append(">=").append(value).append(",");
        }else {
            whereString.append(key).append(">=").append("'").append(value).append("'").append(",");

        }
        return this;
    }
    public Query notEq(Serializable key, Serializable value){
        if(value instanceof Number){
            whereString.append(key).append("!=").append(value).append(",");
        }else {
            whereString.append(key).append("!=").append("'").append(value).append("'").append(",");

        }
        return this;
    }
    public Query sql(Serializable s){
        whereString.append(s).append(" ");
        return this;
    }
    @Override
    public String toString() {
        if(whereString.length()==0)throw new RuntimeException("Query错误");
        if(whereString.charAt(whereString.length()-1)==',')whereString.deleteCharAt(whereString.length()-1);
        return "where "+whereString;
    }
}

