package Main.studySpring.Spring.controller;

import Main.studySpring.Spring.Result.Result;
import Main.studySpring.Spring.annotation.controller.Controller;
import Main.studySpring.Spring.annotation.controller.Mapping.PostMapping;
import Main.studySpring.Spring.annotation.controller.Param;
import Main.studySpring.Spring.annotation.controller.RequestBody;
import Main.studySpring.Spring.model.Body;


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
    public Result a(@Param("id")String id,@RequestBody Body body){
        System.out.println(id);
        System.out.println(body);
        Result ok = Result.ok();
        ok.setMessage("456");
        ok.data("a","b");
        return ok;
    }

}

