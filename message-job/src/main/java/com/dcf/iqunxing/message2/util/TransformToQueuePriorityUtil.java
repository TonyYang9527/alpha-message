package com.dcf.iqunxing.message2.util;

import com.dcf.iqunxing.message2.entity.EmailMessage;
import com.dcf.iqunxing.message2.entity.PushMessage;
import com.dcf.iqunxing.message2.entity.SiteMessage;
import com.dcf.iqunxing.message2.entity.SmsMessage;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.model.enums.MsgType;

public class TransformToQueuePriorityUtil {

    public static QueuePriorityVo transform(SmsMessage smsMessage) {
        return new QueuePriorityVo(smsMessage.getId(), smsMessage.getScheduleTime(), smsMessage.getPriority(), MsgType.SMS);
    }

    public static QueuePriorityVo transform(EmailMessage emailMessage) {
        return new QueuePriorityVo(emailMessage.getId(), emailMessage.getScheduleTime(), emailMessage.getPriority(), MsgType.EMAIL);
    }

    public static QueuePriorityVo transform(PushMessage pushMessage) {
        return new QueuePriorityVo(pushMessage.getId(), pushMessage.getScheduleTime(), pushMessage.getPriority(), MsgType.PUSH);
    }

    public static QueuePriorityVo transform(SiteMessage siteMessage) {
        return new QueuePriorityVo(siteMessage.getId(), siteMessage.getScheduleTime(), siteMessage.getPriority(), MsgType.SITE_MSG);
    }
}
