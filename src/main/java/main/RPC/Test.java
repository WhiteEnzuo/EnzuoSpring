package main.RPC;

import main.RPC.demo.Demo;
import main.RPC.server.RPCContext;
import main.RPC.server.Server;

/**
 * @Classname Test
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 22:48
 * @Created by Enzuo
 */

public class Test {
    public static void main(String[] args) {
        RPCContext.put(new Demo());
        new Server().startServer();
    }
}
