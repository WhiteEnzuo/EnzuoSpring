package Main.studySpring.Spring.Service.impl;

import Main.studySpring.Spring.Service.UserService;
import Main.studySpring.Spring.annotation.bean.Autowired;
import Main.studySpring.Spring.annotation.bean.Bean;
import Main.studySpring.Spring.annotation.bean.Value;

/**
 * @Classname TestService
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 22:52
 * @Created by Enzuo
 */
@Bean
public class TestService {
    @Value("123")
    String a;
    public void print(){
        System.out.println(a);
    }
}
