package cn.likegirl.rt.service.impl;

import cn.likegirl.rt.model.Course;
import cn.likegirl.rt.service.CourseService;
import cn.likegirl.rt.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class CourseServiceImpl extends BaseService<Course> implements CourseService {
}
