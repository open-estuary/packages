package com.htsat.order.model;

import java.util.Date;

public class REcBrand {
    private Integer nbrandid;

    private String sbrandname;

    private String scode;

    private String sbriefname;

    private String nstatus;

    private String scomment;

    private Byte nordernum;

    private Date dcreatedate;

    private Date dupdatedate;

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

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode == null ? null : scode.trim();
    }

    public String getSbriefname() {
        return sbriefname;
    }

    public void setSbriefname(String sbriefname) {
        this.sbriefname = sbriefname == null ? null : sbriefname.trim();
    }

    public String getNstatus() {
        return nstatus;
    }

    public void setNstatus(String nstatus) {
        this.nstatus = nstatus == null ? null : nstatus.trim();
    }

    public String getScomment() {
        return scomment;
    }

    public void setScomment(String scomment) {
        this.scomment = scomment == null ? null : scomment.trim();
    }

    public Byte getNordernum() {
        return nordernum;
    }

    public void setNordernum(Byte nordernum) {
        this.nordernum = nordernum;
    }

    public Date getDcreatedate() {
        return dcreatedate;
    }

    public void setDcreatedate(Date dcreatedate) {
        this.dcreatedate = dcreatedate;
    }

    public Date getDupdatedate() {
        return dupdatedate;
    }

    public void setDupdatedate(Date dupdatedate) {
        this.dupdatedate = dupdatedate;
    }
}