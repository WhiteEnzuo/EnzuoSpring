package main.registry.Dao;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname ServerInfo
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 17:24
 * @Created by Enzuo
 */
@Data
public class ServerInfo implements Serializable {
    private String serverName;
    private String address;
    private Short port;
}
