package cn.likegirl.rt.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


/**
 * 学员表
 */
@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Trainee extends ColumnObject implements Serializable {

    private static final long serialVersionUID = 6106129198056642531L;

    /**
     * id
     */
    private String id;

    /**
     * 学员名字
     */
    private String name;

    /**
     * 学员联系方式
     */
    private String phone;

    /**
     * 学员微信
     */
    private String wechat;

    /**
     * 学员QQ
     */
    private String qq;

    /**
     * 学员所在城市
     */
    private String city;

    /**
     * 出生年月
     */
    private Date birthDate;

    /**
     * 情感经历
     */
    private String emotionUndergo;
}
