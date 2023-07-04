package main.RPC.annotation;

import main.studySpring.Spring.annotation.bean.Bean;

import java.lang.annotation.*;

@Bean
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RPCService {
}
