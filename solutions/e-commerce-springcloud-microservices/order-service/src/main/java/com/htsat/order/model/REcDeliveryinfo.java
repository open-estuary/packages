package com.htsat.order.model;

import java.math.BigDecimal;
import java.util.Date;

public class REcDeliveryinfo {
    private Long ndeliveryid;

    private String sexpresscompany;

    private BigDecimal ndeliveryprice;

    private Short cstatus;

    private Date dcreatetime;

    private Date dupdatetime;

    private Date douttime;

    private Long naddressid;

    private String sconsignee;

    private String sdeliverycomment;

    private String sdeliverycode;

    public Long getNdeliveryid() {
        return ndeliveryid;
    }

    public void setNdeliveryid(Long ndeliveryid) {
        this.ndeliveryid = ndeliveryid;
    }

    public String getSexpresscompany() {
        return sexpresscompany;
    }

    public void setSexpresscompany(String sexpresscompany) {
        this.sexpresscompany = sexpresscompany == null ? null : sexpresscompany.trim();
    }

    public BigDecimal getNdeliveryprice() {
        return ndeliveryprice;
    }

    public void setNdeliveryprice(BigDecimal ndeliveryprice) {
        this.ndeliveryprice = ndeliveryprice;
    }

    public Short getCstatus() {
        return cstatus;
    }

    public void setCstatus(Short cstatus) {
        this.cstatus = cstatus;
    }

    public Date getDcreatetime() {
        return dcreatetime;
    }

    public void setDcreatetime(Date dcreatetime) {
        this.dcreatetime = dcreatetime;
    }

    public Date getDupdatetime() {
        return dupdatetime;
    }

    public void setDupdatetime(Date dupdatetime) {
        this.dupdatetime = dupdatetime;
    }

    public Date getDouttime() {
        return douttime;
    }

    public void setDouttime(Date douttime) {
        this.douttime = douttime;
    }

    public Long getNaddressid() {
        return naddressid;
    }

    public void setNaddressid(Long naddressid) {
        this.naddressid = naddressid;
    }

    public String getSconsignee() {
        return sconsignee;
    }

    public void setSconsignee(String sconsignee) {
        this.sconsignee = sconsignee == null ? null : sconsignee.trim();
    }

    public String getSdeliverycomment() {
        return sdeliverycomment;
    }

    public void setSdeliverycomment(String sdeliverycomment) {
        this.sdeliverycomment = sdeliverycomment == null ? null : sdeliverycomment.trim();
    }

    public String getSdeliverycode() {
        return sdeliverycode;
    }

    public void setSdeliverycode(String sdeliverycode) {
        this.sdeliverycode = sdeliverycode == null ? null : sdeliverycode.trim();
    }
}