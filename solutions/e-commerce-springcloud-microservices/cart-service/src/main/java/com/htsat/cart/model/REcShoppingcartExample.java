package com.htsat.cart.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class REcShoppingcartExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcShoppingcartExample() {
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

        public Criteria andNshoppingcartidIsNull() {
            addCriterion("nShoppingCartID is null");
            return (Criteria) this;
        }

        public Criteria andNshoppingcartidIsNotNull() {
            addCriterion("nShoppingCartID is not null");
            return (Criteria) this;
        }

        public Criteria andNshoppingcartidEqualTo(Long value) {
            addCriterion("nShoppingCartID =", value, "nshoppingcartid");
            return (Criteria) this;
        }

        public Criteria andNshoppingcartidNotEqualTo(Long value) {
            addCriterion("nShoppingCartID <>", value, "nshoppingcartid");
            return (Criteria) this;
        }

        public Criteria andNshoppingcartidGreaterThan(Long value) {
            addCriterion("nShoppingCartID >", value, "nshoppingcartid");
            return (Criteria) this;
        }

        public Criteria andNshoppingcartidGreaterThanOrEqualTo(Long value) {
            addCriterion("nShoppingCartID >=", value, "nshoppingcartid");
            return (Criteria) this;
        }

        public Criteria andNshoppingcartidLessThan(Long value) {
            addCriterion("nShoppingCartID <", value, "nshoppingcartid");
            return (Criteria) this;
        }

        public Criteria andNshoppingcartidLessThanOrEqualTo(Long value) {
            addCriterion("nShoppingCartID <=", value, "nshoppingcartid");
            return (Criteria) this;
        }

        public Criteria andNshoppingcartidIn(List<Long> values) {
            addCriterion("nShoppingCartID in", values, "nshoppingcartid");
            return (Criteria) this;
        }

        public Criteria andNshoppingcartidNotIn(List<Long> values) {
            addCriterion("nShoppingCartID not in", values, "nshoppingcartid");
            return (Criteria) this;
        }

        public Criteria andNshoppingcartidBetween(Long value1, Long value2) {
            addCriterion("nShoppingCartID between", value1, value2, "nshoppingcartid");
            return (Criteria) this;
        }

        public Criteria andNshoppingcartidNotBetween(Long value1, Long value2) {
            addCriterion("nShoppingCartID not between", value1, value2, "nshoppingcartid");
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

        public Criteria andNuseridEqualTo(Long value) {
            addCriterion("nUserID =", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridNotEqualTo(Long value) {
            addCriterion("nUserID <>", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridGreaterThan(Long value) {
            addCriterion("nUserID >", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridGreaterThanOrEqualTo(Long value) {
            addCriterion("nUserID >=", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridLessThan(Long value) {
            addCriterion("nUserID <", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridLessThanOrEqualTo(Long value) {
            addCriterion("nUserID <=", value, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridIn(List<Long> values) {
            addCriterion("nUserID in", values, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridNotIn(List<Long> values) {
            addCriterion("nUserID not in", values, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridBetween(Long value1, Long value2) {
            addCriterion("nUserID between", value1, value2, "nuserid");
            return (Criteria) this;
        }

        public Criteria andNuseridNotBetween(Long value1, Long value2) {
            addCriterion("nUserID not between", value1, value2, "nuserid");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeIsNull() {
            addCriterion("sUpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeIsNotNull() {
            addCriterion("sUpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeEqualTo(Date value) {
            addCriterion("sUpdateTime =", value, "supdatetime");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeNotEqualTo(Date value) {
            addCriterion("sUpdateTime <>", value, "supdatetime");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeGreaterThan(Date value) {
            addCriterion("sUpdateTime >", value, "supdatetime");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sUpdateTime >=", value, "supdatetime");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeLessThan(Date value) {
            addCriterion("sUpdateTime <", value, "supdatetime");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("sUpdateTime <=", value, "supdatetime");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeIn(List<Date> values) {
            addCriterion("sUpdateTime in", values, "supdatetime");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeNotIn(List<Date> values) {
            addCriterion("sUpdateTime not in", values, "supdatetime");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeBetween(Date value1, Date value2) {
            addCriterion("sUpdateTime between", value1, value2, "supdatetime");
            return (Criteria) this;
        }

        public Criteria andSupdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("sUpdateTime not between", value1, value2, "supdatetime");
            return (Criteria) this;
        }

        public Criteria andNdiscountIsNull() {
            addCriterion("nDiscount is null");
            return (Criteria) this;
        }

        public Criteria andNdiscountIsNotNull() {
            addCriterion("nDiscount is not null");
            return (Criteria) this;
        }

        public Criteria andNdiscountEqualTo(BigDecimal value) {
            addCriterion("nDiscount =", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountNotEqualTo(BigDecimal value) {
            addCriterion("nDiscount <>", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountGreaterThan(BigDecimal value) {
            addCriterion("nDiscount >", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("nDiscount >=", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountLessThan(BigDecimal value) {
            addCriterion("nDiscount <", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("nDiscount <=", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountIn(List<BigDecimal> values) {
            addCriterion("nDiscount in", values, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountNotIn(List<BigDecimal> values) {
            addCriterion("nDiscount not in", values, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nDiscount between", value1, value2, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nDiscount not between", value1, value2, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityIsNull() {
            addCriterion("nTotalQuantity is null");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityIsNotNull() {
            addCriterion("nTotalQuantity is not null");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityEqualTo(Integer value) {
            addCriterion("nTotalQuantity =", value, "ntotalquantity");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityNotEqualTo(Integer value) {
            addCriterion("nTotalQuantity <>", value, "ntotalquantity");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityGreaterThan(Integer value) {
            addCriterion("nTotalQuantity >", value, "ntotalquantity");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("nTotalQuantity >=", value, "ntotalquantity");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityLessThan(Integer value) {
            addCriterion("nTotalQuantity <", value, "ntotalquantity");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityLessThanOrEqualTo(Integer value) {
            addCriterion("nTotalQuantity <=", value, "ntotalquantity");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityIn(List<Integer> values) {
            addCriterion("nTotalQuantity in", values, "ntotalquantity");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityNotIn(List<Integer> values) {
            addCriterion("nTotalQuantity not in", values, "ntotalquantity");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityBetween(Integer value1, Integer value2) {
            addCriterion("nTotalQuantity between", value1, value2, "ntotalquantity");
            return (Criteria) this;
        }

        public Criteria andNtotalquantityNotBetween(Integer value1, Integer value2) {
            addCriterion("nTotalQuantity not between", value1, value2, "ntotalquantity");
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

        public Criteria andNtotalpriceIsNull() {
            addCriterion("nTotalPrice is null");
            return (Criteria) this;
        }

        public Criteria andNtotalpriceIsNotNull() {
            addCriterion("nTotalPrice is not null");
            return (Criteria) this;
        }

        public Criteria andNtotalpriceEqualTo(BigDecimal value) {
            addCriterion("nTotalPrice =", value, "ntotalprice");
            return (Criteria) this;
        }

        public Criteria andNtotalpriceNotEqualTo(BigDecimal value) {
            addCriterion("nTotalPrice <>", value, "ntotalprice");
            return (Criteria) this;
        }

        public Criteria andNtotalpriceGreaterThan(BigDecimal value) {
            addCriterion("nTotalPrice >", value, "ntotalprice");
            return (Criteria) this;
        }

        public Criteria andNtotalpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("nTotalPrice >=", value, "ntotalprice");
            return (Criteria) this;
        }

        public Criteria andNtotalpriceLessThan(BigDecimal value) {
            addCriterion("nTotalPrice <", value, "ntotalprice");
            return (Criteria) this;
        }

        public Criteria andNtotalpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("nTotalPrice <=", value, "ntotalprice");
            return (Criteria) this;
        }

        public Criteria andNtotalpriceIn(List<BigDecimal> values) {
            addCriterion("nTotalPrice in", values, "ntotalprice");
            return (Criteria) this;
        }

        public Criteria andNtotalpriceNotIn(List<BigDecimal> values) {
            addCriterion("nTotalPrice not in", values, "ntotalprice");
            return (Criteria) this;
        }

        public Criteria andNtotalpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nTotalPrice between", value1, value2, "ntotalprice");
            return (Criteria) this;
        }

        public Criteria andNtotalpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nTotalPrice not between", value1, value2, "ntotalprice");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeIsNull() {
            addCriterion("nCreateTime is null");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeIsNotNull() {
            addCriterion("nCreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeEqualTo(Date value) {
            addCriterion("nCreateTime =", value, "ncreatetime");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeNotEqualTo(Date value) {
            addCriterion("nCreateTime <>", value, "ncreatetime");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeGreaterThan(Date value) {
            addCriterion("nCreateTime >", value, "ncreatetime");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("nCreateTime >=", value, "ncreatetime");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeLessThan(Date value) {
            addCriterion("nCreateTime <", value, "ncreatetime");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("nCreateTime <=", value, "ncreatetime");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeIn(List<Date> values) {
            addCriterion("nCreateTime in", values, "ncreatetime");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeNotIn(List<Date> values) {
            addCriterion("nCreateTime not in", values, "ncreatetime");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeBetween(Date value1, Date value2) {
            addCriterion("nCreateTime between", value1, value2, "ncreatetime");
            return (Criteria) this;
        }

        public Criteria andNcreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("nCreateTime not between", value1, value2, "ncreatetime");
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