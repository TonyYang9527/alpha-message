package com.dcf.iqunxing.message2.model.enums;

public enum MsgTemplateType {
    // 默认
    DEFAULT((short) 0),
    // 交易
    TRADE((short) 1),
    // 广告
    ADVERT((short) 2),
    // 验证码
    IDENTIFY((short) 3),
    // 公告
    NOTICE((short) 4);

    private short value;

    MsgTemplateType(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    public static MsgTemplateType fromValue(short value) {
        for (MsgTemplateType flag : MsgTemplateType.values()) {
            if (flag.getValue() == value) {
                return flag;
            }
        }
        return null;
    }

}
