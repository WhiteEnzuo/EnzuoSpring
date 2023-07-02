package main.RPC.agreement;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname RPCRequest
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 11:30
 * @Created by Enzuo
 */
@Data
public class RPCRequest implements Serializable {
    private String ServerName;
    private String ClassName;
    private String methodName;
    private Class<?>[] methodAgreeType;
    private Object[] methodAgree;
}
