package Main.studySpring.Tomcat.agreement;

import Main.studySpring.Tomcat.enmus.StatusCode;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Classname Response
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/20 20:10
 * @Created by Enzuo
 */
@Slf4j
public class Response {
    private Request request;//用于读取资源的uri
    private OutputStream outputStream;//用于输出资源
    private Map<String, Object> header;
    public Response(){
        header=new HashMap<>();

    }
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;

    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public Map<String, Object> getHeader() {
        return this.header;
    }

    public void setHeaderKey(String key, Object value) {
        header.put(key, value);
    }

    public <T> T getHeaderKey(String key, Class<T> clazz) {
        return (T) header.get(key);
    }

    public void sentResponse(String s, StatusCode code) {
        PrintWriter out = new PrintWriter(outputStream);
        out.printf("HTTP/1.0 %d %s\n", code.getCode(), code.getMessage());
//        out.println("Content-Type:text/html;charset=" + StandardCharsets.UTF_8);
        if (header==null||!header.containsKey("Content-Type")) {
            out.println("Content-Type:text/html;charset=" + StandardCharsets.UTF_8);

        }
        headerToOut(out);
        out.println();
        if (code.getCode() == 200) {
            out.println(s);
        } else {
            log.info(request.getMethod()+" "+request.getUri()+" "+code.getCode());
            out.printf("{\"code\":%d,\"message\":\"%s\",\"url\":\"%s\"}", code.getCode(), code.getMessage(), request.getUri());
        }
        out.close();
    }

    private void headerToOut(PrintWriter out) {
        if (header == null) {
            return;
        }
        Set<Map.Entry<String, Object>> entries = header.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
    }

}