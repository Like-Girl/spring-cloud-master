package cn.likegirl.rt.service.base;

import cn.likegirl.rt.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BaseService<T> extends AbstractService<T> {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public PermissionMapper permissionMapper;

    @Autowired
    public OrderMapper orderMapper;

    @Autowired
    public CourseMapper courseMapper;

    @Autowired
    public TutorMapper tutorMapper;

}
