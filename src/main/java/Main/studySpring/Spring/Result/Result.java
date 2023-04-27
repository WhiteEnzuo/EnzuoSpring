package Main.studySpring.Spring.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Result
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/25 11:42
 * @Created by Enzuo
 */

public class Result {

    private Boolean isSuccess;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    public static Result ok() {
        Result ok = new Result().setCode(ResultCode.SUCCESS).setMessage("success").setSuccess(true);
        return ok;
    }

    public static Result error() {
        Result error = new Result().setCode(ResultCode.ERROR).setMessage("failed").setSuccess(false);
        return error;
    }

    public static Result forward() {
        Result forward = new Result().setCode(ResultCode.FORWARD).setMessage("forward").setSuccess(true);
        return forward;
    }

    public static Result flush() {
        Result forward = new Result().setCode(ResultCode.FLUSH).setMessage("flush").setSuccess(true);
        return forward;
    }

    public static Result refuse() {
        return new Result().setCode(ResultCode.REFUSE).setMessage("refuse").setSuccess(false);
    }


    public Result data(String key, Object object) {
        data.put(key, object);
        return this;
    }


    public Boolean getSuccess() {
        return isSuccess;
    }

    public Result setSuccess(Boolean success) {
        isSuccess = success;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Result setData(Map<String, Object> data) {
        this.data = new HashMap<>(data);
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "isSuccess=" + isSuccess +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
