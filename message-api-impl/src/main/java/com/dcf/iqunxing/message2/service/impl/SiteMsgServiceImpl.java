package com.dcf.iqunxing.message2.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.entity.SiteMessage;
import com.dcf.iqunxing.message2.entity.SiteMessageContent;
import com.dcf.iqunxing.message2.entity.SiteMessageTemplate;
import com.dcf.iqunxing.message2.enums.ChannelType;
import com.dcf.iqunxing.message2.model.SiteMsgTemplateVo;
import com.dcf.iqunxing.message2.model.SiteMsgVo;
import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.enums.MsgState;
import com.dcf.iqunxing.message2.model.page.PageResult;
import com.dcf.iqunxing.message2.request.CreateSiteMsgRequest;
import com.dcf.iqunxing.message2.request.CreateSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DeleteSiteMsgRequest;
import com.dcf.iqunxing.message2.request.DeleteSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DisableSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.EnableSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.GetSiteMsgRequest;
import com.dcf.iqunxing.message2.request.GetSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListSiteMsgRequest;
import com.dcf.iqunxing.message2.request.ProcessSiteMsgRequest;
import com.dcf.iqunxing.message2.request.ReadSiteMsgRequest;
import com.dcf.iqunxing.message2.request.SendSiteMsgRequest;
import com.dcf.iqunxing.message2.request.SiteMsgRequest;
import com.dcf.iqunxing.message2.request.UpdateSiteMsgRequest;
import com.dcf.iqunxing.message2.request.UpdateSiteMsgTemplateRequest;
import com.dcf.iqunxing.message2.response.CreateSiteMsgResponse;
import com.dcf.iqunxing.message2.response.CreateSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DeleteSiteMsgResponse;
import com.dcf.iqunxing.message2.response.DeleteSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DisableSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.EnableSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.GetSiteMsgResponse;
import com.dcf.iqunxing.message2.response.GetSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.ListSiteMsgResponse;
import com.dcf.iqunxing.message2.response.ListSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.ProcessSiteMsgResponse;
import com.dcf.iqunxing.message2.response.ReadSiteMsgResponse;
import com.dcf.iqunxing.message2.response.RetCodeConst;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.dcf.iqunxing.message2.response.UpdateSiteMsgResponse;
import com.dcf.iqunxing.message2.response.UpdateSiteMsgTemplateResponse;
import com.dcf.iqunxing.message2.service.ISiteMsgService;
import com.dcf.iqunxing.message2.service.aspect.validate.Validator;
import com.dcf.iqunxing.message2.service.aspect.validate.validator.SiteMsgRequestValidator;
import com.dcf.iqunxing.message2.service.internal.site.SiteMessageContentService;
import com.dcf.iqunxing.message2.service.internal.site.SiteMessagePropertyService;
import com.dcf.iqunxing.message2.service.internal.site.SiteMessageService;
import com.dcf.iqunxing.message2.service.internal.site.SiteMessageTemplateService;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.mapper.BeanMapper;

@Service("siteMsgService")
public class SiteMsgServiceImpl implements ISiteMsgService {

    private static final String LOG_TITLE = "接收站内信请求服务";

    ILog log = LogManager.getLogger(SiteMsgServiceImpl.class);

    @Autowired
    protected SiteMessageService siteMessageService;

    @Autowired
    protected SiteMessagePropertyService siteMessagePropertyService;

    @Autowired
    protected SiteMessageTemplateService siteMessageTemplateService;

    @Autowired
    protected SiteMessageContentService siteMessageContentService;

    @Override
    public SendMessageResponse sendSiteMsg(SendSiteMsgRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "receiverId");
        // TODO SendSiteMsgRequest->SiteMsgRequest
        SiteMsgRequest siteMsgRequest = BeanMapper.map(request, SiteMsgRequest.class);
        LogUtils.info(this.getClass(), LOG_TITLE, "接收到站内信发送请求，开始创建站内信", request, builder);
        SendMessageResponse resp = new SendMessageResponse();
        // 如果id存在，则直接发送
        if (request.getId() != null) {
            SiteMessage siteMessage = siteMessageService.getSiteMessageById(request.getId());
            if (siteMessage != null) {
                // 参数检查TODO
                siteMsgRequest.setReceiverId(siteMessage.getReceiverId());
                siteMsgRequest.setExpiredUtcTime(siteMessage.getExpiredTime().getTime());
                siteMsgRequest.setSender(siteMessage.getSender());
                if (RetCodeConst.SUCCESS.equals(resp.getRetCode())) {
                    // state从草稿变为已发送
                    siteMessageService.updateToSend(request.getId(), new Date());
                    LogUtils.info(this.getClass(), LOG_TITLE, "站内信草稿已发送", resp, builder);
                    resp.setRetCode(RetCodeConst.SUCCESS);
                    resp.setRetMsg("站内信发送");
                }
            } else {
                resp.setRetCode(RetCodeConst.FAIL);
                resp.setRetMsg("id不存在,请检查后重发");
            }
        } else {
            resp = sendSiteMsg(siteMsgRequest);
        }
        return resp;
    }

    @Validator(SiteMsgRequestValidator.class)
    private SendMessageResponse sendSiteMsg(SiteMsgRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "receiverId");
        SiteMessage siteMessage = saveReqToDB(request, ChannelType.SEND);
        LogUtils.info(this.getClass(), LOG_TITLE, "将站内信发送请求存入数据库成功", siteMessage, builder);
        SendMessageResponse resp = new SendMessageResponse();
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setId(siteMessage.getId());
        resp.setRetMsg("站内信创建成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "创建站内信成功响应", resp, builder);

        return resp;
    }

    /**
     * 将请求信息保存入DB.
     *
     * @param request
     *            the request
     * @return the site message
     */
    protected SiteMessage saveReqToDB(SiteMsgRequest request, ChannelType channelType) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "receiverId");
        // 创建site_message_content
        SiteMessageContent content = siteMessageContentService.saveSiteContent(request);
        // 创建site_message
        SiteMessage siteMessage = siteMessageService.saveSiteMessageByReq(content.getId(), request, channelType);
        LogUtils.info(this.getClass(), LOG_TITLE, "保存站内信主体成功", siteMessage.getId(), builder);
        // 创建site_message_property
        Long messageId = siteMessage.getId();
        Map<String, String> props = request.getProperties();
        siteMessagePropertyService.saveSiteMessageProperty(messageId, props);
        // 中央日志：开始存入DB
        LogUtils.info(this.getClass(), LOG_TITLE, "保存站内信Key/Value成功", props, builder);
        return siteMessage;
    }

    @Override
    public CreateSiteMsgTemplateResponse createSiteMsgTemplate(CreateSiteMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "name", "title", "content", "addition", "type", "priority", "sender",
                "operator");
        log.info("创建SITE模板", "创建SITE模板开始", builder.build());

        CreateSiteMsgTemplateResponse resp = new CreateSiteMsgTemplateResponse();
        SiteMessageTemplate tmp = siteMessageTemplateService.createSiteTemplate(request);
        resp.setId(tmp.getId());
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("站内信模板创建成功");
        log.info("创建SITE模板", "创建SITE模板成功", builder.build());
        return resp;
    }

    @Override
    public UpdateSiteMsgTemplateResponse updateSiteMsgTemplate(UpdateSiteMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "id", "name", "title", "content", "addition", "type", "priority",
                "sender", "operator");
        log.info("更新SITE模板", "更新SITE模板开始", builder.build());

        UpdateSiteMsgTemplateResponse resp = new UpdateSiteMsgTemplateResponse();
        siteMessageTemplateService.updateSiteMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("站内信模板变更成功");
        log.info("更新SITE模板", "更新SITE模板成功", builder.build());
        return resp;
    }

    @Override
    public DeleteSiteMsgTemplateResponse deleteSiteMsgTemplate(DeleteSiteMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "ids", "operator");
        log.info("删除SITE模板", "删除SITE模板开始", builder.build());
        DeleteSiteMsgTemplateResponse resp = new DeleteSiteMsgTemplateResponse();
        siteMessageTemplateService.deleteSiteMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("站内信模板删除成功");
        log.info("删除SITE模板", "删除SITE模板成功", builder.build());
        return resp;
    }

    @Override
    public CreateSiteMsgResponse createSiteMsg(CreateSiteMsgRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "receiverId", "siteMessageTemplateId", "properties", "sender",
                "addition");
        log.info("创建SITEMESSAGE", "创建SITEMESSAGE开始", builder.build());

        CreateSiteMsgResponse resp = new CreateSiteMsgResponse();
        SiteMsgRequest siteMsgRequest = BeanMapper.map(request, SiteMsgRequest.class);
        // TODO
        SiteMessage siteMessage = this.saveReqToDB(siteMsgRequest, ChannelType.CREATE);
        resp.setId(siteMessage.getId());
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("站内信(草稿)创建成功");
        log.info("创建SITEMESSAGE", "创建SITEMESSAGE成功", builder.build());
        return resp;
    }

    @Override
    public UpdateSiteMsgResponse updateSiteMsg(UpdateSiteMsgRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "receiverId", "siteMessageTemplateId", "properties", "sender",
                "addition");
        log.info("更新SITEMESSAGE", "更新SITEMESSAGE开始", builder.build());
        UpdateSiteMsgResponse resp = new UpdateSiteMsgResponse();
        siteMessageService.updateSiteMsg(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("更新站内信成功");
        log.info("更新SITEMESSAGE", "更新SITEMESSAGE成功", builder.build());
        return resp;
    }

    @Override
    public DeleteSiteMsgResponse deleteSiteMsg(DeleteSiteMsgRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "ids", "operator");
        log.info("删除SITEMESSAGE", "删除SITEMESSAGE开始", builder.build());
        DeleteSiteMsgResponse resp = new DeleteSiteMsgResponse();
        siteMessageService.deleteSiteMsg(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("站内信删除成功");
        log.info("删除SITEMESSAGE", "删除SITEMESSAGE成功", builder.build());
        return resp;
    }

    /***
     * 站内信 已发送 变成 已阅读
     **/
    @Override
    public ReadSiteMsgResponse readSiteMsg(ReadSiteMsgRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "ids", "receiver", "types");
        LogUtils.info(this.getClass(), LOG_TITLE, "readSiteMsg  request", request, builder);
        ReadSiteMsgResponse resp = new ReadSiteMsgResponse();
        siteMessageService.readSiteMsg(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("站内信标记为已读");
        LogUtils.info(this.getClass(), LOG_TITLE, "readSiteMsg  response", resp, builder);
        return resp;
    }

    @Override
    public ListSiteMsgResponse listSiteMsg(ListSiteMsgRequest request) {
        ListSiteMsgResponse resp = new ListSiteMsgResponse();
        LogUtils.info(this.getClass(), LOG_TITLE, "列取SiteMsg传入参数", request,
                TagBuilder.create().append("deviceId", request.getReceiver()));

        PageResult<SiteMsgVo> result = siteMessageService.listSiteMsg(request);

        LogUtils.info(this.getClass(), LOG_TITLE, "列取SiteMsg返回值", result,
                TagBuilder.create().append("deviceId", request.getReceiver()));
        resp.setResult(result);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("查询成功");
        return resp;
    }

    @Override
    public GetSiteMsgTemplateResponse getSiteMsgTemplate(GetSiteMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        log.info("查询SITE模板", "查询SITE模板开始", builder.build());
        GetSiteMsgTemplateResponse resp = new GetSiteMsgTemplateResponse();
        SiteMessageTemplate siteMessageTemplate = siteMessageTemplateService.getMsgTemplate(request.getId());
        SiteMsgTemplateVo siteMsgTemplateVo = BeanMapper.map(siteMessageTemplate, SiteMsgTemplateVo.class);
        siteMsgTemplateVo.setMsgPriority(MsgPriority.fromValue(siteMessageTemplate.getPriority()));
        resp.setTemplate(siteMsgTemplateVo);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("站内信模板查询成功");
        log.info("查询SITE模板", "查询SITE模板成功", builder.build());
        return resp;
    }

    @Override
    public ListSiteMsgTemplateResponse listSiteMsgTemplate(ListMsgTemplateRequest request) {
        ListSiteMsgTemplateResponse resp = new ListSiteMsgTemplateResponse();
        PageResult<SiteMsgTemplateVo> result = siteMessageTemplateService.listSiteMsgTemplate(request);
        resp.setResult(result);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("查询成功");
        return resp;
    }

    @Override
    public GetSiteMsgResponse getSiteMsg(GetSiteMsgRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        log.info("查询SITE", "查询SITE开始", builder.build());
        GetSiteMsgResponse resp = new GetSiteMsgResponse();
        SiteMessage siteMessage = siteMessageService.getSiteMessageById(request.getId());
        SiteMessageContent siteMessageContent = siteMessageContentService.getSiteMessageContent(siteMessage
                .getSiteMessageContentId());
        // SiteMsgVo siteMsgVo = BeanMapper.map(siteMessage, SiteMsgVo.class);
        SiteMsgVo siteMsgVo = buildSiteMsgVo(siteMessage);
        if (siteMsgVo == null) {
            resp.setMsg(siteMsgVo);
            resp.setRetCode(RetCodeConst.FAIL);
            resp.setRetMsg("站内信查询失败");
            log.info("查询SITE", "站内信查询失败", builder.build());
            return resp;
        }
        siteMsgVo.setSiteMessageContent(siteMessageContent.getContent());
        resp.setMsg(siteMsgVo);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("站内信查询成功");
        log.info("查询SITE", "查询SITE成功", builder.build());
        return resp;
    }

    /**
     * 禁用站内信模版
     */
    @Override
    public DisableSiteMsgTemplateResponse disableSiteMsgTemplate(DisableSiteMsgTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        LogUtils.info(this.getClass(), LOG_TITLE, " 禁用站内信模版开始 request ", request, builder);
        DisableSiteMsgTemplateResponse resp = new DisableSiteMsgTemplateResponse();
        siteMessageTemplateService.disableSiteMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg(" 禁用站内信模版成功");
        LogUtils.info(this.getClass(), LOG_TITLE, " 禁用站内信模版结束 response ", resp, builder);
        return resp;
    }

    /**
     * 启用站内信模版
     */
    @Override
    public EnableSiteMsgTemplateResponse enableSiteMsgTemplate(EnableSiteMsgTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        LogUtils.info(this.getClass(), LOG_TITLE, " 启用站内信模版开始 request ", request, builder);
        EnableSiteMsgTemplateResponse resp = new EnableSiteMsgTemplateResponse();
        siteMessageTemplateService.enableSiteMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg(" 启用站内信模版成功");
        LogUtils.info(this.getClass(), LOG_TITLE, " 启用站内信模版结束 response ", resp, builder);
        return resp;
    }

    /**
     * 站内信 已阅读 变成 已处理
     */
    @Override
    public ProcessSiteMsgResponse processSiteMsg(ProcessSiteMsgRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "ids", "receiver", "types");
        LogUtils.info(this.getClass(), LOG_TITLE, "processSiteMsg  request", request, builder);
        ProcessSiteMsgResponse resp = new ProcessSiteMsgResponse();
        siteMessageService.processSiteMsg(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("站内信标记为已处理");
        LogUtils.info(this.getClass(), LOG_TITLE, "processSiteMsg  response", resp, builder);
        return resp;
    }

    private SiteMsgVo buildSiteMsgVo(SiteMessage siteMessage) {
        if (siteMessage == null)
            return null;
        SiteMsgVo vo = new SiteMsgVo();
        vo.setId(siteMessage.getId());
        vo.setReceiverId(siteMessage.getReceiverId());
        vo.setSender(siteMessage.getSender());
        vo.setScheduleTime(siteMessage.getScheduleTime());
        vo.setExpiredTime(siteMessage.getExpiredTime());
        vo.setSentTime(siteMessage.getSentTime());
        vo.setReadTime(siteMessage.getReadTime());
        vo.setCreatedTime(siteMessage.getCreatedTime());

        if (siteMessage.getImmediate() != null)
            vo.setImmediate(Boolean.valueOf(siteMessage.getImmediate().toString()));
        else
            vo.setImmediate(false);

        vo.setMsgPriority(MsgPriority.fromValue(siteMessage.getPriority()));
        vo.setMsgState(MsgState.fromValue(siteMessage.getState()));
        vo.setNewType(siteMessage.getType());
        return vo;
    }
}
