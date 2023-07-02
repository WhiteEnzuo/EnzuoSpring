package main.RPC.agreement;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname RPCResponse
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 11:30
 * @Created by Enzuo
 */
@Data
public class RPCResponse implements Serializable {
    private Object response;
}
