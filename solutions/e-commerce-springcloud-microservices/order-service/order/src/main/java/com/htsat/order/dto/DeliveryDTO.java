package com.htsat.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DeliveryDTO implements Serializable{

    private String deliveryId;

    private String expressCompany;

    private BigDecimal deliveryPrice;

    private String status;

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DeliveryDTO{" +
                "deliveryId='" + deliveryId + '\'' +
                ", expressCompany='" + expressCompany + '\'' +
                ", deliveryPrice=" + deliveryPrice +
                ", status='" + status + '\'' +
                '}';
    }
}
