package com.lli.mp.controller;

import com.lli.mp.service.UserAuthService;
import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import com.lli.mp.wechatclient.model.UserInfoResponseModel;
import com.lli.mp.wechatclient.util.WechatUriFactory;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/mp/")
public class IndexController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private WechatUriFactory wechatUriFactory;
	private UserAuthService userAuthService;

	@Autowired
	public IndexController(WechatUriFactory wechatUriFactory, UserAuthService userAuthService) {
		this.wechatUriFactory = wechatUriFactory;
		this.userAuthService = userAuthService;
	}

	@RequestMapping("queryWechatAuthCode")
	public String queryWechatAuthCode() {
		String wechatOAuthAuthUri = wechatUriFactory.makeWechatOAuthRedirectUri();
		LOGGER.info("Wechat OAuth uri: {}", wechatOAuthAuthUri);

		return "redirect:"+ wechatOAuthAuthUri;
	}

	@RequestMapping("queryWechatOpenID")
	public String queryWechatOpenID(@RequestParam(value = "code", defaultValue = "") String code,
	                                @RequestParam(value = "state", defaultValue = "") String state,
	                                HttpServletResponse httpResponse, Model model) {
		if(StringUtils.isEmpty(code)) {
			return "errorPage";
		}

		try {
			UserInfoResponseModel userInfoResponseModel = userAuthService.getUserDetailsPostAuth(code, httpResponse);

			LOGGER.info("nickname: {}, province: {}", userInfoResponseModel.nickName, userInfoResponseModel.province);
		} catch (Exception e) {
			LOGGER.error("error: {}", e.getMessage());
		}

		model.addAttribute("name", "Bob Smith");

		return "memberIndex";
	}
}
