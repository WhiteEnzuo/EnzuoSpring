package Main.studySpring.Spring.Listeners.impl;

import Main.studySpring.Spring.Listeners.ApplicationListener;
import Main.studySpring.Spring.context.ApplicationContext;
import Main.studySpring.Spring.inteceptor.context.HttpInterceptorContext;
import Main.studySpring.global.Interceptor;

/**
 * @Classname ApplicationListenerImpl
 * @Description
 * @Version 1.0.0
 * @Date 2023/5/8 23:00
 * @Created by Enzuo
 */

public class ApplicationListenerImpl implements ApplicationListener {
    public ApplicationListenerImpl(ApplicationContext context){
    }
    public void contextLoaded(ApplicationContext context){
        Interceptor.httpInterceptorContext= context.getInterceptorContext();
    }
}
