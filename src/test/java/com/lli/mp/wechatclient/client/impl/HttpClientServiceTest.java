package com.lli.mp.wechatclient.client.impl;

import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import com.lli.mp.wechatclient.model.UserInfoResponseModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HttpClientServiceTest {
    private RestTemplate restTemplate = mock(RestTemplate.class);

    private String appId = "wx86135fc9a5e67ff3";
    private String appSecret = "74f13dbccc1b8323aadebda15d35144d";
    private String wechatAccessTokenUriTemplate = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    private String wechatUserInfoUriTemplate = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    private HttpClientService testInstance;

    @Before
    public void initTestCase() {
        this.testInstance = new HttpClientService(appId, appSecret, wechatAccessTokenUriTemplate, wechatUserInfoUriTemplate, restTemplate);
    }

    @Test
    public void testGetAccessTokenByCode() throws Exception {
        testInstance.getAccessTokenByCode("code_123");
        verify(restTemplate, times(1)).getForObject("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx86135fc9a5e67ff3&secret=74f13dbccc1b8323aadebda15d35144d&code=code_123&grant_type=authorization_code",
                AccessTokenResponseModel.class);
    }

    @Test
	public void testGetUserInfoByTokenAndOpenId() {
    	String accessToken = "access_token";
    	String openId = "open_id";
    	testInstance.getUserInfoByTokenAndOpenId(accessToken, openId);
    	verify(restTemplate, times(1))
			    .getForObject("https://api.weixin.qq.com/sns/userinfo?access_token=access_token&openid=open_id&lang=zh_CN",
					    UserInfoResponseModel.class);
    }

}