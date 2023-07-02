package main.studySpring.Spring.annotation.bean;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Value {
    String value() default "";
}
