package com.dcf.iqunxing.message2.response;

import com.dcf.iqunxing.message2.model.EmailMsgTemplateVo;
import com.dcf.iqunxing.message2.model.page.PageResult;

public class ListEmailMsgTemplateResponse extends BaseResponse {

    private static final long serialVersionUID = -2200466399445417860L;

    protected PageResult<EmailMsgTemplateVo> result;

    public PageResult<EmailMsgTemplateVo> getResult() {
        return result;
    }

    public void setResult(PageResult<EmailMsgTemplateVo> result) {
        this.result = result;
    }
}
