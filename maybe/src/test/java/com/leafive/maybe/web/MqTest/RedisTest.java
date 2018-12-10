package com.leafive.maybe.web.MqTest;

import com.leafive.maybe.web.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author jj
 * @Date 2018/11/28 10:24
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {

	@Autowired
	RedisService redisService;

	@Test
	public void testSet(){
		boolean set = redisService.set("test", "这是redis测试");
		Assert.assertTrue(set);
		System.out.println(set);
	}

	@Test
	public void testGet(){
		Object test = redisService.get("test");
		System.out.println(test.toString());
		redisService.remove("test");
	}

	@Test
	public void testSetList(){
		List<Object> list = Lists.newArrayList();
		list.add("小米");
		list.add("小白");
		list.add("小黑");
		redisService.lPush("list",list);
	}

	@Test
	public void testGetList(){
		List<Object> list = redisService.lRange("list", 0L, 1L);
		System.out.println(list.toString());
	}

}
