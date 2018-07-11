package com.dcf.iqunxing.message2.request;

public class UpdateSiteMsgRequest extends SiteMsgRequest {

    private static final long serialVersionUID = -5903864034544014297L;

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
