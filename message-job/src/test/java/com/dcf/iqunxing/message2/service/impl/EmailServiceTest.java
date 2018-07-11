package com.dcf.iqunxing.message2.service.impl;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dcf.iqunxing.message2.entity.EmailMessage;
import com.dcf.iqunxing.message2.enums.SendState;
import com.dcf.iqunxing.message2.service.internal.EmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testGetTimedMsg() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<EmailMessage> result = emailService.getTimedMsg(sf.parse("2015-08-13 14:49:36"), 10000);
        assertEquals(1, result.size());
    }

    @Test
    public void testUpdateSmsStaut() {
        int result = emailService.updateState(12343333L, SendState.SENDING);
        assertEquals(1, result);
    }

    @Test
    public void testGetLeaveMsg() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<EmailMessage> result = emailService.getLeaveMsg(sf.parse("2015-08-13 14:49:36"), 1000);
        assertEquals(1, result.size());
    }
}
