package com.htsat.order.model;

import java.math.BigDecimal;
import java.util.Date;

public class REcOrderinfo {
    private Long norderid;

    private Long nuserid;

    private Long sparentorderid;

    private Short cpaymentmethod;

    private BigDecimal ndiscount;

    private Integer ntotalquantity;

    private BigDecimal ntotalprice;

    private Short cstatus;

    private Date screatetime;

    private Date supdatetime;

    private Date scompletedtime;

    private String scustomermark;

    private Long ndeliveryid;

    private String sordercode;

    private String sshopcode;

    private Short sordertype;

    private Date dpaymenttime;

    private Short sordersource;

    private Long naddressid;

    public Long getNorderid() {
        return norderid;
    }

    public void setNorderid(Long norderid) {
        this.norderid = norderid;
    }

    public Long getNuserid() {
        return nuserid;
    }

    public void setNuserid(Long nuserid) {
        this.nuserid = nuserid;
    }

    public Long getSparentorderid() {
        return sparentorderid;
    }

    public void setSparentorderid(Long sparentorderid) {
        this.sparentorderid = sparentorderid;
    }

    public Short getCpaymentmethod() {
        return cpaymentmethod;
    }

    public void setCpaymentmethod(Short cpaymentmethod) {
        this.cpaymentmethod = cpaymentmethod;
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

    public BigDecimal getNtotalprice() {
        return ntotalprice;
    }

    public void setNtotalprice(BigDecimal ntotalprice) {
        this.ntotalprice = ntotalprice;
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
        this.scustomermark = scustomermark == null ? null : scustomermark.trim();
    }

    public Long getNdeliveryid() {
        return ndeliveryid;
    }

    public void setNdeliveryid(Long ndeliveryid) {
        this.ndeliveryid = ndeliveryid;
    }

    public String getSordercode() {
        return sordercode;
    }

    public void setSordercode(String sordercode) {
        this.sordercode = sordercode == null ? null : sordercode.trim();
    }

    public String getSshopcode() {
        return sshopcode;
    }

    public void setSshopcode(String sshopcode) {
        this.sshopcode = sshopcode == null ? null : sshopcode.trim();
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

    public Long getNaddressid() {
        return naddressid;
    }

    public void setNaddressid(Long naddressid) {
        this.naddressid = naddressid;
    }
}