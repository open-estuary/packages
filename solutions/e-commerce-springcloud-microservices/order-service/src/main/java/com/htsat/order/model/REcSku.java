package com.htsat.order.model;

import java.math.BigDecimal;

public class REcSku {
    private Integer nskuid;

    private Integer nspuid;

    private String ncolor;

    private String ssize;

    private BigDecimal nprice;

    private BigDecimal ndisplayPrice;

    private Integer ninventory;

    private Float ndiscount;

    private String scurrency;

    public Integer getNskuid() {
        return nskuid;
    }

    public void setNskuid(Integer nskuid) {
        this.nskuid = nskuid;
    }

    public Integer getNspuid() {
        return nspuid;
    }

    public void setNspuid(Integer nspuid) {
        this.nspuid = nspuid;
    }

    public String getNcolor() {
        return ncolor;
    }

    public void setNcolor(String ncolor) {
        this.ncolor = ncolor == null ? null : ncolor.trim();
    }

    public String getSsize() {
        return ssize;
    }

    public void setSsize(String ssize) {
        this.ssize = ssize == null ? null : ssize.trim();
    }

    public BigDecimal getNprice() {
        return nprice;
    }

    public void setNprice(BigDecimal nprice) {
        this.nprice = nprice;
    }

    public BigDecimal getNdisplayPrice() {
        return ndisplayPrice;
    }

    public void setNdisplayPrice(BigDecimal ndisplayPrice) {
        this.ndisplayPrice = ndisplayPrice;
    }

    public Integer getNinventory() {
        return ninventory;
    }

    public void setNinventory(Integer ninventory) {
        this.ninventory = ninventory;
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
}