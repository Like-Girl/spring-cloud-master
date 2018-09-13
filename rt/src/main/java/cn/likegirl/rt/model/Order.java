package cn.likegirl.rt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单表
 */
@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "luv_order")
public class Order extends ColumnObject implements Serializable {

    private static final long serialVersionUID = -8975511700358117720L;

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 学员id
     */
    private String traineeId;

    /**
     * 情感经历
     */
    private String emotionUndergo;

    /**
     * 个人想法
     */
    private String personalIdea;

    /**
     * 所报课程
     */
    private String courseId;

    /**
     * 应付金额
     */
    private BigDecimal amountPayable;

    /**
     * 定金
     */
    private BigDecimal deposit;

    /**
     * 尾款
     */
    private BigDecimal retainage;

    /**
     * 付款截图
     */
    private String paymentScreenshot;

    /**
     * 备注
     */
    private String notes;


}
