package Main.studySpring.Spring.beanInit;

import Main.studySpring.Spring.annotation.bean.Autowired;
import Main.studySpring.Spring.annotation.bean.Value;
import Main.studySpring.Spring.bean.BeanFactory;
import Main.studySpring.Spring.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        initBeanPropertyByValue();
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
    private void initBeanPropertyByValue() {
        BeanFactory beanFactory = context.getBeanFactory();
        Map<Class<?>, Object> beanContext = beanFactory.getBeanContext();
        Set<Map.Entry<Class<?>, Object>> entries = beanContext.entrySet();
        for (Map.Entry<Class<?>, Object> entry : entries) {
            Object needObject = entry.getValue();
            if(needObject==null)continue;
            Class<?> objectClass = needObject.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                Value annotation = field.getAnnotation(Value.class);
                if (annotation == null) continue;
                String value = annotation.value();
                String pattern = "\\$\\{(.*?)\\}";
                Pattern compile = Pattern.compile(pattern);
                Matcher matcher = compile.matcher(value);
                field.setAccessible(true);
                if (matcher.find()) {
                    value = matcher.group(1);
                    try {
                        Map<String, Object> config = context.getConfig();
                        String[] split = value.split("\\.");
                        if(split.length==0){continue;}
                        Object assignmentObject=null;
                        Object o = config.get(split[0]);
                        if(o==null)continue;
                        if(!(o instanceof Map) ){
                            assignmentObject=config.get(split[0]);
                        }else {
                            Map returnMap = (Map)o;
                            for (int i = 1; i < split.length; i++) {
                                Object o1 = returnMap.get(split[i]);
                                if(o1==null)break;
                                if(!(o1 instanceof Map)){
                                    assignmentObject=o1;
                                    break;
                                }
                                returnMap=(Map)o1;
                            }
                        }
                        field.set(needObject, assignmentObject);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        System.exit(0);
                    }
                }else {
                    try {
                        field.set(needObject, value);
                    }catch (Exception e) {
                        log.error(e.getMessage());
                        System.exit(0);
                    }

                }


            }
        }
    }
}
