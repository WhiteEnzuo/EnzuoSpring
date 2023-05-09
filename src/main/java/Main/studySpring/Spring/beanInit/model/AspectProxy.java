package Main.studySpring.Spring.beanInit.model;

import Main.studySpring.Spring.annotation.Aspect.After;
import Main.studySpring.Spring.annotation.Aspect.Before;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AspectProxy
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/28 10:00
 * @Created by Enzuo
 */

public class AspectProxy implements InvocationHandler {
    private Class<?> clazz;
    private Class<?> clazzProxy;
    private Map<String,Method> methodMap;
    private Object object;
    public AspectProxy(Object object, Class<?> clazzProxy){
        this.object = object;
        this.clazz=object.getClass();
        this.clazzProxy=clazzProxy;
        HashMap<String, Method> methodMap = new HashMap<>();
        Method[] methods = clazzProxy.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            methodMap.put(name,method);
        }
        this.methodMap=methodMap;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method objectMethod = object.getClass().getMethod(method.getName(), method.getParameterTypes());

        Before before = objectMethod.getAnnotation(Before.class);
        if (before!=null) {
            return BeforeMethod(objectMethod,args,null,before);
        }
        After after = objectMethod.getAnnotation(After.class);
        if (after!=null) {
            return AfterMethod(objectMethod,args,null,after);
        }
        return objectMethod.invoke(this.object, args);
    }
    private Object BeforeMethod(Method method,Object[] args,Object[] methodsArgs,Before before) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        String value = before.value();
        Method proxyMethod = null;
        if (!this.methodMap.containsKey(value)) {
            return null;
        }
        proxyMethod = methodMap.get(value);
        proxyMethod.invoke(clazzProxy.newInstance(), methodsArgs);
        Object invoke = method.invoke(this.object, args);
        return invoke;
    }
    public Object AfterMethod(Method method, Object[] args, Object[] methodsArgs,After after) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        String value = after.value();
        Method proxyMethod = null;
        if (!this.methodMap.containsKey(value)) {
            return null;
        }
        proxyMethod = methodMap.get(value);
        Object invoke = method.invoke(this.object, args);
        proxyMethod.invoke(clazzProxy.newInstance(), methodsArgs);
        return invoke;
    }
}
