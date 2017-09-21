package com.htsat.order.model;

public class REcUserdeliveryaddress {
    private Integer naddressno;

    private Integer nuserid;

    private String sfirstname;

    private String slastname;

    private String saddress;

    private String scity;

    private String sprovince;

    private String scountry;

    private String semail;

    private String sphoneno;

    public Integer getNaddressno() {
        return naddressno;
    }

    public void setNaddressno(Integer naddressno) {
        this.naddressno = naddressno;
    }

    public Integer getNuserid() {
        return nuserid;
    }

    public void setNuserid(Integer nuserid) {
        this.nuserid = nuserid;
    }

    public String getSfirstname() {
        return sfirstname;
    }

    public void setSfirstname(String sfirstname) {
        this.sfirstname = sfirstname == null ? null : sfirstname.trim();
    }

    public String getSlastname() {
        return slastname;
    }

    public void setSlastname(String slastname) {
        this.slastname = slastname == null ? null : slastname.trim();
    }

    public String getSaddress() {
        return saddress;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress == null ? null : saddress.trim();
    }

    public String getScity() {
        return scity;
    }

    public void setScity(String scity) {
        this.scity = scity == null ? null : scity.trim();
    }

    public String getSprovince() {
        return sprovince;
    }

    public void setSprovince(String sprovince) {
        this.sprovince = sprovince == null ? null : sprovince.trim();
    }

    public String getScountry() {
        return scountry;
    }

    public void setScountry(String scountry) {
        this.scountry = scountry == null ? null : scountry.trim();
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail == null ? null : semail.trim();
    }

    public String getSphoneno() {
        return sphoneno;
    }

    public void setSphoneno(String sphoneno) {
        this.sphoneno = sphoneno == null ? null : sphoneno.trim();
    }
}