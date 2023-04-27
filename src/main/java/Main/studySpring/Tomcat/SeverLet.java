package Main.studySpring.Tomcat;

import Main.studySpring.Tomcat.Config.Config;
import Main.studySpring.Tomcat.HttpSocketThread.HttpSocketThread;
import Main.studySpring.Tomcat.context.HttpHandleContext;
import Main.studySpring.Tomcat.init.ServerInit;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @Classname Servelet
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/20 12:33
 * @Created by Enzuo
 */
@Slf4j
public class SeverLet {

    public static void run(Class<?> clazz, String[] args,int ...poolNum) {
        int n;
        if(poolNum.length==0)n=10;
        else n=poolNum[0];
        log.info("开始初始化Tomcat");
        new SeverLet().SeverRun(clazz, args,n);
    }

    private void SeverRun(Class<?> clazz, String[] args,int n) {
        log.info("创建线程池");

        ExecutorService threadPool= Executors.newFixedThreadPool(n);
        log.info("创建线程池成功,线程池线程个数为{}",n);
            try{
                log.info("创建HTTPHandleContext");
                HttpHandleContext httpHandleContext = new HttpHandleContext();
                log.info("创建HTTPHandleContext成功");
                log.info("初始化Server服务");
                ServerInit serverInit = new ServerInit(clazz, args, httpHandleContext);
                serverInit.init();
                log.info("初始化Server服务成功");
                ServerSocket serverSocket = new ServerSocket(Config.port);
                log.info("成功启动端口 {}",Config.port);
                log.info("初始化Tomcat成功");
                while(true){
                    try{
                        Socket connection = serverSocket.accept();
                        Thread task = new HttpSocketThread(connection,httpHandleContext);
                        threadPool.submit(task);
                    }catch(Exception ex){
                            log.error(ex.getMessage());
                    }
                }
            }catch(Exception ex){
                log.error("未知错误");
                log.error(ex.getMessage());
            }

        }




}








