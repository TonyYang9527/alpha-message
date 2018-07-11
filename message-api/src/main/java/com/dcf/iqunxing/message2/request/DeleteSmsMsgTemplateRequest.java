package com.dcf.iqunxing.message2.request;

import java.util.Set;

public class DeleteSmsMsgTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = -4911227714124626952L;

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
