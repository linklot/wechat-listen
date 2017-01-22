package com.lli.mp.wechatclient.client.impl;

import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import com.lli.mp.wechatclient.model.UserInfoResponseModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

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
//    	when(restTemplate.getForEntity(anyString(), any())).thenAnswer(givenAccessTokenEntity());
//
//        testInstance.getAccessTokenByCode("code_123");
//        verify(restTemplate, times(1)).getForObject("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx86135fc9a5e67ff3&secret=74f13dbccc1b8323aadebda15d35144d&code=code_123&grant_type=authorization_code",
//                AccessTokenResponseModel.class);
    }

    @Test
	public void testGetUserInfoByTokenAndOpenId() throws Exception {
//    	String accessToken = "access_token";
//    	String openId = "open_id";
//    	testInstance.getUserInfoByTokenAndOpenId(accessToken, openId);
//    	verify(restTemplate, times(1))
//			    .getForObject("https://api.weixin.qq.com/sns/userinfo?access_token=access_token&openid=open_id&lang=zh_CN",
//					    UserInfoResponseModel.class);
    }

    private ResponseEntity givenAccessTokenEntity() {
    	String body = "{\n" +
			    "   \"access_token\":\"ACCESS_TOKEN\",\n" +
			    "   \"expires_in\":7200,\n" +
			    "   \"refresh_token\":\"REFRESH_TOKEN\",\n" +
			    "   \"openid\":\"OPENID\",\n" +
			    "   \"scope\":\"SCOPE\",\n" +
			    "   \"unionid\": \"o6_bmasdasdsad6_2sgVt7hMZOPfL\"\n" +
			    "}";
    	return new ResponseEntity<>(body, HttpStatus.OK);
    }

    private ResponseEntity givenUserInfoEntity() {
    	String body = "{\n" +
			    "   \"openid\":\" OPENID\",\n" +
			    "   \" nickname\": NICKNAME,\n" +
			    "   \"sex\":\"1\",\n" +
			    "   \"province\":\"PROVINCE\"\n" +
			    "   \"city\":\"CITY\",\n" +
			    "   \"country\":\"COUNTRY\",\n" +
			    "    \"headimgurl\":    \"http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46\", \n" +
			    "\t\"privilege\":[\n" +
			    "\t\"PRIVILEGE1\"\n" +
			    "\t\"PRIVILEGE2\"\n" +
			    "    ],\n" +
			    "    \"unionid\": \"o6_bmasdasdsad6_2sgVt7hMZOPfL\"\n" +
			    "}";
    	return new ResponseEntity<>(body, HttpStatus.OK);
    }

}