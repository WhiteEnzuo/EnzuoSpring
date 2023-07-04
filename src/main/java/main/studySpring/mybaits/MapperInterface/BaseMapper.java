package main.studySpring.mybaits.MapperInterface;

import main.studySpring.mybaits.utils.Query;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname BaseMapper
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/26 12:54
 * @Created by Enzuo
 */

public interface BaseMapper<T> {
    T selectById(Serializable id);

    List<T> select();

    List<T> select(Query query);

    int insert(T obj);

    int update(T obj, Query query);

    int delete(T obj, Query query);

    boolean deleteById(Serializable id);
}
