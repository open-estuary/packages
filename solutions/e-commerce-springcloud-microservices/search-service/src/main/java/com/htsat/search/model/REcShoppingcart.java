package com.htsat.search.model;

import java.math.BigDecimal;
import java.util.Date;

public class REcShoppingcart {
    private Integer nuserid;

    private Date supdatetime;

    private Float ndiscount;

    private Integer ntotalquantity;

    private String scurrency;

    private BigDecimal ntotalprice;

    public Integer getNuserid() {
        return nuserid;
    }

    public void setNuserid(Integer nuserid) {
        this.nuserid = nuserid;
    }

    public Date getSupdatetime() {
        return supdatetime;
    }

    public void setSupdatetime(Date supdatetime) {
        this.supdatetime = supdatetime;
    }

    public Float getNdiscount() {
        return ndiscount;
    }

    public void setNdiscount(Float ndiscount) {
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
}