package Main.studySpring.Spring.Service.impl;

import Main.studySpring.Spring.Service.UserService;

import Main.studySpring.Spring.annotation.Autowired;
import Main.studySpring.Spring.annotation.Bean;

/**
 * @Classname UserServiceImpl
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/19 20:34
 * @Created by Enzuo
 */
@Bean
public class UserServiceImpl implements UserService {
    @Autowired
    TestService testService;
    public void print(){
        System.out.println(testService);
    }
}
