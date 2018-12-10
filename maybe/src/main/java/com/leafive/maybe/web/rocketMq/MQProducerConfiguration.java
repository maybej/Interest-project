package com.leafive.maybe.web.rocketMq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author jj
 * @Date 2018/11/28 9:59
 **/
@Slf4j
@Configuration
public class MQProducerConfiguration {

	/**
	 * 发送同一类消息的设置为同一个group，保证唯一,默认不需要设置，rocketmq会使用ip@pid(pid代表jvm名字)作为唯一标示
	 */
	@Value("${rocketmq.producer.groupName}")
	private String groupName;
	@Value("${rocketmq.producer.namesrvAddr}")
	private String namesrvAddr;
	/**
	 * 消息最大大小，默认4M
	 */
	@Value("${rocketmq.producer.maxMessageSize}")
	private Integer maxMessageSize;
	/**
	 * 消息发送超时时间，默认3秒
	 */
	@Value("${rocketmq.producer.sendMsgTimeout}")
	private Integer sendMsgTimeout;
	/**
	 * 消息发送失败重试次数，默认2次
	 */
	@Value("${rocketmq.producer.retryTimesWhenSendFailed}")
	private Integer retryTimesWhenSendFailed;

	@Bean
	public DefaultMQProducer getRocketMQProducer() throws RocketMQException {

		if (StringUtils.isEmpty(this.groupName)) {
			throw new RocketMQException("1", "groupName is blank", false);
		}

		if (StringUtils.isEmpty(this.namesrvAddr)) {
			throw new RocketMQException("1", "nameServerAddr is blank", false);
		}

		DefaultMQProducer producer;
		producer = new DefaultMQProducer(this.groupName);

		producer.setNamesrvAddr(this.namesrvAddr);
		producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");

		//如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
		//producer.setInstanceName(instanceName);
		if (this.maxMessageSize != null) {
			producer.setMaxMessageSize(this.maxMessageSize);
		}
		if (this.sendMsgTimeout != null) {
			producer.setSendMsgTimeout(this.sendMsgTimeout);
		}
		//如果发送消息失败，设置重试次数，默认为2次
		if (this.retryTimesWhenSendFailed != null) {
			producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);
		}
		try {
			producer.start();
			log.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]", this.groupName, this.namesrvAddr));
		} catch (MQClientException e) {
			log.error(String.format("producer is error {}", e.getMessage(), e));
			throw new RocketMQException("1", "producer is error", false);
		}
		return producer;
	}


}
