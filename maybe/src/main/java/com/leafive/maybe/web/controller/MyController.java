package com.leafive.maybe.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class MyController {
	
	private final Logger log = LoggerFactory.getLogger(MyController.class);
	
	@Value("${book.name}")
	private String name;

	@Autowired
	private DiscoveryClient client;
	
		
	@RequestMapping("/hello")
	public String index() {
		ServiceInstance instance = client.getLocalServiceInstance();
		log.info("host:{},serviceId:{}",instance.getHost(),instance.getServiceId());
		return name;
	}

}
