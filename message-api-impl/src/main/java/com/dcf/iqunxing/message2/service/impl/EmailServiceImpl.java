package com.dcf.iqunxing.message2.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.entity.EmailMessage;
import com.dcf.iqunxing.message2.entity.EmailMessageTemplate;
import com.dcf.iqunxing.message2.enums.ImmediateType;
import com.dcf.iqunxing.message2.model.EmailMsgTemplateVo;
import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.page.PageResult;
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
import com.dcf.iqunxing.message2.response.RetCodeConst;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.dcf.iqunxing.message2.response.UpdateEmailMsgTemplateResponse;
import com.dcf.iqunxing.message2.service.IEmailService;
import com.dcf.iqunxing.message2.service.aspect.validate.Validator;
import com.dcf.iqunxing.message2.service.aspect.validate.validator.SendEmailRequestValidator;
import com.dcf.iqunxing.message2.service.internal.email.EmailMessageAttachmentService;
import com.dcf.iqunxing.message2.service.internal.email.EmailMessagePropertyService;
import com.dcf.iqunxing.message2.service.internal.email.EmailMessageService;
import com.dcf.iqunxing.message2.service.internal.email.EmailMessageTemplateService;
import com.dcf.iqunxing.message2.service.queue.EmailMessageQueueService;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.mapper.BeanMapper;

@Service("emailService")
public class EmailServiceImpl implements IEmailService {

    private static final String LOG_TITLE = "接收邮件请求服务";

    ILog log = LogManager.getLogger(EmailServiceImpl.class);

    @Autowired
    private EmailMessageService emailMessageService;

    @Autowired
    private EmailMessageTemplateService emailMessageTemplateService;

    @Autowired
    private EmailMessagePropertyService emailMessagePropertyService;

    @Autowired
    protected EmailMessageQueueService emailMessageQueueService;

    @Autowired
    protected EmailMessageAttachmentService emailMessageAttachmentService;

    @Value("${attachment.path}")
    protected String attachmentPath;

    @Override
    @Validator(SendEmailRequestValidator.class)
    public SendMessageResponse sendEmail(SendEmailRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "toAddresses");
        LogUtils.info(this.getClass(), LOG_TITLE, "接收到邮件发送请求，开始创建邮件", request, builder);
        // 将请求邮件保存入DB
        EmailMessage emailMessage = saveReqToDB(request);
        // 如果是即时的则插入队列
        if (emailMessage.getImmediate().equals(ImmediateType.IMMEDIATE.getValue())) {
            boolean result = emailMessageQueueService.offerToQueue(emailMessage);
            if (!result) {
                LogUtils.error(this.getClass(), LOG_TITLE, "插入邮件队列失败", emailMessage, null, builder);
            }
        }
        SendMessageResponse resp = new SendMessageResponse();
        resp.setId(emailMessage.getId());
        resp.setRetMsg("邮件创建成功");
        resp.setRetCode(RetCodeConst.SUCCESS);
        LogUtils.info(this.getClass(), LOG_TITLE, "创建邮件成功响应", resp, builder);
        return resp;
    }

    /**
     * 将请求信息保存入DB.
     *
     * @param request
     *            the request
     * @return the email message
     * @throws UnsupportedEncodingException
     */
    private EmailMessage saveReqToDB(SendEmailRequest request) {
        // 创建邮件email_message
        EmailMessage emailMessage = emailMessageService.saveEmailMessageByReq(request);
        // 中央日志：开始存入DB
        TagBuilder builder = LogUtils.getTagBuilder(request, "toAddresses");
        builder.append("id", emailMessage.getId());
        LogUtils.info(this.getClass(), LOG_TITLE, "保存邮件主体成功", emailMessage, builder);

        // 创建email_message_property
        Map<String, String> props = request.getProperties();
        emailMessagePropertyService.saveEmailMessageProperty(emailMessage.getId(), props);
        // 中央日志：开始存入DB
        LogUtils.info(this.getClass(), LOG_TITLE, "保存邮件Key/Value成功", props, builder);

        // 附件操作
        Integer attachmentId = 0;
        if (request.getAttachments() != null) {
            for (Map.Entry<String, byte[]> entry : request.getAttachments().entrySet()) {
                attachmentId = emailMessageAttachmentService.saveEmailMessageAttachment(emailMessage.getId(), entry.getKey(), null);
                FileOutputStream fs = null;
                try {
                    // 根据id保存附件目录为,
                    // id转为格式为15位的数字。目录为nfs上/data/message/attachment/XXXX/XXXX/XXXX。文件名为id，没有后缀名。
                    String nameId = StringUtils.leftPad(attachmentId.toString(), 15, "0");
                    // 例如id为123456789045678，目录为/data/message/attachment/12345/67890/
                    String path = attachmentPath + nameId.substring(0, 4) + "/" + nameId.substring(4, 8) + "/"
                            + nameId.substring(8, 12);
                    // 文件名为123456789045678
                    File downFileDir = new File(path);
                    if (!downFileDir.exists()) {
                        downFileDir.mkdirs();
                    }
                    path = path + "/" + nameId;
                    File fileAttachment = new File(path);

                    fs = new FileOutputStream(fileAttachment);
                    fs.write(entry.getValue());
                    fs.flush();
                    // 3. 更新path到数据库
                    emailMessageAttachmentService.updateEmailMessageAttachmentPath(new Long(attachmentId), path);
                } catch (IOException e) {
                    LogUtils.error(this.getClass(), LOG_TITLE, "保存邮件附件失败", emailMessage.getId() + ":" + attachmentId, e, builder);
                } finally {
                    if (fs != null) {
                        try {
                            fs.close();
                        } catch (IOException e) {
                            LogUtils.error(this.getClass(), LOG_TITLE, "关闭附件文件流出错", emailMessage.getId() + ":" + attachmentId, e,
                                    builder);
                        }
                    }
                }
            }
        }
        return emailMessage;
    }

    @Override
    public CreateEmailMsgTemplateResponse createEmailMsgTemplate(CreateEmailMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "name", "title", "content", "type", "priority", "operator",
                "fromAddress", "senderName");
        log.info("创建邮件模板", "创建邮件模板开始", builder.build());

        CreateEmailMsgTemplateResponse resp = new CreateEmailMsgTemplateResponse();
        EmailMessageTemplate tmp = emailMessageTemplateService.createEmailMsgTemplate(request);
        resp.setId(tmp.getId());
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("邮件模板创建成功");
        log.info("创建邮件模板", "创建邮件模板成功", builder.build());
        return resp;
    }

    @Override
    public DeleteEmailMsgTemplateResponse deleteEmailMsgTemplate(DeleteEmailMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "ids", "operator");
        log.info("删除邮件模板", "删除邮件模板开始", builder.build());
        DeleteEmailMsgTemplateResponse resp = new DeleteEmailMsgTemplateResponse();
        emailMessageTemplateService.deleteEmailMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("邮件模板删除成功");
        log.info("删除邮件模板", "删除邮件模板成功", builder.build());
        return resp;
    }

    @Override
    public UpdateEmailMsgTemplateResponse updateEmailMsgTemplate(UpdateEmailMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "id", "name", "title", "content", "type", "priority", "operator",
                "fromAddress", "senderName");
        log.info("更新邮件模板", "更新邮件模板开始", builder.build());
        UpdateEmailMsgTemplateResponse resp = new UpdateEmailMsgTemplateResponse();
        emailMessageTemplateService.updateEmailMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("邮件模板变更成功");
        log.info("更新邮件模板", "更新邮件模板成功", builder.build());
        return resp;
    }

    @Override
    public GetEmailMsgTemplateResponse getEmailMsgTemplate(GetEmailMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        log.info("查询邮件模板", "查询邮件模板开始", builder.build());
        GetEmailMsgTemplateResponse resp = new GetEmailMsgTemplateResponse();
        EmailMessageTemplate emailMessageTemplate = emailMessageTemplateService.getMsgTemplate(request.getId());
        EmailMsgTemplateVo emailMsgTemplateVo = BeanMapper.map(emailMessageTemplate, EmailMsgTemplateVo.class);
        emailMsgTemplateVo.setMsgPriority(MsgPriority.fromValue(emailMessageTemplate.getPriority()));
        resp.setTemplate(emailMsgTemplateVo);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("邮件模板查询成功");
        // 日志
        TagBuilder tagBuilder = LogUtils.getTagBuilder(emailMsgTemplateVo, "id", "name", "title", "content", "type", "priority");
        log.info("查询邮件模板", "查询邮件模板成功", tagBuilder.build());
        return resp;
    }

    /**
     * 禁用邮件模版
     */
    @Override
    public DisableEmailMsgTemplateResponse disableEmailMsgTemplate(DisableEmailMsgTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        LogUtils.info(this.getClass(), LOG_TITLE, "禁用邮件模板开始 request ", request, builder);
        DisableEmailMsgTemplateResponse resp = new DisableEmailMsgTemplateResponse();
        emailMessageTemplateService.disableEmailMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("禁用邮件模版成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "禁用邮件模板结束 response ", resp, builder);
        return resp;
    }

    /**
     * 启用邮件模版
     */
    @Override
    public EnableEmailMsgTemplateResponse enableEmailMsgTemplate(EnableEmailMsgTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        LogUtils.info(this.getClass(), LOG_TITLE, "启邮件模板开始 request ", request, builder);
        EnableEmailMsgTemplateResponse resp = new EnableEmailMsgTemplateResponse();
        emailMessageTemplateService.enableEmailMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("启用邮件模版成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "启邮件模板结束 response ", resp, builder);
        return resp;
    }

    /**
     * 列取邮件模版
     */
    @Override
    public ListEmailMsgTemplateResponse listEmailMsgTemplate(ListMsgTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "name", "state");
        LogUtils.info(this.getClass(), LOG_TITLE, "列取消息模版开始 request ", request, builder);
        ListEmailMsgTemplateResponse resp = new ListEmailMsgTemplateResponse();
        PageResult<EmailMsgTemplateVo> result = emailMessageTemplateService.listEmailMsgTemplate(request);
        resp.setResult(result);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("列取邮件模版成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "列取消息模版结束 response ", resp, builder);
        return resp;
    }
}
