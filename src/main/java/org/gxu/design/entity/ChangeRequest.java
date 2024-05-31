package org.gxu.design.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("change_requests")
public class ChangeRequest {
    @TableId(type = IdType.AUTO)
        private Integer id;
        private User user;
        private Position requestedPosition;
        private Department requestedDepartment;
        private String requestMessage;
        private LocalDateTime requestTime;
        private String status;

        // Getters and Setters

    // 提供 setter 方法
    public void setUser(User user) {
        this.user = user;
    }

    public void setPosition(Position requestedPosition) {
        this.requestedPosition = requestedPosition;
    }

    public void setDepartment(Department requestedDepartment) {
        this.requestedDepartment = requestedDepartment;
    }
}