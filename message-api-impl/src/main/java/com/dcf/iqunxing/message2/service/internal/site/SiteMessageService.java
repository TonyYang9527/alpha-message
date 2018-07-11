package com.dcf.iqunxing.message2.service.internal.site;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.dao.SiteMessageMapper;
import com.dcf.iqunxing.message2.entity.SiteMessage;
import com.dcf.iqunxing.message2.entity.SiteMessageContent;
import com.dcf.iqunxing.message2.entity.SiteMessageExample;
import com.dcf.iqunxing.message2.entity.SiteMessageTemplate;
import com.dcf.iqunxing.message2.enums.ChannelType;
import com.dcf.iqunxing.message2.enums.DeleteFlag;
import com.dcf.iqunxing.message2.enums.ImmediateType;
import com.dcf.iqunxing.message2.enums.SendState;
import com.dcf.iqunxing.message2.model.SiteMsgVo;
import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.enums.MsgState;
import com.dcf.iqunxing.message2.model.enums.SiteMsgType;
import com.dcf.iqunxing.message2.model.page.PageResult;
import com.dcf.iqunxing.message2.request.DeleteSiteMsgRequest;
import com.dcf.iqunxing.message2.request.ListSiteMsgRequest;
import com.dcf.iqunxing.message2.request.ProcessSiteMsgRequest;
import com.dcf.iqunxing.message2.request.ReadSiteMsgRequest;
import com.dcf.iqunxing.message2.request.SiteMsgRequest;
import com.dcf.iqunxing.message2.request.UpdateSiteMsgRequest;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@Service
public class SiteMessageService {

    private static final String LOG_TITLE = "站内信请求服务";

    ILog log = LogManager.getLogger(SiteMessageService.class);

    @Autowired
    protected SiteMessageMapper siteMessageMapper;

    @Autowired
    protected SiteMessageTemplateService siteMessageTemplateService;

    @Autowired
    protected SiteMessageContentService siteMessageContentService;

    /**
     * 根据主键获取SiteMessage.
     *
     * @param id
     *            the id
     * @return the site message by id
     */
    public SiteMessage getSiteMessageById(Long id) {
        Preconditions.checkNotNull(id);
        SiteMessage o = siteMessageMapper.selectByPrimaryKey(id);
        if (o != null && o.getDeleted() == DeleteFlag.SURVIVOR.getValue()) {
            return o;
        }
        return null;
    }

    /**
     * 根据请求存储SiteMessage实体.
     *
     * @param req
     *            the req
     * @return the long
     */
    public SiteMessage saveSiteMessageByReq(Long contentId, SiteMsgRequest req, ChannelType channelType) {
        Long templateId = null;
        SiteMessageTemplate template = null;
        // 通过模板ID获取模板内容
        if (req.getSiteMessageTemplateId() != null) {
            templateId = req.getSiteMessageTemplateId();
            template = siteMessageTemplateService.getMsgTemplate(templateId);
        }
        // 根据模板和请求实体构造siteMessage实体
        SiteMessage msg = buildSiteMessage(contentId, template, req, channelType);
        siteMessageMapper.insertSelective(msg);
        return msg;
    }

    /**
     * 构造SiteMessage实体并存储.
     *
     * @param template
     *            the template
     * @param req
     *            the req
     * @return the Site message
     */
    private SiteMessage buildSiteMessage(Long contentId, SiteMessageTemplate template, SiteMsgRequest req, ChannelType channelType) {
        SiteMessage message = new SiteMessage();
        message.setSiteMessageContentId(contentId);
        if (req.getReceiverId() != null) {
            message.setReceiverId(req.getReceiverId());
        }
        if (req.getSender() != null) {
            message.setSender(req.getSender());
        } else if (template != null) {
            message.setSender(template.getSender());
        }

        if (req.getPriority() != null) {
            message.setPriority(req.getPriority().getValue());
        } else if (template != null) {
            message.setPriority(template.getPriority());
        }

        if (req.getType() != null) {
            message.setType(req.getType().getValue());
        } else if (template != null) {
            message.setType(template.getType());
        }

        // TODO草稿时
        if (channelType == ChannelType.CREATE) {
            message.setState(SendState.DRAFT.getValue());
        } else {
            // 预期发送时间为空的话，则为即时
            if (req.getScheduleUtcTime() == null) {
                // 0即时；1定时
                message.setImmediate(ImmediateType.IMMEDIATE.getValue());
                message.setScheduleTime(new Date());
                // 站内信状态为已发送
                message.setState(SendState.SENT.getValue());
                message.setSentTime(new Date());
            } else {
                message.setImmediate(ImmediateType.SCHEDULE.getValue());
                message.setScheduleTime(new Date(req.getScheduleUtcTime()));
                // 站内信状态未待发送
                message.setState(SendState.TOBESEND.getValue());
                message.setSentTime(new Date(req.getScheduleUtcTime()));
            }
        }
        if (req.getExpiredUtcTime() != null) {
            message.setExpiredTime(new Date(req.getExpiredUtcTime()));
        }
        message.setCreatedTime(new Date());
        return message;
    }

    public boolean updateToSend(Long id, Date sentDate) {
        SiteMessage message = new SiteMessage();
        message.setId(id);
        message.setSentTime(sentDate);
        message.setState(SendState.SENT.getValue());
        siteMessageMapper.updateByPrimaryKeySelective(message);
        return true;
    }

    /**
     * 更新站内信
     * 
     * @param request
     */
    public void updateSiteMsg(UpdateSiteMsgRequest request) {
        SiteMessage siteMessage = getSiteMessageById(request.getId());
        // 构造siteMessageContent实体，从而更新siteMessageContent
        SiteMessageContent siteMessageContent = new SiteMessageContent();
        siteMessageContent.setId(siteMessage.getSiteMessageContentId());
        siteMessageContent.setTitle(request.getTitle());
        siteMessageContent.setContent(request.getContent());
        siteMessageContent.setAddition(request.getAddition());
        if (request.getType() != null) {
            siteMessageContent.setType(request.getType().getValue());
        }
        siteMessageContent.setUpdatedBy(request.getOperator());
        siteMessageContent.setUpdatedTime(new Date());
        siteMessageContentService.updateSiteContent(siteMessageContent);
        // 更新siteMessage
        if (request.getPriority() != null) {
            siteMessage.setPriority(request.getPriority().getValue());
        }
        siteMessage.setReceiverId(request.getReceiverId());
        siteMessage.setSender(request.getSender());
        if (request.getType() != null) {
            siteMessage.setType(request.getType().getValue());
        }
        if (request.getScheduleUtcTime() != null) {
            siteMessage.setScheduleTime(new Date(request.getScheduleUtcTime()));
        }
        if (request.getExpiredUtcTime() != null) {
            siteMessage.setExpiredTime(new Date(request.getExpiredUtcTime()));
        }
        siteMessageMapper.updateByPrimaryKeySelective(siteMessage);
    }

    /**
     * 删除站内信
     * 
     * @param request
     */
    public void deleteSiteMsg(DeleteSiteMsgRequest request) {
        for (long id : request.getIds()) {
            SiteMessage siteMessage = getSiteMessageById(id);
            siteMessage.setDeleted(DeleteFlag.VICTIM.getValue());
            siteMessage.setDeletedTime(new Date());
            siteMessageMapper.updateByPrimaryKeySelective(siteMessage);
        }
    }

    /**
     * 站内信标记为已读
     * 
     * @param request
     */
    public void readSiteMsg(ReadSiteMsgRequest request) {

        if (!CollectionUtils.isEmpty(request.getIds())) {
            for (long id : request.getIds()) {
                SiteMessage siteMessage = getSiteMessageById(id);
                if (siteMessage.getState() == MsgState.SENT.getValue()) {
                    siteMessage.setReadTime(new Date());
                    siteMessage.setState(MsgState.READ.getValue());
                    siteMessageMapper.updateByPrimaryKeySelective(siteMessage);
                }
            }
        } else if (!StringUtils.isBlank(request.getReceiver())) {
            SiteMessageExample example = new SiteMessageExample();
            SiteMessageExample.Criteria criteria = example.createCriteria();
            criteria.andStateEqualTo(MsgState.SENT.getValue());
            criteria.andReceiverIdEqualTo(request.getReceiver());
            criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
            if (request.getTypes() != null) {
                criteria.andTypeIn(new ArrayList<Short>(request.getTypes()));
            }
            SiteMessage record = new SiteMessage();
            record.setReadTime(new Date());
            record.setState(MsgState.READ.getValue());
            siteMessageMapper.updateByExampleSelective(record, example);
        }
    }

    /**
     * 根据条件，删选结果
     * 
     * @param request
     * @return
     */
    public PageResult<SiteMsgVo> listSiteMsg(final ListSiteMsgRequest request) {
        SiteMessageExample example = buildExampleCriteria(request);

        PageResult<SiteMsgVo> result = new PageResult<SiteMsgVo>();

        // 查询相应条件一共有几条
        if (request.getPage() != null) {
            result.setPage(request.getPage());
            if (request.getPage().isNeedTotalRecord()) {
                int count = siteMessageMapper.countByExample(example);
                request.getPage().setTotalRecord(count);
            }
            // 分页计算
            example.setLimitStart(request.getPage().getStart());
            example.setLimitEnd(request.getPage().getPageSize());
        }

        if (request.getPage() != null && request.getPage().isNeedTotalRecord() && request.getPage().getTotalRecord() == 0) {
            result.setResult(new ArrayList<SiteMsgVo>());
        } else {
            List<SiteMessage> siteMsgs = siteMessageMapper.selectByExample(example);
            List<SiteMsgVo> siteMsgVo = Lists.transform(siteMsgs, new Function<SiteMessage, SiteMsgVo>() {

                @Override
                public SiteMsgVo apply(SiteMessage siteMessage) {
                    SiteMsgVo vo = new SiteMsgVo();
                    vo.setCreatedTime(siteMessage.getCreatedTime());
                    vo.setExpiredTime(siteMessage.getExpiredTime());
                    vo.setId(siteMessage.getId());
                    vo.setImmediate(Boolean.valueOf(siteMessage.getImmediate().toString()));
                    vo.setMsgPriority(MsgPriority.fromValue(siteMessage.getPriority()));
                    vo.setMsgState(MsgState.fromValue(siteMessage.getState()));
                    vo.setReadTime(siteMessage.getReadTime());
                    vo.setReceiverId(siteMessage.getReceiverId());
                    vo.setScheduleTime(siteMessage.getScheduleTime());
                    vo.setSender(siteMessage.getSender());
                    vo.setSentTime(siteMessage.getSentTime());
                    vo.setType(SiteMsgType.fromValue(siteMessage.getType()));
                    vo.setNewType(siteMessage.getType());
                    SiteMessageContent contentPo = siteMessageContentService.getSiteMessageContent(siteMessage
                            .getSiteMessageContentId());
                    vo.setSiteMessageContent(contentPo.getContent());
                    vo.setTitle(contentPo.getTitle());
                    if (request.isNeedAddition()) {
                        vo.setSiteMessageAddition(contentPo.getAddition());
                    }
                    return vo;
                }
            });
            result.setResult(new ArrayList<SiteMsgVo>(siteMsgVo));
        }

        LogUtils.info(this.getClass(), LOG_TITLE, "列取SiteMsg返回值", result,
                TagBuilder.create().append("deviceId", request.getReceiver()));
        return result;
    }

    /**
     * 站内信标记为已处理
     * 
     * @param request
     */
    public void processSiteMsg(ProcessSiteMsgRequest request) {

        if (!CollectionUtils.isEmpty(request.getIds())) {
            for (long id : request.getIds()) {
                SiteMessage siteMessage = getSiteMessageById(id);
                if (siteMessage.getState() == MsgState.SENT.getValue() || siteMessage.getState() == MsgState.READ.getValue()) {
                    if (siteMessage.getReadTime() == null) {
                        siteMessage.setReadTime(new Date());
                    }
                    siteMessage.setProcessedTime(new Date());
                    siteMessage.setState(MsgState.PROCESSED.getValue());
                    siteMessageMapper.updateByPrimaryKeySelective(siteMessage);
                }
            }
        } else if (!StringUtils.isBlank(request.getReceiver())) {
            Date now = new Date();
            // 更新状态为已发送的消息
            SiteMessageExample example = new SiteMessageExample();
            SiteMessageExample.Criteria criteria = example.createCriteria();
            criteria.andStateEqualTo(MsgState.SENT.getValue());
            criteria.andReceiverIdEqualTo(request.getReceiver());
            criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
            if (request.getTypes() != null) {
                criteria.andTypeIn(new ArrayList<Short>(request.getTypes()));
            }
            SiteMessage record = new SiteMessage();
            record.setReadTime(now);
            record.setProcessedTime(now);
            record.setState(MsgState.PROCESSED.getValue());
            siteMessageMapper.updateByExampleSelective(record, example);

            // 更新状态为已阅读的消息
            example = new SiteMessageExample();
            criteria = example.createCriteria();
            criteria.andStateEqualTo(MsgState.READ.getValue());
            criteria.andReceiverIdEqualTo(request.getReceiver());
            criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
            if (request.getTypes() != null) {
                criteria.andTypeIn(new ArrayList<Short>(request.getTypes()));
            }
            record = new SiteMessage();
            record.setProcessedTime(now);
            record.setState(MsgState.PROCESSED.getValue());
            siteMessageMapper.updateByExampleSelective(record, example);
        }
    }

    /**
     * 构造站内信查询条件.
     *
     * @param request
     *            the request
     * @return the site message example
     */
    private SiteMessageExample buildExampleCriteria(ListSiteMsgRequest request) {
        SiteMessageExample example = new SiteMessageExample();
        SiteMessageExample.Criteria criteria = example.createCriteria();
        List<Byte> msgState = Lists.newArrayList();
        for (MsgState state : request.getStates()) {
            msgState.add(state.getValue());
        }
        if (msgState.size() > 0) {
            criteria.andStateIn(msgState);
        }
        List<Short> siteMsgType = Lists.newArrayList();
        for (SiteMsgType type : request.getTypes()) {
            siteMsgType.add(type.getValue());
        }
        if (siteMsgType.size() > 0) {
            criteria.andTypeIn(siteMsgType);
        }
        if (StringUtils.isNotEmpty(request.getReceiver())) {
            criteria.andReceiverIdEqualTo(request.getReceiver());
        }

        if (!CollectionUtils.isEmpty(request.getNewTypes())) {
            criteria.andTypeIn(new ArrayList<Short>(request.getNewTypes()));
        }
        if (request.getStartUtcTime() != null && request.getStartUtcTime() > 0) {
            criteria.andSentTimeGreaterThanOrEqualTo(new Date(request.getStartUtcTime()));
        }
        if (request.getEndUtcTime() != null && request.getEndUtcTime() > 0) {
            criteria.andSentTimeLessThan(new Date(request.getEndUtcTime()));
        }

        criteria.andDeletedEqualTo(DeleteFlag.SURVIVOR.getValue());
        example.setOrderByClause(" sent_time desc, read_time desc  ,  processed_time desc");
        return example;
    }
}
