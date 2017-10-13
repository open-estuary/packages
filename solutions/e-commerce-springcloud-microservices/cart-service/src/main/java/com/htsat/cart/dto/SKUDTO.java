package com.htsat.cart.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SKUDTO implements Serializable{

    Long skuId;

    Long spuId;

    String color;

    String size;

    BigDecimal price;

    BigDecimal displayPrice;

    String currency;

    BigDecimal discount;

    Date dcreatetime;

    Date dupdatetime;

    int quantity;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDisplayPrice() {
        return displayPrice;
    }

    public void setDisplayPrice(BigDecimal displayPrice) {
        this.displayPrice = displayPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getDcreatetime() {
        return dcreatetime;
    }

    public void setDcreatetime(Date dcreatetime) {
        this.dcreatetime = dcreatetime;
    }

    public Date getDupdatetime() {
        return dupdatetime;
    }

    public void setDupdatetime(Date dupdatetime) {
        this.dupdatetime = dupdatetime;
    }

    @Override
    public String toString() {
        return "SKUDTO{" +
                "skuId=" + skuId +
                ", spuId=" + spuId +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", displayPrice=" + displayPrice +
                ", currency='" + currency + '\'' +
                ", discount=" + discount +
                ", dcreatetime=" + dcreatetime +
                ", dupdatetime=" + dupdatetime +
                ", quantity=" + quantity +
                '}';
    }
}
