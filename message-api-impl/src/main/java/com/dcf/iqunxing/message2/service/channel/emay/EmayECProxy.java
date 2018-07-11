package com.dcf.iqunxing.message2.service.channel.emay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dcf.iqunxing.message2.service.channel.SmsProxy;

/**
 * 亿美电子商务999通道
 */
@Component
public class EmayECProxy extends SmsProxy {

    @Value("${sms.emay.ec.no}")
    private String serialNo;

    @Value("${sms.emay.ec.password}")
    private String serialPassword;

    @Value("${sms.emay.ec.key}")
    private String key;

    @Value("${sms.emay.ec.send.url}")
    protected String sendUrl;

    @Value("${sms.emay.ec.sendtime.url}")
    protected String sendTimeUrl;

    @Value("${sms.emay.ec.register.url}")
    protected String registerUrl;

    @Value("${sms.emay.ec.receive.url}")
    protected String receiveUrl;

    @Value("${sms.emay.ec.queryBalance.url}")
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
        return ep;
    }

}
