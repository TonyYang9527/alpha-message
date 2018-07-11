package com.dcf.iqunxing.message2.service.internal.push;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.PushMessageTemplateMapper;
import com.dcf.iqunxing.message2.entity.PushMessageTemplate;
import com.dcf.iqunxing.message2.entity.PushMessageTemplateExample;
import com.dcf.iqunxing.message2.enums.DeleteFlag;
import com.dcf.iqunxing.message2.enums.StateFlag;
import com.dcf.iqunxing.message2.model.PushMsgTemplateVo;
import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.page.Page;
import com.dcf.iqunxing.message2.model.page.PageResult;
import com.dcf.iqunxing.message2.request.CreatePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DeletePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DisablePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.EnablePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.UpdatePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.util.cache.CacheGroup;
import com.dcf.iqunxing.message2.util.cache.CacheService;
import com.dcf.iqunxing.message2.util.mapper.BeanMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/***
 * PushMessageTemplateService 查询push模版
 * 
 * @author jingguo
 **/
@Service
public class PushMessageTemplateService {

    @Autowired
    protected PushMessageTemplateMapper pushMessageTemplateMapper;

    @Autowired
    protected CacheService cacheService;

    /**
     * 根据id获取模板
     * 
     * @param id
     * @return
     */
    public PushMessageTemplate getMsgTemplate(Long id) {
        Preconditions.checkNotNull(id);
        PushMessageTemplate msgTmp = null;
        msgTmp = (PushMessageTemplate) cacheService.getCache(CacheGroup.PUSH_TEMPLATE, id.toString());
        if (msgTmp == null) {
            msgTmp = pushMessageTemplateMapper.selectByPrimaryKey(id);
            if (msgTmp != null)
                cacheService.putCache(CacheGroup.PUSH_TEMPLATE, id.toString(), msgTmp);
        }
        return msgTmp;
    }

    /**
     * 保存push模板
     * 
     * @param id
     * @return
     */
    public PushMessageTemplate createMsgTemplate(CreatePushMsgTemplateRequest request) {

        PushMessageTemplate pushMessageTemplate = new PushMessageTemplate();
        pushMessageTemplate.setTitle(request.getTitle());
        pushMessageTemplate.setContent(request.getContent());
        pushMessageTemplate.setDescription(request.getDescription());
        pushMessageTemplate.setCreatedBy(request.getOperator());
        pushMessageTemplate.setCreatedTime(new Date());
        pushMessageTemplate.setName(request.getName());
        if (request.getPriority() != null) {
            pushMessageTemplate.setPriority(request.getPriority().getValue());
        }
        pushMessageTemplate.setAddition(request.getAddition());
        pushMessageTemplate.setType(request.getType());
        pushMessageTemplate.setDeleted(DeleteFlag.SURVIVOR.getValue());
        pushMessageTemplateMapper.insertSelective(pushMessageTemplate);
        cacheService.putCache(CacheGroup.PUSH_TEMPLATE, pushMessageTemplate.getId().toString(), pushMessageTemplate);
        return pushMessageTemplate;
    }

    /**
     * 删除push模板
     * 
     * @param request
     */
    public void deletePushMsgTemplate(DeletePushMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            PushMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setDeleted(DeleteFlag.VICTIM.getValue());
            tmp.setDeletedBy(request.getOperator());
            tmp.setDeletedTime(new Date());
            pushMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.PUSH_TEMPLATE, tmp.getId().toString());
        }
    }

    /**
     * 更改push模板
     * 
     * @param request
     */
    public void updatePushMsgTemplate(UpdatePushMsgTemplateRequest request) {
        PushMessageTemplate tmp = this.getMsgTemplate(request.getId());
        if (tmp != null) {
            tmp.setTitle(request.getTitle());
            tmp.setName(request.getName());
            tmp.setAddition(request.getAddition());
            tmp.setContent(request.getContent());
            tmp.setType(request.getType());
            tmp.setPriority(request.getPriority().getValue());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setDescription(request.getDescription());
            tmp.setUpdatedTime(new Date());
            pushMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.putCache(CacheGroup.PUSH_TEMPLATE, tmp.getId().toString(), tmp);
        }
    }

    /**
     * 禁用PUSH模版
     * 
     * @param request
     */
    public void disablePushMsgTemplate(DisablePushMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            PushMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setState(StateFlag.DISABLE.getValue());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setUpdatedTime(new Date());
            pushMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.PUSH_TEMPLATE, tmp.getId().toString());
        }
    }

    /**
     * 启用 PUSH模版
     * 
     * @param request
     */
    public void enablePushMsgTemplate(EnablePushMsgTemplateRequest request) {
        Preconditions.checkNotNull(request.getIds());
        for (long id : request.getIds()) {
            PushMessageTemplate tmp = this.getMsgTemplate(id);
            tmp.setState(StateFlag.ENABLE.getValue());
            tmp.setUpdatedBy(request.getOperator());
            tmp.setUpdatedTime(new Date());
            pushMessageTemplateMapper.updateByPrimaryKeySelective(tmp);
            cacheService.deleteCache(CacheGroup.PUSH_TEMPLATE, tmp.getId().toString());
        }
    }

    /**
     * 查询PUSH模板列表
     * 
     * @param request
     */
    public PageResult<PushMsgTemplateVo> listPushMsgTemplate(ListMsgTemplateRequest request) {
        PushMessageTemplateExample example = new PushMessageTemplateExample();
        PushMessageTemplateExample.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(request.getName())) {
            criteria.andNameLike("%" + request.getName() + "%");
        }
        if (request.getState() != null) {
            criteria.andStateEqualTo(request.getState().getValue());
        }
        criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
        example.setOrderByClause(" created_time desc , updated_time  desc ");
        PageResult<PushMsgTemplateVo> result = new PageResult<PushMsgTemplateVo>();
        Page page = request.getPage();
        if (page != null) {
            if (page.isNeedTotalRecord()) {
                // 查询相应条件一共有几条
                int count = pushMessageTemplateMapper.countByExample(example);
                page.setTotalRecord(count);
            }

            // 分页计算
            example.setLimitStart(page.getStart());
            example.setLimitEnd(page.getPageSize());
        }

        List<PushMessageTemplate> pushMessageTemplates = null;
        if (page != null && page.isNeedTotalRecord() && page.getTotalRecord() == 0) {
            pushMessageTemplates = new ArrayList<PushMessageTemplate>();
        } else {
            pushMessageTemplates = pushMessageTemplateMapper.selectByExample(example);
        }
        List<PushMsgTemplateVo> pushMsgTemplateVo = new ArrayList<PushMsgTemplateVo>();
        for (PushMessageTemplate pushMessageTemplate : pushMessageTemplates) {
            PushMsgTemplateVo vo = BeanMapper.map(pushMessageTemplate, PushMsgTemplateVo.class);
            vo.setMsgPriority(MsgPriority.fromValue(pushMessageTemplate.getPriority()));
            pushMsgTemplateVo.add(vo);
        }
        result.setPage(page);
        result.setResult(pushMsgTemplateVo);
        return result;
    }

    /**
     * 验证PUSH模板有效性
     * 
     * @param request
     */
    public boolean validatorTemplateValidity(Long templateId) {
        PushMessageTemplateExample example = new PushMessageTemplateExample();
        PushMessageTemplateExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(templateId);
        criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
        criteria.andStateEqualTo(StateFlag.ENABLE.getValue());
        int count = pushMessageTemplateMapper.countByExample(example);
        return count == 1;
    }
}
