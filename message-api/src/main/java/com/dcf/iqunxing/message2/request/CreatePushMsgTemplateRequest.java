package com.dcf.iqunxing.message2.request;

import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.enums.MsgTemplateState;

public class CreatePushMsgTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = 2708846200373247335L;

    protected String name;

    protected String title;

    protected String content;

    protected String addition;

    protected short type;

    protected MsgPriority priority;

    protected MsgTemplateState state;

    protected String operator;

    protected String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
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
