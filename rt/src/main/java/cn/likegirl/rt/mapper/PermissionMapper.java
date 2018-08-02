package cn.likegirl.rt.mapper;

import cn.likegirl.rt.model.Permission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * Created by HD on 2018/1/31.
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> findPermissions(@Param("username") String username);
}
