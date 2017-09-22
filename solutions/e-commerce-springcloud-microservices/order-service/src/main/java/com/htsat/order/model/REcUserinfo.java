package com.htsat.order.model;

import java.util.Date;

public class REcUserinfo {
    private Integer nuserid;

    private String sloginname;

    private String sloginpassword;

    private String sfirstname;

    private String slastname;

    private String sphoneno;

    private String semailaddress;

    private String cgender;

    private Date sbirthday;

    private Date sregistertime;

    private String spaypassword;

    public Integer getNuserid() {
        return nuserid;
    }

    public void setNuserid(Integer nuserid) {
        this.nuserid = nuserid;
    }

    public String getSloginname() {
        return sloginname;
    }

    public void setSloginname(String sloginname) {
        this.sloginname = sloginname == null ? null : sloginname.trim();
    }

    public String getSloginpassword() {
        return sloginpassword;
    }

    public void setSloginpassword(String sloginpassword) {
        this.sloginpassword = sloginpassword == null ? null : sloginpassword.trim();
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

    public String getSphoneno() {
        return sphoneno;
    }

    public void setSphoneno(String sphoneno) {
        this.sphoneno = sphoneno == null ? null : sphoneno.trim();
    }

    public String getSemailaddress() {
        return semailaddress;
    }

    public void setSemailaddress(String semailaddress) {
        this.semailaddress = semailaddress == null ? null : semailaddress.trim();
    }

    public String getCgender() {
        return cgender;
    }

    public void setCgender(String cgender) {
        this.cgender = cgender == null ? null : cgender.trim();
    }

    public Date getSbirthday() {
        return sbirthday;
    }

    public void setSbirthday(Date sbirthday) {
        this.sbirthday = sbirthday;
    }

    public Date getSregistertime() {
        return sregistertime;
    }

    public void setSregistertime(Date sregistertime) {
        this.sregistertime = sregistertime;
    }

    public String getSpaypassword() {
        return spaypassword;
    }

    public void setSpaypassword(String spaypassword) {
        this.spaypassword = spaypassword == null ? null : spaypassword.trim();
    }
}