package com.dcf.iqunxing.message2.service.internal.site;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.SiteMessageTemplateMapper;
import com.dcf.iqunxing.message2.entity.SiteMessageTemplate;
import com.dcf.iqunxing.message2.entity.SiteMessageTemplateExample;
import com.dcf.iqunxing.message2.enums.DeleteFlag;
import com.dcf.iqunxing.message2.enums.StateFlag;
import com.dcf.iqunxing.message2.model.SiteMsgTemplateVo;
import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.page.Page;
import com.dcf.iqunxing.message2.model.page.PageResult;
import com.dcf.iqunxing.message2.request.CreateSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DeleteSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DisableSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.EnableSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.UpdateSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.util.cache.CacheGroup;
import com.dcf.iqunxing.message2.util.cache.CacheService;
import com.dcf.iqunxing.message2.util.mapper.BeanMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/***
 * SiteMessageTemplateService 查询站内信模版
 * 
 * @author lijun
 **/
@Service
public class SiteMessageTemplateService {

    @Autowired
    protected SiteMessageTemplateMapper siteMessageTemplateMapper;

    @Autowired
    protected CacheService cacheService;

    /**
     * 根据id获取模板
     * 
     * @param id
     * @return
     */
    public SiteMessageTemplate getMsgTemplate(Long id) {
        Preconditions.checkNotNull(id);
        SiteMessageTemplate siteTmp = null;
        siteTmp = (SiteMessageTemplate) cacheService.getCache(CacheGroup.SITE_TEMPLATE, id.toString());
        if (siteTmp == null) {
            siteTmp = siteMessageTemplateMapper.selectByPrimaryKey(id);
            if (siteTmp != null)
                cacheService.putCache(CacheGroup.SITE_TEMPLATE, id.toString(), siteTmp);
        }
        return siteTmp;
    }

    /**
     * 创建站内信模板
     * 
     * @param request
     * @return
     */
    public SiteMessageTemplate createSiteTemplate(CreateSiteMsgTemplateRequest request) {
        SiteMessageTemplate siteTmp = new SiteMessageTemplate();
        siteTmp.setName(request.getName());
        siteTmp.setTitle(request.getTitle());
        siteTmp.setContent(request.getContent());
        siteTmp.setAddition(request.getAddition());
        siteTmp.setType(request.getType());
        siteTmp.setPriority(request.getPriority().getValue());
        siteTmp.setSender(request.getSender());
        siteTmp.setCreatedBy(request.getOperator());
        siteTmp.setCreatedTime(new Date());
        siteTmp.setDeleted(DeleteFlag.SURVIVOR.getValue());
        siteTmp.setDescription(request.getDescription());
        siteMessageTemplateMapper.insertSelective(siteTmp);
        cacheService.putCache(CacheGroup.SITE_TEMPLATE, siteTmp.getId().toString(), siteTmp);
        return siteTmp;
    }

    /**
     * 变更站内信模板内容
     * 
     * @param request
     */
    public void updateSiteMsgTemplate(UpdateSiteMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getId());
        SiteMessageTemplate tmp = this.getMsgTemplate(request.getId());
        if (tmp != null) {
            tmp.setName(request.getName());
            tmp.setTitle(request.getTitle());
            tmp.setContent(request.getContent());
            tmp.setAddition(request.getAddition());
            tmp.setType(request.getType());
            tmp.setPriority(request.getPriority().getValue());
            tmp.setSender(request.getSender());
            tmp.setDescription(request.getDescription());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setUpdatedTime(new Date());
            siteMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.putCache(CacheGroup.SITE_TEMPLATE, tmp.getId().toString(), tmp);
        }
    }

    /**
     * 删除站内信模板
     * 
     * @param request
     */
    public void deleteSiteMsgTemplate(DeleteSiteMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            SiteMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setDeleted(DeleteFlag.VICTIM.getValue());
            tmp.setDeletedBy(request.getOperator());
            tmp.setDeletedTime(new Date());
            siteMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.SITE_TEMPLATE, tmp.getId().toString());
        }
    }

    public PageResult<SiteMsgTemplateVo> listSiteMsgTemplate(ListMsgTemplateRequest request) {
        SiteMessageTemplateExample example = new SiteMessageTemplateExample();
        SiteMessageTemplateExample.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(request.getName())) {
            criteria.andNameLike("%" + request.getName() + "%");
        }
        if (request.getState() != null) {
            criteria.andStateEqualTo(request.getState().getValue());
        }
        criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
        example.setOrderByClause(" created_time desc , updated_time  desc ");
        PageResult<SiteMsgTemplateVo> result = new PageResult<SiteMsgTemplateVo>();
        Page page = request.getPage();
        if (page != null) {
            if (page.isNeedTotalRecord()) {
                // 查询相应条件一共有几条
                int count = siteMessageTemplateMapper.countByExample(example);
                page.setTotalRecord(count);
            }

            // 分页计算
            example.setLimitStart(page.getStart());
            example.setLimitEnd(page.getPageSize());
        }

        List<SiteMessageTemplate> siteMessageTemplates = null;
        if (page != null && page.isNeedTotalRecord() && page.getTotalRecord() == 0) {
            siteMessageTemplates = new ArrayList<SiteMessageTemplate>();
        } else {
            siteMessageTemplates = siteMessageTemplateMapper.selectByExample(example);
        }
        List<SiteMsgTemplateVo> siteMsgTemplateVo = new ArrayList<SiteMsgTemplateVo>();
        for (SiteMessageTemplate SiteMessageTemplate : siteMessageTemplates) {
            SiteMsgTemplateVo vo = BeanMapper.map(SiteMessageTemplate, SiteMsgTemplateVo.class);
            vo.setMsgPriority(MsgPriority.fromValue(SiteMessageTemplate.getPriority()));
            siteMsgTemplateVo.add(vo);
        }
        result.setPage(page);
        result.setResult(siteMsgTemplateVo);
        return result;
    }

    /**
     * 禁用站内信模版
     * 
     * @param request
     */
    public void disableSiteMsgTemplate(DisableSiteMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            SiteMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setState(StateFlag.DISABLE.getValue());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setUpdatedTime(new Date());
            siteMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.SITE_TEMPLATE, tmp.getId().toString());
        }
    }

    /**
     * 启用站内信模版
     * 
     * @param request
     */
    public void enableSiteMsgTemplate(EnableSiteMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            SiteMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setState(StateFlag.ENABLE.getValue());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setUpdatedTime(new Date());
            siteMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.SITE_TEMPLATE, tmp.getId().toString());
        }
    }

    /**
     * 验证站内信模版板有效性
     * 
     * @param request
     */
    public boolean validatorTemplateValidity(Long templateId) {
        SiteMessageTemplateExample example = new SiteMessageTemplateExample();
        SiteMessageTemplateExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(templateId);
        criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
        criteria.andStateEqualTo(StateFlag.ENABLE.getValue());
        int count = siteMessageTemplateMapper.countByExample(example);
        return count == 1;
    }
}
