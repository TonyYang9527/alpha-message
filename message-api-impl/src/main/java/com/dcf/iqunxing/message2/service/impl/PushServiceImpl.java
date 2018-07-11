package com.dcf.iqunxing.message2.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.entity.PushMessage;
import com.dcf.iqunxing.message2.entity.PushMessageTemplate;
import com.dcf.iqunxing.message2.enums.ImmediateType;
import com.dcf.iqunxing.message2.model.PushMsgTemplateVo;
import com.dcf.iqunxing.message2.model.enums.MsgPriority;
import com.dcf.iqunxing.message2.model.page.PageResult;
import com.dcf.iqunxing.message2.request.CreatePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DeletePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.DisablePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.EnablePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.GetPushMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.ListMsgTemplateRequest;
import com.dcf.iqunxing.message2.request.SendPushRequest;
import com.dcf.iqunxing.message2.request.UpdatePushMsgTemplateRequest;
import com.dcf.iqunxing.message2.response.CreatePushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DeletePushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.DisablePushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.EnablePushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.GetPushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.ListPushMsgTemplateResponse;
import com.dcf.iqunxing.message2.response.RetCodeConst;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.dcf.iqunxing.message2.response.UpdatePushMsgTemplateResponse;
import com.dcf.iqunxing.message2.service.IPushService;
import com.dcf.iqunxing.message2.service.aspect.validate.Validator;
import com.dcf.iqunxing.message2.service.aspect.validate.validator.SendPushRequestValidator;
import com.dcf.iqunxing.message2.service.internal.push.PushMessagePropertyService;
import com.dcf.iqunxing.message2.service.internal.push.PushMessageService;
import com.dcf.iqunxing.message2.service.internal.push.PushMessageTemplateService;
import com.dcf.iqunxing.message2.service.queue.PushMessageQueueService;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.mapper.BeanMapper;

@Service("pushService")
public class PushServiceImpl implements IPushService {

    private static final String LOG_TITLE = "接收PUSH请求服务";

    ILog log = LogManager.getLogger(PushServiceImpl.class);

    @Autowired
    protected PushMessageService pushMessageService;

    @Autowired
    protected PushMessagePropertyService pushMessagePropertyService;

    @Autowired
    protected PushMessageQueueService pushMessageQueueService;

    @Autowired
    protected PushMessageTemplateService pushMessageTemplateService;

    @Override
    @Validator(SendPushRequestValidator.class)
    public SendMessageResponse sendPush(SendPushRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "receiverId");
        LogUtils.info(this.getClass(), LOG_TITLE, "接收到PUSH发送请求，开始创建PUSH", request, builder);
        // 将请求信息保存入DB
        PushMessage pushMessage = saveReqToDB(request);
        // 如果是即时的则插入队列
        if (pushMessage.getImmediate().equals(ImmediateType.IMMEDIATE.getValue())) {
            LogUtils.info(this.getClass(), LOG_TITLE, "该PUSH为及时下发PUSH，将插入PUSH队列", pushMessage.getId(), builder);
            boolean result = pushMessageQueueService.offerToQueue(pushMessage);
            if (!result) {
                LogUtils.error(this.getClass(), LOG_TITLE, "插入PUSH队列失败", pushMessage.getId(), null, builder);
            }
        }
        SendMessageResponse resp = new SendMessageResponse();
        resp.setId(pushMessage.getId());
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("PUSH创建成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "PUSH创建成功", resp, builder);
        return resp;
    }

    /**
     * 将请求信息保存入DB.
     *
     * @param request
     *            the request
     * @return the push message
     */
    protected PushMessage saveReqToDB(SendPushRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "receiverId");
        // 创建PUSH push_message
        PushMessage pushMessage = pushMessageService.savePushMessageByReq(request);
        LogUtils.info(this.getClass(), LOG_TITLE, "保存PUSH主体成功", pushMessage, builder);
        // 创建push_message_property
        Long messageId = pushMessage.getId();
        Map<String, String> props = request.getProperties();
        pushMessagePropertyService.savePushMessageProperty(messageId, props);
        // 中央日志：开始存入DB
        LogUtils.info(this.getClass(), LOG_TITLE, "保存PUSHKey/Value成功", props, builder);
        return pushMessage;
    }

    @Override
    public CreatePushMsgTemplateResponse createPushMsgTemplate(CreatePushMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "name", "title", "content", "type", "priority", "operator");
        log.info("创建PUSH模板", "创建PUSH模板开始", builder.build());

        CreatePushMsgTemplateResponse resp = new CreatePushMsgTemplateResponse();
        PushMessageTemplate tmp = pushMessageTemplateService.createMsgTemplate(request);
        resp.setId(tmp.getId());
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("push模板创建成功");
        log.info("创建PUSH模板", "创建PUSH模板成功", builder.build());
        return resp;
    }

    @Override
    public DeletePushMsgTemplateResponse deletePushMsgTemplate(DeletePushMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "ids", "operator");
        log.info("删除PUSH模板", "删除PUSH模板开始", builder.build());

        DeletePushMsgTemplateResponse resp = new DeletePushMsgTemplateResponse();
        pushMessageTemplateService.deletePushMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("push模板删除成功");
        log.info("删除PUSH模板", "删除PUSH模板成功", builder.build());
        return resp;
    }

    @Override
    public UpdatePushMsgTemplateResponse updatePushMsgTemplate(UpdatePushMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "id", "name", "title", "content", "type", "priority", "operator");
        log.info("更新PUSH模板", "更新PUSH模板开始", builder.build());
        UpdatePushMsgTemplateResponse resp = new UpdatePushMsgTemplateResponse();
        pushMessageTemplateService.updatePushMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("push模板变更成功");
        log.info("更新PUSH模板", "更新PUSH模板成功", builder.build());
        return resp;
    }

    @Override
    public GetPushMsgTemplateResponse getPushMsgTemplate(GetPushMsgTemplateRequest request) {
        // 日志Tag
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        log.info("查询PUSH模板", "查询PUSH模板开始", builder.build());
        GetPushMsgTemplateResponse resp = new GetPushMsgTemplateResponse();
        PushMessageTemplate pushMessageTemplate = pushMessageTemplateService.getMsgTemplate(request.getId());
        PushMsgTemplateVo pushMsgTemplateVo = BeanMapper.map(pushMessageTemplate, PushMsgTemplateVo.class);
        pushMsgTemplateVo.setMsgPriority(MsgPriority.fromValue(pushMessageTemplate.getPriority()));
        resp.setTemplate(pushMsgTemplateVo);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("push模板查询成功");
        // 日志
        TagBuilder tagBuilder = LogUtils.getTagBuilder(pushMsgTemplateVo, "id", "name", "title", "content", "type", "priority");
        log.info("查询PUSH模板", "查询PUSH模板成功", tagBuilder.build());
        return resp;
    }

    /**
     * 禁用PUSH模版
     */
    @Override
    public DisablePushMsgTemplateResponse disablePushMsgTemplate(DisablePushMsgTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        LogUtils.info(this.getClass(), LOG_TITLE, "禁用PUSH模板开始 request ", request, builder);
        DisablePushMsgTemplateResponse resp = new DisablePushMsgTemplateResponse();
        pushMessageTemplateService.disablePushMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("禁用PUSH模版成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "禁用PUSH模版结束 response ", resp, builder);
        return resp;
    }

    /**
     * 启用PUSH模版
     */
    @Override
    public EnablePushMsgTemplateResponse enablePushMsgTemplate(EnablePushMsgTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "id");
        LogUtils.info(this.getClass(), LOG_TITLE, "启用PUSH模板开始 request ", request, builder);
        EnablePushMsgTemplateResponse resp = new EnablePushMsgTemplateResponse();
        pushMessageTemplateService.enablePushMsgTemplate(request);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("启用PUSH模版成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "启用PUSH模版结束 response ", resp, builder);
        return resp;
    }

    /**
     * 列取PUSH模版列表
     */
    @Override
    public ListPushMsgTemplateResponse listPushMsgTemplate(ListMsgTemplateRequest request) {
        TagBuilder builder = LogUtils.getTagBuilder(request, "name", "state");
        LogUtils.info(this.getClass(), LOG_TITLE, "列取PUSH模版列表开始 request ", request, builder);
        ListPushMsgTemplateResponse resp = new ListPushMsgTemplateResponse();
        PageResult<PushMsgTemplateVo> result = pushMessageTemplateService.listPushMsgTemplate(request);
        resp.setResult(result);
        resp.setRetCode(RetCodeConst.SUCCESS);
        resp.setRetMsg("列取PUSH模版列表成功");
        LogUtils.info(this.getClass(), LOG_TITLE, "列取PUSH模版列表结束 response ", resp, builder);
        return resp;
    }

}
