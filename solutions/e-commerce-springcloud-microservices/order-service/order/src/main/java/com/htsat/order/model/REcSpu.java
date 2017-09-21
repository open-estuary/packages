package com.htsat.order.model;

public class REcSpu {
    private Integer nspuid;

    private String sspuname;

    private String savailableOn;

    private String slug;

    private String smetaDescription;

    private String smetaKeywords;

    private Integer ncategoryid;

    private Integer nbrandid;

    private Integer nimageid;

    private String sdescription;

    public Integer getNspuid() {
        return nspuid;
    }

    public void setNspuid(Integer nspuid) {
        this.nspuid = nspuid;
    }

    public String getSspuname() {
        return sspuname;
    }

    public void setSspuname(String sspuname) {
        this.sspuname = sspuname == null ? null : sspuname.trim();
    }

    public String getSavailableOn() {
        return savailableOn;
    }

    public void setSavailableOn(String savailableOn) {
        this.savailableOn = savailableOn == null ? null : savailableOn.trim();
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug == null ? null : slug.trim();
    }

    public String getSmetaDescription() {
        return smetaDescription;
    }

    public void setSmetaDescription(String smetaDescription) {
        this.smetaDescription = smetaDescription == null ? null : smetaDescription.trim();
    }

    public String getSmetaKeywords() {
        return smetaKeywords;
    }

    public void setSmetaKeywords(String smetaKeywords) {
        this.smetaKeywords = smetaKeywords == null ? null : smetaKeywords.trim();
    }

    public Integer getNcategoryid() {
        return ncategoryid;
    }

    public void setNcategoryid(Integer ncategoryid) {
        this.ncategoryid = ncategoryid;
    }

    public Integer getNbrandid() {
        return nbrandid;
    }

    public void setNbrandid(Integer nbrandid) {
        this.nbrandid = nbrandid;
    }

    public Integer getNimageid() {
        return nimageid;
    }

    public void setNimageid(Integer nimageid) {
        this.nimageid = nimageid;
    }

    public String getSdescription() {
        return sdescription;
    }

    public void setSdescription(String sdescription) {
        this.sdescription = sdescription == null ? null : sdescription.trim();
    }
}