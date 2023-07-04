package main.studySpring.Spring.inteceptor.init;

import main.studySpring.Spring.bean.BeanFactory;
import main.studySpring.Spring.context.ApplicationContext;
import main.studySpring.Spring.inteceptor.WebHttpInterceptor;
import main.studySpring.Spring.inteceptor.context.HttpInterceptorContext;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

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
    private final ApplicationContext context;

    public HttpInterceptorInit(HttpInterceptorContext interceptorContext, ApplicationContext context) {
        this.interceptorContext = interceptorContext;
        if (context.getBeanFactory() == null) {
            log.error("BeanFactory is null");
            System.exit(0);
        }
        this.beanFactory = context.getBeanFactory();
        this.context = context;
    }

    public void init() {
        if (interceptorContext == null) interceptorContext = new HttpInterceptorContext();
        initInterceptor(new File(context.getClasspath()));
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
                    String className = pathFile.getPath().substring(pathFile.getPath().indexOf(context.getClasspath()))
                            .replace(".class", "").replace("\\", ".");
                    try {
                        Class<?> objClass = Class.forName(className);
                        boolean isInterface = objClass.isInterface();
                        if (isInterface) continue;
                        Class<?>[] interfaces = objClass.getInterfaces();
                        if (interfaces.length == 0 || !interfaces[0].equals(WebHttpInterceptor.class)) continue;
                        Object obj = objClass.newInstance();
                        interceptorContext.addHttpInterceptor((WebHttpInterceptor) obj);
                        beanFactory.putBean(objClass, obj);
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
