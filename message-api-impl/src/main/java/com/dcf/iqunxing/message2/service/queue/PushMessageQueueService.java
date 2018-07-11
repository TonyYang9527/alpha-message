package com.dcf.iqunxing.message2.service.queue;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.entity.PushMessage;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.model.enums.MsgType;

@Service
public class PushMessageQueueService extends QueueService<PushMessage> {

    @Override
    protected int getInitSize() {
        return 100;
    }

    @Override
    protected MsgType getQueueType() {
        return MsgType.PUSH;
    }

    @Override
    protected QueuePriorityVo buildQueuePriorityVo(PushMessage entity) {
        Long id = entity.getId();
        byte priority = entity.getPriority();
        Date scheduleTime = entity.getScheduleTime();
        return new QueuePriorityVo(id, scheduleTime, priority, getQueueType());
    }
}
