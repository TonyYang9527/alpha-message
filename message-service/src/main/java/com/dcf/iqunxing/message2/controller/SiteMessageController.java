package com.dcf.iqunxing.message2.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.dcf.iqunxing.message2.entity.SiteMessageTemplate;
import com.dcf.iqunxing.message2.model.SiteMsgVo;
import com.dcf.iqunxing.message2.model.page.Page;
import com.dcf.iqunxing.message2.model.page.PageResult;
import com.dcf.iqunxing.message2.request.ListSiteMsgRequest;
import com.dcf.iqunxing.message2.request.SendSiteMsgRequest;
import com.dcf.iqunxing.message2.response.SendMessageResponse;
import com.dcf.iqunxing.message2.service.ISiteMsgService;
import com.dcf.iqunxing.message2.service.internal.site.SiteMessageContentService;
import com.dcf.iqunxing.message2.service.internal.site.SiteMessageService;
import com.dcf.iqunxing.message2.service.internal.site.SiteMessageTemplateService;
import com.dcf.iqunxing.message2.util.mapper.JsonMapper;
import com.google.common.collect.Maps;

@Controller
public class SiteMessageController {

    private ILog log = LogManager.getLogger(SiteMessageController.class);

    @Autowired
    private ISiteMsgService siteMsgService;

    @Autowired
    private SiteMessageService siteMessageService;

    @Autowired
    private SiteMessageContentService siteMessageContentService;

    @Autowired
    private SiteMessageTemplateService siteMessageTemplateService;

    @RequestMapping(value = "/api/sendSiteMsg", method = RequestMethod.POST)
    @ResponseBody
    public String sendSiteMsg(Long templateId, String keyValue, String receiver, String scheduleUtcTime, String expiredUtcTime)
            throws IOException {
        SendSiteMsgRequest request = new SendSiteMsgRequest();
        request.setSiteMessageTemplateId(templateId);
        request.setReceiverId(receiver);
        request.setScheduleUtcTime(dateToLong(scheduleUtcTime));
        request.setExpiredUtcTime(dateToLong(expiredUtcTime));

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
        request.setProperties(properties);
        request.setSender("test");

        SendMessageResponse resp = siteMsgService.sendSiteMsg(request);

        return JsonMapper.nonEmptyMapper().toJson(resp.getRetMsg());
    }

    /**
     * 获取站内信模板.
     *
     * @param id
     *            the id
     * @return the push template
     */
    @RequestMapping(value = "/api/getSiteMsgTemplate", method = RequestMethod.GET)
    @ResponseBody
    public String getSiteMsgTemplate(Long id) {
        Map<String, String> resultMap = Maps.newHashMap();
        String template = "不存在[id=" + id + "]对应的模板";
        StringBuffer keyValue = new StringBuffer();
        SiteMessageTemplate po = siteMessageTemplateService.getMsgTemplate(id);
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
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据用户Id查询站内信（测试用默认15条）.
     *
     * @param userId
     *            the user id
     * @return the list
     */
    @RequestMapping(value = "/api/getSiteMsg", method = RequestMethod.GET)
    @ResponseBody
    public List<SiteMsgVo> findSiteMessageByUserId(final String userId) {
        TagBuilder builder = TagBuilder.create().append("userId", userId);
        log.info("查询站内信", "根据用户Id查询站内信" + ":\r\n" + userId, builder.build());
        ListSiteMsgRequest request = new ListSiteMsgRequest();
        Page page = new Page();
        page.setPageNo(1);
        page.setPageSize(5);
        request.setPage(page);
        request.setReceiver(userId);
        PageResult<SiteMsgVo> pageVo = siteMessageService.listSiteMsg(request);
        log.info("查询站内信", "根据用户Id查询站内信" + ":\r\n" + ToStringBuilder.reflectionToString(pageVo, ToStringStyle.MULTI_LINE_STYLE),
                builder.build());
        List<SiteMsgVo> vos = pageVo.getResult();
        return vos;
    }
}
