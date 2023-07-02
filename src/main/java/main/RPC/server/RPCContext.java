package main.RPC.server;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MethodContext
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 12:59
 * @Created by Enzuo
 */
@Slf4j
@Data
public class RPCContext {
    private static Map<String, Object> rpcContext;

    public RPCContext() {
        rpcContext = new HashMap<>();
    }

    public static void put(Object rpc) {
        if (rpcContext == null) rpcContext = new HashMap<>();
        rpcContext.put(rpc.getClass().getInterfaces()[0].getName(), rpc);
    }

    public static Object get(String name) {
        if (rpcContext == null) {
            rpcContext = new HashMap<>();
            return null;
        }
        if (!rpcContext.containsKey(name) || rpcContext.get(name) == null) return null;
        return rpcContext.get(name);
    }


}
