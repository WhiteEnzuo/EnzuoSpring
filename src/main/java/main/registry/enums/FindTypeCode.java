package main.registry.enums;

/**
 * @Classname FindTypeCode
 * @Description
 * @Version 1.0.0
 * @Date 2023/6/26 18:39
 * @Created by Enzuo
 */

public enum FindTypeCode {
    POLLING(0), //轮询
    RANDOM(1);  //随机
    private final Integer type;

    FindTypeCode(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
