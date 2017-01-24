package com.lli.mp.controller;

import com.lli.mp.controller.model.UserUiModel;
import com.lli.mp.service.UserAuthService;
import com.lli.mp.wechatclient.util.WechatUriFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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
	                                HttpServletRequest httpRequest, Model model) {
		if(StringUtils.isEmpty(code)) {
			return "errorPage";
		}

		try {
			userAuthService.userLogin(code, httpRequest);
		} catch (Exception e) {
			LOGGER.error("error: {}", e.getMessage());
		}

		model.addAttribute("name", "Bob Smith");

		return "redirect:index";
	}

	@RequestMapping("index")
	public String indexPage(HttpServletRequest httpRequest, Model model) {
		boolean userSignedIn = userAuthService.isUserSignedIn(httpRequest);

		if(!userSignedIn) {
			return "redirect:queryWechatAuthCode";
		}

		UserUiModel userUiModel = userAuthService.getCurrentUser(httpRequest);
		model.addAttribute("user", userUiModel);

		return "index";
	}

	@RequestMapping("local_index")
	public String localIndex(Model model) {
		UserUiModel userUiModel = new UserUiModel("nick name", "1", "province", "city", "country", "headImgUrl");
		model.addAttribute("user", userUiModel);
		return "index";
	}
}
