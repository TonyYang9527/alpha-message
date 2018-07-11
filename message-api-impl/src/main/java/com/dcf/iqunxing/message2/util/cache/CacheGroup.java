package com.dcf.iqunxing.message2.util.cache;

public enum CacheGroup {

    SHORT_MESSAGE("short-messages"), SYSTEM_PROPERTY("system-properties"), LOGIN_TRY("login-try"), BANK("banks"), CAPTCHA(
            "captchas"), QUERY("queryCache"), SESSION("session"), OUTSIDE("out"), ACCOUNT_POSTING_DUPLICATE_CHECK(
            "account-posting-duplicate-check"), ACCOUNT_POSTING_DUPLICATE_CALLBACK_CHECK("account-posting-duplicate-callback-check"), CHINA_CLEARING_AUTH_PAY_ID(
            "china-clearing-authpay-id"), SMS_TEMPLATE("sms-message-template"), EMAIL_TEMPLATE("email-message-template"), PUSH_TEMPLATE(
            "push-message-template"), SITE_TEMPLATE("site-message-template");

    private String value;

    CacheGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CacheGroup fromValue(String value) {
        for (CacheGroup flag : CacheGroup.values()) {
            if (flag.getValue().equals(value)) {
                return flag;
            }
        }
        return null;
    }
}
