package Main.studySpring.Spring.inteceptor;

import Main.studySpring.Tomcat.agreement.Request;
import Main.studySpring.Tomcat.agreement.Response;

/**
 * @interfaceName WebHttpInterceptor
 * @Description
 * @Version 1.0.0
 * @Date 2023/5/8 22:39
 * @Created by Enzuo
 */

public interface WebHttpInterceptor {
     boolean handle(Request request, Response response);
}
