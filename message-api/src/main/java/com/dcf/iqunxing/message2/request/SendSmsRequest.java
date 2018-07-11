package com.dcf.iqunxing.message2.request;

import java.util.Map;

/**
 * 发送短信的请求
 * 
 * @author zhangjiwei
 */
public class SendSmsRequest extends BaseRequest {

    private static final long serialVersionUID = -1294778451801314404L;

    /**
     * 短信号码，多个号码以逗号分割
     */
    protected String mobiles;

    /**
     * 短信模板id
     */
    protected Long templateId;

    /**
     * 短信内容的变量与值
     */
    protected Map<String, String> properties;

    /**
     * 发送时间。定时消息需填，即时消息不设
     */
    protected Long scheduleUtcTime;

    /**
     * 短信的过期时间，如果超过此时间，短信还未发送，即放弃发送。必填项。
     */
    protected Long expiredUtcTime;

    /**
     * 创建短信的人
     */
    protected String userId;

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
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
