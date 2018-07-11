package com.dcf.iqunxing.message2.service.send.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dcf.iqunxing.message2.entity.SmsMessage;
import com.dcf.iqunxing.message2.entity.SmsMessageProperty;
import com.dcf.iqunxing.message2.entity.SmsMessageTemplate;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.model.enums.SmsChannel;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelResp;
import com.dcf.iqunxing.message2.service.channel.entity.req.SmsChannelReq;
import com.dcf.iqunxing.message2.service.internal.sms.SmsMessagePropertyService;
import com.dcf.iqunxing.message2.service.internal.sms.SmsMessageService;
import com.dcf.iqunxing.message2.service.internal.sms.SmsMessageTemplateService;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.MessageAssembleUtil;
import com.dcf.iqunxing.message2.util.spring.SpringApplicationContextHolder;
import com.google.common.collect.Maps;

public class SmsTask extends Task {

    private static final String LOG_TITLE = "短信发送任务服务";

    /** 短信切割标识. */
    private static final String SPILT_REGEX = "\\,";

    private SmsMessageService smsMessageService;

    private SmsMessageTemplateService templateService;

    private SmsMessagePropertyService messagePropertyService;

    public SmsTask() {
        smsMessageService = SpringApplicationContextHolder.getSpringBean(SmsMessageService.class);
        templateService = SpringApplicationContextHolder.getSpringBean(SmsMessageTemplateService.class);
        messagePropertyService = SpringApplicationContextHolder.getSpringBean(SmsMessagePropertyService.class);
    }

    @Override
    public void sendTask() {
        LogUtils.info(this.getClass(), LOG_TITLE, "开始短信发送任务", queuePriorityVo, getTagBuilder());
        try {
            // 获取短信主体
            SmsMessage smsMessage = getSmsMessage(queuePriorityVo);
            // 获取短信模板
            SmsMessageTemplate template = getSmsMessageTemplate(smsMessage);
            // 获取短信Key/Value
            Map<String, String> props = getProps(smsMessage);
            // 拼装内容发送至网关
            Long id = smsMessage.getId();
            String content = MessageAssembleUtil.assembleContent(template.getContent(), props);
            LogUtils.info(this.getClass(), LOG_TITLE, "拼装短信内容完成", content, getTagBuilder());
            String mobiles = smsMessage.getMobiles();
            // 中央日志
            SmsChannel smsChannel = SmsChannel.fromValue(template.getChannel());
            ChannelResp channelResp = sentToChannel(id, content, mobiles, template.getType().shortValue(), smsChannel);
            // 更新保存结果
            saveSmsSendResult(channelResp);
        } catch (Throwable t) {
            LogUtils.error(this.getClass(), LOG_TITLE, "发送短信出错", queuePriorityVo, t, getTagBuilder());
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "结束短信发送任务", queuePriorityVo, getTagBuilder());

    }

    /**
     * 获取SmsMessage.
     *
     * @param queuePriorityVo
     *            the queue priority vo
     * @return the sms message
     */
    private SmsMessage getSmsMessage(QueuePriorityVo queuePriorityVo) {
        Long msgId = queuePriorityVo.getId();
        SmsMessage smsMessage = smsMessageService.getSmsMessageById(msgId);
        LogUtils.info(this.getClass(), LOG_TITLE, "获取短信主体", smsMessage, getTagBuilder());
        return smsMessage;
    }

    /**
     * 获取短信模板.
     *
     * @param smsMessage
     *            the sms message
     * @return the sms message template
     */
    private SmsMessageTemplate getSmsMessageTemplate(SmsMessage smsMessage) {
        Long templateId = smsMessage.getSmsMessageTemplateId();
        SmsMessageTemplate template = templateService.getMsgTemplate(templateId);
        LogUtils.info(this.getClass(), LOG_TITLE, "获取短信模板", template, getTagBuilder());
        return template;
    }

    /**
     * 获取短信Key/Value.
     *
     * @param smsMessage
     *            the sms message
     * @return the props
     */
    private Map<String, String> getProps(SmsMessage smsMessage) {
        Long msgId = smsMessage.getId();
        List<SmsMessageProperty> msgProperties = messagePropertyService.getSmsPropsByMessageId(msgId);
        Map<String, String> props = Maps.newHashMap();
        for (SmsMessageProperty po : msgProperties) {
            props.put(po.getPropKey(), po.getPropValue());
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "获取短信Key/Value:" + props, msgProperties, getTagBuilder());
        return props;
    }

    /**
     * 构造网关请求对象并发送.
     *
     * @param id
     *            the id
     * @param content
     *            the content
     * @param mobiles
     *            the mobiles
     * @param type
     *            the type
     * @return the channel resp
     */
    private ChannelResp sentToChannel(Long id, String content, String mobiles, short type, SmsChannel smsChannel) {
        String[] mobileArr = mobiles.split(SPILT_REGEX);
        SmsChannelReq req = new SmsChannelReq(id, mobileArr, content, type, smsChannel);
        LogUtils.info(this.getClass(), LOG_TITLE, "构造网关请求对象", req, getTagBuilder());
        ChannelResp resp = channelServiceFacade.request(req);
        LogUtils.info(this.getClass(), LOG_TITLE, "短信网关发送响应", resp, getTagBuilder());
        return resp;
    }

    /**
     * 更新返回结果.
     *
     * @param channelResp
     *            the channel resp
     */
    private void saveSmsSendResult(ChannelResp channelResp) {
        Long id = channelResp.getId();
        Boolean isSucesss = channelResp.isSuccess();
        String code = channelResp.getCode();
        Date currentDate = new Date();
        smsMessageService.updateSmsSendResult(id, isSucesss, code, currentDate);
        LogUtils.info(this.getClass(), LOG_TITLE, "根据网关返回结果更新短信主体", channelResp, getTagBuilder());
    }

}
