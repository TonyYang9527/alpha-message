package com.dcf.iqunxing.message2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dcf.iqunxing.message2.entity.EmailMessageProperty;
import com.dcf.iqunxing.message2.entity.EmailMessagePropertyExample;

public interface EmailMessagePropertyMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message_property
     *
     * @mbggenerated Mon Aug 17 17:03:04 CST 2015
     */
    int countByExample(EmailMessagePropertyExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message_property
     *
     * @mbggenerated Mon Aug 17 17:03:04 CST 2015
     */
    int deleteByExample(EmailMessagePropertyExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message_property
     *
     * @mbggenerated Mon Aug 17 17:03:04 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message_property
     *
     * @mbggenerated Mon Aug 17 17:03:04 CST 2015
     */
    int insert(EmailMessageProperty record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message_property
     *
     * @mbggenerated Mon Aug 17 17:03:04 CST 2015
     */
    int insertSelective(EmailMessageProperty record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message_property
     *
     * @mbggenerated Mon Aug 17 17:03:04 CST 2015
     */
    List<EmailMessageProperty> selectByExample(EmailMessagePropertyExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message_property
     *
     * @mbggenerated Mon Aug 17 17:03:04 CST 2015
     */
    EmailMessageProperty selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message_property
     *
     * @mbggenerated Mon Aug 17 17:03:04 CST 2015
     */
    int updateByExampleSelective(@Param("record") EmailMessageProperty record, @Param("example") EmailMessagePropertyExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message_property
     *
     * @mbggenerated Mon Aug 17 17:03:04 CST 2015
     */
    int updateByExample(@Param("record") EmailMessageProperty record, @Param("example") EmailMessagePropertyExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message_property
     *
     * @mbggenerated Mon Aug 17 17:03:04 CST 2015
     */
    int updateByPrimaryKeySelective(EmailMessageProperty record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table email_message_property
     *
     * @mbggenerated Mon Aug 17 17:03:04 CST 2015
     */
    int updateByPrimaryKey(EmailMessageProperty record);
}
