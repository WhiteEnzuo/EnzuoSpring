package main.registry.server;

import main.registry.Dao.ServerInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname MethodContext
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 12:59
 * @Created by Enzuo
 */
@Slf4j
public class ServerContext {
    private static Map<String, Set<ServerInfo>> serverContext = new ConcurrentHashMap<>();
    private static Map<String, String> ClassContext = new ConcurrentHashMap<>();

    public static Map<String, Set<ServerInfo>> getServerContext() {
        return serverContext;
    }

    public static void setServerContext(Map<String, Set<ServerInfo>> serverContext) {
        ServerContext.serverContext = serverContext;
    }

    public ServerContext() {

    }

    public static void put(ServerInfo serverInfo,String className) {
        if (serverContext == null||ClassContext==null) {
            serverContext = new ConcurrentHashMap<>();
            ClassContext = new ConcurrentHashMap<>();
            return;
        }
        ClassContext.put(className,serverInfo.getServerName());
        if (serverContext.containsKey(serverInfo.getServerName())) {
            Set<ServerInfo> serverInfoList = serverContext.get(serverInfo.getServerName());
            serverInfoList.add(serverInfo);
            return;
        }
        Set<ServerInfo> serverInfoList = new HashSet<>();
        serverInfoList.add(serverInfo);
        serverContext.put(serverInfo.getServerName(), serverInfoList);
    }

    public static Set<ServerInfo> get(String className) {
        if (serverContext == null||ClassContext==null) {
            serverContext = new ConcurrentHashMap<>();
            ClassContext = new ConcurrentHashMap<>();
            return new HashSet<>();
        }
        if (!ClassContext.containsKey(className) || ClassContext.get(className) == null) return new HashSet<>();
        boolean b = serverContext.containsKey(ClassContext.get(className));
        log.info(String.valueOf(b));
        log.info(String.valueOf(serverContext.get(ClassContext.get(className))));
        if(!serverContext.containsKey(ClassContext.get(className))||serverContext.get(ClassContext.get(className))==null) {
            return new HashSet<>();
        }
        return serverContext.get(ClassContext.get(className));
    }
    public synchronized static void removeAllKey(){
        log.info("开始清空Key");
        log.info(serverContext.toString());
        serverContext.keySet().forEach((k)->{
            serverContext.remove(k);
        });
        log.info(ClassContext.toString());
        ClassContext.keySet().forEach((k)->{
            ClassContext.remove(k);

        });
        log.info("清空Key完毕");
    }


}
