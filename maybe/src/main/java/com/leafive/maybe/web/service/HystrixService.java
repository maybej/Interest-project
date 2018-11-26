package com.leafive.maybe.web.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author jj
 * @Date 2018/11/26 13:52
 **/
@Service
public class HystrixService {

	@HystrixCommand(fallbackMethod = "secondUserInterface" ,commandProperties = {
			@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "2"),
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "300000"),
	})
	public String firstUserInterface() throws Exception{
		Thread.sleep(500);
		return "服务熔断第一次调用";
	}

	public String secondUserInterface(){
		return "服务熔断第二次调用";
	}

}
