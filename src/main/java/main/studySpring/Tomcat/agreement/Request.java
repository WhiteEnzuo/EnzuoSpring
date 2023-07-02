package main.studySpring.Tomcat.agreement;

import main.studySpring.Tomcat.Model.FormItem;
import main.studySpring.Tomcat.util.FormParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname Request
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/20 20:10
 * @Created by Enzuo
 */
@Data
@Slf4j
public class Request {
    private String uri;//资源路径
    private String method;
    private InputStream inputStream;//读取请求
    private String encoding = "GBK";//请求资源的编码
    private Map<String, String> header;
    private Map<String, String> query;
    private String body;
    private Map<String, FormItem> formData;

    private final String PARAM_INDEX = "Content-Disposition: form-data; ";

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
        this.header = new HashMap<>();
        this.query = new HashMap<>();
    }

    public String getUri() {
        return uri;
    }


    public void parse() throws IOException {
        //读取第一行，请求地址
        String line = readLine(inputStream, 0);
        uri = line.substring(line.indexOf('/'), line.lastIndexOf('/') - 5);
        String[] uriSplit = uri.split("\\?");
        if (uriSplit.length == 2) {
            String s = uriSplit[1];
            uri = uriSplit[0];
            s = s.replace(" ", "");
            String pattern = "(.*)=(.*)";
            Pattern compile = Pattern.compile(pattern);
            Matcher matcher = compile.matcher(s);
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                query.put(key, value);
            }
        }
        String method = new StringTokenizer(line).nextElement().toString();
        this.method = method;
        int contentLength = 0;
        do {
            line = readLine(inputStream, 0);
            String[] split = line.split(":");
            if (split.length > 0) {
                String key = split[0];
                StringBuilder stringBuffer = new StringBuilder();
                for (int i = 1; i < split.length; i++) {
                    stringBuffer.append(split[i]);
                }
                if (stringBuffer.length() <= 0) continue;
                header.put(key, stringBuffer.toString());
            }
            if (line.startsWith("Content-Length")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }

        } while (!line.equals("\r\n"));
        if ("POST".equalsIgnoreCase(method)) {
            body = readLine(inputStream, contentLength);
        }
        if ("PUT".equalsIgnoreCase(method)) {
            body = readLine(inputStream, contentLength);
        }
        if ("DELETE".equalsIgnoreCase(method)) {
            body = readLine(inputStream, contentLength);
        }
        String s = header.get("Content-Type");
        if (s != null && s.contains("multipart/form-data")) {
            String id = null;
            Pattern compile = Pattern.compile("boundary=(.*)");
            Matcher matcher = compile.matcher(s);
            if (matcher.find()) {
                id = matcher.group(1);
            }
            parseForm(body, id);
        }

    }

    private String readLine(InputStream inputStream, int contentLength) throws IOException {
        ArrayList<Object> arrayList = new ArrayList<>();
        byte readByte = 0;
        int total = 0;
        boolean haveBody = false;
        if (contentLength != 0) {//post请求
            while (total < contentLength) {
                readByte = (byte) inputStream.read();
                arrayList.add(readByte);
                total++;
            }
            haveBody = true;
        } else {//get请求
            while (readByte != 10) {
                readByte = (byte) inputStream.read();
                arrayList.add(readByte);
            }
        }

        byte[] tempByteArr = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            tempByteArr[i] = (Byte) arrayList.get(i);
        }
        arrayList.clear();

        String tempStr = new String(tempByteArr, encoding);

        if (tempStr.startsWith("Referer")) {//如果有Referer头时，使用UTF-8编码
            tempStr = new String(tempByteArr, StandardCharsets.UTF_8);
        } else if (haveBody) {
            tempStr = new String(tempByteArr, StandardCharsets.UTF_8);
        }
        return tempStr;

    }

    private void parseForm(String value, String id) {
        try {
            formData = FormParser.parseForm(value, id);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
