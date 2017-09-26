package com.htsat.cart.model;

import java.math.BigDecimal;
import java.util.Date;

public class REcShoppingcart {
    private Long nshoppingcartid;

    private Long nuserid;

    private Date supdatetime;

    private BigDecimal ndiscount;

    private Integer ntotalquantity;

    private String scurrency;

    private BigDecimal ntotalprice;

    private Date ncreatetime;

    public Long getNshoppingcartid() {
        return nshoppingcartid;
    }

    public void setNshoppingcartid(Long nshoppingcartid) {
        this.nshoppingcartid = nshoppingcartid;
    }

    public Long getNuserid() {
        return nuserid;
    }

    public void setNuserid(Long nuserid) {
        this.nuserid = nuserid;
    }

    public Date getSupdatetime() {
        return supdatetime;
    }

    public void setSupdatetime(Date supdatetime) {
        this.supdatetime = supdatetime;
    }

    public BigDecimal getNdiscount() {
        return ndiscount;
    }

    public void setNdiscount(BigDecimal ndiscount) {
        this.ndiscount = ndiscount;
    }

    public Integer getNtotalquantity() {
        return ntotalquantity;
    }

    public void setNtotalquantity(Integer ntotalquantity) {
        this.ntotalquantity = ntotalquantity;
    }

    public String getScurrency() {
        return scurrency;
    }

    public void setScurrency(String scurrency) {
        this.scurrency = scurrency == null ? null : scurrency.trim();
    }

    public BigDecimal getNtotalprice() {
        return ntotalprice;
    }

    public void setNtotalprice(BigDecimal ntotalprice) {
        this.ntotalprice = ntotalprice;
    }

    public Date getNcreatetime() {
        return ncreatetime;
    }

    public void setNcreatetime(Date ncreatetime) {
        this.ncreatetime = ncreatetime;
    }
}