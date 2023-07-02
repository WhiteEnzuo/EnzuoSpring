package main.RPC.client;

import main.RPC.agreement.RPCRequest;
import main.RPC.agreement.RPCResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Classname Client
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 13:14
 * @Created by Enzuo
 */
@Slf4j
public class Client {
    public RPCResponse getRPCResponse(String ip, int port, RPCRequest request) {
        Socket server = null;
        RPCResponse rpcResponse = new RPCResponse();
        try {
            server = new Socket(ip, port);

            OutputStream outputStream = server.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(request);
            InputStream inputStream = server.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            rpcResponse = (RPCResponse)objectInputStream.readObject();
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        return rpcResponse;
    }
}
