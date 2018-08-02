package cn.likegirl.rt.service;

import cn.likegirl.rt.model.User;
import cn.likegirl.rt.tools.IService;

import java.util.List;

public interface UserService extends IService<User> {

    List<User> listByPage(int page,int pageSize);
}
