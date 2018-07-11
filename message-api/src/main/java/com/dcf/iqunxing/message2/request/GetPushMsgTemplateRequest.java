package com.dcf.iqunxing.message2.request;

public class GetPushMsgTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = 5130368555486689343L;

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
