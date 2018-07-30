package com.leafive.maybe.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leafive.maybe.web.mapper.UserMapper;
import com.leafive.maybe.web.model.User;

@RestController
@RequestMapping("/common")
public class MyController {

	
	@Autowired
	private UserMapper userMapper;
	
	
	@RequestMapping("/hello")
	public String index() {
		return "hello world!";
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public List getUserInfo() {
		List<User> all = userMapper.getAll();
		return all;
	}
	

}
