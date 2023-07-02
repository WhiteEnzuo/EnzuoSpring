package main.RPC.server;

import main.RPC.agreement.RPCRequest;
import main.RPC.agreement.RPCResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @Classname ServerThread
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 11:23
 * @Created by Enzuo
 */
@Data
@Slf4j
public class ServerThread implements Runnable{
    private Socket client;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        ObjectOutputStream objectOutputStream =null;
        ObjectInputStream objectInputStream =null;
        try {
            OutputStream outputStream = client.getOutputStream();
            InputStream inputStream = client.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            RPCRequest request = (RPCRequest)objectInputStream.readObject();
            String className = request.getClassName();
            Class<?> serverClass = Class.forName(className);
            Method method = serverClass.getMethod(request.getMethodName(), request.getMethodAgreeType());
            Object rpcInvoke= RPCContext.get(className);

            Object invoke = method.invoke(rpcInvoke, request.getMethodAgree());
            objectOutputStream = new ObjectOutputStream(outputStream);
            RPCResponse rpcResponse = new RPCResponse();
            rpcResponse.setResponse(invoke);
            objectOutputStream.writeObject(rpcResponse);
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            try {
                if(objectOutputStream!=null)objectOutputStream.close();
                if(objectInputStream!=null)
                    objectInputStream.close();
                if(client!=null)client.close();
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }

    }
}
