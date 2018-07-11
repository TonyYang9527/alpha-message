package com.dcf.iqunxing.message2.service.channel.entity;

import java.io.Serializable;

import com.dcf.iqunxing.message2.model.enums.MsgType;

/**
 * 通道统一返回对象.
 */
public class ChannelResp implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2248014161297002755L;

    /** 传入的msgId. */
    private Long id;

    /** 消息类型. */
    private MsgType msgType;

    /** 是否成功. */
    private boolean isSuccess;

    /** 网关返回代码. */
    private String code;

    /** 网关返回信息. */
    private String result;

    /**
     * Instantiates a new channel result.
     *
     * @param id
     *            传入的msgId
     * @param msgType
     *            消息类型
     * @param isSuccess
     *            是否成功
     * @param code
     *            网关返回代码
     * @param result
     *            网关返回信息
     */
    public ChannelResp(Long id, MsgType msgType, boolean isSuccess, String code, String result) {
        this.id = id;
        this.msgType = msgType;
        this.isSuccess = isSuccess;
        this.code = code;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getCode() {
        return code;
    }

    public String getResult() {
        return result;
    }

}
