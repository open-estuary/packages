package com.htsat.order.model;

import java.math.BigDecimal;

public class REcOrdersku {
    private Long norderskuid;

    private Long norderid;

    private Long nskuid;

    private Integer nquantity;

    private BigDecimal norigprice;

    private BigDecimal ndiscount;

    private String scurrency;

    private BigDecimal nprice;

    public Long getNorderskuid() {
        return norderskuid;
    }

    public void setNorderskuid(Long norderskuid) {
        this.norderskuid = norderskuid;
    }

    public Long getNorderid() {
        return norderid;
    }

    public void setNorderid(Long norderid) {
        this.norderid = norderid;
    }

    public Long getNskuid() {
        return nskuid;
    }

    public void setNskuid(Long nskuid) {
        this.nskuid = nskuid;
    }

    public Integer getNquantity() {
        return nquantity;
    }

    public void setNquantity(Integer nquantity) {
        this.nquantity = nquantity;
    }

    public BigDecimal getNorigprice() {
        return norigprice;
    }

    public void setNorigprice(BigDecimal norigprice) {
        this.norigprice = norigprice;
    }

    public BigDecimal getNdiscount() {
        return ndiscount;
    }

    public void setNdiscount(BigDecimal ndiscount) {
        this.ndiscount = ndiscount;
    }

    public String getScurrency() {
        return scurrency;
    }

    public void setScurrency(String scurrency) {
        this.scurrency = scurrency == null ? null : scurrency.trim();
    }

    public BigDecimal getNprice() {
        return nprice;
    }

    public void setNprice(BigDecimal nprice) {
        this.nprice = nprice;
    }
}