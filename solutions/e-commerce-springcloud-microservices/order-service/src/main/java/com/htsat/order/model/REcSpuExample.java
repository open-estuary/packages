package com.htsat.order.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class REcSpuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcSpuExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andNspuidIsNull() {
            addCriterion("nSPUID is null");
            return (Criteria) this;
        }

        public Criteria andNspuidIsNotNull() {
            addCriterion("nSPUID is not null");
            return (Criteria) this;
        }

        public Criteria andNspuidEqualTo(Long value) {
            addCriterion("nSPUID =", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidNotEqualTo(Long value) {
            addCriterion("nSPUID <>", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidGreaterThan(Long value) {
            addCriterion("nSPUID >", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidGreaterThanOrEqualTo(Long value) {
            addCriterion("nSPUID >=", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidLessThan(Long value) {
            addCriterion("nSPUID <", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidLessThanOrEqualTo(Long value) {
            addCriterion("nSPUID <=", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidIn(List<Long> values) {
            addCriterion("nSPUID in", values, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidNotIn(List<Long> values) {
            addCriterion("nSPUID not in", values, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidBetween(Long value1, Long value2) {
            addCriterion("nSPUID between", value1, value2, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidNotBetween(Long value1, Long value2) {
            addCriterion("nSPUID not between", value1, value2, "nspuid");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameIsNull() {
            addCriterion("sSPUBriefName is null");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameIsNotNull() {
            addCriterion("sSPUBriefName is not null");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameEqualTo(String value) {
            addCriterion("sSPUBriefName =", value, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameNotEqualTo(String value) {
            addCriterion("sSPUBriefName <>", value, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameGreaterThan(String value) {
            addCriterion("sSPUBriefName >", value, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameGreaterThanOrEqualTo(String value) {
            addCriterion("sSPUBriefName >=", value, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameLessThan(String value) {
            addCriterion("sSPUBriefName <", value, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameLessThanOrEqualTo(String value) {
            addCriterion("sSPUBriefName <=", value, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameLike(String value) {
            addCriterion("sSPUBriefName like", value, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameNotLike(String value) {
            addCriterion("sSPUBriefName not like", value, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameIn(List<String> values) {
            addCriterion("sSPUBriefName in", values, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameNotIn(List<String> values) {
            addCriterion("sSPUBriefName not in", values, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameBetween(String value1, String value2) {
            addCriterion("sSPUBriefName between", value1, value2, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspubriefnameNotBetween(String value1, String value2) {
            addCriterion("sSPUBriefName not between", value1, value2, "sspubriefname");
            return (Criteria) this;
        }

        public Criteria andSspunameIsNull() {
            addCriterion("sSPUName is null");
            return (Criteria) this;
        }

        public Criteria andSspunameIsNotNull() {
            addCriterion("sSPUName is not null");
            return (Criteria) this;
        }

        public Criteria andSspunameEqualTo(String value) {
            addCriterion("sSPUName =", value, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSspunameNotEqualTo(String value) {
            addCriterion("sSPUName <>", value, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSspunameGreaterThan(String value) {
            addCriterion("sSPUName >", value, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSspunameGreaterThanOrEqualTo(String value) {
            addCriterion("sSPUName >=", value, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSspunameLessThan(String value) {
            addCriterion("sSPUName <", value, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSspunameLessThanOrEqualTo(String value) {
            addCriterion("sSPUName <=", value, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSspunameLike(String value) {
            addCriterion("sSPUName like", value, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSspunameNotLike(String value) {
            addCriterion("sSPUName not like", value, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSspunameIn(List<String> values) {
            addCriterion("sSPUName in", values, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSspunameNotIn(List<String> values) {
            addCriterion("sSPUName not in", values, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSspunameBetween(String value1, String value2) {
            addCriterion("sSPUName between", value1, value2, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSspunameNotBetween(String value1, String value2) {
            addCriterion("sSPUName not between", value1, value2, "sspuname");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsIsNull() {
            addCriterion("sMetaKeywords is null");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsIsNotNull() {
            addCriterion("sMetaKeywords is not null");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsEqualTo(String value) {
            addCriterion("sMetaKeywords =", value, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsNotEqualTo(String value) {
            addCriterion("sMetaKeywords <>", value, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsGreaterThan(String value) {
            addCriterion("sMetaKeywords >", value, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("sMetaKeywords >=", value, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsLessThan(String value) {
            addCriterion("sMetaKeywords <", value, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsLessThanOrEqualTo(String value) {
            addCriterion("sMetaKeywords <=", value, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsLike(String value) {
            addCriterion("sMetaKeywords like", value, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsNotLike(String value) {
            addCriterion("sMetaKeywords not like", value, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsIn(List<String> values) {
            addCriterion("sMetaKeywords in", values, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsNotIn(List<String> values) {
            addCriterion("sMetaKeywords not in", values, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsBetween(String value1, String value2) {
            addCriterion("sMetaKeywords between", value1, value2, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andSmetakeywordsNotBetween(String value1, String value2) {
            addCriterion("sMetaKeywords not between", value1, value2, "smetakeywords");
            return (Criteria) this;
        }

        public Criteria andNcategoryidIsNull() {
            addCriterion("nCategoryID is null");
            return (Criteria) this;
        }

        public Criteria andNcategoryidIsNotNull() {
            addCriterion("nCategoryID is not null");
            return (Criteria) this;
        }

        public Criteria andNcategoryidEqualTo(Long value) {
            addCriterion("nCategoryID =", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidNotEqualTo(Long value) {
            addCriterion("nCategoryID <>", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidGreaterThan(Long value) {
            addCriterion("nCategoryID >", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidGreaterThanOrEqualTo(Long value) {
            addCriterion("nCategoryID >=", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidLessThan(Long value) {
            addCriterion("nCategoryID <", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidLessThanOrEqualTo(Long value) {
            addCriterion("nCategoryID <=", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidIn(List<Long> values) {
            addCriterion("nCategoryID in", values, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidNotIn(List<Long> values) {
            addCriterion("nCategoryID not in", values, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidBetween(Long value1, Long value2) {
            addCriterion("nCategoryID between", value1, value2, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidNotBetween(Long value1, Long value2) {
            addCriterion("nCategoryID not between", value1, value2, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNbrandidIsNull() {
            addCriterion("nBrandID is null");
            return (Criteria) this;
        }

        public Criteria andNbrandidIsNotNull() {
            addCriterion("nBrandID is not null");
            return (Criteria) this;
        }

        public Criteria andNbrandidEqualTo(Long value) {
            addCriterion("nBrandID =", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidNotEqualTo(Long value) {
            addCriterion("nBrandID <>", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidGreaterThan(Long value) {
            addCriterion("nBrandID >", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidGreaterThanOrEqualTo(Long value) {
            addCriterion("nBrandID >=", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidLessThan(Long value) {
            addCriterion("nBrandID <", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidLessThanOrEqualTo(Long value) {
            addCriterion("nBrandID <=", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidIn(List<Long> values) {
            addCriterion("nBrandID in", values, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidNotIn(List<Long> values) {
            addCriterion("nBrandID not in", values, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidBetween(Long value1, Long value2) {
            addCriterion("nBrandID between", value1, value2, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidNotBetween(Long value1, Long value2) {
            addCriterion("nBrandID not between", value1, value2, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNimageidIsNull() {
            addCriterion("nImageID is null");
            return (Criteria) this;
        }

        public Criteria andNimageidIsNotNull() {
            addCriterion("nImageID is not null");
            return (Criteria) this;
        }

        public Criteria andNimageidEqualTo(Long value) {
            addCriterion("nImageID =", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidNotEqualTo(Long value) {
            addCriterion("nImageID <>", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidGreaterThan(Long value) {
            addCriterion("nImageID >", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidGreaterThanOrEqualTo(Long value) {
            addCriterion("nImageID >=", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidLessThan(Long value) {
            addCriterion("nImageID <", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidLessThanOrEqualTo(Long value) {
            addCriterion("nImageID <=", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidIn(List<Long> values) {
            addCriterion("nImageID in", values, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidNotIn(List<Long> values) {
            addCriterion("nImageID not in", values, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidBetween(Long value1, Long value2) {
            addCriterion("nImageID between", value1, value2, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidNotBetween(Long value1, Long value2) {
            addCriterion("nImageID not between", value1, value2, "nimageid");
            return (Criteria) this;
        }

        public Criteria andSdescriptionIsNull() {
            addCriterion("sDescription is null");
            return (Criteria) this;
        }

        public Criteria andSdescriptionIsNotNull() {
            addCriterion("sDescription is not null");
            return (Criteria) this;
        }

        public Criteria andSdescriptionEqualTo(String value) {
            addCriterion("sDescription =", value, "sdescription");
            return (Criteria) this;
        }

        public Criteria andSdescriptionNotEqualTo(String value) {
            addCriterion("sDescription <>", value, "sdescription");
            return (Criteria) this;
        }

        public Criteria andSdescriptionGreaterThan(String value) {
            addCriterion("sDescription >", value, "sdescription");
            return (Criteria) this;
        }

        public Criteria andSdescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("sDescription >=", value, "sdescription");
            return (Criteria) this;
        }

        public Criteria andSdescriptionLessThan(String value) {
            addCriterion("sDescription <", value, "sdescription");
            return (Criteria) this;
        }

        public Criteria andSdescriptionLessThanOrEqualTo(String value) {
            addCriterion("sDescription <=", value, "sdescription");
            return (Criteria) this;
        }

        public Criteria andSdescriptionLike(String value) {
            addCriterion("sDescription like", value, "sdescription");
            return (Criteria) this;
        }

        public Criteria andSdescriptionNotLike(String value) {
            addCriterion("sDescription not like", value, "sdescription");
            return (Criteria) this;
        }

        public Criteria andSdescriptionIn(List<String> values) {
            addCriterion("sDescription in", values, "sdescription");
            return (Criteria) this;
        }

        public Criteria andSdescriptionNotIn(List<String> values) {
            addCriterion("sDescription not in", values, "sdescription");
            return (Criteria) this;
        }

        public Criteria andSdescriptionBetween(String value1, String value2) {
            addCriterion("sDescription between", value1, value2, "sdescription");
            return (Criteria) this;
        }

        public Criteria andSdescriptionNotBetween(String value1, String value2) {
            addCriterion("sDescription not between", value1, value2, "sdescription");
            return (Criteria) this;
        }

        public Criteria andScodeIsNull() {
            addCriterion("sCode is null");
            return (Criteria) this;
        }

        public Criteria andScodeIsNotNull() {
            addCriterion("sCode is not null");
            return (Criteria) this;
        }

        public Criteria andScodeEqualTo(String value) {
            addCriterion("sCode =", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeNotEqualTo(String value) {
            addCriterion("sCode <>", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeGreaterThan(String value) {
            addCriterion("sCode >", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeGreaterThanOrEqualTo(String value) {
            addCriterion("sCode >=", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeLessThan(String value) {
            addCriterion("sCode <", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeLessThanOrEqualTo(String value) {
            addCriterion("sCode <=", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeLike(String value) {
            addCriterion("sCode like", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeNotLike(String value) {
            addCriterion("sCode not like", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeIn(List<String> values) {
            addCriterion("sCode in", values, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeNotIn(List<String> values) {
            addCriterion("sCode not in", values, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeBetween(String value1, String value2) {
            addCriterion("sCode between", value1, value2, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeNotBetween(String value1, String value2) {
            addCriterion("sCode not between", value1, value2, "scode");
            return (Criteria) this;
        }

        public Criteria andSisvirtualIsNull() {
            addCriterion("sIsVirtual is null");
            return (Criteria) this;
        }

        public Criteria andSisvirtualIsNotNull() {
            addCriterion("sIsVirtual is not null");
            return (Criteria) this;
        }

        public Criteria andSisvirtualEqualTo(String value) {
            addCriterion("sIsVirtual =", value, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andSisvirtualNotEqualTo(String value) {
            addCriterion("sIsVirtual <>", value, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andSisvirtualGreaterThan(String value) {
            addCriterion("sIsVirtual >", value, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andSisvirtualGreaterThanOrEqualTo(String value) {
            addCriterion("sIsVirtual >=", value, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andSisvirtualLessThan(String value) {
            addCriterion("sIsVirtual <", value, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andSisvirtualLessThanOrEqualTo(String value) {
            addCriterion("sIsVirtual <=", value, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andSisvirtualLike(String value) {
            addCriterion("sIsVirtual like", value, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andSisvirtualNotLike(String value) {
            addCriterion("sIsVirtual not like", value, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andSisvirtualIn(List<String> values) {
            addCriterion("sIsVirtual in", values, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andSisvirtualNotIn(List<String> values) {
            addCriterion("sIsVirtual not in", values, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andSisvirtualBetween(String value1, String value2) {
            addCriterion("sIsVirtual between", value1, value2, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andSisvirtualNotBetween(String value1, String value2) {
            addCriterion("sIsVirtual not between", value1, value2, "sisvirtual");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeIsNull() {
            addCriterion("dCreateTime is null");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeIsNotNull() {
            addCriterion("dCreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeEqualTo(Date value) {
            addCriterion("dCreateTime =", value, "dcreatetime");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeNotEqualTo(Date value) {
            addCriterion("dCreateTime <>", value, "dcreatetime");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeGreaterThan(Date value) {
            addCriterion("dCreateTime >", value, "dcreatetime");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("dCreateTime >=", value, "dcreatetime");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeLessThan(Date value) {
            addCriterion("dCreateTime <", value, "dcreatetime");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("dCreateTime <=", value, "dcreatetime");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeIn(List<Date> values) {
            addCriterion("dCreateTime in", values, "dcreatetime");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeNotIn(List<Date> values) {
            addCriterion("dCreateTime not in", values, "dcreatetime");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeBetween(Date value1, Date value2) {
            addCriterion("dCreateTime between", value1, value2, "dcreatetime");
            return (Criteria) this;
        }

        public Criteria andDcreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("dCreateTime not between", value1, value2, "dcreatetime");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeIsNull() {
            addCriterion("dUpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeIsNotNull() {
            addCriterion("dUpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeEqualTo(Date value) {
            addCriterion("dUpdateTime =", value, "dupdatetime");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeNotEqualTo(Date value) {
            addCriterion("dUpdateTime <>", value, "dupdatetime");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeGreaterThan(Date value) {
            addCriterion("dUpdateTime >", value, "dupdatetime");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("dUpdateTime >=", value, "dupdatetime");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeLessThan(Date value) {
            addCriterion("dUpdateTime <", value, "dupdatetime");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("dUpdateTime <=", value, "dupdatetime");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeIn(List<Date> values) {
            addCriterion("dUpdateTime in", values, "dupdatetime");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeNotIn(List<Date> values) {
            addCriterion("dUpdateTime not in", values, "dupdatetime");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeBetween(Date value1, Date value2) {
            addCriterion("dUpdateTime between", value1, value2, "dupdatetime");
            return (Criteria) this;
        }

        public Criteria andDupdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("dUpdateTime not between", value1, value2, "dupdatetime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}