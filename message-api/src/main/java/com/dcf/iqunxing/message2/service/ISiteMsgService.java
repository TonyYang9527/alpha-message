package com.dcf.iqunxing.message2.service;

import com.dcf.iqunxing.message2.request.CreateSiteMsgRequest;
import com.dcf.iqunxing.message2.request.CreateSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DeleteSiteMsgRequest;
import com.dcf.iqunxing.message2.request.DeleteSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DisableSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.EnableSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.GetSiteMsgRequest;
import com.dcf.iqunxing.message2.request.GetSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListSiteMsgRequest;
import com.dcf.iqunxing.message2.request.ProcessSiteMsgRequest;
import com.dcf.iqunxing.message2.request.ReadSiteMsgRequest;
import com.dcf.iqunxing.message2.request.SendSiteMsgRequest;
import com.dcf.iqunxing.message2.request.UpdateSiteMsgRequest;
import com.dcf.iqunxing.message2.request.UpdateSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.response.CreateSiteMsgResponse;
import com.dcf.iqunxing.message2.response.CreateSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DeleteSiteMsgResponse;
import com.dcf.iqunxing.message2.response.DeleteSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DisableSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.EnableSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.GetSiteMsgResponse;
import com.dcf.iqunxing.message2.response.GetSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.ListSiteMsgResponse;
import com.dcf.iqunxing.message2.response.ListSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.ProcessSiteMsgResponse;
import com.dcf.iqunxing.message2.response.ReadSiteMsgResponse;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.dcf.iqunxing.message2.response.UpdateSiteMsgResponse;
import com.dcf.iqunxing.message2.response.UpdateSiteMsgTemplateResponse;

public interface ISiteMsgService {

    /**
     * 发送站内信
     * 
     * @param request
     * @return
     */
    public SendMessageResponse sendSiteMsg(SendSiteMsgRequest request);
    
    public GetSiteMsgTemplateResponse getSiteMsgTemplate(GetSiteMsgTemplateRequest request);
    
    public ListSiteMsgTemplateResponse listSiteMsgTemplate(ListMsgTemplateRequest request);

    public CreateSiteMsgTemplateResponse createSiteMsgTemplate(CreateSiteMsgTemplateRequest request);

    public UpdateSiteMsgTemplateResponse updateSiteMsgTemplate(UpdateSiteMsgTemplateRequest request);

    public DeleteSiteMsgTemplateResponse deleteSiteMsgTemplate(DeleteSiteMsgTemplateRequest request);
    
    public DisableSiteMsgTemplateResponse disableSiteMsgTemplate(DisableSiteMsgTemplateRequest request);
    
    public EnableSiteMsgTemplateResponse enableSiteMsgTemplate(EnableSiteMsgTemplateRequest request);
    
    public GetSiteMsgResponse getSiteMsg(GetSiteMsgRequest request);

    public CreateSiteMsgResponse createSiteMsg(CreateSiteMsgRequest request);

    public UpdateSiteMsgResponse updateSiteMsg(UpdateSiteMsgRequest request);

    public DeleteSiteMsgResponse deleteSiteMsg(DeleteSiteMsgRequest request);

    public ReadSiteMsgResponse readSiteMsg(ReadSiteMsgRequest request);
    
    public ProcessSiteMsgResponse processSiteMsg(ProcessSiteMsgRequest request);

    public ListSiteMsgResponse listSiteMsg(ListSiteMsgRequest request);

}
