package com.dcf.iqunxing.message2.model.page;

import java.io.Serializable;
import java.util.List;

public class PageResult<T>implements Serializable {

    private static final long serialVersionUID = -2374561199162799380L;

    protected Page page;

    protected List<T> result;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PageResult [page=" + page + ", result=" + result + "]";
    }
}
