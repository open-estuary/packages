package com.htsat.order.dto;

import com.htsat.order.enums.ExcuteStatusEnum;

import java.io.Serializable;

public class StatusDTO implements Serializable{

    int userId;

    ExcuteStatusEnum status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ExcuteStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ExcuteStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StatusDTO{" +
                "userId=" + userId +
                ", status=" + status +
                '}';
    }
}
