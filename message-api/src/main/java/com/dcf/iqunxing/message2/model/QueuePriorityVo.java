package com.dcf.iqunxing.message2.model;

import java.io.Serializable;
import java.util.Date;

import com.dcf.iqunxing.message2.model.enums.MsgType;

/**
 * 统一队列对象.
 */
public class QueuePriorityVo implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4433633226283850830L;

    /** The id. */
    private Long id;

    /** 发送时间. */
    private Date scheduleTime;

    /** 优先级. */
    private Byte priority;

    /** msg类型. */
    private MsgType msgType;

    /**
     * 构造方法.
     *
     * @param id
     *            id
     * @param scheduleTime
     *            发送时间
     * @param priority
     *            优先级
     * @param msgType
     *            msg类型
     */
    public QueuePriorityVo(Long id, Date scheduleTime, Byte priority, MsgType msgType) {
        this.id = id;
        this.scheduleTime = scheduleTime;
        this.priority = priority;
        this.msgType = msgType;
    }

    public Long getId() {
        return id;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public Byte getPriority() {
        return priority;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    @Override
    public String toString() {
        return "QueuePriorityVo [id=" + id + ", scheduleTime=" + scheduleTime + ", priority=" + priority + ", msgType=" + msgType
                + "]";
    }

}
