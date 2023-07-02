package main.studySpring.Tomcat.context;

import main.studySpring.Tomcat.handle.HttpHandle;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname HttpHandleContext
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 19:29
 * @Created by Enzuo
 */

public class HttpHandleContext {
    private Map<String, HttpHandle> httpHandleMap;
    public HttpHandleContext(){
        httpHandleMap=new HashMap<>();
    }

    public Map<String, HttpHandle> getHttpHandleMap() {
        return httpHandleMap;
    }

    public void setHttpHandleMap(Map<String, HttpHandle> httpHandleMap) {
        this.httpHandleMap = httpHandleMap;
    }
    public HttpHandle getHandle(String url){
        if(httpHandleMap==null) {
             httpHandleMap = new HashMap<>();
            return null;
        }
        if(httpHandleMap.containsKey(url))return httpHandleMap.get(url);
        return null;
    }
    public void putHandle(String url,HttpHandle handle){
        if(httpHandleMap==null) {
            httpHandleMap = new HashMap<>();
        }
        httpHandleMap.put(url,handle);
    }
}
