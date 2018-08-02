package cn.likegirl.rt.service.base;

import cn.likegirl.rt.mapper.PermissionMapper;
import cn.likegirl.rt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BaseService<T> extends AbstractService<T> {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public PermissionMapper permissionMapper;

}
