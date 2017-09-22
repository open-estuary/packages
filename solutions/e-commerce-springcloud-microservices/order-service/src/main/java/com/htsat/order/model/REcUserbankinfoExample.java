package com.htsat.order.model;

import java.util.ArrayList;
import java.util.List;

public class REcUserbankinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcUserbankinfoExample() {
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

        public Criteria andScardnumberIsNull() {
            addCriterion("sCardNumber is null");
            return (Criteria) this;
        }

        public Criteria andScardnumberIsNotNull() {
            addCriterion("sCardNumber is not null");
            return (Criteria) this;
        }

        public Criteria andScardnumberEqualTo(String value) {
            addCriterion("sCardNumber =", value, "scardnumber");
            return (Criteria) this;
        }

        public Criteria andScardnumberNotEqualTo(String value) {
            addCriterion("sCardNumber <>", value, "scardnumber");
            return (Criteria) this;
        }

        public Criteria andScardnumberGreaterThan(String value) {
            addCriterion("sCardNumber >", value, "scardnumber");
            return (Criteria) this;
        }

        public Criteria andScardnumberGreaterThanOrEqualTo(String value) {
            addCriterion("sCardNumber >=", value, "scardnumber");
            return (Criteria) this;
        }

        public Criteria andScardnumberLessThan(String value) {
            addCriterion("sCardNumber <", value, "scardnumber");
            return (Criteria) this;
        }

        public Criteria andScardnumberLessThanOrEqualTo(String value) {
            addCriterion("sCardNumber <=", value, "scardnumber");
            return (Criteria) this;
        }

        public Criteria andScardnumberLike(String value) {
            addCriterion("sCardNumber like", value, "scardnumber");
            return (Criteria) this;
        }

        public Criteria andScardnumberNotLike(String value) {
            addCriterion("sCardNumber not like", value, "scardnumber");
            return (Criteria) this;
        }

        public Criteria andScardnumberIn(List<String> values) {
            addCriterion("sCardNumber in", values, "scardnumber");
            return (Criteria) this;
        }

        public Criteria andScardnumberNotIn(List<String> values) {
            addCriterion("sCardNumber not in", values, "scardnumber");
            return (Criteria) this;
        }

        public Criteria andScardnumberBetween(String value1, String value2) {
            addCriterion("sCardNumber between", value1, value2, "scardnumber");
            return (Criteria) this;
        }

        public Criteria andScardnumberNotBetween(String value1, String value2) {
            addCriterion("sCardNumber not between", value1, value2, "scardnumber");
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

        public Criteria andSbankIsNull() {
            addCriterion("sBank is null");
            return (Criteria) this;
        }

        public Criteria andSbankIsNotNull() {
            addCriterion("sBank is not null");
            return (Criteria) this;
        }

        public Criteria andSbankEqualTo(String value) {
            addCriterion("sBank =", value, "sbank");
            return (Criteria) this;
        }

        public Criteria andSbankNotEqualTo(String value) {
            addCriterion("sBank <>", value, "sbank");
            return (Criteria) this;
        }

        public Criteria andSbankGreaterThan(String value) {
            addCriterion("sBank >", value, "sbank");
            return (Criteria) this;
        }

        public Criteria andSbankGreaterThanOrEqualTo(String value) {
            addCriterion("sBank >=", value, "sbank");
            return (Criteria) this;
        }

        public Criteria andSbankLessThan(String value) {
            addCriterion("sBank <", value, "sbank");
            return (Criteria) this;
        }

        public Criteria andSbankLessThanOrEqualTo(String value) {
            addCriterion("sBank <=", value, "sbank");
            return (Criteria) this;
        }

        public Criteria andSbankLike(String value) {
            addCriterion("sBank like", value, "sbank");
            return (Criteria) this;
        }

        public Criteria andSbankNotLike(String value) {
            addCriterion("sBank not like", value, "sbank");
            return (Criteria) this;
        }

        public Criteria andSbankIn(List<String> values) {
            addCriterion("sBank in", values, "sbank");
            return (Criteria) this;
        }

        public Criteria andSbankNotIn(List<String> values) {
            addCriterion("sBank not in", values, "sbank");
            return (Criteria) this;
        }

        public Criteria andSbankBetween(String value1, String value2) {
            addCriterion("sBank between", value1, value2, "sbank");
            return (Criteria) this;
        }

        public Criteria andSbankNotBetween(String value1, String value2) {
            addCriterion("sBank not between", value1, value2, "sbank");
            return (Criteria) this;
        }

        public Criteria andScardtypeIsNull() {
            addCriterion("sCardType is null");
            return (Criteria) this;
        }

        public Criteria andScardtypeIsNotNull() {
            addCriterion("sCardType is not null");
            return (Criteria) this;
        }

        public Criteria andScardtypeEqualTo(String value) {
            addCriterion("sCardType =", value, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScardtypeNotEqualTo(String value) {
            addCriterion("sCardType <>", value, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScardtypeGreaterThan(String value) {
            addCriterion("sCardType >", value, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScardtypeGreaterThanOrEqualTo(String value) {
            addCriterion("sCardType >=", value, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScardtypeLessThan(String value) {
            addCriterion("sCardType <", value, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScardtypeLessThanOrEqualTo(String value) {
            addCriterion("sCardType <=", value, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScardtypeLike(String value) {
            addCriterion("sCardType like", value, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScardtypeNotLike(String value) {
            addCriterion("sCardType not like", value, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScardtypeIn(List<String> values) {
            addCriterion("sCardType in", values, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScardtypeNotIn(List<String> values) {
            addCriterion("sCardType not in", values, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScardtypeBetween(String value1, String value2) {
            addCriterion("sCardType between", value1, value2, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScardtypeNotBetween(String value1, String value2) {
            addCriterion("sCardType not between", value1, value2, "scardtype");
            return (Criteria) this;
        }

        public Criteria andScurrencyIsNull() {
            addCriterion("sCurrency is null");
            return (Criteria) this;
        }

        public Criteria andScurrencyIsNotNull() {
            addCriterion("sCurrency is not null");
            return (Criteria) this;
        }

        public Criteria andScurrencyEqualTo(String value) {
            addCriterion("sCurrency =", value, "scurrency");
            return (Criteria) this;
        }

        public Criteria andScurrencyNotEqualTo(String value) {
            addCriterion("sCurrency <>", value, "scurrency");
            return (Criteria) this;
        }

        public Criteria andScurrencyGreaterThan(String value) {
            addCriterion("sCurrency >", value, "scurrency");
            return (Criteria) this;
        }

        public Criteria andScurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("sCurrency >=", value, "scurrency");
            return (Criteria) this;
        }

        public Criteria andScurrencyLessThan(String value) {
            addCriterion("sCurrency <", value, "scurrency");
            return (Criteria) this;
        }

        public Criteria andScurrencyLessThanOrEqualTo(String value) {
            addCriterion("sCurrency <=", value, "scurrency");
            return (Criteria) this;
        }

        public Criteria andScurrencyLike(String value) {
            addCriterion("sCurrency like", value, "scurrency");
            return (Criteria) this;
        }

        public Criteria andScurrencyNotLike(String value) {
            addCriterion("sCurrency not like", value, "scurrency");
            return (Criteria) this;
        }

        public Criteria andScurrencyIn(List<String> values) {
            addCriterion("sCurrency in", values, "scurrency");
            return (Criteria) this;
        }

        public Criteria andScurrencyNotIn(List<String> values) {
            addCriterion("sCurrency not in", values, "scurrency");
            return (Criteria) this;
        }

        public Criteria andScurrencyBetween(String value1, String value2) {
            addCriterion("sCurrency between", value1, value2, "scurrency");
            return (Criteria) this;
        }

        public Criteria andScurrencyNotBetween(String value1, String value2) {
            addCriterion("sCurrency not between", value1, value2, "scurrency");
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