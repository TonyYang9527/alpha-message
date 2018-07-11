package com.dcf.iqunxing.message2.service.internal.push;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.PushMessagePropertyMapper;
import com.dcf.iqunxing.message2.entity.PushMessageProperty;
import com.dcf.iqunxing.message2.entity.PushMessagePropertyExample;
import com.dcf.iqunxing.message2.entity.PushMessagePropertyExample.Criteria;
import com.google.common.base.Preconditions;

@Service
public class PushMessagePropertyService {

    @Autowired
    private PushMessagePropertyMapper mapper;

    /**
     * 根据PushMessageId获取对应的Key/Value.
     *
     * @param messageId
     *            PushMessageId
     * @return the push props by message id
     */
    public List<PushMessageProperty> getPushPropsByMessageId(Long messageId) {
        Preconditions.checkNotNull(messageId);
        PushMessagePropertyExample example = new PushMessagePropertyExample();
        Criteria criteria = example.createCriteria();
        criteria.andPushMessageIdEqualTo(messageId);
        List<PushMessageProperty> props = mapper.selectByExample(example);
        return props;
    }

    /**
     * 创建push_message_property
     * 
     * @param request
     * @return
     */
    public void savePushMessageProperty(Long messageId, Map<String, String> props) {
        for (Map.Entry<String, String> entry : props.entrySet()) {
            PushMessageProperty pushMessageProperty = new PushMessageProperty();
            pushMessageProperty.setPushMessageId(messageId);
            pushMessageProperty.setPropKey(entry.getKey());
            pushMessageProperty.setPropValue(entry.getValue());
            pushMessageProperty.setCreatedTime(new Date());
            mapper.insertSelective(pushMessageProperty);
        }
    }

}
