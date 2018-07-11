package com.dcf.iqunxing.message2.service.channel;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dcf.iqunxing.message2.model.enums.MsgType;
import com.dcf.iqunxing.message2.model.enums.SmsChannel;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelReq;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelResp;
import com.dcf.iqunxing.message2.service.channel.entity.req.EmailChannelReq;
import com.dcf.iqunxing.message2.service.channel.entity.req.PushChannelReq;
import com.dcf.iqunxing.message2.service.channel.entity.req.SmsChannelReq;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class ChannelServiceFacadeTest {

    @Autowired
    private ChannelServiceFacade service;

    private static SmsChannelReq SMS_REQ;

    private static EmailChannelReq EMAIL_REQ;

    private static PushChannelReq PUSH_REQ;

    private static ChannelReq SMS_BALANCE_QUERY_REQ;

    @BeforeClass
    public static void setUpBeforeClass() {
        SMS_REQ = new SmsChannelReq(1L, new String[] {
                "13661992232"
        }, "群星短信 ", (short) 1, SmsChannel.SMS_EMAY_999);
        EMAIL_REQ = new EmailChannelReq(2L, new String[] {
                "ao.wang@dcfservice.com"
        }, "群星邮件", "service@iqunxing.com", "群星tilte", "群星", true, null, null, null);
        SMS_BALANCE_QUERY_REQ = new ChannelReq();
        SMS_BALANCE_QUERY_REQ.setMsgType(MsgType.SMS_EMAY_QRYBALANCE);
    }

    @Test
    @Ignore
    public void testRequest() {
        ChannelResp resp = null;
        resp = service.request(SMS_REQ);
        Assert.assertTrue(resp.isSuccess());
        resp = service.request(EMAIL_REQ);
        Assert.assertTrue(resp.isSuccess());
        resp = service.request(PUSH_REQ);
        Assert.assertTrue(resp.isSuccess());
        resp = service.request(SMS_BALANCE_QUERY_REQ);
        Assert.assertTrue(resp.isSuccess());
    }

}
