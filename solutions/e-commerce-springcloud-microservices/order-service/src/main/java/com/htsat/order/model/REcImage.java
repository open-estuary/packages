package com.htsat.order.model;

import java.util.Date;

public class REcImage {
    private Integer nimageid;

    private Integer nposition;

    private String nattachmentContentType;

    private String sattachmentFileName;

    private String stype;

    private Date sattachmentUpdatedAt;

    private Integer nattachmentWidth;

    private Integer nattachmentHeight;

    private String salt;

    private String sviewableType;

    private String sminiUrl;

    private String ssmallUrl;

    private String slargeUrl;

    private String sproductUrl;

    public Integer getNimageid() {
        return nimageid;
    }

    public void setNimageid(Integer nimageid) {
        this.nimageid = nimageid;
    }

    public Integer getNposition() {
        return nposition;
    }

    public void setNposition(Integer nposition) {
        this.nposition = nposition;
    }

    public String getNattachmentContentType() {
        return nattachmentContentType;
    }

    public void setNattachmentContentType(String nattachmentContentType) {
        this.nattachmentContentType = nattachmentContentType == null ? null : nattachmentContentType.trim();
    }

    public String getSattachmentFileName() {
        return sattachmentFileName;
    }

    public void setSattachmentFileName(String sattachmentFileName) {
        this.sattachmentFileName = sattachmentFileName == null ? null : sattachmentFileName.trim();
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype == null ? null : stype.trim();
    }

    public Date getSattachmentUpdatedAt() {
        return sattachmentUpdatedAt;
    }

    public void setSattachmentUpdatedAt(Date sattachmentUpdatedAt) {
        this.sattachmentUpdatedAt = sattachmentUpdatedAt;
    }

    public Integer getNattachmentWidth() {
        return nattachmentWidth;
    }

    public void setNattachmentWidth(Integer nattachmentWidth) {
        this.nattachmentWidth = nattachmentWidth;
    }

    public Integer getNattachmentHeight() {
        return nattachmentHeight;
    }

    public void setNattachmentHeight(Integer nattachmentHeight) {
        this.nattachmentHeight = nattachmentHeight;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getSviewableType() {
        return sviewableType;
    }

    public void setSviewableType(String sviewableType) {
        this.sviewableType = sviewableType == null ? null : sviewableType.trim();
    }

    public String getSminiUrl() {
        return sminiUrl;
    }

    public void setSminiUrl(String sminiUrl) {
        this.sminiUrl = sminiUrl == null ? null : sminiUrl.trim();
    }

    public String getSsmallUrl() {
        return ssmallUrl;
    }

    public void setSsmallUrl(String ssmallUrl) {
        this.ssmallUrl = ssmallUrl == null ? null : ssmallUrl.trim();
    }

    public String getSlargeUrl() {
        return slargeUrl;
    }

    public void setSlargeUrl(String slargeUrl) {
        this.slargeUrl = slargeUrl == null ? null : slargeUrl.trim();
    }

    public String getSproductUrl() {
        return sproductUrl;
    }

    public void setSproductUrl(String sproductUrl) {
        this.sproductUrl = sproductUrl == null ? null : sproductUrl.trim();
    }
}