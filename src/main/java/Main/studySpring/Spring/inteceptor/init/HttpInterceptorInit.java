package Main.studySpring.Spring.inteceptor.init;

import Main.studySpring.Spring.bean.BeanFactory;
import Main.studySpring.Spring.context.ApplicationContext;
import Main.studySpring.Spring.inteceptor.WebHttpInterceptor;
import Main.studySpring.Spring.inteceptor.context.HttpInterceptorContext;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Classname HttpInterceptorInit
 * @Description
 * @Version 1.0.0
 * @Date 2023/5/8 22:45
 * @Created by Enzuo
 */
@Slf4j
public class HttpInterceptorInit {
    private HttpInterceptorContext interceptorContext;
    private final BeanFactory beanFactory;
    private String classpath;

    public HttpInterceptorInit(HttpInterceptorContext interceptorContext,ApplicationContext context) {
        this.interceptorContext = interceptorContext;
        if(context.getBeanFactory()==null){
            log.error("BeanFactory is null");
            System.exit(0);
        }
        this.beanFactory=context.getBeanFactory();
    }

    public void init(Class<?> clazz) {
        if (interceptorContext == null) interceptorContext = new HttpInterceptorContext();
        String[] split = clazz.getName().split("\\.");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            sb.append(split[i]).append(".");
        }
        if (sb.charAt(sb.length() - 1) == '.') sb.deleteCharAt(sb.length() - 1);
        classpath = sb.toString();
        String path = Objects.requireNonNull(clazz.getResource("")).getPath();
        initInterceptor(new File(path));
    }

    private void initInterceptor(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File pathFile : files) {
                if (pathFile.isDirectory()) {
                    initInterceptor(pathFile);
                    continue;
                }
                if (pathFile.getName().contains(".class")) {
                    String className = pathFile.getPath().substring(pathFile.getPath().indexOf(classpath)).replace(".class", "").replace("\\", ".");
                    try {
                        Class<?> objClass = Class.forName(className);
                        boolean isInterface = objClass.isInterface();
                        if (isInterface) continue;
                        Class<?>[] interfaces = objClass.getInterfaces();
                        if (interfaces.length == 0 || !interfaces[0].equals(WebHttpInterceptor.class)) continue;
                        Object obj = objClass.newInstance();
                        interceptorContext.addHttpInterceptor((WebHttpInterceptor) obj);
                        beanFactory.putBean(objClass,obj);
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
