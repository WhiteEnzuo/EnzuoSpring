package main.registry.handle.impl;

import main.registry.agreement.RegistryAgreement;
import main.registry.Dao.ServerInfo;
import main.registry.server.ServerContext;
import main.registry.enums.FindTypeCode;

import java.util.Random;
import java.util.Set;

/**
 * @Classname GetServerHandle
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 18:38
 * @Created by Enzuo
 */

public class GetServerHandle {

    public static ServerInfo handle(RegistryAgreement registryAgreement) {
        String className = registryAgreement.getClassNames().get(0);
        Set<ServerInfo> serverInfos = ServerContext.get(className);
        if (registryAgreement.getFindTypeCode().equals(FindTypeCode.RANDOM.getType())) {
            return RandomServer(serverInfos);
        }
        return null;
    }

    private static ServerInfo RandomServer(Set<ServerInfo> serverInfos) {
        Random random = new Random();
        int i = (int) (random.nextDouble() * serverInfos.size());
        Object[] objects = serverInfos.toArray();
        return (ServerInfo) objects[i];

    }
}
