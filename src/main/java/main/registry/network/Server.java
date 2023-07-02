package main.registry.network;

import main.registry.server.SocketThread;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname Server
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 17:24
 * @Created by Enzuo
 */
@Slf4j
public class Server {

    private int port=9090;
    private  ExecutorService executorService;
    public Server(){
        executorService= Executors.newSingleThreadExecutor();
    }
    public Server(int port){
        this.port=port;
        executorService= Executors.newSingleThreadExecutor();
    }
    public  void startServer(){
        ServerSocket server  = null;
        try {
            server  = new ServerSocket(this.port);
            log.info("开始监听");
            while (true){
                Socket client = server.accept();
                executorService.submit(new SocketThread(client));
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            try {
                if(server!=null)server.close();
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }
}
