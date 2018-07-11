package com.dcf.iqunxing.message2.request;

import java.util.HashSet;
import java.util.Set;

import com.dcf.iqunxing.message2.model.enums.MsgState;
import com.dcf.iqunxing.message2.model.enums.SiteMsgType;
import com.dcf.iqunxing.message2.model.page.Page;

public class ListSiteMsgRequest extends BaseRequest {

    private static final long serialVersionUID = -4114602476742186093L;

    protected Set<MsgState> states = new HashSet<>();

    protected Set<SiteMsgType> types = new HashSet<>();
    
    protected Set<Short> newTypes = new HashSet<>();

    protected Long startUtcTime;

    protected Long endUtcTime;

    protected String receiver;

    protected boolean needAddition = false;

    protected Page page = new Page();

    public Set<MsgState> getStates() {
        return states;
    }

    public void setStates(Set<MsgState> states) {
        this.states = states;
    }

    public Set<SiteMsgType> getTypes() {
        return types;
    }

    public void setTypes(Set<SiteMsgType> types) {
        this.types = types;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isNeedAddition() {
        return needAddition;
    }

    public void setNeedAddition(boolean needAddition) {
        this.needAddition = needAddition;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Long getStartUtcTime() {
        return startUtcTime;
    }

    /**
     * 查询结果大于等于startUtcTime
     */
    public void setStartUtcTime(Long startUtcTime) {
        this.startUtcTime = startUtcTime;
    }

    public Long getEndUtcTime() {
        return endUtcTime;
    }

    /**
     * 查询结果小于endUtcTime
     */
    public void setEndUtcTime(Long endUtcTime) {
        this.endUtcTime = endUtcTime;
    }

    
    public Set<Short> getNewTypes() {
        return newTypes;
    }

    
    public void setNewTypes(Set<Short> newTypes) {
        this.newTypes = newTypes;
    }
}
