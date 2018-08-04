package cn.likegirl.rt.mapper;

import cn.likegirl.rt.config.database.BaseMapper;
import cn.likegirl.rt.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by HD on 2018/1/31.
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
