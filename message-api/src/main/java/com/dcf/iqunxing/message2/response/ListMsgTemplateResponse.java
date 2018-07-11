package com.dcf.iqunxing.message2.response;

import com.dcf.iqunxing.message2.model.MsgTemplateVo;
import com.dcf.iqunxing.message2.model.page.PageResult;


public class ListMsgTemplateResponse extends BaseResponse {

    private static final long serialVersionUID = 2143188998471716681L;

    protected PageResult<MsgTemplateVo> result;

    public PageResult<MsgTemplateVo> getResult() {
        return result;
    }

    public void setResult(PageResult<MsgTemplateVo> result) {
        this.result = result;
    }
}
