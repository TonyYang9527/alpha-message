package com.dcf.iqunxing.message2.request;

import java.util.Map;

/**
 * 发送短信的请求
 * 
 * @author zhangjiwei
 */
public class SendEmailRequest extends BaseRequest {

    private static final long serialVersionUID = -2116190552894989609L;

    /**
     * 接收方地址，多个地址用'|'分割
     */
    protected String toAddresses;

    /**
     * 抄送方地址，多个地址用'|'分割
     */
    protected String ccAddresses;

    /**
     * 暗送方地址，多个地址用'|'分割
     */
    protected String bccAddresses;

    /**
     * 邮件模板id
     */
    protected Long emailMessageTemplateId;

    /**
     * 邮件内容的变量与值
     */
    protected Map<String, String> properties;

    /**
     * 发送时间。定时消息需填，即时消息不设
     */
    protected Long scheduleUtcTime;

    /**
     * 邮件的过期时间，如果超过此时间，邮件还未发送，即放弃发送。必填项。
     */
    protected Long expiredUtcTime;

    /**
     * 创建邮件的人
     */
    protected String userId;

    /**
     * 附件名称,文件IO
     */
    protected Map<String, byte[]> attachments;

    public String getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(String toAddresses) {
        this.toAddresses = toAddresses;
    }

    public String getCcAddresses() {
        return ccAddresses;
    }

    public void setCcAddresses(String ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public String getBccAddresses() {
        return bccAddresses;
    }

    public void setBccAddresses(String bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    public Long getEmailMessageTemplateId() {
        return emailMessageTemplateId;
    }

    public void setEmailMessageTemplateId(Long emailMessageTemplateId) {
        this.emailMessageTemplateId = emailMessageTemplateId;
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

    public Map<String, byte[]> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, byte[]> attachments) {
        this.attachments = attachments;
    }
}
