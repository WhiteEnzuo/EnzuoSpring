package main.studySpring.mybaits.Mapper;

import main.studySpring.mybaits.MapperInterface.BaseMapper;
import main.studySpring.mybaits.annotation.Mapper;
import main.studySpring.mybaits.model.User;

/**
 * @Classname UserMapper
 * @Description
 * @Version 1.0.0
 * @Date 2023/4/26 13:26
 * @Created by Enzuo
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
