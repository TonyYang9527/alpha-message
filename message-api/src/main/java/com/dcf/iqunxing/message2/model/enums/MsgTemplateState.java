package com.dcf.iqunxing.message2.model.enums;


public enum MsgTemplateState {

    // 启用的
    ENABLED((byte) 0), 
    // 禁用的
    DISABLED((byte) 1);

    private byte value;

    MsgTemplateState(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static MsgTemplateState fromValue(byte value) {
        for (MsgTemplateState flag : MsgTemplateState.values()) {
            if (flag.getValue() == value) {
                return flag;
            }
        }
        return null;
    }
}
