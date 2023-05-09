package Main.studySpring.Spring.controller.inteceptor;

import Main.studySpring.Spring.Service.UserService;
import Main.studySpring.Spring.annotation.bean.Autowired;
import Main.studySpring.Spring.inteceptor.WebHttpInterceptor;
import Main.studySpring.Tomcat.agreement.Request;
import Main.studySpring.Tomcat.agreement.Response;

/**
 * @Classname TestInterceptor
 * @Description
 * @Version 1.0.0
 * @Date 2023/5/8 23:06
 * @Created by Enzuo
 */

public class TestInterceptor implements WebHttpInterceptor {
    @Autowired
    UserService userService;
    @Override
    public boolean handle(Request request, Response response) {
        System.out.println(userService);
        return false;
    }
}
