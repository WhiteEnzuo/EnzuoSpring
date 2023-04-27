package Main.studySpring.Spring.beanInit;

import Main.studySpring.Spring.annotation.Controller;
import Main.studySpring.Spring.annotation.Request;
import Main.studySpring.Spring.bean.BeanFactory;
import Main.studySpring.Spring.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Classname ControllerInit
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/22 22:23
 * @Created by Enzuo
 */

public class ControllerInit {
    private final ApplicationContext context;
    private final Map<String,Map<String, Method>> controllerContext;
    public ControllerInit(ApplicationContext context, Map<String, Map<String, Method>> controllerContext){
        this.context=context;
        this.controllerContext = controllerContext;
    }

    public void init(){
        BeanFactory beanFactory = context.getBeanFactory();
        Map<Class<?>, Object> beanContext = beanFactory.getBeanContext();
        Set<Map.Entry<Class<?>, Object>> entries = beanContext.entrySet();
        for (Map.Entry<Class<?>, Object> entry : entries) {
            if(entry.getValue()==null)continue;
            Controller controller = entry.getValue().getClass().getAnnotation(Controller.class);
            if(controller==null)continue;
            String key = controller.value();
            Map<String,Method> requestMap = new HashMap<>();
            Method[] methods = entry.getValue().getClass().getMethods();
            for (Method method : methods) {
                Request request = method.getAnnotation(Request.class);
                if(request==null)continue;
                String url = request.value();
                requestMap.put(url,method);
            }
            controllerContext.put(key,requestMap);
        }
    }
}
