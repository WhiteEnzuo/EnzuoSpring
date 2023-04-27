package Main.studySpring.Spring.controller;

import Main.studySpring.Spring.Result.Result;
import Main.studySpring.Spring.annotation.Controller;
import Main.studySpring.Spring.annotation.Request;


/**
 * @Classname TestController
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/22 22:21
 * @Created by Enzuo
 */
@Controller("/123")
public class TestController {
    @Request("/456")
    public Result a(){
        Result ok = Result.ok();
        ok.setMessage("456");
        ok.data("a","b");
        return ok;
    }

}

