package com.dcf.iqunxing.message2.service.internal.sms;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.SmsMessageMapper;
import com.dcf.iqunxing.message2.entity.SmsMessage;
import com.dcf.iqunxing.message2.entity.SmsMessageTemplate;
import com.dcf.iqunxing.message2.enums.ImmediateType;
import com.dcf.iqunxing.message2.enums.SendState;
import com.dcf.iqunxing.message2.request.SendSmsRequest;
import com.google.common.base.Preconditions;

@Service
public class SmsMessageService {

    @Autowired
    protected SmsMessageMapper smsMessageMapper;

    @Autowired
    protected SmsMessageTemplateService smsMessageTemplateService;

    /**
     * 根据主键获取SmsMessage.
     *
     * @param id
     *            the id
     * @return the sms message by id
     */
    public SmsMessage getSmsMessageById(Long id) {
        Preconditions.checkNotNull(id);
        return smsMessageMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据请求存储SmsMessage实体.
     *
     * @param req
     *            the req
     * @return the long
     */
    public SmsMessage saveSmsMessageByReq(SendSmsRequest req) {
        // 通过模板ID获取模板内容
        Long templateId = req.getTemplateId();
        SmsMessageTemplate template = smsMessageTemplateService.getMsgTemplate(templateId);
        // 根据模板和请求实体构造smsMessage实体
        SmsMessage msg = buildSmsMessage(template, req);
        smsMessageMapper.insertSelective(msg);
        return msg;
    }

    /**
     * 构造SmsMessage实体并存储.
     *
     * @param template
     *            the template
     * @param req
     *            the req
     * @return the sms message
     */
    private SmsMessage buildSmsMessage(SmsMessageTemplate template, SendSmsRequest req) {
        SmsMessage message = new SmsMessage();
        message.setSmsMessageTemplateId(template.getId());
        message.setMobiles(req.getMobiles());
        message.setPriority(template.getPriority());
        // 创建短信时，短信状态未待发送
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
     * @param retCode
     *            网关返回code
     * @param sentDate
     *            网关发送时间
     * @return true, if successful
     */
    public boolean updateSmsSendResult(Long id, boolean success, String retCode, Date sentDate) {
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setId(id);
        smsMessage.setSentResult(retCode);
        smsMessage.setSentTime(sentDate);
        if (success) {
            smsMessage.setState(SendState.SENT.getValue());
        } else {
            smsMessage.setState(SendState.FAILED.getValue());
        }
        smsMessageMapper.updateByPrimaryKeySelective(smsMessage);
        return true;
    }
}
