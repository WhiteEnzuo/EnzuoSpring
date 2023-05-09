package Main.studySpring.Spring.beanInit;

import Main.studySpring.Spring.annotation.controller.Controller;
import Main.studySpring.Spring.annotation.controller.Mapping.*;
import Main.studySpring.Spring.annotation.controller.RestController;
import Main.studySpring.Spring.bean.BeanFactory;
import Main.studySpring.Spring.context.ApplicationContext;
import Main.studySpring.utils.annotation.AnnotationUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Classname ControllerInit
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/22 22:23
 * @Created by Enzuo
 */
@Slf4j
public class ControllerInit {
    private final ApplicationContext context;
    private final Map<String, Map<String, Map<String, Method>>> controllerContext;

    public ControllerInit(ApplicationContext context, Map<String, Map<String, Map<String, Method>>> controllerContext) {
        this.context = context;
        this.controllerContext = controllerContext;
    }

    public void init() {
        controllerContext.put("all", new HashMap<>());
        controllerContext.put("put", new HashMap<>());
        controllerContext.put("delete", new HashMap<>());
        controllerContext.put("post", new HashMap<>());
        controllerContext.put("get", new HashMap<>());
        BeanFactory beanFactory = context.getBeanFactory();
        Map<Class<?>, Object> beanContext = beanFactory.getBeanContext();
        Set<Map.Entry<Class<?>, Object>> entries = beanContext.entrySet();
        for (Map.Entry<Class<?>, Object> entry : entries) {
            if (entry.getValue() == null) continue;
            ArrayList<Class<? extends Annotation>> list = new ArrayList<>();
            boolean flag = isController(entry.getValue().getClass(), list);
            if (!flag) continue;
            if (list.size() == 0) {
                continue;
            }
            Class<? extends Annotation> aClass = list.get(0);
            Annotation annotation = entry.getValue().getClass().getAnnotation(aClass);
            String key = "";
            try {
                key = (String) annotation.annotationType().getMethod("value").invoke(annotation);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            Map<String, Method> allMap = new HashMap<>();
            Map<String, Method> getMap = new HashMap<>();
            Map<String, Method> postMap = new HashMap<>();
            Map<String, Method> putMap = new HashMap<>();
            Map<String, Method> deleteMap = new HashMap<>();
            Method[] methods = entry.getValue().getClass().getMethods();
            for (Method method : methods) {
                AllMapping allMapping = method.getAnnotation(AllMapping.class);
                if (allMapping != null) {
                    String url = allMapping.value();
                    allMap.put(url, method);
                }
                DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
                if (deleteMapping != null) {
                    String url = deleteMapping.value();
                    deleteMap.put(url, method);
                }
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                if (getMapping != null) {
                    String url = getMapping.value();
                    getMap.put(url, method);
                }
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                if (postMapping != null) {
                    String url = postMapping.value();
                    postMap.put(url, method);
                }
                PutMapping putMapping = method.getAnnotation(PutMapping.class);
                if (putMapping != null) {
                    String url = putMapping.value();
                    putMap.put(url, method);
                }
            }
            controllerContext.get("all").put(key, allMap);
            controllerContext.get("get").put(key, getMap);
            controllerContext.get("put").put(key, putMap);
            controllerContext.get("delete").put(key, deleteMap);
            controllerContext.get("post").put(key, postMap);
        }
    }

    private boolean isController(Class<?> clazz, List<Class<? extends Annotation>> list) {
        return AnnotationUtils.isAnnotation(clazz, Controller.class, list);
    }
}
