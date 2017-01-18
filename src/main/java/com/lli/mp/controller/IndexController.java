package com.lli.mp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mp/")
public class IndexController {

	@RequestMapping("queryWechatAuthCode")
	public String index(Model model) {
		model.addAttribute("name", "John Smith");
		return "queryWechatAuthCode";
	}
}
