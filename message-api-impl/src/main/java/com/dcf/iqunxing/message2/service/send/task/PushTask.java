package com.dcf.iqunxing.message2.service.send.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.entity.PushMessage;
import com.dcf.iqunxing.message2.entity.PushMessageProperty;
import com.dcf.iqunxing.message2.entity.PushMessageTemplate;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelResp;
import com.dcf.iqunxing.message2.service.channel.entity.req.PushChannelReq;
import com.dcf.iqunxing.message2.service.internal.push.PushMessagePropertyService;
import com.dcf.iqunxing.message2.service.internal.push.PushMessageService;
import com.dcf.iqunxing.message2.service.internal.push.PushMessageTemplateService;
import com.dcf.iqunxing.message2.util.Constant;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.MessageAssembleUtil;
import com.dcf.iqunxing.message2.util.spring.SpringApplicationContextHolder;
import com.google.common.collect.Maps;

public class PushTask extends Task {

    private static final String LOG_TITLE = "push发送任务服务";

    ILog log = LogManager.getLogger(PushTask.class);

    private PushMessageService pushMessageService;

    private PushMessageTemplateService templateService;

    private PushMessagePropertyService messagePropertyService;

    public PushTask() {
        pushMessageService = SpringApplicationContextHolder.getSpringBean(PushMessageService.class);
        templateService = SpringApplicationContextHolder.getSpringBean(PushMessageTemplateService.class);
        messagePropertyService = SpringApplicationContextHolder.getSpringBean(PushMessagePropertyService.class);
    }

    @Override
    public void sendTask() {
        LogUtils.info(this.getClass(), LOG_TITLE, "开始push发送任务", queuePriorityVo, getTagBuilder());
        try {
            // 获取push主体
            PushMessage pushMessage = getPushMessage(queuePriorityVo);
            // 获取push模板
            PushMessageTemplate template = getPushMessageTemplate(pushMessage);
            // 获取push Key/Value
            Map<String, String> props = getProps(pushMessage);
            // 拼装内容发送至网关
            Long id = pushMessage.getId();
            String title = null;
            String content = null;
            String addition = null;
            Map<String, Object> custom = Maps.newHashMap();
            if (template.getTitle() != null) {
                title = MessageAssembleUtil.assembleContent(template.getTitle(), props);
            }
            if (template.getContent() != null) {
                content = MessageAssembleUtil.assembleContent(template.getContent(), props);
            }
            if (template.getAddition() != null) {
                // 拼装addition内容
                addition = MessageAssembleUtil.assembleContent(template.getAddition(), props);
                // TODO
                String st[] = addition.split(Constant.ADDITION_KV_SPILT);
                int num = st.length;
                for (int i = 0; i < num; i++) {
                    String stt[] = new String[1];
                    if (st[i] != null) {
                        stt = st[i].trim().split(Constant.ADDITION_SPILT);
                        if (stt != null && stt.length == 2 && stt[0] != null && stt[1] != null) {
                            custom.put(stt[0], stt[1]);
                        }
                    }
                }
            }
            LogUtils.info(this.getClass(), LOG_TITLE, "拼装push内容完成", content, getTagBuilder());
            int deviceType = pushMessage.getDeviceType();
            String deviceId = pushMessage.getDeviceId();
            // 中央日志
            ChannelResp channelResp = sentToChannel(id, title, content, deviceType, deviceId, custom);
            // 更新保存结果
            savePushSendResult(channelResp);
        } catch (Throwable t) {
            LogUtils.error(this.getClass(), LOG_TITLE, "发送push出错", queuePriorityVo, t, getTagBuilder());
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "结束push发送任务", queuePriorityVo, getTagBuilder());

    }

    /**
     * 获取PushMessage.
     *
     * @param queuePriorityVo
     *            the queue priority vo
     * @return the push message
     */
    private PushMessage getPushMessage(QueuePriorityVo queuePriorityVo) {
        Long msgId = queuePriorityVo.getId();
        PushMessage pushMessage = pushMessageService.getPushMessageById(msgId);
        LogUtils.info(this.getClass(), LOG_TITLE, "获取push主体", pushMessage, getTagBuilder());
        return pushMessage;
    }

    /**
     * 获取push模板.
     *
     * @param pushMessage
     *            the push message
     * @return the push message template
     */
    private PushMessageTemplate getPushMessageTemplate(PushMessage pushMessage) {
        Long templateId = pushMessage.getPushMessageTemplateId();
        PushMessageTemplate template = templateService.getMsgTemplate(templateId);
        LogUtils.info(this.getClass(), LOG_TITLE, "获取push模板", template, getTagBuilder());
        return template;
    }

    /**
     * 获取push Key/Value.
     *
     * @param pushMessage
     *            the push message
     * @return the props
     */
    private Map<String, String> getProps(PushMessage pushMessage) {
        Long msgId = pushMessage.getId();
        List<PushMessageProperty> msgProperties = messagePropertyService.getPushPropsByMessageId(msgId);
        Map<String, String> props = Maps.newHashMap();
        for (PushMessageProperty po : msgProperties) {
            props.put(po.getPropKey(), po.getPropValue());
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "获取push Key/Value:" + props, msgProperties, getTagBuilder());
        return props;
    }

    /**
     * 构造网关请求对象并发送.
     *
     * @param id
     *            the id
     * @param content
     *            the content
     * @param receiverId
     *            the 接受者的userId
     * @return the channel resp
     */
    private ChannelResp sentToChannel(long id, String title, String content, int deviceType, String deviceId,
            Map<String, Object> custom) {
        PushChannelReq req = new PushChannelReq(id, title, content, deviceType, deviceId, custom);
        LogUtils.info(this.getClass(), LOG_TITLE, "构造网关请求对象", req, getTagBuilder());
        ChannelResp resp = channelServiceFacade.request(req);
        LogUtils.info(this.getClass(), LOG_TITLE, "push网关发送响应", resp, getTagBuilder());
        return resp;
    }

    /**
     * 更新返回结果.
     *
     * @param channelResp
     *            the channel resp
     */
    private void savePushSendResult(ChannelResp channelResp) {
        Long id = channelResp.getId();
        Boolean isSucesss = channelResp.isSuccess();
        String code = channelResp.getCode();
        Date currentDate = new Date();
        pushMessageService.updatePushSendResult(id, isSucesss, code, currentDate);
        LogUtils.info(this.getClass(), LOG_TITLE, "根据网关返回结果更新push主体", channelResp, getTagBuilder());
    }

}
