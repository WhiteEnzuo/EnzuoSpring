package Main.studySpring.Spring.Listeners;

import Main.studySpring.Spring.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @Classname ApplicationListenerStarter
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/21 16:47
 * @Created by Enzuo
 */
@Slf4j
public class ApplicationListenerStarter {
    private ArrayList<ApplicationListener> list;
    private ApplicationContext context;
    public ApplicationListenerStarter(ApplicationContext context){
        list = new ArrayList<>();
        this.context=context;
    }
    public ApplicationListeners stater(){
        return new ApplicationListeners(list,context);
    }
    public void getResource(Class<?> clazz){
        String path = Objects.requireNonNull(clazz.getResource("/ListenerClass.txt")).getPath();
        File file = new File(path);
        try {
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);

            String[] split = new String(bytes).replace("\r","").split("\\n");
            for (String s : split) {
                Class<?> aClass = Class.forName(s);
                Constructor<?> constructor = aClass.getConstructor(ApplicationContext.class);
                list.add((ApplicationListener) constructor.newInstance(context));
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }
}
