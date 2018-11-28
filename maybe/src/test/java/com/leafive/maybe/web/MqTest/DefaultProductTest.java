package com.leafive.maybe.web.MqTest;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.leafive.maybe.web.RocketMq.MQConsumeMsgListenerProcessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Description
 * @Author jj
 * @Date 2018/11/28 10:24
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DefaultProductTest {

	/**使用RocketMq的生产者*/
	@Autowired
	private DefaultMQProducer defaultMQProducer;

	@Autowired
	MQConsumeMsgListenerProcessor mqConsumeMsgListenerProcessor;

	/*mq发送消息test*/
	@Test
	public void send() throws MQClientException, RemotingException, MQBrokerException, InterruptedException{
		String msg = "哈哈哈哈哈测试数据";
		log.info("开始发送消息："+msg);
		Message sendMsg = new Message("DemoTopic","DemoTag",msg.getBytes());
		//默认3秒超时
		SendResult sendResult = defaultMQProducer.send(sendMsg);
		log.info("消息发送响应信息："+sendResult.toString());
	}

	@Test
	public void consumer(){
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");
		//2.连接namesrv服务器
		consumer.setNamesrvAddr("127.0.0.1:9876");
		try {
			//3.订阅PushTopic的消息
			consumer.subscribe("DemoTopic", "*");
			//4.注册消息监听器
			consumer.registerMessageListener(
					new MessageListenerConcurrently() {
						public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
							Message msg = list.get(0);
							System.out.println(msg);
							String msgstr = new String(msg.getBody());
							System.out.println("msg=====:" + msgstr);
							return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
						}
					}
			);
			//5.启动消费者
			consumer.start();
			Thread.sleep(50 * 1000);
			System.out.println("shutdown...");
			//6.关闭消费者
			consumer.shutdown();
		} catch (Exception e) {
			log.error("error:{}",e);
		}
	}
}
