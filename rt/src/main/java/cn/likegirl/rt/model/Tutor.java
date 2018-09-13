package cn.likegirl.rt.model;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 导师表
 */
@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "luv_tutor")
public class Tutor extends ColumnObject implements Serializable {

    private static final long serialVersionUID = 2069998255809358726L;

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 导师头像
     */
    private String avatar;

    /**
     * 导师照片
     */
    private String photo;

    /**
     * 特色
     */
    private String characteristic;

    /**
     * QQ
     */
    private String qq;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 导师介绍图文地址
     */
    private String url;

}
