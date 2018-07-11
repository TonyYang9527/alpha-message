package com.dcf.iqunxing.message2.service.impl;

import static org.junit.Assert.fail;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dcf.iqunxing.message2.request.SendPushRequest;
import com.dcf.iqunxing.message2.response.RetCodeConst;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.google.common.collect.Maps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class PushServiceImplTest {

    @Autowired
    private PushServiceImpl service;

    @Autowired
    @Qualifier("pushTaskExecutor")
    private ThreadPoolTaskExecutor pushTaskExecutor;

    @Test
    @Ignore
    public void testSendPush() throws Exception {
        SendPushRequest req = new SendPushRequest();
        String receiverId = "U220150107153651123";
        Long templateId = 1L;
        String userId = "test";
        Map<String, String> props = Maps.newHashMap();
        props.put("content", "您有3条可申请融资的交易,快去融资吧!");
        Long expiredUtcTime = System.currentTimeMillis() + (200 * 1000L);
        req.setReceiverId(receiverId);
        req.setPushMessageTemplateId(templateId);
        req.setUserId(userId);
        req.setProperties(props);
        req.setExpiredUtcTime(expiredUtcTime);
        SendMessageResponse resp = service.sendPush(req);
        Thread.sleep(1 * 1000);
        while (pushTaskExecutor.getActiveCount() > 0) {
            Thread.sleep(1 * 1000);
        }
        System.out.println(ToStringBuilder.reflectionToString(resp, ToStringStyle.MULTI_LINE_STYLE));
        Assert.assertEquals(RetCodeConst.SUCCESS, resp.getRetCode());
    }

    @Test
    @Ignore
    public void testSaveReqToDB() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testCreatePushMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testDeletePushMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testUpdatePushMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testGetPushMsgTemplate() {
        fail("Not yet implemented");
    }

}
