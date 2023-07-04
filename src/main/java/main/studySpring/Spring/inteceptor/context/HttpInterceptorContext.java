package main.studySpring.Spring.inteceptor.context;

import main.studySpring.Spring.inteceptor.WebHttpInterceptor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname HttpInterceptorContext
 * @Description
 * @Version 1.0.0
 * @Date 2023/5/8 22:41
 * @Created by Enzuo
 */
@Data
public class HttpInterceptorContext {

    private Set<WebHttpInterceptor> httpInterceptors;

    public HttpInterceptorContext() {
        httpInterceptors = new HashSet<>();
    }

    public boolean addHttpInterceptor(WebHttpInterceptor interceptor) {
        return httpInterceptors.add(interceptor);
    }

    public boolean deleteHttpInterceptor(WebHttpInterceptor interceptor) {
        return httpInterceptors.remove(interceptor);
    }
}
