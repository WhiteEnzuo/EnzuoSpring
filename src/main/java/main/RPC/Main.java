package main.RPC;

import main.RPC.agreement.RPCRequest;
import main.RPC.client.Client;
import main.RPC.demo.Demo;

/**
 * @Classname Main
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 22:49
 * @Created by Enzuo
 */

public class Main {
    public static void main(String[] args) {
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.setServerName(Demo.class.getName());
        rpcRequest.setMethodName("s");
        rpcRequest.setMethodAgreeType(null);
        rpcRequest.setMethodAgree(null);
        System.out.println(new Client().getRPCResponse("127.0.0.1", 8081, rpcRequest));
    }
}
