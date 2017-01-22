package com.lli.mp.service.impl;

import com.lli.mp.service.WechatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class HttpWechatAuthService implements WechatAuthService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private String appid;
	private String extWechatOAuthUri;

	public HttpWechatAuthService(@Value("${appid}") String appid,
	                             @Value("${wechat_oauth_uri}") String extWechatOAuthUri) {
		this.appid = appid;
		this.extWechatOAuthUri = extWechatOAuthUri;
	}

	@Override
	public String makeWechatOAuthRedirectUri(String redirectUriAfterWechatAuth) {
		try {
			String wechatOAuthUri = String.format(extWechatOAuthUri, appid,
					URLEncoder.encode(redirectUriAfterWechatAuth, StandardCharsets.UTF_8.toString()));
			return wechatOAuthUri;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Unsupported encoding!");
		}

		return "";
	}
}
