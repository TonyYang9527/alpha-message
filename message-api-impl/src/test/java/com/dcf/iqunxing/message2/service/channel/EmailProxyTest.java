package com.dcf.iqunxing.message2.service.channel;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
@ActiveProfiles("test")
public class EmailProxyTest {

    @Autowired
    private EmailProxy service;

    @Test
    // @Ignore
    public void testSendEmail() throws UnsupportedEncodingException, MessagingException {
        String from = "service@iqunxing.com";
        String senderName = "群星金融网";
        String text = "群星邮件";
        boolean isHtmlText = true;
        String subject = "群星标题";
        String[] cc = null;
        String[] to = new String[] {
                "ao.wang@dcfservice.net", "zhaoqing.chen@dcfservice.net"
        };
        String[] bcc = null;
        List<File> attachmentFiles = Lists.newArrayList();
        service.sendEmail(from, senderName, text, isHtmlText, subject, cc, to, bcc, attachmentFiles);
    }

}
