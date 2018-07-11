package com.dcf.iqunxing.message2.response;

import com.dcf.iqunxing.message2.model.SiteMsgTemplateVo;
import com.dcf.iqunxing.message2.model.page.PageResult;

public class ListSiteMsgTemplateResponse extends BaseResponse {

    private static final long serialVersionUID = -962061619066858137L;

    protected PageResult<SiteMsgTemplateVo> result;

    public PageResult<SiteMsgTemplateVo> getResult() {
        return result;
    }

    public void setResult(PageResult<SiteMsgTemplateVo> result) {
        this.result = result;
    }
}
