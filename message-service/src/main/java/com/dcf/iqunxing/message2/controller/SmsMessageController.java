package com.dcf.iqunxing.message2.controller;

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

import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.entity.SmsMessageTemplate;
import com.dcf.iqunxing.message2.model.enums.Channel;
import com.dcf.iqunxing.message2.request.QueryBalanceRequest;
import com.dcf.iqunxing.message2.request.SendSmsRequest;
import com.dcf.iqunxing.message2.response.QueryBalanceResponse;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.dcf.iqunxing.message2.service.ISmsService;
import com.dcf.iqunxing.message2.service.internal.sms.SmsMessageTemplateService;
import com.dcf.iqunxing.message2.util.mapper.JsonMapper;
import com.google.common.collect.Maps;

/**
 * The Class SendMseeageController.
 */
@Controller
public class SmsMessageController {

    private Logger logger = LoggerFactory.getLogger(SmsMessageController.class);

    private ILog log = LogManager.getLogger(SmsMessageController.class);

    /** The sms service. */
    @Autowired
    private ISmsService smsService;

    @Autowired
    private SmsMessageTemplateService smsMessageTemplateService;

    @RequestMapping(value = "/api/sendSms", method = RequestMethod.POST)
    @ResponseBody
    public String sendSms(Long templateId, String keyValue, String mobiles, String scheduleUtcTime, String expiredUtcTime) {
        SendSmsRequest smsRequest = new SendSmsRequest();
        smsRequest.setTemplateId(templateId);
        smsRequest.setMobiles(mobiles);

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
        smsRequest.setUserId("test");
        smsRequest.setProperties(properties);
        smsRequest.setScheduleUtcTime(dateToLong(scheduleUtcTime));
        smsRequest.setExpiredUtcTime(dateToLong(expiredUtcTime));
        SendMessageResponse resp = smsService.sendSms(smsRequest);
        return JsonMapper.nonEmptyMapper().toJson(resp.getRetMsg());
    }

    /**
     * 获取短信模板.
     *
     * @param id
     *            the id
     * @return the sms template
     */
    @RequestMapping(value = "/api/getSmsTemplate", method = RequestMethod.GET)
    @ResponseBody
    public String getSmsTemplate(Long id) {
        Map<String, String> resultMap = Maps.newHashMap();
        String template = "不存在[id=" + id + "]对应的模板";
        StringBuffer keyValue = new StringBuffer();
        SmsMessageTemplate po = smsMessageTemplateService.getMsgTemplate(id);
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
     * 查询通道余额.
     *
     * @param id
     *            the id
     * @return the sms template
     */
    @RequestMapping(value = "/api/getBalance", method = RequestMethod.GET)
    @ResponseBody
    public String getBalance() {
        QueryBalanceRequest req = new QueryBalanceRequest();
        req.setChannel(Channel.SMS_EMAY);
        QueryBalanceResponse resp = smsService.queryBalance(req);
        String balance = resp.getRetMsg();
        return balance;
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
