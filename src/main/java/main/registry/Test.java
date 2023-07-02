package main.registry;

import main.registry.agreement.RegistryAgreement;
import main.registry.enums.FindTypeCode;
import main.registry.enums.RegisterType;
import main.studySpring.Spring.Service.TestService;
//import main.studySpring.Spring.Service.impl.TestService;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @Classname Test
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 18:59
 * @Created by Enzuo
 */

public class Test {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1",9090);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        RegistryAgreement registryAgreement = new RegistryAgreement();
        ArrayList<String> list = new ArrayList<>();
        list.add(TestService.class.getName());
        registryAgreement.setClassNames(list);
        registryAgreement.setTypeCode(RegisterType.GET_SEVER_INFO.getType());
//        registryAgreement.setTypeCode(RegisterType.REGISTER.getType());
        registryAgreement.setFindTypeCode(FindTypeCode.RANDOM.getType());

        objectOutputStream.writeObject(registryAgreement);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        System.out.println(objectInputStream.readObject());
        socket.close();
    }
}
