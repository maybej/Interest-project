package com.leafive.maybe.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class MyController {

	@RequestMapping("/hello")
	public String index() {
		return "hello world!";
	}

}
