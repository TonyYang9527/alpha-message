package com.dcf.iqunxing.message2.service.send.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.service.queue.QueueService;
import com.dcf.iqunxing.message2.service.queue.SmsMessageQueueService;
import com.dcf.iqunxing.message2.service.send.task.SmsTask;
import com.dcf.iqunxing.message2.service.send.task.Task;

/***
 * SendingSmsExecutors 发送短信的Executors
 * 
 * @author yxj
 **/
@Service
public class SendingSmsExecutors extends SendingExecutor {

    @Autowired
    @Qualifier("smsTaskExecutor")
    private ThreadPoolTaskExecutor smsTaskExecutor;

    @Autowired
    private SmsMessageQueueService queueService;

    @Override
    protected ThreadPoolTaskExecutor initThreadPool() {
        return smsTaskExecutor;
    }

    @Override
    protected QueueService<?> initQueueService() {
        return queueService;
    }

    @Override
    protected Task initTask() {
        return new SmsTask();
    }

    @Override
    protected String initThreadExecutorName() {
        return "Sms Thread Executor";
    }

}
