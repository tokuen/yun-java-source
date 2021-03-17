package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class HelloController {

	@RequestMapping("/hello.do")
	@ResponseBody
	public String sayHello(){
		return "fox";
	}

	@RequestMapping("/hello2.do")
	@ResponseBody
	public Map<String,String> sayHello2(){
		Map<String,String> map = new HashMap<>();
		map.put("name","fox");
		return map;
	}

	@RequestMapping("/user.do")
	public String user(Model model){
		model.addAttribute("name","fox");
		return "user";
	}
}
