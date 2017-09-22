package com.htsat.order.model;

public class REcUserbankinfo {
    private String scardnumber;

    private Integer nuserid;

    private String sbank;

    private String scardtype;

    private String scurrency;

    public String getScardnumber() {
        return scardnumber;
    }

    public void setScardnumber(String scardnumber) {
        this.scardnumber = scardnumber == null ? null : scardnumber.trim();
    }

    public Integer getNuserid() {
        return nuserid;
    }

    public void setNuserid(Integer nuserid) {
        this.nuserid = nuserid;
    }

    public String getSbank() {
        return sbank;
    }

    public void setSbank(String sbank) {
        this.sbank = sbank == null ? null : sbank.trim();
    }

    public String getScardtype() {
        return scardtype;
    }

    public void setScardtype(String scardtype) {
        this.scardtype = scardtype == null ? null : scardtype.trim();
    }

    public String getScurrency() {
        return scurrency;
    }

    public void setScurrency(String scurrency) {
        this.scurrency = scurrency == null ? null : scurrency.trim();
    }
}