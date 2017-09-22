package com.htsat.order.model;

public class REcOrderskuKey {
    private String sorderid;

    private Integer nskuid;

    public String getSorderid() {
        return sorderid;
    }

    public void setSorderid(String sorderid) {
        this.sorderid = sorderid == null ? null : sorderid.trim();
    }

    public Integer getNskuid() {
        return nskuid;
    }

    public void setNskuid(Integer nskuid) {
        this.nskuid = nskuid;
    }
}