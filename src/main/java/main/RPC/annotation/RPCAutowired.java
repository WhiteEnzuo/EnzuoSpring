package main.RPC.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @interfaceName RPCAutowired
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/28 21:02
 * @Created by Enzuo
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RPCAutowired {
}
