package com.dcf.iqunxing.message2.response;

import com.dcf.iqunxing.message2.model.SiteMsgVo;

public class GetSiteMsgResponse extends BaseResponse {

    private static final long serialVersionUID = -8885676293579070505L;

    protected SiteMsgVo msg;

    public SiteMsgVo getMsg() {
        return msg;
    }

    public void setMsg(SiteMsgVo msg) {
        this.msg = msg;
    }
}
