package com.dcf.iqunxing.message2.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.model.enums.Channel;
import com.dcf.iqunxing.message2.request.QueryBalanceRequest;
import com.dcf.iqunxing.message2.response.QueryBalanceResponse;
import com.dcf.iqunxing.message2.service.ISmsService;
import com.dcf.iqunxing.message2.util.LogUtils;

@Component
public class QueryBalanceJob {

    private static final String LOG_TITLE = "查询通道余额";

    /** 日志对象. */
    private static final ILog log = LogManager.getLogger(QueryBalanceJob.class);

    @Autowired
    private ISmsService smsService;

    /**
     * 查询通道余额，每天中午12点执行一次.
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void queryBalance() {
        TagBuilder builder = TagBuilder.create().append("name", "queryBalance");
        LogUtils.info(LogUtils.SMS, log, LOG_TITLE, "开始执行查询通道余额", null, builder);
        QueryBalanceRequest req = new QueryBalanceRequest();
        req.setChannel(Channel.SMS_EMAY);
        QueryBalanceResponse resp = smsService.queryBalance(req);
        LogUtils.info(LogUtils.SMS, log, LOG_TITLE, "结束执行查询通道余额", resp, builder);
    }

}
