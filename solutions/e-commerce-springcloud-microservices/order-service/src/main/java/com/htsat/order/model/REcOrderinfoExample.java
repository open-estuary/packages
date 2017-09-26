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

        public Criteria andSparentorderidIsNull() {
            addCriterion("sParentOrderID is null");
            return (Criteria) this;
        }

        public Criteria andSparentorderidIsNotNull() {
            addCriterion("sParentOrderID is not null");
            return (Criteria) this;
        }

        public Criteria andSparentorderidEqualTo(Long value) {
            addCriterion("sParentOrderID =", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidNotEqualTo(Long value) {
            addCriterion("sParentOrderID <>", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidGreaterThan(Long value) {
            addCriterion("sParentOrderID >", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidGreaterThanOrEqualTo(Long value) {
            addCriterion("sParentOrderID >=", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidLessThan(Long value) {
            addCriterion("sParentOrderID <", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidLessThanOrEqualTo(Long value) {
            addCriterion("sParentOrderID <=", value, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidIn(List<Long> values) {
            addCriterion("sParentOrderID in", values, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidNotIn(List<Long> values) {
            addCriterion("sParentOrderID not in", values, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidBetween(Long value1, Long value2) {
            addCriterion("sParentOrderID between", value1, value2, "sparentorderid");
            return (Criteria) this;
        }

        public Criteria andSparentorderidNotBetween(Long value1, Long value2) {
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

        public Criteria andCpaymentmethodEqualTo(Short value) {
            addCriterion("cPaymentMethod =", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodNotEqualTo(Short value) {
            addCriterion("cPaymentMethod <>", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodGreaterThan(Short value) {
            addCriterion("cPaymentMethod >", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodGreaterThanOrEqualTo(Short value) {
            addCriterion("cPaymentMethod >=", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodLessThan(Short value) {
            addCriterion("cPaymentMethod <", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodLessThanOrEqualTo(Short value) {
            addCriterion("cPaymentMethod <=", value, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodIn(List<Short> values) {
            addCriterion("cPaymentMethod in", values, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodNotIn(List<Short> values) {
            addCriterion("cPaymentMethod not in", values, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodBetween(Short value1, Short value2) {
            addCriterion("cPaymentMethod between", value1, value2, "cpaymentmethod");
            return (Criteria) this;
        }

        public Criteria andCpaymentmethodNotBetween(Short value1, Short value2) {
            addCriterion("cPaymentMethod not between", value1, value2, "cpaymentmethod");
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

        public Criteria andCstatusIsNull() {
            addCriterion("cStatus is null");
            return (Criteria) this;
        }

        public Criteria andCstatusIsNotNull() {
            addCriterion("cStatus is not null");
            return (Criteria) this;
        }

        public Criteria andCstatusEqualTo(Short value) {
            addCriterion("cStatus =", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusNotEqualTo(Short value) {
            addCriterion("cStatus <>", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusGreaterThan(Short value) {
            addCriterion("cStatus >", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusGreaterThanOrEqualTo(Short value) {
            addCriterion("cStatus >=", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusLessThan(Short value) {
            addCriterion("cStatus <", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusLessThanOrEqualTo(Short value) {
            addCriterion("cStatus <=", value, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusIn(List<Short> values) {
            addCriterion("cStatus in", values, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusNotIn(List<Short> values) {
            addCriterion("cStatus not in", values, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusBetween(Short value1, Short value2) {
            addCriterion("cStatus between", value1, value2, "cstatus");
            return (Criteria) this;
        }

        public Criteria andCstatusNotBetween(Short value1, Short value2) {
            addCriterion("cStatus not between", value1, value2, "cstatus");
            return (Criteria) this;
        }

        public Criteria andScreatetimeIsNull() {
            addCriterion("sCreateTime is null");
            return (Criteria) this;
        }

        public Criteria andScreatetimeIsNotNull() {
            addCriterion("sCreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andScreatetimeEqualTo(Date value) {
            addCriterion("sCreateTime =", value, "screatetime");
            return (Criteria) this;
        }

        public Criteria andScreatetimeNotEqualTo(Date value) {
            addCriterion("sCreateTime <>", value, "screatetime");
            return (Criteria) this;
        }

        public Criteria andScreatetimeGreaterThan(Date value) {
            addCriterion("sCreateTime >", value, "screatetime");
            return (Criteria) this;
        }

        public Criteria andScreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sCreateTime >=", value, "screatetime");
            return (Criteria) this;
        }

        public Criteria andScreatetimeLessThan(Date value) {
            addCriterion("sCreateTime <", value, "screatetime");
            return (Criteria) this;
        }

        public Criteria andScreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("sCreateTime <=", value, "screatetime");
            return (Criteria) this;
        }

        public Criteria andScreatetimeIn(List<Date> values) {
            addCriterion("sCreateTime in", values, "screatetime");
            return (Criteria) this;
        }

        public Criteria andScreatetimeNotIn(List<Date> values) {
            addCriterion("sCreateTime not in", values, "screatetime");
            return (Criteria) this;
        }

        public Criteria andScreatetimeBetween(Date value1, Date value2) {
            addCriterion("sCreateTime between", value1, value2, "screatetime");
            return (Criteria) this;
        }

        public Criteria andScreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("sCreateTime not between", value1, value2, "screatetime");
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

        public Criteria andScompletedtimeIsNull() {
            addCriterion("sCompletedTime is null");
            return (Criteria) this;
        }

        public Criteria andScompletedtimeIsNotNull() {
            addCriterion("sCompletedTime is not null");
            return (Criteria) this;
        }

        public Criteria andScompletedtimeEqualTo(Date value) {
            addCriterion("sCompletedTime =", value, "scompletedtime");
            return (Criteria) this;
        }

        public Criteria andScompletedtimeNotEqualTo(Date value) {
            addCriterion("sCompletedTime <>", value, "scompletedtime");
            return (Criteria) this;
        }

        public Criteria andScompletedtimeGreaterThan(Date value) {
            addCriterion("sCompletedTime >", value, "scompletedtime");
            return (Criteria) this;
        }

        public Criteria andScompletedtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sCompletedTime >=", value, "scompletedtime");
            return (Criteria) this;
        }

        public Criteria andScompletedtimeLessThan(Date value) {
            addCriterion("sCompletedTime <", value, "scompletedtime");
            return (Criteria) this;
        }

        public Criteria andScompletedtimeLessThanOrEqualTo(Date value) {
            addCriterion("sCompletedTime <=", value, "scompletedtime");
            return (Criteria) this;
        }

        public Criteria andScompletedtimeIn(List<Date> values) {
            addCriterion("sCompletedTime in", values, "scompletedtime");
            return (Criteria) this;
        }

        public Criteria andScompletedtimeNotIn(List<Date> values) {
            addCriterion("sCompletedTime not in", values, "scompletedtime");
            return (Criteria) this;
        }

        public Criteria andScompletedtimeBetween(Date value1, Date value2) {
            addCriterion("sCompletedTime between", value1, value2, "scompletedtime");
            return (Criteria) this;
        }

        public Criteria andScompletedtimeNotBetween(Date value1, Date value2) {
            addCriterion("sCompletedTime not between", value1, value2, "scompletedtime");
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

        public Criteria andNdeliveryidIsNull() {
            addCriterion("nDeliveryID is null");
            return (Criteria) this;
        }

        public Criteria andNdeliveryidIsNotNull() {
            addCriterion("nDeliveryID is not null");
            return (Criteria) this;
        }

        public Criteria andNdeliveryidEqualTo(Long value) {
            addCriterion("nDeliveryID =", value, "ndeliveryid");
            return (Criteria) this;
        }

        public Criteria andNdeliveryidNotEqualTo(Long value) {
            addCriterion("nDeliveryID <>", value, "ndeliveryid");
            return (Criteria) this;
        }

        public Criteria andNdeliveryidGreaterThan(Long value) {
            addCriterion("nDeliveryID >", value, "ndeliveryid");
            return (Criteria) this;
        }

        public Criteria andNdeliveryidGreaterThanOrEqualTo(Long value) {
            addCriterion("nDeliveryID >=", value, "ndeliveryid");
            return (Criteria) this;
        }

        public Criteria andNdeliveryidLessThan(Long value) {
            addCriterion("nDeliveryID <", value, "ndeliveryid");
            return (Criteria) this;
        }

        public Criteria andNdeliveryidLessThanOrEqualTo(Long value) {
            addCriterion("nDeliveryID <=", value, "ndeliveryid");
            return (Criteria) this;
        }

        public Criteria andNdeliveryidIn(List<Long> values) {
            addCriterion("nDeliveryID in", values, "ndeliveryid");
            return (Criteria) this;
        }

        public Criteria andNdeliveryidNotIn(List<Long> values) {
            addCriterion("nDeliveryID not in", values, "ndeliveryid");
            return (Criteria) this;
        }

        public Criteria andNdeliveryidBetween(Long value1, Long value2) {
            addCriterion("nDeliveryID between", value1, value2, "ndeliveryid");
            return (Criteria) this;
        }

        public Criteria andNdeliveryidNotBetween(Long value1, Long value2) {
            addCriterion("nDeliveryID not between", value1, value2, "ndeliveryid");
            return (Criteria) this;
        }

        public Criteria andSordercodeIsNull() {
            addCriterion("sOrderCode is null");
            return (Criteria) this;
        }

        public Criteria andSordercodeIsNotNull() {
            addCriterion("sOrderCode is not null");
            return (Criteria) this;
        }

        public Criteria andSordercodeEqualTo(String value) {
            addCriterion("sOrderCode =", value, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSordercodeNotEqualTo(String value) {
            addCriterion("sOrderCode <>", value, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSordercodeGreaterThan(String value) {
            addCriterion("sOrderCode >", value, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSordercodeGreaterThanOrEqualTo(String value) {
            addCriterion("sOrderCode >=", value, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSordercodeLessThan(String value) {
            addCriterion("sOrderCode <", value, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSordercodeLessThanOrEqualTo(String value) {
            addCriterion("sOrderCode <=", value, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSordercodeLike(String value) {
            addCriterion("sOrderCode like", value, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSordercodeNotLike(String value) {
            addCriterion("sOrderCode not like", value, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSordercodeIn(List<String> values) {
            addCriterion("sOrderCode in", values, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSordercodeNotIn(List<String> values) {
            addCriterion("sOrderCode not in", values, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSordercodeBetween(String value1, String value2) {
            addCriterion("sOrderCode between", value1, value2, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSordercodeNotBetween(String value1, String value2) {
            addCriterion("sOrderCode not between", value1, value2, "sordercode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeIsNull() {
            addCriterion("sShopCode is null");
            return (Criteria) this;
        }

        public Criteria andSshopcodeIsNotNull() {
            addCriterion("sShopCode is not null");
            return (Criteria) this;
        }

        public Criteria andSshopcodeEqualTo(String value) {
            addCriterion("sShopCode =", value, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeNotEqualTo(String value) {
            addCriterion("sShopCode <>", value, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeGreaterThan(String value) {
            addCriterion("sShopCode >", value, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeGreaterThanOrEqualTo(String value) {
            addCriterion("sShopCode >=", value, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeLessThan(String value) {
            addCriterion("sShopCode <", value, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeLessThanOrEqualTo(String value) {
            addCriterion("sShopCode <=", value, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeLike(String value) {
            addCriterion("sShopCode like", value, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeNotLike(String value) {
            addCriterion("sShopCode not like", value, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeIn(List<String> values) {
            addCriterion("sShopCode in", values, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeNotIn(List<String> values) {
            addCriterion("sShopCode not in", values, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeBetween(String value1, String value2) {
            addCriterion("sShopCode between", value1, value2, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSshopcodeNotBetween(String value1, String value2) {
            addCriterion("sShopCode not between", value1, value2, "sshopcode");
            return (Criteria) this;
        }

        public Criteria andSordertypeIsNull() {
            addCriterion("sOrderType is null");
            return (Criteria) this;
        }

        public Criteria andSordertypeIsNotNull() {
            addCriterion("sOrderType is not null");
            return (Criteria) this;
        }

        public Criteria andSordertypeEqualTo(Short value) {
            addCriterion("sOrderType =", value, "sordertype");
            return (Criteria) this;
        }

        public Criteria andSordertypeNotEqualTo(Short value) {
            addCriterion("sOrderType <>", value, "sordertype");
            return (Criteria) this;
        }

        public Criteria andSordertypeGreaterThan(Short value) {
            addCriterion("sOrderType >", value, "sordertype");
            return (Criteria) this;
        }

        public Criteria andSordertypeGreaterThanOrEqualTo(Short value) {
            addCriterion("sOrderType >=", value, "sordertype");
            return (Criteria) this;
        }

        public Criteria andSordertypeLessThan(Short value) {
            addCriterion("sOrderType <", value, "sordertype");
            return (Criteria) this;
        }

        public Criteria andSordertypeLessThanOrEqualTo(Short value) {
            addCriterion("sOrderType <=", value, "sordertype");
            return (Criteria) this;
        }

        public Criteria andSordertypeIn(List<Short> values) {
            addCriterion("sOrderType in", values, "sordertype");
            return (Criteria) this;
        }

        public Criteria andSordertypeNotIn(List<Short> values) {
            addCriterion("sOrderType not in", values, "sordertype");
            return (Criteria) this;
        }

        public Criteria andSordertypeBetween(Short value1, Short value2) {
            addCriterion("sOrderType between", value1, value2, "sordertype");
            return (Criteria) this;
        }

        public Criteria andSordertypeNotBetween(Short value1, Short value2) {
            addCriterion("sOrderType not between", value1, value2, "sordertype");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeIsNull() {
            addCriterion("dPaymentTime is null");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeIsNotNull() {
            addCriterion("dPaymentTime is not null");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeEqualTo(Date value) {
            addCriterion("dPaymentTime =", value, "dpaymenttime");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeNotEqualTo(Date value) {
            addCriterion("dPaymentTime <>", value, "dpaymenttime");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeGreaterThan(Date value) {
            addCriterion("dPaymentTime >", value, "dpaymenttime");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("dPaymentTime >=", value, "dpaymenttime");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeLessThan(Date value) {
            addCriterion("dPaymentTime <", value, "dpaymenttime");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeLessThanOrEqualTo(Date value) {
            addCriterion("dPaymentTime <=", value, "dpaymenttime");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeIn(List<Date> values) {
            addCriterion("dPaymentTime in", values, "dpaymenttime");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeNotIn(List<Date> values) {
            addCriterion("dPaymentTime not in", values, "dpaymenttime");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeBetween(Date value1, Date value2) {
            addCriterion("dPaymentTime between", value1, value2, "dpaymenttime");
            return (Criteria) this;
        }

        public Criteria andDpaymenttimeNotBetween(Date value1, Date value2) {
            addCriterion("dPaymentTime not between", value1, value2, "dpaymenttime");
            return (Criteria) this;
        }

        public Criteria andSordersourceIsNull() {
            addCriterion("sOrderSource is null");
            return (Criteria) this;
        }

        public Criteria andSordersourceIsNotNull() {
            addCriterion("sOrderSource is not null");
            return (Criteria) this;
        }

        public Criteria andSordersourceEqualTo(Short value) {
            addCriterion("sOrderSource =", value, "sordersource");
            return (Criteria) this;
        }

        public Criteria andSordersourceNotEqualTo(Short value) {
            addCriterion("sOrderSource <>", value, "sordersource");
            return (Criteria) this;
        }

        public Criteria andSordersourceGreaterThan(Short value) {
            addCriterion("sOrderSource >", value, "sordersource");
            return (Criteria) this;
        }

        public Criteria andSordersourceGreaterThanOrEqualTo(Short value) {
            addCriterion("sOrderSource >=", value, "sordersource");
            return (Criteria) this;
        }

        public Criteria andSordersourceLessThan(Short value) {
            addCriterion("sOrderSource <", value, "sordersource");
            return (Criteria) this;
        }

        public Criteria andSordersourceLessThanOrEqualTo(Short value) {
            addCriterion("sOrderSource <=", value, "sordersource");
            return (Criteria) this;
        }

        public Criteria andSordersourceIn(List<Short> values) {
            addCriterion("sOrderSource in", values, "sordersource");
            return (Criteria) this;
        }

        public Criteria andSordersourceNotIn(List<Short> values) {
            addCriterion("sOrderSource not in", values, "sordersource");
            return (Criteria) this;
        }

        public Criteria andSordersourceBetween(Short value1, Short value2) {
            addCriterion("sOrderSource between", value1, value2, "sordersource");
            return (Criteria) this;
        }

        public Criteria andSordersourceNotBetween(Short value1, Short value2) {
            addCriterion("sOrderSource not between", value1, value2, "sordersource");
            return (Criteria) this;
        }

        public Criteria andNaddressidIsNull() {
            addCriterion("nAddressID is null");
            return (Criteria) this;
        }

        public Criteria andNaddressidIsNotNull() {
            addCriterion("nAddressID is not null");
            return (Criteria) this;
        }

        public Criteria andNaddressidEqualTo(Long value) {
            addCriterion("nAddressID =", value, "naddressid");
            return (Criteria) this;
        }

        public Criteria andNaddressidNotEqualTo(Long value) {
            addCriterion("nAddressID <>", value, "naddressid");
            return (Criteria) this;
        }

        public Criteria andNaddressidGreaterThan(Long value) {
            addCriterion("nAddressID >", value, "naddressid");
            return (Criteria) this;
        }

        public Criteria andNaddressidGreaterThanOrEqualTo(Long value) {
            addCriterion("nAddressID >=", value, "naddressid");
            return (Criteria) this;
        }

        public Criteria andNaddressidLessThan(Long value) {
            addCriterion("nAddressID <", value, "naddressid");
            return (Criteria) this;
        }

        public Criteria andNaddressidLessThanOrEqualTo(Long value) {
            addCriterion("nAddressID <=", value, "naddressid");
            return (Criteria) this;
        }

        public Criteria andNaddressidIn(List<Long> values) {
            addCriterion("nAddressID in", values, "naddressid");
            return (Criteria) this;
        }

        public Criteria andNaddressidNotIn(List<Long> values) {
            addCriterion("nAddressID not in", values, "naddressid");
            return (Criteria) this;
        }

        public Criteria andNaddressidBetween(Long value1, Long value2) {
            addCriterion("nAddressID between", value1, value2, "naddressid");
            return (Criteria) this;
        }

        public Criteria andNaddressidNotBetween(Long value1, Long value2) {
            addCriterion("nAddressID not between", value1, value2, "naddressid");
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