package com.dcf.iqunxing.message2.response;

import com.dcf.iqunxing.message2.model.PushMsgTemplateVo;

public class GetPushMsgTemplateResponse extends BaseResponse {

    private static final long serialVersionUID = 8040247201400962266L;

    protected PushMsgTemplateVo template;

    public PushMsgTemplateVo getTemplate() {
        return template;
    }

    public void setTemplate(PushMsgTemplateVo template) {
        this.template = template;
    }
}
