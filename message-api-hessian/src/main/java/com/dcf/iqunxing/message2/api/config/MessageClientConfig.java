package com.dcf.iqunxing.message2.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.dcf.iqunxing.message2.service.IEmailService;
import com.dcf.iqunxing.message2.service.IMessageJobService;
import com.dcf.iqunxing.message2.service.IPushService;
import com.dcf.iqunxing.message2.service.ISiteMsgService;
import com.dcf.iqunxing.message2.service.ISmsService;
import com.dcf.iqunxing.message2.service.ITemplateService;

/**
 * All hessian service definition
 * 
 * @author zhangjiwei
 * @since 2015.8.3
 */
@Configuration
public class MessageClientConfig {

    @Value("${message2.service.url}")
    private String serviceUrl;

    @Bean(name = {
        "message2SmsService"
    })
    public ISmsService smsService() {
        HessianProxyFactoryBean client = new HessianProxyFactoryBean();
        client.setServiceInterface(ISmsService.class);
        client.setServiceUrl(serviceUrl + "/message/smsService");
        client.setOverloadEnabled(true);
        client.afterPropertiesSet();
        return (ISmsService) client.getObject();
    }

    @Bean
    public IMessageJobService messageJobService() {
        HessianProxyFactoryBean client = new HessianProxyFactoryBean();
        client.setServiceInterface(IMessageJobService.class);
        client.setServiceUrl(serviceUrl + "/message/messageJobService");
        client.setOverloadEnabled(true);
        client.afterPropertiesSet();
        return (IMessageJobService) client.getObject();
    }

    @Bean(name = {
        "message2EmailService"
    })
    public IEmailService emailService() {
        HessianProxyFactoryBean client = new HessianProxyFactoryBean();
        client.setServiceInterface(IEmailService.class);
        client.setServiceUrl(serviceUrl + "/message/emailService");
        client.setOverloadEnabled(true);
        client.afterPropertiesSet();
        return (IEmailService) client.getObject();
    }

    @Bean(name = {
        "message2PushService"
    })
    public IPushService pushService() {
        HessianProxyFactoryBean client = new HessianProxyFactoryBean();
        client.setServiceInterface(IPushService.class);
        client.setServiceUrl(serviceUrl + "/message/pushService");
        client.setOverloadEnabled(true);
        client.afterPropertiesSet();
        return (IPushService) client.getObject();
    }

    @Bean(name = {
        "message2SiteMsgService"
    })
    public ISiteMsgService siteMsgService() {
        HessianProxyFactoryBean client = new HessianProxyFactoryBean();
        client.setServiceInterface(ISiteMsgService.class);
        client.setServiceUrl(serviceUrl + "/message/siteMsgService");
        client.setOverloadEnabled(true);
        client.afterPropertiesSet();
        return (ISiteMsgService) client.getObject();
    }

    @Bean(name = {
        "message2TemplateService"
    })
    public ITemplateService templateService() {
        HessianProxyFactoryBean client = new HessianProxyFactoryBean();
        client.setServiceInterface(ITemplateService.class);
        client.setServiceUrl(serviceUrl + "/message/templateService");
        client.setOverloadEnabled(true);
        client.afterPropertiesSet();
        return (ITemplateService) client.getObject();
    }
}
