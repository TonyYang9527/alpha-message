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

import com.dcf.iqunxing.message2.request.SendSmsRequest;
import com.google.common.collect.Maps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class SendSmsRequestValidatorTest {

    @Autowired
    private SendSmsRequestValidator service;

    @Test
    public void testBusinessValidate() {
        SendSmsRequest req = new SendSmsRequest();
        req.setTemplateId(1L);
        List<String> result = service.businessValidate(req);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testSupports() {
        boolean result = service.supports(SendSmsRequest.class);
        Assert.assertTrue(result);
    }

    @Test
    public void testValidate() {
        SendSmsRequest req = new SendSmsRequest();
        Map<String, String> props = Maps.newHashMap();
        props.put("content", "王小翱");
        req.setMobiles("13661992232");
        req.setTemplateId(1L);
        req.setUserId("test");
        req.setProperties(props);
        req.setExpiredUtcTime(System.currentTimeMillis() + (200 * 1000L));
        Errors errors = new DirectFieldBindingResult(req, req.toString());
        service.validate(req, errors);
        Assert.assertEquals(0L, errors.getErrorCount());
    }

}
