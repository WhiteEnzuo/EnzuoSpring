package Main.studySpring.Mybaits.context;


import Main.studySpring.Mybaits.MapperInterface.BaseMapper;
import Main.studySpring.Mybaits.MapperInterface.impl.BaseMapperInvoke;
import Main.studySpring.Mybaits.annotation.Mapper;

import java.io.File;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname MapperContext
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/26 13:28
 * @Created by Enzuo
 */

public class MapperContext {
    private Map<Class<?>,Object> context;
    private String classpath;
    public MapperContext(){
        context=new HashMap<>();
    }

    public Map<Class<?>, Object> getContext() {
        return context;
    }
    public <T>  T get(Class<T> clazz) {
        return (T) context.get(clazz);
    }


    public void setContext(Map<Class<?>, Object> context) {
        this.context = context;
    }
    public void init(Class<?> clazz){
        String[] split = clazz.getName().split("\\.");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length-1; i++) {
            sb.append(split[i]).append(".");
        }
        if(sb.charAt(sb.length()-1)=='.')sb.deleteCharAt(sb.length()-1);
        classpath=sb.toString();
        String path = Objects.requireNonNull(clazz.getResource("")).getPath();
        initMapper(new File(path));
    }
    private void initMapper(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            assert files != null;
            for (File pathFile : files) {
                if (pathFile.isDirectory()) {
                    initMapper(pathFile);
                    continue;
                }
                if (pathFile.getName().contains(".class")) {
                    String classPath = pathFile.getPath().replace("\\", ".").replace(".class","");
                    int i = classPath.indexOf(classpath);
                    String classname = classPath.substring(i);
                    try {
                        Class<?> obj = Class.forName(classname);
                        Mapper mapper = obj.getAnnotation(Mapper.class);
                        if(mapper==null)continue;
                        if(obj.isInterface()){
                            //执行接口方法
                            Type temp = null;
                            Type[] genericInterfaces = obj.getGenericInterfaces();
                            for (Type genericInterface : genericInterfaces) {
                                String typeName = genericInterface.getTypeName();
                                if(typeName.contains(BaseMapper.class.getName())){
                                    temp=genericInterface;
                                    break;
                                }
                            }
                            if (temp==null) {
                                continue;
                            }
                            String pattern = "(.*)<(.*)>";
                            Pattern compile = Pattern.compile(pattern);
                            Matcher matcher = compile.matcher(temp.getTypeName());
                            String genericName = "";
                            if(matcher.find()){
                                try {
                                    genericName= matcher.group(2);
                                }catch (Exception e){
                                    continue;
                                }
                            }

                            Object o = Proxy.newProxyInstance(obj.getClassLoader(), new Class[]{obj},new BaseMapperInvoke(Class.forName(genericName)));
                            context.put(obj,o);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}
