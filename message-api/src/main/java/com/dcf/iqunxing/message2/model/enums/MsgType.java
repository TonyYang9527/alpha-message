package com.dcf.iqunxing.message2.model.enums;

public enum MsgType {

    // 短信
    SMS((byte) 1),
    // 邮件
    EMAIL((byte) 2),
    // push推送
    PUSH((byte) 3),
    // messageBox
    SITE_MSG((byte) 4),
    // 亿美通道余额查询
    SMS_EMAY_QRYBALANCE((byte) 5);

    private byte value;

    MsgType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static MsgType fromValue(byte value) {
        for (MsgType flag : MsgType.values()) {
            if (flag.getValue() == value) {
                return flag;
            }
        }
        return null;
    }
}
