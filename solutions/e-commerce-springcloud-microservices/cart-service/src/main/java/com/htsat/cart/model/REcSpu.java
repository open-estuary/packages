package com.htsat.cart.model;

import java.util.Date;

public class REcSpu {
    private Long nspuid;

    private String sspubriefname;

    private String sspuname;

    private String smetakeywords;

    private Long ncategoryid;

    private Long nbrandid;

    private Long nimageid;

    private String sdescription;

    private String scode;

    private String sisvirtual;

    private Date dcreatetime;

    private Date dupdatetime;

    public Long getNspuid() {
        return nspuid;
    }

    public void setNspuid(Long nspuid) {
        this.nspuid = nspuid;
    }

    public String getSspubriefname() {
        return sspubriefname;
    }

    public void setSspubriefname(String sspubriefname) {
        this.sspubriefname = sspubriefname == null ? null : sspubriefname.trim();
    }

    public String getSspuname() {
        return sspuname;
    }

    public void setSspuname(String sspuname) {
        this.sspuname = sspuname == null ? null : sspuname.trim();
    }

    public String getSmetakeywords() {
        return smetakeywords;
    }

    public void setSmetakeywords(String smetakeywords) {
        this.smetakeywords = smetakeywords == null ? null : smetakeywords.trim();
    }

    public Long getNcategoryid() {
        return ncategoryid;
    }

    public void setNcategoryid(Long ncategoryid) {
        this.ncategoryid = ncategoryid;
    }

    public Long getNbrandid() {
        return nbrandid;
    }

    public void setNbrandid(Long nbrandid) {
        this.nbrandid = nbrandid;
    }

    public Long getNimageid() {
        return nimageid;
    }

    public void setNimageid(Long nimageid) {
        this.nimageid = nimageid;
    }

    public String getSdescription() {
        return sdescription;
    }

    public void setSdescription(String sdescription) {
        this.sdescription = sdescription == null ? null : sdescription.trim();
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode == null ? null : scode.trim();
    }

    public String getSisvirtual() {
        return sisvirtual;
    }

    public void setSisvirtual(String sisvirtual) {
        this.sisvirtual = sisvirtual == null ? null : sisvirtual.trim();
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