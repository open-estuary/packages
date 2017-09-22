package com.htsat.order.model;

public class REcCategory {
    private Integer ncategoryid;

    private Integer nlevel;

    private Integer nparentcategoryid;

    private String scategoryname;

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
}