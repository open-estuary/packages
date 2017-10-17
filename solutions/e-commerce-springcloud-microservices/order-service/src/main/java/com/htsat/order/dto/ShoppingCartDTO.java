package com.htsat.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ShoppingCartDTO implements Serializable{

    Long nshoppingcartid;

    Long userId;

    BigDecimal discount;

    BigDecimal price;

    int quantity;

    String currency;

    List<SKUDTO> skudtoList;

    Date supdatetime;

    Date ncreatetime;

    public Long getNshoppingcartid() {
        return nshoppingcartid;
    }

    public void setNshoppingcartid(Long nshoppingcartid) {
        this.nshoppingcartid = nshoppingcartid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<SKUDTO> getSkudtoList() {
        return skudtoList;
    }

    public void setSkudtoList(List<SKUDTO> skudtoList) {
        this.skudtoList = skudtoList;
    }

    public Date getSupdatetime() {
        return supdatetime;
    }

    public void setSupdatetime(Date supdatetime) {
        this.supdatetime = supdatetime;
    }

    public Date getNcreatetime() {
        return ncreatetime;
    }

    public void setNcreatetime(Date ncreatetime) {
        this.ncreatetime = ncreatetime;
    }

    @Override
    public String toString() {
        return "ShoppingCartDTO{" +
                "nshoppingcartid=" + nshoppingcartid +
                ", userId=" + userId +
                ", discount=" + discount +
                ", price=" + price +
                ", quantity=" + quantity +
                ", currency='" + currency + '\'' +
                ", skudtoList=" + skudtoList +
                ", supdatetime=" + supdatetime +
                ", ncreatetime=" + ncreatetime +
                '}';
    }
}
