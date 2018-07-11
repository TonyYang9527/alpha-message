package com.dcf.iqunxing.message2.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.entity.SmsMessage;
import com.dcf.iqunxing.message2.entity.SmsMessageTemplate;
import com.dcf.iqunxing.message2.enums.ImmediateType;
import com.dcf.iqunxing.message2.model.SmsMsgTemplateVo;
import com.dcf.iqunxing.message2.model.enums.Channel;
import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.enums.MsgTemplateState;
import com.dcf.iqunxing.message2.model.enums.MsgType;
import com.dcf.iqunxing.message2.model.enums.SmsChannel;
import com.dcf.iqunxing.message2.model.page.PageResult;
import com.dcf.iqunxing.message2.request.CreateSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DeleteSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DisableSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.EnableSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.GetSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.QueryBalanceRequest;
import com.dcf.iqunxing.message2.request.SendEmailRequest;
import com.dcf.iqunxing.message2.request.SendSmsRequest;
import com.dcf.iqunxing.message2.request.UpdateSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.response.CreateSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DeleteSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DisableSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.EnableSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.GetSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.ListSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.QueryBalanceResponse;
import com.dcf.iqunxing.message2.response.RetCodeConst;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.dcf.iqunxing.message2.response.UpdateSmsMsgTemplateResponse;
import com.dcf.iqunxing.message2.service.ISmsService;
import com.dcf.iqunxing.message2.service.aspect.validate.Validator;
import com.dcf.iqunxing.message2.service.aspect.validate.validator.SendSmsRequestValidator;
import com.dcf.iqunxing.message2.service.channel.ChannelServiceFacade;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelReq;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelResp;
import com.dcf.iqunxing.message2.service.internal.sms.SmsMessagePropertyService;
import com.dcf.iqunxing.message2.service.internal.sms.SmsMessageService;
import com.dcf.iqunxing.message2.service.internal.sms.SmsMessageTemplateService;
import com.dcf.iqunxing.message2.service.queue.SmsMessageQueueService;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.mapper.BeanMapper;
import com.google.common.collect.Maps;

@Service("smsService")
public class SmsServiceImpl implements ISmsService {

    private static final String LOG_TITLE = "接收短信请求服务";

    private static final ILog log = LogManager.getLogger(SmsServiceImpl.class);

    /** 短信余额告警阈值. */
    private static final double ALARM_THRESHOLD = 1000;

    @Autowired
    protected SmsMessageService smsMessageService;

    @Autowired
    protected SmsMessagePropertyService smsMessagePropertyService;

    @Autowired
    protected SmsMessageQueueService smsMessageQueueService;

    @Autowired
    protected SmsMessageTemplateService smsMessageTemplateService;

    @Autowired
    protected ChannelServiceFacade channelServiceFacade;

    @Autowired
    protected EmailServiceImpl emailSerivce;

    @Override
    @Validator(SendSmsRequestValidator.class)
    public SendMessageResponse sendSms(SendSmsRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "mobiles");
        LogUtils.info(this.getClass(), LOG_TITLE, "接收到短信发送请求，开始创建短信", request, builder);
        // 将请求信息保存入DB
        SmsMessage smsMessage = saveReqToDB(request);
        // 如果是即时的则插入队列
        if (smsMessage.getImmediate().equals(ImmediateType.IMMEDIATE.getValue())) {
            LogUtils.info(this.getClass(), LOG_TITLE, "该短信为及时下发短信，插入短信队列", smsMessage, builder);
            boolean result = smsMessageQueueService.offerToQueue(smsMessage);
            if (!result) {
                LogUtils.error(this.getClass(), LOG_TITLE, "插入短信队列失败", smsMessage, null, builder);
            }
        }
        SendMessageResponse resp = new SendMessageResponse();
        resp.setId(smsMessage.getId());
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("短信创建成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "创建短信成功", resp, builder);
        return resp;
    }

    /**
     * 将请求信息保存入DB.
     *
     * @param request
     *            the request
     * @return the sms message
     */
    protected SmsMessage saveReqToDB(SendSmsRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "mobiles");
        // 创建短信sms_message
        SmsMessage smsMessage = smsMessageService.saveSmsMessageByReq(request);
        LogUtils.info(this.getClass(), LOG_TITLE, "保存短信主体成功", smsMessage, builder);
        Long messageId = smsMessage.getId();
        Map<String, String> props = request.getProperties();
        // 创建短信sms_message_property
        smsMessagePropertyService.saveSmsMessageProperty(messageId, props);
        LogUtils.info(this.getClass(), LOG_TITLE, "保存短信Key/Value成功", props, builder);
        return smsMessage;
    }

    @Override
    public CreateSmsMsgTemplateResponse createSmsMsgTemplate(CreateSmsMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "name", "content", "type", "priority", "operator");
        log.info("创建短信模板", "创建短信模板开始", builder.build());
        CreateSmsMsgTemplateResponse resp = new CreateSmsMsgTemplateResponse();
        SmsMessageTemplate tmp = smsMessageTemplateService.createMsgTemplate(request);
        resp.setId(tmp.getId());
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("站内信模板创建成功");
        log.info("创建短信模板", "创建短信模板成功", builder.build());
        return resp;
    }

    @Override
    public DeleteSmsMsgTemplateResponse deleteSmsMsgTemplate(DeleteSmsMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "ids", "operator");
        log.info("删除短信模板", "删除短信模板开始", builder.build());

        DeleteSmsMsgTemplateResponse resp = new DeleteSmsMsgTemplateResponse();
        smsMessageTemplateService.deleteSmsMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("短信模板删除成功");
        log.info("删除短信模板", "删除短信模板成功", builder.build());
        return resp;
    }

    @Override
    public UpdateSmsMsgTemplateResponse updateSmsMsgTemplate(UpdateSmsMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "id", "name", "content", "type", "priority", "operator");
        log.info("更新短信模板", "更新短信模板开始", builder.build());

        UpdateSmsMsgTemplateResponse resp = new UpdateSmsMsgTemplateResponse();
        smsMessageTemplateService.updateSmsMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("短信模板变更成功");
        log.info("更新短信模板", "更新短信模板成功", builder.build());
        return resp;
    }

    @Override
    public GetSmsMsgTemplateResponse getSmsMsgTemplate(GetSmsMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        log.info("查询短信模板", "查询短信模板开始", builder.build());
        GetSmsMsgTemplateResponse resp = new GetSmsMsgTemplateResponse();
        SmsMessageTemplate smsMessageTemplate = smsMessageTemplateService.getMsgTemplate(request.getId());
        SmsMsgTemplateVo smsMsgTemplateVo = BeanMapper.map(smsMessageTemplate, SmsMsgTemplateVo.class);
        if (smsMessageTemplate != null && smsMessageTemplate.getPriority() != null)
            smsMsgTemplateVo.setMsgPriority(MsgPriority.fromValue(smsMessageTemplate.getPriority()));
        if (smsMessageTemplate != null && smsMessageTemplate.getChannel() != null)
            smsMsgTemplateVo.setSmsChannel(SmsChannel.fromValue(smsMessageTemplate.getChannel()));
        if (smsMessageTemplate != null && smsMessageTemplate.getState() != null)
            smsMsgTemplateVo.setState(MsgTemplateState.fromValue(smsMessageTemplate.getState()));
        resp.setTemplate(smsMsgTemplateVo);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("短信模板查询成功");
        // 日志
        TagBuilder tagBuilder = LogUtils.getTagBuilder(smsMsgTemplateVo, "id", "name", "content", "type", "priority");
        log.info("查询短信模板", "查询短信模板成功 :  response  : " + JSON.toJSONString(resp), tagBuilder.build());
        return resp;
    }

    /**
     * 查询短信通道余额.
     *
     * @param request
     *            the request
     * @return the query balance response
     */
    public QueryBalanceResponse queryBalance(QueryBalanceRequest request) {
        Channel channel = request.getChannel();
        ChannelReq channelReq = new ChannelReq();
        ChannelResp resp = null;
        switch (channel) {
            case SMS_EMAY:
                channelReq.setMsgType(MsgType.SMS_EMAY_QRYBALANCE);
                resp = channelServiceFacade.request(channelReq);
                break;
        }
        QueryBalanceResponse response = new QueryBalanceResponse();
        String balanceStr = resp.getResult();
        double balance = Double.valueOf(balanceStr);
        if (balance < ALARM_THRESHOLD) {
            log.info("发送告警邮件", "当前短信余额为: " + balance + ", 发送告警邮件");
            sendAlarmEmail(channel, balanceStr);
        }
        response.setRetCode(resp.getCode());
        response.setRetMsg(resp.getResult());
        return response;
    }

    /**
     * 发送告警邮件.
     *
     * @param balance
     *            the balance
     */
    private void sendAlarmEmail(Channel channel, String balance) {
        String channelName = channel.getName();
        // 通道余额不足告警邮件模板
        final Long channelAlarmEmailTemplateId = 23L;
        // 发送联系人
        final String toAddress = "jun.li@dcfservice.net|ao.wang@dcfservice.net|ziyi.wang@dcfservice.net";
        SendEmailRequest req = new SendEmailRequest();
        Map<String, String> props = Maps.newHashMap();
        props.put("channel", channelName);
        props.put("balance", balance);
        // 过期时间为半个小时
        Long expiredUtcTime = System.currentTimeMillis() + (30 * 60 * 1000L);
        req.setExpiredUtcTime(expiredUtcTime);
        req.setEmailMessageTemplateId(channelAlarmEmailTemplateId);
        req.setToAddresses(toAddress);
        req.setUserId("message-service");
        req.setProperties(props);
        LogUtils.info(this.getClass(), LOG_TITLE, "发送通道余额不足告警邮件请求", req, TagBuilder.create());
        SendMessageResponse resp = emailSerivce.sendEmail(req);
        LogUtils.info(this.getClass(), LOG_TITLE, "得到通道余额不足告警邮件响应", resp, TagBuilder.create());
    }

    /**
     * 禁用短信模版
     */
    @Override
    public DisableSmsMsgTemplateResponse disableSmsMsgTemplate(DisableSmsMsgTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        LogUtils.info(this.getClass(), LOG_TITLE, "禁用短信模版开始 request ", request, builder);
        DisableSmsMsgTemplateResponse resp = new DisableSmsMsgTemplateResponse();
        smsMessageTemplateService.disableSmsMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("禁用短信模版成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "禁用短信模版结束 response ", resp, builder);
        return resp;
    }

    /**
     * 启用短信模版
     */
    @Override
    public EnableSmsMsgTemplateResponse enableableSmsMsgTemplate(EnableSmsMsgTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        LogUtils.info(this.getClass(), LOG_TITLE, "启用短信模版开始 request ", request, builder);
        EnableSmsMsgTemplateResponse resp = new EnableSmsMsgTemplateResponse();
        smsMessageTemplateService.enableableSmsMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("启用短信模版成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "启用短信模版结束 response ", resp, builder);
        return resp;
    }

    /**
     * 列取短信模版
     */
    @Override
    public ListSmsMsgTemplateResponse listSmsMsgTemplate(ListMsgTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "name", "state");
        LogUtils.info(this.getClass(), LOG_TITLE, "列取短信模版开始 request ", request, builder);
        ListSmsMsgTemplateResponse resp = new ListSmsMsgTemplateResponse();
        PageResult<SmsMsgTemplateVo> result = smsMessageTemplateService.listSmsMsgTemplate(request);
        resp.setResult(result);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("列取短信模版成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "列取短信模版结束 response ", resp, builder);
        return resp;
    }
}
