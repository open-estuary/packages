package com.htsat.order.model;

import java.math.BigDecimal;

public class REcOrdersku extends REcOrderskuKey {
    private Integer nquantity;

    private BigDecimal norigprice;

    private Float ndiscount;

    private String scurrency;

    private BigDecimal nprice;

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

    public Float getNdiscount() {
        return ndiscount;
    }

    public void setNdiscount(Float ndiscount) {
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