package main.registry.handle.impl;

import main.registry.Dao.ServerInfo;
import main.registry.agreement.RegistryAgreement;
import main.registry.enums.FindTypeCode;
import main.registry.server.ServerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Classname GetServerAllHandle
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/27 13:46
 * @Created by Enzuo
 */

public class GetAllServerHandle {
    public static List<Set<ServerInfo>> handle(RegistryAgreement registryAgreement) {
        if (registryAgreement.getClassNames() == null || registryAgreement.getClassNames().size() == 0) {
            Map<String, Set<ServerInfo>> serverContext = ServerContext.getServerContext();
            return new ArrayList<>(serverContext.values());
        }
        ArrayList<Set<ServerInfo>> list = new ArrayList<>();
        registryAgreement.getClassNames().forEach((className) -> {
            Set<ServerInfo> serverInfos = ServerContext.get(className);

            list.add(serverInfos);
        });
        return list;
    }
}
