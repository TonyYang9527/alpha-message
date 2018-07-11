package com.dcf.iqunxing.message2.service.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.SmsMessageMapper;
import com.dcf.iqunxing.message2.entity.SmsMessage;
import com.dcf.iqunxing.message2.entity.SmsMessageExample;
import com.dcf.iqunxing.message2.entity.SmsMessageExample.Criteria;
import com.dcf.iqunxing.message2.enums.ImmediateType;
import com.dcf.iqunxing.message2.enums.SendState;

@Service
public class SmsService {

    @Autowired
    protected SmsMessageMapper smsMessageMapper;

    /**
     * 获取定时短信
     * 
     * @param size
     * @return
     */
    public List<SmsMessage> getTimedMsg(Date date, int size) {
        SmsMessageExample example = new SmsMessageExample();
        Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(SendState.TOBESEND.getValue()); // 信息状态为10（待发送）
        criteria.andScheduleTimeLessThanOrEqualTo(date); // 预计发送时间<=当前时间
        criteria.andExpiredTimeGreaterThan(date); // 过期时间>=当前时间
        criteria.andImmediateEqualTo(ImmediateType.SCHEDULE.getValue()); // 信息为定时信息
        example.setLimitStart(0);
        example.setLimitEnd(size); // 一次查处1000条
        List<SmsMessage> result = smsMessageMapper.selectByExample(example);
        // logger.info("短信", JsonMapper.nonEmptyMapper().toJson(example),
        // TagBuilder.create()
        // .append("SmsService getTimedMsg", "param").build());
        // logger.info("短信", JsonMapper.nonEmptyMapper().toJson(result),
        // TagBuilder.create()
        // .append("SmsService getTimedMsg", "result").build());
        // MsgLogger.SMS.info("SmsService  getTimedMsg  ,param{} ,result{}",
        // JsonMapper.nonEmptyMapper().toJson(example), JsonMapper
        // .nonEmptyMapper().toJson(result));
        return result;
    }

    /**
     * 更新短信SMS状态
     * 
     * @param id
     */
    public int updateState(long id, SendState state) {
        SmsMessage msg = new SmsMessage();
        msg.setId(id);
        msg.setState(state.getValue());
        // logger.info("短信", JsonMapper.nonEmptyMapper().toJson(msg),
        // TagBuilder.create().append("SmsService updateState", "param")
        // .build());
        // MsgLogger.SMS.info("SmsService  updateState  ,param{} ",
        // JsonMapper.nonEmptyMapper().toJson(msg));
        return smsMessageMapper.updateByPrimaryKeySelective(msg);
    }

    /**
     * 获取遗留未发送短信
     * 
     * @param size
     * @param size
     * @return
     */
    public List<SmsMessage> getLeaveMsg(Date startdate, int size) {
        SmsMessageExample example = new SmsMessageExample();
        List<Byte> values = new ArrayList<Byte>();
        values.add(SendState.TOBESEND.getValue());
        values.add(SendState.JOB_HANDLED.getValue());// 信息状态为10（待发送）,15(JOB已处理)
        Date leftDate = new Date(startdate.getTime() - 15 * 60 * 1000); // 当前时间-预计发送时间=15分钟
        Criteria criteria = example.createCriteria();
        criteria.andStateIn(values);
        criteria.andScheduleTimeLessThanOrEqualTo(leftDate); // 预计发送时间<=当前时间-15分钟
        criteria.andExpiredTimeGreaterThan(startdate); // 过期时间>=当前时间
        example.setLimitStart(0);
        example.setLimitEnd(size); // 一次查处1000条
        List<SmsMessage> result = smsMessageMapper.selectByExample(example);
        // logger.info("短信", JsonMapper.nonEmptyMapper().toJson(example),
        // TagBuilder.create()
        // .append("SmsService getLeaveMsg", "param").build());
        // logger.info("短信", JsonMapper.nonEmptyMapper().toJson(result),
        // TagBuilder.create()
        // .append("SmsService getLeaveMsg", "result").build());
        // MsgLogger.SMS.info("SmsService  getLeaveMsg  ,param{} ,result{}",
        // JsonMapper.nonEmptyMapper().toJson(example), JsonMapper
        // .nonEmptyMapper().toJson(result));
        return result;
    }
}
