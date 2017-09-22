package com.htsat.order.model;

public class REcBrand {
    private Integer nbrandid;

    private String sbrandname;

    public Integer getNbrandid() {
        return nbrandid;
    }

    public void setNbrandid(Integer nbrandid) {
        this.nbrandid = nbrandid;
    }

    public String getSbrandname() {
        return sbrandname;
    }

    public void setSbrandname(String sbrandname) {
        this.sbrandname = sbrandname == null ? null : sbrandname.trim();
    }
}