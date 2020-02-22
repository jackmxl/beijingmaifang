package com.study.boot1.conteroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloSpringboot {


	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "hellow world";
	}


}
