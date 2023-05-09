package Main.studySpring.Spring.annotation.Aspect;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface After {
    String value() default "";
}
