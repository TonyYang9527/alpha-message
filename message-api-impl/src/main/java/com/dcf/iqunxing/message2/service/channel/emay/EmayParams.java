package com.dcf.iqunxing.message2.service.channel.emay;

/**
 * 亿美发送参数.
 */
public class EmayParams {

    private String serialNo;

    private String serialPassword;

    private String key;

    private String sendUrl;

    private String sendTimeUrl;

    protected String registerUrl;

    protected String receiveUrl;

    protected String queryBalanceUrl;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSerialPassword() {
        return serialPassword;
    }

    public void setSerialPassword(String serialPassword) {
        this.serialPassword = serialPassword;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public String getSendTimeUrl() {
        return sendTimeUrl;
    }

    public void setSendTimeUrl(String sendTimeUrl) {
        this.sendTimeUrl = sendTimeUrl;
    }

    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    public String getReceiveUrl() {
        return receiveUrl;
    }

    public void setReceiveUrl(String receiveUrl) {
        this.receiveUrl = receiveUrl;
    }

    public String getQueryBalanceUrl() {
        return queryBalanceUrl;
    }

    public void setQueryBalanceUrl(String queryBalanceUrl) {
        this.queryBalanceUrl = queryBalanceUrl;
    }

}
