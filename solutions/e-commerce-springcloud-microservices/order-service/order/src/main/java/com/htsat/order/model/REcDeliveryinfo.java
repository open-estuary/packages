package com.htsat.order.model;

import java.math.BigDecimal;

public class REcDeliveryinfo {
    private String sdeliveryid;

    private String sexpresscompany;

    private BigDecimal ndeliveryprice;

    private String cstatus;

    public String getSdeliveryid() {
        return sdeliveryid;
    }

    public void setSdeliveryid(String sdeliveryid) {
        this.sdeliveryid = sdeliveryid == null ? null : sdeliveryid.trim();
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

    public String getCstatus() {
        return cstatus;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus == null ? null : cstatus.trim();
    }
}