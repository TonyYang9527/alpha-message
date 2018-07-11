package com.dcf.iqunxing.message2.service.channel;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.user.entity.enums.DeviceType;
import com.tencent.xinge.ClickAction;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.Style;
import com.tencent.xinge.XingeApp;

/**
 * 邮件网关代理
 * 
 * @author yxj
 */
@Service
public class PushProxy {

    private static final String LOG_TITLE = "PUSH网关服务";

    ILog log = LogManager.getLogger(PushProxy.class);

    @Value("${message.expire.second.in.tencent}")
    private int messageExpireSecondInTencent;

    @Value("${android.access.id.in.tencent}")
    private long androidAccessIdInTencent;

    @Value("${android.security.key.in.tencent}")
    private String androidSecurityKeyInTencent;

    @Value("${ios.access.id.in.tencent}")
    private long iosAccessIdInTencent;

    @Value("${ios.security.key.in.tencent}")
    private String iosSecurityKeyInTencent;

    /**
     * Push消息推送.
     * 
     * @param deviceType
     * @param deviceId
     * @param title
     * @param title
     */
    public boolean pushMessage(int deviceType, String deviceId, String title, String content, Map<String, Object> custom) {
        boolean isSuccess = false;
        if (deviceType == DeviceType.ANDROID.getValue()) {
            isSuccess = pushAndroidMessage(deviceId, title, content, custom);
        } else if (deviceType == DeviceType.IOS.getValue()) {
            isSuccess = pushIOSMessage(deviceId, content, custom);
        }
        return isSuccess;
    }

    /**
     * Push IOS 消息推送.
     * 
     * @param deviceType
     * @param deviceId
     * @param title
     * @param title
     */
    public boolean pushIOSMessage(String deviceId, String content, Map<String, Object> custom) {
        XingeApp xinge = buildXingeApp(DeviceType.IOS.getValue());
        if (xinge == null)
            return false;
        MessageIOS message = buildIosMessage(content, custom);
        JSONObject ret = xinge.pushSingleDevice(deviceId, message, XingeApp.IOSENV_PROD);
        int retCode = ret.getInt("ret_code");
        LogUtils.info(this.getClass(), LOG_TITLE, "PUSH网关发送请求返回值", ret, TagBuilder.create().append("deviceId", deviceId));
        if (retCode == 0) {
            return true;
        }
        return false;
    }

    /**
     * Push Android 消息推送.
     * 
     * @param deviceType
     * @param deviceId
     * @param title
     * @param title
     */
    public boolean pushAndroidMessage(String deviceId, String title, String content, Map<String, Object> custom) {
        XingeApp xinge = buildXingeApp(DeviceType.ANDROID.getValue());
        if (xinge == null)
            return false;
        Message message = buildAndroidMessage(title, content, custom);
        JSONObject ret = xinge.pushSingleDevice(deviceId, message);
        int retCode = ret.getInt("ret_code");
        if (retCode == 0) {
            return true;
        }
        return false;
    }

    public XingeApp buildXingeApp(int deviceType) {
        if (DeviceType.ANDROID.getValue() == deviceType) {
            return new XingeApp(androidAccessIdInTencent, androidSecurityKeyInTencent);
        } else if (DeviceType.IOS.getValue() == deviceType) {
            return new XingeApp(iosAccessIdInTencent, iosSecurityKeyInTencent);
        }
        return null;
    }

    /**
     * Build 腾讯信鸽Android消息
     *
     * @param qxmessage
     * @return
     * @author zhangjiwei 2014年10月31日
     */
    protected Message buildAndroidMessage(String title, String content, Map<String, Object> custom) {
        Message message = new Message();
        message.setTitle(title);
        message.setContent(content);
        message.setType(Message.TYPE_NOTIFICATION);
        message.setCustom(custom);
        Style style = new Style(1);
        message.setStyle(style);
        ClickAction clickAction = new ClickAction();
        message.setAction(clickAction);
        // // 消息定时推送时间，格式为year-mon-day
        // hour:min:sec，若小于服务器当前时间则立即推送。选填，默认为空字符串，代表立即推送
        // message.setSendTime("");
        // TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
        // message.addAcceptTime(acceptTime1);
        // 消息离线存储多久，单位为秒，最长存储时间3天。选填，默认为0，即不存储
        message.setExpireTime(messageExpireSecondInTencent);

        return message;
    }

    /**
     * Build 腾讯信鸽IOS消息
     *
     * @param qxmessage
     * @return
     * @author zhangjiwei 2014年10月31日
     */
    protected MessageIOS buildIosMessage(String content, Map<String, Object> custom) {
        MessageIOS message = new MessageIOS();
        message.setAlert(content);
        message.setSound("default");
        message.setBadge(1);
        message.setCustom(custom);
        // // 消息定时推送时间，格式为year-mon-day
        // hour:min:sec，若小于服务器当前时间则立即推送。选填，默认为空字符串，代表立即推送
        // message.setSendTime("");
        // TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
        // message.addAcceptTime(acceptTime1);
        // Map<String, Object> custom = new HashMap<String, Object>();
        // custom.put("c", qxmessage.getContent());
        // message.setCustom(custom);
        // 消息离线存储多久，单位为秒，最长存储时间3天。选填，默认为0，即不存储
        message.setExpireTime(messageExpireSecondInTencent);

        return message;
    }

}
