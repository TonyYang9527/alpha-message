package com.dcf.iqunxing.message2.vo;

public class SiteMessageVo {

    private String userId;

    private String content;

    public SiteMessageVo() {
    }

    public SiteMessageVo(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

}
