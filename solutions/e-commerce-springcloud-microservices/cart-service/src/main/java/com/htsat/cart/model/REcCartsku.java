package com.htsat.cart.model;

public class REcCartsku {
    private Long ncartskuid;

    private Long nuserid;

    private Long nskuid;

    private Integer nquantity;

    private Long nshoppingcartid;

    public Long getNcartskuid() {
        return ncartskuid;
    }

    public void setNcartskuid(Long ncartskuid) {
        this.ncartskuid = ncartskuid;
    }

    public Long getNuserid() {
        return nuserid;
    }

    public void setNuserid(Long nuserid) {
        this.nuserid = nuserid;
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

    public Long getNshoppingcartid() {
        return nshoppingcartid;
    }

    public void setNshoppingcartid(Long nshoppingcartid) {
        this.nshoppingcartid = nshoppingcartid;
    }
}