package com.htsat.order.dto;

import com.htsat.order.enums.ExcuteStatusEnum;

import java.io.Serializable;

public class StatusDTO implements Serializable{

    Long userId;

    ExcuteStatusEnum status;

    String info;

    String error;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "StatusDTO{" +
                "userId=" + userId +
                ", status=" + status +
                ", info='" + info + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
