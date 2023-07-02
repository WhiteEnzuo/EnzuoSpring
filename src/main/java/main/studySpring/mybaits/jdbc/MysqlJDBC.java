package main.studySpring.mybaits.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MysqlJDBC
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/26 19:58
 * @Created by Enzuo
 */
@Slf4j
public class MysqlJDBC {
    private static Connection connection;

    public MysqlJDBC() {

    }
    public static void setConnection(String url,String username,String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.exit(0);
        }
    }
    public static  <T> List<T> select(String sql, Class<T> clazz) {
        Statement statement = null;
        try {
            List<T> list = new ArrayList<>();

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                T t = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    if (type.equals(String.class)) {
                        String value = resultSet.getString(field.getName());
                        field.set(t, value);
                        continue;
                    }
                    if (type.equals(Integer.class)) {
                        int value = resultSet.getInt(field.getName());
                        field.set(t, value);
                        continue;
                    }
                    if (type.equals(Long.class)) {
                        Long value = resultSet.getLong(field.getName());
                        field.set(t, value);
                        continue;
                    }
                    if (type.equals(Byte.class)) {
                        Byte value = resultSet.getByte(field.getName());
                        field.set(t, value);
                        continue;
                    }
                    if (type.equals(Array.class)) {
                        Array value = resultSet.getArray(field.getName());
                        field.set(t, value);
                        continue;
                    }
                    if (type.equals(List.class)) {
                        Boolean value = resultSet.getBoolean(field.getName());
                        field.set(t, value);
                    }

                }
                list.add(t);
            }
            return list;
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
    public static int insert(String sql) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException  e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return 0;
    }
    public static int update(String sql) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException  e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return 0;
    }
    public static int delete(String sql) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException  e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return 0;
    }
}
