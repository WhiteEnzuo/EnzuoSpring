package Main.studySpring.Mybaits.MapperInterface.impl;

import Main.studySpring.Mybaits.jdbc.MysqlJDBC;
import Main.studySpring.Mybaits.utils.MakeSql;
import Main.studySpring.Mybaits.MapperInterface.BaseMapper;
import Main.studySpring.Mybaits.utils.Query;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname BaseMapperImpl
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/26 13:17
 * @Created by Enzuo
 */

public class BaseMapperInvoke implements InvocationHandler {
    private Class<?> clazz;
    public BaseMapperInvoke(Class<?> clazz){
        this.clazz=clazz;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(BaseMapper.class.getMethod("selectById", Serializable.class).equals(method)){
            Serializable arg = (Serializable) args[0];
            String s = MakeSql.selectById(clazz, arg);
            List<?> select = MysqlJDBC.select(s, clazz);
            if(select==null||select.size()==0){
                return null;
            }
            return select.get(0);
        }
        if(BaseMapper.class.getMethod("select").equals(method)){
            String s = MakeSql.select(clazz);
            List<?> select = MysqlJDBC.select(s, clazz);
            if(select==null||select.size()==0)return new ArrayList<>();
            return select;
        }
        if(BaseMapper.class.getMethod("select", Query.class).equals(method)){
            Query arg = (Query) args[0];
            String s = MakeSql.select(clazz, arg);
            List<?> select = MysqlJDBC.select(s, clazz);
            if(select==null||select.size()==0)return new ArrayList<>();
            return select ;
        }
        if(BaseMapper.class.getMethod("insert", Object.class).equals(method)){
            Object arg = args[0];
            String insert = MakeSql.insert(arg);
            return MysqlJDBC.delete(insert);
        }
        if(BaseMapper.class.getMethod("update", Object.class, Query.class).equals(method)){
            Object arg = args[0];
            Query arg1 = (Query)args[1];
            String update = MakeSql.update(arg, arg1);

            return MysqlJDBC.update(update);
        }
        if(BaseMapper.class.getMethod("delete", Object.class, Query.class).equals(method)){
            Object arg = args[0];
            Query arg1 = (Query)args[1];
            String delete = MakeSql.delete(arg, arg1);
            return MysqlJDBC.delete(delete);
        }
        if(BaseMapper.class.getMethod("deleteById", Serializable.class).equals(method)){
            Serializable arg = (Serializable) args[0];
            String delete = MakeSql.deleteById(clazz, arg);
            return MysqlJDBC.delete(delete);
        }
        return this.toString();
    }
}
