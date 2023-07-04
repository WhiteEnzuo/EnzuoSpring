package main.studySpring.Spring.controller;

import main.studySpring.Spring.Result.Result;
import main.studySpring.Spring.annotation.controller.Controller;
import main.studySpring.Spring.annotation.controller.Mapping.PostMapping;
import main.studySpring.Spring.annotation.controller.Param;
import main.studySpring.Spring.annotation.controller.RequestBody;
import main.studySpring.Spring.model.Body;


/**
 * @Classname TestController
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/22 22:21
 * @Created by Enzuo
 */
@Controller("/123")
public class TestController {
    @PostMapping("/456")
    public Result a(@Param("id") String id, @RequestBody Body body) {
        System.out.println(id);
        System.out.println(body);
        Result ok = Result.ok();
        ok.setMessage("456");
        ok.data("a", "b");
        return ok;
    }

}

