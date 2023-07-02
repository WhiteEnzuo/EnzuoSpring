package main.studySpring.Tomcat.util;


import main.studySpring.Tomcat.Model.FormItem;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Classname HttpRequestsFormResolver
 * @Description
 * @Version 1.0.0
 * @Date 2023/5/24 15:27
 * @Created by Enzuo
 */

public class FormParser {
    public static Map<String, FormItem> parseForm(String body, String id) {
        Map<String, FormItem> formItemMap = new HashMap<>();
        body = body.replace("--" + id + "--", "--" + id);
        String[] split = body.split("--" + id);
        for (String s : split) {
            if (s.equals("")) {
                continue;
            }
            FormItem formItem;
            if (s.contains("Content-Type")) {
                formItem = fileParse(s);
            } else {
                formItem = textParse(s);
            }

            formItemMap.put(formItem.getName(), formItem);

        }
        return formItemMap;
    }

    private static FormItem fileParse(String s) {
        FormItem formItem = new FormItem();
        setName(s, formItem);
        formItem.setIsFile(true);
        Pattern compile = Pattern.compile("filename=\"(.*?)\"");
        Matcher matcher = compile.matcher(s);
        if (matcher.find()) {
            String group = matcher.group(1);
            formItem.setFileName(group);
        }
        compile = Pattern.compile("Content-Type: (.*?)");
        matcher = compile.matcher(s);
        if (matcher.find()) {
            String group = matcher.group(1);
            formItem.setFileType(group);
        }
        int i = s.indexOf(System.lineSeparator() + System.lineSeparator());
        String substring = s.substring(i + 4);
        substring = substring.substring(0, substring.length() - System.lineSeparator().length());
        formItem.setContent(substring.getBytes(StandardCharsets.UTF_8));
        return formItem;
    }

    private static FormItem textParse(String s) {
        FormItem formItem = new FormItem();
        setName(s, formItem);
        formItem.setIsFile(false);
        Pattern compile = Pattern.compile(System.lineSeparator() + System.lineSeparator() + "(.*)");
        Matcher matcher = compile.matcher(s);
        if (matcher.find()) {
            String group = matcher.group(1);
            formItem.setText(group);
            formItem.setFileType("text");
        }
        return formItem;
    }

    private static void setName(String s, FormItem formItem) {
        Pattern compile = Pattern.compile("Content-Disposition: form-data; name=\"(.*?)\"");
        Matcher matcher = compile.matcher(s);
        if (matcher.find()) {
            formItem.setName(matcher.group(1));
        }
    }
}
