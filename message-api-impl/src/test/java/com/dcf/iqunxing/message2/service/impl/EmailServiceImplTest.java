package com.dcf.iqunxing.message2.service.impl;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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

import com.dcf.iqunxing.message2.request.SendEmailRequest;
import com.dcf.iqunxing.message2.response.RetCodeConst;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.google.common.collect.Maps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class EmailServiceImplTest {

    @Autowired
    private EmailServiceImpl service;

    @Autowired
    @Qualifier("emailTaskExecutor")
    private ThreadPoolTaskExecutor emailTaskExecutor;

    @Test
    // @Ignore
    public void testSendEmail() throws Exception {
        SendEmailRequest req = new SendEmailRequest();
        Long templateId = 26L;
        String toAddress = "ao.wang@dcfservice.net";
        String userId = "test";
        Map<String, String> props = Maps.newHashMap();
        String content = FileUtils.readFileToString(new File("F:/test.txt"), "utf-8");
        System.out.println(content);
        props.put("content", content);
        Long expiredUtcTime = System.currentTimeMillis() + (200 * 1000L);
        req.setEmailMessageTemplateId(templateId);
        req.setToAddresses(toAddress);
        req.setUserId(userId);
        req.setProperties(props);
        req.setExpiredUtcTime(expiredUtcTime);
        SendMessageResponse resp = service.sendEmail(req);
        Thread.sleep(1 * 1000);
        while (emailTaskExecutor.getActiveCount() > 0) {
            Thread.sleep(1 * 1000);
        }
        System.out.println(ToStringBuilder.reflectionToString(resp, ToStringStyle.MULTI_LINE_STYLE));
        Assert.assertEquals(RetCodeConst.SUCCESS, resp.getRetCode());
    }

    @Test
    @Ignore
    public void testCheckSendParams() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testCreateEmailMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testDeleteEmailMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testUpdateEmailMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testGetEmailMsgTemplate() {
        fail("Not yet implemented");
    }

}
