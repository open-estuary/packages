package com.htsat.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class REcOrderinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcOrderinfoExample() {
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

        public Criteria andSorderidIsNull() {
            addCriterion("sOrderID is null");
            return (Criteria) this;
        }

        public Criteria andSorderidIsNotNull() {
            addCriterion("sOrderID is not null");
            return (Criteria) this;
        }

        public Criteria andSorderidEqualTo(String value) {
            addCriterion("sOrderID =", value, "sorderid");
            return (Criteria) this;
        }

        public Criteria andSorderidNotEqualTo(String value) {
            addCriterion("sOrderID <>", value, "sorderid");
            return (Criteria) this;
        }

        public Criteria andSorderidGreaterThan(String value) {
            addCriterion("sOrderID >", value, "sorderid");
            return (Criteria) this;
        }

        public Criteria andSorderidGreaterThanOrEqualTo(String value) {
            addCriterion("sOrderID >=", value, "sorderid");
            return (Criteria) this;
        }

        public Criteria andSorderidLessThan(String value) {
            addCriterion("sOrderID <", value, "sorderid");
            return (Criteria) this;
        }

        public Criteria andSorderidLessThanOrEqualTo(String value) {
            addCriterion("sOrderID <=", value, "sorderid");
            return (Criteria) this;
        }

        public Criteria andSorderidLike(String value) {
            addCriterion("sOrderID like", value, "sorderid");
            return (Criteria) this;
        }

        public Criteria andSorderidNotLike(String value) {
            addCriterion("sOrderID not like", value, "sorderid");
            return (Criteria) this;
        }

        public Criteria andSorderidIn(List<String> values) {
            addCriterion("sOrderID in", values, "sorderid");
            return (Criteria) this;
        }

        public Criteria andSorderidNotIn(List<String> values) {
            addCriterion("sOrderID not in", values, "sorderid");
            return (Criteria) this;
        }

        public Criteria andSorderidBetween(String value1, String value2) {
            addCriterion("sOrderID between", value1, value2, "sorderid");
            return (Criteria) this;
        }

        public Criteria andSorderidNotBetween(String value1, String value2) {
            addCriterion("sOrderID not between", value1, value2, "sorderid");
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

        public Criteria andSparentorderidIsNull() {
            addCriterion("sParentOrderID is null");
            return (Criteria) this;
        }

        public Criteria andSparentorderidIsNotNull() {
            addCriterion("sParentOrderID is not null");
            return (Criteria) this;
        }

        public Criteria andSparentorderidEqualTo(String value) {
            addCriterion("sParentOrderID =", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidNotEqualTo(String value) {
            addCriterion("sParentOrderID <>", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidGreaterThan(String value) {
            addCriterion("sParentOrderID >", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidGreaterThanOrEqualTo(String value) {
            addCriterion("sParentOrderID >=", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidLessThan(String value) {
            addCriterion("sParentOrderID <", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidLessThanOrEqualTo(String value) {
            addCriterion("sParentOrderID <=", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidLike(String value) {
            addCriterion("sParentOrderID like", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidNotLike(String value) {
            addCriterion("sParentOrderID not like", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidIn(List<String> values) {
            addCriterion("sParentOrderID in", values, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidNotIn(List<String> values) {
            addCriterion("sParentOrderID not in", values, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidBetween(String value1, String value2) {
            addCriterion("sParentOrderID between", value1, value2, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidNotBetween(String value1, String value2) {
            addCriterion("sParentOrderID not between", value1, value2, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodIsNull() {
            addCriterion("cPaymentMethod is null");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodIsNotNull() {
            addCriterion("cPaymentMethod is not null");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodEqualTo(String value) {
            addCriterion("cPaymentMethod =", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodNotEqualTo(String value) {
            addCriterion("cPaymentMethod <>", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodGreaterThan(String value) {
            addCriterion("cPaymentMethod >", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodGreaterThanOrEqualTo(String value) {
            addCriterion("cPaymentMethod >=", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodLessThan(String value) {
            addCriterion("cPaymentMethod <", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodLessThanOrEqualTo(String value) {
            addCriterion("cPaymentMethod <=", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodLike(String value) {
            addCriterion("cPaymentMethod like", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodNotLike(String value) {
            addCriterion("cPaymentMethod not like", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodIn(List<String> values) {
            addCriterion("cPaymentMethod in", values, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodNotIn(List<String> values) {
            addCriterion("cPaymentMethod not in", values, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodBetween(String value1, String value2) {
            addCriterion("cPaymentMethod between", value1, value2, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodNotBetween(String value1, String value2) {
            addCriterion("cPaymentMethod not between", value1, value2, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleIsNull() {
            addCriterion("sPaymentMethodTitle is null");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleIsNotNull() {
            addCriterion("sPaymentMethodTitle is not null");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleEqualTo(String value) {
            addCriterion("sPaymentMethodTitle =", value, "spaymentmethodtitle");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleNotEqualTo(String value) {
            addCriterion("sPaymentMethodTitle <>", value, "spaymentmethodtitle");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleGreaterThan(String value) {
            addCriterion("sPaymentMethodTitle >", value, "spaymentmethodtitle");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleGreaterThanOrEqualTo(String value) {
            addCriterion("sPaymentMethodTitle >=", value, "spaymentmethodtitle");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleLessThan(String value) {
            addCriterion("sPaymentMethodTitle <", value, "spaymentmethodtitle");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleLessThanOrEqualTo(String value) {
            addCriterion("sPaymentMethodTitle <=", value, "spaymentmethodtitle");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleLike(String value) {
            addCriterion("sPaymentMethodTitle like", value, "spaymentmethodtitle");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleNotLike(String value) {
            addCriterion("sPaymentMethodTitle not like", value, "spaymentmethodtitle");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleIn(List<String> values) {
            addCriterion("sPaymentMethodTitle in", values, "spaymentmethodtitle");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleNotIn(List<String> values) {
            addCriterion("sPaymentMethodTitle not in", values, "spaymentmethodtitle");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleBetween(String value1, String value2) {
            addCriterion("sPaymentMethodTitle between", value1, value2, "spaymentmethodtitle");
            return (Criteria) this;
        }

        public Criteria andSpaymentmethodtitleNotBetween(String value1, String value2) {
            addCriterion("sPaymentMethodTitle not between", value1, value2, "spaymentmethodtitle");
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

        public Criteria andSversionIsNull() {
            addCriterion("sVersion is null");
            return (Criteria) this;
        }

        public Criteria andSversionIsNotNull() {
            addCriterion("sVersion is not null");
            return (Criteria) this;
        }

        public Criteria andSversionEqualTo(String value) {
            addCriterion("sVersion =", value, "sversion");
            return (Criteria) this;
        }

        public Criteria andSversionNotEqualTo(String value) {
            addCriterion("sVersion <>", value, "sversion");
            return (Criteria) this;
        }

        public Criteria andSversionGreaterThan(String value) {
            addCriterion("sVersion >", value, "sversion");
            return (Criteria) this;
        }

        public Criteria andSversionGreaterThanOrEqualTo(String value) {
            addCriterion("sVersion >=", value, "sversion");
            return (Criteria) this;
        }

        public Criteria andSversionLessThan(String value) {
            addCriterion("sVersion <", value, "sversion");
            return (Criteria) this;
        }

        public Criteria andSversionLessThanOrEqualTo(String value) {
            addCriterion("sVersion <=", value, "sversion");
            return (Criteria) this;
        }

        public Criteria andSversionLike(String value) {
            addCriterion("sVersion like", value, "sversion");
            return (Criteria) this;
        }

        public Criteria andSversionNotLike(String value) {
            addCriterion("sVersion not like", value, "sversion");
            return (Criteria) this;
        }

        public Criteria andSversionIn(List<String> values) {
            addCriterion("sVersion in", values, "sversion");
            return (Criteria) this;
        }

        public Criteria andSversionNotIn(List<String> values) {
            addCriterion("sVersion not in", values, "sversion");
            return (Criteria) this;
        }

        public Criteria andSversionBetween(String value1, String value2) {
            addCriterion("sVersion between", value1, value2, "sversion");
            return (Criteria) this;
        }

        public Criteria andSversionNotBetween(String value1, String value2) {
            addCriterion("sVersion not between", value1, value2, "sversion");
            return (Criteria) this;
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

        public Criteria andCstatusIsNull() {
            addCriterion("cStatus is null");
            return (Criteria) this;
        }

        public Criteria andCstatusIsNotNull() {
            addCriterion("cStatus is not null");
            return (Criteria) this;
        }

        public Criteria andCstatusEqualTo(String value) {
            addCriterion("cStatus =", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusNotEqualTo(String value) {
            addCriterion("cStatus <>", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusGreaterThan(String value) {
            addCriterion("cStatus >", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusGreaterThanOrEqualTo(String value) {
            addCriterion("cStatus >=", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusLessThan(String value) {
            addCriterion("cStatus <", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusLessThanOrEqualTo(String value) {
            addCriterion("cStatus <=", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusLike(String value) {
            addCriterion("cStatus like", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusNotLike(String value) {
            addCriterion("cStatus not like", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusIn(List<String> values) {
            addCriterion("cStatus in", values, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusNotIn(List<String> values) {
            addCriterion("cStatus not in", values, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusBetween(String value1, String value2) {
            addCriterion("cStatus between", value1, value2, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusNotBetween(String value1, String value2) {
            addCriterion("cStatus not between", value1, value2, "cstatus");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedIsNull() {
            addCriterion("sDate_created is null");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedIsNotNull() {
            addCriterion("sDate_created is not null");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedEqualTo(Date value) {
            addCriterion("sDate_created =", value, "sdateCreated");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedNotEqualTo(Date value) {
            addCriterion("sDate_created <>", value, "sdateCreated");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedGreaterThan(Date value) {
            addCriterion("sDate_created >", value, "sdateCreated");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("sDate_created >=", value, "sdateCreated");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedLessThan(Date value) {
            addCriterion("sDate_created <", value, "sdateCreated");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedLessThanOrEqualTo(Date value) {
            addCriterion("sDate_created <=", value, "sdateCreated");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedIn(List<Date> values) {
            addCriterion("sDate_created in", values, "sdateCreated");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedNotIn(List<Date> values) {
            addCriterion("sDate_created not in", values, "sdateCreated");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedBetween(Date value1, Date value2) {
            addCriterion("sDate_created between", value1, value2, "sdateCreated");
            return (Criteria) this;
        }

        public Criteria andSdateCreatedNotBetween(Date value1, Date value2) {
            addCriterion("sDate_created not between", value1, value2, "sdateCreated");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedIsNull() {
            addCriterion("sDate_Modified is null");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedIsNotNull() {
            addCriterion("sDate_Modified is not null");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedEqualTo(Date value) {
            addCriterion("sDate_Modified =", value, "sdateModified");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedNotEqualTo(Date value) {
            addCriterion("sDate_Modified <>", value, "sdateModified");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedGreaterThan(Date value) {
            addCriterion("sDate_Modified >", value, "sdateModified");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("sDate_Modified >=", value, "sdateModified");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedLessThan(Date value) {
            addCriterion("sDate_Modified <", value, "sdateModified");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedLessThanOrEqualTo(Date value) {
            addCriterion("sDate_Modified <=", value, "sdateModified");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedIn(List<Date> values) {
            addCriterion("sDate_Modified in", values, "sdateModified");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedNotIn(List<Date> values) {
            addCriterion("sDate_Modified not in", values, "sdateModified");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedBetween(Date value1, Date value2) {
            addCriterion("sDate_Modified between", value1, value2, "sdateModified");
            return (Criteria) this;
        }

        public Criteria andSdateModifiedNotBetween(Date value1, Date value2) {
            addCriterion("sDate_Modified not between", value1, value2, "sdateModified");
            return (Criteria) this;
        }

        public Criteria andSdatePaidIsNull() {
            addCriterion("sDate_Paid is null");
            return (Criteria) this;
        }

        public Criteria andSdatePaidIsNotNull() {
            addCriterion("sDate_Paid is not null");
            return (Criteria) this;
        }

        public Criteria andSdatePaidEqualTo(Date value) {
            addCriterion("sDate_Paid =", value, "sdatePaid");
            return (Criteria) this;
        }

        public Criteria andSdatePaidNotEqualTo(Date value) {
            addCriterion("sDate_Paid <>", value, "sdatePaid");
            return (Criteria) this;
        }

        public Criteria andSdatePaidGreaterThan(Date value) {
            addCriterion("sDate_Paid >", value, "sdatePaid");
            return (Criteria) this;
        }

        public Criteria andSdatePaidGreaterThanOrEqualTo(Date value) {
            addCriterion("sDate_Paid >=", value, "sdatePaid");
            return (Criteria) this;
        }

        public Criteria andSdatePaidLessThan(Date value) {
            addCriterion("sDate_Paid <", value, "sdatePaid");
            return (Criteria) this;
        }

        public Criteria andSdatePaidLessThanOrEqualTo(Date value) {
            addCriterion("sDate_Paid <=", value, "sdatePaid");
            return (Criteria) this;
        }

        public Criteria andSdatePaidIn(List<Date> values) {
            addCriterion("sDate_Paid in", values, "sdatePaid");
            return (Criteria) this;
        }

        public Criteria andSdatePaidNotIn(List<Date> values) {
            addCriterion("sDate_Paid not in", values, "sdatePaid");
            return (Criteria) this;
        }

        public Criteria andSdatePaidBetween(Date value1, Date value2) {
            addCriterion("sDate_Paid between", value1, value2, "sdatePaid");
            return (Criteria) this;
        }

        public Criteria andSdatePaidNotBetween(Date value1, Date value2) {
            addCriterion("sDate_Paid not between", value1, value2, "sdatePaid");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedIsNull() {
            addCriterion("sDate_Completed is null");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedIsNotNull() {
            addCriterion("sDate_Completed is not null");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedEqualTo(Date value) {
            addCriterion("sDate_Completed =", value, "sdateCompleted");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedNotEqualTo(Date value) {
            addCriterion("sDate_Completed <>", value, "sdateCompleted");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedGreaterThan(Date value) {
            addCriterion("sDate_Completed >", value, "sdateCompleted");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedGreaterThanOrEqualTo(Date value) {
            addCriterion("sDate_Completed >=", value, "sdateCompleted");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedLessThan(Date value) {
            addCriterion("sDate_Completed <", value, "sdateCompleted");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedLessThanOrEqualTo(Date value) {
            addCriterion("sDate_Completed <=", value, "sdateCompleted");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedIn(List<Date> values) {
            addCriterion("sDate_Completed in", values, "sdateCompleted");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedNotIn(List<Date> values) {
            addCriterion("sDate_Completed not in", values, "sdateCompleted");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedBetween(Date value1, Date value2) {
            addCriterion("sDate_Completed between", value1, value2, "sdateCompleted");
            return (Criteria) this;
        }

        public Criteria andSdateCompletedNotBetween(Date value1, Date value2) {
            addCriterion("sDate_Completed not between", value1, value2, "sdateCompleted");
            return (Criteria) this;
        }

        public Criteria andScustomermarkIsNull() {
            addCriterion("sCustomerMark is null");
            return (Criteria) this;
        }

        public Criteria andScustomermarkIsNotNull() {
            addCriterion("sCustomerMark is not null");
            return (Criteria) this;
        }

        public Criteria andScustomermarkEqualTo(String value) {
            addCriterion("sCustomerMark =", value, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andScustomermarkNotEqualTo(String value) {
            addCriterion("sCustomerMark <>", value, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andScustomermarkGreaterThan(String value) {
            addCriterion("sCustomerMark >", value, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andScustomermarkGreaterThanOrEqualTo(String value) {
            addCriterion("sCustomerMark >=", value, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andScustomermarkLessThan(String value) {
            addCriterion("sCustomerMark <", value, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andScustomermarkLessThanOrEqualTo(String value) {
            addCriterion("sCustomerMark <=", value, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andScustomermarkLike(String value) {
            addCriterion("sCustomerMark like", value, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andScustomermarkNotLike(String value) {
            addCriterion("sCustomerMark not like", value, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andScustomermarkIn(List<String> values) {
            addCriterion("sCustomerMark in", values, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andScustomermarkNotIn(List<String> values) {
            addCriterion("sCustomerMark not in", values, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andScustomermarkBetween(String value1, String value2) {
            addCriterion("sCustomerMark between", value1, value2, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andScustomermarkNotBetween(String value1, String value2) {
            addCriterion("sCustomerMark not between", value1, value2, "scustomermark");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidIsNull() {
            addCriterion("sDeliveryID is null");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidIsNotNull() {
            addCriterion("sDeliveryID is not null");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidEqualTo(String value) {
            addCriterion("sDeliveryID =", value, "sdeliveryid");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidNotEqualTo(String value) {
            addCriterion("sDeliveryID <>", value, "sdeliveryid");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidGreaterThan(String value) {
            addCriterion("sDeliveryID >", value, "sdeliveryid");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidGreaterThanOrEqualTo(String value) {
            addCriterion("sDeliveryID >=", value, "sdeliveryid");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidLessThan(String value) {
            addCriterion("sDeliveryID <", value, "sdeliveryid");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidLessThanOrEqualTo(String value) {
            addCriterion("sDeliveryID <=", value, "sdeliveryid");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidLike(String value) {
            addCriterion("sDeliveryID like", value, "sdeliveryid");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidNotLike(String value) {
            addCriterion("sDeliveryID not like", value, "sdeliveryid");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidIn(List<String> values) {
            addCriterion("sDeliveryID in", values, "sdeliveryid");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidNotIn(List<String> values) {
            addCriterion("sDeliveryID not in", values, "sdeliveryid");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidBetween(String value1, String value2) {
            addCriterion("sDeliveryID between", value1, value2, "sdeliveryid");
            return (Criteria) this;
        }

        public Criteria andSdeliveryidNotBetween(String value1, String value2) {
            addCriterion("sDeliveryID not between", value1, value2, "sdeliveryid");
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