package com.htsat.cart.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

        public Criteria andNdisplaypriceIsNull() {
            addCriterion("nDisplayPrice is null");
            return (Criteria) this;
        }

        public Criteria andNdisplaypriceIsNotNull() {
            addCriterion("nDisplayPrice is not null");
            return (Criteria) this;
        }

        public Criteria andNdisplaypriceEqualTo(BigDecimal value) {
            addCriterion("nDisplayPrice =", value, "ndisplayprice");
            return (Criteria) this;
        }

        public Criteria andNdisplaypriceNotEqualTo(BigDecimal value) {
            addCriterion("nDisplayPrice <>", value, "ndisplayprice");
            return (Criteria) this;
        }

        public Criteria andNdisplaypriceGreaterThan(BigDecimal value) {
            addCriterion("nDisplayPrice >", value, "ndisplayprice");
            return (Criteria) this;
        }

        public Criteria andNdisplaypriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("nDisplayPrice >=", value, "ndisplayprice");
            return (Criteria) this;
        }

        public Criteria andNdisplaypriceLessThan(BigDecimal value) {
            addCriterion("nDisplayPrice <", value, "ndisplayprice");
            return (Criteria) this;
        }

        public Criteria andNdisplaypriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("nDisplayPrice <=", value, "ndisplayprice");
            return (Criteria) this;
        }

        public Criteria andNdisplaypriceIn(List<BigDecimal> values) {
            addCriterion("nDisplayPrice in", values, "ndisplayprice");
            return (Criteria) this;
        }

        public Criteria andNdisplaypriceNotIn(List<BigDecimal> values) {
            addCriterion("nDisplayPrice not in", values, "ndisplayprice");
            return (Criteria) this;
        }

        public Criteria andNdisplaypriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nDisplayPrice between", value1, value2, "ndisplayprice");
            return (Criteria) this;
        }

        public Criteria andNdisplaypriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nDisplayPrice not between", value1, value2, "ndisplayprice");
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