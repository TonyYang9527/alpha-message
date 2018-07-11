package com.dcf.iqunxing.message2.model.enums;

/**
 * 为兼容旧消息系统。新消息系统统一使用摸板中的type，不接受外部传入的type。
 * 
 * @author zhangjiwei
 * @deprecated
 * @see MsgTemplateType
 */
public enum SiteMsgType {
	
	  /**
	   * 客户和客户发送
	   */
	  CUSTOMER_TO_CUSTOMER((short) 0),
	  /**
	   * 有新的融资单据
	   */
	  NEW_LOAN((short) 11),
	  /**
	   * 融资放款成功
	   */
	  LEND_SUCCESS((short) 12),
	  /**
	   * 有逾期还款
	   */
	  REPAYMENT_OVERDUE((short) 13),
	  /**
	   * 用户认证成功
	   */
	  USER_AUTH_SUCCESS((short) 14),
	  /**
	   * 融资申请审批通过
	   */
	  LOAN_APPLICATION_PASS((short) 15),
	  /**
	   * 融资申请审批失败
	   */
	  LOAN_APPLICATION_FAIL((short) 16),
	  /**
	   * 融资放款拒绝
	   */
	  LEND_FAIL((short) 17),
	  /**
	   * 还款完成
	   */
	  LEND_PAYMENT_FINISH((short) 18),
	  /**
	   * 融资到期提醒
	   */
	  LOANDUE_NOTIFY((short) 19),
	  /**
	   *红包入账提醒
	   */
	  REDENVELOPE_REMIND((short) 20);
	  
	  private short value;

	  SiteMsgType(short value) {
	    this.value = value;
	  }

	  public short getValue() {
	    return value;
	  }

	  public static SiteMsgType fromValue(short value) {
	    for (SiteMsgType flag : SiteMsgType.values()) {
	      if (flag.getValue() == value) {
	        return flag;
	      }
	    }
	    return null;
	  }
	}

