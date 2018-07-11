package com.dcf.iqunxing.message2.service.aspect.validate.validator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.dcf.iqunxing.message2.entity.EmailMessageTemplate;
import com.dcf.iqunxing.message2.request.SendEmailRequest;
import com.dcf.iqunxing.message2.service.internal.email.EmailMessageTemplateService;
import com.dcf.iqunxing.message2.util.MailValid;
import com.google.common.collect.Lists;

@Component
public class SendEmailRequestValidator extends ReqValidator {

    @Autowired
    private EmailMessageTemplateService service;

    @Autowired
    private MailValid mailValid;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SendEmailRequest.class);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        SendEmailRequest req = (SendEmailRequest) obj;
        ValidationUtils.rejectIfEmpty(errors, "toAddresses", "toAddress不能为空");
        if (StringUtils.isNotEmpty(req.getToAddresses())) {
            if (req.getToAddresses().length() > 3000) {
                errors.reject("toAddresses", "邮件地址长度最长为3000");
            }
            String[] addressArr = req.getToAddresses().split("\\|");
            for (String address : addressArr) {
                if (!mailValid.valid(address)) {
                    errors.reject("toAddresses", "邮件地址不合法");
                }
            }
        }
        if (StringUtils.isNotEmpty(req.getCcAddresses())) {
            if (req.getCcAddresses().length() > 3000) {
                errors.reject("ccAddresses", "cc地址长度最长为3000");
            }
            String[] addressArr = req.getCcAddresses().split("\\|");
            for (String address : addressArr) {
                if (!mailValid.valid(address)) {
                    errors.reject("ccAddresses", "cc邮件地址不合法");
                }
            }
        }
        if (StringUtils.isNotEmpty(req.getBccAddresses())) {
            if (req.getBccAddresses().length() > 3000) {
                errors.reject("bccAddresses", "bcc地址长度最长为3000");
            }
            String[] addressArr = req.getBccAddresses().split("\\|");
            for (String address : addressArr) {
                if (!mailValid.valid(address)) {
                    errors.reject("bccAddresses", "bcc邮件地址不合法");
                }
            }
        }
        ValidationUtils.rejectIfEmpty(errors, "expiredUtcTime", "过期时间不能为空");
        if (req.getExpiredUtcTime() != null && req.getExpiredUtcTime() < System.currentTimeMillis()) {
            errors.reject("expiredUtcTime", "过期时间时间必须为将来时间");
        }
        if (req.getScheduleUtcTime() != null && req.getScheduleUtcTime() < System.currentTimeMillis()) {
            errors.reject("scheduleUtcTime", "发送时间时间必须为将来时间");
        }
        ValidationUtils.rejectIfEmpty(errors, "emailMessageTemplateId", "emailMessageTemplateId不能为空");
        ValidationUtils.rejectIfEmpty(errors, "userId", "userId不能为空");
    }

    @Override
    public List<String> businessValidate(Object obj) {
        SendEmailRequest req = (SendEmailRequest) obj;
        List<String> errorMsgs = Lists.newArrayList();
        Long templateId = req.getEmailMessageTemplateId();
        EmailMessageTemplate template = service.getMsgTemplate(templateId);
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
