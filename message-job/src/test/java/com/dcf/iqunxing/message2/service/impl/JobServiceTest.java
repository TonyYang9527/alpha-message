package com.dcf.iqunxing.message2.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dcf.iqunxing.message2.dao.MessageJobMapper;
import com.dcf.iqunxing.message2.entity.MessageJob;
import com.dcf.iqunxing.message2.service.internal.JobService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class JobServiceTest {
    
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    MessageJobMapper messageJobMapper;

    private static MessageJob mjob;
    
     @BeforeClass
        public static void setUpBeforeClass() throws Exception {       
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
    public void testGetMsgJob() {
        messageJobMapper.insert(mjob);
        MessageJob mjob = jobService.getMsgJob(1l);
        assertEquals(true, mjob.getId().equals(1l));
    }

    @Test
    public void testUpdateMessageJob() {
        jobService.updateMessageJob(mjob);
        MessageJob mjob = jobService.getMsgJob(1l);
        assertEquals(true, mjob.getId().equals(1l));
    }

}
