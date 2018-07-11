package com.dcf.iqunxing.message2.service.send.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.service.queue.EmailMessageQueueService;
import com.dcf.iqunxing.message2.service.queue.QueueService;
import com.dcf.iqunxing.message2.service.send.task.EmailTask;
import com.dcf.iqunxing.message2.service.send.task.Task;

/***
 * SendingEmailExecutors 发送邮件的Executors
 * 
 * @author yxj
 **/
@Service
public class SendingEmailExecutors extends SendingExecutor {

    @Autowired
    @Qualifier("emailTaskExecutor")
    private ThreadPoolTaskExecutor emailTaskExecutor;

    @Autowired
    private EmailMessageQueueService queueService;

    @Override
    protected ThreadPoolTaskExecutor initThreadPool() {
        return emailTaskExecutor;
    }

    @Override
    protected QueueService<?> initQueueService() {
        return queueService;
    }

    @Override
    protected Task initTask() {
        return new EmailTask();
    }

    @Override
    protected String initThreadExecutorName() {
        return "Email Thread Executor";
    }

}
