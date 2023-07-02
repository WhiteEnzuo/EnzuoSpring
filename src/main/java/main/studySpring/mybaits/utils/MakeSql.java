package main.studySpring.mybaits.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Locale;

/**
 * @Classname MakeSql
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/26 12:50
 * @Created by Enzuo
 */
@Slf4j
public class MakeSql {
    public static String select(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        for (Field field : fields) {
            sb.append(field.getName()).append(",");
        }
        if (sb.charAt(sb.length() - 1) == ',') sb.deleteCharAt(sb.length() - 1);
        sb.append(" from ").append(clazz.getSimpleName().toLowerCase(Locale.ROOT)).append(";");
        return sb.toString();
    }

    public static String select(Class<?> clazz, Query query) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        for (Field field : fields) {
            sb.append(field.getName()).append(",");
        }
        if (sb.charAt(sb.length() - 1) == ',') sb.deleteCharAt(sb.length() - 1);
        sb.append(" from ").append(clazz.getSimpleName().toLowerCase(Locale.ROOT)).append(" ").append(query).append(";");
        return sb.toString();
    }

    public static String selectById(Class<?> clazz, Serializable id) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        for (Field field : fields) {
            sb.append(field.getName()).append(",");
        }
        if (sb.charAt(sb.length() - 1) == ',') sb.deleteCharAt(sb.length() - 1);
        sb.append(" from ")
                .append(clazz.getSimpleName().toLowerCase(Locale.ROOT))
                .append(" where id = ")
                .append(id)
                .append(";");
        return sb.toString();
    }

    public static String insert(Object o) {
        try {
            Class<?> objectClass = o.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            StringBuilder sb = new StringBuilder();
            sb.append("insert into ");
            sb.append(objectClass.getSimpleName().toLowerCase(Locale.ROOT));
            sb.append("(");
            for (Field field : fields) {
                sb.append(field.getName()).append(",");
                field.setAccessible(true);
            }
            if (sb.charAt(sb.length() - 1) == ',') sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            sb.append(" values");
            sb.append("(");
            for (Field field : fields) {
                Object value = field.get(o);
                if (value instanceof Number) {
                    sb.append(value).append(",");
                    continue;
                }
                sb.append("'").append(value).append("'").append(",");
            }
            if (sb.charAt(sb.length() - 1) == ',') sb.deleteCharAt(sb.length() - 1);
            sb.append(")").append(";");
            return sb.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "";
    }

    public static String update(Object o, Query query) {
        try {
            Class<?> objectClass = o.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            StringBuilder sb = new StringBuilder();
            sb.append("update ");
            sb.append(objectClass.getSimpleName().toLowerCase(Locale.ROOT));
            sb.append(" set ");
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(o);
                if (value instanceof Number) {
                    sb.append(field.getName()).append("=").append(value).append(",");

                    continue;
                }
                sb.append(field.getName()).append("=").append("'").append(value).append("'").append(",");
            }
            if (sb.charAt(sb.length() - 1) == ',')
                sb.deleteCharAt(sb.length() - 1);
            sb.append(" ").append(query).append(";");
            return sb.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "";
    }

    public static String delete(Object o, Query query) {
        try {
            Class<?> objectClass = o.getClass();
            return "delete from " +
                    objectClass.getSimpleName().toLowerCase(Locale.ROOT) +
                    " " + query + ";";
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "";
    }

    public static String deleteById(Class<?> clazz, Serializable id) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            return "delete from " +
                    clazz.getSimpleName().toLowerCase(Locale.ROOT) +
                    " where id = " + id + ";";
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "";
    }

}
