package main.studySpring.Spring.Listeners;

import main.studySpring.Spring.context.ApplicationContext;

import java.util.List;

/**
 * @Classname LinstenerStarter
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 16:34
 * @Created by Enzuo
 */

public class ApplicationListeners {
    private List<ApplicationListener> listeners;
    private ApplicationContext context;

    public ApplicationListeners(List<ApplicationListener> listeners, ApplicationContext context) {
        this.listeners = listeners;
        this.context = context;
    }

    public void starting() {
        for (ApplicationListener listener : listeners) {
            listener.starting();
        }
    }

    public void environmentPrepared() {
        for (ApplicationListener listener : listeners) {
            listener.environmentPrepared(context);
        }
    }

    public void contextPrepared() {
        for (ApplicationListener listener : listeners) {
            listener.contextPrepared(context);
        }
    }

    public void contextLoaded() {
        for (ApplicationListener listener : listeners) {
            listener.contextLoaded(context);
        }
    }

    public void started() {
        for (ApplicationListener listener : listeners) {
            listener.started(context);
        }
    }

    public void running() {
        for (ApplicationListener listener : listeners) {
            listener.running(context);
        }
    }

    public void failed(Throwable exception) {
        for (ApplicationListener listener : listeners) {
            listener.failed(context, exception);
        }
    }
}
