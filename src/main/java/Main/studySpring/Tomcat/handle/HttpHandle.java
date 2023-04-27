package Main.studySpring.Tomcat.handle;

import Main.studySpring.Tomcat.agreement.Request;
import Main.studySpring.Tomcat.agreement.Response;

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
