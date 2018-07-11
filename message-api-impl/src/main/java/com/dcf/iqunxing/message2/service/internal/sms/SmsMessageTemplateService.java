package com.dcf.iqunxing.message2.service.internal.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.SmsMessageTemplateMapper;
import com.dcf.iqunxing.message2.entity.SmsMessageTemplate;
import com.dcf.iqunxing.message2.entity.SmsMessageTemplateExample;
import com.dcf.iqunxing.message2.enums.DeleteFlag;
import com.dcf.iqunxing.message2.enums.StateFlag;
import com.dcf.iqunxing.message2.model.SmsMsgTemplateVo;
import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.page.Page;
import com.dcf.iqunxing.message2.model.page.PageResult;
import com.dcf.iqunxing.message2.request.CreateSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DeleteSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DisableSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.EnableSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.UpdateSmsMsgTemplateRequest;
import com.dcf.iqunxing.message2.util.cache.CacheGroup;
import com.dcf.iqunxing.message2.util.cache.CacheService;
import com.dcf.iqunxing.message2.util.mapper.BeanMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/***
 * SmsMessageTemplateService 查询短息模版
 * 
 * @author yxj
 **/
@Service
public class SmsMessageTemplateService {

    @Autowired
    protected SmsMessageTemplateMapper smsMessageTemplateMapper;

    @Autowired
    protected CacheService cacheService;

    /**
     * 根据id获取模板
     * 
     * @param id
     * @return
     */
    public SmsMessageTemplate getMsgTemplate(Long id) {
        Preconditions.checkNotNull(id);
        SmsMessageTemplate smgTmp = null;
        smgTmp = (SmsMessageTemplate) cacheService.getCache(CacheGroup.SMS_TEMPLATE, id.toString());
        if (smgTmp == null) {
            smgTmp = smsMessageTemplateMapper.selectByPrimaryKey(id);
            if (smgTmp != null)
                cacheService.putCache(CacheGroup.SMS_TEMPLATE, id.toString(), smgTmp);
        }
        return smgTmp;
    }

    /**
     * 保存短信模板
     * 
     * @param id
     * @return
     */
    public SmsMessageTemplate createMsgTemplate(CreateSmsMsgTemplateRequest request) {
        SmsMessageTemplate smsMsgTemplate = new SmsMessageTemplate();
        smsMsgTemplate.setContent(request.getContent());
        smsMsgTemplate.setCreatedBy(request.getOperator());
        smsMsgTemplate.setCreatedTime(new Date());
        smsMsgTemplate.setName(request.getName());
        smsMsgTemplate.setPriority(request.getPriority().getValue());
        smsMsgTemplate.setType(request.getType());
        smsMsgTemplate.setDeleted(DeleteFlag.SURVIVOR.getValue());
        smsMsgTemplate.setChannel(request.getSmsChannel().getValue());
        smsMsgTemplate.setDescription(request.getDescription());
        smsMessageTemplateMapper.insertSelective(smsMsgTemplate);
        cacheService.putCache(CacheGroup.SMS_TEMPLATE, smsMsgTemplate.getId().toString(), smsMsgTemplate);
        return smsMsgTemplate;
    }

    /**
     * 删除短信模板
     * 
     * @param request
     */
    public void deleteSmsMsgTemplate(DeleteSmsMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            SmsMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setDeleted(DeleteFlag.VICTIM.getValue());
            tmp.setDeletedBy(request.getOperator());
            tmp.setDeletedTime(new Date());
            smsMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.SMS_TEMPLATE, tmp.getId().toString());
        }
    }

    /**
     * 更新短信模板
     * 
     * @param request
     */
    public void updateSmsMsgTemplate(UpdateSmsMsgTemplateRequest request) {
        SmsMessageTemplate tmp = this.getMsgTemplate(request.getId());
        if (tmp != null) {
            tmp.setName(request.getName());
            tmp.setContent(request.getContent());
            tmp.setType(request.getType());
            tmp.setPriority(request.getPriority().getValue());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setUpdatedTime(new Date());
            tmp.setChannel(request.getSmsChannel().getValue());
            tmp.setDescription(request.getDescription());
            smsMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.putCache(CacheGroup.SMS_TEMPLATE, tmp.getId().toString(), tmp);
        }
    }

    /**
     * 禁用短信模板
     * 
     * @param request
     */
    public void disableSmsMsgTemplate(DisableSmsMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            SmsMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setState(StateFlag.DISABLE.getValue());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setUpdatedTime(new Date());
            smsMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.SMS_TEMPLATE, tmp.getId().toString());
        }
    }

    /**
     * 启用短信模板
     * 
     * @param request
     */
    public void enableableSmsMsgTemplate(EnableSmsMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            SmsMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setState(StateFlag.ENABLE.getValue());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setUpdatedTime(new Date());
            smsMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.SMS_TEMPLATE, tmp.getId().toString());
        }
    }

    /**
     * 查询短信模板列表
     * 
     * @param request
     */
    public PageResult<SmsMsgTemplateVo> listSmsMsgTemplate(ListMsgTemplateRequest request) {
        SmsMessageTemplateExample example = new SmsMessageTemplateExample();
        SmsMessageTemplateExample.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(request.getName())) {
            criteria.andNameLike("%" + request.getName() + "%");
        }
        if (request.getState() != null) {
            criteria.andStateEqualTo(request.getState().getValue());
        }
        criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
        example.setOrderByClause(" created_time desc , updated_time  desc ");
        PageResult<SmsMsgTemplateVo> result = new PageResult<SmsMsgTemplateVo>();
        Page page = request.getPage();
        if (page != null) {
            if (page.isNeedTotalRecord()) {
                // 查询相应条件一共有几条
                int count = smsMessageTemplateMapper.countByExample(example);
                page.setTotalRecord(count);
            }

            // 分页计算
            example.setLimitStart(page.getStart());
            example.setLimitEnd(page.getPageSize());
        }

        List<SmsMessageTemplate> smsMessageTemplates = null;
        if (page != null && page.isNeedTotalRecord() && page.getTotalRecord() == 0) {
            smsMessageTemplates = new ArrayList<SmsMessageTemplate>();
        } else {
            smsMessageTemplates = smsMessageTemplateMapper.selectByExample(example);
        }
        List<SmsMsgTemplateVo> smsMsgTemplateVo = new ArrayList<SmsMsgTemplateVo>();
        for (SmsMessageTemplate smsMessageTemplate : smsMessageTemplates) {
            SmsMsgTemplateVo vo = BeanMapper.map(smsMessageTemplate, SmsMsgTemplateVo.class);
            vo.setMsgPriority(MsgPriority.fromValue(smsMessageTemplate.getPriority()));
            smsMsgTemplateVo.add(vo);
        }
        result.setPage(page);
        result.setResult(smsMsgTemplateVo);
        return result;
    }

    /**
     * 验证短信模版板有效性
     * 
     * @param request
     */
    public boolean validatorTemplateValidity(Long templateId) {
        SmsMessageTemplateExample example = new SmsMessageTemplateExample();
        SmsMessageTemplateExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(templateId);
        criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
        criteria.andStateEqualTo(StateFlag.ENABLE.getValue());
        int count = smsMessageTemplateMapper.countByExample(example);
        return count == 1;
    }
}
