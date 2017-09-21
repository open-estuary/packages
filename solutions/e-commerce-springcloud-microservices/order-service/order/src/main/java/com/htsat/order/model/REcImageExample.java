package com.htsat.order.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class REcImageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public REcImageExample() {
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

        public Criteria andNimageidIsNull() {
            addCriterion("nImageID is null");
            return (Criteria) this;
        }

        public Criteria andNimageidIsNotNull() {
            addCriterion("nImageID is not null");
            return (Criteria) this;
        }

        public Criteria andNimageidEqualTo(Integer value) {
            addCriterion("nImageID =", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidNotEqualTo(Integer value) {
            addCriterion("nImageID <>", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidGreaterThan(Integer value) {
            addCriterion("nImageID >", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidGreaterThanOrEqualTo(Integer value) {
            addCriterion("nImageID >=", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidLessThan(Integer value) {
            addCriterion("nImageID <", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidLessThanOrEqualTo(Integer value) {
            addCriterion("nImageID <=", value, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidIn(List<Integer> values) {
            addCriterion("nImageID in", values, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidNotIn(List<Integer> values) {
            addCriterion("nImageID not in", values, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidBetween(Integer value1, Integer value2) {
            addCriterion("nImageID between", value1, value2, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNimageidNotBetween(Integer value1, Integer value2) {
            addCriterion("nImageID not between", value1, value2, "nimageid");
            return (Criteria) this;
        }

        public Criteria andNpositionIsNull() {
            addCriterion("nPosition is null");
            return (Criteria) this;
        }

        public Criteria andNpositionIsNotNull() {
            addCriterion("nPosition is not null");
            return (Criteria) this;
        }

        public Criteria andNpositionEqualTo(Integer value) {
            addCriterion("nPosition =", value, "nposition");
            return (Criteria) this;
        }

        public Criteria andNpositionNotEqualTo(Integer value) {
            addCriterion("nPosition <>", value, "nposition");
            return (Criteria) this;
        }

        public Criteria andNpositionGreaterThan(Integer value) {
            addCriterion("nPosition >", value, "nposition");
            return (Criteria) this;
        }

        public Criteria andNpositionGreaterThanOrEqualTo(Integer value) {
            addCriterion("nPosition >=", value, "nposition");
            return (Criteria) this;
        }

        public Criteria andNpositionLessThan(Integer value) {
            addCriterion("nPosition <", value, "nposition");
            return (Criteria) this;
        }

        public Criteria andNpositionLessThanOrEqualTo(Integer value) {
            addCriterion("nPosition <=", value, "nposition");
            return (Criteria) this;
        }

        public Criteria andNpositionIn(List<Integer> values) {
            addCriterion("nPosition in", values, "nposition");
            return (Criteria) this;
        }

        public Criteria andNpositionNotIn(List<Integer> values) {
            addCriterion("nPosition not in", values, "nposition");
            return (Criteria) this;
        }

        public Criteria andNpositionBetween(Integer value1, Integer value2) {
            addCriterion("nPosition between", value1, value2, "nposition");
            return (Criteria) this;
        }

        public Criteria andNpositionNotBetween(Integer value1, Integer value2) {
            addCriterion("nPosition not between", value1, value2, "nposition");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeIsNull() {
            addCriterion("nAttachment_content_type is null");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeIsNotNull() {
            addCriterion("nAttachment_content_type is not null");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeEqualTo(String value) {
            addCriterion("nAttachment_content_type =", value, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeNotEqualTo(String value) {
            addCriterion("nAttachment_content_type <>", value, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeGreaterThan(String value) {
            addCriterion("nAttachment_content_type >", value, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("nAttachment_content_type >=", value, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeLessThan(String value) {
            addCriterion("nAttachment_content_type <", value, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeLessThanOrEqualTo(String value) {
            addCriterion("nAttachment_content_type <=", value, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeLike(String value) {
            addCriterion("nAttachment_content_type like", value, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeNotLike(String value) {
            addCriterion("nAttachment_content_type not like", value, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeIn(List<String> values) {
            addCriterion("nAttachment_content_type in", values, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeNotIn(List<String> values) {
            addCriterion("nAttachment_content_type not in", values, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeBetween(String value1, String value2) {
            addCriterion("nAttachment_content_type between", value1, value2, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andNattachmentContentTypeNotBetween(String value1, String value2) {
            addCriterion("nAttachment_content_type not between", value1, value2, "nattachmentContentType");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameIsNull() {
            addCriterion("sAttachment_file_name is null");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameIsNotNull() {
            addCriterion("sAttachment_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameEqualTo(String value) {
            addCriterion("sAttachment_file_name =", value, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameNotEqualTo(String value) {
            addCriterion("sAttachment_file_name <>", value, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameGreaterThan(String value) {
            addCriterion("sAttachment_file_name >", value, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("sAttachment_file_name >=", value, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameLessThan(String value) {
            addCriterion("sAttachment_file_name <", value, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameLessThanOrEqualTo(String value) {
            addCriterion("sAttachment_file_name <=", value, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameLike(String value) {
            addCriterion("sAttachment_file_name like", value, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameNotLike(String value) {
            addCriterion("sAttachment_file_name not like", value, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameIn(List<String> values) {
            addCriterion("sAttachment_file_name in", values, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameNotIn(List<String> values) {
            addCriterion("sAttachment_file_name not in", values, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameBetween(String value1, String value2) {
            addCriterion("sAttachment_file_name between", value1, value2, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andSattachmentFileNameNotBetween(String value1, String value2) {
            addCriterion("sAttachment_file_name not between", value1, value2, "sattachmentFileName");
            return (Criteria) this;
        }

        public Criteria andStypeIsNull() {
            addCriterion("sType is null");
            return (Criteria) this;
        }

        public Criteria andStypeIsNotNull() {
            addCriterion("sType is not null");
            return (Criteria) this;
        }

        public Criteria andStypeEqualTo(String value) {
            addCriterion("sType =", value, "stype");
            return (Criteria) this;
        }

        public Criteria andStypeNotEqualTo(String value) {
            addCriterion("sType <>", value, "stype");
            return (Criteria) this;
        }

        public Criteria andStypeGreaterThan(String value) {
            addCriterion("sType >", value, "stype");
            return (Criteria) this;
        }

        public Criteria andStypeGreaterThanOrEqualTo(String value) {
            addCriterion("sType >=", value, "stype");
            return (Criteria) this;
        }

        public Criteria andStypeLessThan(String value) {
            addCriterion("sType <", value, "stype");
            return (Criteria) this;
        }

        public Criteria andStypeLessThanOrEqualTo(String value) {
            addCriterion("sType <=", value, "stype");
            return (Criteria) this;
        }

        public Criteria andStypeLike(String value) {
            addCriterion("sType like", value, "stype");
            return (Criteria) this;
        }

        public Criteria andStypeNotLike(String value) {
            addCriterion("sType not like", value, "stype");
            return (Criteria) this;
        }

        public Criteria andStypeIn(List<String> values) {
            addCriterion("sType in", values, "stype");
            return (Criteria) this;
        }

        public Criteria andStypeNotIn(List<String> values) {
            addCriterion("sType not in", values, "stype");
            return (Criteria) this;
        }

        public Criteria andStypeBetween(String value1, String value2) {
            addCriterion("sType between", value1, value2, "stype");
            return (Criteria) this;
        }

        public Criteria andStypeNotBetween(String value1, String value2) {
            addCriterion("sType not between", value1, value2, "stype");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtIsNull() {
            addCriterion("sAttachment_updated_at is null");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtIsNotNull() {
            addCriterion("sAttachment_updated_at is not null");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtEqualTo(Date value) {
            addCriterion("sAttachment_updated_at =", value, "sattachmentUpdatedAt");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtNotEqualTo(Date value) {
            addCriterion("sAttachment_updated_at <>", value, "sattachmentUpdatedAt");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtGreaterThan(Date value) {
            addCriterion("sAttachment_updated_at >", value, "sattachmentUpdatedAt");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("sAttachment_updated_at >=", value, "sattachmentUpdatedAt");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtLessThan(Date value) {
            addCriterion("sAttachment_updated_at <", value, "sattachmentUpdatedAt");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtLessThanOrEqualTo(Date value) {
            addCriterion("sAttachment_updated_at <=", value, "sattachmentUpdatedAt");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtIn(List<Date> values) {
            addCriterion("sAttachment_updated_at in", values, "sattachmentUpdatedAt");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtNotIn(List<Date> values) {
            addCriterion("sAttachment_updated_at not in", values, "sattachmentUpdatedAt");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtBetween(Date value1, Date value2) {
            addCriterion("sAttachment_updated_at between", value1, value2, "sattachmentUpdatedAt");
            return (Criteria) this;
        }

        public Criteria andSattachmentUpdatedAtNotBetween(Date value1, Date value2) {
            addCriterion("sAttachment_updated_at not between", value1, value2, "sattachmentUpdatedAt");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthIsNull() {
            addCriterion("nAttachment_width is null");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthIsNotNull() {
            addCriterion("nAttachment_width is not null");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthEqualTo(Integer value) {
            addCriterion("nAttachment_width =", value, "nattachmentWidth");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthNotEqualTo(Integer value) {
            addCriterion("nAttachment_width <>", value, "nattachmentWidth");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthGreaterThan(Integer value) {
            addCriterion("nAttachment_width >", value, "nattachmentWidth");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("nAttachment_width >=", value, "nattachmentWidth");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthLessThan(Integer value) {
            addCriterion("nAttachment_width <", value, "nattachmentWidth");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthLessThanOrEqualTo(Integer value) {
            addCriterion("nAttachment_width <=", value, "nattachmentWidth");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthIn(List<Integer> values) {
            addCriterion("nAttachment_width in", values, "nattachmentWidth");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthNotIn(List<Integer> values) {
            addCriterion("nAttachment_width not in", values, "nattachmentWidth");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthBetween(Integer value1, Integer value2) {
            addCriterion("nAttachment_width between", value1, value2, "nattachmentWidth");
            return (Criteria) this;
        }

        public Criteria andNattachmentWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("nAttachment_width not between", value1, value2, "nattachmentWidth");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightIsNull() {
            addCriterion("nAttachment_height is null");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightIsNotNull() {
            addCriterion("nAttachment_height is not null");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightEqualTo(Integer value) {
            addCriterion("nAttachment_height =", value, "nattachmentHeight");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightNotEqualTo(Integer value) {
            addCriterion("nAttachment_height <>", value, "nattachmentHeight");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightGreaterThan(Integer value) {
            addCriterion("nAttachment_height >", value, "nattachmentHeight");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("nAttachment_height >=", value, "nattachmentHeight");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightLessThan(Integer value) {
            addCriterion("nAttachment_height <", value, "nattachmentHeight");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightLessThanOrEqualTo(Integer value) {
            addCriterion("nAttachment_height <=", value, "nattachmentHeight");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightIn(List<Integer> values) {
            addCriterion("nAttachment_height in", values, "nattachmentHeight");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightNotIn(List<Integer> values) {
            addCriterion("nAttachment_height not in", values, "nattachmentHeight");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightBetween(Integer value1, Integer value2) {
            addCriterion("nAttachment_height between", value1, value2, "nattachmentHeight");
            return (Criteria) this;
        }

        public Criteria andNattachmentHeightNotBetween(Integer value1, Integer value2) {
            addCriterion("nAttachment_height not between", value1, value2, "nattachmentHeight");
            return (Criteria) this;
        }

        public Criteria andSaltIsNull() {
            addCriterion("sAlt is null");
            return (Criteria) this;
        }

        public Criteria andSaltIsNotNull() {
            addCriterion("sAlt is not null");
            return (Criteria) this;
        }

        public Criteria andSaltEqualTo(String value) {
            addCriterion("sAlt =", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotEqualTo(String value) {
            addCriterion("sAlt <>", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThan(String value) {
            addCriterion("sAlt >", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThanOrEqualTo(String value) {
            addCriterion("sAlt >=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThan(String value) {
            addCriterion("sAlt <", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThanOrEqualTo(String value) {
            addCriterion("sAlt <=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLike(String value) {
            addCriterion("sAlt like", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotLike(String value) {
            addCriterion("sAlt not like", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltIn(List<String> values) {
            addCriterion("sAlt in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotIn(List<String> values) {
            addCriterion("sAlt not in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltBetween(String value1, String value2) {
            addCriterion("sAlt between", value1, value2, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotBetween(String value1, String value2) {
            addCriterion("sAlt not between", value1, value2, "salt");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeIsNull() {
            addCriterion("sViewable_type is null");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeIsNotNull() {
            addCriterion("sViewable_type is not null");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeEqualTo(String value) {
            addCriterion("sViewable_type =", value, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeNotEqualTo(String value) {
            addCriterion("sViewable_type <>", value, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeGreaterThan(String value) {
            addCriterion("sViewable_type >", value, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeGreaterThanOrEqualTo(String value) {
            addCriterion("sViewable_type >=", value, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeLessThan(String value) {
            addCriterion("sViewable_type <", value, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeLessThanOrEqualTo(String value) {
            addCriterion("sViewable_type <=", value, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeLike(String value) {
            addCriterion("sViewable_type like", value, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeNotLike(String value) {
            addCriterion("sViewable_type not like", value, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeIn(List<String> values) {
            addCriterion("sViewable_type in", values, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeNotIn(List<String> values) {
            addCriterion("sViewable_type not in", values, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeBetween(String value1, String value2) {
            addCriterion("sViewable_type between", value1, value2, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSviewableTypeNotBetween(String value1, String value2) {
            addCriterion("sViewable_type not between", value1, value2, "sviewableType");
            return (Criteria) this;
        }

        public Criteria andSminiUrlIsNull() {
            addCriterion("sMini_url is null");
            return (Criteria) this;
        }

        public Criteria andSminiUrlIsNotNull() {
            addCriterion("sMini_url is not null");
            return (Criteria) this;
        }

        public Criteria andSminiUrlEqualTo(String value) {
            addCriterion("sMini_url =", value, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSminiUrlNotEqualTo(String value) {
            addCriterion("sMini_url <>", value, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSminiUrlGreaterThan(String value) {
            addCriterion("sMini_url >", value, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSminiUrlGreaterThanOrEqualTo(String value) {
            addCriterion("sMini_url >=", value, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSminiUrlLessThan(String value) {
            addCriterion("sMini_url <", value, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSminiUrlLessThanOrEqualTo(String value) {
            addCriterion("sMini_url <=", value, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSminiUrlLike(String value) {
            addCriterion("sMini_url like", value, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSminiUrlNotLike(String value) {
            addCriterion("sMini_url not like", value, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSminiUrlIn(List<String> values) {
            addCriterion("sMini_url in", values, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSminiUrlNotIn(List<String> values) {
            addCriterion("sMini_url not in", values, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSminiUrlBetween(String value1, String value2) {
            addCriterion("sMini_url between", value1, value2, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSminiUrlNotBetween(String value1, String value2) {
            addCriterion("sMini_url not between", value1, value2, "sminiUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlIsNull() {
            addCriterion("sSmall_url is null");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlIsNotNull() {
            addCriterion("sSmall_url is not null");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlEqualTo(String value) {
            addCriterion("sSmall_url =", value, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlNotEqualTo(String value) {
            addCriterion("sSmall_url <>", value, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlGreaterThan(String value) {
            addCriterion("sSmall_url >", value, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlGreaterThanOrEqualTo(String value) {
            addCriterion("sSmall_url >=", value, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlLessThan(String value) {
            addCriterion("sSmall_url <", value, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlLessThanOrEqualTo(String value) {
            addCriterion("sSmall_url <=", value, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlLike(String value) {
            addCriterion("sSmall_url like", value, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlNotLike(String value) {
            addCriterion("sSmall_url not like", value, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlIn(List<String> values) {
            addCriterion("sSmall_url in", values, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlNotIn(List<String> values) {
            addCriterion("sSmall_url not in", values, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlBetween(String value1, String value2) {
            addCriterion("sSmall_url between", value1, value2, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSsmallUrlNotBetween(String value1, String value2) {
            addCriterion("sSmall_url not between", value1, value2, "ssmallUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlIsNull() {
            addCriterion("sLarge_url is null");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlIsNotNull() {
            addCriterion("sLarge_url is not null");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlEqualTo(String value) {
            addCriterion("sLarge_url =", value, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlNotEqualTo(String value) {
            addCriterion("sLarge_url <>", value, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlGreaterThan(String value) {
            addCriterion("sLarge_url >", value, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlGreaterThanOrEqualTo(String value) {
            addCriterion("sLarge_url >=", value, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlLessThan(String value) {
            addCriterion("sLarge_url <", value, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlLessThanOrEqualTo(String value) {
            addCriterion("sLarge_url <=", value, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlLike(String value) {
            addCriterion("sLarge_url like", value, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlNotLike(String value) {
            addCriterion("sLarge_url not like", value, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlIn(List<String> values) {
            addCriterion("sLarge_url in", values, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlNotIn(List<String> values) {
            addCriterion("sLarge_url not in", values, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlBetween(String value1, String value2) {
            addCriterion("sLarge_url between", value1, value2, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSlargeUrlNotBetween(String value1, String value2) {
            addCriterion("sLarge_url not between", value1, value2, "slargeUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlIsNull() {
            addCriterion("sProduct_url is null");
            return (Criteria) this;
        }

        public Criteria andSproductUrlIsNotNull() {
            addCriterion("sProduct_url is not null");
            return (Criteria) this;
        }

        public Criteria andSproductUrlEqualTo(String value) {
            addCriterion("sProduct_url =", value, "sproductUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlNotEqualTo(String value) {
            addCriterion("sProduct_url <>", value, "sproductUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlGreaterThan(String value) {
            addCriterion("sProduct_url >", value, "sproductUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlGreaterThanOrEqualTo(String value) {
            addCriterion("sProduct_url >=", value, "sproductUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlLessThan(String value) {
            addCriterion("sProduct_url <", value, "sproductUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlLessThanOrEqualTo(String value) {
            addCriterion("sProduct_url <=", value, "sproductUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlLike(String value) {
            addCriterion("sProduct_url like", value, "sproductUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlNotLike(String value) {
            addCriterion("sProduct_url not like", value, "sproductUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlIn(List<String> values) {
            addCriterion("sProduct_url in", values, "sproductUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlNotIn(List<String> values) {
            addCriterion("sProduct_url not in", values, "sproductUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlBetween(String value1, String value2) {
            addCriterion("sProduct_url between", value1, value2, "sproductUrl");
            return (Criteria) this;
        }

        public Criteria andSproductUrlNotBetween(String value1, String value2) {
            addCriterion("sProduct_url not between", value1, value2, "sproductUrl");
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