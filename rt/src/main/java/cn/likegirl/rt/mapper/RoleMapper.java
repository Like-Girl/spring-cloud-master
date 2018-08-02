package cn.likegirl.rt.mapper;

import cn.likegirl.rt.config.database.BaseMapper;
import cn.likegirl.rt.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by HD on 2018/1/31.
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> findRoles(@Param("username") String username);
}
