package com.dcf.iqunxing.message2.model;

import java.io.Serializable;
import java.util.Date;

import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.enums.MsgTemplateState;
import com.dcf.iqunxing.message2.model.enums.SmsChannel;

public class SmsMsgTemplateVo implements Serializable {

    private static final long serialVersionUID = 5591967456593800489L;

    private Long id;

    private String name;

    private String content;

    private Short type;

    private MsgPriority msgPriority;

    private MsgTemplateState state;

    private SmsChannel smsChannel;

    private String description;

    private Date createdTime;

    private String createdBy;

    private Date updatedTime;

    private String updatedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public MsgPriority getMsgPriority() {
        return msgPriority;
    }

    public void setMsgPriority(MsgPriority priority) {
        this.msgPriority = priority;
    }

    public SmsChannel getSmsChannel() {
        return smsChannel;
    }

    public void setSmsChannel(SmsChannel smsChannel) {
        this.smsChannel = smsChannel;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public MsgTemplateState getState() {
        return state;
    }

    public void setState(MsgTemplateState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SmsMsgTemplateVo [id=" + id + ", name=" + name + ", content=" + content + ", type=" + type + ", msgPriority="
                + msgPriority + ", state=" + state + ", smsChannel=" + smsChannel + ", description=" + description
                + ", createdTime=" + createdTime + ", createdBy=" + createdBy + ", updatedTime=" + updatedTime + ", updatedBy="
                + updatedBy + "]";
    }

}
