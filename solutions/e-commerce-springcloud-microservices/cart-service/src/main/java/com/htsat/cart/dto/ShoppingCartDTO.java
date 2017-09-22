package com.htsat.cart.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ShoppingCartDTO implements Serializable{

    int userId;

    Date date;

    float discount;

    BigDecimal price;

    int quantity;

    String currency;

    List<SKUDTO> skudtoList;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
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

    @Override
    public String toString() {
        return "ShoppingCartDTO{" +
                "userId=" + userId +
                ", date=" + date +
                ", discount=" + discount +
                ", price=" + price +
                ", quantity=" + quantity +
                ", currency='" + currency + '\'' +
                ", skudtoList=" + skudtoList +
                '}';
    }
}
