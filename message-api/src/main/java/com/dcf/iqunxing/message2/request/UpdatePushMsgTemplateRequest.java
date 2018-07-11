package com.dcf.iqunxing.message2.request;

import com.dcf.iqunxing.message2.model.enums.MsgPriority;

public class UpdatePushMsgTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = -1735103014285359504L;

    protected Long id;

    protected String name;

    protected String title;

    protected String content;

    protected String addition;

    protected Short type;

    protected MsgPriority priority;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
