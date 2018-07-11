package com.dcf.iqunxing.message2.service;

import com.dcf.iqunxing.message2.model.QueuePriorityVo;

/**
 * 只给Job调用
 * 
 * @author zcj
 *
 */
public interface IMessageJobService {

	public boolean addMessageToQueue(QueuePriorityVo vo, String password);

}
