package com.dcf.iqunxing.message2.service.aspect.validate.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.dcf.iqunxing.message2.entity.SiteMessageTemplate;
import com.dcf.iqunxing.message2.request.SiteMsgRequest;
import com.dcf.iqunxing.message2.service.internal.site.SiteMessageTemplateService;
import com.google.common.collect.Lists;

@Component
public class SiteMsgRequestValidator extends ReqValidator {

    @Autowired
    private SiteMessageTemplateService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SiteMsgRequest.class);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        SiteMsgRequest req = (SiteMsgRequest) obj;
        ValidationUtils.rejectIfEmpty(errors, "receiverId", "receiverId不能为空");
        ValidationUtils.rejectIfEmpty(errors, "expiredUtcTime", "过期时间不能为空");
        if (req.getExpiredUtcTime() != null && req.getExpiredUtcTime() < System.currentTimeMillis()) {
            errors.reject("expiredUtcTime", "过期时间时间必须为将来时间");
        }
        if (req.getScheduleUtcTime() != null && req.getScheduleUtcTime() < System.currentTimeMillis()) {
            errors.reject("scheduleUtcTime", "发送时间时间必须为将来时间");
        }
        ValidationUtils.rejectIfEmpty(errors, "siteMessageTemplateId", "siteMessageTemplateId不能为空");
        ValidationUtils.rejectIfEmpty(errors, "sender", "sender不能为空");
    }

    @Override
    public List<String> businessValidate(Object obj) {
        SiteMsgRequest req = (SiteMsgRequest) obj;
        List<String> errorMsgs = Lists.newArrayList();
        Long templateId = req.getSiteMessageTemplateId();
        SiteMessageTemplate template = service.getMsgTemplate(templateId);
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
