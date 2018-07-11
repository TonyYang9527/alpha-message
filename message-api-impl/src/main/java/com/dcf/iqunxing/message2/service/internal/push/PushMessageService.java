package com.dcf.iqunxing.message2.service.internal.push;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.PushMessageMapper;
import com.dcf.iqunxing.message2.entity.PushMessage;
import com.dcf.iqunxing.message2.entity.PushMessageTemplate;
import com.dcf.iqunxing.message2.enums.ImmediateType;
import com.dcf.iqunxing.message2.enums.SendState;
import com.dcf.iqunxing.message2.request.SendPushRequest;
import com.dcf.iqunxing.mobile.IMobileService;
import com.dcf.iqunxing.user.entity.UserDevice;
import com.google.common.base.Preconditions;

@Service
public class PushMessageService {

    @Autowired
    protected PushMessageMapper pushMessageMapper;

    @Autowired
    protected PushMessageTemplateService pushMessageTemplateService;

    @Autowired
    protected IMobileService mobileService;

    /**
     * 根据主键获取PushMessage.
     *
     * @param id
     *            the id
     * @return the push message by id
     */
    public PushMessage getPushMessageById(Long id) {
        Preconditions.checkNotNull(id);
        return pushMessageMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据请求存储PushMessage实体.
     *
     * @param req
     *            the req
     * @return the long
     */
    public PushMessage savePushMessageByReq(SendPushRequest req) {
        // 通过模板ID获取模板内容
        Long templateId = req.getPushMessageTemplateId();
        PushMessageTemplate template = pushMessageTemplateService.getMsgTemplate(templateId);
        UserDevice userDevice = mobileService.getLastAccessUserDevices(req.getReceiverId());
        // 根据模板和请求实体构造pushMessage实体
        PushMessage msg = buildPushMessage(template, userDevice, req);
        pushMessageMapper.insertSelective(msg);
        return msg;
    }

    /**
     * 构造PushMessage实体并存储.
     *
     * @param template
     *            the template
     * @param req
     *            the req
     * @return the push message
     */
    private PushMessage buildPushMessage(PushMessageTemplate template, UserDevice userDevice, SendPushRequest req) {
        PushMessage message = new PushMessage();
        message.setPushMessageTemplateId(template.getId());
        message.setReceiverId(req.getReceiverId());
        message.setDeviceId(userDevice.getDeviceId());
        message.setDeviceType(userDevice.getDeviceType().getValue());
        message.setPriority(template.getPriority());
        // 创建push时，push状态未待发送
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
        message.setExpireTime(new Date(req.getExpiredUtcTime()));
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
    public boolean updatePushSendResult(Long id, boolean success, String retCode, Date sentDate) {
        PushMessage pushMessage = new PushMessage();
        pushMessage.setId(id);
        pushMessage.setSentResult(retCode);
        pushMessage.setSentTime(sentDate);
        if (success) {
            pushMessage.setState(SendState.SENT.getValue());
        } else {
            pushMessage.setState(SendState.FAILED.getValue());
        }
        pushMessageMapper.updateByPrimaryKeySelective(pushMessage);
        return true;
    }
}
