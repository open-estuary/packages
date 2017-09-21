package com.htsat.order.model;

import java.util.ArrayList;
import java.util.List;

public class REcUserdeliveryaddressExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcUserdeliveryaddressExample() {
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

        public Criteria andNaddressnoIsNull() {
            addCriterion("nAddressNo is null");
            return (Criteria) this;
        }

        public Criteria andNaddressnoIsNotNull() {
            addCriterion("nAddressNo is not null");
            return (Criteria) this;
        }

        public Criteria andNaddressnoEqualTo(Integer value) {
            addCriterion("nAddressNo =", value, "naddressno");
            return (Criteria) this;
        }

        public Criteria andNaddressnoNotEqualTo(Integer value) {
            addCriterion("nAddressNo <>", value, "naddressno");
            return (Criteria) this;
        }

        public Criteria andNaddressnoGreaterThan(Integer value) {
            addCriterion("nAddressNo >", value, "naddressno");
            return (Criteria) this;
        }

        public Criteria andNaddressnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("nAddressNo >=", value, "naddressno");
            return (Criteria) this;
        }

        public Criteria andNaddressnoLessThan(Integer value) {
            addCriterion("nAddressNo <", value, "naddressno");
            return (Criteria) this;
        }

        public Criteria andNaddressnoLessThanOrEqualTo(Integer value) {
            addCriterion("nAddressNo <=", value, "naddressno");
            return (Criteria) this;
        }

        public Criteria andNaddressnoIn(List<Integer> values) {
            addCriterion("nAddressNo in", values, "naddressno");
            return (Criteria) this;
        }

        public Criteria andNaddressnoNotIn(List<Integer> values) {
            addCriterion("nAddressNo not in", values, "naddressno");
            return (Criteria) this;
        }

        public Criteria andNaddressnoBetween(Integer value1, Integer value2) {
            addCriterion("nAddressNo between", value1, value2, "naddressno");
            return (Criteria) this;
        }

        public Criteria andNaddressnoNotBetween(Integer value1, Integer value2) {
            addCriterion("nAddressNo not between", value1, value2, "naddressno");
            return (Criteria) this;
        }

        public Criteria andNuseridIsNull() {
            addCriterion("nUserID is null");
            return (Criteria) this;
        }

        public Criteria andNuseridIsNotNull() {
            addCriterion("nUserID is not null");
            return (Criteria) this;
        }

        public Criteria andNuseridEqualTo(Integer value) {
            addCriterion("nUserID =", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridNotEqualTo(Integer value) {
            addCriterion("nUserID <>", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridGreaterThan(Integer value) {
            addCriterion("nUserID >", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("nUserID >=", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridLessThan(Integer value) {
            addCriterion("nUserID <", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridLessThanOrEqualTo(Integer value) {
            addCriterion("nUserID <=", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridIn(List<Integer> values) {
            addCriterion("nUserID in", values, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridNotIn(List<Integer> values) {
            addCriterion("nUserID not in", values, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridBetween(Integer value1, Integer value2) {
            addCriterion("nUserID between", value1, value2, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridNotBetween(Integer value1, Integer value2) {
            addCriterion("nUserID not between", value1, value2, "nuserid");
            return (Criteria) this;
        }

        public Criteria andSfirstnameIsNull() {
            addCriterion("sFirstName is null");
            return (Criteria) this;
        }

        public Criteria andSfirstnameIsNotNull() {
            addCriterion("sFirstName is not null");
            return (Criteria) this;
        }

        public Criteria andSfirstnameEqualTo(String value) {
            addCriterion("sFirstName =", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameNotEqualTo(String value) {
            addCriterion("sFirstName <>", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameGreaterThan(String value) {
            addCriterion("sFirstName >", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameGreaterThanOrEqualTo(String value) {
            addCriterion("sFirstName >=", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameLessThan(String value) {
            addCriterion("sFirstName <", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameLessThanOrEqualTo(String value) {
            addCriterion("sFirstName <=", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameLike(String value) {
            addCriterion("sFirstName like", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameNotLike(String value) {
            addCriterion("sFirstName not like", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameIn(List<String> values) {
            addCriterion("sFirstName in", values, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameNotIn(List<String> values) {
            addCriterion("sFirstName not in", values, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameBetween(String value1, String value2) {
            addCriterion("sFirstName between", value1, value2, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameNotBetween(String value1, String value2) {
            addCriterion("sFirstName not between", value1, value2, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSlastnameIsNull() {
            addCriterion("sLastName is null");
            return (Criteria) this;
        }

        public Criteria andSlastnameIsNotNull() {
            addCriterion("sLastName is not null");
            return (Criteria) this;
        }

        public Criteria andSlastnameEqualTo(String value) {
            addCriterion("sLastName =", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameNotEqualTo(String value) {
            addCriterion("sLastName <>", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameGreaterThan(String value) {
            addCriterion("sLastName >", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameGreaterThanOrEqualTo(String value) {
            addCriterion("sLastName >=", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameLessThan(String value) {
            addCriterion("sLastName <", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameLessThanOrEqualTo(String value) {
            addCriterion("sLastName <=", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameLike(String value) {
            addCriterion("sLastName like", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameNotLike(String value) {
            addCriterion("sLastName not like", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameIn(List<String> values) {
            addCriterion("sLastName in", values, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameNotIn(List<String> values) {
            addCriterion("sLastName not in", values, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameBetween(String value1, String value2) {
            addCriterion("sLastName between", value1, value2, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameNotBetween(String value1, String value2) {
            addCriterion("sLastName not between", value1, value2, "slastname");
            return (Criteria) this;
        }

        public Criteria andSaddressIsNull() {
            addCriterion("sAddress is null");
            return (Criteria) this;
        }

        public Criteria andSaddressIsNotNull() {
            addCriterion("sAddress is not null");
            return (Criteria) this;
        }

        public Criteria andSaddressEqualTo(String value) {
            addCriterion("sAddress =", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressNotEqualTo(String value) {
            addCriterion("sAddress <>", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressGreaterThan(String value) {
            addCriterion("sAddress >", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressGreaterThanOrEqualTo(String value) {
            addCriterion("sAddress >=", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressLessThan(String value) {
            addCriterion("sAddress <", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressLessThanOrEqualTo(String value) {
            addCriterion("sAddress <=", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressLike(String value) {
            addCriterion("sAddress like", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressNotLike(String value) {
            addCriterion("sAddress not like", value, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressIn(List<String> values) {
            addCriterion("sAddress in", values, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressNotIn(List<String> values) {
            addCriterion("sAddress not in", values, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressBetween(String value1, String value2) {
            addCriterion("sAddress between", value1, value2, "saddress");
            return (Criteria) this;
        }

        public Criteria andSaddressNotBetween(String value1, String value2) {
            addCriterion("sAddress not between", value1, value2, "saddress");
            return (Criteria) this;
        }

        public Criteria andScityIsNull() {
            addCriterion("sCity is null");
            return (Criteria) this;
        }

        public Criteria andScityIsNotNull() {
            addCriterion("sCity is not null");
            return (Criteria) this;
        }

        public Criteria andScityEqualTo(String value) {
            addCriterion("sCity =", value, "scity");
            return (Criteria) this;
        }

        public Criteria andScityNotEqualTo(String value) {
            addCriterion("sCity <>", value, "scity");
            return (Criteria) this;
        }

        public Criteria andScityGreaterThan(String value) {
            addCriterion("sCity >", value, "scity");
            return (Criteria) this;
        }

        public Criteria andScityGreaterThanOrEqualTo(String value) {
            addCriterion("sCity >=", value, "scity");
            return (Criteria) this;
        }

        public Criteria andScityLessThan(String value) {
            addCriterion("sCity <", value, "scity");
            return (Criteria) this;
        }

        public Criteria andScityLessThanOrEqualTo(String value) {
            addCriterion("sCity <=", value, "scity");
            return (Criteria) this;
        }

        public Criteria andScityLike(String value) {
            addCriterion("sCity like", value, "scity");
            return (Criteria) this;
        }

        public Criteria andScityNotLike(String value) {
            addCriterion("sCity not like", value, "scity");
            return (Criteria) this;
        }

        public Criteria andScityIn(List<String> values) {
            addCriterion("sCity in", values, "scity");
            return (Criteria) this;
        }

        public Criteria andScityNotIn(List<String> values) {
            addCriterion("sCity not in", values, "scity");
            return (Criteria) this;
        }

        public Criteria andScityBetween(String value1, String value2) {
            addCriterion("sCity between", value1, value2, "scity");
            return (Criteria) this;
        }

        public Criteria andScityNotBetween(String value1, String value2) {
            addCriterion("sCity not between", value1, value2, "scity");
            return (Criteria) this;
        }

        public Criteria andSprovinceIsNull() {
            addCriterion("sProvince is null");
            return (Criteria) this;
        }

        public Criteria andSprovinceIsNotNull() {
            addCriterion("sProvince is not null");
            return (Criteria) this;
        }

        public Criteria andSprovinceEqualTo(String value) {
            addCriterion("sProvince =", value, "sprovince");
            return (Criteria) this;
        }

        public Criteria andSprovinceNotEqualTo(String value) {
            addCriterion("sProvince <>", value, "sprovince");
            return (Criteria) this;
        }

        public Criteria andSprovinceGreaterThan(String value) {
            addCriterion("sProvince >", value, "sprovince");
            return (Criteria) this;
        }

        public Criteria andSprovinceGreaterThanOrEqualTo(String value) {
            addCriterion("sProvince >=", value, "sprovince");
            return (Criteria) this;
        }

        public Criteria andSprovinceLessThan(String value) {
            addCriterion("sProvince <", value, "sprovince");
            return (Criteria) this;
        }

        public Criteria andSprovinceLessThanOrEqualTo(String value) {
            addCriterion("sProvince <=", value, "sprovince");
            return (Criteria) this;
        }

        public Criteria andSprovinceLike(String value) {
            addCriterion("sProvince like", value, "sprovince");
            return (Criteria) this;
        }

        public Criteria andSprovinceNotLike(String value) {
            addCriterion("sProvince not like", value, "sprovince");
            return (Criteria) this;
        }

        public Criteria andSprovinceIn(List<String> values) {
            addCriterion("sProvince in", values, "sprovince");
            return (Criteria) this;
        }

        public Criteria andSprovinceNotIn(List<String> values) {
            addCriterion("sProvince not in", values, "sprovince");
            return (Criteria) this;
        }

        public Criteria andSprovinceBetween(String value1, String value2) {
            addCriterion("sProvince between", value1, value2, "sprovince");
            return (Criteria) this;
        }

        public Criteria andSprovinceNotBetween(String value1, String value2) {
            addCriterion("sProvince not between", value1, value2, "sprovince");
            return (Criteria) this;
        }

        public Criteria andScountryIsNull() {
            addCriterion("sCountry is null");
            return (Criteria) this;
        }

        public Criteria andScountryIsNotNull() {
            addCriterion("sCountry is not null");
            return (Criteria) this;
        }

        public Criteria andScountryEqualTo(String value) {
            addCriterion("sCountry =", value, "scountry");
            return (Criteria) this;
        }

        public Criteria andScountryNotEqualTo(String value) {
            addCriterion("sCountry <>", value, "scountry");
            return (Criteria) this;
        }

        public Criteria andScountryGreaterThan(String value) {
            addCriterion("sCountry >", value, "scountry");
            return (Criteria) this;
        }

        public Criteria andScountryGreaterThanOrEqualTo(String value) {
            addCriterion("sCountry >=", value, "scountry");
            return (Criteria) this;
        }

        public Criteria andScountryLessThan(String value) {
            addCriterion("sCountry <", value, "scountry");
            return (Criteria) this;
        }

        public Criteria andScountryLessThanOrEqualTo(String value) {
            addCriterion("sCountry <=", value, "scountry");
            return (Criteria) this;
        }

        public Criteria andScountryLike(String value) {
            addCriterion("sCountry like", value, "scountry");
            return (Criteria) this;
        }

        public Criteria andScountryNotLike(String value) {
            addCriterion("sCountry not like", value, "scountry");
            return (Criteria) this;
        }

        public Criteria andScountryIn(List<String> values) {
            addCriterion("sCountry in", values, "scountry");
            return (Criteria) this;
        }

        public Criteria andScountryNotIn(List<String> values) {
            addCriterion("sCountry not in", values, "scountry");
            return (Criteria) this;
        }

        public Criteria andScountryBetween(String value1, String value2) {
            addCriterion("sCountry between", value1, value2, "scountry");
            return (Criteria) this;
        }

        public Criteria andScountryNotBetween(String value1, String value2) {
            addCriterion("sCountry not between", value1, value2, "scountry");
            return (Criteria) this;
        }

        public Criteria andSemailIsNull() {
            addCriterion("sEmail is null");
            return (Criteria) this;
        }

        public Criteria andSemailIsNotNull() {
            addCriterion("sEmail is not null");
            return (Criteria) this;
        }

        public Criteria andSemailEqualTo(String value) {
            addCriterion("sEmail =", value, "semail");
            return (Criteria) this;
        }

        public Criteria andSemailNotEqualTo(String value) {
            addCriterion("sEmail <>", value, "semail");
            return (Criteria) this;
        }

        public Criteria andSemailGreaterThan(String value) {
            addCriterion("sEmail >", value, "semail");
            return (Criteria) this;
        }

        public Criteria andSemailGreaterThanOrEqualTo(String value) {
            addCriterion("sEmail >=", value, "semail");
            return (Criteria) this;
        }

        public Criteria andSemailLessThan(String value) {
            addCriterion("sEmail <", value, "semail");
            return (Criteria) this;
        }

        public Criteria andSemailLessThanOrEqualTo(String value) {
            addCriterion("sEmail <=", value, "semail");
            return (Criteria) this;
        }

        public Criteria andSemailLike(String value) {
            addCriterion("sEmail like", value, "semail");
            return (Criteria) this;
        }

        public Criteria andSemailNotLike(String value) {
            addCriterion("sEmail not like", value, "semail");
            return (Criteria) this;
        }

        public Criteria andSemailIn(List<String> values) {
            addCriterion("sEmail in", values, "semail");
            return (Criteria) this;
        }

        public Criteria andSemailNotIn(List<String> values) {
            addCriterion("sEmail not in", values, "semail");
            return (Criteria) this;
        }

        public Criteria andSemailBetween(String value1, String value2) {
            addCriterion("sEmail between", value1, value2, "semail");
            return (Criteria) this;
        }

        public Criteria andSemailNotBetween(String value1, String value2) {
            addCriterion("sEmail not between", value1, value2, "semail");
            return (Criteria) this;
        }

        public Criteria andSphonenoIsNull() {
            addCriterion("sPhoneNo is null");
            return (Criteria) this;
        }

        public Criteria andSphonenoIsNotNull() {
            addCriterion("sPhoneNo is not null");
            return (Criteria) this;
        }

        public Criteria andSphonenoEqualTo(String value) {
            addCriterion("sPhoneNo =", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoNotEqualTo(String value) {
            addCriterion("sPhoneNo <>", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoGreaterThan(String value) {
            addCriterion("sPhoneNo >", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoGreaterThanOrEqualTo(String value) {
            addCriterion("sPhoneNo >=", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoLessThan(String value) {
            addCriterion("sPhoneNo <", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoLessThanOrEqualTo(String value) {
            addCriterion("sPhoneNo <=", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoLike(String value) {
            addCriterion("sPhoneNo like", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoNotLike(String value) {
            addCriterion("sPhoneNo not like", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoIn(List<String> values) {
            addCriterion("sPhoneNo in", values, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoNotIn(List<String> values) {
            addCriterion("sPhoneNo not in", values, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoBetween(String value1, String value2) {
            addCriterion("sPhoneNo between", value1, value2, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoNotBetween(String value1, String value2) {
            addCriterion("sPhoneNo not between", value1, value2, "sphoneno");
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