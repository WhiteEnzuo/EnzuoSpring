package main.registry.server;

import lombok.extern.slf4j.Slf4j;
import main.registry.Dao.ServerInfo;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname ConfigContext
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/29 8:48
 * @Created by Enzuo
 */
@Slf4j
public class ConfigContext {
    private static Map<String, Object> configContext = new ConcurrentHashMap<>();
    public static void init(Class<?> clazz){
        configContext=getLoad(clazz);
    }
    public static Map<String, Object> get(String serverName){
        if (!configContext.containsKey(serverName)||configContext.get(serverName)==null) {
            return new HashMap<>();
        }
        return (Map<String, Object>)configContext.get(serverName);
    }
    public static void put(String serverName,Map<String, Object> map){
        configContext.put(serverName,map);
    }
    private static Map<String,Object> getLoad(Class<?> clazz){
        Yaml yaml = new Yaml();
        InputStream resourceAsStream = clazz.getClassLoader().getResourceAsStream("config.yaml");
        if(resourceAsStream==null){
            log.error("config.yaml is null");
            System.exit(0);
        }
        return (Map<String,Object>)yaml.load(resourceAsStream);
    }
}
