package com.dcf.iqunxing.message2.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.MessageJobMapper;
import com.dcf.iqunxing.message2.entity.MessageJob;

@Service
public class JobService {

    @Autowired
    protected MessageJobMapper messageJobMapper;

    /**
     * 查询上一次job
     * 
     * @param l
     * @return
     */
    public MessageJob getMsgJob(long id) {
        MessageJob job = messageJobMapper.selectByPrimaryKey(id);
        // logger.info("JOB", Long.toString(id),
        // TagBuilder.create().append("JobService getMsgJob", "param").build());
        // logger.info("JOB", JsonMapper.nonEmptyMapper().toJson(job),
        // TagBuilder.create().append("JobService getMsgJob", "result")
        // .build());
        // MsgLogger.EMAIL.info("JobService  getMsgJob  ,param:{},result{}", id,
        // JsonMapper.nonEmptyMapper().toJson(job));
        return job;
    }

    /**
     * 更新job状态
     */
    public void updateMessageJob(MessageJob messageJob) {
        // logger.info("JOB", JsonMapper.nonEmptyMapper().toJson(messageJob),
        // TagBuilder.create().append("JobService updateMessageJob",
        // "param").build());
        // MsgLogger.EMAIL.info("JobService  getMsgJob  ,param{}",
        // JsonMapper.nonEmptyMapper().toJson(messageJob));
        messageJobMapper.updateByPrimaryKeySelective(messageJob);
    }

}
