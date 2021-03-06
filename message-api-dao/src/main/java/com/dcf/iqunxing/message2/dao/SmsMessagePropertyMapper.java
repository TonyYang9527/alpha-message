package com.dcf.iqunxing.message2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dcf.iqunxing.message2.entity.SmsMessageProperty;
import com.dcf.iqunxing.message2.entity.SmsMessagePropertyExample;

public interface SmsMessagePropertyMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table sms_message_property
     *
     * @mbggenerated Fri Aug 14 19:21:34 CST 2015
     */
    int countByExample(SmsMessagePropertyExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table sms_message_property
     *
     * @mbggenerated Fri Aug 14 19:21:34 CST 2015
     */
    int deleteByExample(SmsMessagePropertyExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table sms_message_property
     *
     * @mbggenerated Fri Aug 14 19:21:34 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table sms_message_property
     *
     * @mbggenerated Fri Aug 14 19:21:34 CST 2015
     */
    int insert(SmsMessageProperty record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table sms_message_property
     *
     * @mbggenerated Fri Aug 14 19:21:34 CST 2015
     */
    int insertSelective(SmsMessageProperty record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table sms_message_property
     *
     * @mbggenerated Fri Aug 14 19:21:34 CST 2015
     */
    List<SmsMessageProperty> selectByExample(SmsMessagePropertyExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table sms_message_property
     *
     * @mbggenerated Fri Aug 14 19:21:34 CST 2015
     */
    SmsMessageProperty selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table sms_message_property
     *
     * @mbggenerated Fri Aug 14 19:21:34 CST 2015
     */
    int updateByExampleSelective(@Param("record") SmsMessageProperty record, @Param("example") SmsMessagePropertyExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table sms_message_property
     *
     * @mbggenerated Fri Aug 14 19:21:34 CST 2015
     */
    int updateByExample(@Param("record") SmsMessageProperty record, @Param("example") SmsMessagePropertyExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table sms_message_property
     *
     * @mbggenerated Fri Aug 14 19:21:34 CST 2015
     */
    int updateByPrimaryKeySelective(SmsMessageProperty record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table sms_message_property
     *
     * @mbggenerated Fri Aug 14 19:21:34 CST 2015
     */
    int updateByPrimaryKey(SmsMessageProperty record);
}
