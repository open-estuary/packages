package com.htsat.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class REcSkuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcSkuExample() {
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

        public Criteria andNskuidIsNull() {
            addCriterion("nSKUID is null");
            return (Criteria) this;
        }

        public Criteria andNskuidIsNotNull() {
            addCriterion("nSKUID is not null");
            return (Criteria) this;
        }

        public Criteria andNskuidEqualTo(Integer value) {
            addCriterion("nSKUID =", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidNotEqualTo(Integer value) {
            addCriterion("nSKUID <>", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidGreaterThan(Integer value) {
            addCriterion("nSKUID >", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("nSKUID >=", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidLessThan(Integer value) {
            addCriterion("nSKUID <", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidLessThanOrEqualTo(Integer value) {
            addCriterion("nSKUID <=", value, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidIn(List<Integer> values) {
            addCriterion("nSKUID in", values, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidNotIn(List<Integer> values) {
            addCriterion("nSKUID not in", values, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidBetween(Integer value1, Integer value2) {
            addCriterion("nSKUID between", value1, value2, "nskuid");
            return (Criteria) this;
        }

        public Criteria andNskuidNotBetween(Integer value1, Integer value2) {
            addCriterion("nSKUID not between", value1, value2, "nskuid");
            return (Criteria) this;
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

        public Criteria andNcolorIsNull() {
            addCriterion("nColor is null");
            return (Criteria) this;
        }

        public Criteria andNcolorIsNotNull() {
            addCriterion("nColor is not null");
            return (Criteria) this;
        }

        public Criteria andNcolorEqualTo(String value) {
            addCriterion("nColor =", value, "ncolor");
            return (Criteria) this;
        }

        public Criteria andNcolorNotEqualTo(String value) {
            addCriterion("nColor <>", value, "ncolor");
            return (Criteria) this;
        }

        public Criteria andNcolorGreaterThan(String value) {
            addCriterion("nColor >", value, "ncolor");
            return (Criteria) this;
        }

        public Criteria andNcolorGreaterThanOrEqualTo(String value) {
            addCriterion("nColor >=", value, "ncolor");
            return (Criteria) this;
        }

        public Criteria andNcolorLessThan(String value) {
            addCriterion("nColor <", value, "ncolor");
            return (Criteria) this;
        }

        public Criteria andNcolorLessThanOrEqualTo(String value) {
            addCriterion("nColor <=", value, "ncolor");
            return (Criteria) this;
        }

        public Criteria andNcolorLike(String value) {
            addCriterion("nColor like", value, "ncolor");
            return (Criteria) this;
        }

        public Criteria andNcolorNotLike(String value) {
            addCriterion("nColor not like", value, "ncolor");
            return (Criteria) this;
        }

        public Criteria andNcolorIn(List<String> values) {
            addCriterion("nColor in", values, "ncolor");
            return (Criteria) this;
        }

        public Criteria andNcolorNotIn(List<String> values) {
            addCriterion("nColor not in", values, "ncolor");
            return (Criteria) this;
        }

        public Criteria andNcolorBetween(String value1, String value2) {
            addCriterion("nColor between", value1, value2, "ncolor");
            return (Criteria) this;
        }

        public Criteria andNcolorNotBetween(String value1, String value2) {
            addCriterion("nColor not between", value1, value2, "ncolor");
            return (Criteria) this;
        }

        public Criteria andSsizeIsNull() {
            addCriterion("sSize is null");
            return (Criteria) this;
        }

        public Criteria andSsizeIsNotNull() {
            addCriterion("sSize is not null");
            return (Criteria) this;
        }

        public Criteria andSsizeEqualTo(String value) {
            addCriterion("sSize =", value, "ssize");
            return (Criteria) this;
        }

        public Criteria andSsizeNotEqualTo(String value) {
            addCriterion("sSize <>", value, "ssize");
            return (Criteria) this;
        }

        public Criteria andSsizeGreaterThan(String value) {
            addCriterion("sSize >", value, "ssize");
            return (Criteria) this;
        }

        public Criteria andSsizeGreaterThanOrEqualTo(String value) {
            addCriterion("sSize >=", value, "ssize");
            return (Criteria) this;
        }

        public Criteria andSsizeLessThan(String value) {
            addCriterion("sSize <", value, "ssize");
            return (Criteria) this;
        }

        public Criteria andSsizeLessThanOrEqualTo(String value) {
            addCriterion("sSize <=", value, "ssize");
            return (Criteria) this;
        }

        public Criteria andSsizeLike(String value) {
            addCriterion("sSize like", value, "ssize");
            return (Criteria) this;
        }

        public Criteria andSsizeNotLike(String value) {
            addCriterion("sSize not like", value, "ssize");
            return (Criteria) this;
        }

        public Criteria andSsizeIn(List<String> values) {
            addCriterion("sSize in", values, "ssize");
            return (Criteria) this;
        }

        public Criteria andSsizeNotIn(List<String> values) {
            addCriterion("sSize not in", values, "ssize");
            return (Criteria) this;
        }

        public Criteria andSsizeBetween(String value1, String value2) {
            addCriterion("sSize between", value1, value2, "ssize");
            return (Criteria) this;
        }

        public Criteria andSsizeNotBetween(String value1, String value2) {
            addCriterion("sSize not between", value1, value2, "ssize");
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

        public Criteria andNdisplayPriceIsNull() {
            addCriterion("nDisplay_price is null");
            return (Criteria) this;
        }

        public Criteria andNdisplayPriceIsNotNull() {
            addCriterion("nDisplay_price is not null");
            return (Criteria) this;
        }

        public Criteria andNdisplayPriceEqualTo(BigDecimal value) {
            addCriterion("nDisplay_price =", value, "ndisplayPrice");
            return (Criteria) this;
        }

        public Criteria andNdisplayPriceNotEqualTo(BigDecimal value) {
            addCriterion("nDisplay_price <>", value, "ndisplayPrice");
            return (Criteria) this;
        }

        public Criteria andNdisplayPriceGreaterThan(BigDecimal value) {
            addCriterion("nDisplay_price >", value, "ndisplayPrice");
            return (Criteria) this;
        }

        public Criteria andNdisplayPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("nDisplay_price >=", value, "ndisplayPrice");
            return (Criteria) this;
        }

        public Criteria andNdisplayPriceLessThan(BigDecimal value) {
            addCriterion("nDisplay_price <", value, "ndisplayPrice");
            return (Criteria) this;
        }

        public Criteria andNdisplayPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("nDisplay_price <=", value, "ndisplayPrice");
            return (Criteria) this;
        }

        public Criteria andNdisplayPriceIn(List<BigDecimal> values) {
            addCriterion("nDisplay_price in", values, "ndisplayPrice");
            return (Criteria) this;
        }

        public Criteria andNdisplayPriceNotIn(List<BigDecimal> values) {
            addCriterion("nDisplay_price not in", values, "ndisplayPrice");
            return (Criteria) this;
        }

        public Criteria andNdisplayPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nDisplay_price between", value1, value2, "ndisplayPrice");
            return (Criteria) this;
        }

        public Criteria andNdisplayPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nDisplay_price not between", value1, value2, "ndisplayPrice");
            return (Criteria) this;
        }

        public Criteria andNinventoryIsNull() {
            addCriterion("nInventory is null");
            return (Criteria) this;
        }

        public Criteria andNinventoryIsNotNull() {
            addCriterion("nInventory is not null");
            return (Criteria) this;
        }

        public Criteria andNinventoryEqualTo(Integer value) {
            addCriterion("nInventory =", value, "ninventory");
            return (Criteria) this;
        }

        public Criteria andNinventoryNotEqualTo(Integer value) {
            addCriterion("nInventory <>", value, "ninventory");
            return (Criteria) this;
        }

        public Criteria andNinventoryGreaterThan(Integer value) {
            addCriterion("nInventory >", value, "ninventory");
            return (Criteria) this;
        }

        public Criteria andNinventoryGreaterThanOrEqualTo(Integer value) {
            addCriterion("nInventory >=", value, "ninventory");
            return (Criteria) this;
        }

        public Criteria andNinventoryLessThan(Integer value) {
            addCriterion("nInventory <", value, "ninventory");
            return (Criteria) this;
        }

        public Criteria andNinventoryLessThanOrEqualTo(Integer value) {
            addCriterion("nInventory <=", value, "ninventory");
            return (Criteria) this;
        }

        public Criteria andNinventoryIn(List<Integer> values) {
            addCriterion("nInventory in", values, "ninventory");
            return (Criteria) this;
        }

        public Criteria andNinventoryNotIn(List<Integer> values) {
            addCriterion("nInventory not in", values, "ninventory");
            return (Criteria) this;
        }

        public Criteria andNinventoryBetween(Integer value1, Integer value2) {
            addCriterion("nInventory between", value1, value2, "ninventory");
            return (Criteria) this;
        }

        public Criteria andNinventoryNotBetween(Integer value1, Integer value2) {
            addCriterion("nInventory not between", value1, value2, "ninventory");
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

        public Criteria andNdiscountEqualTo(Float value) {
            addCriterion("nDiscount =", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountNotEqualTo(Float value) {
            addCriterion("nDiscount <>", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountGreaterThan(Float value) {
            addCriterion("nDiscount >", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountGreaterThanOrEqualTo(Float value) {
            addCriterion("nDiscount >=", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountLessThan(Float value) {
            addCriterion("nDiscount <", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountLessThanOrEqualTo(Float value) {
            addCriterion("nDiscount <=", value, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountIn(List<Float> values) {
            addCriterion("nDiscount in", values, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountNotIn(List<Float> values) {
            addCriterion("nDiscount not in", values, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountBetween(Float value1, Float value2) {
            addCriterion("nDiscount between", value1, value2, "ndiscount");
            return (Criteria) this;
        }

        public Criteria andNdiscountNotBetween(Float value1, Float value2) {
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