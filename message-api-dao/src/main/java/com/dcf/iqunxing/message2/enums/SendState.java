package com.dcf.iqunxing.message2.enums;

public enum SendState {
    // 草稿
    DRAFT((byte) 0),
    // 待发送
    TOBESEND((byte) 10),
    // JOB已处理
    JOB_HANDLED((byte) 15),
    // 发送中
    SENDING((byte) 20),
    // 已发送
    SENT((byte) 30),
    // 已阅读
    READ((byte) 40),
    // 发送失败
    FAILED((byte) 99);

    private byte value;

    SendState(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static SendState fromValue(byte value) {
        for (SendState flag : SendState.values()) {
            if (flag.getValue() == value) {
                return flag;
            }
        }
        return null;
    }
}
