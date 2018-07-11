package com.dcf.iqunxing.message2.request;

import com.dcf.iqunxing.message2.model.enums.Channel;

public class QueryBalanceRequest extends BaseRequest {

    private static final long serialVersionUID = 7343313946933884897L;

    /** 查询通道. */
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
