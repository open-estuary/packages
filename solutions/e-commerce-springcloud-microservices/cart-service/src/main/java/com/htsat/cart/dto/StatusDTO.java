package com.htsat.cart.dto;

import com.htsat.cart.enums.ExcuteStatusEnum;

import java.io.Serializable;

public class StatusDTO implements Serializable{

    Long userId;

    ExcuteStatusEnum status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
