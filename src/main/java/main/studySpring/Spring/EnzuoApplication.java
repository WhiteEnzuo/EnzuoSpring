package main.studySpring.Spring;


import main.registry.server.ServerContext;
import main.studySpring.Spring.Listeners.ApplicationListenerStarter;
import main.studySpring.Spring.Listeners.ApplicationListeners;
import main.studySpring.Spring.annotation.bean.Bean;
import main.studySpring.Spring.beanInit.AspectInit;
import main.studySpring.Spring.beanInit.BeanWired;
import main.studySpring.Spring.beanInit.ControllerInit;
import main.studySpring.Spring.context.ApplicationContext;
import main.studySpring.global.UrlMap;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Classname SpringApplication
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/19 20:31
 * @Created by Enzuo
 */
@Bean
@Slf4j
public class EnzuoApplication {
    public static void run(ApplicationContext context,Class<?> clazz,String[] args) {
        log.info("创建IOC容器");
        log.info("创建IOC容器成功");
        log.info("初始化监听器");
        ApplicationListenerStarter applicationListenerStarter = new ApplicationListenerStarter(context);
        applicationListenerStarter.getResource(clazz,args);
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
        TimerTask task = new TimerTask() {
            public void run() {
                listeners.running();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000, 30000);
        log.info("EnzuoSpring启动成功");
    }
}
