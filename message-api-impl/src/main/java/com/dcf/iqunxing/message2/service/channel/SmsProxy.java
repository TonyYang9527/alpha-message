package com.dcf.iqunxing.message2.service.channel;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.message2.service.channel.emay.EmayParams;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.google.common.collect.Maps;

// TODO: Auto-generated Javadoc
/**
 * 亿美发送短信网关代理.
 *
 * @author zhangjiwei
 */
@Component
public abstract class SmsProxy {

    /** The Constant LOG_TITLE. */
    private static final String LOG_TITLE = "亿美短信通道服务 ";

    /** 格式化时间. */
    protected static final String SCHEDULE_TIME_FORMAT = "yyyyMMddHHmmss";

    /** 默认优先级. */
    protected static final int DEFAULT_PRIORITY = 5;

    /** 发送短消息超过这个时间（毫秒），就输出日志. */
    protected int threshhold = 5000;

    /** The Constant START_TAG. */
    private static final String START_TAG = "<error>";

    /** The Constant END_TAG. */
    private static final String END_TAG = "</error>";

    /**
     * 群发短信.
     *
     * @param id
     *            长整型值企业内部必须保持唯一，获取状态报告使用
     * @param mobiles
     *            手机号码(群发为字符串数组推荐最多为200个手机号码或以内)
     * @param content
     *            短信内容(最多500个汉字或1000个纯英文，emay服务器程序能够自动分割)`
     * @param scheduleUtcTime
     *            预计发送时间
     * @return Object[2], obj[0] 为是否发送成功, obj[1] 返回结果
     */
    public Object[] send(long id, String[] mobiles, String content, Long scheduleUtcTime) {
        long start = System.currentTimeMillis();
        EmayParams ep = initEmayParams();
        String result = null;
        Map<String, String> params = initBasicParams();
        // phone 手机号码（最多200个），多个用英文逗号(,)隔开
        params.put("phone", StringUtils.join(mobiles, ","));
        // message 短信内容（UTF-8编码）（最多500个汉字或1000个纯英文）
        params.put("message", content);
        // addserial 附加号（最长10位，可置空）
        // params.put("addserial", type);
        // sendtime 定时时间，发定时短信时可增加此参数
        if (scheduleUtcTime != null && scheduleUtcTime > System.currentTimeMillis()) {
            SimpleDateFormat sdf = new SimpleDateFormat(SCHEDULE_TIME_FORMAT);
            String runtime = sdf.format(new Date(scheduleUtcTime));
            params.put("sendtime", runtime);
        }
        // seqid 长整型值企业内部必须保持唯一，获取状态报告使用
        params.put("seqid", String.valueOf(id));
        // smspriority 短信优先级1-5，最高优先级为5
        params.put("smspriority", String.valueOf(DEFAULT_PRIORITY));
        if (scheduleUtcTime == null) {
            result = post(ep.getSendUrl(), params);
        } else {
            result = post(ep.getSendTimeUrl(), params);
        }
        result = result.substring(result.indexOf(START_TAG) + START_TAG.length(), result.indexOf(END_TAG));
        boolean checkResult = checkResult(result, id, System.currentTimeMillis() - start);
        return new Object[] {
                checkResult, result
        };
    }

    /**
     * 接受短信.
     *
     * @return the string
     */
    public String receive() {
        EmayParams ep = initEmayParams();
        Map<String, String> basicParams = initBasicParams();
        return post(ep.getReceiveUrl(), basicParams);
    }

    /**
     * 查询短信余额.
     *
     * @return the string
     */
    public String queryBalance() {
        EmayParams ep = initEmayParams();
        Map<String, String> basicParams = initBasicParams();
        return post(ep.getQueryBalanceUrl(), basicParams);
    }

    /**
     * 初始化emay发送参数.
     *
     * @return the emay params
     */
    protected abstract EmayParams initEmayParams();

    /**
     * 初始化emay基础参数map.
     *
     * @return the map
     */
    public Map<String, String> initBasicParams() {
        Map<String, String> basicParams = Maps.newHashMap();
        EmayParams ep = initEmayParams();
        // cdkey 用户序列号
        basicParams.put("cdkey", ep.getSerialNo());
        // password 用户密码
        basicParams.put("password", ep.getKey());
        return basicParams;
    }

    /**
     * Post 发送短信命令到亿美网关.
     *
     * @param url
     *            the url
     * @param params
     *            the params
     * @return the string
     */
    protected String post(String url, Map<String, String> params) {
        try {
            HttpPost httppost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Entry<String, String> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 设置编码
            httppost.setEntity(new UrlEncodedFormEntity(list, StandardCharsets.UTF_8));
            HttpResponse response = HttpClientBuilder.create().build().execute(httppost);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity).trim();
                    return result;
                }
            }
        } catch (Exception e) {
            LogUtils.error(this.getClass(), LOG_TITLE, "发送短信至亿美网关出错", params, e, TagBuilder.create().append(params));
        }
        return null;
    }

    // -1 发送信息失败（短信内容长度越界）
    // 0 短信发送成功
    // 17 发送信息失败（未激活序列号或序列号和KEY值不对，或账户没有余额等）
    // 101 客户端网络故障
    // 305 服务器端返回错误，错误的返回值（返回值不是数字字符串）
    // 307 目标电话号码不符合规则，电话号码必须是以0、1开头
    // 997 平台返回找不到超时的短信，该信息是否成功无法确定
    /**
     * Check result.
     *
     * @param result
     *            the result
     * @param id
     *            the id
     * @param time
     *            the time
     * @return true, if successful
     */
    // 303 由于客户端网络问题导致信息发送超时，该信息是否成功下发无法确定
    protected boolean checkResult(String result, long id, long time) {
        boolean isSuccess = false;
        TagBuilder builder = TagBuilder.create();
        builder.append("id", id);
        builder.append("resultCode", result);
        if ("0".equals(result)) {
            LogUtils.info(this.getClass(), LOG_TITLE, "发送短信成功", "用时[" + time / 1000 + "秒]", builder);
            isSuccess = true;
        } else if ("17".equals(result)) {
            LogUtils.warn(this.getClass(), LOG_TITLE, "发送短信失败, 原因", "未激活序列号或序列号和KEY值不对，或账户没有余额等", builder);
        } else if ("101".equals(result)) {
            LogUtils.warn(this.getClass(), LOG_TITLE, "发送短信失败, 原因", "客户端网络故障", builder);
        } else if ("305".equals(result)) {
            LogUtils.warn(this.getClass(), LOG_TITLE, "发送短信失败, 原因", "服务器端返回错误，错误的返回值", builder);
        } else if ("307".equals(result)) {
            LogUtils.warn(this.getClass(), LOG_TITLE, "发送短信失败, 原因", "目标电话号码不符合规则，电话号码必须是以0、1开头", builder);
        } else if ("303".equals(result)) {
            LogUtils.warn(this.getClass(), LOG_TITLE, "发送短信失败, 原因", "由于客户端网络问题导致信息发送超时，该信息是否成功下发无法确定", builder);
        } else if ("997".equals(result)) {
            LogUtils.warn(this.getClass(), LOG_TITLE, "发送短信失败, 原因", "平台返回找不到超时的短信，该信息是否成功无法确定", builder);
        } else if ("-1".equals(result)) {
            LogUtils.warn(this.getClass(), LOG_TITLE, "发送短信失败, 原因", "发送信息失败（短信内容长度越界）", builder);
        } else {
            LogUtils.warn(this.getClass(), LOG_TITLE, "发送短信失败, 原因", "未知错误", builder);
        }
        // 超时告警
        if (time > threshhold) {
            LogUtils.warn(this.getClass(), LOG_TITLE, "发送短信用时过长, 用时:" + time + ", 阀值: " + threshhold, null, builder);
        }
        return isSuccess;
    }

}
