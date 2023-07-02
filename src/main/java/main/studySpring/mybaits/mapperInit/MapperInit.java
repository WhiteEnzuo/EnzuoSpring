package main.studySpring.mybaits.mapperInit;

import main.studySpring.mybaits.MapperInterface.BaseMapper;
import main.studySpring.mybaits.MapperInterface.impl.BaseMapperInvoke;
import main.studySpring.mybaits.annotation.Mapper;
import main.studySpring.Spring.bean.BeanFactory;
import main.studySpring.Spring.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname MapperInit
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/27 21:37
 * @Created by Enzuo
 */
@Slf4j
public class MapperInit {
    public  void init(ApplicationContext context){
        try {
            if(context==null) {
                log.error("MapperInit错误");
                return;
            }
            BeanFactory beanFactory = context.getBeanFactory();
            if(beanFactory==null) {
                log.error("MapperInit错误");
                return;
            }
            Map<Class<?>, Object> beanContext = beanFactory.getBeanContext();
            if(beanContext==null) {
                log.error("MapperInit错误");
                return;
            }
            Set<Map.Entry<Class<?>, Object>> entries = beanContext.entrySet();
            for (Map.Entry<Class<?>, Object> entry : entries) {
                Class<?> key = entry.getKey();
                Mapper annotation = key.getAnnotation(Mapper.class);
                if(annotation==null)continue;
                Type temp = null;
                Type[] genericInterfaces = key.getGenericInterfaces();
                for (Type genericInterface : genericInterfaces) {
                    String typeName = genericInterface.getTypeName();
                    if(typeName.contains(BaseMapper.class.getName())){
                        temp=genericInterface;
                        break;
                    }
                }
                if (temp==null) {
                    continue;
                }
                String pattern = "(.*)<(.*)>";
                Pattern compile = Pattern.compile(pattern);
                Matcher matcher = compile.matcher(temp.getTypeName());
                String genericName = "";
                if(matcher.find()){
                    try {
                        genericName= matcher.group(2);
                    }catch (Exception e){
                        continue;
                    }
                }

                Object o = Proxy.newProxyInstance(key.getClassLoader(), new Class[]{key},new BaseMapperInvoke(Class.forName(genericName)));
                beanContext.put(key,o);
            }
        }catch (Exception exception){
            log.error(exception.getMessage());
        }
    }
}
