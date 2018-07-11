package com.dcf.iqunxing.message2.service.channel;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dcf.iqunxing.message2.service.channel.emay.EmayECProxy;
import com.dcf.iqunxing.message2.service.channel.emay.EmayEMProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class SmsProxyTest {

    @Autowired
    private EmayECProxy ecProxy;

    @Autowired
    private EmayEMProxy emProxy;

    private static Long ID;

    private static String[] MOBILES;

    private static String CONTENT;

    @BeforeClass
    public static void setUpBeforeClass() {
        ID = 1L;
        MOBILES = new String[] {
                "13661992232"
        };
        CONTENT = "您好！群星金融网携手测试链属3开启针对全国经销商的互联网融资服务通道。您的融资额度：10000.00元，签约后可立即使用。您的注册邀请码：874619，下载APP即可完成注册，点击下载链接：http://m.iqunxing.com/d.htm 开始体验吧。如不再接收该消息，回复TD退订。";
    }

    @Test
    public void testCheckResult() {
        boolean isSuccess = ecProxy.checkResult("0", ID, 200);
        Assert.assertTrue(isSuccess);
    }

    @Test
    // @Ignore
    public void testSend() {
        // Object[] result = ecProxy.send(ID, MOBILES, CONTENT, null);
        // boolean isSuccess = (boolean) result[0];
        // Assert.assertTrue(isSuccess);
        Object[] result1 = emProxy.send(ID, MOBILES, CONTENT, null);
        boolean isSuccess1 = (boolean) result1[0];
        Assert.assertTrue(isSuccess1);
    }

    @Test
    public void testReceive() {
        String result = ecProxy.receive();
        System.out.println("接收消息: " + result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testQueryBalance() {
        String balance = ecProxy.queryBalance();
        System.out.println("余额：" + balance);
        Assert.assertNotNull(balance);
    }

}
