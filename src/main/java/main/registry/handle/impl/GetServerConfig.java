package main.registry.handle.impl;

import lombok.extern.slf4j.Slf4j;
import main.registry.Dao.ConfigInfo;
import main.registry.agreement.RegistryAgreement;
import main.registry.server.ConfigContext;

import java.util.Map;

/**
 * @Classname GetServerConfig
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/29 8:39
 * @Created by Enzuo
 */
@Slf4j
public class GetServerConfig {
    public static ConfigInfo handle(RegistryAgreement registryAgreement) {
        String serverName = registryAgreement.getServerName();
        Map<String, Object> map = ConfigContext.get(serverName);
        ConfigInfo configInfo = new ConfigInfo();
        configInfo.setConfig(map);
        return configInfo;
    }
}
