package com.dcf.iqunxing.message2.service.channel.entity.req;

import java.util.Map;

import com.dcf.iqunxing.message2.model.enums.MsgType;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelReq;

public class PushChannelReq extends ChannelReq {

    private static final long serialVersionUID = -4068250539705349479L;

    private final String title;

    private final Integer deviceType;

    private final String deviceId;

    private final Map<String, Object> custom;

    public PushChannelReq(Long id, String title, String content, Integer deviceType, String deviceId, Map<String, Object> custom) {
        super.setId(id);
        super.setContent(content);
        super.setMsgType(MsgType.PUSH);
        this.title = title;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.custom = custom;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Map<String, Object> getCustom() {
        return custom;
    }

}
