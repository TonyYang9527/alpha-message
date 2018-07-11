package com.dcf.iqunxing.message2.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dcf.iqunxing.message2.dao.MessageJobMapper;
import com.dcf.iqunxing.message2.dao.SmsMessageMapper;
import com.dcf.iqunxing.message2.entity.MessageJob;
import com.dcf.iqunxing.message2.entity.SmsMessage;
import com.dcf.iqunxing.message2.enums.SendState;
import com.dcf.iqunxing.message2.service.internal.SmsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class SmsServiceTest {
    
    @Autowired
    private SmsService smsService;
    
    @Autowired
    SmsMessageMapper smsMessageMapper;
    
    @Autowired
    MessageJobMapper messageJobMapper;
	
	 private static SmsMessage smsg;
	 private static MessageJob mjob;
	
	 @BeforeClass
	    public static void setUpBeforeClass() throws Exception {
		 smsg = new SmsMessage();
		 smsg.setCreatedBy("U132873234");
		 smsg.setCreatedTime(new Date());
		 smsg.setDatachangeLasttime(new Date());
		 smsg.setExpiredTime(new Date());
		 smsg.setId(1l);
		 smsg.setImmediate((byte)1);
		 smsg.setMobiles("1226352");
		 smsg.setPriority((byte)1);
		 smsg.setScheduleTime(new Date());
		 smsg.setSentResult("success");
		 smsg.setSentTime(new Date());
		 smsg.setSmsMessageTemplateId(1234l);
		 smsg.setState((byte)1);
		 
		 mjob = new MessageJob(); 
		 mjob.setCreatedTime(new Date());
		 mjob.setDatachangeLasttime(new Date());
		 mjob.setId(1l);
		 mjob.setLastEndTime(new Date());
		 mjob.setLastScheduleTime(new Date());
		 mjob.setLastStartTime(new Date());
		 mjob.setUpdatedTime(new Date());	 
	    }
	

	@Test
	public void testGetTimedMsg() {
	    int rrr = smsMessageMapper.insert(smsg);
	    List<SmsMessage> result = smsService.getTimedMsg(new Date(), 10000);
	    assertEquals(1, result.size());
	}

	@Test
	public void testUpdateSmsStaut() {
	    smsService.updateState(smsg.getId(), SendState.SENDING);
	    SmsMessage smsg1 =smsMessageMapper.selectByPrimaryKey(smsg.getId());
	    assertEquals(true, smsg1.getId().equals(1l));
	}

	@Test
	public void testGetLeaveMsg() {
	    smsMessageMapper.insert(smsg);
	    smsService.getLeaveMsg(new Date(), 1000);
	}

}
