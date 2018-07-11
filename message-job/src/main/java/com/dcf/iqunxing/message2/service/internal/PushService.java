package com.dcf.iqunxing.message2.service.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.PushMessageMapper;
import com.dcf.iqunxing.message2.entity.PushMessage;
import com.dcf.iqunxing.message2.entity.PushMessageExample;
import com.dcf.iqunxing.message2.entity.PushMessageExample.Criteria;
import com.dcf.iqunxing.message2.enums.ImmediateType;
import com.dcf.iqunxing.message2.enums.SendState;

@Service
public class PushService {

    @Autowired
    protected PushMessageMapper pushMessageMapper;

    /**
     * 获取定时push
     * 
     * @param size
     * @return
     */
    public List<PushMessage> getTimedMsg(Date date, int size) {
        PushMessageExample example = new PushMessageExample();
        Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(SendState.TOBESEND.getValue()); // 信息状态为10（待发送）
        criteria.andScheduleTimeLessThanOrEqualTo(date); // 预计发送时间<=当前时间
        criteria.andExpireTimeGreaterThan(date); // 过期时间>=当前时间
        criteria.andImmediateEqualTo(ImmediateType.SCHEDULE.getValue()); // 信息为定时信息
        example.setLimitStart(0);
        example.setLimitEnd(size); // 一次查处1000条
        List<PushMessage> result = pushMessageMapper.selectByExample(example);
        return result;
    }

    /**
     * 更新PUSH状态
     * 
     * @param id
     */
    public int updateState(long id, SendState state) {
        PushMessage msg = new PushMessage();
        msg.setId(id);
        msg.setState(state.getValue());
        return pushMessageMapper.updateByPrimaryKeySelective(msg);
    }

    /**
     * 获取遗留未发送push
     * 
     * @param size
     * @param size
     * @return
     */
    public List<PushMessage> getLeaveMsg(Date startdate, int size) {
        PushMessageExample example = new PushMessageExample();
        List<Byte> values = new ArrayList<Byte>();
        values.add(SendState.TOBESEND.getValue());
        values.add(SendState.JOB_HANDLED.getValue());// 信息状态为10（待发送）,15(JOB已处理)
        Date leftDate = new Date(startdate.getTime() - 15 * 60 * 1000); // 当前时间-预计发送时间=15分钟
        Criteria criteria = example.createCriteria();
        criteria.andStateIn(values);
        criteria.andScheduleTimeLessThanOrEqualTo(leftDate); // 预计发送时间<=当前时间-15分钟
        criteria.andExpireTimeGreaterThan(startdate); // 过期时间>=当前时间
        example.setLimitStart(0);
        example.setLimitEnd(size); // 一次查处1000条
        List<PushMessage> result = pushMessageMapper.selectByExample(example);
        return result;
    }
}
