package cn.likegirl.rt.model;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 课程表
 */

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "luv_course")
public class Course extends ColumnObject implements Serializable {

    private static final long serialVersionUID = 112962305065937054L;

    /**
     * 课程ID
     */
    @Id
    private String id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 主要负责导师
     */
    private String principal;

    /**
     * 课程图文地址
     */
    private String url;

}


