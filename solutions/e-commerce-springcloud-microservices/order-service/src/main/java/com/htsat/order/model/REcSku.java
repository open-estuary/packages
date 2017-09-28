package com.htsat.order.model;

import java.math.BigDecimal;
import java.util.Date;

public class REcSku {
    private Long nskuid;

    private Long nspuid;

    private String ncolor;

    private String ssize;

    private BigDecimal nprice;

    private BigDecimal ndisplayprice;

    private Integer ninventory;

    private Long ndiscount;

    private String scurrency;

    private Date dcreatetime;

    private Date dupdatetime;

    public Long getNskuid() {
        return nskuid;
    }

    public void setNskuid(Long nskuid) {
        this.nskuid = nskuid;
    }

    public Long getNspuid() {
        return nspuid;
    }

    public void setNspuid(Long nspuid) {
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

    public BigDecimal getNdisplayprice() {
        return ndisplayprice;
    }

    public void setNdisplayprice(BigDecimal ndisplayprice) {
        this.ndisplayprice = ndisplayprice;
    }

    public Integer getNinventory() {
        return ninventory;
    }

    public void setNinventory(Integer ninventory) {
        this.ninventory = ninventory;
    }

    public Long getNdiscount() {
        return ndiscount;
    }

    public void setNdiscount(Long ndiscount) {
        this.ndiscount = ndiscount;
    }

    public String getScurrency() {
        return scurrency;
    }

    public void setScurrency(String scurrency) {
        this.scurrency = scurrency == null ? null : scurrency.trim();
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
}