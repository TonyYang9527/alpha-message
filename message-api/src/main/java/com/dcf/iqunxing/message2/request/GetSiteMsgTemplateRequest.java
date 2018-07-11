package com.dcf.iqunxing.message2.request;


public class GetSiteMsgTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = -3835228901757080118L;

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
