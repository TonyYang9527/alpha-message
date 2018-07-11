package com.dcf.iqunxing.message2.request;

import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.enums.MsgTemplateState;
import com.dcf.iqunxing.message2.model.enums.SmsChannel;

public class CreateSmsMsgTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = -1075831271485106113L;

    protected String name;

    protected String content;

    protected short type;

    protected MsgPriority priority;

    protected MsgTemplateState state;

    protected SmsChannel smsChannel;

    protected String operator;

    protected String description;

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

    public short getType() {
        return type;
    }

    public void setType(short type) {
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

    public MsgTemplateState getState() {
        return state;
    }

    public void setState(MsgTemplateState state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
