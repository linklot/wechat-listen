package com.lli.mp.controller;

import com.lli.mp.service.WechatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mp/")
public class IndexController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final String REDIRECT_URI_AFTER_WECHATAUTH = "http://whq628318.cn/mp/queryWechatOpenID";
	private WechatAuthService wechatAuthService;

	@Autowired
	public IndexController(WechatAuthService wechatAuthService) {
		this.wechatAuthService = wechatAuthService;
	}

	@RequestMapping("queryWechatAuthCode")
	public String queryWechatAuthCode() {
		String wechatOAuthAuthUri = wechatAuthService.makeWechatOAuthRedirectUri(REDIRECT_URI_AFTER_WECHATAUTH);
		LOGGER.info("Wechat OAuth uri: {}", wechatOAuthAuthUri);

		return "redirect:"+ wechatOAuthAuthUri;
	}

	@RequestMapping("queryWechatOpenID")
	public String queryWechatOpenID(@RequestParam(value = "code", defaultValue = "") String code,
	                                @RequestParam(value = "state", defaultValue = "") String state,
	                                Model model) {
		LOGGER.info("code: {}", code);
		LOGGER.info("state: {}", state);

		model.addAttribute("name", "Bob Smith");

		return "memberIndex";
	}
}
