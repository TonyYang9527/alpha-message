package com.dcf.iqunxing.message2.service.internal.sms;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.SmsMessagePropertyMapper;
import com.dcf.iqunxing.message2.entity.SmsMessageProperty;
import com.dcf.iqunxing.message2.entity.SmsMessagePropertyExample;
import com.dcf.iqunxing.message2.entity.SmsMessagePropertyExample.Criteria;
import com.google.common.base.Preconditions;

@Service
public class SmsMessagePropertyService {

    @Autowired
    private SmsMessagePropertyMapper mapper;

    /**
     * 根据SmsMessageId获取对应的Key/Value.
     *
     * @param messageId
     *            SmsMessageId
     * @return the sms props by message id
     */
    public List<SmsMessageProperty> getSmsPropsByMessageId(Long messageId) {
        Preconditions.checkNotNull(messageId);
        SmsMessagePropertyExample example = new SmsMessagePropertyExample();
        Criteria criteria = example.createCriteria();
        criteria.andSmsMessageIdEqualTo(messageId);
        List<SmsMessageProperty> props = mapper.selectByExample(example);
        return props;
    }

    /**
     * 创建sms_message_property
     * 
     * @param request
     * @return
     */
    public void saveSmsMessageProperty(Long messageId, Map<String, String> props) {
        for (Map.Entry<String, String> entry : props.entrySet()) {
            SmsMessageProperty smsMessageProperty = new SmsMessageProperty();
            smsMessageProperty.setSmsMessageId(messageId);
            smsMessageProperty.setPropKey(entry.getKey());
            smsMessageProperty.setPropValue(entry.getValue());
            smsMessageProperty.setCreatedTime(new Date());
            mapper.insertSelective(smsMessageProperty);
        }
    }

}
