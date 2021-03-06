package com.rujianbin.mq;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component("testQueueListener2")
public class TestQueueListener2 extends MessageListenerAdapter{

	private static final Logger  LOGGER = LoggerFactory.getLogger(TestQueueListener2.class);
	
	@Override
	public void onMessage(Message message,Session session) {
		try {
			ObjectMessage t = (ObjectMessage) message;
			TestBean bean = (TestBean)t.getObject();
			LOGGER.info("------------- listener2接收队列消息---key1="+bean.getName()+",key2="+bean.getAge());
	
			message.acknowledge();
		} catch (Exception e) {
			LOGGER.info("",e);
			try{
				session.recover();
			}catch(Exception e1){
				LOGGER.info("",e1);
			}
		}
	}
}
