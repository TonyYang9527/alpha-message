package com.dcf.iqunxing.message2.service.job;

import java.util.Date;
import java.util.List;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dcf.iqunxing.message2.entity.EmailMessage;
import com.dcf.iqunxing.message2.enums.SendState;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.service.internal.EmailService;
import com.dcf.iqunxing.message2.util.Constants;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.TransformToQueuePriorityUtil;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Component
public class EmailScheduleSendingJob extends BaseMessageJob {

    @Autowired
    protected EmailService emailService;

    @Override
    protected Logger initLogger() {
        return LogUtils.EMAIL;
    }

    @Override
    protected String initTaskName() {
        return "邮件定时任务";
    }

    @Override
    protected Long initJobId() {
        return Constants.EMAIL_JOB_ID;
    }

    @Override
    protected int initQuerySize() {
        return 1000;
    }

    @Override
    protected void updateMsgState(final Long id) {
        emailService.updateState(id, SendState.JOB_HANDLED);
    }

    @Override
    public List<QueuePriorityVo> getQueuePriorityVos(final Date currentDate) {
        List<EmailMessage> result = emailService.getTimedMsg(currentDate, querySize);
        // logger.info("邮件", format.format(currentDate),
        // TagBuilder.create().append("EmailScheduleSendingJob updateMsgState",
        // "param")
        // .build());
        // logger.info("邮件", JsonMapper.nonEmptyMapper().toJson(result),
        // TagBuilder.create().append("EmailScheduleSendingJob
        // getQueuePriorityVos",
        // "result").build());
        // MsgLogger.EMAIL.info("EmailScheduleSendingJob getQueuePriorityVos
        // ,param{} ,result{}",
        // JsonMapper.nonEmptyMapper()
        // .toJson(currentDate), JsonMapper.nonEmptyMapper().toJson(result));
        List<QueuePriorityVo> queuePriorityVos = Lists.transform(result, new Function<EmailMessage, QueuePriorityVo>() {

            @Override
            public QueuePriorityVo apply(EmailMessage emailMessage) {
                return TransformToQueuePriorityUtil.transform(emailMessage);
            }
        });

        return queuePriorityVos;
    }

    @Override
    @Scheduled(fixedDelay = 60 * 1000)
    protected void executeJob() {
        super.execute();
    }

    @PreDestroy
    public synchronized void destory() {
        shutdown = true;
    }
}
