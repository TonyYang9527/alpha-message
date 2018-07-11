package com.dcf.iqunxing.message2.service.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.SiteMessageMapper;
import com.dcf.iqunxing.message2.entity.SiteMessage;
import com.dcf.iqunxing.message2.entity.SiteMessageExample;
import com.dcf.iqunxing.message2.entity.SiteMessageExample.Criteria;
import com.dcf.iqunxing.message2.enums.ImmediateType;
import com.dcf.iqunxing.message2.enums.SendState;

@Service
public class SiteMessageService {

    @Autowired
    protected SiteMessageMapper siteMessageMapper;

    /**
     * 获取定时Site
     * 
     * @param size
     * @return
     */
    public List<SiteMessage> getTimedMsg(Date date, int size) {
        SiteMessageExample example = new SiteMessageExample();
        Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(SendState.TOBESEND.getValue()); // 信息状态为10（待发送）
        criteria.andScheduleTimeLessThanOrEqualTo(date); // 预计发送时间<=当前时间
        criteria.andImmediateEqualTo(ImmediateType.SCHEDULE.getValue()); // 信息为定时信息
        example.setLimitStart(0);
        example.setLimitEnd(size); // 一次查处1000条
        List<SiteMessage> result = siteMessageMapper.selectByExample(example);
        return result;
    }

    /**
     * 发送站内信
     * 
     * @param id
     */
    public int send(long id) {
        SiteMessage msg = new SiteMessage();
        msg.setId(id);
        msg.setState(SendState.SENT.getValue());
        msg.setSentTime(new Date());
        return siteMessageMapper.updateByPrimaryKeySelective(msg);
    }

    /**
     * 获取遗留未发送Site
     * 
     * @param size
     * @param size
     * @return
     */
    public List<SiteMessage> getLeaveMsg(Date startdate, int size) {
        SiteMessageExample example = new SiteMessageExample();
        List<Byte> values = new ArrayList<Byte>();
        values.add(SendState.TOBESEND.getValue());
        values.add(SendState.JOB_HANDLED.getValue());// 信息状态为10（待发送）,15(JOB已处理)
        Date leftDate = new Date(startdate.getTime() - 15 * 60 * 1000); // 当前时间-预计发送时间=15分钟
        Criteria criteria = example.createCriteria();
        criteria.andStateIn(values);
        criteria.andScheduleTimeLessThanOrEqualTo(leftDate); // 预计发送时间<=当前时间-15分钟
        example.setLimitStart(0);
        example.setLimitEnd(size); // 一次查处1000条
        List<SiteMessage> result = siteMessageMapper.selectByExample(example);
        return result;
    }
}
