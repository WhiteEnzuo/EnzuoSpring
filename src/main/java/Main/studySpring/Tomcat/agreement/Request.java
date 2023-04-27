package Main.studySpring.Tomcat.agreement;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @Classname Request
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/20 20:10
 * @Created by Enzuo
 */
@Data
public class Request{
    private String uri;//资源路径
    private String method;
    private InputStream inputStream;//读取请求
    private String encoding = "GBK";//请求资源的编码
    private Map<String,String> header;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
        this.header= new HashMap<>();
    }

    public String getEncoding() {
        return encoding;
    }

    public String getUri() {
        return uri;
    }
    public Map<String,String> getHeader(){return header;}
    public String getHeaderValue(String key){
        if(!this.header.containsKey(key))return "";
        return this.header.get(key);
    }



    public void parse() throws IOException {
        //读取第一行，请求地址
        String line = readLine(inputStream, 0);
        uri = line.substring(line.indexOf('/'),line.lastIndexOf('/') - 5);
        String method = new StringTokenizer(line).nextElement().toString();
        this.method=method;
        int contentLength = 0;
        do{
            line = readLine(inputStream, 0);
            String[] split = line.split(":");
            if(split.length>0){
                String key = split[0];
                StringBuilder stringBuffer = new StringBuilder();
                for (int i = 1; i < split.length; i++) {
                    stringBuffer.append(split[i]);
                }
                if(stringBuffer.length()<=0)continue;
                header.put(key,stringBuffer.toString());
            }
            if(line.startsWith("Content-Length")){
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }

        }while(!line.equals("\r\n"));
        if("POST".equalsIgnoreCase(method)){
            readLine(inputStream, contentLength);
        }
        if("PUT".equalsIgnoreCase(method)){
            readLine(inputStream, contentLength);
        }
        if("DELETE".equalsIgnoreCase(method)){
            readLine(inputStream, contentLength);
        }
    }

    private String readLine(InputStream inputStream, int contentLength) throws IOException{
        ArrayList<Object> arrayList = new ArrayList<>();
        byte readByte = 0;
        int total = 0;
        if(contentLength != 0){//post请求
            while(total < contentLength){
                readByte = (byte)inputStream.read();
                arrayList.add(readByte);
                total++;
            }
        }else{//get请求
            while(readByte != 10){
                readByte = (byte)inputStream.read();
                arrayList.add(readByte);
            }
        }

        byte[] tempByteArr = new byte[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            tempByteArr[i] = (Byte) arrayList.get(i);
        }
        arrayList.clear();

        String tempStr = new String(tempByteArr,encoding);

        if(tempStr.startsWith("Referer")){//如果有Referer头时，使用UTF-8编码
            tempStr = new String(tempByteArr, StandardCharsets.UTF_8);
        }
        return tempStr;

    }

}
