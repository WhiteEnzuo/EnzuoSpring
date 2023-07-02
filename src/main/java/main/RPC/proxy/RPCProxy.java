package main.RPC.proxy;

import lombok.Data;
import main.RPC.agreement.RPCRequest;
import main.RPC.agreement.RPCResponse;
import main.RPC.client.Client;
import main.registry.Dao.ServerInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Classname RPCProxy
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/28 21:05
 * @Created by Enzuo
 */
@Data
public class RPCProxy implements InvocationHandler {
    private ServerInfo serverInfo;
    private Class<?> clazz;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("toString")){
            return clazz.getName();
        }
        RPCRequest request=new RPCRequest();
        request.setMethodName(method.getName());
        request.setMethodAgree(args);
        request.setMethodAgreeType(method.getParameterTypes());
        request.setClassName(clazz.getName());
        request.setServerName(serverInfo.getServerName());
        RPCResponse rpcResponse = new Client().getRPCResponse(serverInfo.getAddress(), serverInfo.getPort(), request);
        return rpcResponse.getResponse();
    }
}
