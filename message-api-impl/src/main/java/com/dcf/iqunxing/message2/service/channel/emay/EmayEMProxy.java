package com.dcf.iqunxing.message2.service.channel.emay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dcf.iqunxing.message2.service.channel.SmsProxy;

/**
 * 亿美106共享营销短信通道
 */
@Component
public class EmayEMProxy extends SmsProxy {

    @Value("${sms.emay.em.no}")
    private String serialNo;

    @Value("${sms.emay.em.password}")
    private String serialPassword;

    @Value("${sms.emay.em.key}")
    private String key;

    @Value("${sms.emay.em.send.url}")
    protected String sendUrl;

    @Value("${sms.emay.em.sendtime.url}")
    protected String sendTimeUrl;

    @Value("${sms.emay.em.register.url}")
    protected String registerUrl;

    @Value("${sms.emay.em.receive.url}")
    protected String receiveUrl;

    @Value("${sms.emay.em.queryBalance.url}")
    protected String queryBalanceUrl;

    @Override
    protected EmayParams initEmayParams() {
        EmayParams ep = new EmayParams();
        ep.setKey(key);
        ep.setSerialNo(serialNo);
        ep.setSerialPassword(serialPassword);
        ep.setSendUrl(sendUrl);
        ep.setSendTimeUrl(sendTimeUrl);
        ep.setRegisterUrl(registerUrl);
        ep.setReceiveUrl(receiveUrl);
        ep.setQueryBalanceUrl(queryBalanceUrl);
        ep.setRegisterUrl(registerUrl);
        ep.setReceiveUrl(receiveUrl);
        ep.setQueryBalanceUrl(queryBalanceUrl);
        return ep;
    }

}
