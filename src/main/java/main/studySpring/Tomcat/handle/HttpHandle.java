package main.studySpring.Tomcat.handle;

import main.studySpring.Tomcat.agreement.Request;
import main.studySpring.Tomcat.agreement.Response;

/**
 * @interfaceName HttpHandle
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 19:26
 * @Created by Enzuo
 */

public interface HttpHandle {
    void handle(Request request, Response response);
}
