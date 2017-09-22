package com.htsat.order.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class REcUserinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcUserinfoExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andSloginnameIsNull() {
            addCriterion("sLoginName is null");
            return (Criteria) this;
        }

        public Criteria andSloginnameIsNotNull() {
            addCriterion("sLoginName is not null");
            return (Criteria) this;
        }

        public Criteria andSloginnameEqualTo(String value) {
            addCriterion("sLoginName =", value, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginnameNotEqualTo(String value) {
            addCriterion("sLoginName <>", value, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginnameGreaterThan(String value) {
            addCriterion("sLoginName >", value, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginnameGreaterThanOrEqualTo(String value) {
            addCriterion("sLoginName >=", value, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginnameLessThan(String value) {
            addCriterion("sLoginName <", value, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginnameLessThanOrEqualTo(String value) {
            addCriterion("sLoginName <=", value, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginnameLike(String value) {
            addCriterion("sLoginName like", value, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginnameNotLike(String value) {
            addCriterion("sLoginName not like", value, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginnameIn(List<String> values) {
            addCriterion("sLoginName in", values, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginnameNotIn(List<String> values) {
            addCriterion("sLoginName not in", values, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginnameBetween(String value1, String value2) {
            addCriterion("sLoginName between", value1, value2, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginnameNotBetween(String value1, String value2) {
            addCriterion("sLoginName not between", value1, value2, "sloginname");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordIsNull() {
            addCriterion("sLoginPassword is null");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordIsNotNull() {
            addCriterion("sLoginPassword is not null");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordEqualTo(String value) {
            addCriterion("sLoginPassword =", value, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordNotEqualTo(String value) {
            addCriterion("sLoginPassword <>", value, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordGreaterThan(String value) {
            addCriterion("sLoginPassword >", value, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordGreaterThanOrEqualTo(String value) {
            addCriterion("sLoginPassword >=", value, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordLessThan(String value) {
            addCriterion("sLoginPassword <", value, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordLessThanOrEqualTo(String value) {
            addCriterion("sLoginPassword <=", value, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordLike(String value) {
            addCriterion("sLoginPassword like", value, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordNotLike(String value) {
            addCriterion("sLoginPassword not like", value, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordIn(List<String> values) {
            addCriterion("sLoginPassword in", values, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordNotIn(List<String> values) {
            addCriterion("sLoginPassword not in", values, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordBetween(String value1, String value2) {
            addCriterion("sLoginPassword between", value1, value2, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSloginpasswordNotBetween(String value1, String value2) {
            addCriterion("sLoginPassword not between", value1, value2, "sloginpassword");
            return (Criteria) this;
        }

        public Criteria andSfirstnameIsNull() {
            addCriterion("sFirstName is null");
            return (Criteria) this;
        }

        public Criteria andSfirstnameIsNotNull() {
            addCriterion("sFirstName is not null");
            return (Criteria) this;
        }

        public Criteria andSfirstnameEqualTo(String value) {
            addCriterion("sFirstName =", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameNotEqualTo(String value) {
            addCriterion("sFirstName <>", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameGreaterThan(String value) {
            addCriterion("sFirstName >", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameGreaterThanOrEqualTo(String value) {
            addCriterion("sFirstName >=", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameLessThan(String value) {
            addCriterion("sFirstName <", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameLessThanOrEqualTo(String value) {
            addCriterion("sFirstName <=", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameLike(String value) {
            addCriterion("sFirstName like", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameNotLike(String value) {
            addCriterion("sFirstName not like", value, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameIn(List<String> values) {
            addCriterion("sFirstName in", values, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameNotIn(List<String> values) {
            addCriterion("sFirstName not in", values, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameBetween(String value1, String value2) {
            addCriterion("sFirstName between", value1, value2, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSfirstnameNotBetween(String value1, String value2) {
            addCriterion("sFirstName not between", value1, value2, "sfirstname");
            return (Criteria) this;
        }

        public Criteria andSlastnameIsNull() {
            addCriterion("sLastName is null");
            return (Criteria) this;
        }

        public Criteria andSlastnameIsNotNull() {
            addCriterion("sLastName is not null");
            return (Criteria) this;
        }

        public Criteria andSlastnameEqualTo(String value) {
            addCriterion("sLastName =", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameNotEqualTo(String value) {
            addCriterion("sLastName <>", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameGreaterThan(String value) {
            addCriterion("sLastName >", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameGreaterThanOrEqualTo(String value) {
            addCriterion("sLastName >=", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameLessThan(String value) {
            addCriterion("sLastName <", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameLessThanOrEqualTo(String value) {
            addCriterion("sLastName <=", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameLike(String value) {
            addCriterion("sLastName like", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameNotLike(String value) {
            addCriterion("sLastName not like", value, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameIn(List<String> values) {
            addCriterion("sLastName in", values, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameNotIn(List<String> values) {
            addCriterion("sLastName not in", values, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameBetween(String value1, String value2) {
            addCriterion("sLastName between", value1, value2, "slastname");
            return (Criteria) this;
        }

        public Criteria andSlastnameNotBetween(String value1, String value2) {
            addCriterion("sLastName not between", value1, value2, "slastname");
            return (Criteria) this;
        }

        public Criteria andSphonenoIsNull() {
            addCriterion("sPhoneNo is null");
            return (Criteria) this;
        }

        public Criteria andSphonenoIsNotNull() {
            addCriterion("sPhoneNo is not null");
            return (Criteria) this;
        }

        public Criteria andSphonenoEqualTo(String value) {
            addCriterion("sPhoneNo =", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoNotEqualTo(String value) {
            addCriterion("sPhoneNo <>", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoGreaterThan(String value) {
            addCriterion("sPhoneNo >", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoGreaterThanOrEqualTo(String value) {
            addCriterion("sPhoneNo >=", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoLessThan(String value) {
            addCriterion("sPhoneNo <", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoLessThanOrEqualTo(String value) {
            addCriterion("sPhoneNo <=", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoLike(String value) {
            addCriterion("sPhoneNo like", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoNotLike(String value) {
            addCriterion("sPhoneNo not like", value, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoIn(List<String> values) {
            addCriterion("sPhoneNo in", values, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoNotIn(List<String> values) {
            addCriterion("sPhoneNo not in", values, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoBetween(String value1, String value2) {
            addCriterion("sPhoneNo between", value1, value2, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSphonenoNotBetween(String value1, String value2) {
            addCriterion("sPhoneNo not between", value1, value2, "sphoneno");
            return (Criteria) this;
        }

        public Criteria andSemailaddressIsNull() {
            addCriterion("sEmailAddress is null");
            return (Criteria) this;
        }

        public Criteria andSemailaddressIsNotNull() {
            addCriterion("sEmailAddress is not null");
            return (Criteria) this;
        }

        public Criteria andSemailaddressEqualTo(String value) {
            addCriterion("sEmailAddress =", value, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andSemailaddressNotEqualTo(String value) {
            addCriterion("sEmailAddress <>", value, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andSemailaddressGreaterThan(String value) {
            addCriterion("sEmailAddress >", value, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andSemailaddressGreaterThanOrEqualTo(String value) {
            addCriterion("sEmailAddress >=", value, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andSemailaddressLessThan(String value) {
            addCriterion("sEmailAddress <", value, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andSemailaddressLessThanOrEqualTo(String value) {
            addCriterion("sEmailAddress <=", value, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andSemailaddressLike(String value) {
            addCriterion("sEmailAddress like", value, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andSemailaddressNotLike(String value) {
            addCriterion("sEmailAddress not like", value, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andSemailaddressIn(List<String> values) {
            addCriterion("sEmailAddress in", values, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andSemailaddressNotIn(List<String> values) {
            addCriterion("sEmailAddress not in", values, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andSemailaddressBetween(String value1, String value2) {
            addCriterion("sEmailAddress between", value1, value2, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andSemailaddressNotBetween(String value1, String value2) {
            addCriterion("sEmailAddress not between", value1, value2, "semailaddress");
            return (Criteria) this;
        }

        public Criteria andCgenderIsNull() {
            addCriterion("cGender is null");
            return (Criteria) this;
        }

        public Criteria andCgenderIsNotNull() {
            addCriterion("cGender is not null");
            return (Criteria) this;
        }

        public Criteria andCgenderEqualTo(String value) {
            addCriterion("cGender =", value, "cgender");
            return (Criteria) this;
        }

        public Criteria andCgenderNotEqualTo(String value) {
            addCriterion("cGender <>", value, "cgender");
            return (Criteria) this;
        }

        public Criteria andCgenderGreaterThan(String value) {
            addCriterion("cGender >", value, "cgender");
            return (Criteria) this;
        }

        public Criteria andCgenderGreaterThanOrEqualTo(String value) {
            addCriterion("cGender >=", value, "cgender");
            return (Criteria) this;
        }

        public Criteria andCgenderLessThan(String value) {
            addCriterion("cGender <", value, "cgender");
            return (Criteria) this;
        }

        public Criteria andCgenderLessThanOrEqualTo(String value) {
            addCriterion("cGender <=", value, "cgender");
            return (Criteria) this;
        }

        public Criteria andCgenderLike(String value) {
            addCriterion("cGender like", value, "cgender");
            return (Criteria) this;
        }

        public Criteria andCgenderNotLike(String value) {
            addCriterion("cGender not like", value, "cgender");
            return (Criteria) this;
        }

        public Criteria andCgenderIn(List<String> values) {
            addCriterion("cGender in", values, "cgender");
            return (Criteria) this;
        }

        public Criteria andCgenderNotIn(List<String> values) {
            addCriterion("cGender not in", values, "cgender");
            return (Criteria) this;
        }

        public Criteria andCgenderBetween(String value1, String value2) {
            addCriterion("cGender between", value1, value2, "cgender");
            return (Criteria) this;
        }

        public Criteria andCgenderNotBetween(String value1, String value2) {
            addCriterion("cGender not between", value1, value2, "cgender");
            return (Criteria) this;
        }

        public Criteria andSbirthdayIsNull() {
            addCriterion("sBirthday is null");
            return (Criteria) this;
        }

        public Criteria andSbirthdayIsNotNull() {
            addCriterion("sBirthday is not null");
            return (Criteria) this;
        }

        public Criteria andSbirthdayEqualTo(Date value) {
            addCriterionForJDBCDate("sBirthday =", value, "sbirthday");
            return (Criteria) this;
        }

        public Criteria andSbirthdayNotEqualTo(Date value) {
            addCriterionForJDBCDate("sBirthday <>", value, "sbirthday");
            return (Criteria) this;
        }

        public Criteria andSbirthdayGreaterThan(Date value) {
            addCriterionForJDBCDate("sBirthday >", value, "sbirthday");
            return (Criteria) this;
        }

        public Criteria andSbirthdayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("sBirthday >=", value, "sbirthday");
            return (Criteria) this;
        }

        public Criteria andSbirthdayLessThan(Date value) {
            addCriterionForJDBCDate("sBirthday <", value, "sbirthday");
            return (Criteria) this;
        }

        public Criteria andSbirthdayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("sBirthday <=", value, "sbirthday");
            return (Criteria) this;
        }

        public Criteria andSbirthdayIn(List<Date> values) {
            addCriterionForJDBCDate("sBirthday in", values, "sbirthday");
            return (Criteria) this;
        }

        public Criteria andSbirthdayNotIn(List<Date> values) {
            addCriterionForJDBCDate("sBirthday not in", values, "sbirthday");
            return (Criteria) this;
        }

        public Criteria andSbirthdayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("sBirthday between", value1, value2, "sbirthday");
            return (Criteria) this;
        }

        public Criteria andSbirthdayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("sBirthday not between", value1, value2, "sbirthday");
            return (Criteria) this;
        }

        public Criteria andSregistertimeIsNull() {
            addCriterion("sRegisterTime is null");
            return (Criteria) this;
        }

        public Criteria andSregistertimeIsNotNull() {
            addCriterion("sRegisterTime is not null");
            return (Criteria) this;
        }

        public Criteria andSregistertimeEqualTo(Date value) {
            addCriterion("sRegisterTime =", value, "sregistertime");
            return (Criteria) this;
        }

        public Criteria andSregistertimeNotEqualTo(Date value) {
            addCriterion("sRegisterTime <>", value, "sregistertime");
            return (Criteria) this;
        }

        public Criteria andSregistertimeGreaterThan(Date value) {
            addCriterion("sRegisterTime >", value, "sregistertime");
            return (Criteria) this;
        }

        public Criteria andSregistertimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sRegisterTime >=", value, "sregistertime");
            return (Criteria) this;
        }

        public Criteria andSregistertimeLessThan(Date value) {
            addCriterion("sRegisterTime <", value, "sregistertime");
            return (Criteria) this;
        }

        public Criteria andSregistertimeLessThanOrEqualTo(Date value) {
            addCriterion("sRegisterTime <=", value, "sregistertime");
            return (Criteria) this;
        }

        public Criteria andSregistertimeIn(List<Date> values) {
            addCriterion("sRegisterTime in", values, "sregistertime");
            return (Criteria) this;
        }

        public Criteria andSregistertimeNotIn(List<Date> values) {
            addCriterion("sRegisterTime not in", values, "sregistertime");
            return (Criteria) this;
        }

        public Criteria andSregistertimeBetween(Date value1, Date value2) {
            addCriterion("sRegisterTime between", value1, value2, "sregistertime");
            return (Criteria) this;
        }

        public Criteria andSregistertimeNotBetween(Date value1, Date value2) {
            addCriterion("sRegisterTime not between", value1, value2, "sregistertime");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordIsNull() {
            addCriterion("sPayPassword is null");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordIsNotNull() {
            addCriterion("sPayPassword is not null");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordEqualTo(String value) {
            addCriterion("sPayPassword =", value, "spaypassword");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordNotEqualTo(String value) {
            addCriterion("sPayPassword <>", value, "spaypassword");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordGreaterThan(String value) {
            addCriterion("sPayPassword >", value, "spaypassword");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordGreaterThanOrEqualTo(String value) {
            addCriterion("sPayPassword >=", value, "spaypassword");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordLessThan(String value) {
            addCriterion("sPayPassword <", value, "spaypassword");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordLessThanOrEqualTo(String value) {
            addCriterion("sPayPassword <=", value, "spaypassword");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordLike(String value) {
            addCriterion("sPayPassword like", value, "spaypassword");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordNotLike(String value) {
            addCriterion("sPayPassword not like", value, "spaypassword");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordIn(List<String> values) {
            addCriterion("sPayPassword in", values, "spaypassword");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordNotIn(List<String> values) {
            addCriterion("sPayPassword not in", values, "spaypassword");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordBetween(String value1, String value2) {
            addCriterion("sPayPassword between", value1, value2, "spaypassword");
            return (Criteria) this;
        }

        public Criteria andSpaypasswordNotBetween(String value1, String value2) {
            addCriterion("sPayPassword not between", value1, value2, "spaypassword");
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