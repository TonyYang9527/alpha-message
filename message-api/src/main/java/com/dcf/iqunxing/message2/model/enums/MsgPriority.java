package com.dcf.iqunxing.message2.model.enums;

public enum MsgPriority {

    // 高
    HIGH((byte) 20),
    // 中
    NORMAL((byte) 40),
    // 低
    LOW((byte) 60);

    private byte value;

    MsgPriority(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static MsgPriority fromValue(byte value) {
        for (MsgPriority flag : MsgPriority.values()) {
            if (flag.getValue() == value) {
                return flag;
            }
        }
        return null;
    }
}
