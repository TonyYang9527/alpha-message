package com.dcf.iqunxing.message2.service;

import com.dcf.iqunxing.message2.request.CreateEmailMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DeleteEmailMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DisableEmailMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.EnableEmailMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.GetEmailMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.SendEmailRequest;
import com.dcf.iqunxing.message2.request.UpdateEmailMsgTemplateRequest;
import com.dcf.iqunxing.message2.response.CreateEmailMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DeleteEmailMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DisableEmailMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.EnableEmailMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.GetEmailMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.ListEmailMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.dcf.iqunxing.message2.response.UpdateEmailMsgTemplateResponse;

public interface IEmailService {

    /**
     * 发送邮件
     * 
     * @param request
     * @return
     */
    public SendMessageResponse sendEmail(SendEmailRequest request);

    /**
     * 增加邮件模板
     * 
     * @param request
     * @return
     */
    public CreateEmailMsgTemplateResponse createEmailMsgTemplate(CreateEmailMsgTemplateRequest request);

    /**
     * 删除邮件模板
     * 
     * @param request
     * @return
     */
    public DeleteEmailMsgTemplateResponse deleteEmailMsgTemplate(DeleteEmailMsgTemplateRequest request);

    /**
     * 冻结邮件模板
     * 
     * @param request
     * @return
     */
    public DisableEmailMsgTemplateResponse disableEmailMsgTemplate(DisableEmailMsgTemplateRequest request);

    /**
     * 启用邮件模板
     * 
     * @param request
     * @return
     */
    public EnableEmailMsgTemplateResponse enableEmailMsgTemplate(EnableEmailMsgTemplateRequest request);

    /**
     * 更改邮件模板
     * 
     * @param request
     * @return
     */
    public UpdateEmailMsgTemplateResponse updateEmailMsgTemplate(UpdateEmailMsgTemplateRequest request);

    /**
     * 查询邮件模板
     * 
     * @param request
     * @return
     */
    public GetEmailMsgTemplateResponse getEmailMsgTemplate(GetEmailMsgTemplateRequest request);

    /**
     * 查询邮件模板列表
     * 
     * @param request
     * @return
     */
    public ListEmailMsgTemplateResponse listEmailMsgTemplate(ListMsgTemplateRequest request);
}
