package com.dcf.iqunxing.message2.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmsMessageExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    protected Integer limitStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    protected Integer limitEnd;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public SmsMessageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public Integer getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
    public Integer getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdIsNull() {
            addCriterion("sms_message_template_id is null");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdIsNotNull() {
            addCriterion("sms_message_template_id is not null");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdEqualTo(Long value) {
            addCriterion("sms_message_template_id =", value, "smsMessageTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdNotEqualTo(Long value) {
            addCriterion("sms_message_template_id <>", value, "smsMessageTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdGreaterThan(Long value) {
            addCriterion("sms_message_template_id >", value, "smsMessageTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdGreaterThanOrEqualTo(Long value) {
            addCriterion("sms_message_template_id >=", value, "smsMessageTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdLessThan(Long value) {
            addCriterion("sms_message_template_id <", value, "smsMessageTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdLessThanOrEqualTo(Long value) {
            addCriterion("sms_message_template_id <=", value, "smsMessageTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdIn(List<Long> values) {
            addCriterion("sms_message_template_id in", values, "smsMessageTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdNotIn(List<Long> values) {
            addCriterion("sms_message_template_id not in", values, "smsMessageTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdBetween(Long value1, Long value2) {
            addCriterion("sms_message_template_id between", value1, value2, "smsMessageTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsMessageTemplateIdNotBetween(Long value1, Long value2) {
            addCriterion("sms_message_template_id not between", value1, value2, "smsMessageTemplateId");
            return (Criteria) this;
        }

        public Criteria andMobilesIsNull() {
            addCriterion("mobiles is null");
            return (Criteria) this;
        }

        public Criteria andMobilesIsNotNull() {
            addCriterion("mobiles is not null");
            return (Criteria) this;
        }

        public Criteria andMobilesEqualTo(String value) {
            addCriterion("mobiles =", value, "mobiles");
            return (Criteria) this;
        }

        public Criteria andMobilesNotEqualTo(String value) {
            addCriterion("mobiles <>", value, "mobiles");
            return (Criteria) this;
        }

        public Criteria andMobilesGreaterThan(String value) {
            addCriterion("mobiles >", value, "mobiles");
            return (Criteria) this;
        }

        public Criteria andMobilesGreaterThanOrEqualTo(String value) {
            addCriterion("mobiles >=", value, "mobiles");
            return (Criteria) this;
        }

        public Criteria andMobilesLessThan(String value) {
            addCriterion("mobiles <", value, "mobiles");
            return (Criteria) this;
        }

        public Criteria andMobilesLessThanOrEqualTo(String value) {
            addCriterion("mobiles <=", value, "mobiles");
            return (Criteria) this;
        }

        public Criteria andMobilesLike(String value) {
            addCriterion("mobiles like", value, "mobiles");
            return (Criteria) this;
        }

        public Criteria andMobilesNotLike(String value) {
            addCriterion("mobiles not like", value, "mobiles");
            return (Criteria) this;
        }

        public Criteria andMobilesIn(List<String> values) {
            addCriterion("mobiles in", values, "mobiles");
            return (Criteria) this;
        }

        public Criteria andMobilesNotIn(List<String> values) {
            addCriterion("mobiles not in", values, "mobiles");
            return (Criteria) this;
        }

        public Criteria andMobilesBetween(String value1, String value2) {
            addCriterion("mobiles between", value1, value2, "mobiles");
            return (Criteria) this;
        }

        public Criteria andMobilesNotBetween(String value1, String value2) {
            addCriterion("mobiles not between", value1, value2, "mobiles");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNull() {
            addCriterion("priority is null");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNotNull() {
            addCriterion("priority is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityEqualTo(Byte value) {
            addCriterion("priority =", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotEqualTo(Byte value) {
            addCriterion("priority <>", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThan(Byte value) {
            addCriterion("priority >", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThanOrEqualTo(Byte value) {
            addCriterion("priority >=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThan(Byte value) {
            addCriterion("priority <", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThanOrEqualTo(Byte value) {
            addCriterion("priority <=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityIn(List<Byte> values) {
            addCriterion("priority in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotIn(List<Byte> values) {
            addCriterion("priority not in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityBetween(Byte value1, Byte value2) {
            addCriterion("priority between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotBetween(Byte value1, Byte value2) {
            addCriterion("priority not between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Byte value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Byte value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Byte value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Byte value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Byte value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Byte> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Byte> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Byte value1, Byte value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Byte value1, Byte value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andImmediateIsNull() {
            addCriterion("immediate is null");
            return (Criteria) this;
        }

        public Criteria andImmediateIsNotNull() {
            addCriterion("immediate is not null");
            return (Criteria) this;
        }

        public Criteria andImmediateEqualTo(Byte value) {
            addCriterion("immediate =", value, "immediate");
            return (Criteria) this;
        }

        public Criteria andImmediateNotEqualTo(Byte value) {
            addCriterion("immediate <>", value, "immediate");
            return (Criteria) this;
        }

        public Criteria andImmediateGreaterThan(Byte value) {
            addCriterion("immediate >", value, "immediate");
            return (Criteria) this;
        }

        public Criteria andImmediateGreaterThanOrEqualTo(Byte value) {
            addCriterion("immediate >=", value, "immediate");
            return (Criteria) this;
        }

        public Criteria andImmediateLessThan(Byte value) {
            addCriterion("immediate <", value, "immediate");
            return (Criteria) this;
        }

        public Criteria andImmediateLessThanOrEqualTo(Byte value) {
            addCriterion("immediate <=", value, "immediate");
            return (Criteria) this;
        }

        public Criteria andImmediateIn(List<Byte> values) {
            addCriterion("immediate in", values, "immediate");
            return (Criteria) this;
        }

        public Criteria andImmediateNotIn(List<Byte> values) {
            addCriterion("immediate not in", values, "immediate");
            return (Criteria) this;
        }

        public Criteria andImmediateBetween(Byte value1, Byte value2) {
            addCriterion("immediate between", value1, value2, "immediate");
            return (Criteria) this;
        }

        public Criteria andImmediateNotBetween(Byte value1, Byte value2) {
            addCriterion("immediate not between", value1, value2, "immediate");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeIsNull() {
            addCriterion("schedule_time is null");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeIsNotNull() {
            addCriterion("schedule_time is not null");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeEqualTo(Date value) {
            addCriterion("schedule_time =", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeNotEqualTo(Date value) {
            addCriterion("schedule_time <>", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeGreaterThan(Date value) {
            addCriterion("schedule_time >", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("schedule_time >=", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeLessThan(Date value) {
            addCriterion("schedule_time <", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeLessThanOrEqualTo(Date value) {
            addCriterion("schedule_time <=", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeIn(List<Date> values) {
            addCriterion("schedule_time in", values, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeNotIn(List<Date> values) {
            addCriterion("schedule_time not in", values, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeBetween(Date value1, Date value2) {
            addCriterion("schedule_time between", value1, value2, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeNotBetween(Date value1, Date value2) {
            addCriterion("schedule_time not between", value1, value2, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeIsNull() {
            addCriterion("expired_time is null");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeIsNotNull() {
            addCriterion("expired_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeEqualTo(Date value) {
            addCriterion("expired_time =", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotEqualTo(Date value) {
            addCriterion("expired_time <>", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeGreaterThan(Date value) {
            addCriterion("expired_time >", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("expired_time >=", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeLessThan(Date value) {
            addCriterion("expired_time <", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeLessThanOrEqualTo(Date value) {
            addCriterion("expired_time <=", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeIn(List<Date> values) {
            addCriterion("expired_time in", values, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotIn(List<Date> values) {
            addCriterion("expired_time not in", values, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeBetween(Date value1, Date value2) {
            addCriterion("expired_time between", value1, value2, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotBetween(Date value1, Date value2) {
            addCriterion("expired_time not between", value1, value2, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeIsNull() {
            addCriterion("sent_time is null");
            return (Criteria) this;
        }

        public Criteria andSentTimeIsNotNull() {
            addCriterion("sent_time is not null");
            return (Criteria) this;
        }

        public Criteria andSentTimeEqualTo(Date value) {
            addCriterion("sent_time =", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeNotEqualTo(Date value) {
            addCriterion("sent_time <>", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeGreaterThan(Date value) {
            addCriterion("sent_time >", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sent_time >=", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeLessThan(Date value) {
            addCriterion("sent_time <", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeLessThanOrEqualTo(Date value) {
            addCriterion("sent_time <=", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeIn(List<Date> values) {
            addCriterion("sent_time in", values, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeNotIn(List<Date> values) {
            addCriterion("sent_time not in", values, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeBetween(Date value1, Date value2) {
            addCriterion("sent_time between", value1, value2, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeNotBetween(Date value1, Date value2) {
            addCriterion("sent_time not between", value1, value2, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentResultIsNull() {
            addCriterion("sent_result is null");
            return (Criteria) this;
        }

        public Criteria andSentResultIsNotNull() {
            addCriterion("sent_result is not null");
            return (Criteria) this;
        }

        public Criteria andSentResultEqualTo(String value) {
            addCriterion("sent_result =", value, "sentResult");
            return (Criteria) this;
        }

        public Criteria andSentResultNotEqualTo(String value) {
            addCriterion("sent_result <>", value, "sentResult");
            return (Criteria) this;
        }

        public Criteria andSentResultGreaterThan(String value) {
            addCriterion("sent_result >", value, "sentResult");
            return (Criteria) this;
        }

        public Criteria andSentResultGreaterThanOrEqualTo(String value) {
            addCriterion("sent_result >=", value, "sentResult");
            return (Criteria) this;
        }

        public Criteria andSentResultLessThan(String value) {
            addCriterion("sent_result <", value, "sentResult");
            return (Criteria) this;
        }

        public Criteria andSentResultLessThanOrEqualTo(String value) {
            addCriterion("sent_result <=", value, "sentResult");
            return (Criteria) this;
        }

        public Criteria andSentResultLike(String value) {
            addCriterion("sent_result like", value, "sentResult");
            return (Criteria) this;
        }

        public Criteria andSentResultNotLike(String value) {
            addCriterion("sent_result not like", value, "sentResult");
            return (Criteria) this;
        }

        public Criteria andSentResultIn(List<String> values) {
            addCriterion("sent_result in", values, "sentResult");
            return (Criteria) this;
        }

        public Criteria andSentResultNotIn(List<String> values) {
            addCriterion("sent_result not in", values, "sentResult");
            return (Criteria) this;
        }

        public Criteria andSentResultBetween(String value1, String value2) {
            addCriterion("sent_result between", value1, value2, "sentResult");
            return (Criteria) this;
        }

        public Criteria andSentResultNotBetween(String value1, String value2) {
            addCriterion("sent_result not between", value1, value2, "sentResult");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNull() {
            addCriterion("created_time is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNotNull() {
            addCriterion("created_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeEqualTo(Date value) {
            addCriterion("created_time =", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotEqualTo(Date value) {
            addCriterion("created_time <>", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThan(Date value) {
            addCriterion("created_time >", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("created_time >=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThan(Date value) {
            addCriterion("created_time <", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("created_time <=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIn(List<Date> values) {
            addCriterion("created_time in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotIn(List<Date> values) {
            addCriterion("created_time not in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeBetween(Date value1, Date value2) {
            addCriterion("created_time between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("created_time not between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(String value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(String value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(String value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(String value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(String value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(String value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLike(String value) {
            addCriterion("created_by like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotLike(String value) {
            addCriterion("created_by not like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<String> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<String> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(String value1, String value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(String value1, String value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeIsNull() {
            addCriterion("DataChange_LastTime is null");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeIsNotNull() {
            addCriterion("DataChange_LastTime is not null");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeEqualTo(Date value) {
            addCriterion("DataChange_LastTime =", value, "datachangeLasttime");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeNotEqualTo(Date value) {
            addCriterion("DataChange_LastTime <>", value, "datachangeLasttime");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeGreaterThan(Date value) {
            addCriterion("DataChange_LastTime >", value, "datachangeLasttime");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("DataChange_LastTime >=", value, "datachangeLasttime");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeLessThan(Date value) {
            addCriterion("DataChange_LastTime <", value, "datachangeLasttime");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeLessThanOrEqualTo(Date value) {
            addCriterion("DataChange_LastTime <=", value, "datachangeLasttime");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeIn(List<Date> values) {
            addCriterion("DataChange_LastTime in", values, "datachangeLasttime");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeNotIn(List<Date> values) {
            addCriterion("DataChange_LastTime not in", values, "datachangeLasttime");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeBetween(Date value1, Date value2) {
            addCriterion("DataChange_LastTime between", value1, value2, "datachangeLasttime");
            return (Criteria) this;
        }

        public Criteria andDatachangeLasttimeNotBetween(Date value1, Date value2) {
            addCriterion("DataChange_LastTime not between", value1, value2, "datachangeLasttime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sms_message
     *
     * @mbggenerated do_not_delete_during_merge Mon Aug 10 15:03:32 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sms_message
     *
     * @mbggenerated Mon Aug 10 15:03:32 CST 2015
     */
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