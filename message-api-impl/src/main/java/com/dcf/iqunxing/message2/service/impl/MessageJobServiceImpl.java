package com.dcf.iqunxing.message2.service.impl;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.model.enums.MsgType;
import com.dcf.iqunxing.message2.service.IMessageJobService;
import com.dcf.iqunxing.message2.service.queue.EmailMessageQueueService;
import com.dcf.iqunxing.message2.service.queue.PushMessageQueueService;
import com.dcf.iqunxing.message2.service.queue.QueueService;
import com.dcf.iqunxing.message2.service.queue.SmsMessageQueueService;
import com.dcf.iqunxing.message2.util.Constants;
import com.dcf.iqunxing.message2.util.LogUtils;

@Service("messageJobService")
public class MessageJobServiceImpl implements IMessageJobService {

    private static final ILog log = LogManager.getLogger(MessageJobServiceImpl.class);

    private static final String LOG_TITLE = "提供JOB调用的服务";

    @Autowired
    protected SmsMessageQueueService smsMessageQueueService;

    @Autowired
    protected EmailMessageQueueService emailMessageQueueService;

    @Autowired
    protected PushMessageQueueService pushMessageQueueService;

    @SuppressWarnings("rawtypes")
    @Override
    public boolean addMessageToQueue(QueuePriorityVo vo, String password) {
        TagBuilder builder = getTagBuilder(vo);
        if (!Constants.MESSAGE_JOB_SERVICE_PASSWORD.equals(password)) {
            LogUtils.info(this.getClass(), LOG_TITLE, "插入定时消息出错，密码不正确: " + password, vo.getId(), builder);
            return false;
        }
        if (vo == null || vo.getMsgType() == null) {
            LogUtils.error(this.getClass(), LOG_TITLE, "获取到的QueuePriorityVo对象非法", vo, null, builder);
            return false;
        }
        QueueService queueService = null;
        MsgType msgType = vo.getMsgType();
        switch (msgType) {
            case SMS:
                queueService = smsMessageQueueService;
                break;
            case EMAIL:
                queueService = emailMessageQueueService;
                break;
            case PUSH:
                queueService = pushMessageQueueService;
                break;
            default:
                break;
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "从Job接受到消息", vo.getId(), builder);
        boolean result = queueService.offerToQueue(vo);
        if (!result) {
            LogUtils.error(this.getClass(), LOG_TITLE, "插入消息队列失败", vo.getId(), null, builder);
        }
        LogUtils.info(this.getClass(), LOG_TITLE, "从Job接受到消息,插入消息队列成功", vo.getId(), builder);
        return result;
    }

    /**
     * 根据队列中的优先级对象构造TagBuilder.
     */
    protected TagBuilder getTagBuilder(QueuePriorityVo queuePriorityVo) {
        TagBuilder builder = LogUtils.getTagBuilder(queuePriorityVo, "id", "priority", "msgType");
        Date scheduleUtcTime = queuePriorityVo.getScheduleTime();
        if (scheduleUtcTime != null) {
            builder.append("scheduleTime", DateFormatUtils.format(scheduleUtcTime, "yyyy-MM-dd HH:mm:ss"));
        }
        return builder;
    }

}
