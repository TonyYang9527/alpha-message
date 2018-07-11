package com.dcf.iqunxing.message2.service.internal.site;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.SiteMessageContentMapper;
import com.dcf.iqunxing.message2.entity.SiteMessageContent;
import com.dcf.iqunxing.message2.entity.SiteMessageTemplate;
import com.dcf.iqunxing.message2.request.SiteMsgRequest;
import com.dcf.iqunxing.message2.util.MessageAssembleUtil;
import com.google.common.base.Preconditions;

/***
 * SiteMessageContentService 查询站内信内容
 * 
 * @author lijun
 **/
@Service
public class SiteMessageContentService {

    @Autowired
    protected SiteMessageContentMapper siteMessageContentMapper;

    @Autowired
    protected SiteMessageTemplateService siteMessageTemplateService;

    /**
     * 根据id获取站内信内容
     * 
     * @param id
     * @return
     */
    public SiteMessageContent getSiteMessageContent(Long id) {
        Preconditions.checkNotNull(id);
        SiteMessageContent content = siteMessageContentMapper.selectByPrimaryKey(id);
        return content;
    }

    /**
     * 保存站内信内容
     * 
     * @param id
     * @return
     */
    public SiteMessageContent saveSiteContent(SiteMsgRequest request) {
        SiteMessageContent siteMessageContent = new SiteMessageContent();
        if (request.getSiteMessageTemplateId() != null) {
            // 获取站内信模板
            SiteMessageTemplate template = siteMessageTemplateService.getMsgTemplate(request.getSiteMessageTemplateId());
            // 创建站内信内容
            String content = MessageAssembleUtil.assembleContent(template.getContent(), request.getProperties());
            siteMessageContent.setSiteMessageTemplateId(request.getSiteMessageTemplateId());
            siteMessageContent.setTitle(template.getTitle());
            siteMessageContent.setContent(content);
            siteMessageContent.setAddition(request.getAddition());
            siteMessageContent.setType(template.getType());
            siteMessageContent.setCreatedTime(new Date());
            siteMessageContent.setCreatedBy(request.getSender());
        } else {
            siteMessageContent.setTitle(request.getTitle());
            siteMessageContent.setContent(request.getContent());
            siteMessageContent.setAddition(request.getAddition());
            if (request.getType() != null) {
                siteMessageContent.setType(request.getType().getValue());
            }
            siteMessageContent.setCreatedTime(new Date());
            siteMessageContent.setCreatedBy(request.getSender());
        }
        siteMessageContentMapper.insertSelective(siteMessageContent);
        return siteMessageContent;
    }

    /**
     * 更新siteMessageContent
     * 
     * @param siteMessageContent
     */
    public void updateSiteContent(SiteMessageContent siteMessageContent) {
        siteMessageContentMapper.updateByPrimaryKeySelective(siteMessageContent);
    }
}
