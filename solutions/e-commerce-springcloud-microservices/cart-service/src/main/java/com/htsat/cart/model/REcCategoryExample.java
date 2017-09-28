package com.htsat.cart.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class REcCategoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcCategoryExample() {
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

        public Criteria andNcategoryidIsNull() {
            addCriterion("nCategoryID is null");
            return (Criteria) this;
        }

        public Criteria andNcategoryidIsNotNull() {
            addCriterion("nCategoryID is not null");
            return (Criteria) this;
        }

        public Criteria andNcategoryidEqualTo(Integer value) {
            addCriterion("nCategoryID =", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidNotEqualTo(Integer value) {
            addCriterion("nCategoryID <>", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidGreaterThan(Integer value) {
            addCriterion("nCategoryID >", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidGreaterThanOrEqualTo(Integer value) {
            addCriterion("nCategoryID >=", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidLessThan(Integer value) {
            addCriterion("nCategoryID <", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidLessThanOrEqualTo(Integer value) {
            addCriterion("nCategoryID <=", value, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidIn(List<Integer> values) {
            addCriterion("nCategoryID in", values, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidNotIn(List<Integer> values) {
            addCriterion("nCategoryID not in", values, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidBetween(Integer value1, Integer value2) {
            addCriterion("nCategoryID between", value1, value2, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNcategoryidNotBetween(Integer value1, Integer value2) {
            addCriterion("nCategoryID not between", value1, value2, "ncategoryid");
            return (Criteria) this;
        }

        public Criteria andNlevelIsNull() {
            addCriterion("nLevel is null");
            return (Criteria) this;
        }

        public Criteria andNlevelIsNotNull() {
            addCriterion("nLevel is not null");
            return (Criteria) this;
        }

        public Criteria andNlevelEqualTo(Integer value) {
            addCriterion("nLevel =", value, "nlevel");
            return (Criteria) this;
        }

        public Criteria andNlevelNotEqualTo(Integer value) {
            addCriterion("nLevel <>", value, "nlevel");
            return (Criteria) this;
        }

        public Criteria andNlevelGreaterThan(Integer value) {
            addCriterion("nLevel >", value, "nlevel");
            return (Criteria) this;
        }

        public Criteria andNlevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("nLevel >=", value, "nlevel");
            return (Criteria) this;
        }

        public Criteria andNlevelLessThan(Integer value) {
            addCriterion("nLevel <", value, "nlevel");
            return (Criteria) this;
        }

        public Criteria andNlevelLessThanOrEqualTo(Integer value) {
            addCriterion("nLevel <=", value, "nlevel");
            return (Criteria) this;
        }

        public Criteria andNlevelIn(List<Integer> values) {
            addCriterion("nLevel in", values, "nlevel");
            return (Criteria) this;
        }

        public Criteria andNlevelNotIn(List<Integer> values) {
            addCriterion("nLevel not in", values, "nlevel");
            return (Criteria) this;
        }

        public Criteria andNlevelBetween(Integer value1, Integer value2) {
            addCriterion("nLevel between", value1, value2, "nlevel");
            return (Criteria) this;
        }

        public Criteria andNlevelNotBetween(Integer value1, Integer value2) {
            addCriterion("nLevel not between", value1, value2, "nlevel");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidIsNull() {
            addCriterion("nParentCategoryID is null");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidIsNotNull() {
            addCriterion("nParentCategoryID is not null");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidEqualTo(Integer value) {
            addCriterion("nParentCategoryID =", value, "nparentcategoryid");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidNotEqualTo(Integer value) {
            addCriterion("nParentCategoryID <>", value, "nparentcategoryid");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidGreaterThan(Integer value) {
            addCriterion("nParentCategoryID >", value, "nparentcategoryid");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidGreaterThanOrEqualTo(Integer value) {
            addCriterion("nParentCategoryID >=", value, "nparentcategoryid");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidLessThan(Integer value) {
            addCriterion("nParentCategoryID <", value, "nparentcategoryid");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidLessThanOrEqualTo(Integer value) {
            addCriterion("nParentCategoryID <=", value, "nparentcategoryid");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidIn(List<Integer> values) {
            addCriterion("nParentCategoryID in", values, "nparentcategoryid");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidNotIn(List<Integer> values) {
            addCriterion("nParentCategoryID not in", values, "nparentcategoryid");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidBetween(Integer value1, Integer value2) {
            addCriterion("nParentCategoryID between", value1, value2, "nparentcategoryid");
            return (Criteria) this;
        }

        public Criteria andNparentcategoryidNotBetween(Integer value1, Integer value2) {
            addCriterion("nParentCategoryID not between", value1, value2, "nparentcategoryid");
            return (Criteria) this;
        }

        public Criteria andScategorynameIsNull() {
            addCriterion("sCategoryName is null");
            return (Criteria) this;
        }

        public Criteria andScategorynameIsNotNull() {
            addCriterion("sCategoryName is not null");
            return (Criteria) this;
        }

        public Criteria andScategorynameEqualTo(String value) {
            addCriterion("sCategoryName =", value, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScategorynameNotEqualTo(String value) {
            addCriterion("sCategoryName <>", value, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScategorynameGreaterThan(String value) {
            addCriterion("sCategoryName >", value, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScategorynameGreaterThanOrEqualTo(String value) {
            addCriterion("sCategoryName >=", value, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScategorynameLessThan(String value) {
            addCriterion("sCategoryName <", value, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScategorynameLessThanOrEqualTo(String value) {
            addCriterion("sCategoryName <=", value, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScategorynameLike(String value) {
            addCriterion("sCategoryName like", value, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScategorynameNotLike(String value) {
            addCriterion("sCategoryName not like", value, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScategorynameIn(List<String> values) {
            addCriterion("sCategoryName in", values, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScategorynameNotIn(List<String> values) {
            addCriterion("sCategoryName not in", values, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScategorynameBetween(String value1, String value2) {
            addCriterion("sCategoryName between", value1, value2, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScategorynameNotBetween(String value1, String value2) {
            addCriterion("sCategoryName not between", value1, value2, "scategoryname");
            return (Criteria) this;
        }

        public Criteria andScodeIsNull() {
            addCriterion("sCode is null");
            return (Criteria) this;
        }

        public Criteria andScodeIsNotNull() {
            addCriterion("sCode is not null");
            return (Criteria) this;
        }

        public Criteria andScodeEqualTo(String value) {
            addCriterion("sCode =", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeNotEqualTo(String value) {
            addCriterion("sCode <>", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeGreaterThan(String value) {
            addCriterion("sCode >", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeGreaterThanOrEqualTo(String value) {
            addCriterion("sCode >=", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeLessThan(String value) {
            addCriterion("sCode <", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeLessThanOrEqualTo(String value) {
            addCriterion("sCode <=", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeLike(String value) {
            addCriterion("sCode like", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeNotLike(String value) {
            addCriterion("sCode not like", value, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeIn(List<String> values) {
            addCriterion("sCode in", values, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeNotIn(List<String> values) {
            addCriterion("sCode not in", values, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeBetween(String value1, String value2) {
            addCriterion("sCode between", value1, value2, "scode");
            return (Criteria) this;
        }

        public Criteria andScodeNotBetween(String value1, String value2) {
            addCriterion("sCode not between", value1, value2, "scode");
            return (Criteria) this;
        }

        public Criteria andIsleafIsNull() {
            addCriterion("IsLeaf is null");
            return (Criteria) this;
        }

        public Criteria andIsleafIsNotNull() {
            addCriterion("IsLeaf is not null");
            return (Criteria) this;
        }

        public Criteria andIsleafEqualTo(String value) {
            addCriterion("IsLeaf =", value, "isleaf");
            return (Criteria) this;
        }

        public Criteria andIsleafNotEqualTo(String value) {
            addCriterion("IsLeaf <>", value, "isleaf");
            return (Criteria) this;
        }

        public Criteria andIsleafGreaterThan(String value) {
            addCriterion("IsLeaf >", value, "isleaf");
            return (Criteria) this;
        }

        public Criteria andIsleafGreaterThanOrEqualTo(String value) {
            addCriterion("IsLeaf >=", value, "isleaf");
            return (Criteria) this;
        }

        public Criteria andIsleafLessThan(String value) {
            addCriterion("IsLeaf <", value, "isleaf");
            return (Criteria) this;
        }

        public Criteria andIsleafLessThanOrEqualTo(String value) {
            addCriterion("IsLeaf <=", value, "isleaf");
            return (Criteria) this;
        }

        public Criteria andIsleafLike(String value) {
            addCriterion("IsLeaf like", value, "isleaf");
            return (Criteria) this;
        }

        public Criteria andIsleafNotLike(String value) {
            addCriterion("IsLeaf not like", value, "isleaf");
            return (Criteria) this;
        }

        public Criteria andIsleafIn(List<String> values) {
            addCriterion("IsLeaf in", values, "isleaf");
            return (Criteria) this;
        }

        public Criteria andIsleafNotIn(List<String> values) {
            addCriterion("IsLeaf not in", values, "isleaf");
            return (Criteria) this;
        }

        public Criteria andIsleafBetween(String value1, String value2) {
            addCriterion("IsLeaf between", value1, value2, "isleaf");
            return (Criteria) this;
        }

        public Criteria andIsleafNotBetween(String value1, String value2) {
            addCriterion("IsLeaf not between", value1, value2, "isleaf");
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