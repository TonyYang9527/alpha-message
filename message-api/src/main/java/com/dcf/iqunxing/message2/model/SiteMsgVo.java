package com.dcf.iqunxing.message2.model;

import java.io.Serializable;
import java.util.Date;

import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.enums.MsgState;
import com.dcf.iqunxing.message2.model.enums.SiteMsgType;

public class SiteMsgVo implements Serializable {

    private static final long serialVersionUID = 6745477181027202035L;

    private Long id;

    private String siteMessageContent;

    private String siteMessageAddition;

    private String receiverId;

    private String sender;

    private String title;

    private Short newType;

    private SiteMsgType type;

    private MsgPriority msgPriority;

    private MsgState msgState;

    private boolean immediate;

    private Date scheduleTime;

    private Date expiredTime;

    private Date sentTime;

    private Date readTime;

    private Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteMessageContent() {
        return siteMessageContent;
    }

    public void setSiteMessageContent(String siteMessageContent) {
        this.siteMessageContent = siteMessageContent;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
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

    public SiteMsgType getType() {
        return type;
    }

    public void setType(SiteMsgType type) {
        this.type = type;
    }

    public MsgPriority getMsgPriority() {
        return msgPriority;
    }

    public void setMsgPriority(MsgPriority msgPriority) {
        this.msgPriority = msgPriority;
    }

    public MsgState getMsgState() {
        return msgState;
    }

    public void setMsgState(MsgState msgState) {
        this.msgState = msgState;
    }

    public boolean isImmediate() {
        return immediate;
    }

    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getSiteMessageAddition() {
        return siteMessageAddition;
    }

    public void setSiteMessageAddition(String siteMessageAddition) {
        this.siteMessageAddition = siteMessageAddition;
    }

    public Short getNewType() {
        return newType;
    }

    public void setNewType(Short newType) {
        this.newType = newType;
    }

    @Override
    public String toString() {
        return "SiteMsgVo [id=" + id + ", siteMessageContent=" + siteMessageContent + ", siteMessageAddition="
                + siteMessageAddition + ", receiverId=" + receiverId + ", sender=" + sender + ", newType=" + newType + ", msgPriority="
                + msgPriority + ", msgState=" + msgState + ", immediate=" + immediate + ", scheduleTime=" + scheduleTime
                + ", expiredTime=" + expiredTime + ", sentTime=" + sentTime + ", readTime=" + readTime + ", createdTime="
                + createdTime + "]";
    }

}
