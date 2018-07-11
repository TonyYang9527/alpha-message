package com.dcf.iqunxing.message2.response;

import com.dcf.iqunxing.message2.model.SiteMsgTemplateVo;

public class GetSiteMsgTemplateResponse extends BaseResponse {

    private static final long serialVersionUID = 7350433130544844893L;

    protected SiteMsgTemplateVo template;

    public SiteMsgTemplateVo getTemplate() {
        return template;
    }

    public void setTemplate(SiteMsgTemplateVo template) {
        this.template = template;
    }
}
