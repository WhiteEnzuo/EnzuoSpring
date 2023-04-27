package Main.studySpring.Mybaits.listeners;

import Main.studySpring.Mybaits.jdbc.MysqlJDBC;
import Main.studySpring.Mybaits.mapperInit.MapperInit;
import Main.studySpring.Spring.Listeners.ApplicationListener;
import Main.studySpring.Spring.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Classname MybatisListener
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/27 21:13
 * @Created by Enzuo
 */
@Slf4j
public class MybatisListener implements ApplicationListener {
    public MybatisListener(ApplicationContext context){
    }
    public void contextPrepared(ApplicationContext context) {
        Map<String, Object> config = context.getConfig();
        if (config==null){
            log.error("Application is null");
            System.exit(0);
        }
        Map<String, Object> mybatis = (Map<String, Object>)config.get("mybatis");
        if (mybatis==null){
            log.error("Application's mybatis is null");
            System.exit(0);
        }
        String url = (String) mybatis.get("url");
        if (url==null||url.equals("")){
            log.error("databases url is null");
            System.exit(0);
        }
        String username = (String) mybatis.get("username");
        if (username==null||username.equals("")){
            log.error("databases url is null");
            System.exit(0);
        }
        String password = (String) mybatis.get("password");
        if (password==null||password.equals("")){
            log.error("databases password is null");
            System.exit(0);
        }
        log.info("启动Mybatis");
        MysqlJDBC.setConnection(url, username, password);
        log.info("监听数据库 {}",url);
        log.info("启动Mybatis成功");


        log.info("开始注入Mapper");
        new MapperInit().init(context);
        log.info("注入Mapper成功");
    }
}
