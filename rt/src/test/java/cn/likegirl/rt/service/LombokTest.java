package cn.likegirl.rt.service;

import cn.likegirl.rt.model.Course;
import org.junit.Test;

import java.util.Date;

public class LombokTest {

    @Test
    public void test01(){
        Course course = Course.builder().name("ck").build();

        course.setCreateTime(new Date());
        System.out.println(course.toString());
    }
}
