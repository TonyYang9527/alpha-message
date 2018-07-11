package com.dcf.iqunxing.message2.enums;

public enum DeleteFlag {

    // 未删除
    SURVIVOR((byte) 0),

    // 删除
    VICTIM((byte) 1);

    private byte value;

    DeleteFlag(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static DeleteFlag fromValue(byte value) {
        for (DeleteFlag flag : DeleteFlag.values()) {
            if (flag.getValue() == value) {
                return flag;
            }
        }
        return null;
    }
}
