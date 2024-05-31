package org.gxu.design.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 职位实体类
 */
@Data
@ToString
@TableName("position")
public class Position implements Serializable {

    /**
     * 职位ID
     */
    @TableId
    private int id;

    /**
     * 职位名称
     */
    private String name;

    /**
     * 职位状态
     */
    private int status;

    /**
     * 职位描述
     */
    private String description;
}
