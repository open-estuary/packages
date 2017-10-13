package com.htsat.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class REcDeliveryinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcDeliveryinfoExample() {
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

        public Criteria andSexpresscompanyIsNull() {
            addCriterion("sExpressCompany is null");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyIsNotNull() {
            addCriterion("sExpressCompany is not null");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyEqualTo(String value) {
            addCriterion("sExpressCompany =", value, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyNotEqualTo(String value) {
            addCriterion("sExpressCompany <>", value, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyGreaterThan(String value) {
            addCriterion("sExpressCompany >", value, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyGreaterThanOrEqualTo(String value) {
            addCriterion("sExpressCompany >=", value, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyLessThan(String value) {
            addCriterion("sExpressCompany <", value, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyLessThanOrEqualTo(String value) {
            addCriterion("sExpressCompany <=", value, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyLike(String value) {
            addCriterion("sExpressCompany like", value, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyNotLike(String value) {
            addCriterion("sExpressCompany not like", value, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyIn(List<String> values) {
            addCriterion("sExpressCompany in", values, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyNotIn(List<String> values) {
            addCriterion("sExpressCompany not in", values, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyBetween(String value1, String value2) {
            addCriterion("sExpressCompany between", value1, value2, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andSexpresscompanyNotBetween(String value1, String value2) {
            addCriterion("sExpressCompany not between", value1, value2, "sexpresscompany");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceIsNull() {
            addCriterion("nDeliveryPrice is null");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceIsNotNull() {
            addCriterion("nDeliveryPrice is not null");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceEqualTo(BigDecimal value) {
            addCriterion("nDeliveryPrice =", value, "ndeliveryprice");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceNotEqualTo(BigDecimal value) {
            addCriterion("nDeliveryPrice <>", value, "ndeliveryprice");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceGreaterThan(BigDecimal value) {
            addCriterion("nDeliveryPrice >", value, "ndeliveryprice");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("nDeliveryPrice >=", value, "ndeliveryprice");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceLessThan(BigDecimal value) {
            addCriterion("nDeliveryPrice <", value, "ndeliveryprice");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("nDeliveryPrice <=", value, "ndeliveryprice");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceIn(List<BigDecimal> values) {
            addCriterion("nDeliveryPrice in", values, "ndeliveryprice");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceNotIn(List<BigDecimal> values) {
            addCriterion("nDeliveryPrice not in", values, "ndeliveryprice");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nDeliveryPrice between", value1, value2, "ndeliveryprice");
            return (Criteria) this;
        }

        public Criteria andNdeliverypriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nDeliveryPrice not between", value1, value2, "ndeliveryprice");
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

        public Criteria andDouttimeIsNull() {
            addCriterion("dOutTime is null");
            return (Criteria) this;
        }

        public Criteria andDouttimeIsNotNull() {
            addCriterion("dOutTime is not null");
            return (Criteria) this;
        }

        public Criteria andDouttimeEqualTo(Date value) {
            addCriterion("dOutTime =", value, "douttime");
            return (Criteria) this;
        }

        public Criteria andDouttimeNotEqualTo(Date value) {
            addCriterion("dOutTime <>", value, "douttime");
            return (Criteria) this;
        }

        public Criteria andDouttimeGreaterThan(Date value) {
            addCriterion("dOutTime >", value, "douttime");
            return (Criteria) this;
        }

        public Criteria andDouttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("dOutTime >=", value, "douttime");
            return (Criteria) this;
        }

        public Criteria andDouttimeLessThan(Date value) {
            addCriterion("dOutTime <", value, "douttime");
            return (Criteria) this;
        }

        public Criteria andDouttimeLessThanOrEqualTo(Date value) {
            addCriterion("dOutTime <=", value, "douttime");
            return (Criteria) this;
        }

        public Criteria andDouttimeIn(List<Date> values) {
            addCriterion("dOutTime in", values, "douttime");
            return (Criteria) this;
        }

        public Criteria andDouttimeNotIn(List<Date> values) {
            addCriterion("dOutTime not in", values, "douttime");
            return (Criteria) this;
        }

        public Criteria andDouttimeBetween(Date value1, Date value2) {
            addCriterion("dOutTime between", value1, value2, "douttime");
            return (Criteria) this;
        }

        public Criteria andDouttimeNotBetween(Date value1, Date value2) {
            addCriterion("dOutTime not between", value1, value2, "douttime");
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

        public Criteria andSconsigneeIsNull() {
            addCriterion("sConsignee is null");
            return (Criteria) this;
        }

        public Criteria andSconsigneeIsNotNull() {
            addCriterion("sConsignee is not null");
            return (Criteria) this;
        }

        public Criteria andSconsigneeEqualTo(String value) {
            addCriterion("sConsignee =", value, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSconsigneeNotEqualTo(String value) {
            addCriterion("sConsignee <>", value, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSconsigneeGreaterThan(String value) {
            addCriterion("sConsignee >", value, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSconsigneeGreaterThanOrEqualTo(String value) {
            addCriterion("sConsignee >=", value, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSconsigneeLessThan(String value) {
            addCriterion("sConsignee <", value, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSconsigneeLessThanOrEqualTo(String value) {
            addCriterion("sConsignee <=", value, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSconsigneeLike(String value) {
            addCriterion("sConsignee like", value, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSconsigneeNotLike(String value) {
            addCriterion("sConsignee not like", value, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSconsigneeIn(List<String> values) {
            addCriterion("sConsignee in", values, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSconsigneeNotIn(List<String> values) {
            addCriterion("sConsignee not in", values, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSconsigneeBetween(String value1, String value2) {
            addCriterion("sConsignee between", value1, value2, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSconsigneeNotBetween(String value1, String value2) {
            addCriterion("sConsignee not between", value1, value2, "sconsignee");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentIsNull() {
            addCriterion("sDeliveryComment is null");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentIsNotNull() {
            addCriterion("sDeliveryComment is not null");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentEqualTo(String value) {
            addCriterion("sDeliveryComment =", value, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentNotEqualTo(String value) {
            addCriterion("sDeliveryComment <>", value, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentGreaterThan(String value) {
            addCriterion("sDeliveryComment >", value, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentGreaterThanOrEqualTo(String value) {
            addCriterion("sDeliveryComment >=", value, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentLessThan(String value) {
            addCriterion("sDeliveryComment <", value, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentLessThanOrEqualTo(String value) {
            addCriterion("sDeliveryComment <=", value, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentLike(String value) {
            addCriterion("sDeliveryComment like", value, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentNotLike(String value) {
            addCriterion("sDeliveryComment not like", value, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentIn(List<String> values) {
            addCriterion("sDeliveryComment in", values, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentNotIn(List<String> values) {
            addCriterion("sDeliveryComment not in", values, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentBetween(String value1, String value2) {
            addCriterion("sDeliveryComment between", value1, value2, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycommentNotBetween(String value1, String value2) {
            addCriterion("sDeliveryComment not between", value1, value2, "sdeliverycomment");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeIsNull() {
            addCriterion("sDeliveryCode is null");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeIsNotNull() {
            addCriterion("sDeliveryCode is not null");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeEqualTo(String value) {
            addCriterion("sDeliveryCode =", value, "sdeliverycode");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeNotEqualTo(String value) {
            addCriterion("sDeliveryCode <>", value, "sdeliverycode");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeGreaterThan(String value) {
            addCriterion("sDeliveryCode >", value, "sdeliverycode");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeGreaterThanOrEqualTo(String value) {
            addCriterion("sDeliveryCode >=", value, "sdeliverycode");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeLessThan(String value) {
            addCriterion("sDeliveryCode <", value, "sdeliverycode");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeLessThanOrEqualTo(String value) {
            addCriterion("sDeliveryCode <=", value, "sdeliverycode");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeLike(String value) {
            addCriterion("sDeliveryCode like", value, "sdeliverycode");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeNotLike(String value) {
            addCriterion("sDeliveryCode not like", value, "sdeliverycode");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeIn(List<String> values) {
            addCriterion("sDeliveryCode in", values, "sdeliverycode");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeNotIn(List<String> values) {
            addCriterion("sDeliveryCode not in", values, "sdeliverycode");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeBetween(String value1, String value2) {
            addCriterion("sDeliveryCode between", value1, value2, "sdeliverycode");
            return (Criteria) this;
        }

        public Criteria andSdeliverycodeNotBetween(String value1, String value2) {
            addCriterion("sDeliveryCode not between", value1, value2, "sdeliverycode");
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