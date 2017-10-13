package com.htsat.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class REcOrderskuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcOrderskuExample() {
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

        public Criteria andNorderskuidIsNull() {
            addCriterion("nOrderSKUID is null");
            return (Criteria) this;
        }

        public Criteria andNorderskuidIsNotNull() {
            addCriterion("nOrderSKUID is not null");
            return (Criteria) this;
        }

        public Criteria andNorderskuidEqualTo(Long value) {
            addCriterion("nOrderSKUID =", value, "norderskuid");
            return (Criteria) this;
        }

        public Criteria andNorderskuidNotEqualTo(Long value) {
            addCriterion("nOrderSKUID <>", value, "norderskuid");
            return (Criteria) this;
        }

        public Criteria andNorderskuidGreaterThan(Long value) {
            addCriterion("nOrderSKUID >", value, "norderskuid");
            return (Criteria) this;
        }

        public Criteria andNorderskuidGreaterThanOrEqualTo(Long value) {
            addCriterion("nOrderSKUID >=", value, "norderskuid");
            return (Criteria) this;
        }

        public Criteria andNorderskuidLessThan(Long value) {
            addCriterion("nOrderSKUID <", value, "norderskuid");
            return (Criteria) this;
        }

        public Criteria andNorderskuidLessThanOrEqualTo(Long value) {
            addCriterion("nOrderSKUID <=", value, "norderskuid");
            return (Criteria) this;
        }

        public Criteria andNorderskuidIn(List<Long> values) {
            addCriterion("nOrderSKUID in", values, "norderskuid");
            return (Criteria) this;
        }

        public Criteria andNorderskuidNotIn(List<Long> values) {
            addCriterion("nOrderSKUID not in", values, "norderskuid");
            return (Criteria) this;
        }

        public Criteria andNorderskuidBetween(Long value1, Long value2) {
            addCriterion("nOrderSKUID between", value1, value2, "norderskuid");
            return (Criteria) this;
        }

        public Criteria andNorderskuidNotBetween(Long value1, Long value2) {
            addCriterion("nOrderSKUID not between", value1, value2, "norderskuid");
            return (Criteria) this;
        }

        public Criteria andNorderidIsNull() {
            addCriterion("nOrderID is null");
            return (Criteria) this;
        }

        public Criteria andNorderidIsNotNull() {
            addCriterion("nOrderID is not null");
            return (Criteria) this;
        }

        public Criteria andNorderidEqualTo(Long value) {
            addCriterion("nOrderID =", value, "norderid");
            return (Criteria) this;
        }

        public Criteria andNorderidNotEqualTo(Long value) {
            addCriterion("nOrderID <>", value, "norderid");
            return (Criteria) this;
        }

        public Criteria andNorderidGreaterThan(Long value) {
            addCriterion("nOrderID >", value, "norderid");
            return (Criteria) this;
        }

        public Criteria andNorderidGreaterThanOrEqualTo(Long value) {
            addCriterion("nOrderID >=", value, "norderid");
            return (Criteria) this;
        }

        public Criteria andNorderidLessThan(Long value) {
            addCriterion("nOrderID <", value, "norderid");
            return (Criteria) this;
        }

        public Criteria andNorderidLessThanOrEqualTo(Long value) {
            addCriterion("nOrderID <=", value, "norderid");
            return (Criteria) this;
        }

        public Criteria andNorderidIn(List<Long> values) {
            addCriterion("nOrderID in", values, "norderid");
            return (Criteria) this;
        }

        public Criteria andNorderidNotIn(List<Long> values) {
            addCriterion("nOrderID not in", values, "norderid");
            return (Criteria) this;
        }

        public Criteria andNorderidBetween(Long value1, Long value2) {
            addCriterion("nOrderID between", value1, value2, "norderid");
            return (Criteria) this;
        }

        public Criteria andNorderidNotBetween(Long value1, Long value2) {
            addCriterion("nOrderID not between", value1, value2, "norderid");
            return (Criteria) this;
        }

        public Criteria andNskuidIsNull() {
            addCriterion("nSKUID is null");
            return (Criteria) this;
        }

        public Criteria andNskuidIsNotNull() {
            addCriterion("nSKUID is not null");
            return (Criteria) this;
        }

        public Criteria andNskuidEqualTo(Long value) {
            addCriterion("nSKUID =", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidNotEqualTo(Long value) {
            addCriterion("nSKUID <>", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidGreaterThan(Long value) {
            addCriterion("nSKUID >", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidGreaterThanOrEqualTo(Long value) {
            addCriterion("nSKUID >=", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidLessThan(Long value) {
            addCriterion("nSKUID <", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidLessThanOrEqualTo(Long value) {
            addCriterion("nSKUID <=", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidIn(List<Long> values) {
            addCriterion("nSKUID in", values, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidNotIn(List<Long> values) {
            addCriterion("nSKUID not in", values, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidBetween(Long value1, Long value2) {
            addCriterion("nSKUID between", value1, value2, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidNotBetween(Long value1, Long value2) {
            addCriterion("nSKUID not between", value1, value2, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNquantityIsNull() {
            addCriterion("nQuantity is null");
            return (Criteria) this;
        }

        public Criteria andNquantityIsNotNull() {
            addCriterion("nQuantity is not null");
            return (Criteria) this;
        }

        public Criteria andNquantityEqualTo(Integer value) {
            addCriterion("nQuantity =", value, "nquantity");
            return (Criteria) this;
        }

        public Criteria andNquantityNotEqualTo(Integer value) {
            addCriterion("nQuantity <>", value, "nquantity");
            return (Criteria) this;
        }

        public Criteria andNquantityGreaterThan(Integer value) {
            addCriterion("nQuantity >", value, "nquantity");
            return (Criteria) this;
        }

        public Criteria andNquantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("nQuantity >=", value, "nquantity");
            return (Criteria) this;
        }

        public Criteria andNquantityLessThan(Integer value) {
            addCriterion("nQuantity <", value, "nquantity");
            return (Criteria) this;
        }

        public Criteria andNquantityLessThanOrEqualTo(Integer value) {
            addCriterion("nQuantity <=", value, "nquantity");
            return (Criteria) this;
        }

        public Criteria andNquantityIn(List<Integer> values) {
            addCriterion("nQuantity in", values, "nquantity");
            return (Criteria) this;
        }

        public Criteria andNquantityNotIn(List<Integer> values) {
            addCriterion("nQuantity not in", values, "nquantity");
            return (Criteria) this;
        }

        public Criteria andNquantityBetween(Integer value1, Integer value2) {
            addCriterion("nQuantity between", value1, value2, "nquantity");
            return (Criteria) this;
        }

        public Criteria andNquantityNotBetween(Integer value1, Integer value2) {
            addCriterion("nQuantity not between", value1, value2, "nquantity");
            return (Criteria) this;
        }

        public Criteria andNorigpriceIsNull() {
            addCriterion("nOrigPrice is null");
            return (Criteria) this;
        }

        public Criteria andNorigpriceIsNotNull() {
            addCriterion("nOrigPrice is not null");
            return (Criteria) this;
        }

        public Criteria andNorigpriceEqualTo(BigDecimal value) {
            addCriterion("nOrigPrice =", value, "norigprice");
            return (Criteria) this;
        }

        public Criteria andNorigpriceNotEqualTo(BigDecimal value) {
            addCriterion("nOrigPrice <>", value, "norigprice");
            return (Criteria) this;
        }

        public Criteria andNorigpriceGreaterThan(BigDecimal value) {
            addCriterion("nOrigPrice >", value, "norigprice");
            return (Criteria) this;
        }

        public Criteria andNorigpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("nOrigPrice >=", value, "norigprice");
            return (Criteria) this;
        }

        public Criteria andNorigpriceLessThan(BigDecimal value) {
            addCriterion("nOrigPrice <", value, "norigprice");
            return (Criteria) this;
        }

        public Criteria andNorigpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("nOrigPrice <=", value, "norigprice");
            return (Criteria) this;
        }

        public Criteria andNorigpriceIn(List<BigDecimal> values) {
            addCriterion("nOrigPrice in", values, "norigprice");
            return (Criteria) this;
        }

        public Criteria andNorigpriceNotIn(List<BigDecimal> values) {
            addCriterion("nOrigPrice not in", values, "norigprice");
            return (Criteria) this;
        }

        public Criteria andNorigpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nOrigPrice between", value1, value2, "norigprice");
            return (Criteria) this;
        }

        public Criteria andNorigpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nOrigPrice not between", value1, value2, "norigprice");
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

        public Criteria andNpriceIsNull() {
            addCriterion("nPrice is null");
            return (Criteria) this;
        }

        public Criteria andNpriceIsNotNull() {
            addCriterion("nPrice is not null");
            return (Criteria) this;
        }

        public Criteria andNpriceEqualTo(BigDecimal value) {
            addCriterion("nPrice =", value, "nprice");
            return (Criteria) this;
        }

        public Criteria andNpriceNotEqualTo(BigDecimal value) {
            addCriterion("nPrice <>", value, "nprice");
            return (Criteria) this;
        }

        public Criteria andNpriceGreaterThan(BigDecimal value) {
            addCriterion("nPrice >", value, "nprice");
            return (Criteria) this;
        }

        public Criteria andNpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("nPrice >=", value, "nprice");
            return (Criteria) this;
        }

        public Criteria andNpriceLessThan(BigDecimal value) {
            addCriterion("nPrice <", value, "nprice");
            return (Criteria) this;
        }

        public Criteria andNpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("nPrice <=", value, "nprice");
            return (Criteria) this;
        }

        public Criteria andNpriceIn(List<BigDecimal> values) {
            addCriterion("nPrice in", values, "nprice");
            return (Criteria) this;
        }

        public Criteria andNpriceNotIn(List<BigDecimal> values) {
            addCriterion("nPrice not in", values, "nprice");
            return (Criteria) this;
        }

        public Criteria andNpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nPrice between", value1, value2, "nprice");
            return (Criteria) this;
        }

        public Criteria andNpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nPrice not between", value1, value2, "nprice");
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