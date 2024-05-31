package org.gxu.design.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="messageitems")
public class MessageItems implements Serializable {
    @TableId(type = IdType.ASSIGN_UUID)
    private Integer id;

    private Integer isUsed;

    @TableField(exist = false)
    private Admin admin;

    @TableField(exist = false)
    private User user;

    private String message;

    private Date sendTime;

    private Integer messageType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @NotNull
    public static MessageItems build(Integer id, Integer isUsed, Admin admin, User user, String msg, Date date, Integer messageType) {
        return new MessageItems(id, isUsed, admin, user, msg, date, messageType);
    }
}