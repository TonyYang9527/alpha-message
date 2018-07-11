package com.dcf.iqunxing.message2.model.enums;

public enum SmsChannel {

    // 短信亿美通道
    SMS_EMAY_999((byte) 0, "亿美三网合一999通道"), SMS_EMAY_106((byte) 1, "亿美106共享营销短信通道");

    private byte value;

    private String description;

    private SmsChannel(byte value, String description) {
        this.value = value;
        this.description = description;
    }

    public byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static SmsChannel fromValue(byte value) {
        for (SmsChannel flag : SmsChannel.values()) {
            if (flag.getValue() == value) {
                return flag;
            }
        }
        return null;
    }
}
