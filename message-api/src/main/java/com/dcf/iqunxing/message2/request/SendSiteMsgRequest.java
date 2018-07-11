package com.dcf.iqunxing.message2.request;

/**
 * 发送站内信的请求
 * 
 * @author lijun
 */
public class SendSiteMsgRequest extends SiteMsgRequest {

    private static final long serialVersionUID = 6496653667162558639L;

    /**
     * 消息id，如果这条消息已被创建；如果id被填入，其他字段忽略
     */
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
