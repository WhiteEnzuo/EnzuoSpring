package main.studySpring.Spring.Service.impl;

import lombok.extern.slf4j.Slf4j;
import main.RPC.annotation.RPCAutowired;
import main.studySpring.Spring.Service.TestService;
import main.studySpring.Spring.Service.UserService;
import main.studySpring.Spring.annotation.bean.Bean;

/**
 * @Classname UserServiceImpl
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/19 20:34
 * @Created by Enzuo
 */
@Bean
@Slf4j
public class UserServiceImpl implements UserService {
    @RPCAutowired
    TestService testService;
    public void print(){
        String s = testService.s();
        log.info(s);
//        log.info("");
    }
}
