package Main.studySpring.Spring.annotation.controller;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Controller
public @interface RestController {
    String value() default "";
}
