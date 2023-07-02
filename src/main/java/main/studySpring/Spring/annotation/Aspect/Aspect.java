package main.studySpring.Spring.annotation.Aspect;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Aspect {
    Class<?> value();
}
