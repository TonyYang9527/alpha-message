package com.dcf.iqunxing.message2.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dcf.iqunxing.message2.model.enums.Channel;
import com.dcf.iqunxing.message2.request.QueryBalanceRequest;
import com.dcf.iqunxing.message2.request.SendSmsRequest;
import com.dcf.iqunxing.message2.response.QueryBalanceResponse;
import com.dcf.iqunxing.message2.response.RetCodeConst;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.google.common.collect.Maps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class SmsServiceImplTest {

    @Autowired
    private SmsServiceImpl service;

    @Autowired
    @Qualifier("smsTaskExecutor")
    private ThreadPoolTaskExecutor smsTaskExecutor;

    @Test
    // @Ignore
    public void testSendSms() throws Exception {
        SendSmsRequest req = new SendSmsRequest();
        Map<String, String> props = Maps.newHashMap();
        props.put("content",
                "您好！群星金融网携手测试链属3开启针对全国经销商的互联网融资服务通道。您的融资额度：10000.00元，签约后可立即使用。您的注册邀请码：874619，下载APP即可完成注册，点击下载链接：http://m.iqunxing.com/d.htm 开始体验吧。如不再接收该消息，回复TD退订。");
        req.setMobiles("13661992232");
        req.setTemplateId(1L);
        req.setUserId("test");
        req.setProperties(props);
        req.setExpiredUtcTime(System.currentTimeMillis() + (200 * 1000L));
        SendMessageResponse resp = service.sendSms(req);
        Thread.sleep(1 * 1000);
        while (smsTaskExecutor.getActiveCount() > 0) {
            Thread.sleep(1 * 1000);
        }
        System.out.println(ToStringBuilder.reflectionToString(resp, ToStringStyle.MULTI_LINE_STYLE));
        assertEquals(RetCodeConst.SUCCESS, resp.getRetCode());
    }

    @Test
    @Ignore
    public void testSaveReqToDB() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testCreateSmsMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testDeleteSmsMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testUpdateSmsMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testGetSmsMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    public void testQueryBalance() {
        QueryBalanceRequest req = new QueryBalanceRequest();
        req.setChannel(Channel.SMS_EMAY);
        QueryBalanceResponse resp = service.queryBalance(req);
        System.out.println(ToStringBuilder.reflectionToString(resp, ToStringStyle.MULTI_LINE_STYLE));
        assertEquals(RetCodeConst.SUCCESS, resp.getRetCode());
    }

}
