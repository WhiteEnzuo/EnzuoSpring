package Main.studySpring.Spring.annotation.Aspect;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.ArrayList;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Aspect {
    Class<?> value();
}
