package com.dcf.iqunxing.message2.service.internal.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.EmailMessageTemplateMapper;
import com.dcf.iqunxing.message2.entity.EmailMessageTemplate;
import com.dcf.iqunxing.message2.entity.EmailMessageTemplateExample;
import com.dcf.iqunxing.message2.enums.DeleteFlag;
import com.dcf.iqunxing.message2.enums.StateFlag;
import com.dcf.iqunxing.message2.model.EmailMsgTemplateVo;
import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.page.Page;
import com.dcf.iqunxing.message2.model.page.PageResult;
import com.dcf.iqunxing.message2.request.CreateEmailMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DeleteEmailMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DisableEmailMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.EnableEmailMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.UpdateEmailMsgTemplateRequest;
import com.dcf.iqunxing.message2.util.cache.CacheGroup;
import com.dcf.iqunxing.message2.util.cache.CacheService;
import com.dcf.iqunxing.message2.util.mapper.BeanMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/***
 * EmailMessageTemplateService 查询邮件模版
 * 
 * @author yxj
 **/
@Service
public class EmailMessageTemplateService {

    @Autowired
    protected EmailMessageTemplateMapper emailMessageTemplateMapper;

    @Autowired
    private CacheService cacheService;

    /**
     * 根据id获取模板
     * 
     * @param id
     * @return
     */
    public EmailMessageTemplate getMsgTemplate(Long id) {
        Preconditions.checkNotNull(id);
        EmailMessageTemplate emgTmp = null;
        emgTmp = (EmailMessageTemplate) cacheService.getCache(CacheGroup.EMAIL_TEMPLATE, id.toString());
        if (emgTmp == null) {
            emgTmp = emailMessageTemplateMapper.selectByPrimaryKey(id);
            if (emgTmp != null)
                cacheService.putCache(CacheGroup.EMAIL_TEMPLATE, id.toString(), emgTmp);
        }
        return emgTmp;
    }

    /**
     * 保存邮件模板
     * 
     * @param id
     * @return
     */
    public EmailMessageTemplate createEmailMsgTemplate(CreateEmailMsgTemplateRequest request) {

        EmailMessageTemplate emailMessageTemplate = new EmailMessageTemplate();
        emailMessageTemplate.setFromAddress(request.getFromAddress());
        emailMessageTemplate.setSenderName(request.getSenderName());
        emailMessageTemplate.setTitle(request.getTitle());
        emailMessageTemplate.setContent(request.getContent());
        emailMessageTemplate.setCreatedBy(request.getOperator());
        emailMessageTemplate.setCreatedTime(new Date());
        emailMessageTemplate.setName(request.getName());
        emailMessageTemplate.setPriority(request.getPriority().getValue());
        emailMessageTemplate.setType(request.getType());
        emailMessageTemplate.setDeleted(DeleteFlag.SURVIVOR.getValue());
        emailMessageTemplate.setDescription(request.getDescription());
        emailMessageTemplateMapper.insertSelective(emailMessageTemplate);
        cacheService.putCache(CacheGroup.EMAIL_TEMPLATE, emailMessageTemplate.getId().toString(), emailMessageTemplate);
        return emailMessageTemplate;
    }

    /**
     * 删除邮件模板
     * 
     * @param request
     */
    public void deleteEmailMsgTemplate(DeleteEmailMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            EmailMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setDeleted(DeleteFlag.VICTIM.getValue());
            tmp.setDeletedBy(request.getOperator());
            tmp.setDeletedTime(new Date());
            emailMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.EMAIL_TEMPLATE, tmp.getId().toString());
        }
    }

    /**
     * 更新邮件模板
     * 
     * @param request
     */
    public void updateEmailMsgTemplate(UpdateEmailMsgTemplateRequest request) {
        EmailMessageTemplate tmp = this.getMsgTemplate(request.getId());
        if (tmp != null) {
            tmp.setFromAddress(request.getFromAddress());
            tmp.setSenderName(request.getSenderName());
            tmp.setTitle(request.getTitle());
            tmp.setName(request.getName());
            tmp.setContent(request.getContent());
            tmp.setType(request.getType());
            tmp.setPriority(request.getPriority().getValue());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setDescription(request.getDescription());
            tmp.setUpdatedTime(new Date());
            emailMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.putCache(CacheGroup.EMAIL_TEMPLATE, tmp.getId().toString(), tmp);
        }
    }

    /**
     * 禁用邮件模板
     * 
     * @param request
     */
    public void disableEmailMsgTemplate(DisableEmailMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            EmailMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setState(StateFlag.DISABLE.getValue());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setUpdatedTime(new Date());
            emailMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.EMAIL_TEMPLATE, tmp.getId().toString());
        }
    }

    /**
     * 启用邮件模板
     * 
     * @param request
     */
    public void enableEmailMsgTemplate(EnableEmailMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            EmailMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setState(StateFlag.ENABLE.getValue());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setUpdatedTime(new Date());
            emailMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.EMAIL_TEMPLATE, tmp.getId().toString());
        }
    }

    /**
     * 查询邮件模板列表
     * 
     * @param request
     */
    public PageResult<EmailMsgTemplateVo> listEmailMsgTemplate(ListMsgTemplateRequest request) {
        EmailMessageTemplateExample example = new EmailMessageTemplateExample();
        EmailMessageTemplateExample.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(request.getName())) {
            criteria.andNameLike("%" + request.getName() + "%");
        }
        if (request.getState() != null) {
            criteria.andStateEqualTo(request.getState().getValue());
        }
        criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
        example.setOrderByClause(" created_time desc , updated_time  desc ");
        PageResult<EmailMsgTemplateVo> result = new PageResult<EmailMsgTemplateVo>();
        Page page = request.getPage();
        if (page != null) {
            if (page.isNeedTotalRecord()) {
                // 查询相应条件一共有几条
                int count = emailMessageTemplateMapper.countByExample(example);
                page.setTotalRecord(count);
            }

            // 分页计算
            example.setLimitStart(page.getStart());
            example.setLimitEnd(page.getPageSize());
        }

        List<EmailMessageTemplate> emailMessageTemplates = null;
        if (page != null && page.isNeedTotalRecord() && page.getTotalRecord() == 0) {
            emailMessageTemplates = new ArrayList<EmailMessageTemplate>();
        } else {
            emailMessageTemplates = emailMessageTemplateMapper.selectByExample(example);
        }
        List<EmailMsgTemplateVo> emailMsgTemplateVo = new ArrayList<EmailMsgTemplateVo>();
        for (EmailMessageTemplate emailMessageTemplate : emailMessageTemplates) {
            EmailMsgTemplateVo vo = BeanMapper.map(emailMessageTemplate, EmailMsgTemplateVo.class);
            vo.setMsgPriority(MsgPriority.fromValue(emailMessageTemplate.getPriority()));
            emailMsgTemplateVo.add(vo);
        }
        result.setPage(page);
        result.setResult(emailMsgTemplateVo);
        return result;
    }

    /**
     * 验证邮件模板有效性
     * 
     * @param request
     */
    public boolean validatorTemplateValidity(Long templateId) {
        EmailMessageTemplateExample example = new EmailMessageTemplateExample();
        EmailMessageTemplateExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(templateId);
        criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
        criteria.andStateEqualTo(StateFlag.ENABLE.getValue());
        int count = emailMessageTemplateMapper.countByExample(example);
        return count == 1;
    }
}
