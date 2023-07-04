package main.registry.Dao;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Classname ConfigInfo
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/29 8:40
 * @Created by Enzuo
 */
@Data
public class ConfigInfo implements Serializable {
    private Map<String, Object> config;
}
