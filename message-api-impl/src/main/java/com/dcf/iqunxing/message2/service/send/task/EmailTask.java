package com.dcf.iqunxing.message2.service.send.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.entity.EmailMessage;
import com.dcf.iqunxing.message2.entity.EmailMessageAttachment;
import com.dcf.iqunxing.message2.entity.EmailMessageProperty;
import com.dcf.iqunxing.message2.entity.EmailMessageTemplate;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelResp;
import com.dcf.iqunxing.message2.service.channel.entity.req.EmailChannelReq;
import com.dcf.iqunxing.message2.service.internal.email.EmailMessageAttachmentService;
import com.dcf.iqunxing.message2.service.internal.email.EmailMessagePropertyService;
import com.dcf.iqunxing.message2.service.internal.email.EmailMessageService;
import com.dcf.iqunxing.message2.service.internal.email.EmailMessageTemplateService;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.MessageAssembleUtil;
import com.dcf.iqunxing.message2.util.spring.SpringApplicationContextHolder;
import com.google.common.collect.Maps;

public class EmailTask extends Task {

    private static final String LOG_TITLE = "拼接邮件发送至网管";

    ILog log = LogManager.getLogger(EmailTask.class);

    /** 邮件切割标识| */
    private static final String SPILT_REGEX = "\\|";

    private EmailMessagePropertyService emailMessagePropertyService;

    private EmailMessageService emailMessageService;

    private EmailMessageAttachmentService emailMessageAttachmentService;

    private EmailMessageTemplateService emailMessageTemplateService;

    public EmailTask() {
        emailMessagePropertyService = SpringApplicationContextHolder.getSpringBean(EmailMessagePropertyService.class);
        emailMessageService = SpringApplicationContextHolder.getSpringBean(EmailMessageService.class);
        emailMessageAttachmentService = SpringApplicationContextHolder.getSpringBean(EmailMessageAttachmentService.class);
        emailMessageTemplateService = SpringApplicationContextHolder.getSpringBean(EmailMessageTemplateService.class);
    }

    @Override
    public void sendTask() {
        LogUtils.info(this.getClass(), LOG_TITLE, "开始邮件发送任务", queuePriorityVo, getTagBuilder());
        try {
            // 获取邮件主体
            EmailMessage eamilMessage = getEmailMessage(queuePriorityVo);
            // 获取邮件模板
            EmailMessageTemplate template = getEmailMessageTemplate(eamilMessage);
            // 获取邮件Key/Value
            Map<String, String> props = getProps(eamilMessage);
            // 拼装内容发送至网关
            Long id = eamilMessage.getId();
            // 获取邮件附件
            List<File> attachmentFiles = getEmailAttachment(id);
            String content = MessageAssembleUtil.assembleContent(template.getContent(), props);
            LogUtils.info(this.getClass(), LOG_TITLE, "拼装邮件内容完成", content, getTagBuilder());
            String toAddress = eamilMessage.getToAddress();
            String from = template.getFromAddress();
            String senderName = template.getSenderName();
            String ccAddress = eamilMessage.getCcAddress();
            String bccAddress = eamilMessage.getBccAddress();
            String title = null;
            if (props.get("title") != null) {
                title = props.get("title");// message2重构完成之前临时处理邮件标题，重构完成之后可调整去掉
            } else {
                title = template.getTitle();
            }
            Boolean isHtmlText = true;
            ChannelResp channelResp = sendToChannel(id, content, toAddress, attachmentFiles, from, senderName, ccAddress,
                    bccAddress, title, isHtmlText);
            // 根据请求存储EmailMessage实体
            saveEmailSendResult(channelResp);
        } catch (Throwable t) {
            LogUtils.error(this.getClass(), LOG_TITLE, "发送邮件出错", queuePriorityVo, t, getTagBuilder());
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "结束邮件发送任务", queuePriorityVo, getTagBuilder());
    }

    /**
     * 获取EmailMessage.
     *
     * @param queuePriorityVo
     * @return emailMessage
     */
    private EmailMessage getEmailMessage(QueuePriorityVo queuePriorityVo) {
        Long msgId = queuePriorityVo.getId();
        EmailMessage emailMessage = emailMessageService.getEmailMessageById(msgId);
        LogUtils.info(this.getClass(), LOG_TITLE, "获取邮件主体", emailMessage, getTagBuilder());
        return emailMessage;
    }

    /**
     * 获取邮件Key/Value.
     *
     * @param smsMessage
     *            the email message
     * @return the props
     */
    private Map<String, String> getProps(EmailMessage emailMessage) {
        Long messageId = emailMessage.getId();
        List<EmailMessageProperty> msgProperties = emailMessagePropertyService.getEmailPropsByMessageId(messageId);
        Map<String, String> props = Maps.newHashMapWithExpectedSize(msgProperties.size());
        for (EmailMessageProperty po : msgProperties) {
            props.put(po.getPropKey(), po.getPropValue());
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "获取邮件Key/Value:" + props, msgProperties, getTagBuilder());
        return props;
    }

    /**
     * 构造网关请求对象并发送.
     *
     * @param id
     * @param content
     * @param toAddress
     * @param isHtmlText
     * @param title
     * @param bccAddress
     * @param ccAddress
     * @param senderName
     * @param from
     * @return the channel resp
     */
    private ChannelResp sendToChannel(Long id, String content, String toAddress, List<File> attachmentFiles, String from,
            String senderName, String ccAddress, String bccAddress, String title, Boolean isHtmlText) {
        String[] emailArrs = null;
        String[] cc = null;
        String[] bcc = null;
        if (ccAddress != null && ccAddress.length() > 0) {
            cc = ccAddress.split(SPILT_REGEX);
        }
        if (bccAddress != null && bccAddress.length() > 0) {
            bcc = bccAddress.split(SPILT_REGEX);
        }
        if (toAddress != null && toAddress.length() > 0) {
            emailArrs = toAddress.split(SPILT_REGEX);
        }

        EmailChannelReq req = new EmailChannelReq(id, emailArrs, content, from, title, senderName, isHtmlText, cc, bcc,
                attachmentFiles);
        LogUtils.info(this.getClass(), LOG_TITLE, "构造网关请求对象", req, getTagBuilder());
        ChannelResp resp = channelServiceFacade.request(req);
        LogUtils.info(this.getClass(), LOG_TITLE, "邮件网关发送响应", resp, getTagBuilder());
        return resp;
    }

    /**
     * 更新返回结果.
     *
     * @param sendEmailRequest
     */
    private void saveEmailSendResult(ChannelResp channelResp) {
        Long id = channelResp.getId();
        Boolean isSucesss = channelResp.isSuccess();
        Date currentDate = new Date();
        emailMessageService.updateEmailSendResult(id, isSucesss, currentDate);
        LogUtils.info(this.getClass(), LOG_TITLE, "根据网关返回结果更新邮件主体", channelResp, getTagBuilder());
    }

    /**
     * 根据邮件ID查询附件文件.
     *
     * @param sendEmailRequest
     */
    private List<File> getEmailAttachment(Long messageId) {
        List<File> attachmentFiles = new ArrayList<File>();
        List<EmailMessageAttachment> attachments = emailMessageAttachmentService.getEmailAttachmentByMessageId(messageId);
        // 中央日志
        if (CollectionUtils.isNotEmpty(attachments)) {
            for (EmailMessageAttachment attachment : attachments) {
                if (StringUtils.isBlank(attachment.getPath())) {
                    continue;
                }
                File file = new File(attachment.getPath());
                attachmentFiles.add(file);
            }
        }
        return attachmentFiles;
    }

    /**
     * 获取邮件模板.
     *
     * @param EmailMessage
     * @return EmailMessageTemplate
     */
    private EmailMessageTemplate getEmailMessageTemplate(EmailMessage emailMessage) {
        Long templateId = emailMessage.getEmailMessageTemplateId();
        EmailMessageTemplate template = emailMessageTemplateService.getMsgTemplate(templateId);
        LogUtils.info(this.getClass(), LOG_TITLE, "获取邮件模板", template, getTagBuilder());
        return template;
    }
}
