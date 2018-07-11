package com.dcf.iqunxing.message2.service.internal.email;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.EmailMessagePropertyMapper;
import com.dcf.iqunxing.message2.entity.EmailMessageProperty;
import com.dcf.iqunxing.message2.entity.EmailMessagePropertyExample;
import com.dcf.iqunxing.message2.entity.EmailMessagePropertyExample.Criteria;
import com.google.common.base.Preconditions;

@Service
public class EmailMessagePropertyService {

    @Autowired
    private EmailMessagePropertyMapper mapper;

    /**
     * 根据EmailMessageId获取对应的Key/Value.
     *
     * @param messageId
     *            EmailMessageId
     * @return the email props by message id
     */
    public List<EmailMessageProperty> getEmailPropsByMessageId(Long messageId) {
        Preconditions.checkNotNull(messageId);
        EmailMessagePropertyExample example = new EmailMessagePropertyExample();
        Criteria criteria = example.createCriteria();
        criteria.andEmailMessageIdEqualTo(messageId);
        List<EmailMessageProperty> props = mapper.selectByExample(example);
        return props;
    }

    /**
     * 创建email_message_property
     * 
     * @param request
     * @return
     */
    public void saveEmailMessageProperty(Long messageId, Map<String, String> props) {
        for (Map.Entry<String, String> entry : props.entrySet()) {
            EmailMessageProperty emailMessageProperty = new EmailMessageProperty();
            emailMessageProperty.setEmailMessageId(messageId);
            emailMessageProperty.setPropKey(entry.getKey());
            emailMessageProperty.setPropValue(entry.getValue());
            emailMessageProperty.setCreatedTime(new Date());
            mapper.insertSelective(emailMessageProperty);
        }
    }

}
