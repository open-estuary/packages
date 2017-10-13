package com.htsat.cart.model;

import java.util.Date;

public class REcCategory {
    private Integer ncategoryid;

    private Integer nlevel;

    private Integer nparentcategoryid;

    private String scategoryname;

    private String scode;

    private String isleaf;

    private Date dcreatetime;

    private Date dupdatetime;

    public Integer getNcategoryid() {
        return ncategoryid;
    }

    public void setNcategoryid(Integer ncategoryid) {
        this.ncategoryid = ncategoryid;
    }

    public Integer getNlevel() {
        return nlevel;
    }

    public void setNlevel(Integer nlevel) {
        this.nlevel = nlevel;
    }

    public Integer getNparentcategoryid() {
        return nparentcategoryid;
    }

    public void setNparentcategoryid(Integer nparentcategoryid) {
        this.nparentcategoryid = nparentcategoryid;
    }

    public String getScategoryname() {
        return scategoryname;
    }

    public void setScategoryname(String scategoryname) {
        this.scategoryname = scategoryname == null ? null : scategoryname.trim();
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode == null ? null : scode.trim();
    }

    public String getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf == null ? null : isleaf.trim();
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