package com.htsat.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderSKUDTO implements Serializable{

    private String orderId;

    private int skuId;

    private int quantity;

    private BigDecimal originPrice;

    private float discount;

    private String currency;

    private BigDecimal price;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderSKUDTO{" +
                "orderId='" + orderId + '\'' +
                ", skuId=" + skuId +
                ", quantity=" + quantity +
                ", originPrice=" + originPrice +
                ", discount=" + discount +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                '}';
    }
}
