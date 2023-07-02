package main.studySpring.Spring.Service.impl;

import main.RPC.annotation.RPCService;
import main.studySpring.Spring.Service.TestService;
import main.studySpring.Spring.annotation.bean.Bean;
import main.studySpring.Spring.annotation.bean.Value;

/**
 * @Classname TestService
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 22:52
 * @Created by Enzuo
 */
@RPCService
public class TestServiceImpl implements TestService {

    @Override
    public String s() {
        return "123";
    }
}
