package com.htsat.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDTO implements Serializable{

    private String orderId;

    private int userId;

    private String parentOrderid;

    private String paymentMethod;

    private String paymentMethodTitle;

    private float discount;

    private int totalQuantity;

    private BigDecimal totalPrice;

    private String version;

    private AddressDTO addressDTO;

    private String status;

    private Date paidDate;

    private Date completeDate;

    private String customerMark;

    private DeliveryDTO deliveryDTO;

    private List<OrderSKUDTO> orderskudtoList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getParentOrderid() {
        return parentOrderid;
    }

    public void setParentOrderid(String parentOrderid) {
        this.parentOrderid = parentOrderid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodTitle() {
        return paymentMethodTitle;
    }

    public void setPaymentMethodTitle(String paymentMethodTitle) {
        this.paymentMethodTitle = paymentMethodTitle;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getCustomerMark() {
        return customerMark;
    }

    public void setCustomerMark(String customerMark) {
        this.customerMark = customerMark;
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

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", parentOrderid='" + parentOrderid + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentMethodTitle='" + paymentMethodTitle + '\'' +
                ", discount=" + discount +
                ", totalQuantity=" + totalQuantity +
                ", totalPrice=" + totalPrice +
                ", version='" + version + '\'' +
                ", addressDTO=" + addressDTO +
                ", status='" + status + '\'' +
                ", paidDate=" + paidDate +
                ", completeDate=" + completeDate +
                ", customerMark='" + customerMark + '\'' +
                ", deliveryDTO=" + deliveryDTO +
                ", orderskudtoList=" + orderskudtoList +
                '}';
    }
}
