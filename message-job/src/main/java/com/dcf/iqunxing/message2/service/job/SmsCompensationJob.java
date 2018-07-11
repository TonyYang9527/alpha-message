package com.dcf.iqunxing.message2.service.job;

import java.util.Date;
import java.util.List;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dcf.iqunxing.message2.entity.SmsMessage;
import com.dcf.iqunxing.message2.enums.SendState;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.service.internal.SmsService;
import com.dcf.iqunxing.message2.util.Constants;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.TransformToQueuePriorityUtil;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Component
public class SmsCompensationJob extends BaseMessageJob {

    @Autowired
    protected SmsService smsService;

    @Override
    protected Logger initLogger() {
        return LogUtils.SMS;
    }

    @Override
    protected String initTaskName() {
        return "短信补偿任务";
    }

    @Override
    protected Long initJobId() {
        return Constants.SMS_CPST_JOB_ID;
    }

    @Override
    protected int initQuerySize() {
        return 1000;
    }

    @Override
    protected void updateMsgState(final Long id) {
        smsService.updateState(id, SendState.JOB_HANDLED);
    }

    @Override
    public List<QueuePriorityVo> getQueuePriorityVos(final Date currentDate) {
        List<SmsMessage> result = smsService.getLeaveMsg(currentDate, querySize);
        List<QueuePriorityVo> queuePriorityVos = Lists.transform(result, new Function<SmsMessage, QueuePriorityVo>() {

            @Override
            public QueuePriorityVo apply(SmsMessage entity) {
                return TransformToQueuePriorityUtil.transform(entity);
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
