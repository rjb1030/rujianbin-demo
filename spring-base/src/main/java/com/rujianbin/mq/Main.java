package com.rujianbin.mq;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class Main {

	public static AtomicInteger n=new AtomicInteger(1);
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		//订阅消息和单点消息 修改acclicationContext-mq.xml listener的监听频道，监听topic类型
		ActiveMQQueue queue = context.getBean("testQueue", ActiveMQQueue.class);
		ActiveMQTopic topicQueue = context.getBean("testTopicQueue", ActiveMQTopic.class);
		JmsTemplate jmsTemplate = context.getBean("jmsTemplate", JmsTemplate.class);
		for(int i=1;i<=10;i++){
			jmsTemplate.send(queue, new MessageCreator() {     
	            public javax.jms.Message createMessage(Session session) throws JMSException {  
	            	ObjectMessage objMessage = session.createObjectMessage();
	            	objMessage.setObject(new TestBean("rjb"+n.getAndIncrement(),21)); 
	                return objMessage;  
	            }  
	              
	        });
		}
		System.exit(1);
	}
}
