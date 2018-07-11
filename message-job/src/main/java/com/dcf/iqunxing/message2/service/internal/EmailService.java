package com.dcf.iqunxing.message2.service.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.EmailMessageMapper;
import com.dcf.iqunxing.message2.entity.EmailMessage;
import com.dcf.iqunxing.message2.entity.EmailMessageExample;
import com.dcf.iqunxing.message2.entity.EmailMessageExample.Criteria;
import com.dcf.iqunxing.message2.enums.ImmediateType;
import com.dcf.iqunxing.message2.enums.SendState;

@Service
public class EmailService {

    @Autowired
    protected EmailMessageMapper emailMessageMapper;

    /**
     * 获取定时邮件
     * 
     * @param size
     * @return
     */
    public List<EmailMessage> getTimedMsg(Date date, int size) {
        EmailMessageExample example = new EmailMessageExample();
        Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(SendState.TOBESEND.getValue()); // 信息状态为10（待发送）
        criteria.andScheduleTimeLessThanOrEqualTo(date); // 预计发送时间<=当前时间
        criteria.andExpiredTimeGreaterThan(date); // 过期时间>当前时间
        criteria.andImmediateEqualTo(ImmediateType.SCHEDULE.getValue()); // 信息为定时信息
        example.setLimitStart(0);
        example.setLimitEnd(size); // 一次查处1000条
        List<EmailMessage> result = emailMessageMapper.selectByExample(example);
        // logger.info("邮件", JsonMapper.nonEmptyMapper().toJson(example),
        // TagBuilder.create().append("EmailService getTimedMsg",
        // "param").build());
        // logger.info("邮件", JsonMapper.nonEmptyMapper().toJson(result),
        // TagBuilder.create().append("EmailService getTimedMsg",
        // "result").build());
        // MsgLogger.EMAIL.info("EmailService  getTimedMsg  ,param:{},result{}",
        // JsonMapper.nonEmptyMapper().toJson(example),
        // JsonMapper.nonEmptyMapper().toJson(result));
        return result;
    }

    /**
     * 更新EMail状态
     * 
     * @param id
     */
    public int updateState(long id, SendState state) {
        EmailMessage msg = new EmailMessage();
        msg.setId(id);
        msg.setState(state.getValue());
        // logger.info("邮件", JsonMapper.nonEmptyMapper().toJson(msg),
        // TagBuilder.create().append("EmailService updateState", "param")
        // .build());
        // MsgLogger.EMAIL.info("EmailService  updateState  ,param:{} ",
        // JsonMapper.nonEmptyMapper().toJson(msg));
        return emailMessageMapper.updateByPrimaryKeySelective(msg);
    }

    /**
     * 获取遗留未发送邮件
     * 
     * @param size
     * @param size
     * @return
     */
    public List<EmailMessage> getLeaveMsg(Date startdate, int size) {
        EmailMessageExample example = new EmailMessageExample();
        List<Byte> values = new ArrayList<Byte>();
        values.add(SendState.TOBESEND.getValue());
        values.add(SendState.JOB_HANDLED.getValue());// 信息状态为10（待发送）,15(JOB已处理)
        Date leftDate = new Date(startdate.getTime() - 15 * 60 * 1000); // 当前时间-预计发送时间=15分钟
        Criteria criteria = example.createCriteria();
        criteria.andStateIn(values);
        criteria.andScheduleTimeLessThanOrEqualTo(leftDate); // 预计发送时间<=当前时间-15分钟
        criteria.andExpiredTimeGreaterThan(startdate); // 过期时间>当前时间
        example.setLimitStart(0);
        example.setLimitEnd(size); // 一次查处1000条
        List<EmailMessage> result = emailMessageMapper.selectByExample(example);
        // logger.info("邮件", JsonMapper.nonEmptyMapper().toJson(example),
        // TagBuilder.create().append("EmailService getLeaveMsg",
        // "param").build());
        // logger.info("邮件", JsonMapper.nonEmptyMapper().toJson(result),
        // TagBuilder.create().append("EmailService getLeaveMsg",
        // "result").build());
        // MsgLogger.EMAIL.info("EmailService  getLeaveMsg  ,param:{},result{} ",
        // JsonMapper.nonEmptyMapper().toJson(example),
        // JsonMapper.nonEmptyMapper().toJson(result));
        return result;
    }
}
