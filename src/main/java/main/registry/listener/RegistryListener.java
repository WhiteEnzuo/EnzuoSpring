package main.registry.listener;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import main.RPC.annotation.RPCService;
import main.registry.Dao.ConfigInfo;
import main.registry.agreement.RegistryAgreement;
import main.registry.enums.RegisterType;
import main.studySpring.Spring.Listeners.ApplicationListener;
import main.studySpring.Spring.context.ApplicationContext;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.*;


/**
 * @Classname RegistryListener
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 22:43
 * @Created by Enzuo
 */
@Slf4j
public class RegistryListener implements ApplicationListener {
    private ApplicationContext context;
    public RegistryListener(ApplicationContext context,String[] args){
        this.context=context;
    }
    public void starting () {
        Map<String, Object> config = context.getConfig();
        Map<String, Object> configServer=(Map<String, Object>)config.get("config");
        RegistryAgreement registryAgreement = new RegistryAgreement();
        registryAgreement.setTypeCode(RegisterType.GET_SEVER_CONFIG.getType());
        registryAgreement.setServerName((String) configServer.get("serverName"));
        Map<String, Object> fromServerConfig = new HashMap<>();
        try{
            Socket socket = new Socket((String)configServer.get("address"),(int)configServer.get("port"));
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(registryAgreement);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ConfigInfo configInfo =(ConfigInfo) objectInputStream.readObject();
            fromServerConfig = configInfo.getConfig();
            inputStream.close();
            objectInputStream.close();
            outputStream.close();
            objectOutputStream.close();
            socket.close();
        }catch (IOException | ClassNotFoundException e){
            log.error(e.getMessage());
        }
        ArrayList<String> configInfo =  (ArrayList<String>)configServer.get("config");
        for (String configName : configInfo) {
            if (fromServerConfig.containsKey(configName)){
                config.put(configName,fromServerConfig.get(configName));
                continue;
            }
            System.exit(0);
            throw new RuntimeException("配置中心没有数据");
        }

    }
    public void contextPrepared(ApplicationContext context){
        Map<String, Object> config = context.getConfig();
        Map<String, Object> registry = (Map<String, Object>)config.get("registry");
        Map<String, Object> rpc =(Map<String, Object>) config.get("rpc");
        int port = (int)rpc.get("port");
        log.info("开始启动注册中心监听器");
//            InputStream inputStream = socket.getInputStream();
        RegistryAgreement registryAgreement = new RegistryAgreement();
        registryAgreement.setAddress((String) rpc.get("address"));
        registryAgreement.setPort((short) port);
        Map<Class<?>, Object> beanContext = context.getBeanFactory().getBeanContext();

        ArrayList<String> list = new ArrayList<>();
        beanContext.values().forEach((value)->{
            RPCService annotation = value.getClass().getAnnotation(RPCService.class);
            if(annotation!=null)list.add(value.getClass().getInterfaces()[0].getName());
        });
        registryAgreement.setServerName((String)registry.get("serverName"));
        registryAgreement.setClassNames(list);
        registryAgreement.setTypeCode(RegisterType.REGISTER.getType());
        TimerTask task = new TimerTask() {
            @SneakyThrows
            public void run() {
                try{
                    Socket socket = new Socket((String)registry.get("address"),(int)registry.get("port"));
                    OutputStream outputStream = socket.getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(registryAgreement);
                    outputStream.close();
                    objectOutputStream.close();
                    socket.close();
                }catch (IOException e){
                    log.error(e.getMessage());
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000, 1000);

    }

}
