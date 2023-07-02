package main.registry;

import main.registry.network.Server;
import main.registry.server.ConfigContext;
import main.registry.server.ServerContext;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Classname Main
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 17:23
 * @Created by Enzuo
 */

public class Main {
    public static void main(String[] args) {
        ConfigContext.init(Main.class);
        TimerTask task = new TimerTask() {
            public void run() {
                ServerContext.removeAllKey();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 5000, 10000);
        new Server().startServer();

    }
}
