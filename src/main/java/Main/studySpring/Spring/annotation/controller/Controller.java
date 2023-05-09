package Main.studySpring.Spring.annotation.controller;

import Main.studySpring.Spring.annotation.bean.Bean;

import java.lang.annotation.*;

@Bean
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Controller {
    String value() default "";
}
