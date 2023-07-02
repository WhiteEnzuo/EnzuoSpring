package main.studySpring.Spring.controller.inteceptor;

import main.studySpring.Spring.inteceptor.UrlFilter;
import main.studySpring.Spring.inteceptor.WebHttpInterceptor;
import main.studySpring.Tomcat.agreement.Request;
import main.studySpring.Tomcat.agreement.Response;

/**
 * @Classname TestInterceptor
 * @Description
 * @Version 1.0.0
 * @Date 2023/5/8 23:06
 * @Created by Enzuo
 */

public class TestInterceptor implements WebHttpInterceptor {

    @Override
    public boolean handle(Request request, Response response) {
        UrlFilter urlFilter = new UrlFilter();
        boolean url = urlFilter
                .addInterceptorUrl("/test/*.jpg")
                .addAccessUrl("/test/*.html")
                .isUrlAccess(request);
//        response.setHeaderKey("Content-Type", "application/json;charset=UTF-8");
//        response.sentResponse("Test", StatusCode.ERROR);
        return true;
    }
}
