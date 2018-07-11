package com.dcf.iqunxing.message2.response;

import com.dcf.iqunxing.message2.model.PushMsgTemplateVo;
import com.dcf.iqunxing.message2.model.page.PageResult;

public class ListPushMsgTemplateResponse extends BaseResponse {

    private static final long serialVersionUID = -5433010377110750209L;

    protected PageResult<PushMsgTemplateVo> result;

    public PageResult<PushMsgTemplateVo> getResult() {
        return result;
    }

    public void setResult(PageResult<PushMsgTemplateVo> result) {
        this.result = result;
    }
}
