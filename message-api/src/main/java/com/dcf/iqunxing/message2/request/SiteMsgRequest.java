package com.dcf.iqunxing.message2.request;

import java.util.Map;

import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.enums.SiteMsgType;

public class SiteMsgRequest extends BaseRequest {

    private static final long serialVersionUID = 4343607458341401299L;

    /**
     * 站内信接受者，用户pkey
     */
    protected String receiverId;

    /**
     * 站内信模板主键ID
     */
    protected Long siteMessageTemplateId;

    /**
     * 站内信模板内容的变量与值
     */
    protected Map<String, String> properties;

    /**
     * 站内信发送时间。定时消息需填，即时消息不设
     */
    protected Long scheduleUtcTime;

    /**
     * 站内信的过期时间，如果超过此时间，站内信还未发送，即放弃发送。必填项。
     */
    protected Long expiredUtcTime;

    /**
     * 消息发送者，用户pkey或者其他
     */
    protected String sender;

    /**
     * 操作者
     */
    protected String operator;
    
    protected Short newType;

    protected SiteMsgType type;

    protected MsgPriority priority;

    protected String title;

    protected String content;

    protected String addition;

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Long getSiteMessageTemplateId() {
        return siteMessageTemplateId;
    }

    public void setSiteMessageTemplateId(Long siteMessageTemplateId) {
        this.siteMessageTemplateId = siteMessageTemplateId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Long getScheduleUtcTime() {
        return scheduleUtcTime;
    }

    public void setScheduleUtcTime(Long scheduleUtcTime) {
        this.scheduleUtcTime = scheduleUtcTime;
    }

    public Long getExpiredUtcTime() {
        return expiredUtcTime;
    }

    public void setExpiredUtcTime(Long expiredUtcTime) {
        this.expiredUtcTime = expiredUtcTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public SiteMsgType getType() {
        return type;
    }

    public void setType(SiteMsgType type) {
        this.type = type;
    }

    public MsgPriority getPriority() {
        return priority;
    }

    public void setPriority(MsgPriority priority) {
        this.priority = priority;
    }

    
    public Short getNewType() {
        return newType;
    }

    
    public void setNewType(Short newType) {
        this.newType = newType;
    }
}
