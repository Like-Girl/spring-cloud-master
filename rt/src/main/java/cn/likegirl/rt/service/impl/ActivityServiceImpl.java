package cn.likegirl.rt.service.impl;

import cn.likegirl.rt.model.Activity;
import cn.likegirl.rt.service.ActivityService;
import cn.likegirl.rt.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: ActivityServiceImpl
 * @description: TODO
 * @date 2018/9/18 10:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ActivityServiceImpl extends BaseService<Activity> implements ActivityService {

}
