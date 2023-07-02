package main.studySpring.Tomcat.Model;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname FormItem
 * @Description
 * @Version 1.0.0
 * @Date 2023/5/24 19:26
 * @Created by Enzuo
 */
@Data
@ToString
public class FormItem {
    private String name;
    private String fileName;
    private Boolean isFile;
    private String fileType;
    private String text;
    private byte[] content;
}
