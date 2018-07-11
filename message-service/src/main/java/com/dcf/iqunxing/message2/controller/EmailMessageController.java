package com.dcf.iqunxing.message2.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcf.iqunxing.message2.entity.EmailMessageTemplate;
import com.dcf.iqunxing.message2.request.SendEmailRequest;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.dcf.iqunxing.message2.service.IEmailService;
import com.dcf.iqunxing.message2.service.internal.email.EmailMessageTemplateService;
import com.dcf.iqunxing.message2.util.mapper.JsonMapper;
import com.google.common.collect.Maps;

/**
 * The Class EmailMseeageController.
 */
@Controller
public class EmailMessageController {

    private Logger logger = LoggerFactory.getLogger(EmailMessageController.class);

    /** The email service. */
    @Autowired
    private IEmailService emailService;

    @Autowired
    private EmailMessageTemplateService emailMessageTemplateService;

    @RequestMapping(value = "api/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail(String toAddresses, String ccAddresses, String bccAddresses, Long templateId, String keyValue,
            String scheduleUtcTime, String expiredUtcTime) throws IOException {
        SendEmailRequest emailRequest = new SendEmailRequest();
        emailRequest.setToAddresses(toAddresses);
        emailRequest.setCcAddresses(ccAddresses);
        emailRequest.setBccAddresses(bccAddresses);
        emailRequest.setEmailMessageTemplateId(templateId);

        Map<String, String> properties = Maps.newHashMap();
        String st[] = keyValue.split("\r\n");
        int num = st.length;
        for (int i = 0; i < num; i++) {
            String stt[] = new String[1];
            if (st[i] != null) {
                stt = st[i].trim().split("=");
                if (stt != null && stt.length == 2 && stt[0] != null && stt[1] != null) {
                    properties.put(stt[0], stt[1]);
                }
            }
        }
        emailRequest.setProperties(properties);
        emailRequest.setUserId("test");
        emailRequest.setScheduleUtcTime(dateToLong(scheduleUtcTime));
        emailRequest.setExpiredUtcTime(dateToLong(expiredUtcTime));

        // 附件复数
        Map<String, byte[]> attachments = Maps.newHashMap();

        // 获取附件名字
        // for (int i = 0; i < file.length; i++) {
        // attachments.put(file.getName(), file.getBytes());
        // }

        emailRequest.setAttachments(attachments);

        SendMessageResponse resp = emailService.sendEmail(emailRequest);

        return JsonMapper.nonEmptyMapper().toJson(resp.getRetMsg());
    }

    /**
     * 获取邮件模板.
     *
     * @param id
     *            the id
     * @return the email template
     */
    @RequestMapping(value = "api/getEmailTemplate", method = RequestMethod.GET)
    @ResponseBody
    public String getEmailTemplate(Long id) {
        Map<String, String> resultMap = Maps.newHashMap();
        String template = "不存在[id=" + id + "]对应的模板";
        StringBuffer keyValue = new StringBuffer();
        EmailMessageTemplate po = emailMessageTemplateService.getMsgTemplate(id);
        if (po != null) {
            template = po.getContent();
            Pattern p = Pattern.compile("\\$\\{(.*?)\\}");
            Matcher m = p.matcher(template);
            while (m.find()) {
                keyValue.append(m.group(1)).append("=\r\n");
            }
        }
        resultMap.put("template", template);
        resultMap.put("keyValue", keyValue.toString());
        return JsonMapper.nonEmptyMapper().toJson(resultMap);
    }

    /**
     * 日期字符串转long.
     *
     * @param dateStr
     *            the date str
     * @return the long
     */
    public Long dateToLong(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Long date = null;
        try {
            date = sdf.parse(dateStr).getTime();
        } catch (ParseException e) {
            logger.error("日期转换错误:{}", dateStr);
        }
        return date;
    }
}
