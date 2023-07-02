package main.studySpring.Spring.Test;

import main.studySpring.Spring.annotation.Aspect.Aspect;
import main.studySpring.Spring.annotation.bean.Bean;
import main.studySpring.Spring.annotation.Aspect.Before;

/**
 * @Classname TestImpl
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/28 10:46
 * @Created by Enzuo
 */
@Bean
@Aspect(TestProxy.class)
public class TestImpl implements Test{
    @Before("start")
    public void aest(){
        System.out.println("ast");
    }
}
