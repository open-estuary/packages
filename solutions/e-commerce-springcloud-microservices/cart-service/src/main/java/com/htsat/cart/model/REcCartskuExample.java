package com.htsat.cart.model;

import java.util.ArrayList;
import java.util.List;

public class REcCartskuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcCartskuExample() {
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

        public Criteria andNcartskuidIsNull() {
            addCriterion("nCartSKUID is null");
            return (Criteria) this;
        }

        public Criteria andNcartskuidIsNotNull() {
            addCriterion("nCartSKUID is not null");
            return (Criteria) this;
        }

        public Criteria andNcartskuidEqualTo(Long value) {
            addCriterion("nCartSKUID =", value, "ncartskuid");
            return (Criteria) this;
        }

        public Criteria andNcartskuidNotEqualTo(Long value) {
            addCriterion("nCartSKUID <>", value, "ncartskuid");
            return (Criteria) this;
        }

        public Criteria andNcartskuidGreaterThan(Long value) {
            addCriterion("nCartSKUID >", value, "ncartskuid");
            return (Criteria) this;
        }

        public Criteria andNcartskuidGreaterThanOrEqualTo(Long value) {
            addCriterion("nCartSKUID >=", value, "ncartskuid");
            return (Criteria) this;
        }

        public Criteria andNcartskuidLessThan(Long value) {
            addCriterion("nCartSKUID <", value, "ncartskuid");
            return (Criteria) this;
        }

        public Criteria andNcartskuidLessThanOrEqualTo(Long value) {
            addCriterion("nCartSKUID <=", value, "ncartskuid");
            return (Criteria) this;
        }

        public Criteria andNcartskuidIn(List<Long> values) {
            addCriterion("nCartSKUID in", values, "ncartskuid");
            return (Criteria) this;
        }

        public Criteria andNcartskuidNotIn(List<Long> values) {
            addCriterion("nCartSKUID not in", values, "ncartskuid");
            return (Criteria) this;
        }

        public Criteria andNcartskuidBetween(Long value1, Long value2) {
            addCriterion("nCartSKUID between", value1, value2, "ncartskuid");
            return (Criteria) this;
        }

        public Criteria andNcartskuidNotBetween(Long value1, Long value2) {
            addCriterion("nCartSKUID not between", value1, value2, "ncartskuid");
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