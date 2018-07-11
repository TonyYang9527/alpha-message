package com.dcf.iqunxing.message2.service.job;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dcf.iqunxing.message2.entity.SiteMessage;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.service.internal.SiteMessageService;
import com.dcf.iqunxing.message2.util.Constants;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.TransformToQueuePriorityUtil;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Component
public class SiteMessageScheduleSendingJob extends BaseMessageJob {

    @Autowired
    protected SiteMessageService siteMessageService;

    @Override
    protected Logger initLogger() {
        return LogUtils.SITE_MSG;
    }

    @Override
    protected String initTaskName() {
        return "站内信定时任务";
    }

    @Override
    protected Long initJobId() {
        return Constants.SITEMSG_JOB_ID;
    }

    @Override
    protected int initQuerySize() {
        return 1000;
    }

    @Override
    protected void updateMsgState(final Long id) {
        siteMessageService.send(id);
    }

    @Override
    protected boolean handlePriorityVo(final QueuePriorityVo queuePriorityVo) {
        // 不需要发送到服务端的queue
        return true;
    }

    @Override
    public List<QueuePriorityVo> getQueuePriorityVos(final Date currentDate) {
        List<SiteMessage> result = siteMessageService.getTimedMsg(currentDate, querySize);
        List<QueuePriorityVo> queuePriorityVos = Lists.transform(result, new Function<SiteMessage, QueuePriorityVo>() {

            @Override
            public QueuePriorityVo apply(SiteMessage entity) {
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
