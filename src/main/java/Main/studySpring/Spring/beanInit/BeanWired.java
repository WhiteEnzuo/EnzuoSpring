package Main.studySpring.Spring.beanInit;

import Main.studySpring.Spring.annotation.Autowired;
import Main.studySpring.Spring.bean.BeanFactory;
import Main.studySpring.Spring.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @Classname BeanWired
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 15:52
 * @Created by Enzuo
 */
@Slf4j
public class BeanWired {
    private final ApplicationContext context;
    public BeanWired(ApplicationContext context){
        this.context=context;
    }
    public void initBeanProperty(){
        initBeanPropertyByPrivate();
    }
    private void initBeanPropertyByPrivate() {
        BeanFactory beanFactory = context.getBeanFactory();
        Map<Class<?>, Object> beanContext = beanFactory.getBeanContext();
        Set<Map.Entry<Class<?>, Object>> entries = beanContext.entrySet();
        for (Map.Entry<Class<?>, Object> entry : entries) {
            Object needObject = entry.getValue();
            if(needObject==null)continue;
            Class<?> objectClass = needObject.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                Autowired annotation = field.getAnnotation(Autowired.class);
                if (annotation == null) continue;

                try {
                    Type genericType = field.getGenericType();
                    field.setAccessible(true);
                    String typeName = genericType.getTypeName();
                    Class<?> typeClass = Class.forName(typeName);
                    Object assignmentObject = beanContext.get(typeClass);
                    if (assignmentObject == null) {
                        throw new RuntimeException(typeName + " in " + needObject + " not have object");
                    }
                    field.set(needObject, assignmentObject);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    System.exit(0);
                }

            }
        }
    }
}
