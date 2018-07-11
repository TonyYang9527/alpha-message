package com.dcf.iqunxing.message2.request;


public class GetSiteMsgRequest extends BaseRequest {

    private static final long serialVersionUID = -3392778677810551774L;

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
