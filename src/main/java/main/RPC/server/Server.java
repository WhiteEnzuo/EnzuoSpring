package main.RPC.server;

import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname ServerThread
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 11:19
 * @Created by Enzuo
 */
@Slf4j
public class Server {
    private final ExecutorService executorService;
    private int port = 8081;

    public Server(int port) {
        this.port = port;
        executorService = Executors.newSingleThreadExecutor();
    }

    public Server() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public void startServer() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
            log.info("开始监听");
            while (true) {
                Socket accept = null;
                try {
                    accept = socket.accept();
                    ServerThread serverThread = new ServerThread(accept);
                    executorService.submit(serverThread);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
