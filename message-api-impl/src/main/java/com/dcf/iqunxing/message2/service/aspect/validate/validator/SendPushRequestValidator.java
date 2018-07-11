package com.dcf.iqunxing.message2.service.aspect.validate.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.dcf.iqunxing.message2.entity.PushMessageTemplate;
import com.dcf.iqunxing.message2.request.SendPushRequest;
import com.dcf.iqunxing.message2.service.internal.push.PushMessageTemplateService;
import com.google.common.collect.Lists;

@Component
public class SendPushRequestValidator extends ReqValidator {

    @Autowired
    private PushMessageTemplateService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SendPushRequest.class);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        SendPushRequest req = (SendPushRequest) obj;
        ValidationUtils.rejectIfEmpty(errors, "receiverId", "receiverId不能为空");
        ValidationUtils.rejectIfEmpty(errors, "expiredUtcTime", "过期时间不能为空");
        if (req.getExpiredUtcTime() != null && req.getExpiredUtcTime() < System.currentTimeMillis()) {
            errors.reject("expiredUtcTime", "过期时间时间必须为将来时间");
        }
        if (req.getScheduleUtcTime() != null && req.getScheduleUtcTime() < System.currentTimeMillis()) {
            errors.reject("scheduleUtcTime", "发送时间时间必须为将来时间");
        }
        ValidationUtils.rejectIfEmpty(errors, "pushMessageTemplateId", "emailMessageTemplateId不能为空");
        ValidationUtils.rejectIfEmpty(errors, "userId", "userId不能为空");
    }

    @Override
    public List<String> businessValidate(Object obj) {
        SendPushRequest req = (SendPushRequest) obj;
        List<String> errorMsgs = Lists.newArrayList();
        Long templateId = req.getPushMessageTemplateId();
        PushMessageTemplate template = service.getMsgTemplate(templateId);
        if (template == null) {
            errorMsgs.add("模板ID: " + templateId + "对应的模板不存在");
        } else {
            boolean isValidity = service.validatorTemplateValidity(templateId);
            if (!isValidity)
                errorMsgs.add("模板ID: " + templateId + "对应的模板被禁用或删除");
        }
        return errorMsgs;
    }
}
