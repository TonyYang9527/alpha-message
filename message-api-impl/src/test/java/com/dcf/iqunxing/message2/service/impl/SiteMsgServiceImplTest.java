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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dcf.iqunxing.message2.request.SendSiteMsgRequest;
import com.dcf.iqunxing.message2.response.RetCodeConst;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.google.common.collect.Maps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class SiteMsgServiceImplTest {

    @Autowired
    private SiteMsgServiceImpl service;

    @Test
    public void testSendSiteMsg() {
        SendSiteMsgRequest req = new SendSiteMsgRequest();
        Long templateId = 1L;
        String userId = "1";
        Map<String, String> props = Maps.newHashMap();
        props.put("content", "王小翱");
        Long expiredUtcTime = System.currentTimeMillis() + (200 * 1000L);
        req.setSiteMessageTemplateId(templateId);
        req.setReceiverId(userId);
        req.setSender(userId);
        req.setProperties(props);
        req.setExpiredUtcTime(expiredUtcTime);
        SendMessageResponse resp = service.sendSiteMsg(req);
        System.out.println(ToStringBuilder.reflectionToString(resp, ToStringStyle.MULTI_LINE_STYLE));
        assertEquals(RetCodeConst.SUCCESS, resp.getRetCode());

    }

    @Test
    @Ignore
    public void testCreateSiteMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testUpdateSiteMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testDeleteSiteMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testCreateSiteMsg() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testUpdateSiteMsg() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testDeleteSiteMsg() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testReadSiteMsg() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testListSiteMsg() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testGetSiteMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testListSiteMsgTemplate() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testGetSiteMsg() {
        fail("Not yet implemented");
    }

}
