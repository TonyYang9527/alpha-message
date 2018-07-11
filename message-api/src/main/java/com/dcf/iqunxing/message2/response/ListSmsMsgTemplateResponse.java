package com.dcf.iqunxing.message2.response;

import com.dcf.iqunxing.message2.model.SmsMsgTemplateVo;
import com.dcf.iqunxing.message2.model.page.PageResult;

public class ListSmsMsgTemplateResponse extends BaseResponse {

    private static final long serialVersionUID = -8868980283520910044L;

    protected PageResult<SmsMsgTemplateVo> result;

    public PageResult<SmsMsgTemplateVo> getResult() {
        return result;
    }

    public void setResult(PageResult<SmsMsgTemplateVo> result) {
        this.result = result;
    }
}
