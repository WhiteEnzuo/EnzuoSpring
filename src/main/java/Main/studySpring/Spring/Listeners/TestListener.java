package Main.studySpring.Spring.Listeners;

import Main.studySpring.Spring.context.ApplicationContext;

/**
 * @Classname TestLinstener
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 17:02
 * @Created by Enzuo
 */

public class TestListener implements ApplicationListener {
    public TestListener(ApplicationContext context){
    }

    @Override
    public void starting() {
//        System.out.println(123);
    }

    @Override
    public void environmentPrepared(ApplicationContext context) {
//        System.out.println(context.getBeanFactory().getBeanContext());
    }

    @Override
    public void contextPrepared(ApplicationContext context) {
//        System.out.println(context.getBeanFactory().getBeanContext());
    }

    @Override
    public void contextLoaded(ApplicationContext context) {
//        System.out.println(context.getBeanFactory().getBeanContext());
    }
}
