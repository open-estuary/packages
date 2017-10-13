package com.htsat.order.model;

public class REcUserbankinfo {
    private String scardid;

    private Long nuserid;

    private Short sbank;

    private Short scardtype;

    private String scurrency;

    private String scardnumber;

    public String getScardid() {
        return scardid;
    }

    public void setScardid(String scardid) {
        this.scardid = scardid == null ? null : scardid.trim();
    }

    public Long getNuserid() {
        return nuserid;
    }

    public void setNuserid(Long nuserid) {
        this.nuserid = nuserid;
    }

    public Short getSbank() {
        return sbank;
    }

    public void setSbank(Short sbank) {
        this.sbank = sbank;
    }

    public Short getScardtype() {
        return scardtype;
    }

    public void setScardtype(Short scardtype) {
        this.scardtype = scardtype;
    }

    public String getScurrency() {
        return scurrency;
    }

    public void setScurrency(String scurrency) {
        this.scurrency = scurrency == null ? null : scurrency.trim();
    }

    public String getScardnumber() {
        return scardnumber;
    }

    public void setScardnumber(String scardnumber) {
        this.scardnumber = scardnumber == null ? null : scardnumber.trim();
    }
}