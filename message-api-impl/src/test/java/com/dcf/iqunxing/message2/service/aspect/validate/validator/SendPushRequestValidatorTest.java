package com.dcf.iqunxing.message2.service.aspect.validate.validator;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import com.dcf.iqunxing.message2.request.SendPushRequest;
import com.google.common.collect.Maps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class SendPushRequestValidatorTest {

    @Autowired
    private SendPushRequestValidator service;

    @Test
    public void testBusinessValidate() {
        SendPushRequest req = new SendPushRequest();
        req.setPushMessageTemplateId(1L);
        List<String> result = service.businessValidate(req);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testSupports() {
        boolean result = service.supports(SendPushRequest.class);
        Assert.assertTrue(result);
    }

    @Test
    public void testValidate() {
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
        Errors errors = new DirectFieldBindingResult(req, req.toString());
        service.validate(req, errors);
        Assert.assertEquals(0L, errors.getErrorCount());
    }

}
