package com.htsat.order.dto;

import java.io.Serializable;
import java.util.Date;

public class AddressDTO implements Serializable{
    private Long naddressid;

    private Long nuserid;

    private String sfirstname;

    private String slastname;

    private String saddress;

    private String scity;

    private String sprovince;

    private String scountry;

    private String semail;

    private String sphoneno;

    private String sdistrict;

    private Date dcreatetime;

    private Date dupdatetime;

    private String szipcode;

    public Long getNaddressid() {
        return naddressid;
    }

    public void setNaddressid(Long naddressid) {
        this.naddressid = naddressid;
    }

    public Long getNuserid() {
        return nuserid;
    }

    public void setNuserid(Long nuserid) {
        this.nuserid = nuserid;
    }

    public String getSfirstname() {
        return sfirstname;
    }

    public void setSfirstname(String sfirstname) {
        this.sfirstname = sfirstname;
    }

    public String getSlastname() {
        return slastname;
    }

    public void setSlastname(String slastname) {
        this.slastname = slastname;
    }

    public String getSaddress() {
        return saddress;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress;
    }

    public String getScity() {
        return scity;
    }

    public void setScity(String scity) {
        this.scity = scity;
    }

    public String getSprovince() {
        return sprovince;
    }

    public void setSprovince(String sprovince) {
        this.sprovince = sprovince;
    }

    public String getScountry() {
        return scountry;
    }

    public void setScountry(String scountry) {
        this.scountry = scountry;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public String getSphoneno() {
        return sphoneno;
    }

    public void setSphoneno(String sphoneno) {
        this.sphoneno = sphoneno;
    }

    public String getSdistrict() {
        return sdistrict;
    }

    public void setSdistrict(String sdistrict) {
        this.sdistrict = sdistrict;
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

    public String getSzipcode() {
        return szipcode;
    }

    public void setSzipcode(String szipcode) {
        this.szipcode = szipcode;
    }
}
