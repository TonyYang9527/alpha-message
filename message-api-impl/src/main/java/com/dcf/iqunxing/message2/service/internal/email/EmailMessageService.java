package com.dcf.iqunxing.message2.service.internal.email;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.EmailMessageMapper;
import com.dcf.iqunxing.message2.dao.EmailMessageTemplateMapper;
import com.dcf.iqunxing.message2.entity.EmailMessage;
import com.dcf.iqunxing.message2.entity.EmailMessageTemplate;
import com.dcf.iqunxing.message2.enums.ImmediateType;
import com.dcf.iqunxing.message2.enums.SendState;
import com.dcf.iqunxing.message2.request.SendEmailRequest;
import com.google.common.base.Preconditions;

@Service
public class EmailMessageService {

    @Autowired
    protected EmailMessageMapper emailMessageMapper;

    @Autowired
    protected EmailMessageTemplateMapper emailMessageTemplateMapper;

    /**
     * 根据主键获取EmailMessage.
     *
     * @param id
     *            the id
     * @return the email message by id
     */
    public EmailMessage getEmailMessageById(Long id) {
        Preconditions.checkNotNull(id);
        return emailMessageMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据请求存储EmailMessage实体.
     *
     * @param req
     *            the req
     * @return the long
     */
    public EmailMessage saveEmailMessageByReq(SendEmailRequest req) {
        // 通过模板ID获取模板内容
        Long templateId = req.getEmailMessageTemplateId();
        EmailMessageTemplate template = emailMessageTemplateMapper.selectByPrimaryKey(templateId);
        // 根据模板和请求实体构造emailMessage实体
        EmailMessage msg = buildEmailMessage(template, req);
        emailMessageMapper.insertSelective(msg);
        return msg;
    }

    /**
     * 构造EmailMessage实体并存储.
     *
     * @param template
     *            the template
     * @param req
     *            the req
     * @return the email message
     */
    private EmailMessage buildEmailMessage(EmailMessageTemplate template, SendEmailRequest req) {
        EmailMessage message = new EmailMessage();

        message.setEmailMessageTemplateId(template.getId());
        message.setToAddress(req.getToAddresses());
        message.setCcAddress(req.getCcAddresses());
        message.setBccAddress(req.getBccAddresses());
        message.setPriority(template.getPriority());
        // 创建邮件时，邮件状态未待发送
        message.setState(SendState.TOBESEND.getValue());
        // 预期发送时间为空的话，则为即时
        if (req.getScheduleUtcTime() == null) {
            // 0即时；1定时
            message.setImmediate(ImmediateType.IMMEDIATE.getValue());
            message.setScheduleTime(new Date());
        } else {
            message.setImmediate(ImmediateType.SCHEDULE.getValue());
            message.setScheduleTime(new Date(req.getScheduleUtcTime()));
        }
        message.setExpiredTime(new Date(req.getExpiredUtcTime()));
        message.setCreatedBy(req.getUserId());
        message.setCreatedTime(new Date());
        return message;
    }

    /**
     * 更新网关返回结果.
     *
     * @param id
     *            主键
     * @param success
     *            是否成功
     * @param sentDate
     *            网关发送时间
     * @return true, if successful
     */
    public boolean updateEmailSendResult(Long id, boolean success, Date sentDate) {
        EmailMessage record = new EmailMessage();
        record.setId(id);
        record.setSentTime(sentDate);
        if (success) {
            record.setState(SendState.SENT.getValue());
        } else {
            record.setState(SendState.FAILED.getValue());
        }
        emailMessageMapper.updateByPrimaryKeySelective(record);
        return true;
    }
}
