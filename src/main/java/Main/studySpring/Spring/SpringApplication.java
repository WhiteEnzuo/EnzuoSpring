package Main.studySpring.Spring;


import Main.studySpring.Spring.Listeners.ApplicationListenerStarter;
import Main.studySpring.Spring.Listeners.ApplicationListeners;
import Main.studySpring.Spring.annotation.bean.Bean;
import Main.studySpring.Spring.beanInit.AspectInit;
import Main.studySpring.Spring.beanInit.BeanWired;
import Main.studySpring.Spring.beanInit.ControllerInit;
import Main.studySpring.Spring.context.ApplicationContext;
import Main.studySpring.Spring.inteceptor.context.HttpInterceptorContext;
import Main.studySpring.Spring.inteceptor.init.HttpInterceptorInit;
import Main.studySpring.global.UrlMap;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @Classname SpringApplication
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/19 20:31
 * @Created by Enzuo
 */
@Bean
@Slf4j
public class SpringApplication {
    public static void run(ApplicationContext context,Class<?> clazz,String[] args) {
        log.info("创建IOC容器");
        log.info("创建IOC容器成功");
        log.info("初始化监听器");
        ApplicationListenerStarter applicationListenerStarter = new ApplicationListenerStarter(context);
        applicationListenerStarter.getResource(clazz);
        ApplicationListeners listeners = applicationListenerStarter.stater();
        listeners.starting();
        listeners.environmentPrepared();
        log.info("初始化IOC容器");
        context.initContext(clazz);
        log.info("初始化IOC容器成功");
        listeners.contextPrepared();
        log.info("切面注入");
        AspectInit aspectInit = new AspectInit(context);
        aspectInit.initAspect();
        log.info("切面注入完成");
        log.info("Bean的注入");
        new BeanWired(context).initBeanProperty();
        log.info("Bean的注入成功");
        log.info("Controller注入");
        UrlMap.controllerContext=new HashMap<>();
        ControllerInit controllerInit = new ControllerInit(context,UrlMap.controllerContext);
        controllerInit.init();
        log.info("Controller注入成功");
        listeners.contextLoaded();
        listeners.started();
        listeners.running();
        log.info("EnzuoSpring启动成功");
    }
}
