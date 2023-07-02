package main.registry.enums;

public enum RegisterType {
    REGISTER(0),
    GET_SEVER_INFO(1),
    GET_ALL_INFO(2),
    REGISTER_CLASS(3),
    GET_SEVER_CONFIG(4);
    private final Integer type;
    RegisterType(Integer type){
        this.type=type;
    }
    public Integer getType(){
        return type;
    }
}
