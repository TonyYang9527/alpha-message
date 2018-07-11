package com.dcf.iqunxing.message2.service;

import com.dcf.iqunxing.message2.request.CreatePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DeletePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DisablePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.EnablePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.GetPushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.SendPushRequest;
import com.dcf.iqunxing.message2.request.UpdatePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.response.CreatePushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DeletePushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DisablePushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.EnablePushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.GetPushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.ListPushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.dcf.iqunxing.message2.response.UpdatePushMsgTemplateResponse;

public interface IPushService {

    public SendMessageResponse sendPush(SendPushRequest request);

    /**
     * 增加PUSH模板
     * 
     * @param request
     * @return
     */
    public CreatePushMsgTemplateResponse createPushMsgTemplate(CreatePushMsgTemplateRequest request);

    /**
     * 删除PUSH模板
     * 
     * @param request
     * @return
     */
    public DeletePushMsgTemplateResponse deletePushMsgTemplate(DeletePushMsgTemplateRequest request);

    /**
     * 冻结PUSH模板
     * 
     * @param request
     * @return
     */
    public DisablePushMsgTemplateResponse disablePushMsgTemplate(DisablePushMsgTemplateRequest request);

    /**
     * 启用PUSH模板
     * 
     * @param request
     * @return
     */
    public EnablePushMsgTemplateResponse enablePushMsgTemplate(EnablePushMsgTemplateRequest request);

    /**
     * 更改PUSH模板
     * 
     * @param request
     * @return
     */
    public UpdatePushMsgTemplateResponse updatePushMsgTemplate(UpdatePushMsgTemplateRequest request);

    /**
     * 查询PUSH模板
     * 
     * @param request
     * @return
     */
    public GetPushMsgTemplateResponse getPushMsgTemplate(GetPushMsgTemplateRequest request);

    /**
     * 查询PUSH模板列表
     * 
     * @param request
     * @return
     */
    public ListPushMsgTemplateResponse listPushMsgTemplate(ListMsgTemplateRequest request);
}
