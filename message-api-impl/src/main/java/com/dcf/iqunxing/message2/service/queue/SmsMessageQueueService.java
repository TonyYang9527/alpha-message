package com.dcf.iqunxing.message2.service.queue;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.entity.SmsMessage;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.model.enums.MsgType;

@Service
public class SmsMessageQueueService extends QueueService<SmsMessage> {

    @Override
    protected int getInitSize() {
        return 100;
    }

    @Override
    protected MsgType getQueueType() {
        return MsgType.SMS;
    }

    @Override
    protected QueuePriorityVo buildQueuePriorityVo(SmsMessage entity) {
        Long id = entity.getId();
        byte priority = entity.getPriority();
        Date scheduleTime = entity.getScheduleTime();
        return new QueuePriorityVo(id, scheduleTime, priority, getQueueType());
    }
}
