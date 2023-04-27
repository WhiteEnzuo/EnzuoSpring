package Main.studySpring.Tomcat.handle.impl;

import Main.studySpring.Tomcat.agreement.Request;
import Main.studySpring.Tomcat.agreement.Response;
import Main.studySpring.Tomcat.enmus.StatusCode;
import Main.studySpring.Tomcat.handle.HttpHandle;
import Main.studySpring.Spring.annotation.Bean;
import Main.studySpring.global.UrlMap;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname HttpHandleImpl
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 19:27
 * @Created by Enzuo
 */
@Bean
@Slf4j
public class HttpHandleImpl implements HttpHandle {
    @Override

    public void handle(Request request, Response response) {

        List<String> list = new ArrayList<>();
        getUrl(list,request.getUri());
        if (list.size()==0) {
            response.setHeaderKey("Content-Type","application/json;charset=UTF-8");
            response.sentResponse("note found", StatusCode.NOTFOUND);
            return;
        }
        if(!UrlMap.controllerContext.containsKey(list.get(0))){
            response.setHeaderKey("Content-Type","application/json;charset=UTF-8");
            response.sentResponse("note found", StatusCode.NOTFOUND);
            return;
        }
        Method method = returnMethod(UrlMap.controllerContext.get(list.get(0)), list);
        if(method==null){
            response.setHeaderKey("Content-Type","application/json;charset=UTF-8");
            response.sentResponse("note found", StatusCode.NOTFOUND);
            return;
        }
        Object result=null;
        try {
            Object o = method.getDeclaringClass().newInstance();
            result= method.invoke(o);
            if(result==null){
                response.setHeaderKey("Content-Type","application/json;charset=UTF-8");
                response.sentResponse("Error", StatusCode.ERROR);
            }
            response.setHeaderKey("Content-Type","application/json;charset=UTF-8");
            assert result != null;
            if(result instanceof String){
                response.sentResponse((String) result, StatusCode.OK);
                return;
            }
            if(result instanceof Integer){
                response.sentResponse(String.valueOf(result), StatusCode.OK);
                return;
            }
            if(result instanceof Character){
                response.sentResponse(String.valueOf(result), StatusCode.OK);
                return;
            }
            response.sentResponse(new JSONObject(result).toString(), StatusCode.OK);
            log.info(request.getMethod()+" "+request.getUri()+" "+StatusCode.OK.getCode());
        }catch (Exception ex){
            log.error(ex.getMessage());
        }

    }
    private void getUrl(List<String> list,String url){
        String[] split = url.split("/");
        for (int i = 1; i < split.length; i++) {
            list.add("/"+split[i]);
        }
    }
    private Method returnMethod(Map<String,Method> map , List<String> list){
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 1; i < list.size(); i++) {
            stringBuffer.
                    append(list.get(i));

        }
        if(!map.containsKey(stringBuffer.toString()))return null;
        return map.get(stringBuffer.toString());
    }
}
