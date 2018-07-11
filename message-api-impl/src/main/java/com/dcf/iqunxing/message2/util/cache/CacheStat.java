package com.dcf.iqunxing.message2.util.cache;

import java.text.DecimalFormat;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Stat cache
 * 
 * @author zhangjiwei
 */
@Component
class CacheStat {

    private org.slf4j.Logger log = LoggerFactory.getLogger("CacheStat");

    private ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    private static long statStartTime = System.currentTimeMillis();

    @Value("${cache.log.period}")
    private long log_period;

    /*
     * Do not use AtomicLong, we need performance rather than accuracy
     */
    public long putMaxTime;

    public long putTotalTime;

    public long putTimes;

    public long getMaxTime;

    public long getTotalTime;

    public long getTimes;

    public long hitTotalTime;

    public long hitTimes;

    public void putStart() {
        startTime.set(System.currentTimeMillis());
    }

    public void putEnd() {
        long time = System.currentTimeMillis() - startTime.get();
        startTime.remove();
        if (time > putMaxTime) {
            putMaxTime = time;
        }
        putTotalTime += time;
        putTimes++;
        log();
    }

    public void getStart() {
        startTime.set(System.currentTimeMillis());
    }

    public void getHit() {
        long time = System.currentTimeMillis() - startTime.get();
        hitTimes++;
        hitTotalTime += time;
    }

    public void getEnd() {
        long time = System.currentTimeMillis() - startTime.get();
        startTime.remove();
        if (time > getMaxTime) {
            getMaxTime = time;
        }
        getTotalTime += time;
        getTimes++;
        log();
    }

    public void log() {
        // one day
        if (System.currentTimeMillis() - statStartTime >= log_period) {
            if (putTimes > 0) {
                log.info("PutMaxTime: {}, PutTotalTime: {}, PutTimes: {}, PutAvgTime: {}", putMaxTime, putTotalTime, putTimes,
                        putTotalTime / putTimes);

                putMaxTime = 0;
                putTotalTime = 0;
                putTimes = 0;
            }

            if (getTimes > 0) {
                log.info(
                        "GetMaxTime: {}, GetTotalTime: {}, GetTimes: {}, GetAvgTime: {}, HitTimes: {}, HitAvgTime: {}, HitRatio: {}",
                        getMaxTime, getTotalTime, getTimes, getTotalTime / getTimes, hitTimes, hitTimes > 0 ? hitTotalTime
                                / hitTimes : 0, new DecimalFormat("0.00%").format(hitTimes * 1.0d / getTimes));

                getMaxTime = 0;
                getTotalTime = 0;
                getTimes = 0;
                hitTotalTime = 0;
                hitTimes = 0;
            }
            statStartTime = System.currentTimeMillis();
        }
    }
}
