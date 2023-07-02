package main.registry.server;

import main.registry.Dao.ConfigInfo;
import main.registry.agreement.RegistryAgreement;
import main.registry.Dao.ServerInfo;
import main.registry.enums.RegisterType;
import main.registry.handle.impl.GetAllServerHandle;
import main.registry.handle.impl.GetServerConfig;
import main.registry.handle.impl.GetServerHandle;
import main.registry.handle.impl.RegisterHandle;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Set;

/**
 * @Classname SocketThread
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 17:28
 * @Created by Enzuo
 */
@Slf4j
public class SocketThread implements Runnable {
    private Socket client;

    public SocketThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = client.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            RegistryAgreement registryAgreement = (RegistryAgreement) objectInputStream.readObject();
            outputStream = client.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            if (registryAgreement.getTypeCode().equals(RegisterType.REGISTER.getType())) {
                log.info("有人注册服务");
                ServerInfo serverInfo = RegisterHandle.handle(registryAgreement);
                objectOutputStream.writeObject(serverInfo);
                return;
            }
            if (registryAgreement.getTypeCode().equals(RegisterType.GET_SEVER_INFO.getType())) {
                log.info("有人获取服务");
                ServerInfo serverInfo = GetServerHandle.handle(registryAgreement);
                objectOutputStream.writeObject(serverInfo);
                return;
            }
            if (registryAgreement.getTypeCode().equals(RegisterType.GET_ALL_INFO.getType())) {
                log.info("有人获取所有服务");
                List<Set<ServerInfo>> serverInfos = GetAllServerHandle.handle(registryAgreement);
                objectOutputStream.writeObject(serverInfos);
                return;
            }
            if (registryAgreement.getTypeCode().equals(RegisterType.GET_SEVER_CONFIG.getType())) {
                log.info("有人获取配置");
                ConfigInfo configInfo = GetServerConfig.handle(registryAgreement);
                objectOutputStream.writeObject(configInfo);
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (outputStream != null) outputStream.close();
                if (inputStream != null) inputStream.close();
                if (client != null) client.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }


        }


    }
}
