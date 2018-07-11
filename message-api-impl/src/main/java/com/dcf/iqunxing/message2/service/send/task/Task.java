package com.dcf.iqunxing.message2.service.send.task;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.service.channel.ChannelServiceFacade;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.spring.SpringApplicationContextHolder;

/**
 * 发送任务抽象类
 */
public abstract class Task implements Runnable {

    /** 队列中的优先级对象. */
    protected QueuePriorityVo queuePriorityVo;

    protected ChannelServiceFacade channelServiceFacade = SpringApplicationContextHolder.getSpringBean(ChannelServiceFacade.class);

    public void setQueuePriorityVo(QueuePriorityVo queuePriorityVo) {
        this.queuePriorityVo = queuePriorityVo;
    }

    /**
     * 发送业务逻辑实现. 1. 获取短信主体 2. 获取短信模板 3. 获取短信Key/Value 4. 拼装内容发送至网关 5. 更新保存结果
     */
    public abstract void sendTask();

    @Override
    public void run() {
        sendTask();
    }

    /**
     * 根据队列中的优先级对象构造TagBuilder.
     */
    protected TagBuilder getTagBuilder() {
        TagBuilder builder = LogUtils.getTagBuilder(queuePriorityVo, "id", "priority", "msgType");
        Date scheduleUtcTime = queuePriorityVo.getScheduleTime();
        if (scheduleUtcTime != null) {
            builder.append("scheduleTime", DateFormatUtils.format(scheduleUtcTime, "yyyy-MM-dd HH:mm:ss"));
        }
        return builder;
    }

}
