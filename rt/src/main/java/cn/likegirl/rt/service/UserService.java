package cn.likegirl.rt.service;

import cn.likegirl.rt.model.User;
import cn.likegirl.rt.service.base.Service;

import java.util.List;

public interface UserService extends Service<User> {

    List<User> listByPage(int page,int pageSize);
}
