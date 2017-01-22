package com.lli.mp.service;

import javax.servlet.http.HttpServletResponse;

public interface WechatAuthService {
	String makeWechatOAuthRedirectUri(String redirectUriAfterWechatAuth);
}
