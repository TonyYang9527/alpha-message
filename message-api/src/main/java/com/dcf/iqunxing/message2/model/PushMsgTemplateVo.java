package com.dcf.iqunxing.message2.model;

import java.io.Serializable;
import java.util.Date;

import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.enums.MsgTemplateState;

public class PushMsgTemplateVo implements Serializable {

    private static final long serialVersionUID = -3537233199138339931L;

    private Long id;

    private String name;

    private String title;

    private String content;

    private Short type;

    private String addition;

    private MsgPriority msgPriority;

    private MsgTemplateState state;

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

    public MsgPriority getMsgPriority() {
        return msgPriority;
    }

    public void setMsgPriority(MsgPriority priority) {
        this.msgPriority = priority;
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

    @Override
    public String toString() {
        return "PushMsgTemplateVo [id=" + id + ", name=" + name + ", title=" + title + ", content=" + content + ", type=" + type
                + ", addition=" + addition + ", msgPriority=" + msgPriority + ", state=" + state + ", description=" + description
                + ", createdTime=" + createdTime + ", createdBy=" + createdBy + ", updatedTime=" + updatedTime + ", updatedBy="
                + updatedBy + "]";
    }

}
