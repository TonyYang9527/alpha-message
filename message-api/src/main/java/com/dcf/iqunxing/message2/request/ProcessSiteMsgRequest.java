package com.dcf.iqunxing.message2.request;

import java.util.Set;


public class ProcessSiteMsgRequest extends BaseRequest {

    private static final long serialVersionUID = 2089353069474058647L;

    protected Set<Long> ids;

    protected String receiver;

    protected Set<Short> types;

    public Set<Long> getIds() {
        return ids;
    }

    public void setIds(Set<Long> ids) {
        this.ids = ids;
    }

    public String getReceiver() {
        return receiver;
    }

    public Set<Short> getTypes() {
        return types;
    }

    public void setTypes(Set<Short> types) {
        this.types = types;
    }

    /**
     * 将此人的所有未读消息置为已处理
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
