package com.dcf.iqunxing.message2.request;

import com.dcf.iqunxing.message2.model.enums.MsgTemplateState;
import com.dcf.iqunxing.message2.model.page.Page;


public class ListMsgTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = 2800687052628220698L;

    protected String name;

    protected MsgTemplateState state;

    protected Page page;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MsgTemplateState getState() {
        return state;
    }

    public void setState(MsgTemplateState state) {
        this.state = state;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
