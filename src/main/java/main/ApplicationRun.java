package main;

import main.studySpring.Application;
import lombok.extern.slf4j.Slf4j;

/**
 * @Classname ApplicantionRun
 * @Description
 * @Version 1.0.0'
 * @Date 2023/4/20 12:32
 * @Created by Enzuo
 */
@Slf4j
public class ApplicationRun {
    public static void main(String[] args) {
        Application.run(ApplicationRun.class, args);
    }
}
