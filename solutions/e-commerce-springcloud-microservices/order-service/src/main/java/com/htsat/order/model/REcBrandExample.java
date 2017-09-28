package com.htsat.order.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class REcBrandExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcBrandExample() {
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

        public Criteria andSbrandnameIsNull() {
            addCriterion("sBrandName is null");
            return (Criteria) this;
        }

        public Criteria andSbrandnameIsNotNull() {
            addCriterion("sBrandName is not null");
            return (Criteria) this;
        }

        public Criteria andSbrandnameEqualTo(String value) {
            addCriterion("sBrandName =", value, "sbrandname");
            return (Criteria) this;
        }

        public Criteria andSbrandnameNotEqualTo(String value) {
            addCriterion("sBrandName <>", value, "sbrandname");
            return (Criteria) this;
        }

        public Criteria andSbrandnameGreaterThan(String value) {
            addCriterion("sBrandName >", value, "sbrandname");
            return (Criteria) this;
        }

        public Criteria andSbrandnameGreaterThanOrEqualTo(String value) {
            addCriterion("sBrandName >=", value, "sbrandname");
            return (Criteria) this;
        }

        public Criteria andSbrandnameLessThan(String value) {
            addCriterion("sBrandName <", value, "sbrandname");
            return (Criteria) this;
        }

        public Criteria andSbrandnameLessThanOrEqualTo(String value) {
            addCriterion("sBrandName <=", value, "sbrandname");
            return (Criteria) this;
        }

        public Criteria andSbrandnameLike(String value) {
            addCriterion("sBrandName like", value, "sbrandname");
            return (Criteria) this;
        }

        public Criteria andSbrandnameNotLike(String value) {
            addCriterion("sBrandName not like", value, "sbrandname");
            return (Criteria) this;
        }

        public Criteria andSbrandnameIn(List<String> values) {
            addCriterion("sBrandName in", values, "sbrandname");
            return (Criteria) this;
        }

        public Criteria andSbrandnameNotIn(List<String> values) {
            addCriterion("sBrandName not in", values, "sbrandname");
            return (Criteria) this;
        }

        public Criteria andSbrandnameBetween(String value1, String value2) {
            addCriterion("sBrandName between", value1, value2, "sbrandname");
            return (Criteria) this;
        }

        public Criteria andSbrandnameNotBetween(String value1, String value2) {
            addCriterion("sBrandName not between", value1, value2, "sbrandname");
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

        public Criteria andSbriefnameIsNull() {
            addCriterion("sBriefName is null");
            return (Criteria) this;
        }

        public Criteria andSbriefnameIsNotNull() {
            addCriterion("sBriefName is not null");
            return (Criteria) this;
        }

        public Criteria andSbriefnameEqualTo(String value) {
            addCriterion("sBriefName =", value, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andSbriefnameNotEqualTo(String value) {
            addCriterion("sBriefName <>", value, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andSbriefnameGreaterThan(String value) {
            addCriterion("sBriefName >", value, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andSbriefnameGreaterThanOrEqualTo(String value) {
            addCriterion("sBriefName >=", value, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andSbriefnameLessThan(String value) {
            addCriterion("sBriefName <", value, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andSbriefnameLessThanOrEqualTo(String value) {
            addCriterion("sBriefName <=", value, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andSbriefnameLike(String value) {
            addCriterion("sBriefName like", value, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andSbriefnameNotLike(String value) {
            addCriterion("sBriefName not like", value, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andSbriefnameIn(List<String> values) {
            addCriterion("sBriefName in", values, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andSbriefnameNotIn(List<String> values) {
            addCriterion("sBriefName not in", values, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andSbriefnameBetween(String value1, String value2) {
            addCriterion("sBriefName between", value1, value2, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andSbriefnameNotBetween(String value1, String value2) {
            addCriterion("sBriefName not between", value1, value2, "sbriefname");
            return (Criteria) this;
        }

        public Criteria andNstatusIsNull() {
            addCriterion("nStatus is null");
            return (Criteria) this;
        }

        public Criteria andNstatusIsNotNull() {
            addCriterion("nStatus is not null");
            return (Criteria) this;
        }

        public Criteria andNstatusEqualTo(String value) {
            addCriterion("nStatus =", value, "nstatus");
            return (Criteria) this;
        }

        public Criteria andNstatusNotEqualTo(String value) {
            addCriterion("nStatus <>", value, "nstatus");
            return (Criteria) this;
        }

        public Criteria andNstatusGreaterThan(String value) {
            addCriterion("nStatus >", value, "nstatus");
            return (Criteria) this;
        }

        public Criteria andNstatusGreaterThanOrEqualTo(String value) {
            addCriterion("nStatus >=", value, "nstatus");
            return (Criteria) this;
        }

        public Criteria andNstatusLessThan(String value) {
            addCriterion("nStatus <", value, "nstatus");
            return (Criteria) this;
        }

        public Criteria andNstatusLessThanOrEqualTo(String value) {
            addCriterion("nStatus <=", value, "nstatus");
            return (Criteria) this;
        }

        public Criteria andNstatusLike(String value) {
            addCriterion("nStatus like", value, "nstatus");
            return (Criteria) this;
        }

        public Criteria andNstatusNotLike(String value) {
            addCriterion("nStatus not like", value, "nstatus");
            return (Criteria) this;
        }

        public Criteria andNstatusIn(List<String> values) {
            addCriterion("nStatus in", values, "nstatus");
            return (Criteria) this;
        }

        public Criteria andNstatusNotIn(List<String> values) {
            addCriterion("nStatus not in", values, "nstatus");
            return (Criteria) this;
        }

        public Criteria andNstatusBetween(String value1, String value2) {
            addCriterion("nStatus between", value1, value2, "nstatus");
            return (Criteria) this;
        }

        public Criteria andNstatusNotBetween(String value1, String value2) {
            addCriterion("nStatus not between", value1, value2, "nstatus");
            return (Criteria) this;
        }

        public Criteria andScommentIsNull() {
            addCriterion("sComment is null");
            return (Criteria) this;
        }

        public Criteria andScommentIsNotNull() {
            addCriterion("sComment is not null");
            return (Criteria) this;
        }

        public Criteria andScommentEqualTo(String value) {
            addCriterion("sComment =", value, "scomment");
            return (Criteria) this;
        }

        public Criteria andScommentNotEqualTo(String value) {
            addCriterion("sComment <>", value, "scomment");
            return (Criteria) this;
        }

        public Criteria andScommentGreaterThan(String value) {
            addCriterion("sComment >", value, "scomment");
            return (Criteria) this;
        }

        public Criteria andScommentGreaterThanOrEqualTo(String value) {
            addCriterion("sComment >=", value, "scomment");
            return (Criteria) this;
        }

        public Criteria andScommentLessThan(String value) {
            addCriterion("sComment <", value, "scomment");
            return (Criteria) this;
        }

        public Criteria andScommentLessThanOrEqualTo(String value) {
            addCriterion("sComment <=", value, "scomment");
            return (Criteria) this;
        }

        public Criteria andScommentLike(String value) {
            addCriterion("sComment like", value, "scomment");
            return (Criteria) this;
        }

        public Criteria andScommentNotLike(String value) {
            addCriterion("sComment not like", value, "scomment");
            return (Criteria) this;
        }

        public Criteria andScommentIn(List<String> values) {
            addCriterion("sComment in", values, "scomment");
            return (Criteria) this;
        }

        public Criteria andScommentNotIn(List<String> values) {
            addCriterion("sComment not in", values, "scomment");
            return (Criteria) this;
        }

        public Criteria andScommentBetween(String value1, String value2) {
            addCriterion("sComment between", value1, value2, "scomment");
            return (Criteria) this;
        }

        public Criteria andScommentNotBetween(String value1, String value2) {
            addCriterion("sComment not between", value1, value2, "scomment");
            return (Criteria) this;
        }

        public Criteria andNordernumIsNull() {
            addCriterion("nOrderNum is null");
            return (Criteria) this;
        }

        public Criteria andNordernumIsNotNull() {
            addCriterion("nOrderNum is not null");
            return (Criteria) this;
        }

        public Criteria andNordernumEqualTo(Byte value) {
            addCriterion("nOrderNum =", value, "nordernum");
            return (Criteria) this;
        }

        public Criteria andNordernumNotEqualTo(Byte value) {
            addCriterion("nOrderNum <>", value, "nordernum");
            return (Criteria) this;
        }

        public Criteria andNordernumGreaterThan(Byte value) {
            addCriterion("nOrderNum >", value, "nordernum");
            return (Criteria) this;
        }

        public Criteria andNordernumGreaterThanOrEqualTo(Byte value) {
            addCriterion("nOrderNum >=", value, "nordernum");
            return (Criteria) this;
        }

        public Criteria andNordernumLessThan(Byte value) {
            addCriterion("nOrderNum <", value, "nordernum");
            return (Criteria) this;
        }

        public Criteria andNordernumLessThanOrEqualTo(Byte value) {
            addCriterion("nOrderNum <=", value, "nordernum");
            return (Criteria) this;
        }

        public Criteria andNordernumIn(List<Byte> values) {
            addCriterion("nOrderNum in", values, "nordernum");
            return (Criteria) this;
        }

        public Criteria andNordernumNotIn(List<Byte> values) {
            addCriterion("nOrderNum not in", values, "nordernum");
            return (Criteria) this;
        }

        public Criteria andNordernumBetween(Byte value1, Byte value2) {
            addCriterion("nOrderNum between", value1, value2, "nordernum");
            return (Criteria) this;
        }

        public Criteria andNordernumNotBetween(Byte value1, Byte value2) {
            addCriterion("nOrderNum not between", value1, value2, "nordernum");
            return (Criteria) this;
        }

        public Criteria andDcreatedateIsNull() {
            addCriterion("dCreateDate is null");
            return (Criteria) this;
        }

        public Criteria andDcreatedateIsNotNull() {
            addCriterion("dCreateDate is not null");
            return (Criteria) this;
        }

        public Criteria andDcreatedateEqualTo(Date value) {
            addCriterion("dCreateDate =", value, "dcreatedate");
            return (Criteria) this;
        }

        public Criteria andDcreatedateNotEqualTo(Date value) {
            addCriterion("dCreateDate <>", value, "dcreatedate");
            return (Criteria) this;
        }

        public Criteria andDcreatedateGreaterThan(Date value) {
            addCriterion("dCreateDate >", value, "dcreatedate");
            return (Criteria) this;
        }

        public Criteria andDcreatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("dCreateDate >=", value, "dcreatedate");
            return (Criteria) this;
        }

        public Criteria andDcreatedateLessThan(Date value) {
            addCriterion("dCreateDate <", value, "dcreatedate");
            return (Criteria) this;
        }

        public Criteria andDcreatedateLessThanOrEqualTo(Date value) {
            addCriterion("dCreateDate <=", value, "dcreatedate");
            return (Criteria) this;
        }

        public Criteria andDcreatedateIn(List<Date> values) {
            addCriterion("dCreateDate in", values, "dcreatedate");
            return (Criteria) this;
        }

        public Criteria andDcreatedateNotIn(List<Date> values) {
            addCriterion("dCreateDate not in", values, "dcreatedate");
            return (Criteria) this;
        }

        public Criteria andDcreatedateBetween(Date value1, Date value2) {
            addCriterion("dCreateDate between", value1, value2, "dcreatedate");
            return (Criteria) this;
        }

        public Criteria andDcreatedateNotBetween(Date value1, Date value2) {
            addCriterion("dCreateDate not between", value1, value2, "dcreatedate");
            return (Criteria) this;
        }

        public Criteria andDupdatedateIsNull() {
            addCriterion("dUpdateDate is null");
            return (Criteria) this;
        }

        public Criteria andDupdatedateIsNotNull() {
            addCriterion("dUpdateDate is not null");
            return (Criteria) this;
        }

        public Criteria andDupdatedateEqualTo(Date value) {
            addCriterion("dUpdateDate =", value, "dupdatedate");
            return (Criteria) this;
        }

        public Criteria andDupdatedateNotEqualTo(Date value) {
            addCriterion("dUpdateDate <>", value, "dupdatedate");
            return (Criteria) this;
        }

        public Criteria andDupdatedateGreaterThan(Date value) {
            addCriterion("dUpdateDate >", value, "dupdatedate");
            return (Criteria) this;
        }

        public Criteria andDupdatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("dUpdateDate >=", value, "dupdatedate");
            return (Criteria) this;
        }

        public Criteria andDupdatedateLessThan(Date value) {
            addCriterion("dUpdateDate <", value, "dupdatedate");
            return (Criteria) this;
        }

        public Criteria andDupdatedateLessThanOrEqualTo(Date value) {
            addCriterion("dUpdateDate <=", value, "dupdatedate");
            return (Criteria) this;
        }

        public Criteria andDupdatedateIn(List<Date> values) {
            addCriterion("dUpdateDate in", values, "dupdatedate");
            return (Criteria) this;
        }

        public Criteria andDupdatedateNotIn(List<Date> values) {
            addCriterion("dUpdateDate not in", values, "dupdatedate");
            return (Criteria) this;
        }

        public Criteria andDupdatedateBetween(Date value1, Date value2) {
            addCriterion("dUpdateDate between", value1, value2, "dupdatedate");
            return (Criteria) this;
        }

        public Criteria andDupdatedateNotBetween(Date value1, Date value2) {
            addCriterion("dUpdateDate not between", value1, value2, "dupdatedate");
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