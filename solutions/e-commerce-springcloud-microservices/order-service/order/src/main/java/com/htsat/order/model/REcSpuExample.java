package com.htsat.order.model;

import java.util.ArrayList;
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

        public Criteria andNspuidEqualTo(Integer value) {
            addCriterion("nSPUID =", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidNotEqualTo(Integer value) {
            addCriterion("nSPUID <>", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidGreaterThan(Integer value) {
            addCriterion("nSPUID >", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("nSPUID >=", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidLessThan(Integer value) {
            addCriterion("nSPUID <", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidLessThanOrEqualTo(Integer value) {
            addCriterion("nSPUID <=", value, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidIn(List<Integer> values) {
            addCriterion("nSPUID in", values, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidNotIn(List<Integer> values) {
            addCriterion("nSPUID not in", values, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidBetween(Integer value1, Integer value2) {
            addCriterion("nSPUID between", value1, value2, "nspuid");
            return (Criteria) this;
        }

        public Criteria andNspuidNotBetween(Integer value1, Integer value2) {
            addCriterion("nSPUID not between", value1, value2, "nspuid");
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

        public Criteria andSavailableOnIsNull() {
            addCriterion("sAvailable_on is null");
            return (Criteria) this;
        }

        public Criteria andSavailableOnIsNotNull() {
            addCriterion("sAvailable_on is not null");
            return (Criteria) this;
        }

        public Criteria andSavailableOnEqualTo(String value) {
            addCriterion("sAvailable_on =", value, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSavailableOnNotEqualTo(String value) {
            addCriterion("sAvailable_on <>", value, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSavailableOnGreaterThan(String value) {
            addCriterion("sAvailable_on >", value, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSavailableOnGreaterThanOrEqualTo(String value) {
            addCriterion("sAvailable_on >=", value, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSavailableOnLessThan(String value) {
            addCriterion("sAvailable_on <", value, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSavailableOnLessThanOrEqualTo(String value) {
            addCriterion("sAvailable_on <=", value, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSavailableOnLike(String value) {
            addCriterion("sAvailable_on like", value, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSavailableOnNotLike(String value) {
            addCriterion("sAvailable_on not like", value, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSavailableOnIn(List<String> values) {
            addCriterion("sAvailable_on in", values, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSavailableOnNotIn(List<String> values) {
            addCriterion("sAvailable_on not in", values, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSavailableOnBetween(String value1, String value2) {
            addCriterion("sAvailable_on between", value1, value2, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSavailableOnNotBetween(String value1, String value2) {
            addCriterion("sAvailable_on not between", value1, value2, "savailableOn");
            return (Criteria) this;
        }

        public Criteria andSlugIsNull() {
            addCriterion("slug is null");
            return (Criteria) this;
        }

        public Criteria andSlugIsNotNull() {
            addCriterion("slug is not null");
            return (Criteria) this;
        }

        public Criteria andSlugEqualTo(String value) {
            addCriterion("slug =", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugNotEqualTo(String value) {
            addCriterion("slug <>", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugGreaterThan(String value) {
            addCriterion("slug >", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugGreaterThanOrEqualTo(String value) {
            addCriterion("slug >=", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugLessThan(String value) {
            addCriterion("slug <", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugLessThanOrEqualTo(String value) {
            addCriterion("slug <=", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugLike(String value) {
            addCriterion("slug like", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugNotLike(String value) {
            addCriterion("slug not like", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugIn(List<String> values) {
            addCriterion("slug in", values, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugNotIn(List<String> values) {
            addCriterion("slug not in", values, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugBetween(String value1, String value2) {
            addCriterion("slug between", value1, value2, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugNotBetween(String value1, String value2) {
            addCriterion("slug not between", value1, value2, "slug");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionIsNull() {
            addCriterion("sMeta_description is null");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionIsNotNull() {
            addCriterion("sMeta_description is not null");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionEqualTo(String value) {
            addCriterion("sMeta_description =", value, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionNotEqualTo(String value) {
            addCriterion("sMeta_description <>", value, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionGreaterThan(String value) {
            addCriterion("sMeta_description >", value, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("sMeta_description >=", value, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionLessThan(String value) {
            addCriterion("sMeta_description <", value, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionLessThanOrEqualTo(String value) {
            addCriterion("sMeta_description <=", value, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionLike(String value) {
            addCriterion("sMeta_description like", value, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionNotLike(String value) {
            addCriterion("sMeta_description not like", value, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionIn(List<String> values) {
            addCriterion("sMeta_description in", values, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionNotIn(List<String> values) {
            addCriterion("sMeta_description not in", values, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionBetween(String value1, String value2) {
            addCriterion("sMeta_description between", value1, value2, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaDescriptionNotBetween(String value1, String value2) {
            addCriterion("sMeta_description not between", value1, value2, "smetaDescription");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsIsNull() {
            addCriterion("sMeta_keywords is null");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsIsNotNull() {
            addCriterion("sMeta_keywords is not null");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsEqualTo(String value) {
            addCriterion("sMeta_keywords =", value, "smetaKeywords");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsNotEqualTo(String value) {
            addCriterion("sMeta_keywords <>", value, "smetaKeywords");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsGreaterThan(String value) {
            addCriterion("sMeta_keywords >", value, "smetaKeywords");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("sMeta_keywords >=", value, "smetaKeywords");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsLessThan(String value) {
            addCriterion("sMeta_keywords <", value, "smetaKeywords");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsLessThanOrEqualTo(String value) {
            addCriterion("sMeta_keywords <=", value, "smetaKeywords");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsLike(String value) {
            addCriterion("sMeta_keywords like", value, "smetaKeywords");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsNotLike(String value) {
            addCriterion("sMeta_keywords not like", value, "smetaKeywords");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsIn(List<String> values) {
            addCriterion("sMeta_keywords in", values, "smetaKeywords");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsNotIn(List<String> values) {
            addCriterion("sMeta_keywords not in", values, "smetaKeywords");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsBetween(String value1, String value2) {
            addCriterion("sMeta_keywords between", value1, value2, "smetaKeywords");
            return (Criteria) this;
        }

        public Criteria andSmetaKeywordsNotBetween(String value1, String value2) {
            addCriterion("sMeta_keywords not between", value1, value2, "smetaKeywords");
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

        public Criteria andNcategoryidEqualTo(Integer value) {
            addCriterion("nCategoryID =", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidNotEqualTo(Integer value) {
            addCriterion("nCategoryID <>", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidGreaterThan(Integer value) {
            addCriterion("nCategoryID >", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidGreaterThanOrEqualTo(Integer value) {
            addCriterion("nCategoryID >=", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidLessThan(Integer value) {
            addCriterion("nCategoryID <", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidLessThanOrEqualTo(Integer value) {
            addCriterion("nCategoryID <=", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidIn(List<Integer> values) {
            addCriterion("nCategoryID in", values, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidNotIn(List<Integer> values) {
            addCriterion("nCategoryID not in", values, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidBetween(Integer value1, Integer value2) {
            addCriterion("nCategoryID between", value1, value2, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidNotBetween(Integer value1, Integer value2) {
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

        public Criteria andNbrandidEqualTo(Integer value) {
            addCriterion("nBrandID =", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidNotEqualTo(Integer value) {
            addCriterion("nBrandID <>", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidGreaterThan(Integer value) {
            addCriterion("nBrandID >", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidGreaterThanOrEqualTo(Integer value) {
            addCriterion("nBrandID >=", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidLessThan(Integer value) {
            addCriterion("nBrandID <", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidLessThanOrEqualTo(Integer value) {
            addCriterion("nBrandID <=", value, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidIn(List<Integer> values) {
            addCriterion("nBrandID in", values, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidNotIn(List<Integer> values) {
            addCriterion("nBrandID not in", values, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidBetween(Integer value1, Integer value2) {
            addCriterion("nBrandID between", value1, value2, "nbrandid");
            return (Criteria) this;
        }

        public Criteria andNbrandidNotBetween(Integer value1, Integer value2) {
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

        public Criteria andNimageidEqualTo(Integer value) {
            addCriterion("nImageID =", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidNotEqualTo(Integer value) {
            addCriterion("nImageID <>", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidGreaterThan(Integer value) {
            addCriterion("nImageID >", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidGreaterThanOrEqualTo(Integer value) {
            addCriterion("nImageID >=", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidLessThan(Integer value) {
            addCriterion("nImageID <", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidLessThanOrEqualTo(Integer value) {
            addCriterion("nImageID <=", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidIn(List<Integer> values) {
            addCriterion("nImageID in", values, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidNotIn(List<Integer> values) {
            addCriterion("nImageID not in", values, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidBetween(Integer value1, Integer value2) {
            addCriterion("nImageID between", value1, value2, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidNotBetween(Integer value1, Integer value2) {
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