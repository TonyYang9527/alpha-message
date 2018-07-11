package com.dcf.iqunxing.message2.service.channel.entity.req;

import com.dcf.iqunxing.message2.model.enums.MsgType;
import com.dcf.iqunxing.message2.model.enums.SmsChannel;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelReq;

public class SmsChannelReq extends ChannelReq {

    private static final long serialVersionUID = -7825583888068100421L;

    private final SmsChannel smsChannel;

    /**
     * Instantiates a new sms channel req.
     *
     * @param id
     *            the id
     * @param target
     *            the target
     * @param content
     *            the content
     */
    public SmsChannelReq(Long id, String[] target, String content, short type, SmsChannel smsChannel) {
        super.setId(id);
        super.setContent(content);
        super.setTarget(target);
        super.setMsgType(MsgType.SMS);
        super.setType(type);
        this.smsChannel = smsChannel;
    }

    public SmsChannel getSmsChannel() {
        return smsChannel;
    }

}
