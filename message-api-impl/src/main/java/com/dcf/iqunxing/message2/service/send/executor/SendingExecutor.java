package com.dcf.iqunxing.message2.service.send.executor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.service.queue.QueueService;
import com.dcf.iqunxing.message2.service.send.task.Task;
import com.dcf.iqunxing.message2.util.LogUtils;

public abstract class SendingExecutor implements Runnable {

    private static final String LOG_TITLE = "从队列中取出内容";

    protected static Logger log = LoggerFactory.getLogger(SendingExecutor.class);

    protected ThreadPoolTaskExecutor threadPool;

    protected QueueService<?> queueService;

    protected String threadName;

    private volatile boolean shutdown;

    private static final int THREAD_SLEEP_TIME_IN_MS = 10;

    @Override
    public void run() {
        while (true) {
            QueuePriorityVo queuePriorityVo = null;
            try {
                // 从 Queue 取出优先级最高的对象
                queuePriorityVo = queueService.pollFromQueue();
                if (queuePriorityVo != null) {
                    Task task = initTask();
                    task.setQueuePriorityVo(queuePriorityVo);
                    threadPool.execute(task);
                } else if (shutdown) {
                    // 把队列中所有消息都发送掉，再关闭
                    if (queueService.getSize() == 0) {
                        threadPool.shutdown();
                        return;
                    } else {
                        continue;
                    }
                } else {
                    Thread.sleep(THREAD_SLEEP_TIME_IN_MS);
                }
            } catch (Exception e) {
                TagBuilder builder = TagBuilder.create();
                LogUtils.error(this.getClass(), LOG_TITLE, "从队列中获取对象失败", queuePriorityVo, e, builder);
            }
        }

    }

    @PostConstruct
    public void init() {
        this.threadPool = initThreadPool();
        this.queueService = initQueueService();
        this.threadName = initThreadExecutorName();
        this.shutdown = false;
        log.info("启动{}成功", threadName);
        new Thread(this, threadName).start();
    }

    @PreDestroy
    public synchronized void shutdown() {
        this.shutdown = true;
        log.info("关闭{}成功", threadName);
    }

    /**
     * 初始化线程池.
     *
     * @return the executor service
     */
    protected abstract ThreadPoolTaskExecutor initThreadPool();

    /**
     * 初始化queueService.
     *
     * @return the executor service
     */
    protected abstract QueueService<?> initQueueService();

    /**
     * 初始化task.
     *
     * @return the executor service
     */
    protected abstract Task initTask();

    /**
     * 初始化线程名字.
     *
     * @return the executor service
     */
    protected abstract String initThreadExecutorName();
}
