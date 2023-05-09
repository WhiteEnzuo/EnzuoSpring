package Main.studySpring.Spring.beanInit;

import Main.studySpring.Spring.annotation.Aspect.Aspect;
import Main.studySpring.Spring.bean.BeanFactory;
import Main.studySpring.Spring.beanInit.model.AspectProxy;
import Main.studySpring.Spring.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;

/**
 * @Classname AspectInit
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/28 9:55
 * @Created by Enzuo
 */
@Slf4j
public class AspectInit {
    private final ApplicationContext context;
    public AspectInit(ApplicationContext context){
        this.context=context;
    }
    public void initAspect(){
        initAspectByPrivate();
    }
    public void initAspectByPrivate(){
        BeanFactory beanFactory = context.getBeanFactory();
        Map<Class<?>, Object> beanContext = beanFactory.getBeanContext();
        Set<Map.Entry<Class<?>, Object>> entries = beanContext.entrySet();
        for (Map.Entry<Class<?>, Object> entry : entries) {
            Object value = entry.getValue();
            if(value==null){continue;}
            Aspect aspect = value.getClass().getAnnotation(Aspect.class);
            if(aspect==null)continue;
            Class<?> proxyObjectClass = aspect.value();
            try {
                if(proxyObjectClass.isInterface())continue;
//                System.out.println(value.getClass().getClassLoader());
                Object o = Proxy.newProxyInstance(entry.getKey().getClassLoader(), new Class[]{entry.getKey()}, new AspectProxy(entry.getValue(),proxyObjectClass));
                beanContext.put(entry.getKey(),o);
            }catch (Exception e){
                log.error(e.getMessage());
            }

        }
    }
}
