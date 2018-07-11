package com.dcf.iqunxing.message2.enums;

public enum StateFlag {

    // 启用的
    ENABLE((byte) 0),

    // 禁用的
    DISABLE((byte) 1);

    private byte value;

    StateFlag(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static StateFlag fromValue(byte value) {
        for (StateFlag flag : StateFlag.values()) {
            if (flag.getValue() == value) {
                return flag;
            }
        }
        return null;
    }

}
