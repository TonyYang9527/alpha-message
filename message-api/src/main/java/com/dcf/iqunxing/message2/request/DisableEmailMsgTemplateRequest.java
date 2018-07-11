package com.dcf.iqunxing.message2.request;

import java.util.Set;


public class DisableEmailMsgTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = -7157465625881051137L;
    
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
