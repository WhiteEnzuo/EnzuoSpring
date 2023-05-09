package Main.studySpring;

import Main.studySpring.Spring.Service.impl.TestService;
import Main.studySpring.Spring.SpringApplication;
import Main.studySpring.Spring.context.ApplicationContext;
import Main.studySpring.Tomcat.Config.Config;
import Main.studySpring.Tomcat.SeverLet;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

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
            SpringApplication.run(context, clazz, args);
            Map<String, Object> config = context.getConfig();
            if (config.containsKey("port")) {
                Config.port = (Integer) config.get("port");
            }
        TestService bean = context.getBeanFactory().getBean(TestService.class);
            bean.print();
        SeverLet.run(clazz, args);


    }
}
