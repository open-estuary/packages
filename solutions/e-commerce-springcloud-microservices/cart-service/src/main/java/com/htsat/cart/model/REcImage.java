package com.htsat.cart.model;

import java.util.Date;

public class REcImage {
    private Long nimageid;

    private String nimagetype;

    private String simagename;

    private Date dupdatetime;

    private String sdescription;

    private String spath;

    private Date dcreatetime;

    public Long getNimageid() {
        return nimageid;
    }

    public void setNimageid(Long nimageid) {
        this.nimageid = nimageid;
    }

    public String getNimagetype() {
        return nimagetype;
    }

    public void setNimagetype(String nimagetype) {
        this.nimagetype = nimagetype == null ? null : nimagetype.trim();
    }

    public String getSimagename() {
        return simagename;
    }

    public void setSimagename(String simagename) {
        this.simagename = simagename == null ? null : simagename.trim();
    }

    public Date getDupdatetime() {
        return dupdatetime;
    }

    public void setDupdatetime(Date dupdatetime) {
        this.dupdatetime = dupdatetime;
    }

    public String getSdescription() {
        return sdescription;
    }

    public void setSdescription(String sdescription) {
        this.sdescription = sdescription == null ? null : sdescription.trim();
    }

    public String getSpath() {
        return spath;
    }

    public void setSpath(String spath) {
        this.spath = spath == null ? null : spath.trim();
    }

    public Date getDcreatetime() {
        return dcreatetime;
    }

    public void setDcreatetime(Date dcreatetime) {
        this.dcreatetime = dcreatetime;
    }
}