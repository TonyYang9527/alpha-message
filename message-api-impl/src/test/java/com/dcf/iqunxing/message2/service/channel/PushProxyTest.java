package com.dcf.iqunxing.message2.service.channel;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dcf.iqunxing.user.entity.enums.DeviceType;
import com.google.common.collect.Maps;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.XingeApp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class PushProxyTest {

    @Autowired
    private PushProxy service;

    private static String IOS_DEVICE_ID;

    private static Byte IOS;

    private static String ANDROID_DEVICE_ID;

    private static Byte ANDROID;

    private static String TITLE;

    private static String CONTENT;

    private static Map<String, Object> CUSTOM;

    @BeforeClass
    public static void setUpBeforeClass() {
        IOS_DEVICE_ID = "118effb46c898d13efe9e1d99a4b514ba6db8a748af0ace3f6d47738f05e42f0";
        IOS = DeviceType.IOS.getValue();
        ANDROID_DEVICE_ID = "269e78b11035ff5b4ea5b0edc0f3fce26afe434d";
        ANDROID = DeviceType.ANDROID.getValue();
        TITLE = "群星金融APP";
        CONTENT = "【群星金融】";
        CUSTOM = Maps.newHashMap();
    }

    @Test
    @Ignore
    public void testPushMessage() {
        boolean result = false;
        result = service.pushMessage(IOS, IOS_DEVICE_ID, TITLE, CONTENT, CUSTOM);
        Assert.assertTrue(result);
        result = service.pushMessage(ANDROID, ANDROID_DEVICE_ID, TITLE, CONTENT, CUSTOM);
        Assert.assertTrue(result);
    }

    @Test
    // @Ignore
    public void testPushIOSMessage() {
        boolean result = service.pushIOSMessage(IOS_DEVICE_ID, CONTENT, CUSTOM);
        Assert.assertTrue(result);
    }

    @Test
    @Ignore
    public void testPushAndroidMessage() {
        boolean result = service.pushAndroidMessage(ANDROID_DEVICE_ID, TITLE, CONTENT, CUSTOM);
        Assert.assertTrue(result);
    }

    @Test
    public void testBuildXingeApp() {
        XingeApp app = service.buildXingeApp(IOS);
        Assert.assertNotNull(app);
    }

    @Test
    public void testBuildAndroidMessage() {
        Message message = service.buildAndroidMessage(TITLE, CONTENT, CUSTOM);
        Assert.assertNotNull(message);
    }

    @Test
    public void testBuildIosMessage() {
        MessageIOS message = service.buildIosMessage(CONTENT, CUSTOM);
        Assert.assertNotNull(message);
    }
}
