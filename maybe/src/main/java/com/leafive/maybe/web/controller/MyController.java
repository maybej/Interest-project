package com.leafive.maybe.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leafive.maybe.web.mapper.UserMapper;
import com.leafive.maybe.web.model.GeneralResult;
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
	public GeneralResult getUserInfo(@RequestParam int pageNum, @RequestParam(defaultValue = "10", required = false) int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<User> list = userMapper.getAll();
		PageInfo<User> pageInfo = new PageInfo<>(list);
		GeneralResult result = new GeneralResult();
		result.setList(list);
		result.setTotal(pageInfo.getSize());
		return result;
	}
	

}
