package com.lli.mp.service.impl;

import com.lli.mp.Application;
import com.lli.mp.entity.User;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class DefaultLocalUserServiceTest {

	@Autowired
	private DefaultLocalUserService testInstance;

	@BeforeClass
	public static void initTestCase() {
		System.setProperty("appid", "123");
		System.setProperty("appsecret", "456");
		System.setProperty("wechat_oauth_uri_template", "xxx");
		System.setProperty("wechat_accesstoken_uri_template", "xxx");
		System.setProperty("wechat_userinfo_uri_template", "xxx");
		System.setProperty("user_details_local_uri", "xxx");

		System.setProperty("spring.data.mongodb.database", "wechat");
		System.setProperty("spring.data.mongodb.uri", "mongodb://localhost/wechat");
	}

	@Test
	public void testSaveUser() throws Exception {
//		User user = testInstance.saveOrUpdateUser(givenUser());
	}
//
//	private User givenUser() {
//		User user = new User();
//
//		user.openId = "open id";
//		user.nickName = "nickname";
//		user.sex = "1";
//		user.province = "province";
//		user.city = "city";
//		user.country = "country";
//		user.headImgUrl = "headimgurl";
//		user.unionId = "unionid";
//		user.accessToken = "access token";
//		user.expiresIn = 7200;
//		user.refreshToken = "refresh token";
//		user.scope = "scope";
//		user.lastLoginDateTime = new DateTime().toString(ISODateTimeFormat.dateTime());
//
//		return user;
//	}

}