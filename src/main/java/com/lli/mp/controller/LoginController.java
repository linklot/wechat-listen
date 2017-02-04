package com.lli.mp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}

	@RequestMapping("")
	public String siteDefault() {
		return "redirect:/admin";
	}
}
