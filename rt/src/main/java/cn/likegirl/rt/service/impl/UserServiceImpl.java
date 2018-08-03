package cn.likegirl.rt.service.impl;

import cn.likegirl.rt.config.database.ReadOnlyConnection;
import cn.likegirl.rt.model.User;
import cn.likegirl.rt.service.UserService;
import cn.likegirl.rt.service.base.BaseService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseService<User> implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);



//    @ReadOnlyConnection
    @Override
    public List<User> listByPage(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return userMapper.selectAll();
    }
}
