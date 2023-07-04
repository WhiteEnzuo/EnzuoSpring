package main.registry.agreement;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname RegistryAgreement
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 17:37
 * @Created by Enzuo
 */
@Data
public class RegistryAgreement implements Serializable {
    private Integer typeCode;
    private Integer findTypeCode;
    private String serverName;
    private List<String> classNames;
    private String address;
    private Short port;
}
