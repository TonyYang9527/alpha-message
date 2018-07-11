package com.dcf.iqunxing.message2.request;

import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.enums.SmsChannel;

public class UpdateSmsMsgTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = -3414544566298581976L;

    protected Long id;

    protected String name;

    protected String content;

    protected Short type;

    protected MsgPriority priority;

    protected SmsChannel smsChannel;

    protected String operator;

    protected String description;

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

    public MsgPriority getPriority() {
        return priority;
    }

    public void setPriority(MsgPriority priority) {
        this.priority = priority;
    }

    public SmsChannel getSmsChannel() {
        return smsChannel;
    }

    public void setSmsChannel(SmsChannel smsChannel) {
        this.smsChannel = smsChannel;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
