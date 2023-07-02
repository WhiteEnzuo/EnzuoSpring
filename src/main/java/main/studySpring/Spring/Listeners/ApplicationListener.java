package main.studySpring.Spring.Listeners;

import main.studySpring.Spring.context.ApplicationContext;

/**
 * @Classname ApplicationListener
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 16:34
 * @Created by Enzuo
 */

public interface ApplicationListener {
    default void starting() {

    }

    default void environmentPrepared(ApplicationContext context) {
    }

    default void contextPrepared(ApplicationContext context) {
    }

    default void contextLoaded(ApplicationContext context) {
    }

    default void started(ApplicationContext context) {
    }

    default void running(ApplicationContext context) {
    }

    default void failed(ApplicationContext context,Throwable exception) {
    }

}
