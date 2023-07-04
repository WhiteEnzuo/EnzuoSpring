package main.registry.handle.impl;

import main.registry.agreement.RegistryAgreement;
import main.registry.Dao.ServerInfo;
import main.registry.server.ServerContext;

/**
 * @Classname RegisterHandle
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 18:32
 * @Created by Enzuo
 */

public class RegisterHandle {

    public static ServerInfo handle(RegistryAgreement registryAgreement) {
        String address = registryAgreement.getAddress();
        String serverName = registryAgreement.getServerName();
        short port = registryAgreement.getPort();
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setServerName(serverName);
        serverInfo.setPort(port);
        serverInfo.setAddress(address);
        registryAgreement.getClassNames().forEach(className -> {
            ServerContext.put(serverInfo, className);
        });
        return serverInfo;
    }
}
