package com.dcf.iqunxing.message2.service.job;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dcf.iqunxing.message2.entity.PushMessage;
import com.dcf.iqunxing.message2.enums.SendState;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.service.internal.PushService;
import com.dcf.iqunxing.message2.util.Constants;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.TransformToQueuePriorityUtil;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Component
public class PushScheduleSendingJob extends BaseMessageJob {

    @Autowired
    protected PushService pushService;

    @Override
    protected Logger initLogger() {
        return LogUtils.PUSH;
    }

    @Override
    protected String initTaskName() {
        return "push定时任务";
    }

    @Override
    protected Long initJobId() {
        return Constants.PUSH_JOB_ID;
    }

    @Override
    protected int initQuerySize() {
        return 1000;
    }

    @Override
    protected void updateMsgState(final Long id) {
        pushService.updateState(id, SendState.JOB_HANDLED);
    }

    @Override
    public List<QueuePriorityVo> getQueuePriorityVos(final Date currentDate) {
        List<PushMessage> result = pushService.getTimedMsg(currentDate, querySize);
        List<QueuePriorityVo> queuePriorityVos = Lists.transform(result, new Function<PushMessage, QueuePriorityVo>() {

            @Override
            public QueuePriorityVo apply(PushMessage entity) {
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
}
