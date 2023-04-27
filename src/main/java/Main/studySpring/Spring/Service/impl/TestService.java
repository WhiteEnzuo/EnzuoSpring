package Main.studySpring.Spring.Service.impl;

import Main.studySpring.Spring.Service.UserService;
import Main.studySpring.Spring.annotation.Autowired;
import Main.studySpring.Spring.annotation.Bean;

/**
 * @Classname TestService
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 22:52
 * @Created by Enzuo
 */
@Bean
public class TestService {
    @Autowired
    UserService userService;
    public void print(){
        System.out.println(userService);
        userService.print();
    }
}
