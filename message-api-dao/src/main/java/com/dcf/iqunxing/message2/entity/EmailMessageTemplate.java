package com.dcf.iqunxing.message2.entity;

import java.util.Date;

public class EmailMessageTemplate {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.id
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.name
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.title
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.content
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.type
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private Short type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.state
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private Byte state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.priority
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private Byte priority;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.sender_name
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private String senderName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.from_address
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private String fromAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.description
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.created_time
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private Date createdTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.created_by
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private String createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.updated_time
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private Date updatedTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.updated_by
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private String updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.deleted_time
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private Date deletedTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.deleted_by
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private String deletedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.deleted
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private Byte deleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_message_template.datachange_lasttime
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    private Date datachangeLasttime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.id
     *
     * @return the value of email_message_template.id
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.id
     *
     * @param id the value for email_message_template.id
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.name
     *
     * @return the value of email_message_template.name
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.name
     *
     * @param name the value for email_message_template.name
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.title
     *
     * @return the value of email_message_template.title
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.title
     *
     * @param title the value for email_message_template.title
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.content
     *
     * @return the value of email_message_template.content
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.content
     *
     * @param content the value for email_message_template.content
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.type
     *
     * @return the value of email_message_template.type
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public Short getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.type
     *
     * @param type the value for email_message_template.type
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.state
     *
     * @return the value of email_message_template.state
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.state
     *
     * @param state the value for email_message_template.state
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.priority
     *
     * @return the value of email_message_template.priority
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public Byte getPriority() {
        return priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.priority
     *
     * @param priority the value for email_message_template.priority
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.sender_name
     *
     * @return the value of email_message_template.sender_name
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.sender_name
     *
     * @param senderName the value for email_message_template.sender_name
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName == null ? null : senderName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.from_address
     *
     * @return the value of email_message_template.from_address
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public String getFromAddress() {
        return fromAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.from_address
     *
     * @param fromAddress the value for email_message_template.from_address
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress == null ? null : fromAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.description
     *
     * @return the value of email_message_template.description
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.description
     *
     * @param description the value for email_message_template.description
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.created_time
     *
     * @return the value of email_message_template.created_time
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.created_time
     *
     * @param createdTime the value for email_message_template.created_time
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.created_by
     *
     * @return the value of email_message_template.created_by
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.created_by
     *
     * @param createdBy the value for email_message_template.created_by
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.updated_time
     *
     * @return the value of email_message_template.updated_time
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.updated_time
     *
     * @param updatedTime the value for email_message_template.updated_time
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.updated_by
     *
     * @return the value of email_message_template.updated_by
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.updated_by
     *
     * @param updatedBy the value for email_message_template.updated_by
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.deleted_time
     *
     * @return the value of email_message_template.deleted_time
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public Date getDeletedTime() {
        return deletedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.deleted_time
     *
     * @param deletedTime the value for email_message_template.deleted_time
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setDeletedTime(Date deletedTime) {
        this.deletedTime = deletedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.deleted_by
     *
     * @return the value of email_message_template.deleted_by
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public String getDeletedBy() {
        return deletedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.deleted_by
     *
     * @param deletedBy the value for email_message_template.deleted_by
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy == null ? null : deletedBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.deleted
     *
     * @return the value of email_message_template.deleted
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public Byte getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.deleted
     *
     * @param deleted the value for email_message_template.deleted
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_message_template.datachange_lasttime
     *
     * @return the value of email_message_template.datachange_lasttime
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public Date getDatachangeLasttime() {
        return datachangeLasttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_message_template.datachange_lasttime
     *
     * @param datachangeLasttime the value for email_message_template.datachange_lasttime
     *
     * @mbggenerated Mon Feb 22 15:00:46 CST 2016
     */
    public void setDatachangeLasttime(Date datachangeLasttime) {
        this.datachangeLasttime = datachangeLasttime;
    }
}