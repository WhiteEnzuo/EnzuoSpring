package Main.studySpring.Mybaits.Mapper;

import Main.studySpring.Mybaits.MapperInterface.BaseMapper;
import Main.studySpring.Mybaits.annotation.Mapper;
import Main.studySpring.Mybaits.model.User;

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
