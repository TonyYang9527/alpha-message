package com.dcf.iqunxing.message2.service.queue;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.entity.EmailMessage;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.model.enums.MsgType;

@Service
public class EmailMessageQueueService extends QueueService<EmailMessage> {

    @Override
    protected int getInitSize() {
        return 100;
    }

    @Override
    protected MsgType getQueueType() {
        return MsgType.EMAIL;
    }

    @Override
    protected QueuePriorityVo buildQueuePriorityVo(EmailMessage entity) {
        Long id = entity.getId();
        byte priority = entity.getPriority();
        Date scheduleTime = entity.getScheduleTime();
        return new QueuePriorityVo(id, scheduleTime, priority, getQueueType());
    }

}
