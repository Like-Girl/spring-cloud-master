package cn.likegirl.rt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ColumnObject implements Serializable {

    private static final long serialVersionUID = 8131431109454998423L;

    /**
     * 标记删除
     * default: 0
     * 0： 可用
     * 1： 删除
     */
    private Integer deleteFlag;

    /**
     * 标记删除的key
     * default: ''
     */
    private String deleteKey;

    /**
     * 创建人
     */
    private String gmtCreate;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private String gmtModified;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;
}
