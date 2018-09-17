package cn.likegirl.rt.model;

import lombok.*;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: Activity
 * @description: TODO
 * @date 2018/9/17 16:55
 */
@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "luv_activity")
public class Activity extends ColumnObject implements Serializable {

    private static final long serialVersionUID = 2384380538839335410L;

    private String id;

    /**
     * 活动期数
     */
    private String issue;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 主要负责导师
     */
    private String principal;

    /**
     * 课程
     */
    private String courseId;

    /**
     * 活动状态 0：已结束 1：进行中
     */
    private Integer status;
}
