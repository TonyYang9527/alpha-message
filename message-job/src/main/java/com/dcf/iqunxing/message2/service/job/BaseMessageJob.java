package com.dcf.iqunxing.message2.service.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.entity.MessageJob;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.service.IMessageJobService;
import com.dcf.iqunxing.message2.service.internal.JobService;
import com.dcf.iqunxing.message2.util.Constants;
import com.dcf.iqunxing.message2.util.LogUtils;

/**
 * 定时发送抽象类.
 */
public abstract class BaseMessageJob {

    private static final String LOG_TITLE = "通知JOB服务";

    /** 日志对象. */
    protected Logger logger;

    /** 任务名称. */
    protected String taskName;

    /** 对应JobId. */
    protected Long jobId;

    /** 查询数量. */
    protected int querySize;

    @Autowired
    protected IMessageJobService messageJobService;

    @Autowired
    protected JobService jobService;

    protected volatile boolean shutdown = false;

    private ILog log = LogManager.getLogger(BaseMessageJob.class);

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 初始化参数.
     */
    @PostConstruct
    protected void initParams() {
        logger = initLogger();
        taskName = initTaskName();
        jobId = initJobId();
        querySize = initQuerySize();
    }

    protected abstract Logger initLogger();

    protected abstract String initTaskName();

    protected abstract Long initJobId();

    protected abstract int initQuerySize();

    protected abstract void updateMsgState(final Long id);

    protected abstract List<QueuePriorityVo> getQueuePriorityVos(final Date currentDate);

    /**
     * 执行job的方法，需要配置具体的定时器注解.
     */
    protected abstract void executeJob();

    /**
     * 执行JOB的模板类.
     */
    protected final void execute() {
        final Date currentDate = new Date();
        TagBuilder builder = TagBuilder.create().append("id", jobId).append("taskName", taskName)
                .append("date", format.format(currentDate));
        LogUtils.info(logger, log, LOG_TITLE, "开始执行[" + taskName + "]", null, builder);
        try {
            boolean doit = true;
            while (doit && !shutdown) {
                List<QueuePriorityVo> queuePriorityVos = getQueuePriorityVos(currentDate);
                LogUtils.info(logger, log, LOG_TITLE, "开始发送[" + taskName + "]", "本次需要发送的消息有[" + queuePriorityVos.size() + "]条",
                        builder);
                // 获取带定时发送信息
                if (queuePriorityVos.size() < querySize) {
                    doit = false;
                }
                for (QueuePriorityVo queuePriorityVo : queuePriorityVos) {
                    if (shutdown) {
                        break;
                    }
                    sendToQueueAndUpdMsgState(queuePriorityVo);
                }
            }
            updateJobInfo(currentDate);
        } catch (Throwable t) {
            LogUtils.error(logger, log, LOG_TITLE, "执行[" + taskName + "]", "JOB执行异常", t, builder);
        }
        LogUtils.info(logger, log, LOG_TITLE, "结束执行[" + taskName + "]", null, builder);
    }

    /**
     * 将任务发送至优先级队列，并更新JOB的状态.
     *
     * @param queuePriorityVos
     *            the queue priority vos
     */
    protected final void sendToQueueAndUpdMsgState(final QueuePriorityVo queuePriorityVo) {
        Long id = queuePriorityVo.getId();
        TagBuilder builder = LogUtils.getTagBuilder(queuePriorityVo, "id", "priority", "msgType");
        builder.append("taskName", taskName);
        Date scheduleUtcTime = queuePriorityVo.getScheduleTime();
        if (scheduleUtcTime != null) {
            builder.append("scheduleTime", DateFormatUtils.format(scheduleUtcTime, "yyyy-MM-dd HH:mm:ss"));
        }
        try {
            // 丢进队列发送
            boolean result = handlePriorityVo(queuePriorityVo);
            LogUtils.info(logger, log, LOG_TITLE, "将定时消息发送至消息队列成功", queuePriorityVo, builder);
            // 更新信息发送状态为15(job处理完成)
            if (result) {
                updateMsgState(id);
                LogUtils.info(logger, log, LOG_TITLE, "更新信息发送状态为job处理完成", queuePriorityVo, builder);
            }
        } catch (Exception e) {
            LogUtils.error(logger, log, LOG_TITLE, "将定时消息发送至消息队列失败", queuePriorityVo, e, builder);
        }
    }

    protected boolean handlePriorityVo(final QueuePriorityVo queuePriorityVo) {
        return messageJobService.addMessageToQueue(queuePriorityVo, Constants.MESSAGE_JOB_SERVICE_PASSWORD);
    }

    /**
     * 更新JOB信息.
     *
     * @param currentDate
     *            the current date
     */
    protected final void updateJobInfo(final Date currentDate) {
        MessageJob messageJob = jobService.getMsgJob(jobId);
        messageJob.setLastStartTime(currentDate);
        messageJob.setLastEndTime(new Date());
        messageJob.setLastScheduleTime(currentDate);
        messageJob.setUpdatedTime(new Date());
        jobService.updateMessageJob(messageJob);
        TagBuilder builder = LogUtils.getTagBuilder(messageJob, "id").append("taskName", taskName);
        LogUtils.info(logger, log, LOG_TITLE, "更新[" + taskName + "]的Job状态", messageJob, builder);
    }
}
