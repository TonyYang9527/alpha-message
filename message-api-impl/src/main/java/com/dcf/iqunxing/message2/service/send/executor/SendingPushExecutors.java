package com.dcf.iqunxing.message2.service.send.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.service.queue.PushMessageQueueService;
import com.dcf.iqunxing.message2.service.queue.QueueService;
import com.dcf.iqunxing.message2.service.send.task.PushTask;
import com.dcf.iqunxing.message2.service.send.task.Task;

/***
 * SendingPushExecutors 发送push的Executors
 * 
 * @author jingguo
 **/
@Service
public class SendingPushExecutors extends SendingExecutor {

    @Autowired
    @Qualifier("pushTaskExecutor")
    private ThreadPoolTaskExecutor pushTaskExecutor;

    @Autowired
    private PushMessageQueueService queueService;

    @Override
    protected ThreadPoolTaskExecutor initThreadPool() {
        return pushTaskExecutor;
    }

    @Override
    protected QueueService<?> initQueueService() {
        return queueService;
    }

    @Override
    protected Task initTask() {
        return new PushTask();
    }

    @Override
    protected String initThreadExecutorName() {
        return "Push Thread Executor";
    }

}
