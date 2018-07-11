package com.dcf.iqunxing.message2.response;

import com.dcf.iqunxing.message2.model.SmsMsgTemplateVo;

public class GetSmsMsgTemplateResponse extends BaseResponse {

    private static final long serialVersionUID = 4557572666542991846L;

    protected SmsMsgTemplateVo template;

    public SmsMsgTemplateVo getTemplate() {
        return template;
    }

    public void setTemplate(SmsMsgTemplateVo template) {
        this.template = template;
    }
}
