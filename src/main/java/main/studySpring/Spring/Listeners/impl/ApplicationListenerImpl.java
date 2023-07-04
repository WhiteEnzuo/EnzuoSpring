package main.studySpring.Spring.Listeners.impl;

import main.studySpring.Spring.Listeners.ApplicationListener;
import main.studySpring.Spring.context.ApplicationContext;
import main.studySpring.Spring.inteceptor.init.HttpInterceptorInit;
import main.studySpring.global.Interceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Classname ApplicationListenerImpl
 * @Description
 * @Version 1.0.0
 * @Date 2023/5/8 23:00
 * @Created by Enzuo
 */
@Slf4j
public class ApplicationListenerImpl implements ApplicationListener {
    public ApplicationListenerImpl(ApplicationContext context, String[] args) {
    }

    public void contextPrepared(ApplicationContext context) {
        log.info("加载拦截器");
        HttpInterceptorInit httpInterceptorInit = new HttpInterceptorInit(context.getInterceptorContext(), context);
        httpInterceptorInit.init();
        log.info("加载拦截器成功");
    }

    public void contextLoaded(ApplicationContext context) {
        Interceptor.httpInterceptorContext = context.getInterceptorContext();
    }
}
