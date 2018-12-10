package com.leafive.maybe.web.rocketMq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author jj
 * @Date 2018/11/28 10:18
 **/
@Configuration
@Slf4j
public class MQConsumerConfiguration {
	@Value("${rocketmq.consumer.namesrvAddr}")
	private String namesrvAddr;
	@Value("${rocketmq.consumer.groupName}")
	private String groupName;
	@Value("${rocketmq.consumer.consumeThreadMin}")
	private int consumeThreadMin;
	@Value("${rocketmq.consumer.consumeThreadMax}")
	private int consumeThreadMax;
	@Value("${rocketmq.consumer.topics}")
	private String topics;
	@Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
	private int consumeMessageBatchMaxSize;

	@Autowired
	private MQConsumeMsgListenerProcessor mqMessageListenerProcessor;

	public DefaultMQPushConsumer getRocketMQConsumer() throws RocketMQException{

		if (StringUtils.isEmpty(groupName)){
			throw new RocketMQException("1","groupName is null !!!",false);
		}
		if (StringUtils.isEmpty(namesrvAddr)){
			throw new RocketMQException("1","namesrvAddr is null !!!",false);
		}
		if(StringUtils.isEmpty(topics)){
			throw new RocketMQException("1","topics is null !!!",false);
		}

		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
		consumer.setNamesrvAddr(namesrvAddr);
		consumer.setConsumeThreadMin(consumeThreadMin);
		consumer.setConsumeThreadMax(consumeThreadMax);
		consumer.registerMessageListener(mqMessageListenerProcessor);

		/**
		 * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		 * 如果非第一次启动，那么按照上次消费的位置继续消费
		 */
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		/**
		 * 设置消费模型，集群还是广播，默认为集群
		 */
		//consumer.setMessageModel(MessageModel.CLUSTERING);
		/**
		 * 设置一次消费消息的条数，默认为1条
		 */
		consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);

		try {
			/**
			 * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，
			 * 则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
			 */
            /*String[] topicTagsArr = topics.split(";");
            for (String topicTags : topicTagsArr) {
                String[] topicTag = topicTags.split("~");
                consumer.subscribe(topicTag[0],topicTag[1]);
            }*/
			consumer.subscribe(topics, "*");
			consumer.start();
		} catch (Exception e) {
			throw new RocketMQException("1","error !!!",false);
		}

		return consumer;
	}

}
