package com.dcf.iqunxing.message2.service.queue.comparator;

import java.util.Comparator;
import java.util.Date;

import com.dcf.iqunxing.message2.model.QueuePriorityVo;
import com.dcf.iqunxing.message2.util.time.DateUtils;

public class QueueComparator implements Comparator<QueuePriorityVo> {

    /**
     * 根据 priority 和 schedule_time 排序
     */
    public int compare(QueuePriorityVo s1, QueuePriorityVo s2) {
        Date scheduleTm1 = s1.getScheduleTime();
        Date scheduleTm2 = s2.getScheduleTime();
        Byte priority1 = s1.getPriority();
        Byte priority2 = s2.getPriority();
        int retVal = 0;
        if (priority1.compareTo(priority2) <= -1) {
            retVal = -1;
        } else if (priority1.compareTo(priority2) == 0) {
            /* 比较时间 */
            int retVals = DateUtils.compareDate(scheduleTm1, scheduleTm2);
            if (retVals == 1) {
                retVal = 1;
            } else if (retVals == 0) {
                retVal = 0;
            } else if (retVals == -1) {
                retVal = -1;
            }
        } else if (priority1.compareTo(priority2) >= 1) {
            retVal = 1;
        }
        return retVal;
    }
}
