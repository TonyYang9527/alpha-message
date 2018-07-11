package com.dcf.iqunxing.message2.request;

import java.util.Map;

/**
 * 发送push的请求
 * 
 * @author jingguo
 */
public class SendPushRequest extends BaseRequest {

    private static final long serialVersionUID = 6906059954848469833L;

    /**
     * 接收方userId
     */
    protected String receiverId;

    /**
     * push模板id
     */
    protected Long pushMessageTemplateId;

    /**
     * push内容的变量与值
     */
    protected Map<String, String> properties;

    /**
     * 发送时间。定时消息需填，即时消息不设
     */
    protected Long scheduleUtcTime;

    /**
     * push的过期时间，如果超过此时间，push还未发送，即放弃发送。必填项。
     */
    protected Long expiredUtcTime;

    /**
     * 创建邮件的人
     */
    protected String userId;

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Long getPushMessageTemplateId() {
        return pushMessageTemplateId;
    }

    public void setPushMessageTemplateId(Long pushMessageTemplateId) {
        this.pushMessageTemplateId = pushMessageTemplateId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
