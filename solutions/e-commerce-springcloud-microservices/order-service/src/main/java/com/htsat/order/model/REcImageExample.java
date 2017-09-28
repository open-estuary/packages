package com.htsat.order.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class REcImageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcImageExample() {
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

        public Criteria andNimagetypeIsNull() {
            addCriterion("nImageType is null");
            return (Criteria) this;
        }

        public Criteria andNimagetypeIsNotNull() {
            addCriterion("nImageType is not null");
            return (Criteria) this;
        }

        public Criteria andNimagetypeEqualTo(String value) {
            addCriterion("nImageType =", value, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andNimagetypeNotEqualTo(String value) {
            addCriterion("nImageType <>", value, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andNimagetypeGreaterThan(String value) {
            addCriterion("nImageType >", value, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andNimagetypeGreaterThanOrEqualTo(String value) {
            addCriterion("nImageType >=", value, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andNimagetypeLessThan(String value) {
            addCriterion("nImageType <", value, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andNimagetypeLessThanOrEqualTo(String value) {
            addCriterion("nImageType <=", value, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andNimagetypeLike(String value) {
            addCriterion("nImageType like", value, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andNimagetypeNotLike(String value) {
            addCriterion("nImageType not like", value, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andNimagetypeIn(List<String> values) {
            addCriterion("nImageType in", values, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andNimagetypeNotIn(List<String> values) {
            addCriterion("nImageType not in", values, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andNimagetypeBetween(String value1, String value2) {
            addCriterion("nImageType between", value1, value2, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andNimagetypeNotBetween(String value1, String value2) {
            addCriterion("nImageType not between", value1, value2, "nimagetype");
            return (Criteria) this;
        }

        public Criteria andSimagenameIsNull() {
            addCriterion("sImageName is null");
            return (Criteria) this;
        }

        public Criteria andSimagenameIsNotNull() {
            addCriterion("sImageName is not null");
            return (Criteria) this;
        }

        public Criteria andSimagenameEqualTo(String value) {
            addCriterion("sImageName =", value, "simagename");
            return (Criteria) this;
        }

        public Criteria andSimagenameNotEqualTo(String value) {
            addCriterion("sImageName <>", value, "simagename");
            return (Criteria) this;
        }

        public Criteria andSimagenameGreaterThan(String value) {
            addCriterion("sImageName >", value, "simagename");
            return (Criteria) this;
        }

        public Criteria andSimagenameGreaterThanOrEqualTo(String value) {
            addCriterion("sImageName >=", value, "simagename");
            return (Criteria) this;
        }

        public Criteria andSimagenameLessThan(String value) {
            addCriterion("sImageName <", value, "simagename");
            return (Criteria) this;
        }

        public Criteria andSimagenameLessThanOrEqualTo(String value) {
            addCriterion("sImageName <=", value, "simagename");
            return (Criteria) this;
        }

        public Criteria andSimagenameLike(String value) {
            addCriterion("sImageName like", value, "simagename");
            return (Criteria) this;
        }

        public Criteria andSimagenameNotLike(String value) {
            addCriterion("sImageName not like", value, "simagename");
            return (Criteria) this;
        }

        public Criteria andSimagenameIn(List<String> values) {
            addCriterion("sImageName in", values, "simagename");
            return (Criteria) this;
        }

        public Criteria andSimagenameNotIn(List<String> values) {
            addCriterion("sImageName not in", values, "simagename");
            return (Criteria) this;
        }

        public Criteria andSimagenameBetween(String value1, String value2) {
            addCriterion("sImageName between", value1, value2, "simagename");
            return (Criteria) this;
        }

        public Criteria andSimagenameNotBetween(String value1, String value2) {
            addCriterion("sImageName not between", value1, value2, "simagename");
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

        public Criteria andSpathIsNull() {
            addCriterion("sPath is null");
            return (Criteria) this;
        }

        public Criteria andSpathIsNotNull() {
            addCriterion("sPath is not null");
            return (Criteria) this;
        }

        public Criteria andSpathEqualTo(String value) {
            addCriterion("sPath =", value, "spath");
            return (Criteria) this;
        }

        public Criteria andSpathNotEqualTo(String value) {
            addCriterion("sPath <>", value, "spath");
            return (Criteria) this;
        }

        public Criteria andSpathGreaterThan(String value) {
            addCriterion("sPath >", value, "spath");
            return (Criteria) this;
        }

        public Criteria andSpathGreaterThanOrEqualTo(String value) {
            addCriterion("sPath >=", value, "spath");
            return (Criteria) this;
        }

        public Criteria andSpathLessThan(String value) {
            addCriterion("sPath <", value, "spath");
            return (Criteria) this;
        }

        public Criteria andSpathLessThanOrEqualTo(String value) {
            addCriterion("sPath <=", value, "spath");
            return (Criteria) this;
        }

        public Criteria andSpathLike(String value) {
            addCriterion("sPath like", value, "spath");
            return (Criteria) this;
        }

        public Criteria andSpathNotLike(String value) {
            addCriterion("sPath not like", value, "spath");
            return (Criteria) this;
        }

        public Criteria andSpathIn(List<String> values) {
            addCriterion("sPath in", values, "spath");
            return (Criteria) this;
        }

        public Criteria andSpathNotIn(List<String> values) {
            addCriterion("sPath not in", values, "spath");
            return (Criteria) this;
        }

        public Criteria andSpathBetween(String value1, String value2) {
            addCriterion("sPath between", value1, value2, "spath");
            return (Criteria) this;
        }

        public Criteria andSpathNotBetween(String value1, String value2) {
            addCriterion("sPath not between", value1, value2, "spath");
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