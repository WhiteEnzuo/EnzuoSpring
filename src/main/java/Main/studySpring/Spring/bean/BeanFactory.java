package Main.studySpring.Spring.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname BeanFactory
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 13:53
 * @Created by Enzuo
 */

public class BeanFactory {
    private  Map<Class<?>,Object> beanContext;
    public  Map<Class<?>, Object> getBeanContext() {
        if(beanContext==null)beanContext=new HashMap<>();
        return beanContext;
    }
    public  void putBean(Class<?> clazz,Object o){
        if(beanContext==null)beanContext=new HashMap<>();
        beanContext.put(clazz,o);
    }
    public  void  setBeanContext(Map<Class<?>, Object> context) {
        beanContext= context;
    }
    public   <T> T getBean(Class<T> clazz) {
        if(beanContext==null||(!beanContext.containsKey(clazz)))return null;
        return (T)beanContext.get(clazz);
    }

}
