package main.studySpring.Spring.Result;

/**
 * @interfaceName ResultCode
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/25 11:42
 * @Created by Enzuo
 */

public interface ResultCode {
    public static Integer SUCCESS = 200;
    public static Integer FORWARD = 300;
    public static Integer NO_LOGIN = 402; //未登录
    public static Integer REFUSE = 403;  //废弃
    public static Integer BAN = 405; //账号被封禁
    public static Integer ERROR = 500;
    public static Integer FLUSH = 600;
}
