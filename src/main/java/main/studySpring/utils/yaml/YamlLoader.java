package main.studySpring.utils.yaml;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * @Classname YamlLoader
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/27 21:59
 * @Created by Enzuo
 */
@Slf4j
public class YamlLoader {
    public static Map<String, Object> getLoad(Class<?> clazz) {
        Yaml yaml = new Yaml();
        InputStream resourceAsStream = clazz.getClassLoader().getResourceAsStream("application.yaml");
        if (resourceAsStream == null) {
            log.error("application.yaml is null");
            System.exit(0);
        }
        return (Map<String, Object>) yaml.load(resourceAsStream);
    }
}
