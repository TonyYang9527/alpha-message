package com.dcf.iqunxing.message2.enums;

public enum ChannelType {

    // 发送
    SEND((byte) 0),

    // 创建
    CREATE((byte) 1);

    private byte value;

    ChannelType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static ChannelType fromValue(byte value) {
        for (ChannelType flag : ChannelType.values()) {
            if (flag.getValue() == value) {
                return flag;
            }
        }
        return null;
    }
}
