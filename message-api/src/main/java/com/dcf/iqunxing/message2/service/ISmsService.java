package com.dcf.iqunxing.message2.service;

import com.dcf.iqunxing.message2.request.CreateSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DeleteSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DisableSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.EnableSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.GetSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.QueryBalanceRequest;
import com.dcf.iqunxing.message2.request.SendSmsRequest;
import com.dcf.iqunxing.message2.request.UpdateSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.response.CreateSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DeleteSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DisableSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.EnableSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.GetSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.ListSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.QueryBalanceResponse;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.dcf.iqunxing.message2.response.UpdateSmsMsgTemplateResponse;

public interface ISmsService {

    /**
     * 发送短信
     * 
     * @param request
     * @return
     */
    public SendMessageResponse sendSms(SendSmsRequest request);

    /**
     * 增加短信模板
     * 
     * @param request
     * @return
     */
    public CreateSmsMsgTemplateResponse createSmsMsgTemplate(CreateSmsMsgTemplateRequest request);

    /**
     * 删除短信模板
     * 
     * @param request
     * @return
     */
    public DeleteSmsMsgTemplateResponse deleteSmsMsgTemplate(DeleteSmsMsgTemplateRequest request);

    /**
     * 冻结短信模板
     * 
     * @param request
     * @return
     */
    public DisableSmsMsgTemplateResponse disableSmsMsgTemplate(DisableSmsMsgTemplateRequest request);

    /**
     * 启用短信模板
     * 
     * @param request
     * @return
     */
    public EnableSmsMsgTemplateResponse enableableSmsMsgTemplate(EnableSmsMsgTemplateRequest request);

    /**
     * 更改短信模板
     * 
     * @param request
     * @return
     */
    public UpdateSmsMsgTemplateResponse updateSmsMsgTemplate(UpdateSmsMsgTemplateRequest request);

    /**
     * 查询短信模板
     * 
     * @param request
     * @return
     */
    public GetSmsMsgTemplateResponse getSmsMsgTemplate(GetSmsMsgTemplateRequest request);

    /**
     * 查询短信通道余额.
     *
     * @param request
     *            the request
     * @return the query balance response
     */
    public QueryBalanceResponse queryBalance(QueryBalanceRequest request);

    /**
     * 查询短信模板
     * 
     * @param request
     * @return
     */
    public ListSmsMsgTemplateResponse listSmsMsgTemplate(ListMsgTemplateRequest request);

}
