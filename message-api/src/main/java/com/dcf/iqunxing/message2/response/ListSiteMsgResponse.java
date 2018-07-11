package com.dcf.iqunxing.message2.response;

import com.dcf.iqunxing.message2.model.SiteMsgVo;
import com.dcf.iqunxing.message2.model.page.PageResult;

public class ListSiteMsgResponse extends BaseResponse {

    private static final long serialVersionUID = 1252744360938824348L;

    protected PageResult<SiteMsgVo> result;

    public PageResult<SiteMsgVo> getResult() {
        return result;
    }

    public void setResult(PageResult<SiteMsgVo> result) {
        this.result = result;
    }
}
