package com.htsat.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
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