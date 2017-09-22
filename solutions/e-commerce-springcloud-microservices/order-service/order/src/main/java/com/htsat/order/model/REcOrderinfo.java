package com.htsat.order.model;

import java.math.BigDecimal;
import java.util.Date;

public class REcOrderinfo {
    private String sorderid;

    private Integer nuserid;

    private String sparentorderid;

    private String cpaymentmethod;

    private String spaymentmethodtitle;

    private Float ndiscount;

    private Integer ntotalquantity;

    private BigDecimal ntotalprice;

    private String sversion;

    private Integer naddressno;

    private String cstatus;

    private Date sdateCreated;

    private Date sdateModified;

    private Date sdatePaid;

    private Date sdateCompleted;

    private String scustomermark;

    private String sdeliveryid;

    public String getSorderid() {
        return sorderid;
    }

    public void setSorderid(String sorderid) {
        this.sorderid = sorderid == null ? null : sorderid.trim();
    }

    public Integer getNuserid() {
        return nuserid;
    }

    public void setNuserid(Integer nuserid) {
        this.nuserid = nuserid;
    }

    public String getSparentorderid() {
        return sparentorderid;
    }

    public void setSparentorderid(String sparentorderid) {
        this.sparentorderid = sparentorderid == null ? null : sparentorderid.trim();
    }

    public String getCpaymentmethod() {
        return cpaymentmethod;
    }

    public void setCpaymentmethod(String cpaymentmethod) {
        this.cpaymentmethod = cpaymentmethod == null ? null : cpaymentmethod.trim();
    }

    public String getSpaymentmethodtitle() {
        return spaymentmethodtitle;
    }

    public void setSpaymentmethodtitle(String spaymentmethodtitle) {
        this.spaymentmethodtitle = spaymentmethodtitle == null ? null : spaymentmethodtitle.trim();
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

    public BigDecimal getNtotalprice() {
        return ntotalprice;
    }

    public void setNtotalprice(BigDecimal ntotalprice) {
        this.ntotalprice = ntotalprice;
    }

    public String getSversion() {
        return sversion;
    }

    public void setSversion(String sversion) {
        this.sversion = sversion == null ? null : sversion.trim();
    }

    public Integer getNaddressno() {
        return naddressno;
    }

    public void setNaddressno(Integer naddressno) {
        this.naddressno = naddressno;
    }

    public String getCstatus() {
        return cstatus;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus == null ? null : cstatus.trim();
    }

    public Date getSdateCreated() {
        return sdateCreated;
    }

    public void setSdateCreated(Date sdateCreated) {
        this.sdateCreated = sdateCreated;
    }

    public Date getSdateModified() {
        return sdateModified;
    }

    public void setSdateModified(Date sdateModified) {
        this.sdateModified = sdateModified;
    }

    public Date getSdatePaid() {
        return sdatePaid;
    }

    public void setSdatePaid(Date sdatePaid) {
        this.sdatePaid = sdatePaid;
    }

    public Date getSdateCompleted() {
        return sdateCompleted;
    }

    public void setSdateCompleted(Date sdateCompleted) {
        this.sdateCompleted = sdateCompleted;
    }

    public String getScustomermark() {
        return scustomermark;
    }

    public void setScustomermark(String scustomermark) {
        this.scustomermark = scustomermark == null ? null : scustomermark.trim();
    }

    public String getSdeliveryid() {
        return sdeliveryid;
    }

    public void setSdeliveryid(String sdeliveryid) {
        this.sdeliveryid = sdeliveryid == null ? null : sdeliveryid.trim();
    }
}