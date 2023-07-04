package main.studySpring.Tomcat.init;

import main.studySpring.Tomcat.annotation.Controller;
import main.studySpring.Tomcat.context.HttpHandleContext;
import main.studySpring.Tomcat.handle.HttpHandle;

import java.io.File;
import java.util.Objects;

/**
 * @Classname ServerInit
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 19:36
 * @Created by Enzuo
 */

public class ServerInit {


    private Class<?> clazz;
    private String[] args;
    private HttpHandleContext context;
    private String classpath;

    public ServerInit() {
    }

    public HttpHandleContext getContext() {
        return context;
    }

    public void setContext(HttpHandleContext context) {
        this.context = context;
    }

    public ServerInit(Class<?> clazz, String[] args, HttpHandleContext context) {

        this.clazz = clazz;
        this.args = args;
        this.context = context;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void init() {
        String[] split = clazz.getName().split("\\.");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            sb.append(split[i]);
        }
        classpath = sb.toString();
        String path = Objects.requireNonNull(clazz.getResource("")).getPath();
        getAllClass(new File(path));
    }

    private void getAllClass(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File pathFile : files) {
                if (pathFile.isDirectory()) {
                    getAllClass(pathFile);
                    continue;
                }
                if (pathFile.getName().contains(".class")) {
                    String className = pathFile.getPath().substring(pathFile.getPath().indexOf(classpath)).replace(".class", "").replace("\\", ".");
                    try {
                        Class<?> obj = Class.forName(className);
                        Controller controllerAnnotation = obj.getAnnotation(Controller.class);
                        if (controllerAnnotation == null) {
                            continue;
                        }
                        if (obj.isInterface()) {
                            //执行接口方法
                            continue;
                        }
                        if (!obj.isPrimitive()) {
                            Class<?>[] interfaces = obj.getInterfaces();

                            boolean flag = true;
                            for (Class<?> handleInterface : interfaces) {
                                if (handleInterface.equals(HttpHandle.class)) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                continue;
                            }

                            context.putHandle(controllerAnnotation.value(), (HttpHandle) obj.newInstance());

                        }
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
