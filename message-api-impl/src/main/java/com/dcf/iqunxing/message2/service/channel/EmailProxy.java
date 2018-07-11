package com.dcf.iqunxing.message2.service.channel;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 邮件网关代理
 * 
 * @author yxj
 */
@Service
public class EmailProxy {

    @Autowired
    private JavaMailSender emailTemplate;

    public void setEmailTemplate(JavaMailSender emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    /**
     * 发送邮件.
     *
     * @param from
     *            the from
     * @param senderName
     *            the sender name
     * @param text
     *            the text
     * @param isHtmlText
     *            the is html text
     * @param subject
     *            the subject
     * @param cc
     *            the cc
     * @param to
     *            the to
     * @param bcc
     *            the bcc
     * @param sentDate
     *            the sent date
     * @param attachmentFiles
     *            the attachment files
     * @throws MessagingException
     *             the messaging exception
     * @throws UnsupportedEncodingException
     *             the unsupported encoding exception
     */
    public void sendEmail(String from, String senderName, String text, boolean isHtmlText, String subject, String[] cc, String[] to,
            String[] bcc, List<File> attachmentFiles) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailTemplate.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress(from, MimeUtility.encodeText(senderName, "UTF-8", "B")));
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, isHtmlText);
        if (cc != null && cc.length > 0) {
            helper.setCc(cc);
        }
        if (bcc != null && bcc.length > 0) {
            helper.setBcc(bcc);
        }
        helper.setSentDate(new Date());
        if (attachmentFiles != null && attachmentFiles.size() > 0) {
            for (File file : attachmentFiles) {
                FileSystemResource fileSystemResource = new FileSystemResource(file);
                helper.addAttachment(file.getName(), fileSystemResource);
            }
        }
        emailTemplate.send(message);
    }
}
