package com.dcf.iqunxing.message2.service.channel.entity.req;

import java.io.File;
import java.util.List;

import com.dcf.iqunxing.message2.model.enums.MsgType;
import com.dcf.iqunxing.message2.service.channel.entity.ChannelReq;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailChannelReq.
 */
public class EmailChannelReq extends ChannelReq {

    private static final long serialVersionUID = 2965772170128084637L;

    private final String from;

    private final String title;

    private final String senderName;

    private final Boolean isHtmlText;

    private final String[] cc;

    private final String[] bcc;

    private final List<File> attachmentFiles;

    public EmailChannelReq(Long id, String[] target, String content, String from, String title, String senderName,
            Boolean isHtmlText, String[] cc, String[] bcc, List<File> attachmentFiles) {
        super.setId(id);
        super.setContent(content);
        super.setTarget(target);
        super.setMsgType(MsgType.EMAIL);
        this.from = from;
        this.title = title;
        this.senderName = senderName;
        this.isHtmlText = isHtmlText;
        this.cc = cc;
        this.bcc = bcc;
        this.attachmentFiles = attachmentFiles;
    }

    public String getFrom() {
        return from;
    }

    public String getTitle() {
        return title;
    }

    public String getSenderName() {
        return senderName;
    }

    public Boolean getIsHtmlText() {
        return isHtmlText;
    }

    public String[] getCc() {
        return cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public List<File> getAttachmentFiles() {
        return attachmentFiles;
    }

}
