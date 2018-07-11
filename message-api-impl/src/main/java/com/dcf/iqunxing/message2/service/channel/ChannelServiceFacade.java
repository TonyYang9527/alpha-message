package com.dcf.iqunxing.message2.service.channel;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.model.enums.MsgType;
import com.dcf.iqunxing.message2.model.enums.SmsChannel;
import com.dcf.iqunxing.message2.service.channel.emay.EmayECProxy;
import com.dcf.iqunxing.message2.service.channel.emay.EmayEMProxy;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelReq;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelResp;
import com.dcf.iqunxing.message2.service.channel.entity.req.EmailChannelReq;
import com.dcf.iqunxing.message2.service.channel.entity.req.PushChannelReq;
import com.dcf.iqunxing.message2.service.channel.entity.req.SmsChannelReq;
import com.dcf.iqunxing.message2.util.LogUtils;

/**
 * 各通道网关门面方法.
 */
@Service
public class ChannelServiceFacade {

    private ILog log = LogManager.getLogger(ChannelServiceFacade.class);

    /** 短信网关签名. */
    private static final String SIGNATURE = "【群星金融网】";

    @Autowired
    protected EmayECProxy emayECProxy;

    @Autowired
    protected EmayEMProxy emayEMProxy;

    @Autowired
    protected EmailProxy emailProxy;

    @Autowired
    protected PushProxy pushProxy;

    public ChannelResp request(ChannelReq req) {
        MsgType msgType = req.getMsgType();
        ChannelResp resp = null;
        switch (msgType) {
            case SMS:
                resp = sendSms(req);
                break;
            case EMAIL:
                resp = sendEmail(req);
                break;
            case PUSH:
                resp = sendPush(req);
                break;
            case SITE_MSG:
                break;
            case SMS_EMAY_QRYBALANCE:
                resp = queryEmayBalance();
                break;
        }
        return resp;
    }

    /**
     * 发送短信.
     *
     * @param req
     *            the req
     * @return the channel resp
     */
    private ChannelResp sendSms(ChannelReq req) {
        Object[] result = null;
        SmsChannelReq smsReq = (SmsChannelReq) req;
        Long id = smsReq.getId();
        String[] mobiles = smsReq.getTarget();
        String content = smsReq.getContent();
        SmsChannel smsChannel = smsReq.getSmsChannel();
        switch (smsChannel) {
            case SMS_EMAY_999:
                result = emayECProxy.send(id, mobiles, SIGNATURE + content, null);
                break;
            case SMS_EMAY_106:
                result = emayEMProxy.send(id, mobiles, content, null);
                break;
            default:
                break;
        }

        Boolean isSuccess = (Boolean) result[0];
        String code = (String) result[1];
        ChannelResp resp = new ChannelResp(id, MsgType.SMS, isSuccess, code, null);
        return resp;
    }

    /**
     * 发送邮件.
     *
     * @param req
     *            the req
     * @return the channel resp
     */
    private ChannelResp sendEmail(ChannelReq req) {
        EmailChannelReq emailReq = (EmailChannelReq) req;
        final String LOG_TITLE = "邮件通道服务";
        TagBuilder builder = LogUtils.getTagBuilder(emailReq, "id", "title", "target");
        Boolean isSuccess = false;
        String result = null;
        String from = emailReq.getFrom();
        String senderName = emailReq.getSenderName();
        String text = emailReq.getContent();
        Boolean isHtmlText = emailReq.getIsHtmlText();
        String subject = emailReq.getTitle();
        String[] cc = emailReq.getCc();
        String[] bcc = emailReq.getBcc();
        String[] to = emailReq.getTarget();
        List<File> attachmentFiles = null;
        try {
            emailProxy.sendEmail(from, senderName, text, isHtmlText, subject, cc, to, bcc, attachmentFiles);
            isSuccess = true;
            result = "success";
            LogUtils.info(this.getClass(), LOG_TITLE, "邮件网关发送邮件成功", emailReq, builder);
        } catch (Exception e) {
            isSuccess = false;
            result = "false";
            LogUtils.error(this.getClass(), LOG_TITLE, "邮件网关发送邮件失败", emailReq, e, builder);
        }
        ChannelResp resp = new ChannelResp(emailReq.getId(), MsgType.EMAIL, isSuccess, null, result);
        return resp;
    }

    /**
     * Push.
     *
     * @param req
     *            the req
     * @return the channel resp
     */
    private ChannelResp sendPush(ChannelReq req) {
        PushChannelReq pushReq = (PushChannelReq) req;
        Long id = pushReq.getId();
        String deviceId = pushReq.getDeviceId();
        String content = pushReq.getContent();
        int deviceType = pushReq.getDeviceType();
        String title = pushReq.getTitle();
        Map<String, Object> custom = pushReq.getCustom();
        Boolean isSuccess = false;
        try {
            isSuccess = pushProxy.pushMessage(deviceType, deviceId, title, content, custom);
        } catch (Exception e) {
            isSuccess = false;
        }
        String code = isSuccess ? "Push成功" : "Push失败";
        ChannelResp resp = new ChannelResp(id, MsgType.PUSH, isSuccess, code, null);
        return resp;
    }

    /**
     * 查询亿美通道余额.
     *
     * @return the channel resp
     */
    private ChannelResp queryEmayBalance() {
        final String LOG_TITLE = "查询亿美通道余额";
        TagBuilder builder = TagBuilder.create().append("name", "queryEmayBalance");
        ChannelResp resp = null;
        String result = emayECProxy.queryBalance();
        String code = StringUtils.substringBetween(result, "<error>", "</error>");
        String balance = StringUtils.substringBetween(result, "<message>", "</message>");
        // 余额查询成功code为0
        if ("0".equals(code)) {
            resp = new ChannelResp(null, MsgType.SMS, true, code, balance);
        } else {
            resp = new ChannelResp(null, MsgType.SMS, false, code, "查询余额失败");
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "余额查询", resp, builder);
        return resp;
    }
}
