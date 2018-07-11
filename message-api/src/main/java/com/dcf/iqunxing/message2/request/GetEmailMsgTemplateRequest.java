package com.dcf.iqunxing.message2.request;

public class GetEmailMsgTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = -3351093216792084799L;

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
