package com.dcf.iqunxing.message2.response;

import com.dcf.iqunxing.message2.model.EmailMsgTemplateVo;

public class GetEmailMsgTemplateResponse extends BaseResponse {

    private static final long serialVersionUID = 7350433130544844893L;

    protected EmailMsgTemplateVo template;

    public EmailMsgTemplateVo getTemplate() {
        return template;
    }

    public void setTemplate(EmailMsgTemplateVo template) {
        this.template = template;
    }
}
