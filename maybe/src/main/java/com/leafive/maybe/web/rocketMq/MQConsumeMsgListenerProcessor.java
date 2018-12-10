package com.leafive.maybe.web.rocketMq;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Description
 * @Author jj
 * @Date 2018/11/28 10:20
 **/
@Component
@Slf4j
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		if(CollectionUtils.isEmpty(msgs)){
			log.info("接收到的消息为空，不做任何处理");
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
		MessageExt messageExt = msgs.get(0);
		String msg = new String(messageExt.getBody());
		//logger.info("接收到的消息是："+messageExt.toString());
		log.info("接收到的消息是："+msg);
		if(messageExt.getTopic().equals("DemoTopic")){
			if(messageExt.getTags().equals("DemoTag")){
				int reconsumeTimes = messageExt.getReconsumeTimes();
				if(reconsumeTimes == 3){
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
				//TODO 处理对应的业务逻辑
			}
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}


}
