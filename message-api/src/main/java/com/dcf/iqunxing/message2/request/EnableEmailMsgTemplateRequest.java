package com.dcf.iqunxing.message2.request;

import java.util.Set;


public class EnableEmailMsgTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = -6260632149311516062L;
    
    protected Set<Long> ids;

    protected String operator;

    public Set<Long> getIds() {
        return ids;
    }

    public void setIds(Set<Long> ids) {
        this.ids = ids;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
