package main.RPC.listener;

import lombok.extern.slf4j.Slf4j;
import main.RPC.annotation.RPCAutowired;
import main.RPC.annotation.RPCService;
import main.RPC.proxy.RPCProxy;
import main.RPC.server.RPCContext;
import main.RPC.server.Server;
import main.registry.Dao.ServerInfo;
import main.registry.agreement.RegistryAgreement;
import main.registry.enums.FindTypeCode;
import main.registry.enums.RegisterType;
import main.studySpring.Spring.Listeners.ApplicationListener;
import main.studySpring.Spring.bean.BeanFactory;
import main.studySpring.Spring.beanInit.model.AspectProxy;
import main.studySpring.Spring.context.ApplicationContext;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname RPCListener
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/27 20:23
 * @Created by Enzuo
 */
@Slf4j
public class RPCListener implements ApplicationListener {
    private Map<Field,Object> fieldObjectMap;
    public RPCListener(ApplicationContext context,String[] args){
        fieldObjectMap = new HashMap<>();
    }

    public void contextPrepared(ApplicationContext context) {
        //开始注入RPC
        BeanFactory beanFactory = context.getBeanFactory();
        Map<Class<?>, Object> beanContext = beanFactory.getBeanContext();
        beanContext.forEach((key, value) -> {
            Field[] declaredFields = value.getClass().getDeclaredFields();
            for (Field declaredField :declaredFields ) {
                RPCAutowired annotation = declaredField.getAnnotation(RPCAutowired.class);
                if(annotation==null)continue;
                fieldObjectMap.put(declaredField,value);
            }
            RPCService rpcService = value.getClass().getAnnotation(RPCService.class);
            if (rpcService == null) return;
            RPCContext.put(value);
        });
        new Thread(()->{
            new Server().startServer();
        }).start();
    }

    @Override
    public void running(ApplicationContext context) {
        Map<String, Object> config = context.getConfig();
        Map<String, Object> registry =(Map<String, Object>) config.get("registry");
        fieldObjectMap.forEach((filed,object)->{
            try {
                Socket socket = new Socket((String)registry.get("address"),(int)registry.get("port"));
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                RegistryAgreement registryAgreement = new RegistryAgreement();
                ArrayList<String> list = new ArrayList<>();
                list.add(filed.getType().getName());
                registryAgreement.setClassNames(list);
                registryAgreement.setTypeCode(RegisterType.GET_SEVER_INFO.getType());
                registryAgreement.setFindTypeCode(FindTypeCode.RANDOM.getType());

                objectOutputStream.writeObject(registryAgreement);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                ServerInfo serverInfo = (ServerInfo)objectInputStream.readObject();
                RPCProxy rpcProxy = new RPCProxy();
                rpcProxy.setServerInfo(serverInfo);
                rpcProxy.setClazz(filed.getType());
                Object proxyObject = Proxy.newProxyInstance(filed.getType().getClassLoader(), new Class[]{filed.getType()}, rpcProxy);
                filed.setAccessible(true);
                filed.set(object,proxyObject);
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            }catch (IOException | ClassNotFoundException | IllegalAccessException e){
                log.error(e.getMessage());
            }

        });

    }
}
