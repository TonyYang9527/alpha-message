package com.dcf.iqunxing.message2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dcf.iqunxing.message2.entity.EmailMessage;
import com.dcf.iqunxing.message2.entity.EmailMessageExample;

public interface EmailMessageMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message
     *
     * @mbggenerated Wed Aug 12 10:16:04 CST 2015
     */
    int countByExample(EmailMessageExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message
     *
     * @mbggenerated Wed Aug 12 10:16:04 CST 2015
     */
    int deleteByExample(EmailMessageExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message
     *
     * @mbggenerated Wed Aug 12 10:16:04 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message
     *
     * @mbggenerated Wed Aug 12 10:16:04 CST 2015
     */
    int insert(EmailMessage record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message
     *
     * @mbggenerated Wed Aug 12 10:16:04 CST 2015
     */
    int insertSelective(EmailMessage record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message
     *
     * @mbggenerated Wed Aug 12 10:16:04 CST 2015
     */
    List<EmailMessage> selectByExample(EmailMessageExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message
     *
     * @mbggenerated Wed Aug 12 10:16:04 CST 2015
     */
    EmailMessage selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message
     *
     * @mbggenerated Wed Aug 12 10:16:04 CST 2015
     */
    int updateByExampleSelective(@Param("record") EmailMessage record, @Param("example") EmailMessageExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message
     *
     * @mbggenerated Wed Aug 12 10:16:04 CST 2015
     */
    int updateByExample(@Param("record") EmailMessage record, @Param("example") EmailMessageExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message
     *
     * @mbggenerated Wed Aug 12 10:16:04 CST 2015
     */
    int updateByPrimaryKeySelective(EmailMessage record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message
     *
     * @mbggenerated Wed Aug 12 10:16:04 CST 2015
     */
    int updateByPrimaryKey(EmailMessage record);
}
