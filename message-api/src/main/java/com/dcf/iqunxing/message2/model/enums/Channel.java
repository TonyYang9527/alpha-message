package com.dcf.iqunxing.message2.model.enums;

public enum Channel {

    // 短信亿美通道
    SMS_EMAY((byte) 1, "亿美短信");

    private byte value;

    private String name;

    Channel(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static Channel fromValue(byte value) {
        for (Channel flag : Channel.values()) {
            if (flag.getValue() == value) {
                return flag;
            }
        }
        return null;
    }
}
