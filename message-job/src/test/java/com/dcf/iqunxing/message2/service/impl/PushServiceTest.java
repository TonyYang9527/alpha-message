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
import com.dcf.iqunxing.message2.dao.PushMessageMapper;
import com.dcf.iqunxing.message2.entity.MessageJob;
import com.dcf.iqunxing.message2.entity.PushMessage;
import com.dcf.iqunxing.message2.enums.SendState;
import com.dcf.iqunxing.message2.service.internal.PushService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class PushServiceTest {

    @Autowired
    private PushService pushService;

    @Autowired
    PushMessageMapper pushMessageMapper;

    @Autowired
    MessageJobMapper messageJobMapper;

    private static PushMessage msg;

    private static MessageJob mjob;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        msg = new PushMessage();
        msg.setCreatedBy("U120140707115754787");
        msg.setCreatedTime(new Date());
        msg.setDatachangeLasttime(new Date());
        msg.setExpireTime(new Date(System.currentTimeMillis() + (1000 * 1000L)));
        msg.setId(1l);
        msg.setImmediate((byte) 1);
        msg.setReceiverId("U120140707115754787");
        msg.setDeviceId("7c72a3b9611f8e4e4d9c9a1936edb012d2e6d2ae");
        msg.setDeviceType((byte) 1);
        msg.setPriority((byte) 1);
        msg.setScheduleTime(new Date());
        msg.setSentResult("success");
        msg.setSentTime(new Date());
        msg.setPushMessageTemplateId(1234l);
        msg.setState((byte) 10);

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
        pushMessageMapper.insert(msg);
        List<PushMessage> result = pushService.getTimedMsg(new Date(), 10000);
        assertEquals(1, result.size());
    }

    @Test
    public void testUpdateSmsStaut() {
        pushService.updateState(msg.getId(), SendState.SENDING);
        PushMessage smsg1 = pushMessageMapper.selectByPrimaryKey(msg.getId());
        assertEquals(true, smsg1.getId().equals(1l));
    }

    @Test
    public void testGetLeaveMsg() {
        pushMessageMapper.insert(msg);
        pushService.getLeaveMsg(new Date(), 1000);
    }

}
