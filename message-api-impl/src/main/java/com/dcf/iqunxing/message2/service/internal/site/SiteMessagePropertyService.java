package com.dcf.iqunxing.message2.service.internal.site;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.SiteMessagePropertyMapper;
import com.dcf.iqunxing.message2.entity.SiteMessageProperty;
import com.dcf.iqunxing.message2.entity.SiteMessagePropertyExample;
import com.dcf.iqunxing.message2.entity.SiteMessagePropertyExample.Criteria;
import com.google.common.base.Preconditions;

@Service
public class SiteMessagePropertyService {

    @Autowired
    private SiteMessagePropertyMapper mapper;

    /**
     * 根据SiteMessageId获取对应的Key/Value.
     *
     * @param messageId
     *            SiteMessageId
     * @return the Site props by message id
     */
    public List<SiteMessageProperty> getSitePropsByMessageId(Long messageId) {
        Preconditions.checkNotNull(messageId);
        SiteMessagePropertyExample example = new SiteMessagePropertyExample();
        Criteria criteria = example.createCriteria();
        criteria.andSiteMessageIdEqualTo(messageId);
        List<SiteMessageProperty> props = mapper.selectByExample(example);
        return props;
    }

    /**
     * 创建site_message_property
     * 
     * @param request
     * @return
     */
    public void saveSiteMessageProperty(Long messageId, Map<String, String> props) {
        if (props != null) {
            for (Map.Entry<String, String> entry : props.entrySet()) {
                SiteMessageProperty siteMessageProperty = new SiteMessageProperty();
                siteMessageProperty.setSiteMessageId(messageId);
                siteMessageProperty.setPropKey(entry.getKey());
                siteMessageProperty.setPropValue(entry.getValue());
                siteMessageProperty.setCreatedTime(new Date());
                mapper.insertSelective(siteMessageProperty);
            }

        }
    }
}
