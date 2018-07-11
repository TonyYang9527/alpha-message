package com.dcf.iqunxing.message2.service.internal.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcf.iqunxing.message2.dao.EmailMessageAttachmentMapper;
import com.dcf.iqunxing.message2.entity.EmailMessageAttachment;
import com.dcf.iqunxing.message2.entity.EmailMessageAttachmentExample;
import com.dcf.iqunxing.message2.entity.EmailMessageAttachmentExample.Criteria;
import com.google.common.base.Preconditions;

@Service
public class EmailMessageAttachmentService {

    @Autowired
    private EmailMessageAttachmentMapper mapper;

    /**
     * 根据EmailMessageId获取对应的name/path.
     *
     * @param messageId
     *            EmailMessageId
     * @return the email props by message id
     */
    public List<EmailMessageAttachment> getEmailAttachmentByMessageId(Long messageId) {
        Preconditions.checkNotNull(messageId);
        EmailMessageAttachmentExample example = new EmailMessageAttachmentExample();
        Criteria criteria = example.createCriteria();
        criteria.andEmailMessageIdEqualTo(messageId);
        List<EmailMessageAttachment> props = mapper.selectByExample(example);
        return props;
    }

    /**
     * 创建email_message_attachment
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public int saveEmailMessageAttachment(Long messageId, String name, String path) {
        EmailMessageAttachment emailMessageAttachment = new EmailMessageAttachment();
        emailMessageAttachment.setEmailMessageId(messageId);
        emailMessageAttachment.setName(name);
        emailMessageAttachment.setPath(path);
        emailMessageAttachment.setCreatedTime(new Date());
        return mapper.insertSelective(emailMessageAttachment);
    }

    /**
     * 更新附件地址
     * 
     * @param id
     * @param path
     */
    public void updateEmailMessageAttachmentPath(Long id, String path) {
        EmailMessageAttachment emailAttachment = new EmailMessageAttachment();
        emailAttachment.setPath(path);
        mapper.updateByPrimaryKeySelective(emailAttachment);
    }

}
