package main.studySpring.Tomcat.HttpSocketThread;

import main.studySpring.Tomcat.agreement.Request;
import main.studySpring.Tomcat.agreement.Response;
import main.studySpring.Tomcat.context.HttpHandleContext;
import main.studySpring.Tomcat.enmus.StatusCode;
import main.studySpring.Tomcat.handle.impl.FilterHandleImpl;
import main.studySpring.Tomcat.handle.impl.SpringMVCHandleImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

/**
 * @Classname HttpSocketThred
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/20 20:11
 * @Created by Enzuo
 */
@Slf4j
public class HttpSocketThread extends Thread {
    private Socket connection;//客户端与服务器之间的连接
    private HttpHandleContext context;


    public HttpSocketThread(Socket connection,HttpHandleContext context) {
        this.connection = connection;
        this.context=context;
    }

    @Override
    public void run() {
        if (connection != null) {
            try {
                Request request = new Request(connection.getInputStream());
                request.parse();
                Response response = new Response();
                response.setOutputStream(connection.getOutputStream());
                if (request.getUri() == null) {
                    response.sentResponse("Error", StatusCode.ERROR);
                    throw new RuntimeException("url is null");
                }
                if (context == null) {
                    response.sentResponse("Error", StatusCode.ERROR);
                    throw new RuntimeException("HandleContext is null");
                }
                response.setRequest(request);
                FilterHandleImpl filterHandle = new FilterHandleImpl();
                filterHandle.handle(request,response);
                SpringMVCHandleImpl httpHandle = new SpringMVCHandleImpl();
                httpHandle.handle(request, response);
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }finally {
                closeSocket(connection);
            }
        }
    }

    void closeSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}