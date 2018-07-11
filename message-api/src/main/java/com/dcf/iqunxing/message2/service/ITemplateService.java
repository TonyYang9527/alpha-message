package com.dcf.iqunxing.message2.service;

import com.dcf.iqunxing.message2.request.ListTemplateRequest;
import com.dcf.iqunxing.message2.response.ListMsgTemplateResponse;



public interface ITemplateService {

    /**
     * 查询所有模板列表
     * 
     * @param request
     * @return
     */
    public ListMsgTemplateResponse listMsgTemplate(ListTemplateRequest request);
}
