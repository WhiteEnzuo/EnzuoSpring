package main.studySpring.Tomcat.enmus;

public enum StatusCode {
    OK(200, "OK"),
    ERROR(500, "ERROR"),
    Moved_Permanently(301, "Moved Permanently"),
    Bad_Request(400, "Bad Request"),
    Forbidden(403, "Forbidden"),
    NOTFOUND(404, "Not Found"),
    Method_Not_Allowed(405, "Method Not Allowed"),
    Bad_Gateway(502, "Bad Gateway");


    int code;
    String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
