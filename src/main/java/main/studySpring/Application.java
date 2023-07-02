package main.studySpring;

import main.registry.server.ServerContext;
import main.studySpring.Spring.Service.UserService;
import main.studySpring.Spring.EnzuoApplication;
import main.studySpring.Spring.context.ApplicationContext;
import main.studySpring.Tomcat.Config.Config;
import main.studySpring.Tomcat.SeverLet;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Classname Run
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/27 22:16
 * @Created by Enzuo
 */
@Slf4j
public class Application {
    public static void run(Class<?> clazz, String[] args) {

            ApplicationContext context = new ApplicationContext(clazz);
            EnzuoApplication.run(context, clazz, args);
            Map<String, Object> config = context.getConfig();
            if (config.containsKey("port")) {
                Config.port = (Integer) config.get("port");
            }
        UserService bean = context.getBeanFactory().getBean(UserService.class);

        TimerTask task = new TimerTask() {
            public void run() {
                bean.print();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 5000, 10000);
        SeverLet.run(clazz, args);


    }
}
