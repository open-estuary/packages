package com.htsat.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDTO implements Serializable{

    private Long orderId;

    private Long userId;

    private Long parentOrderid;

    private Short paymentMethod;

    private BigDecimal discount;

    private int totalQuantity;

    private BigDecimal totalPrice;

    private Short cstatus;

    private Date screatetime;

    private Date supdatetime;

    private Date scompletedtime;

    private String scustomermark;

    private String sordercode;

    private String sshopcode;

    private Short sordertype;

    private Date dpaymenttime;

    private Short sordersource;

    private AddressDTO addressDTO;

    private DeliveryDTO deliveryDTO;

    private List<OrderSKUDTO> orderskudtoList;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentOrderid() {
        return parentOrderid;
    }

    public void setParentOrderid(Long parentOrderid) {
        this.parentOrderid = parentOrderid;
    }

    public Short getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Short paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Short getCstatus() {
        return cstatus;
    }

    public void setCstatus(Short cstatus) {
        this.cstatus = cstatus;
    }

    public Date getScreatetime() {
        return screatetime;
    }

    public void setScreatetime(Date screatetime) {
        this.screatetime = screatetime;
    }

    public Date getSupdatetime() {
        return supdatetime;
    }

    public void setSupdatetime(Date supdatetime) {
        this.supdatetime = supdatetime;
    }

    public Date getScompletedtime() {
        return scompletedtime;
    }

    public void setScompletedtime(Date scompletedtime) {
        this.scompletedtime = scompletedtime;
    }

    public String getScustomermark() {
        return scustomermark;
    }

    public void setScustomermark(String scustomermark) {
        this.scustomermark = scustomermark;
    }

    public String getSordercode() {
        return sordercode;
    }

    public void setSordercode(String sordercode) {
        this.sordercode = sordercode;
    }

    public String getSshopcode() {
        return sshopcode;
    }

    public void setSshopcode(String sshopcode) {
        this.sshopcode = sshopcode;
    }

    public Short getSordertype() {
        return sordertype;
    }

    public void setSordertype(Short sordertype) {
        this.sordertype = sordertype;
    }

    public Date getDpaymenttime() {
        return dpaymenttime;
    }

    public void setDpaymenttime(Date dpaymenttime) {
        this.dpaymenttime = dpaymenttime;
    }

    public Short getSordersource() {
        return sordersource;
    }

    public void setSordersource(Short sordersource) {
        this.sordersource = sordersource;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public DeliveryDTO getDeliveryDTO() {
        return deliveryDTO;
    }

    public void setDeliveryDTO(DeliveryDTO deliveryDTO) {
        this.deliveryDTO = deliveryDTO;
    }

    public List<OrderSKUDTO> getOrderskudtoList() {
        return orderskudtoList;
    }

    public void setOrderskudtoList(List<OrderSKUDTO> orderskudtoList) {
        this.orderskudtoList = orderskudtoList;
    }
}
