package com.dcf.iqunxing.message2.service.channel.entity;

import java.io.Serializable;

import com.dcf.iqunxing.message2.model.enums.MsgType;

/**
 * 通道统一请求对象.
 */
public class ChannelReq implements Serializable {

    private static final long serialVersionUID = 338304648259222627L;

    /**************************** 通用 ****************************/
    /** The id. */
    private Long id;

    /** 发送目标. */
    private String[] target;

    /** 发送内容. */
    private String content;

    /** 消息类型. */
    private MsgType msgType;

    /** 消息发送渠道. */
    private short type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getTarget() {
        return target;
    }

    public void setTarget(String[] target) {
        this.target = target;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

}
