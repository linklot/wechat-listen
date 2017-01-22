package com.lli.mp.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

public class HttpWechatAuthServiceTest {

	private HttpWechatAuthService testInstance;
	private String appid = "wx86135fc9a5e67ff3";
	private String extWechatOAuthUri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo#wechat_redirect";

	@Before
	public void initTest() {
		this.testInstance = new HttpWechatAuthService(appid, extWechatOAuthUri);
	}

	@Test
	public void makeWechatOAuthRedirectUri() throws Exception {
		String uri = testInstance.makeWechatOAuthRedirectUri("http://whq628318.cn/mp/queryWechatOpenID");
		assertEquals("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx86135fc9a5e67ff3&redirect_uri=http%3A%2F%2Fwhq628318.cn%2Fmp%2FqueryWechatOpenID&response_type=code&scope=snsapi_userinfo#wechat_redirect",
				uri);
	}

}