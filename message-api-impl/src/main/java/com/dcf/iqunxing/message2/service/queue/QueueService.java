package com.dcf.iqunxing.message2.service.queue;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.model.enums.MsgType;
import com.dcf.iqunxing.message2.service.queue.comparator.QueueComparator;

public abstract class QueueService<T> {

    private static final Logger log = LoggerFactory.getLogger(QueueService.class);

    protected BlockingQueue<QueuePriorityVo> priorityQueue = new PriorityBlockingQueue<QueuePriorityVo>(getInitSize(),
            getComparator());

    // 防止有重复的消息在队列中
    protected Set<Long> ids = new HashSet<Long>();

    protected abstract int getInitSize();

    protected abstract MsgType getQueueType();

    protected abstract QueuePriorityVo buildQueuePriorityVo(T entity);

    protected volatile boolean shutdown = false;

    /**
     * 返回队列大小.
     *
     * @return the size
     */
    public int getSize() {
        return priorityQueue.size();
    }

    /**
     * Queue 添加对象
     */
    public synchronized boolean offerToQueue(QueuePriorityVo vo) {
        if (shutdown) {
            log.warn(getQueueType() + " queue has been shutdown!, id:{}", vo.getId());
            return false;
        }
        if (ids.contains(vo.getId())) {
            return true;
        }
        if (priorityQueue.offer(vo)) {
            ids.add(vo.getId());
            return true;
        }

        return false;
    }

    /**
     * Queue 添加对象
     */
    public synchronized boolean offerToQueue(T entity) {
        return offerToQueue(buildQueuePriorityVo(entity));
    }

    /**
     * 从 Queue 取出优先级最高的对象
     * 
     * @return
     */
    public synchronized QueuePriorityVo pollFromQueue() {
        QueuePriorityVo vo = priorityQueue.poll();
        if (vo != null) {
            ids.remove(vo.getId());
        }
        return vo;
    }

    @PreDestroy
    public synchronized void shutdown() {
        shutdown = true;
    }

    protected Comparator<QueuePriorityVo> getComparator() {
        return new QueueComparator();
    }

}
