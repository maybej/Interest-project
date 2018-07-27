package com.leafive.maybe.web;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.leafive.maybe.web.mapper.UserMapper;
import com.leafive.maybe.web.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaybeApplicationTests {
	
	@Autowired
	private UserMapper UserMapper;

	@Test
	public void contextLoads() {
	}

	
	@Test
	public void testGetUser() {
		List<User> all = UserMapper.getAll();
		for (User user : all) {
			System.out.println("+++++++++"+user.getName());
		}
	}

}
