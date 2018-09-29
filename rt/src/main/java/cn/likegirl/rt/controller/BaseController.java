package cn.likegirl.rt.controller;

import cn.likegirl.rt.service.ActivityService;
import cn.likegirl.rt.service.CourseService;
import cn.likegirl.rt.service.OrderService;
import cn.likegirl.rt.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    protected OrderService orderService;

    @Autowired
    protected TutorService tutorService;

    @Autowired
    protected CourseService courseService;

    @Autowired
    protected ActivityService activityService;

}
