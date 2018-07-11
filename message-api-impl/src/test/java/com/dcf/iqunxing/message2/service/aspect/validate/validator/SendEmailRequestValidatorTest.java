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

import com.dcf.iqunxing.message2.request.SendEmailRequest;
import com.google.common.collect.Maps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class SendEmailRequestValidatorTest {

    @Autowired
    private SendEmailRequestValidator service;

    @Test
    public void testBusinessValidate() {
        SendEmailRequest req = new SendEmailRequest();
        req.setEmailMessageTemplateId(1L);
        List<String> result = service.businessValidate(req);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testSupports() {
        boolean result = service.supports(SendEmailRequest.class);
        Assert.assertTrue(result);
    }

    @Test
    public void testValidate() {
        SendEmailRequest req = new SendEmailRequest();
        Long templateId = 1L;
        String toAddress = "ao.wang@dcfservice.net";
        String userId = "test";
        Map<String, String> props = Maps.newHashMap();
        props.put("content", "王小翱");
        Long expiredUtcTime = System.currentTimeMillis() + (200 * 1000L);
        req.setEmailMessageTemplateId(templateId);
        req.setToAddresses(toAddress);
        req.setUserId(userId);
        req.setProperties(props);
        req.setExpiredUtcTime(expiredUtcTime);
        Errors errors = new DirectFieldBindingResult(req, req.toString());
        service.validate(req, errors);
        Assert.assertEquals(0L, errors.getErrorCount());
    }

}
